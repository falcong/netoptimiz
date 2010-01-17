package netoptimiz.recuit;

import netoptimiz.Application;
import netoptimiz.graphe.Arc;
import netoptimiz.graphe.Noeud;
import netoptimiz.graphe.Graphe;

public class TelecomRecuit extends ModeleRecuit {

    private double coutinitial;
    private double cout;
    private double deltacout;
    private Arc monArc;
    private int nbiteration=0;
    protected Graphe monGraphe;


    public TelecomRecuit () {
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
            nbiteration++;
            // On compte le nombre d'itération pour savoir si on doit ensuite baisser la température
            if (nbiteration==this.getIterationsInternes()) {
                this.setTemperature(this.getTemperature()-this.getDecroissanceTemp());
                // On réinitialise le nombre d'itérations
                nbiteration=0;
            }
        }
    }

    public void faireMvt (Graphe g) {
        // On récupère le nombre d'arcs du graphe
        int taille=g.getarcs().size();
        // On prend au hasard un des arcs et on change sa capacité
        monArc = g.getarcs().get((int)(Math.random()*taille));
        if (monArc.getCapacite()==0) monArc.setCapacite(1);
        else monArc.setCapacite(0);
    }

    public boolean accepterMVT (Graphe g) {
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
        return true;
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

    public double deltaCout () {
        deltacout=cout-coutinitial;
        return deltacout;
    }

    // devenu inutile après avoir utilisé un boolen pour accepterMvt()
    public void refuserMvt () {
    }

}

