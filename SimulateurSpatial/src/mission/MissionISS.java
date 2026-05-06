package mission;

public class MissionISS extends Mission {
    public MissionISS() { super("ISS", true, 400, 1.2); }

    @Override
    public double calculerCarburantNecessaire(double masse) {
        return (masse * getDistanceKm() * getCoeffCarburant()) / 1000;
    }
}