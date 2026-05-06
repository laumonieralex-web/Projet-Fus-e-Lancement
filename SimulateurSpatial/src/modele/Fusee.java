package modele;

import capsule.Capsule;
import java.util.ArrayList;
import java.util.List;
import lanceur.Lanceur;

public class Fusee {

    private final Lanceur lanceur;
    private final Capsule capsule;
    private List<Booster> boosters = new ArrayList<>();

    // surcharge 1 — sans boosters
    public Fusee(Lanceur lanceur, Capsule capsule) {
        this.lanceur = lanceur;
        this.capsule = capsule;
    }

    // surcharge 2 — avec boosters
    public Fusee(Lanceur lanceur, Capsule capsule, List<Booster> boosters) {
        this.lanceur = lanceur;
        this.capsule = capsule;
        this.boosters = boosters;
    }

    // surcharge 3 — ajouter un booster
    public void ajouterBooster(Booster b) {
        boosters.add(b);
    }

    // surcharge 4 — ajouter plusieurs boosters
    public void ajouterBooster(Booster b, int quantite) {
        for (int i = 0; i < quantite; i++) boosters.add(b);
    }

    public double calculerMasseTotale() {
        double masse = capsule.getMasseTonnes();
        for (Booster b : boosters) {
            masse += b.getMasseTonnes();
        }
        return masse;
    }

    public double calculerCoutMateriel() {
        double cout = lanceur.getPrixMillionsEuros() + capsule.getPrixMillionsEuros();
        for (Booster b : boosters) {
            cout += b.getPrixMillionsEuros();
        }
        return cout;
    }

    public Lanceur getLanceur()        { return lanceur; }
    public Capsule getCapsule()        { return capsule; }
    public List<Booster> getBoosters() { return boosters; }

    @Override
    public String toString() {
        return "Fusée [" + lanceur.getNom() + " + " + capsule.getNom()
             + " + " + boosters.size() + " booster(s)]";
    }
}