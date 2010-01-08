package netoptimiz.programmelineaire;

import java.util.ArrayList; 

public class Contrainte {

    private int signe;

    private double secondMembre;

    private ArrayList<Variable> variables;

    public Contrainte () {
    }

    public double getsecondMembre () {
        return secondMembre;
    }

    public void setsecondMembre (double val) {
        this.secondMembre = val;
    }

    public int getsigne () {
        return signe;
    }

    public void setsigne (int val) {
        this.signe = val;
    }

    public ArrayList<Variable> getvariables () {
        return variables;
    }

    public void setvariables (ArrayList<Variable> val) {
        this.variables = val;
    }

}

