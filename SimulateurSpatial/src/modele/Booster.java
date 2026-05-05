package modele;

public class Booster {

    private final String nom;
    private final double pousseeKN;
    private final double masseTonnes;
    private final double prixMillionsEuros;

    public Booster(String nom, double pousseeKN, double masseTonnes, double prixMillionsEuros) {
        this.nom = nom;
        this.pousseeKN = pousseeKN;
        this.masseTonnes = masseTonnes;
        this.prixMillionsEuros = prixMillionsEuros;
    }

    public String getNom()               { return nom; }
    public double getPousseeKN()         { return pousseeKN; }
    public double getMasseTonnes()       { return masseTonnes; }
    public double getPrixMillionsEuros() { return prixMillionsEuros; }

    @Override
    public String toString() {
        return nom + " | " + pousseeKN + " kN | " + masseTonnes + "t | " + prixMillionsEuros + "M€";
    }
}