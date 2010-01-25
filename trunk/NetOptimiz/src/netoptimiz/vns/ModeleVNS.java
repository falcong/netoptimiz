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

    public abstract double resoudre (int nombrePalliers, int iterationsInternes);
     /*   double coutMvt;
        bestVns.faireMvt();
        for(int i=0;i<kmax;i++)
        {
        this.valideMvt();
        this.calculerCout();
        if(coutMvt=this.deltaCout()< 0)
        { 
            bestVns = this;
        this.faireMvt();}
        else{
         this.retourMvt();
         this.faireMvt();
        }
        }

    }*/
    public abstract boolean accepterMVT (Graphe g);
    public abstract void faireMvt (Graphe g);
    //public abstract void retourMvt(Graphe g);
    //public abstract void valideMvt (Graphe g);
    public abstract double calculerCout (Graphe g);
    //public abstract double deltaCout ();

}

