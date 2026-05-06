import capsule.*;
import java.io.*;
import java.util.*;
import lanceur.*;
import mission.*;
import modele.*;

public class Simulateur {

    // Constantes
    private static final double PRIX_KEROSENE_PAR_TONNE = 1200;
    private static final double PROBA_ECHEC = 0.05;
    private static final String FICHIER_HISTORIQUE = "historique.csv";

    // Collections
    private final List<Lancement> historique = new ArrayList<>();

    // Constructeur simple
    public Simulateur() {
        chargerHistorique();
    }

    /**
     * Effectue un lancement et retourne le résultat
     */
    public Lancement effectuerLancement(Fusee fusee, Mission mission) 
            throws CarburantInsuffisantException {
        
        double masse = fusee.calculerMasseTotale();
        double carburant = mission.calculerCarburantNecessaire(masse);

        // Vérification 1 : Carburant suffisant
        if (carburant > fusee.getLanceur().getCarburantMaxTonnes()) {
            throw new CarburantInsuffisantException("Carburant insuffisant");
        }

        // Vérification 2 : Masse de la capsule acceptable (payload)
        if (fusee.getCapsule().getMasseTonnes() > fusee.getLanceur().getChargeUtileTonnes()) {
            return creerLancement(fusee, mission, false, "Surcharge de la capsule dépassée", carburant);
        }

        // Vérification 3 : Nombre de boosters acceptable
        if (fusee.getBoosters().size() > fusee.getLanceur().getBoostersMax()) {
            return creerLancement(fusee, mission, false, "Trop de boosters", carburant);
        }

        // Vérification 4 : Compatibilité mission habitée (capsule + lanceur)
        if (mission.isHabitee()) {
            if (!fusee.getCapsule().isHabitee()) {
                return creerLancement(fusee, mission, false, 
                    "Capsule incompatible avec une mission habitée", carburant);
            }
            if (!fusee.getLanceur().isHabite()) {
                return creerLancement(fusee, mission, false, 
                    "Lanceur non certifié pour le vol habité", carburant);
            }
        }

        // Vérification 5 : Chance d'échec aléatoire
        if (Math.random() < PROBA_ECHEC) {
            return creerLancement(fusee, mission, false, "Anomalie technique imprévue", carburant);
        }

        // Succès !
        return creerLancement(fusee, mission, true, "Succès", carburant);
    }

    /**
     * Crée un lancement et l'ajoute à l'historique
     */
    private Lancement creerLancement(Fusee fusee, Mission mission, 
                                     boolean succes, String raison, double carburant) {
        double coutCarburantMillions = (carburant * PRIX_KEROSENE_PAR_TONNE) / 1_000_000.0;
        double coutTotal = fusee.calculerCoutMateriel() + coutCarburantMillions;
        
        Lancement lancement = new Lancement(fusee, mission, succes, raison, coutTotal);
        historique.add(lancement);
        sauvegarderHistorique();
        
        return lancement;
    }

    /**
     * Charge l'historique depuis le fichier CSV
     */
    private void chargerHistorique() {
        File fichier = new File(FICHIER_HISTORIQUE);
        if (!fichier.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                System.out.println("[Historique] " + ligne);
            }
        } catch (IOException e) {
            System.err.println("Erreur lecture historique : " + e.getMessage());
        }
    }

    /**
     * Sauvegarde l'historique dans le fichier CSV
     */
    private void sauvegarderHistorique() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIER_HISTORIQUE))) {
            for (Lancement lancement : historique) {
                bw.write(lancement.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde : " + e.getMessage());
        }
    }

    // Accesseurs
    public List<Lancement> obtenirHistorique() {
        return historique;
    }

    public int obtenirNombreLancementsReussis() {
        return (int) historique.stream().filter(l -> l.isSucces()).count();
    }

    public int obtenirNombreLancementsEchoues() {
        return (int) historique.stream().filter(l -> !l.isSucces()).count();
    }

    public double obtenirCoutTotal() {
        return historique.stream()
            .mapToDouble(l -> l.getCoutTotal())
            .sum();
    }

    // --- MENU INTERACTIF ---

    public static void main(String[] args) {
        Simulateur simulateur = new Simulateur();
        Scanner scanner = new Scanner(System.in);
        int choix = -1;

        while (choix != 0) {
            afficherMenuPrincipal();
            System.out.print("Choix : ");
            
            try {
                choix = Integer.parseInt(scanner.nextLine());
                
                switch (choix) {
                    case 1 -> simulateur.lancerFusee(scanner);
                    case 2 -> simulateur.afficherHistorique();
                    case 3 -> afficherStatistiques(simulateur);
                    case 0 -> System.out.println("Au revoir !");
                    default -> System.out.println(" Option invalide.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Entrée invalide, veuillez entrer un nombre.");
            } catch (CarburantInsuffisantException e) {
                System.out.println(" Erreur : " + e.getMessage());
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void afficherMenuPrincipal() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  SIMULATEUR DE LANCEMENT SPATIAL       ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Configurer et lancer une fusée");
        System.out.println("2. Consulter l'historique");
        System.out.println("3. Voir les statistiques");
        System.out.println("0. Quitter");
    }

    private void lancerFusee(Scanner scanner) throws CarburantInsuffisantException {
        System.out.println("\n--- Configuration de la fusée ---");

        // Choisir le lanceur
        Lanceur lanceur = choisirLanceur(scanner);
        if (lanceur == null) return;

        // Choisir la capsule
        Capsule capsule = choisirCapsule(scanner);
        if (capsule == null) return;

        // Choisir les boosters
        List<Booster> boosters = choisirBoosters(scanner, lanceur);

        // Créer la fusée
        Fusee fusee = new Fusee(lanceur, capsule, boosters);

        // Afficher la configuration
        System.out.println("\n✓ Fusée configurée : " + fusee);

        // Choisir la mission
        Mission mission = choisirMission(scanner);
        if (mission == null) return;

        // Effectuer le lancement
        System.out.println("\n Lancement en cours...\n");
        Lancement lancement = effectuerLancement(fusee, mission);
        afficherResultatLancement(lancement);
    }

    private Lanceur choisirLanceur(Scanner scanner) {
        List<Lanceur> lanceurs = Arrays.asList(
            new SaturneV(), new Ariane5(), new Falcon9(), new SLS()
        );

        System.out.println("\nLanceurs disponibles :");
        for (int i = 0; i < lanceurs.size(); i++) {
            System.out.println((i + 1) + ". " + lanceurs.get(i));
        }
        System.out.print("Choix : ");

        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < lanceurs.size()) {
                return lanceurs.get(index);
            }
        } catch (NumberFormatException e) {
            // Erreur ignorée
        }

        System.out.println(" Choix invalide.");
        return null;
    }

    private Capsule choisirCapsule(Scanner scanner) {
        List<Capsule> capsules = Arrays.asList(
            new Orion(), new CrewDragon(), new Apollo(), new CargoDragon()
        );

        System.out.println("\nCapsules disponibles :");
        for (int i = 0; i < capsules.size(); i++) {
            System.out.println((i + 1) + ". " + capsules.get(i));
        }
        System.out.print("Choix : ");

        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < capsules.size()) {
                return capsules.get(index);
            }
        } catch (NumberFormatException e) {
            // Erreur ignorée
        }

        System.out.println(" Choix invalide.");
        return null;
    }

    private List<Booster> choisirBoosters(Scanner scanner, Lanceur lanceur) {
        List<Booster> boostersDispo = Arrays.asList(
            new Booster("EAP", 6470, 270, 30),
            new Booster("SRB", 12500, 590, 55),
            new Booster("BE-3", 490, 25, 12)
        );

        List<Booster> boosters = new ArrayList<>();

        System.out.println("\nBoosters disponibles (max : " + lanceur.getBoostersMax() + ") :");
        for (int i = 0; i < boostersDispo.size(); i++) {
            System.out.println((i + 1) + ". " + boostersDispo.get(i).getNom());
        }

        while (boosters.size() < lanceur.getBoostersMax()) {
            System.out.print("Ajouter un booster ? (0 = fin, 1-" + boostersDispo.size() + ") : ");

            try {
                int choix = Integer.parseInt(scanner.nextLine());
                if (choix == 0) break;

                if (choix > 0 && choix <= boostersDispo.size()) {
                    boosters.add(boostersDispo.get(choix - 1));
                    System.out.println("✓ Booster ajouté (" + boosters.size() + "/" + lanceur.getBoostersMax() + ")");
                } else {
                    System.out.println(" Choix invalide.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Entrée invalide.");
            }
        }

        return boosters;
    }

    private Mission choisirMission(Scanner scanner) {
        List<Mission> missions = Arrays.asList(
            new MissionOrbite(), new MissionISS(),
            new MissionLune(), new MissionMars(), new MissionPersonnelle()
        );

        System.out.println("\nMissions disponibles :");
        for (int i = 0; i < missions.size(); i++) {
            System.out.println((i + 1) + ". " + missions.get(i));
        }
        System.out.print("Choix : ");

        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < missions.size()) {
                return missions.get(index);
            }
        } catch (NumberFormatException e) {
            // Erreur ignorée
        }

        System.out.println("Choix invalide.");
        return null;
    }

    private void afficherResultatLancement(Lancement lancement) {
        System.out.println("╔════════════════════════════════════════╗");
        if (lancement.isSucces()) {
            System.out.println("║          ✓ LANCEMENT RÉUSSI            ║");
        } else {
            System.out.println("║          ✗ LANCEMENT ÉCHOUÉ            ║");
        }
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Fusée    : " + lancement.getFusee());
        System.out.println("Mission  : " + lancement.getMission().getNom());
        System.out.println("Raison   : " + lancement.getRaison());
        System.out.println("Coût     : " + String.format("%.2f", lancement.getCoutTotal()) + "M€");
    }

    private void afficherHistorique() {
        if (historique.isEmpty()) {
            System.out.println(" Aucun lancement dans l'historique.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           HISTORIQUE DES LANCEMENTS    ║");
        System.out.println("╚════════════════════════════════════════╝");

        for (int i = 0; i < historique.size(); i++) {
            Lancement l = historique.get(i);
            String statut = l.isSucces() ? "✓" : "✗";
            System.out.println((i + 1) + ". [" + statut + "] " + l.getFusee() + " → " + l.getMission().getNom() +
                    " (" + String.format("%.2f", l.getCoutTotal()) + "M€)");
        }
    }

    private static void afficherStatistiques(Simulateur simulateur) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           STATISTIQUES GLOBALES        ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Lancements réussis : " + simulateur.obtenirNombreLancementsReussis());
        System.out.println("Lancements échoués : " + simulateur.obtenirNombreLancementsEchoues());
        System.out.println("Total lancements   : " + (simulateur.obtenirNombreLancementsReussis() + 
                                                       simulateur.obtenirNombreLancementsEchoues()));
        System.out.println("Coût total         : " + String.format("%.2f", simulateur.obtenirCoutTotal()) + "M€");

        if (simulateur.obtenirNombreLancementsReussis() > 0) {
            double tauxReussite = (double) simulateur.obtenirNombreLancementsReussis() / 
                    (simulateur.obtenirNombreLancementsReussis() + simulateur.obtenirNombreLancementsEchoues()) * 100;
            System.out.println("Taux de réussite   : " + String.format("%.1f", tauxReussite) + "%");
        }
    }
}
