package netoptimiz.graphe;

import java.util.ArrayList; 

public class Graphe {

    private ArrayList<Noeud> noeuds;

    public void setArcs(ArrayList<Arc> arcs) {
        this.arcs = arcs;
    }

    public void setDemandes(ArrayList<Demande> demandes) {
        this.demandes = demandes;
    }

    public void setNoeuds(ArrayList<Noeud> noeuds) {
        this.noeuds = noeuds;
    }

    private ArrayList<Arc> arcs;

    private ArrayList<Demande> demandes;

    public Graphe () {
        noeuds = new ArrayList<Noeud>();
        arcs = new ArrayList<Arc>();
        demandes = new ArrayList<Demande>();
    }

    public void reset () {
        this.noeuds.clear();
        this.arcs.clear();
        this.demandes.clear();
    }

    public Graphe clone() {
        Graphe newGraphe = new Graphe();
        newGraphe.getnoeuds().addAll(this.noeuds);
        for (Arc a : this.arcs) {
            newGraphe.getarcs().add(new Arc(a.getNoeudOrigine(),a.getNoeudExtremite(),a.getCout(),a.getCapacite()));
        }
        newGraphe.getdemandes().addAll(this.getdemandes());
        return newGraphe;
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

