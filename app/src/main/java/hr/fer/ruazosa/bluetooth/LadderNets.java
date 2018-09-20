package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 12.9.2018..
 */

public class LadderNets {
    int Idx;
    byte[] RelState=new byte[4];
    byte[] RelNr=new byte[4];
    boolean[] isSeries=new boolean[4];
    boolean[] notsernotpar=new boolean[4];
    boolean[] conectshort=new boolean[4];

    public LadderNets(){

    }

    public LadderNets(int idx, byte[] relState, byte[] relNR, boolean[] isSeries, boolean[] notsernotpar, boolean[] conectshort) {
        Idx = idx;
        RelState = relState;
        RelNr = relNR;
        this.isSeries = isSeries;
        this.notsernotpar = notsernotpar;
        this.conectshort = conectshort;
    }
}
