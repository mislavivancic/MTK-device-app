package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 28.8.2018..
 */

public class Tlg6 {
    Telegram Fn6;
    Telegram Sinh;

    public Tlg6(){

    }

    public Tlg6(Tlg6 tlg6){
        this.Fn6=tlg6.Fn6;
        this.Sinh=tlg6.Sinh;
    }

    public void updt1(){
        this.Fn6=this.Sinh;
    }

    public void updt2(){
        this.Sinh=this.Fn6;
    }
}
