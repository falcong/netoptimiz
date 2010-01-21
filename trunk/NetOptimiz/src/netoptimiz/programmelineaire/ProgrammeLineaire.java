package netoptimiz.programmelineaire;

import java.util.ArrayList;
import netoptimiz.Application;
import netoptimiz.graphe.Graphe;

public class ProgrammeLineaire {

  private ArrayList<Variable> variables;
  private Objectif objectif;
  private ArrayList<Contrainte> contraintes;

  public ProgrammeLineaire() {
  }

  public ArrayList<Variable> getVariables() {
    return variables;
  }

  public void setVariables(ArrayList<Variable> val) {
    this.variables = val;
  }

  public double resoudre() {
    Graphe graphe = Application.getSingleton().getgraphe();
    if (graphe.getnoeuds().isEmpty()) {
      System.err.println("Le graphe n'est pas encore intitalisé");
    } else {
      
    }
    return 0.0;
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

