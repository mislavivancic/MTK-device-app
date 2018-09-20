package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 28.8.2018..
 */

public class Tlg3 {
    Telegram Fn3;
    Telegram CikR2;

    public Tlg3(){

    }
    public Tlg3(Tlg3 tlg3){
        this.Fn3=tlg3.Fn3;
        this.CikR2=tlg3.CikR2;
    }

    public void updt1(){
        this.Fn3=this.CikR2;
    }

    public void updt2(){
        this.CikR2=this.Fn3;
    }
}
