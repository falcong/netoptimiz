package netoptimiz.graphe;

import java.util.ArrayList;

public class Arc {

    private static ArrayList<Arc> arcs = new ArrayList<Arc>();

    private double cout;

    private double capacite;

    private Noeud noeudOrigine;

    private Noeud noeudExtremite;

    public Arc (Noeud Orig,Noeud Extr,double c,double mu) {
        noeudOrigine = Orig;
        noeudExtremite = Extr;
        cout = c;
        capacite = mu;
        arcs.add(this);
    }

    public static void displayArcs() {
        for (Arc a : arcs) {
            System.out.println("Arc : " + a.getNoeudOrigine().getNom() + " -> " + a.getNoeudExtremite().getNom());
        }
    }

    public static ArrayList<Arc> getarcs () {
        return arcs;
    }

    public double getCapacite () {
        return capacite;
    }

    public void setCapacite (double val) {
        this.capacite = val;
    }

    public double getCout () {
        return cout;
    }

    public void setCout (double val) {
        this.cout = val;
    }

    public Noeud getNoeudExtremite () {
        return noeudExtremite;
    }

    public void setNoeudExtremite (Noeud val) {
        this.noeudExtremite = val;
    }

    public Noeud getNoeudOrigine () {
        return noeudOrigine;
    }

    public void setNoeudOrigine (Noeud val) {
        this.noeudOrigine = val;
    }

}

