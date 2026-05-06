package mission;

public class MissionPersonnelle extends Mission {
    // Point de Lagrange L2 — satellite d'observation, non habité
    public MissionPersonnelle() { super("Point de Lagrange L2", false, 1500000, 0.003); }

    @Override
    public double calculerCarburantNecessaire(double masse) {
        return (masse * getDistanceKm() * getCoeffCarburant()) / 1000;
    }
}