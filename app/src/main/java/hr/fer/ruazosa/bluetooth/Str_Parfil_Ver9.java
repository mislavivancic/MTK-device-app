package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 29.8.2018..
 */

public class Str_Parfil_Ver9 {
    byte NYM1;
    byte NYM2;
    int K_V;	//
    int REZ;
    int UTHMIN;
    int UTLMAX;
    int PERIOD;
    int FORMAT;	//Formatirani izlaz iz prve dekade ((YNH:YNL)*2^FORMAT)/256
    int BROJ;
    double fre;

    public Str_Parfil_Ver9(byte NYM1, byte NYM2, int k_V, int REZ, int UTHMIN, int UTLMAX, int PERIOD, int FORMAT, int BROJ, double fre) {
        this.NYM1 = NYM1;
        this.NYM2 = NYM2;
        K_V = k_V;
        this.REZ = REZ;
        this.UTHMIN = UTHMIN;
        this.UTLMAX = UTLMAX;
        this.PERIOD = PERIOD;
        this.FORMAT = FORMAT;
        this.BROJ = BROJ;
        this.fre = fre;
    }

    public Str_Parfil_Ver9(){

    }
}
