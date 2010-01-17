package netoptimiz.graphe;

import java.util.ArrayList; 

public class Graphe {

    private static final Graphe instanceGraphe = new Graphe();

    public static Graphe getSingleton() {
        return instanceGraphe;
    }

    private ArrayList<Noeud> noeuds;

    private ArrayList<Arc> arcs;

    private ArrayList<Demande> demandes;

    public Graphe () {
        noeuds = new ArrayList<Noeud>();
        arcs = new ArrayList<Arc>();
        demandes = new ArrayList<Demande>();
    }


    public ArrayList<Noeud> getnoeuds () {
        return noeuds;
    }

    public void addNoeud (Noeud n) {
        this.noeuds.add(n);
    }

    public void removeNoeud (Noeud n) {
        this.noeuds.remove(n);
    }

    public ArrayList<Arc> getarcs () {
        return arcs;
    }

    public void addArc (Arc a) {
        this.arcs.add(a);
    }

    public void removeArc (Arc a) {
        this.arcs.remove(a);
    }

    public ArrayList<Demande> getdemandes () {
        return demandes;
    }

    public String toString() {
        return ("Graphe : " + this.noeuds.size() + " noeuds, " + this.arcs.size() + " arcs, " + this.demandes.size() + " demandes");
    }
}

