package mission;

public abstract class Mission {

    private String nom;
    private boolean habitee;
    private double distanceKm;
    private double coeffCarburant;

    public Mission(String nom, boolean habitee, double distanceKm, double coeffCarburant) {
        this.nom = nom;
        this.habitee = habitee;
        this.distanceKm = distanceKm;
        this.coeffCarburant = coeffCarburant;
    }

    public abstract double calculerCarburantNecessaire(double masseFuseeTonnes);

    public String getNom()              { return nom; }
    public boolean isHabitee()          { return habitee; }
    public double getDistanceKm()       { return distanceKm; }
    public double getCoeffCarburant()   { return coeffCarburant; }

    @Override
    public String toString() {
        return nom + " | habitée: " + habitee + " | " + distanceKm + " km";
    }
}