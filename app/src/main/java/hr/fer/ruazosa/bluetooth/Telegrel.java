package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 28.8.2018..
 */

public class Telegrel {
    TelegCMD Uk;
    TelegCMD Isk;
    int ID;

    public Telegrel(){
        this.Uk=new TelegCMD();
        this.Isk=new TelegCMD();
    }

    public Telegrel(Telegrel telegrel){
        this.Uk=telegrel.Uk;
        this.Isk=telegrel.Isk;
        this.ID=telegrel.ID;
    }
}
