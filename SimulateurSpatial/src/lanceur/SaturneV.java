package lanceur;

public class SaturneV extends Lanceur {
    public SaturneV() { super("Saturne V", true, 0, 2700, 140, 1500); }

    @Override
    public double calculerPousseeMax() { return 34000; }
}