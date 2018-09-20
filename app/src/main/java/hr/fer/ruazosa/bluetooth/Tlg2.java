package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 28.8.2018..
 */

public class Tlg2 {
    Telegram Fn2;
    Telegram CikR1;

    public Tlg2(){

    }

    public Tlg2(Tlg2 tlg2){
        this.Fn2=tlg2.Fn2;
        this.CikR1=tlg2.CikR1;
    }
    public void updt1(){
        this.Fn2=this.CikR1;
    }

    public void updt2(){
        this.CikR1=this.Fn2;
    }
}
