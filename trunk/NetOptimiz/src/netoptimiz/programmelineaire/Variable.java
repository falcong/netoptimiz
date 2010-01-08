package netoptimiz.programmelineaire;

public class Variable {

    private String nomVar;

    private double coefficient;

    private int type;

    private int borneInf;

    private int borneSup;

    public Variable () {
    }

    public int getBorneInf () {
        return borneInf;
    }

    public void setBorneInf (int val) {
        this.borneInf = val;
    }

    public int getBorneSup () {
        return borneSup;
    }

    public void setBorneSup (int val) {
        this.borneSup = val;
    }

    public double getCoefficient () {
        return coefficient;
    }

    public void setCoefficient (double val) {
        this.coefficient = val;
    }

    public String getNomVar () {
        return nomVar;
    }

    public void setNomVar (String val) {
        this.nomVar = val;
    }

    public int getType () {
        return type;
    }

    public void setType (int val) {
        this.type = val;
    }

}

