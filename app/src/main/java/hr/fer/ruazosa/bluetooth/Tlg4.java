package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 28.8.2018..
 */

public class Tlg4 {
    Telegram Fn4;
    Telegram CikR3;

    public Tlg4(){

    }
    public Tlg4(Tlg4 tlg4){
        this.Fn4=tlg4.Fn4;
        this.CikR3=tlg4.CikR3;
    }

    public void updt1(){
        this.Fn4=this.CikR3;
    }

    public void updt2(){
        this.CikR3=this.Fn4;
    }
}
