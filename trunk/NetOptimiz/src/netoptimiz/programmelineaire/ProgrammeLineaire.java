package netoptimiz.programmelineaire;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVarType;
import ilog.cplex.IloCplex;
import java.util.ArrayList;
import netoptimiz.Application;
import netoptimiz.Methode;
import netoptimiz.NetOptimizApp;
import netoptimiz.graphe.Arc;
import netoptimiz.graphe.Demande;
import netoptimiz.graphe.Graphe;
import netoptimiz.graphe.Noeud;

public class ProgrammeLineaire {

  private ArrayList<Variable> variables = new ArrayList<Variable>();
  private Objectif objectif;
  private ArrayList<Contrainte> contraintes = new ArrayList<Contrainte>();
  IloCplex modele;
  private Graphe graphe;

  public ProgrammeLineaire() throws IloException {
    modele = new IloCplex();
    graphe = Application.getSingleton().getgrapheOriginal().clone();
  }

  public void addVariable(Variable v) {
    variables.add(v);
  }

  public ArrayList<Variable> getVariables() {
    return variables;
  }

  public void addContrainte(Contrainte c) {
    contraintes.add(c);
  }

  public void setVariables(ArrayList<Variable> val) {
    this.variables = val;
  }

  public double resoudre() throws IloException {
    //Graphe graphe = null;// = Application.getSingleton().getgrapheOriginal().clone();
    //construireGraphe();
    if (graphe.getnoeuds().isEmpty()) {
      System.err.println("Le graphe n'est pas encore intitalisé");
    } else {

      createPL(graphe);

      for (Variable v : variables) {
        if (v.getType() == 0) {
          v.setVar(modele.numVar(v.getBorneInf(), v.getBorneSup(), IloNumVarType.Float));
          System.out.print(v.getNomVar() + " ; ");
        } else {
          v.setVar(modele.boolVar());
          System.out.print(v.getNomVar() + " ; ");
        }
      }

      for (Contrainte c : contraintes) {
        IloLinearNumExpr exp = modele.linearNumExpr();
        for (Terme t : c.getTermes()) {
          exp.addTerm(t.getCoef(), t.getV().getVar());
        }
        switch (c.getsigne()) {
          case -1:
            modele.addLe(exp, c.getsecondMembre());
            break;
          case 0:
            modele.addEq(exp, c.getsecondMembre());
            break;
          case 1:
            modele.addGe(exp, c.getsecondMembre());
            break;
        }
      }

      IloLinearNumExpr obj = modele.linearNumExpr();
      for (Terme t : objectif.getTermes()) {
        obj.addTerm(t.getCoef(), t.getV().getVar());
      }
      if (objectif.isMaximiser()) {
        modele.addMaximize(obj);
      } else {
        modele.addMinimize(obj);
      }

      modele.exportModel("c:/lpex1.lp");
      if (modele.solve()) {
        Graphe tempGraphe = new Graphe();
        int arcs = 0;
        for (Variable v : variables) {

          if (v.getType() == 1) {
            if (Math.round(modele.getValue(v.getVar())) == 1) {
              if (!tempGraphe.getnoeuds().contains(v.getArc().getNoeudExtremite())) {
                tempGraphe.addNoeud(v.getArc().getNoeudExtremite());
              }
              if (!tempGraphe.getnoeuds().contains(v.getArc().getNoeudOrigine())) {
                tempGraphe.addNoeud(v.getArc().getNoeudOrigine());
              }
              if (!tempGraphe.getarcs().contains(v.getArc())) {
                tempGraphe.addArc(v.getArc());
                afficherInfos(v.getArc().getNoeudOrigine().getNom() + "-->" + v.getArc().getNoeudExtremite().getNom() + "\n");
                arcs++;
              }
            }

            System.out.println(v.getNomVar() + " = " + modele.getValue(v.getVar()));
          }
        }
        NetOptimizApp.getApplication().getVuePrincipale().drawGraph(tempGraphe, Methode.PL);
        //NetOptimizApp.getApplication().getVuePrincipale().refresh("console", "test");
                //drawGraph(tempGraphe, Methode.PL);

        System.out.println("Objectif = " + modele.getObjValue());
        afficherInfos("Nombre d'arcs = " + arcs + "\n");
        afficherInfos("Objectif = " + Double.toString(modele.getObjValue()));
        
        return modele.getObjValue();
      }

      modele.end();
    }
    return 0.0;
  }

  public void afficherInfos(String s) {
        // options d'affichage :
        // console
        // principal
        // température
        // itérations
        NetOptimizApp.getApplication().getVuePrincipale().refreshPL(s);
    }

  public void construireGraphe() {
    graphe = new Graphe();
    // Les noeud
    Noeud n1 = new Noeud("n1", 10, 10);
    Noeud n2 = new Noeud("n2", 5, 10);
    Noeud n3 = new Noeud("n3", 15, 20);
    Noeud n4 = new Noeud("n3", 13, 2);

    //Les arcs
    Arc a1 = new Arc(n1, n2, 2, 3);
    Arc a2 = new Arc(n1, n3, 4, 3);
    Arc a3 = new Arc(n1, n4, 6, 3);
    Arc a4 = new Arc(n2, n3, 3, 3);
    Arc a5 = new Arc(n2, n4, 6, 3);
    Arc a6 = new Arc(n3, n4, 1, 3);

    //Les demandes
    Demande d1 = new Demande(n1, n2, 3);
    Demande d2 = new Demande(n1, n3, 4);
    Demande d3 = new Demande(n1, n4, 1);
    Demande d4 = new Demande(n2, n3, 3);
    Demande d5 = new Demande(n2, n4, 4);
    Demande d6 = new Demande(n3, n4, 4);


    //Ajout des noeuds au graphe
    graphe.addNoeud(n1);
    graphe.addNoeud(n2);
    graphe.addNoeud(n3);
    graphe.addNoeud(n4);

    //Ajout des arcs au graphe
    graphe.addArc(a1);
    graphe.addArc(a2);
    graphe.addArc(a3);
    graphe.addArc(a4);
    graphe.addArc(a5);
    graphe.addArc(a6);

    //Ajout des demandes au graphe
    graphe.addDemande(d1);
    graphe.addDemande(d2);
    graphe.addDemande(d3);
    graphe.addDemande(d4);
    graphe.addDemande(d5);
    graphe.addDemande(d6);

  //createPL(graphe);
  }

  public void createPL(Graphe g) {
    int totalNoeuds = g.getnoeuds().size();
    int totalArcs = (totalNoeuds) * (totalNoeuds - 1) / 2;
    //int matrixWidth = totalArcs * 2;
    //int matrixHeight = totalNoeuds;
    //int matrix[][] = new int[matrixHeight][matrixWidth];
    int x, y;
    Variable xVars[] = new Variable[totalArcs * 2 * g.getdemandes().size()];
    Variable yVars[] = new Variable[totalArcs];

    int varIdx = 0;
    for (Demande d : g.getdemandes()) {
      String dName = "d" + (g.getdemandes().indexOf(d) + 1);
      for (Arc a : g.getarcs()) {
        Variable v = new Variable();
        v.setNomVar("x_" + a.getNoeudOrigine().getNom() + "_" + a.getNoeudExtremite().getNom() + "_" + dName);
        v.setBorneInf(0);
        v.setBorneSup(Double.MAX_VALUE);
        v.setType(0);
        addVariable(v);
        xVars[varIdx++] = v;
        //System.out.println(v.getNomVar());

        v = new Variable();
        v.setNomVar("x_" + a.getNoeudExtremite().getNom() + "_" + a.getNoeudOrigine().getNom() + "_" + dName);
        v.setBorneInf(0);
        v.setBorneSup(Double.MAX_VALUE);
        v.setType(0);
        addVariable(v);
        xVars[varIdx++] = v;
      //System.out.println(v.getNomVar());
      }
    }

    objectif = new Objectif();
    varIdx = 0;
    for (Arc a : g.getarcs()) {
      Variable v = new Variable();
      v.setNomVar("y_" + (varIdx + 1));
      v.setArc(a);
      v.setBorneInf(0);
      v.setBorneSup(1);
      v.setType(1);
      addVariable(v);
      yVars[varIdx++] = v;
      objectif.addTerme(new Terme(v, a.getCout()));
    }
    objectif.setMaximiser(false);


    varIdx = 0;
    for (Demande d : g.getdemandes()) {
      x = 0;
      y = 0;
      //varIdx = 6 * g.getdemandes().indexOf(d);
      for (Noeud n : g.getnoeuds()) {
        varIdx = totalNoeuds * 2 * g.getdemandes().indexOf(d);
        Contrainte c = new Contrainte();
        for (Arc a : g.getarcs()) {
          if (a.getNoeudOrigine() == n) {
            //matrix[x][y] = 1;
            //matrix[x][y + totalArcs] = -1;

            c.addTerme(new Terme(xVars[varIdx++], 1));

            c.addTerme(new Terme(xVars[varIdx++], -1));

          } else if (a.getNoeudExtremite() == n) {
            //matrix[x][y] = -1;
            //matrix[x][y + totalArcs] = 1;

            c.addTerme(new Terme(xVars[varIdx++], -1));

            c.addTerme(new Terme(xVars[varIdx++], 1));

          } else {
            //matrix[x][y] = 0;
            varIdx++;
            varIdx++;
          }
          y++;
        }
        c.setsigne(0);
        if (d.getNoeudOrigine() == n) {
          c.setsecondMembre(d.getFlux());
        } else if (d.getNoeudExtremite() == n) {
          c.setsecondMembre(-d.getFlux());
        } else {
          c.setsecondMembre(0);
        }

        addContrainte(c);

        y = 0;
        x++;
      }
    //varIdx += 6;
    }

    varIdx = 0;
    for (Demande d : g.getdemandes()) {

      for (y = 0; y < totalNoeuds; y++) {
        //varIdx = 6 * y;
        Contrainte c = new Contrainte();
        for (x = 0; x < totalArcs; x++) {
          if (x == y) {
            //System.out.println(xVars[varIdx++].getNomVar());
            c.addTerme(new Terme(xVars[varIdx++], 1));
            //System.out.println(xVars[varIdx++].getNomVar());
            c.addTerme(new Terme(xVars[varIdx++], 1));
            //System.out.println(yVars[x].getNomVar());
            c.addTerme(new Terme(yVars[x], -g.getarcs().get(x).getCapacite()));
          }
        }
        c.setsigne(-1);
        c.setsecondMembre(0);
        addContrainte(c);
      }
    }
    /*
    for (int i = 0; i < matrixHeight; i++) {
    System.out.println("");
    for (int j = 0; j < matrixWidth; j++) {
    System.out.print(matrix[i][j] + "  ");
    }
    }
     */

    int tt = 0;
    for (Variable v : variables) {
      //System.out.println(v.getNomVar());
      tt++;
    }
    System.out.println("Nombre de variables : " + tt);
    System.out.println("Nombre de contraintes : " + contraintes.size());
  }

  public ArrayList<Contrainte> getcontraintes() {
    return contraintes;
  }

  public void setcontraintes(ArrayList<Contrainte> val) {
    this.contraintes = val;
  }

  public Objectif getobjectif() {
    return objectif;
  }

  public void setobjectif(Objectif val) {
    this.objectif = val;
  }
}

