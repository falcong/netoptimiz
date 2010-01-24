package netoptimiz.programmelineaire;

import java.util.ArrayList;

public class Objectif {

  private boolean maximiser;
  private ArrayList<Terme> termes = new ArrayList<Terme>();

  public Objectif() {
  }

  public void addTerme(Terme v) {
    termes.add(v);
  }

  public boolean isMaximiser() {
    return maximiser;
  }

  public void setMaximiser(boolean maximiser) {
    this.maximiser = maximiser;
  }

  public ArrayList<Terme> getTermes() {
    return termes;
  }

  public void setTermes(ArrayList<Terme> termes) {
    this.termes = termes;
  }
}

