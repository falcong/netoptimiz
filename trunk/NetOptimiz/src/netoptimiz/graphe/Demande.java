package netoptimiz.graphe;

import java.util.ArrayList;

public class Demande {

    private static ArrayList<Demande> demandes = new ArrayList<Demande>();

    private double flux;

    private Noeud noeudOrigine;

    private Noeud noeudExtremite;

    public Demande (Noeud Origine,Noeud Destination, double f) {
        noeudOrigine = Origine;
        noeudExtremite = Destination;
        flux = f;
        demandes.add(this);
    }

    public static void displayDemandes() {
        for (Demande d : demandes) {
            System.out.println("Demande : " + d.getNoeudOrigine().getNom() + " -> " + d.getNoeudExtremite().getNom());
        }
    }

    public static ArrayList<Demande> getdemandes () {
        return demandes;
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

