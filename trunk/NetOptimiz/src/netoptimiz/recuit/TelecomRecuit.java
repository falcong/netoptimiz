package netoptimiz.recuit;

import netoptimiz.graphe.Arc;
import netoptimiz.graphe.Noeud;
import netoptimiz.graphe.Graphe;

public class TelecomRecuit extends ModeleRecuit {

    
    private double cout;
    private double deltacout;

    public TelecomRecuit () {
        
    }

    public double calculerCout (Graphe g) {
        for (Arc a : g.getarcs()) {
            // Si la capacité=1 alors on ajoute le cout de l'arc
            if (a.getCapacite()==1) {
                cout=cout + a.getCapacite();
            }
        }
        return cout;
    }

    public void faireMvt (Graphe monGraphe, Arc monArc, double coutinitial) {
        // On récupère le nombre d'arcs du graphe
        int taille=monGraphe.getarcs().size();
        // On prend au hasard un des arcs et on change sa capacité
        monArc = monGraphe.getarcs().get((int)(Math.random()*taille));
        if (monArc.getCapacite()==0) monArc.setCapacite(1);
        else monArc.setCapacite(0);
    }

    public boolean accepterMVT (Graphe monGraphe, Arc monArc) {
        // On commence par le calcul du deltacout et de la probabilité d'acceptation car c'est moins lourd à
        // calculer que de vérifier que chaque noeud est bien relié au réseau

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
            for (Noeud n : monGraphe.getnoeuds()) {
                for (Arc a : monGraphe.getarcs()) {
                    // On regarde si la capacité de l'arc=1 et on regarde si son noeud origine ou extremité correspond au noeud courant
                    if (a.getCapacite()==1 && (a.getNoeudOrigine().equals(n) || a.getNoeudExtremite().equals(n))) {
                        break;
                    }
                    // si le noeud a aucun arc de capacité 1 alors le mouvement n'est pas accepté
                    else return false;
                }
            }
        }

        // Test Dijkstra

        return true;
    }



    public double deltaCout (double coutinitial) {
        deltacout=cout-coutinitial;
        return deltacout;
    }

    // devenu inutile après avoir utilisé un boolen pour accepterMvt()
    public void refuserMvt () {
    }

}

