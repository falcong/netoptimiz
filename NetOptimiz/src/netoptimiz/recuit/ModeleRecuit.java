package netoptimiz.recuit;

import netoptimiz.Application;
import netoptimiz.graphe.Arc;
import netoptimiz.graphe.Graphe;

public abstract class ModeleRecuit {

    private double temperature;
    private int decroissanceTemp;
    private int iterationsInternes;
    private int nombrePalliers;
    private int nbiteration=0;
    private double coutinitial;
    private double cout;
    private Arc monArc;
    private Graphe monGraphe;

    public ModeleRecuit () {

    }

    public double resoudre () {
        monGraphe=Application.getSingleton().getgraphe();
        //faire un new recuit
        // On déroule l'algo tant que la température est positive
        while (this.getTemperature()>0) {
            // Calcul du cout initial
            coutinitial=calculerCout(monGraphe);
            //Pour le mouvement
            faireMvt (monGraphe, monArc, coutinitial);
            // On vérifie que la transformation est valide
            // Si ce n'est pas le cas on revient en arrière et on refait un mvt
            while (accepterMVT(monGraphe, monArc)==false) {
                if (monArc.getCapacite()==0) monArc.setCapacite(1);
                else monArc.setCapacite(0);
                faireMvt (monGraphe, monArc, coutinitial);
            }
            // On incrémente le nombre d'itérations
            nbiteration++;
            // On compte le nombre d'itération pour savoir si on doit ensuite baisser la température
            if (nbiteration==this.getIterationsInternes()) {
                this.setTemperature(this.getTemperature()-this.getDecroissanceTemp());
                // On réinitialise le nombre d'itérations
                nbiteration=0;
            }
        }
        return cout;
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

    public int getNombrePalliers () {
        return nombrePalliers;
    }

    public void setNombrePalliers (int val) {
        this.nombrePalliers = val;
    }

    public abstract void faireMvt (Graphe g, Arc a, double c);

    public abstract boolean accepterMVT (Graphe g, Arc a);

    public abstract double calculerCout (Graphe g);

    public abstract double deltaCout (double c);

    public void tempInitiale () {
    }

    public abstract void refuserMvt ();

}

