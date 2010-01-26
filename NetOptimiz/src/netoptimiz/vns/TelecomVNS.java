package netoptimiz.vns;
import netoptimiz.Application;
import netoptimiz.graphe.Arc;
import netoptimiz.graphe.Graphe;

public class TelecomVNS extends ModeleVNS {
 protected Graphe monGraphe;
 private double coutInitial;
    private double cout;
    private double deltacout;
    private Arc monArc;
    boolean suppression;

  public void faireMvt (Graphe g) {
        // On récupère le nombre d'arcs du graphe
        int taille=g.getarcs().size();
        // On prend au hasard un des arcs et on change sa capacité
        monArc = g.getarcs().get((int)(Math.random()*taille));
        if (monArc.getCapacite()==0) monArc.setCapacite(1);
        else monArc.setCapacite(0);
    }


    public void resoudre () {
         monGraphe=Application.getSingleton().getgraphe();
         coutInitial=calculerCout(monGraphe);
         int i=0;
    for(int n=0;n<this.getIterationsInternes();n++)
    {
        boolean systemFige;
        faireMvt (monGraphe);
        //on initialise le booleen de controle a true en début de chaque boucle
             systemFige = true;
        // On initialiser suppression a false par defaut
             suppression = false
            // On vérifie que la transformation est valide
            // Si ce n'est pas le cas on revient en arrière et on refait un mvt
            while (accepterMVT(monGraphe)==false && i<this.getKmax()) {
                if (monArc.getCapacite()==0) 
                {
                    // Si on as trouvé une transformation valide on passe systemFige à false
                    monArc.setCapacite(1);
                    suppression = true;
                    systemFige = false;
                    i++;
                }
                else monArc.setCapacite(0);
                faireMvt (monGraphe);
            }
        // Si aucune transformation valide n'est trouvée, le système est considéré comme figé on peut sortir de la boucle
        if(systemFige == true) break;

    }
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
/*    public double deltaCout () {
        return 0.0;
    }
*/

   public boolean accepterMVT(Graphe monGraphe){
    double calculCout;
// il faudra ajouter le calcul d'intégrité du réseau
   calculCout=this.calculerCout(monGraphe);
    if( coutInitial >calculCout )
   {
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

        // On appelle la fonction de vérification des demandes
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
       coutInitial = calculCout;
       return true;
   }
   else return false;
   }

}