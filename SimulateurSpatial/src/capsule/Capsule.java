package capsule;

public abstract class Capsule {

    private String nom;
    private boolean habitee;
    private int occupantsMax;
    private double masseTonnes;
    private double prixMillionsEuros;

    public Capsule(String nom, boolean habitee, int occupantsMax,
                   double masseTonnes, double prixMillionsEuros) {
        this.nom = nom;
        this.habitee = habitee;
        this.occupantsMax = occupantsMax;
        this.masseTonnes = masseTonnes;
        this.prixMillionsEuros = prixMillionsEuros;
    }

    public String getNom()                { return nom; }
    public boolean isHabitee()            { return habitee; }
    public int getOccupantsMax()          { return occupantsMax; }
    public double getMasseTonnes()        { return masseTonnes; }
    public double getPrixMillionsEuros()  { return prixMillionsEuros; }

    @Override
    public String toString() {
        return nom + " | habitée: " + habitee + " | " + masseTonnes + "t | " + prixMillionsEuros + "M€";
    }
}