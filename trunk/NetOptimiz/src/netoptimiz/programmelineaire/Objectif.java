package netoptimiz.programmelineaire;

import java.util.ArrayList; 

public class Objectif {

    private boolean maximiser;

    private ArrayList<Variable> variables;

    public Objectif () {
    }

    public boolean getmaximiser () {
        return maximiser;
    }

    public void setmaximiser (boolean val) {
        this.maximiser = val;
    }

    public ArrayList<Variable> getvariables () {
        return variables;
    }

    public void setvariables (ArrayList<Variable> val) {
        this.variables = val;
    }

}

