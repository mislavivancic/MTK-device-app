package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 28.8.2018..
 */

public class VadrTreiler {
    byte ValidVAdr;
    byte DuzAdr;
    Telegram TlgAdr;

    public VadrTreiler(){
        this.TlgAdr=new Telegram();
    }
    public VadrTreiler(VadrTreiler vadrTreiler) {
        this.ValidVAdr=vadrTreiler.ValidVAdr;
        this.DuzAdr=vadrTreiler.DuzAdr;
        this.TlgAdr=vadrTreiler.TlgAdr;
    }
}
