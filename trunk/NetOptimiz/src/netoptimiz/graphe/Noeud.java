package netoptimiz.graphe;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;
import java.util.ArrayList;

public class Noeud {

    private static ArrayList<Noeud> noeuds = new ArrayList<Noeud>();

    private double abs;

    private double ord;

    private String nom;

    public Noeud (String Name,double x,double y) {
        nom = Name;
        abs = x;
        ord = y;
        noeuds.add(this);
    }

    public static Noeud getNoeud(String searchName) {
        for (Noeud node : noeuds) {
            if (node.getNom().equals(searchName)) {
                return node;
            }
        }
        return null;
    }

    public static void displayNoeuds() {
        for (Noeud node : noeuds) {
            System.out.println("Noeud : " + node.getNom());
        }
    }

    public static ArrayList<Noeud> getnoeuds () {
        return noeuds;
    }
    
    public double getAbs () {
        return abs;
    }

    public void setAbs (double val) {
        this.abs = val;
    }

    public String getNom () {
        return nom;
    }

    public void setNom (String val) {
        this.nom = val;
    }

    public double getOrd () {
        return ord;
    }

    public void setOrd (double val) {
        this.ord = val;
    }

    @Override
    public String toString() {
        return nom;
    }



}

