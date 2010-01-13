package netoptimiz.graphe;

import java.util.ArrayList; 

public class Graphe {

    private ArrayList<Noeud> noeuds;

    private ArrayList<Arc> arcs;

    private ArrayList<Demande> demandes;

    public Graphe () {
    }

    public ArrayList<Arc> getarcs () {
        return arcs;
    }

    public void setarcs (ArrayList<Arc> val) {
        this.arcs = val;
    }

    public ArrayList<Demande> getdemandes () {
        return demandes;
    }

    public void setdemandes (ArrayList<Demande> val) {
        this.demandes = val;
    }

    public ArrayList<Noeud> getnoeuds () {
        return noeuds;
    }

    public void setnoeuds (ArrayList<Noeud> val) {
        this.noeuds = val;
    }

}

