package lanceur;

public abstract class Lanceur {

    private final String nom;
    private final boolean habite;
    private final int boostersMax;
    private final double carburantMaxTonnes;
    private final double chargeUtileTonnes;
    private final double prixMillionsEuros;

    public Lanceur(String nom, boolean habite, int boostersMax,
                   double carburantMaxTonnes, double chargeUtileTonnes,
                   double prixMillionsEuros) {
        this.nom = nom;
        this.habite = habite;
        this.boostersMax = boostersMax;
        this.carburantMaxTonnes = carburantMaxTonnes;
        this.chargeUtileTonnes = chargeUtileTonnes;
        this.prixMillionsEuros = prixMillionsEuros;
    }

    public abstract double calculerPousseeMax();

    public String getNom()                  { return nom; }
    public boolean isHabite()               { return habite; }
    public int getBoostersMax()             { return boostersMax; }
    public double getCarburantMaxTonnes()   { return carburantMaxTonnes; }
    public double getChargeUtileTonnes()    { return chargeUtileTonnes; }
    public double getPrixMillionsEuros()    { return prixMillionsEuros; }

    @Override
    public String toString() {
        return nom + " | " + chargeUtileTonnes + "t payload | "
             + carburantMaxTonnes + "t carbu | " + prixMillionsEuros + "M€";
    }
}