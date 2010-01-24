/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package netoptimiz.programmelineaire;

/**
 *
 * @author yassine
 */
public class Terme {
  private Variable v;
  private double coeff;

  public Terme(Variable v, double coeff) {
    this.v = v;
    this.coeff = coeff;
  }

  public double getCoef() {
    return coeff;
  }

  public void setCoef(double coeff) {
    this.coeff = coeff;
  }

  public Variable getV() {
    return v;
  }

  public void setV(Variable v) {
    this.v = v;
  }

  
}
