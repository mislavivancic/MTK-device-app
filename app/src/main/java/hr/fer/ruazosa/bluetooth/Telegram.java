package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 28.8.2018..
 */

public class Telegram {
    TelegCMD Cmd;
    int  ID;

    public Telegram(){
        this.Cmd=new TelegCMD();
    }

    public Telegram(Telegram telegram){
        this.Cmd=telegram.Cmd;
        this.ID=telegram.ID;
    }
}
