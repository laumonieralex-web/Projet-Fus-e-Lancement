package modele;

import java.time.LocalDateTime;
import mission.Mission;

public class Lancement {

    private  final Fusee fusee;
    private final Mission mission;
    private final LocalDateTime date;
    private final boolean succes;
    private final String raison;
    private final double coutTotal;

    public Lancement(Fusee fusee, Mission mission,
                     boolean succes, String raison, double coutTotal) {
        this.fusee = fusee;
        this.mission = mission;
        this.date = LocalDateTime.now();
        this.succes = succes;
        this.raison = raison;
        this.coutTotal = coutTotal;
    }

    public String toCsv() {
        return date + ";" + fusee.getLanceur().getNom() + ";"
             + mission.getNom() + ";" + succes + ";" + raison + ";" + coutTotal;
    }

    public boolean isSucces()    { return succes; }
    public double getCoutTotal() { return coutTotal; }
    public Mission getMission()  { return mission; }
    public Fusee getFusee()      { return fusee; }
    public String getRaison()    { return raison; }

    @Override
    public String toString() {
        return toCsv().replace(";", " | ");
    }
}