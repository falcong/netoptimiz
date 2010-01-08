package netoptimiz.graphe;

public class Arc {

    private double cout;

    private double capacite;

    private Noeud noeudOrigine;

    private Noeud noeudExtremite;

    public Arc () {
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

