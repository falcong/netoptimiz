package netoptimiz.graphe;

import java.util.ArrayList; 

public class Graphe {

    private Noeud noeuds;

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

    public Noeud getNoeuds () {
        return noeuds;
    }

    public void setNoeuds (Noeud val) {
        this.noeuds = val;
    }

}

