package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 29.8.2018..
 */

public class Str_Parfil {
    byte NYM1;
    byte NYM2;
    int YM_B1;	//adresa buffera u prijemniku
    int YM_B2;
    int UTHMIN;
    int UTLMAX;
    int PERIOD;
    byte FORMAT;	//Formatirani izlaz iz prve dekade ((YNH:YNL)*2^FORMAT)/256
    int BROJ;
    double fre;

    public Str_Parfil(byte NYM1, byte NYM2, int YM_B1, int YM_B2, int UTHMIN, int UTLMAX, int PERIOD, byte FORMAT, int BROJ, double fre) {
        this.NYM1 = NYM1;
        this.NYM2 = NYM2;
        this.YM_B1 = YM_B1;
        this.YM_B2 = YM_B2;
        this.UTHMIN = UTHMIN;
        this.UTLMAX = UTLMAX;
        this.PERIOD = PERIOD;
        this.FORMAT = FORMAT;
        this.BROJ = BROJ;
        this.fre = fre;
    }
    public Str_Parfil(){

    }
}
