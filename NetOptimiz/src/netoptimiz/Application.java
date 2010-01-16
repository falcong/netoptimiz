package netoptimiz;

import netoptimiz.graphe.Graphe;

public class Application {

    private static final Application instanceApplication = new Application();

    private Optimisation optimisation;

    private Graphe graphe;

    public Application () {
        optimisation = null;
        graphe = Graphe.getSingleton();
    }

    public static Application getSingleton() {
        return instanceApplication;
    }

    public Graphe getgraphe () {
        return graphe;
    }

    public void setgraphe (Graphe val) {
        this.graphe = val;
    }

    public Optimisation getoptimisation () {
        return optimisation;
    }

    public void setoptimisation (Optimisation val) {
        this.optimisation = val;
    }

    public void chargerDonnees () {
    }

    public int verifierReseau () {
        return 0;
    }

    public void optimiser () {
    }

}

