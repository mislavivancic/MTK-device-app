package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 28.8.2018..
 */

public class Tlg1 {
    Telegram Fn1;
    //Telegram VerAdr;

    public Tlg1(){
        this.Fn1=new Telegram();
    }

    public Tlg1(Tlg1 tlg1){
        this.Fn1=tlg1.Fn1;
       // this.VerAdr=tlg1.VerAdr;
    }
   // public void updt1(){this.Fn1=this.VerAdr;    }
   // public void updt2(){this.VerAdr=this.Fn1;}
}
