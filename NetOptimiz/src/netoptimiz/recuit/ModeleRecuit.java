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

    // Clacul de la température initiale selon Kirkpatrick
    public double tempInitiale (Graphe g) {
        Graphe tempGrapheTinit = g.clone();
        double pi=0;
        // Définition d'une température de départ (faible)
        double tempInit=100;
        do {
            // On défini le nombre de transformation couteuse à évaluer
            double nbTransCouteuses=tempGrapheTinit.getarcs().size()*2;
            // Afin de compter le nombre de transformations couteuses engendrées
            int nbEngendre=0;
            do {
                faireMvt(tempGrapheTinit);
                accepterMVT(tempGrapheTinit);
                
            }
            while (nbEngendre==20); // On a déterminé que l'on s'arrétait à ce nombre de transformations couteuses

            // Dans le cas où le while serait vérifié
            tempInit=tempInit*2;
        }
        while (pi<0.75); // objectif à atteindre : pi doit être au moin égale à 075

        return tempInit;
    }

    public abstract double resoudre (int nombrePalliers, int iterationsInternes);

    public abstract void faireMvt (Graphe g);

    public abstract boolean accepterMVT (Graphe g);

    public abstract double calculerCout (Graphe g);

    public abstract double deltaCout ();

    public abstract void refuserMvt ();

}

