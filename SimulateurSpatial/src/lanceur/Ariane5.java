package lanceur;

public class Ariane5 extends Lanceur {
    public Ariane5() { super("Ariane 5", false, 2, 700, 20, 180); }

    @Override
    public double calculerPousseeMax() { return 13000; }
}