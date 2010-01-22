package netoptimiz.recuit;

import netoptimiz.graphe.Graphe;

public abstract class ModeleRecuit {

    private double temperature;
    private double decroissanceTemp;
    private int iterationsInternes;
    private int nombrePalliers;

    public ModeleRecuit () {

    }

    // Valeur de la température à décrémenter (g(t)=µt , 0<µ<1. En pratique, µ≃0,95)
    public double getDecroissanceTemp () {
        return 0.95;
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

    public double tempInitiale (Graphe g) {
        // Calcul du cout initial
        double coutInitial=calculerCout(g);
        // On génère un nombre de transformations couteuses

        // On calcule le cout moyen OU détérioration moyenne

        // Taux d'acceptation fixe à 60%

        // tempInitiale= cout moyen / (ln(1/0.6))
        double tempInitiale=Math.log(1/0.6);
        return tempInitiale;
    }

    public abstract double resoudre (int nombrePalliers, int iterationsInternes);

    public abstract void faireMvt (Graphe g);

    public abstract boolean accepterMVT (Graphe g);

    public abstract double calculerCout (Graphe g);

    public abstract double deltaCout ();

    public abstract void refuserMvt ();

}

