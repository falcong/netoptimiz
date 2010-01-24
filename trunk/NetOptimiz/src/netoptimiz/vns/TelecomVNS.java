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

  public void faireMvt (Graphe g) {
        // On récupère le nombre d'arcs du graphe
        int taille=g.getarcs().size();
        // On prend au hasard un des arcs et on change sa capacité
        monArc = g.getarcs().get((int)(Math.random()*taille));
        if (monArc.getCapacite()==0) monArc.setCapacite(1);
        else monArc.setCapacite(0);
    }


    public void resoudre () {
         monGraphe = Application.getSingleton().getgrapheOriginal().clone();
         coutInitial=calculerCout(monGraphe);
         int i=0;
    for(int n=0;n<this.getIterationsInternes();n++)
    {
        boolean systemFige;
        faireMvt (monGraphe);
        //on initialise le booleen de controle a true en début de chaque boucle
            systemFige = true;
            // On vérifie que la transformation est valide
            // Si ce n'est pas le cas on revient en arrière et on refait un mvt
            while (accepterMVT(monGraphe)==false && i<this.getKmax()) {
                if (monArc.getCapacite()==0) 
                {
                    // Si on as trouvé une transformation valide on passe systemFige à false
                    monArc.setCapacite(1);
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
        coutInitial = calculCout;
       return true;
   }
   else return false;
   }

}