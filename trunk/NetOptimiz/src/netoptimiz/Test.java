package netoptimiz;

import netoptimiz.programmelineaire.ProgrammeLineaire;
import netoptimiz.recuit.TelecomRecuit;
import netoptimiz.vns.TelecomVNS;

public class Optimisation {

    private TelecomVNS telecomVNS;

    private TelecomRecuit telecomRecuit;

    private Methode methode;

    private ProgrammeLineaire programmeLineaire;

    public Optimisation () {
    }

    public int verifSolution () {
        return 0;
    }

    public double resoudre () {
        return 0.0;
    }

    public void plusCourtChemin () {
    }

    public Methode getmethode () {
        return methode;
    }

    public void setmethode (Methode val) {
        this.methode = val;
    }

    public ProgrammeLineaire getprogrammeLineaire () {
        return programmeLineaire;
    }

    public void setprogrammeLineaire (ProgrammeLineaire val) {
        this.programmeLineaire = val;
    }

    public TelecomRecuit getTelecomRecuit () {
        return telecomRecuit;
    }

    public void setTelecomRecuit (TelecomRecuit val) {
        this.telecomRecuit = val;
    }

    public TelecomVNS getTelecomVNS () {
        return telecomVNS;
    }

    public void setTelecomVNS (TelecomVNS val) {
        this.telecomVNS = val;
    }

}

