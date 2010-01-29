package netoptimiz;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import java.util.List;
import netoptimiz.graphe.Arc;
import netoptimiz.graphe.Demande;
import netoptimiz.graphe.Graphe;
import netoptimiz.graphe.Noeud;

public class Application {

    private static final Application instanceApplication = new Application();
    private Optimisation optimisation;
    private Graphe grapheOriginal;

    public Application() {
        optimisation = null;
        grapheOriginal = new Graphe();
    }

    public static Application getSingleton() {
        return instanceApplication;
    }

    public Graphe getgrapheOriginal() {
        return grapheOriginal;
    }

    public void setggrapheOriginal(Graphe val) {
        this.grapheOriginal = val;
    }

    public Optimisation getoptimisation() {
        return optimisation;
    }

    public void setoptimisation(Optimisation val) {
        this.optimisation = val;
    }

    public void chargerDonnees() {
    }

    public int verifierReseau() {
        return 0;
    }

    public void optimiser() {
    }

    // Ajouté par rapport aux méthodes définies à l'origine
    public boolean verifierDemandes(UndirectedSparseMultigraph<Noeud, Arc> gJung) {
        // On parcoure les demandes
        for (Demande d : Demande.getdemandes()) {
            // Flag de vérification de demande satisfaite
            boolean flag=false;
            // On récupère la valeur de la demande
            double flux=d.getFlux();
            // On le fait tant que capaciteBasse>getDemande
            do {
                // Pour chaque demande on fait le Dijkstra
                List<Arc> chemin = this.TrouverCheminPlusCourt(gJung, d.getNoeudOrigine(), d.getNoeudExtremite());
                // Si le chemin est possible
                if (!chemin.isEmpty()) {
                    // On initialise la capacité basse par le premier arc trouvé
                    double capaciteBasse = chemin.get(0).getCapacite();
                    for (Arc aJung : chemin) {
                        // On récupère le minimun des capacités du chemin
                        if (aJung.getCapacite() < capaciteBasse) {
                            capaciteBasse = aJung.getCapacite();
                        }
                    }
                    // On regarde si la capacité suffit à combler la demande
                    // Si oui
                    if ((flux <= capaciteBasse)) {
                        for (Arc aJung : chemin) {
                            // On enlève de la capacité en conséquence sur ces arcs
                            aJung.setCapacite(aJung.getCapacite() - d.getFlux());
                        }
                        flag=true;
                    } 
                    // si non on réduit le flux et on recommence avec la même demande
                    else {
                        flux=flux-capaciteBasse;
                        // Pour chaque arc on va réduire la capacité en conséquence
                        for (Arc aJung : chemin) {
                            aJung.setCapacite(aJung.getCapacite() - capaciteBasse);
                            // Si la capacité est négative ou égale à 0 alors on supprime l'arc
                            if (aJung.getCapacite()<=0) {
                                gJung.removeEdge(aJung);
                            }
                        }
                    }
                }
                // on n'a pas trouvé de chemin pour une demande => non valide
                else return false;
            }
            while (!flag); // On le fait tant que le flag=false
        }
        return true;
    }

    public List<Arc> TrouverCheminPlusCourt(UndirectedSparseMultigraph<Noeud, Arc> gJung, Noeud n1, Noeud n2) {
        // On appelle le Dijkstra pour ces 2 noeuds
        DijkstraShortestPath<Noeud, Arc> alg = new DijkstraShortestPath(gJung);
        List<Arc> chemin = alg.getPath(n1, n2);
        return chemin;
    }
}














