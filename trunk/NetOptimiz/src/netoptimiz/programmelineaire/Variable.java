package netoptimiz.programmelineaire;

import ilog.concert.IloNumVar;
import netoptimiz.graphe.Arc;

public class Variable {

  private String nomVar;

  //private double coefficient;
  private int type;
  private double borneInf;
  private double borneSup;
  private IloNumVar var;
  private Arc arc;

  public Variable() {
  }

  public IloNumVar getVar() {
    return var;
  }

  public void setVar(IloNumVar var) {
    this.var = var;
  }

  public double getBorneInf() {
    return borneInf;
  }

  public void setBorneInf(double val) {
    this.borneInf = val;
  }

  public double getBorneSup() {
    return borneSup;
  }

  public void setBorneSup(double val) {
    this.borneSup = val;
  }
  /*public double getCoefficient () {
  return coefficient;
  }

  public void setCoefficient (double val) {
  this.coefficient = val;
  }*/

  public String getNomVar() {
    return nomVar;
  }

  public void setNomVar(String val) {
    this.nomVar = val;
  }

  public int getType() {
    return type;
  }

  /**
   * Détermine si la variables est continue ou discrète
   * @param val Valeurs possibles (0 ou 1) <br/>
   * 0 signifie variable continue<br/>
   * 1 signifie variable discrete
   */
  public void setType(int val) {
    this.type = val;
  }

  void setArc(Arc a) {
    this.arc = a;
  }

  public Arc getArc() {
    return arc;
  }

  
}

