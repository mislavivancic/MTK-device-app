package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 28.8.2018..
 */

public class Tlg5 {
    Telegram Fn5;
    Telegram CikR4;

    public Tlg5(){

    }

    public Tlg5(Tlg5 tlg5){
        this.Fn5=tlg5.Fn5;
        this.CikR4=tlg5.CikR4;
    }

    public void updt1(){
        this.Fn5=this.CikR4;
    }

    public void updt2(){
        this.CikR4=this.Fn5;
    }

}
