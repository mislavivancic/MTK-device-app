package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 28.8.2018..
 */

public class Tlg8 {
    Telegram Fn8;
    Telegram Emerg;

    public Tlg8(){

    }
    public Tlg8(Tlg8 tlg8){
        this.Fn8=tlg8.Fn8;
        this.Emerg=tlg8.Emerg;
    }

    public void updt1(){
        this.Fn8=this.Emerg;
    }

    public void updt2(){
        this.Emerg=this.Fn8;
    }
}
