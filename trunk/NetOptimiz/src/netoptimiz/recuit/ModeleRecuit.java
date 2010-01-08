package netoptimiz.recuit;

public abstract class ModeleRecuit {

    private double temperature;

    private int decroissanceTemp;

    private int iterationsInternes;

    private int nombrePalliers;

    public ModeleRecuit () {
    }

    public int getDecroissanceTemp () {
        return decroissanceTemp;
    }

    public void setDecroissanceTemp (int val) {
        this.decroissanceTemp = val;
    }

    public int getIterationsInternes () {
        return iterationsInternes;
    }

    public void setIterationsInternes (int val) {
        this.iterationsInternes = val;
    }

    public double getTemperature () {
        return temperature;
    }

    public void setTemperature (double val) {
        this.temperature = val;
    }

    public double resoudre () {
        return 0.0;
    }

    public int getNombrePalliers () {
        return nombrePalliers;
    }

    public void setNombrePalliers (int val) {
        this.nombrePalliers = val;
    }

    public abstract void faireMvt ();

    public abstract void accepterMVT ();

    public abstract double calculerCout ();

    public abstract double deltaCout ();

    public void tempInitiale () {
    }

    public abstract void refuserMvt ();

}

