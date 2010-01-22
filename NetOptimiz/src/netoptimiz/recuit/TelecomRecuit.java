package netoptimiz.recuit;

import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import netoptimiz.Application;
import netoptimiz.graphe.Arc;
import netoptimiz.graphe.Graphe;
import netoptimiz.graphe.Noeud;
import java.util.List;

public class TelecomRecuit extends ModeleRecuit {

    private double coutInitial;
    private double cout;
    private double deltaCout;
    private int nbIterationsEnCours=0;
    private  double capaciteInitiale;
    private Arc monArc;
    // pour savoir si on a ajouté ou supprimé une capacité
    private boolean suppression;
    protected Graphe monGraphe;

    public TelecomRecuit () {

    }

    public double resoudre (int nombrePalliers, int iterationsInternes) {
        // On récupère le graphe
        monGraphe=Application.getSingleton().getgraphe();
        // Initialisation du nombre de paliers de température
        this.setNombrePalliers(nombrePalliers);
        // Calcule et affecte la température initiale
        double tempInitiale=tempInitiale(monGraphe);
        this.setTemperature(tempInitiale);
        // On récupère la valeur de la capacité initiale
        capaciteInitiale = monArc.getCapacite();
        // On déroule l'algo tant que l'état gelé n'est pas atteint (nombre de paliers atteint)
        while (this.getNombrePalliers()>0) {
            // Calcul du cout initial OU de la dernière acceptation
            coutInitial=calculerCout(monGraphe);
            // boucle pour les itérations par température
            do {
                //Pour le mouvement
                faireMvt (monGraphe);
                // On incrémente le nombre d'itérations
                nbIterationsEnCours++;
                // On vérifie que la transformation n'est pas valide (pas forcément vrai du à la permière itération)
                // Si c'est le cas on revient en arrière et on repart dans la boucle pour refaire un mvt
                if (accepterMVT(monGraphe)==false) {
                    if (monArc.getCapacite()==0) monArc.setCapacite(capaciteInitiale);
                    else monArc.setCapacite(0);
                }
                // On compte le nombre d'itération pour savoir si on doit quitter la boucle
                if (nbIterationsEnCours>=this.getIterationsInternes()) {
                    // On baisse la température
                    this.setTemperature(this.getTemperature()*this.getDecroissanceTemp());
                    // On décrémente le palier
                    this.setNombrePalliers(nombrePalliers-1);
                    // On réinitialise le nombre d'itérations
                    nbIterationsEnCours=0;
                    // On sort du do-while
                    break;
                }
            }
            while (accepterMVT(monGraphe)==false);
        }
        return calculerCout(monGraphe);
    }

    public void faireMvt (Graphe g) {
        // On récupère le nombre d'arcs du graphe
        int taille=g.getarcs().size();
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
    }

    public boolean accepterMVT (Graphe g) {
        // On commence par le calcul du deltaCout et de la probabilité d'acceptation car c'est moins lourd à
        //  calculer que de vérifier que chaque noeud est bien relié au réseau

        // on calcule le cout du nouveau graphe
        deltaCout = calculerCout(monGraphe);
        // si deltaCout>0 alors on calcule la probabilité qu'il soit intérressant d'explorer ce système
        if (deltaCout>0) {
            double exp=Math.exp(-deltaCout/this.getTemperature());
            double p = Math.random();
            if (p>exp) return false;
        }

        //*** En cas d'ajout de capacité il est inutile de faire les 3 tests suivants ***//
        // 1) On  cherche à vérifier que chaque Noeud à au moins un arc de capacité!=0
        if (suppression) {
            for (Noeud n : g.getnoeuds()) {
                for (Arc a : g.getarcs()) {
                    // On regarde si la capacité de l'arc=1 et on regarde si son noeud origine ou extremité correspond au noeud courant
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

        // 3) On vérifie que les demandes sont remplies
            // On alimente les noeuds à notre graphe
            for (Noeud n : Graphe.getSingleton().getnoeuds()) {
              gJung.addVertex(n);
            }
            // On appelle la fonction de vérification des demandes
            Application.getSingleton().verifierDemandes(gJung);
        }
        //*******************//

        // Si tout est vérifié
        return true;
    }

    public double calculerCout (Graphe g) {
        for (Arc a : g.getarcs()) {
            // Si la capacité!=0 alors on ajoute le cout de l'arc
            if (a.getCapacite()!=0) {
                cout = cout + a.getCapacite();
            }
        }
        return cout;
    }

    public double deltaCout () {
        deltaCout=cout-coutInitial;
        return deltaCout;
    }

    // devenu inutile après avoir utilisé un boolen pour accepterMvt()
    public void refuserMvt () {
    }

}

