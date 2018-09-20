package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 28.8.2018..
 */

public class Tlg7 {
    Telegram Fn7;
    Telegram XXX;

    public Tlg7(){

    }

    public Tlg7(Tlg7 tlg7){
        this.Fn7=tlg7.Fn7;
        this.XXX=tlg7.XXX;
    }


    public void updt1(){
        this.Fn7=this.XXX;
    }

    public void updt2(){
        this.XXX=this.Fn7;
    }
}
