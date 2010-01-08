package netoptimiz.vns;

public abstract class ModeleVNS {

    private int kmax;

    private int iterationsInternes;

    public ModeleVNS () {
    }

    public int getIterationsInternes () {
        return iterationsInternes;
    }

    public void setIterationsInternes (int val) {
        this.iterationsInternes = val;
    }

    public int getKmax () {
        return kmax;
    }

    public void setKmax (int val) {
        this.kmax = val;
    }

    public double resoudre () {
        return 0.0;
    }

    public abstract void faireMvt ();

    public abstract void accepterMvt ();

    public abstract void refuserMvt ();

    public abstract double calculerCout ();

    public abstract double deltaCout ();

}

