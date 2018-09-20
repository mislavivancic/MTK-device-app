package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 28.8.2018..
 */

public class TelegCMD {
    byte [] AktiImp;
    byte BrAkImp;
    byte [] NeutImp;
    byte Fn;

    public TelegCMD(){
        this.AktiImp=new byte[7];
        this.NeutImp=new byte[7];
    }
}
