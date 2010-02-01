package netoptimiz.vns;

import netoptimiz.graphe.Graphe;

public abstract class ModeleVNS {

    private int kmax;
    private static int bestVns;
    private int iterationsInternes;

    public ModeleVNS () {
        kmax = 0;
        iterationsInternes = 0;
    }


    public ModeleVNS (int km , int iI) {
        kmax = km;
        iterationsInternes = iI;
    }


    public int getIterationsInternes () {
        return iterationsInternes;
    }

    public void setIterationsInternes (int val) {
        this.iterationsInternes = val;
    }

    public int getKmax () {
        return kmax;
    }

    public void setKmax (int val) {
        this.kmax = val;
    }

    public abstract double resoudre (int K,int I);
    public abstract boolean accepterMVT (Graphe g);
    public abstract void faireMvt (Graphe g);
    public abstract void faireMvtRt(Graphe g);
    //public abstract void valideMvt (Graphe g);
    public abstract double calculerCout (Graphe g);
    //public abstract double deltaCout ();

}