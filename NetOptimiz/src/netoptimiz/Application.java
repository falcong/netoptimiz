package netoptimiz;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import java.util.List;
import netoptimiz.graphe.Arc;
import netoptimiz.graphe.Graphe;
import netoptimiz.graphe.Noeud;

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
      public void TrouverCheminPlusCourt(UndirectedSparseMultigraph<Noeud, Arc> gJung, Noeud n1, Noeud n2) {
        // On appelle le Dijkstra pour ces 2 noeuds
        DijkstraShortestPath<Noeud, Arc> alg = new DijkstraShortestPath(gJung);
        List<Arc> chemin = alg.getPath(n1, n2);
      }
}

