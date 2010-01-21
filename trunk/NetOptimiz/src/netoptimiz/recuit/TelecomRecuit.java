package netoptimiz.recuit;

import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import netoptimiz.Application;
import netoptimiz.graphe.Arc;
import netoptimiz.graphe.Graphe;
import netoptimiz.graphe.Noeud;
import java.util.List;

public class TelecomRecuit extends ModeleRecuit {

    private double coutinitial;
    private double cout;
    private double deltacout;
    private int nbIterationsEnCours;
    private Arc monArc;
    // pour savoir si on a ajouté ou supprimé une capacité
    private boolean suppression;
    protected Graphe monGraphe;

    public TelecomRecuit () {

    }

    public double resoudre (int nombrePalliers, int iterationsInternes) {
        // Initialisation du nombre de palliers de température
        this.setNombrePalliers(nombrePalliers);
        // Temporaire : pour définir la température initiale
        this.setTemperature(20);
        // On détermine la valeur de la température à décrémenter
        this.setDecroissanceTemp(this.getTemperature()/this.getNombrePalliers());
        // Afin de savoir où on en est pour les itérations par température
        nbIterationsEnCours=0;
        // On déroule l'algo tant que la température est positive
        while (this.getTemperature()>0) {
            monGraphe=Application.getSingleton().getgraphe();
            // Calcul du cout initial
            coutinitial=calculerCout(monGraphe);
            //Pour le mouvement
            faireMvt (monGraphe);
            // On vérifie que la transformation est valide
            // Si ce n'est pas le cas on revient en arrière et on refait un mvt
            while (accepterMVT(monGraphe)==false) {
                if (monArc.getCapacite()==0) monArc.setCapacite(1);
                else monArc.setCapacite(0);
                faireMvt (monGraphe);
            }
            // On incrémente le nombre d'itérations
            nbIterationsEnCours++;
            // On compte le nombre d'itération pour savoir si on doit ensuite baisser la température
            if (nbIterationsEnCours==this.getIterationsInternes()) {
                // On baisse la température
                this.setTemperature(this.getTemperature()-this.getDecroissanceTemp());
                // On réinitialise le nombre d'itérations
                nbIterationsEnCours=0;
            }
        }
        return cout;
    }

    public void faireMvt (Graphe g) {
        // On récupère le nombre d'arcs du graphe
        int taille=g.getarcs().size();
        // On prend au hasard un des arcs et on change sa capacité
        monArc = g.getarcs().get((int)(Math.random()*taille));
        if (monArc.getCapacite()==0) {
            monArc.setCapacite(1);
            suppression=false;
        }
        else {
            monArc.setCapacite(0);
            suppression=true;
        }
    }

    public boolean accepterMVT (Graphe g) {
        // On commence par le calcul du deltacout et de la probabilité d'acceptation car c'est moins lourd à
        //  calculer que de vérifier que chaque noeud est bien relié au réseau

        // on calcule le cout du nouveau graphe
        deltacout = calculerCout(monGraphe);
        // si deltacout>0 alors on calcule la probabilité qu'il soit intérressant d'explorer ce système
        if (deltacout>0) {
            double exp=Math.exp(-deltacout/this.getTemperature());
            double p = Math.random();
            if (p>exp) return false;
        }

        // On  cherche à vérifier que chaque Noeud à au moins un arc de capacité=1
        // En cas d'ajout de capacité il est inutile de faire ce test. Donc on ne le fait que si l'arc concerné à une capacité nulle
        if (monArc.getCapacite()==0) {
            for (Noeud n : g.getnoeuds()) {
                for (Arc a : g.getarcs()) {
                    // On regarde si la capacité de l'arc=1 et on regarde si son noeud origine ou extremité correspond au noeud courant

                    if (a.getCapacite()==1 && (a.getNoeudOrigine().equals(n) || a.getNoeudExtremite().equals(n))) {
                        break;
                    }
                    // si le noeud a aucun arc de capacité 1 alors le mouvement n'est pas accepté
                    else return false;
                }
            }
        }

        // En cas de suppression de capacité on veut vérifier que le réseau n'est pas coupé en 2 sous-réseaux
        // pour celà on vérifie que les 2 noeuds concernés peuvent se joindre via d'autres arcs
        if (suppression=true) {
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
            // Si la liste de retour st vide, c'est que le réseau est coupé en 2 sous réseauw => non valide
            if (chemin.isEmpty()) return false;
        }
        return true;
    }

    public double calculerCout (Graphe g) {
        for (Arc a : g.getarcs()) {
            // Si la capacité=1 alors on ajoute le cout de l'arc
            if (a.getCapacite()==1) {
                cout = cout + a.getCapacite();
            }
        }
        return cout;
    }

    public double deltaCout () {
        deltacout=cout-coutinitial;
        return deltacout;
    }

    // devenu inutile après avoir utilisé un boolen pour accepterMvt()
    public void refuserMvt () {
    }

}

