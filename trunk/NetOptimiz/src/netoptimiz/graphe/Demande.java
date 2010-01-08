package netoptimiz.graphe;

public class Demande {

    private double flux;

    private Noeud noeudOrigine;

    private Noeud noeudExtremite;

    public Demande () {
    }

    public double getFlux () {
        return flux;
    }

    public void setFlux (double val) {
        this.flux = val;
    }

    public void getChemin () {
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

