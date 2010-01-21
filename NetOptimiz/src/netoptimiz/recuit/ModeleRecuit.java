package netoptimiz.recuit;

import netoptimiz.graphe.Graphe;

public abstract class ModeleRecuit {

    private double temperature;
    private double decroissanceTemp;
    private int iterationsInternes;
    private int nombrePalliers;

    public ModeleRecuit () {

    }

    public double getDecroissanceTemp () {
        return decroissanceTemp;
    }

    public void setDecroissanceTemp (double val) {
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

    public int getNombrePalliers () {
        return nombrePalliers;
    }

    public void setNombrePalliers (int val) {
        this.nombrePalliers = val;
    }

    public void tempInitiale () {
    }

    public abstract double resoudre (int nombrePalliers, int iterationsInternes);

    public abstract void faireMvt (Graphe g);

    public abstract boolean accepterMVT (Graphe g);

    public abstract double calculerCout (Graphe g);

    public abstract double deltaCout ();

    public abstract void refuserMvt ();

}

