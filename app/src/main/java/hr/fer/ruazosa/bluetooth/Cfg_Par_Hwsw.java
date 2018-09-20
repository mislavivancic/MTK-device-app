package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 29.8.2018..
 */

public class Cfg_Par_Hwsw {
    byte	cBrparam;
    int	cID;
    byte	cPcbRev;
    byte	cNrel;
    byte	cRtc;
    byte	cNprog;
    byte	cNpar;
    byte	cNsez;
    byte	cNpraz;

    public Cfg_Par_Hwsw(byte cBrparam, int cID, byte cPcbRev, byte cNrel, byte cRtc, byte cNprog, byte cNpar, byte cNsez, byte cNpraz) {
        this.cBrparam = cBrparam;
        this.cID = cID;
        this.cPcbRev = cPcbRev;
        this.cNrel = cNrel;
        this.cRtc = cRtc;
        this.cNprog = cNprog;
        this.cNpar = cNpar;
        this.cNsez = cNsez;
        this.cNpraz = cNpraz;
    }

    public Cfg_Par_Hwsw(){

    }
}
