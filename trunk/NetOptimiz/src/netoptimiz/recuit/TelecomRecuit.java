package netoptimiz.recuit;

import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import netoptimiz.Application;
import netoptimiz.graphe.Arc;
import netoptimiz.graphe.Graphe;
import netoptimiz.graphe.Noeud;
import netoptimiz.NetOptimizApp;

import java.util.Date;
import netoptimiz.Methode;


public class TelecomRecuit extends ModeleRecuit {

    private double cout;
    private double deltaCout;
    private double capaciteInitiale;
    private Arc monArc;
    protected Graphe monGraphe;
    // pour savoir si on a ajouté ou supprimé une capacité
    private boolean suppression;
    // Variables nécessaires à déterminer la température initiale
    private double transGenerees=0;
    private double transAcceptees=0;
    // Variables nécessaires à déterminer la température initiale
    private double afficheTransGenerees=0;
    private double afficheTransAcceptees=0;


    public TelecomRecuit () {
        // On crée une copie du graphe original
        monGraphe = Application.getSingleton().getgrapheOriginal().clone();
    }

    // Permet d'afficher des infos dans l'interface
        // options d'affichage :
        // console
        // principal
        // température
        // itérations
    public void afficherInfos(String type,String s) {
        if (!type.equals("graphe")) {
            NetOptimizApp.getApplication().getVuePrincipale().refresh(type, s);
        } else {
            NetOptimizApp.getApplication().getVuePrincipale().drawGraph(monGraphe, Methode.Recuit);
        }
    }

    public double resoudre (int nombrePalliers, int iterationsInternes) {
        // Afin de calculer le temps de résolution
        Date maDateDebut = new Date();
        // On récupère le premier arc afin d'avoir la capacité initiale
        capaciteInitiale = monGraphe.getarcs().get(0).getCapacite();
        // Initialisation du nombre de paliers de température
        this.setNombrePalliers(nombrePalliers);
        // Initialisation du nombre d'itérations internes
        this.setIterationsInternes(iterationsInternes);
        // Calcule et affecte la température initiale
        tempInitiale(monGraphe);
		// On remet à zéro car le calcul de la température initiale a modifié ces variables
        afficheTransGenerees=0;
        afficheTransAcceptees=0;
        // Calcul du cout du graphe
        cout=calculerCout(monGraphe);
        // Affiche des informations dans l'interface
        this.afficherInfos("principal","Température initiale = " + this.getTemperature());
        this.afficherInfos("température",""+(double)Math.round(this.getTemperature()*100)/100);
        this.afficherInfos("principal","****************************************");
        int mvtAccepte;
        // On déroule l'algo tant que l'état gelé n'est pas atteint (nombre de paliers atteint)
        for (int nbPalliers = 1; nbPalliers <= this.getNombrePalliers(); nbPalliers++) {
            // Affichage d'informations
            this.afficherInfos("principal","Palier "+nbPalliers);
            this.afficherInfos("principal","Température au départ du palier = "+(double)Math.round(this.getTemperature()*100)/100);
            // boucle pour les itérations par température
            for (int nbIterationsEnCours = 1; nbIterationsEnCours <= this.getIterationsInternes(); nbIterationsEnCours++) {
                this.afficherInfos("itérations",String.valueOf(nbIterationsEnCours));
                // On fait un mouvement
                faireMvt(monGraphe);
                // Si le mouvement n'est pas accepté on fait le mouvement inverse pour se remettre à l'état précédent
                if (accepterMVT(monGraphe)==false) {
                    if (monArc.getCapacite()==0) monArc.setCapacite(capaciteInitiale);
                    else monArc.setCapacite(0);
                    mvtAccepte=0;
                }
                else {
                    cout=calculerCout(monGraphe);
                    mvtAccepte=1;
                }
                if (nbIterationsEnCours<=10 || nbIterationsEnCours>=this.getIterationsInternes()-10) {
                    this.afficherInfos("principal","   |Itération "+nbIterationsEnCours);
                    this.afficherInfos("principal","   |Fonction objectif = "+cout);
                    this.afficherInfos("principal","   |Statut mouvement = "+mvtAccepte);
                    this.afficherInfos("principal","   ---------------------------------");
                }
            }
            // Les itérations ont été faites => On baisse la température
            this.setTemperature(this.getTemperature() * this.getDecroissanceTemp());
            // On affiche la température courante sur l'interface (2 chiffres après la virgule)
            this.afficherInfos("température",""+(double)Math.round(this.getTemperature()*100)/100);
            //this.afficherInfos("principal","Transformations couteuses générées = "+afficheTransGenerees);
            //this.afficherInfos("principal","Transformations couteuses acceptées = "+afficheTransAcceptees);
            this.afficherInfos("principal","Transformations couteuses acceptées = "+(double)Math.round((afficheTransAcceptees/afficheTransGenerees*100)*100)/100+" %");
            this.afficherInfos("principal","Fonction objectif = "+cout);
            this.afficherInfos("principal","********************");
            afficheTransGenerees=0;
            afficheTransAcceptees=0;
        } // Fin du recuit
        // Afin de connaître le nombre d'arcs ayant une capacité pour la solution
        int nbArcsSolution=0;
        // On affiche la liste des arcs ayant une capacité
        this.afficherInfos("principal","Liste des arcs:");
        for (Arc a : monGraphe.getarcs()) {
            if (a.getCapacite()>0) {
                this.afficherInfos("principal",a.getNoeudOrigine().getNom()+"-"+a.getNoeudExtremite().getNom()+" Cout:"+a.getCout());
                nbArcsSolution++;
            }
        }
        // Calcul du temps de résolution
        Date maDateFin = new Date();
        double duree=maDateFin.getTime()-maDateDebut.getTime();
		cout=calculerCout(monGraphe);
		// Affichage des résultats
        this.afficherInfos("principal","****************************************");
        this.afficherInfos("principal","Total transformations couteuses générées = "+transGenerees);
        this.afficherInfos("principal","Total transformations couteuses acceptées = "+transAcceptees);
        this.afficherInfos("principal","Nombre d'arcs = " + nbArcsSolution);
        this.afficherInfos("principal","Temps de résolution = " + duree/1000 + " secondes");
        this.afficherInfos("principal","Solution = " + cout);
        // Affichage du graphique
        this.afficherInfos("graphe", "");
        // Déclenche le garbage collector
        System.gc();
        // On retourne la solution
        return cout;
    }

    // Calcul de la température initiale selon Kirkpatrick
    public void tempInitiale (Graphe g) {
        // On travaille sur un clone du graphe
        Graphe tempGrapheTinit = g.clone();
        cout=calculerCout(g);
        double tauxAcceptation;
        // Définition d'une température de départ: fixée à la moyenne des couts des arcs
        double coutMoyen=0;
        for (Arc a : g.getarcs()) {
            coutMoyen=coutMoyen+a.getCout();
        }
        coutMoyen=coutMoyen/g.getarcs().size();
        this.setTemperature(coutMoyen);
        // boucle pour déterminer la température initiale
        do {
            // On défini le nombre de transformations couteuses à évaluer
            //  On a déterminé que ce nombre serait égal au nombre d'arcs du graphe original multiplié par 10
            double nbTransCouteuses=10*tempGrapheTinit.getarcs().size();
            int nbPalliers = 1;
            while((transGenerees<nbTransCouteuses) && (nbPalliers <= this.getNombrePalliers())) {
                // boucle pour les itérations par température qui s'arrête soit pas la fin du recuit soit par le nombre de
                //  transformation couteuses définies atteintes
                for (int nbIterationsEnCours = 1; nbIterationsEnCours <= this.getIterationsInternes(); nbIterationsEnCours++) {
                    this.afficherInfos("itérations",String.valueOf(nbIterationsEnCours));
                    // On fait un mouvement
                    faireMvt(tempGrapheTinit);
                    // Si le mouvement n'est pas accepté on fait le mouvement inverse pour se remettre à l'état précédent
                    if (accepterMVT(tempGrapheTinit)==false) {
                        if (monArc.getCapacite()==0) monArc.setCapacite(capaciteInitiale);
                        else monArc.setCapacite(0);
                    }
                    else cout=calculerCout(tempGrapheTinit);
                }
                nbPalliers++;
            } // On a atteint le nombre de tranformations couteuses définies ou bien c'est la fin du recuit

            tauxAcceptation = transAcceptees/transGenerees;
            // Dans le cas où le while serait vérifié on double la température et on recommence
            this.setTemperature(getTemperature()*2);
            transAcceptees=0;
            transGenerees=0;
        }
        while (tauxAcceptation<0.80); // objectif à atteindre : taux acceptation doit être au moins égal à 80%
    }

    public void faireMvt (Graphe g) {
        // On récupère le nombre d'arcs du graphe
        int taille=g.getarcs().size();
        // On prend au hasard un des arcs et on change sa capacité
        monArc = g.getarcs().get((int)(Math.random()*taille));
        //System.out.print("Arc a modifier:" + monArc.getNoeudOrigine().getNom() + monArc.getNoeudExtremite().getNom()+"\n");
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
        // On clone le grpahe pour ne pas travailler dessus directement pour l'acceptation ou non du mouvement
        Graphe tempGraphe = g.clone();
        // On commence par le calcul du deltaCout et de la probabilité d'acceptation car c'est moins lourd à
        //  calculer que de vérifier que chaque noeud est bien relié au réseau

        // on calcule le cout du nouveau graphe et on fait le delta
        deltaCout = deltaCout(tempGraphe);
        // si deltaCout>0 alors on calcule la probabilité qu'il soit intérressant d'explorer ce système
        if (deltaCout>0) {
            double exp=Math.exp(-deltaCout/getTemperature());
            double p = Math.random();
            transGenerees++;
            afficheTransGenerees++;
            if (p>exp) return false;
            else {
                transAcceptees++;
                afficheTransAcceptees++;
            }
        }

        //*** En cas d'ajout de capacité il est inutile de faire les tests suivants ***//
        if (suppression) {
        // 1) On  cherche à vérifier que chaque Noeud à au moins un arc de capacité!=0
            // Devenu presque "inutile" après le test des demandes sauf qu'il est intéressant de le faire pour
            //  gagner du temps de traitement car pas très couteux et peut éviter le calcul lourd des demandes
            boolean flagNoeudOrphelin=true;
            for (Noeud n : g.getnoeuds()) {
                for (Arc a : g.getarcs()) {
                    // On regarde si la capacité de l'arc est !=0 et on regarde si son noeud origine ou extremité correspond au noeud courant
                    if (a.getCapacite()!=0 && (a.getNoeudOrigine().equals(n) || a.getNoeudExtremite().equals(n))) {
                        flagNoeudOrphelin=false;
                        break;
                    }
                }
            }
            // si le noeud a aucun arc de capacité!=0 alors le mouvement n'est pas accepté
            if (flagNoeudOrphelin) { //System.out.print("***** Refusé *****\n");
                return false;
            }
            
        /*
        // 2) vérification que le réseau n'a pas été coupé en 2 sous-réseaux
        // pour cela on vérifie que les 2 noeuds concernés peuvent se joindre via d'autres arcs
        //**** Cette vérification a été abandonnée car n'apporte rien en temps de traitement et le test des
        //      demandes vérifie cet aspect ****
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
                // On ne crée que les arcs qui ont une capacité
                if (a.getCapacite()!=0) gJung.addEdge(a,a.getNoeudOrigine(), a.getNoeudExtremite());
            }
            // On alimente les noeuds à notre graphe
            for (Noeud n : tempGraphe.getnoeuds()) {
              gJung.addVertex(n);
            }
            // On appelle la fonction de vérification des demandes

            if (!Application.getSingleton().verifierDemandes(gJung)) {
                //System.out.print("***** Refusé *****\n");
                return false;
            }
        }
        //*******************//
        // Si tout est vérifié
        cout = calculerCout(tempGraphe);
        //System.out.print("***** Accepté *****\n");
        return true;
    }

    // pour déterminer le cout du graphe
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

    public double deltaCout (Graphe tempGraphe) {
        deltaCout=calculerCout(tempGraphe)-cout;
        return deltaCout;
    }

    // devenu inutile après avoir utilisé un boolen pour accepterMvt()
    public void refuserMvt () {
    }

}

