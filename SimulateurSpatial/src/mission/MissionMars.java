package mission;

public class MissionMars extends Mission {
    public MissionMars() { super("Mars", true, 225000000, 0.000015); }

    @Override
    public double calculerCarburantNecessaire(double masse) {
        return (masse * getDistanceKm() * getCoeffCarburant()) / 1000;
    }
}