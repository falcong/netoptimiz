package netoptimiz.vns;

import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import netoptimiz.Application;
import netoptimiz.graphe.Arc;
import netoptimiz.graphe.Graphe;
import netoptimiz.graphe.Noeud;
import netoptimiz.NetOptimizApp;

/*import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.Dimension;
import javax.swing.JFrame;
import netoptimiz.Methode;
 */
public class TelecomVNS extends ModeleVNS {

    protected Graphe monGraphe;
    protected Graphe tempRet;
    private double coutInitial;
    private double capaciteInitiale;
    //private double InitCapacite;
    protected int nbArcsSolution = 0;
    //private double cout;
    private Arc monArc;
    boolean suppression;
    private double cout;


    // Permet d'afficher des infos dans l'interface
    public void afficherInfos(String type, String s) {
        // options d'affichage :
        // console
        // principal
        // kmax
        // itérations
        NetOptimizApp.getApplication().getVuePrincipale().refresh(type, s);
    }

    public void faireMvt(Graphe g) {
        System.out.println("on fait un mvt");
        tempRet = g.clone();
        // On récupère le nombre d'arcs du graphe
        int taille = g.getarcs().size();
        // On prend au hasard un des arcs et on change sa capacité
        // modif fab : il ne faut pas utiliser g mais tempRet
        monArc = g.getarcs().get((int) (Math.random() * taille));

        // on sauvegarde pour la fonction de retour
        if (monArc.getCapacite() == 0) {
            monArc.setCapacite(capaciteInitiale); //***modif fab : capacite initiale
        } else {
            monArc.setCapacite(0);
        }
    }

    public void faireMvtRt(Graphe g) {
        System.out.println("on fait un retour");
        g = tempRet;
    }

    public double calculerCout(Graphe g) {
        double coutTransformation = 0.0;
        for (Arc a : g.getarcs()) {
            // Si la capacité=1 alors on ajoute le cout de l'arc
            if (a.getCapacite() != 0) {
                coutTransformation = coutTransformation + a.getCout();

            }
        }
        return coutTransformation;
    }

    public double resoudre(int K, int I) {

        this.setIterationsInternes(I);
        this.setKmax(K);
        monGraphe = Application.getSingleton().getgrapheOriginal();


        coutInitial = this.calculerCout(monGraphe);
        capaciteInitiale = monGraphe.getarcs().get(0).getCapacite();

        int c = 1;

        while (c <= K) {
            for (int n = 1; n <= I; n++) {
                // On initialiser suppression a false par defaut
                suppression = false;


                for (int j = 1; j <= c; j++) // ***modif fab : j<=c pas à K car c'est le nombre opt en cours
                {
                    faireMvt(monGraphe);
                    //  puis on fait le delta cout pour savoir si la solution est meilleure
                    //  si oui, alors on verifie l'intégrité du graphe et des demandes
                    cout = calculerCout(monGraphe);
                    System.out.println(cout);
                    System.out.println(coutInitial);
                    if (coutInitial > cout) {

                        if (!accepterMVT(monGraphe)) {
                            faireMvtRt(monGraphe);
                        } else {
                            coutInitial = cout; // il faut mettre à jour le cout initial
                        }
                    }
                }
                c++;
            }
            System.out.print(nbArcsSolution);

            System.out.println("Cout final : " + cout + " Nombre d'arcs : " + nbArcsSolution);
            this.afficherInfos("principal", "Nombre d'arcs = " + nbArcsSolution);
            System.out.println("Nb arcs : " + nbArcsSolution);
            // Dessine le graph

            return cout;
        }
        return 0.0;
    }

    /*    public double deltaCout () {
    return 0.0;
    }
     */
    public boolean accepterMVT(Graphe g) {
        int nbArcsSolutiont;
        nbArcsSolutiont = 0;
        //  Graphe tempGraphe = g.clone();
        UndirectedSparseMultigraph<Noeud, Arc> gJung = new UndirectedSparseMultigraph<Noeud, Arc>();
        //*** En cas d'ajout de capacité il est inutile de faire les 3 tests suivants ***//
        // 1) On  cherche à vérifier que chaque Noeud à au moins un arc de capacité!=0

        for (Noeud n : g.getnoeuds()) {
            for (Arc a : g.getarcs()) {

                // On regarde si la capacité de l'arc=1 et on regarde si son noeud origine ou extremité correspond au noeud courant
                if (a.getCapacite() != 0 && (a.getNoeudOrigine().equals(n) || a.getNoeudExtremite().equals(n))) {

                    // On appelle la fonction de vérification des demandes
                    // On crée un graphe Jung (non orienté)

                    // On l'alimente pas les arcs de notre graphe
                    for (Arc b : g.getarcs()) {

                        // On ne crée que les arcs qui ont une capacité
                        if (b.getCapacite() != 0) {
                            gJung.addEdge(b, b.getNoeudOrigine(), b.getNoeudExtremite());
                        }
                    }
                    // On alimente les noeuds à notre graphe
                    for (Noeud m : g.getnoeuds()) {
                        gJung.addVertex(m);

                    }

                }


                // On appelle la fonction de vérification des demandes

                if (!Application.getSingleton().verifierDemandes(gJung)) {
                    System.out.print("***** Refusé *****\n");

                    faireMvtRt(monGraphe);
                }


            }
        }
        // On affiche la liste des arcs ayant une capacité
        this.afficherInfos("principal", "Liste des arcs:");
        for (Arc a : g.getarcs()) {

            if (a.getCapacite() > 0) {
                this.afficherInfos("principal", a.getNoeudOrigine().getNom() + "-" + a.getNoeudExtremite().getNom() + " Cout:" + a.getCout());
                nbArcsSolutiont++;
            }
        }

        System.out.println(nbArcsSolutiont);

        // Si tout est vérifié

        nbArcsSolution = nbArcsSolutiont;
//   NetOptimizApp.getApplication().getView().drawGraph(g, Methode.Recuit);
        return true;
    }
}
