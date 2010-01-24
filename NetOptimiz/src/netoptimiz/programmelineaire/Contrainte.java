package netoptimiz.programmelineaire;

import java.util.ArrayList;

public class Contrainte {

  private int signe;
  private double secondMembre;
  private ArrayList<Terme> termes = new ArrayList<Terme>();

  public Contrainte() {
  }

  public void addTerme(Terme v) {
    termes.add(v);
  }

  public double getsecondMembre() {
    return secondMembre;
  }

  public void setsecondMembre(double val) {
    this.secondMembre = val;
  }

  public int getsigne() {
    return signe;
  }

  /**
   *
   * @param val Valeurs possibles (-1, 0, 1) <br/>
   * -1 signifie inférieur ou égale <br/>
   *  0 signifie égale <br/>
   *  1 signifie supérieur ou égale
   */
  public void setsigne(int val) {
    this.signe = val;
  }

  public ArrayList<Terme> getTermes() {
    return termes;
  }

  public void setTermes(ArrayList<Terme> val) {
    this.termes = val;
  }
}

