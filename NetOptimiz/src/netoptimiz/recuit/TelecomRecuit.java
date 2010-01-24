package netoptimiz.recuit;

import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import netoptimiz.Application;
import netoptimiz.graphe.Arc;
import netoptimiz.graphe.Graphe;
import netoptimiz.graphe.Noeud;
import java.util.List;
import netoptimiz.NetOptimizApp;

public class TelecomRecuit extends ModeleRecuit {

    private double coutInitial;
    private double cout;
    private double deltaCout;
    private double capaciteInitiale;
    private double kBoltzman = 1.3806 * Math.pow(10,-23);
    private Arc monArc;
    // pour savoir si on a ajouté ou supprimé une capacité
    private boolean suppression;
    protected Graphe monGraphe;

    public TelecomRecuit () {
        monGraphe = Application.getSingleton().getgrapheOriginal().clone();
    }

    public void afficherInfos(String s) {
        NetOptimizApp.getApplication().getView().refresh(s);
    }

    public double resoudre (int nombrePalliers, int iterationsInternes) {
        // On récupère le graphe
        monArc = monGraphe.getarcs().get(0);
        // Initialisation du nombre de paliers de température
        this.setNombrePalliers(nombrePalliers);
        // Initialisation du nombre d'itérations internes
        this.setIterationsInternes(iterationsInternes);
        // Calcule et affecte la température initiale
        //double tempInitiale=tempInitiale(monGraphe);
        double tempInitiale=100;
        this.setTemperature(tempInitiale);
        // On récupère la valeur de la capacité initiale
        capaciteInitiale = monArc.getCapacite();
        // On déroule l'algo tant que l'état gelé n'est pas atteint (nombre de paliers atteint)
        for (int nbPalliers = 1; nbPalliers <= this.getNombrePalliers(); nbPalliers++) {
            // Calcul du cout initial OU de la dernière acceptation
            coutInitial=calculerCout(monGraphe);
            // boucle pour les itérations par température
            for (int nbIterationsEnCours = 1; nbIterationsEnCours <= this.getIterationsInternes(); nbIterationsEnCours++) {
                faireMvt(monGraphe);
                if (accepterMVT(monGraphe)==false) {
                    if (monArc.getCapacite()==0) monArc.setCapacite(capaciteInitiale);
                    else monArc.setCapacite(0);
                }
                else {
                }
            }
            // On baisse la température
            this.setTemperature(this.getTemperature() * this.getDecroissanceTemp());
            // On décrémente le palier
            this.setNombrePalliers(nombrePalliers - 1);
        }
        return calculerCout(monGraphe);
    }

    public void faireMvt (Graphe g) {
        // On récupère le nombre d'arcs du graphe
        int taille=g.getarcs().size();
        double capa = monArc.getCapacite();
        // On prend au hasard un des arcs et on change sa capacité
        monArc = g.getarcs().get((int)(Math.random()*taille));
        if (monArc.getCapacite()==0) {
            monArc.setCapacite(capaciteInitiale);
            suppression=false;
        }
        else {
            monArc.setCapacite(0);
            suppression=true;
        }
        this.afficherInfos("mvt : " + monArc.getNoeudOrigine().getNom()
                            + " -> " + monArc.getNoeudExtremite().getNom()
                            + " : " + capa
                            + " devient " + monArc.getCapacite());
    }

    public boolean accepterMVT (Graphe g) {
        Graphe tempGraphe = g.clone();
        // On commence par le calcul du deltaCout et de la probabilité d'acceptation car c'est moins lourd à
        //  calculer que de vérifier que chaque noeud est bien relié au réseau

        // on calcule le cout du nouveau graphe
        deltaCout = calculerCout(tempGraphe) - coutInitial;
        // si deltaCout>0 alors on calcule la probabilité qu'il soit intérressant d'explorer ce système
        if (deltaCout>0) {
            //double exp=Math.exp(-deltaCout/(kBoltzman * this.getTemperature()));
            double exp=Math.exp(-deltaCout/(100));
            double p = Math.random();
            if (p>exp) return false;
        }

        //*** En cas d'ajout de capacité il est inutile de faire les tests suivants ***//

        if (suppression) {
        // 1) On  cherche à vérifier que chaque Noeud à au moins un arc de capacité!=0
            // Devenu inutile après le test des demandes
            /*for (Noeud n : g.getnoeuds()) {
                for (Arc a : g.getarcs()) {
                    // On regarde si la capacité de l'arc est !=0 et on regarde si son noeud origine ou extremité correspond au noeud courant
                    if (a.getCapacite()!=0 && (a.getNoeudOrigine().equals(n) || a.getNoeudExtremite().equals(n))) {
                        break;
                    }
                    // si le noeud a aucun arc de capacité!=0 alors le mouvement n'est pas accepté
                    else return false;
                }
            }

        // 2) vérification que le réseau n'a pas été coupé en 2 sous-réseaux
        // pour cela on vérifie que les 2 noeuds concernés peuvent se joindre via d'autres arcs
            // On récupère les noeuds extrémité et origine
            Noeud n1=monArc.getNoeudOrigine();
            Noeud n2=monArc.getNoeudExtremite();
            // On crée un graphe Jung (non orienté)
            UndirectedSparseMultigraph<Noeud, Arc> gJung = new UndirectedSparseMultigraph<Noeud, Arc>();
            // On l'alimente pas les arcs de notre graphe
            for (Arc a : Graphe.getSingleton().getarcs()) {
              gJung.addEdge(a,a.getNoeudOrigine(), a.getNoeudExtremite());
            }
            // On regarde si les noeuds sont toujours reliés grace au Dijkstra
            List<Arc> chemin = Application.getSingleton().TrouverCheminPlusCourt(gJung, n1, n2);
            // Si la liste de retour est vide, c'est que le réseau est coupé en 2 sous réseaux => non valide
            if (chemin.isEmpty()) return false;
            */
        // 3) On vérifie que les demandes sont remplies
            // On crée un graphe Jung (non orienté)
            UndirectedSparseMultigraph<Noeud, Arc> gJung = new UndirectedSparseMultigraph<Noeud, Arc>();
            // On l'alimente pas les arcs de notre graphe
            for (Arc a : tempGraphe.getarcs()) {
              gJung.addEdge(a,a.getNoeudOrigine(), a.getNoeudExtremite());
            }
            // On alimente les noeuds à notre graphe
            for (Noeud n : tempGraphe.getnoeuds()) {
              gJung.addVertex(n);
            }
            // On appelle la fonction de vérification des demandes

            if (!Application.getSingleton().verifierDemandes(gJung)) return false;
        }
        //*******************//

        // Si tout est vérifié
        return true;
    }

    public double calculerCout (Graphe g) {
        double coutTransformation=0;
        for (Arc a : g.getarcs()) {
            // Si la capacité!=0 alors on ajoute le cout de l'arc
            if (a.getCapacite()!=0) {
                coutTransformation = coutTransformation + a.getCout();
            }
        }
        return coutTransformation;
    }

    public double deltaCout () {
        deltaCout=cout-coutInitial;
        return deltaCout;
    }

    // devenu inutile après avoir utilisé un boolen pour accepterMvt()
    public void refuserMvt () {
    }

}

