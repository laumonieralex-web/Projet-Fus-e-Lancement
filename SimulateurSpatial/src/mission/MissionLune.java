package mission;

public class MissionLune extends Mission {
    public MissionLune() { super("Lune", true, 400000, 0.005); }

    @Override
    public double calculerCarburantNecessaire(double masse) {
        return (masse * getDistanceKm() * getCoeffCarburant()) / 1000;
    }
}