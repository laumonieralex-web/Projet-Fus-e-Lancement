package mission;

public class MissionOrbite extends Mission {
    public MissionOrbite() { super("Orbite terrestre", false, 400, 1.0); }

    @Override
    public double calculerCarburantNecessaire(double masse) {
        return (masse * getDistanceKm() * getCoeffCarburant()) / 1000;
    }
}