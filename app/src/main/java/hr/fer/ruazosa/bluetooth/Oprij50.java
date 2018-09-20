package hr.fer.ruazosa.bluetooth;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mislav on 28.8.2018..
 */

public class Oprij50 implements Parcelable {
    Telegrel TlgRel1;
    Telegrel TlgRel2;
    Telegrel TlgRel3;
    Telegrel TlgRel4;


    Tlg1 [] tlg;


    VadrTreiler	TlgVerAdr2;

    int []	SinhTime;
    byte RTCSinh;
    byte WDaySinh;
    int CPWBRTIME;
    int [] CLOGENFLGS;

    public Oprij50(){
        this.TlgRel1=new Telegrel();
        this.TlgRel2=new Telegrel();
        this.TlgRel3=new Telegrel();
        this.TlgRel4=new Telegrel();

        this.tlg=new Tlg1[8];
        for (int i=0;i<8;i++){
            tlg[i]=new Tlg1();
        }

        this.TlgVerAdr2=new VadrTreiler();

        this.SinhTime=new int[5];

        this.CLOGENFLGS=new int[3];

    }

    public static final Parcelable.Creator<Oprij50> CREATOR = new Parcelable.Creator<Oprij50>() {
        public Oprij50 createFromParcel(Parcel in) {
            return new Oprij50(in);
        }

        public Oprij50[] newArray(int size) {
            return new Oprij50[size];
        }
    };
    public Oprij50(Oprij50 oprij50){
        this.TlgRel1=new Telegrel(oprij50.TlgRel1);
        this.TlgRel2=new Telegrel(oprij50.TlgRel2);
        this.TlgRel3=new Telegrel(oprij50.TlgRel3);
        this.TlgRel4=new Telegrel(oprij50.TlgRel4);
        /*
        this.tlg1=new Tlg1(oprij50.tlg1);
        this.tlg2=new Tlg2(oprij50.tlg2);
        this.tlg3=new Tlg3(oprij50.tlg3);
        this.tlg4=new Tlg4(oprij50.tlg4);
        this.tlg5=new Tlg5(oprij50.tlg5);
        this.tlg6=new Tlg6(oprij50.tlg6);
        this.tlg7=new Tlg7(oprij50.tlg7);
        this.tlg8=new Tlg8(oprij50.tlg8);
        */
        this.TlgVerAdr2=new VadrTreiler(oprij50.TlgVerAdr2);

        this.SinhTime=oprij50.SinhTime;
        this.RTCSinh=oprij50.RTCSinh;
        this.WDaySinh=oprij50.WDaySinh;
        this.CPWBRTIME=oprij50.CPWBRTIME;
        this.CLOGENFLGS=oprij50.CLOGENFLGS;
    }
    public Oprij50(Parcel in){



        for (int i=0;i<7;i++){
            this.TlgRel1.Uk.AktiImp[i]=in.readByte();
        }
        this.TlgRel1.Uk.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.TlgRel1.Uk.NeutImp[i]=in.readByte();
        }
        this.TlgRel1.Uk.Fn=in.readByte();

        for (int i=0;i<7;i++){
            this.TlgRel1.Isk.AktiImp[i]=in.readByte();
        }
        this.TlgRel1.Isk.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.TlgRel1.Isk.NeutImp[i]=in.readByte();
        }
        this.TlgRel1.Isk.Fn=in.readByte();
        this.TlgRel1.ID=in.readInt();






        for (int i=0;i<7;i++){
            this.TlgRel2.Uk.AktiImp[i]=in.readByte();
        }
        this.TlgRel2.Uk.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.TlgRel2.Uk.NeutImp[i]=in.readByte();
        }
        this.TlgRel2.Uk.Fn=in.readByte();

        for (int i=0;i<7;i++){
            this.TlgRel2.Isk.AktiImp[i]=in.readByte();
        }
        this.TlgRel2.Isk.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.TlgRel2.Isk.NeutImp[i]=in.readByte();
        }
        this.TlgRel2.Isk.Fn=in.readByte();
        this.TlgRel2.ID=in.readInt();




        for (int i=0;i<7;i++){
            this.TlgRel3.Uk.AktiImp[i]=in.readByte();
        }
        this.TlgRel3.Uk.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.TlgRel3.Uk.NeutImp[i]=in.readByte();
        }
        this.TlgRel3.Uk.Fn=in.readByte();

        for (int i=0;i<7;i++){
            this.TlgRel3.Isk.AktiImp[i]=in.readByte();
        }
        this.TlgRel3.Isk.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.TlgRel3.Isk.NeutImp[i]=in.readByte();
        }
        this.TlgRel3.Isk.Fn=in.readByte();
        this.TlgRel3.ID=in.readInt();




        for (int i=0;i<7;i++){
            this.TlgRel4.Uk.AktiImp[i]=in.readByte();
        }
        this.TlgRel4.Uk.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.TlgRel4.Uk.NeutImp[i]=in.readByte();
        }
        this.TlgRel4.Uk.Fn=in.readByte();

        for (int i=0;i<7;i++){
            this.TlgRel4.Isk.AktiImp[i]=in.readByte();
        }
        this.TlgRel4.Isk.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.TlgRel4.Isk.NeutImp[i]=in.readByte();
        }
        this.TlgRel4.Isk.Fn=in.readByte();
        this.TlgRel4.ID=in.readInt();



/*

        for (int i=0;i<7;i++){
            this.tlg1.Fn1.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg1.Fn1.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg1.Fn1.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg1.Fn1.Cmd.Fn=in.readByte();
        this.tlg1.Fn1.ID=in.readInt();
        for (int i=0;i<7;i++){
            this.tlg1.VerAdr.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg1.VerAdr.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg1.VerAdr.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg1.VerAdr.Cmd.Fn=in.readByte();
        this.tlg1.VerAdr.ID=in.readInt();




        for (int i=0;i<7;i++){
            this.tlg2.Fn2.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg2.Fn2.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg2.Fn2.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg2.Fn2.Cmd.Fn=in.readByte();
        this.tlg2.Fn2.ID=in.readInt();
        for (int i=0;i<7;i++){
            this.tlg2.CikR1.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg2.CikR1.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg2.CikR1.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg2.CikR1.Cmd.Fn=in.readByte();
        this.tlg2.CikR1.ID=in.readInt();




        for (int i=0;i<7;i++){
            this.tlg3.Fn3.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg3.Fn3.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg3.Fn3.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg3.Fn3.Cmd.Fn=in.readByte();
        this.tlg3.Fn3.ID=in.readInt();
        for (int i=0;i<7;i++){
            this.tlg3.CikR2.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg3.CikR2.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg3.CikR2.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg3.CikR2.Cmd.Fn=in.readByte();
        this.tlg3.CikR2.ID=in.readInt();




        for (int i=0;i<7;i++){
            this.tlg4.Fn4.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg4.Fn4.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg4.Fn4.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg4.Fn4.Cmd.Fn=in.readByte();
        this.tlg4.Fn4.ID=in.readInt();
        for (int i=0;i<7;i++){
            this.tlg4.CikR3.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg4.CikR3.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg4.CikR3.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg4.CikR3.Cmd.Fn=in.readByte();
        this.tlg4.CikR3.ID=in.readInt();




        for (int i=0;i<7;i++){
            this.tlg5.Fn5.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg5.Fn5.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg5.Fn5.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg5.Fn5.Cmd.Fn=in.readByte();
        this.tlg5.Fn5.ID=in.readInt();
        for (int i=0;i<7;i++){
            this.tlg5.CikR4.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg5.CikR4.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg5.CikR4.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg5.CikR4.Cmd.Fn=in.readByte();
        this.tlg5.CikR4.ID=in.readInt();





        for (int i=0;i<7;i++){
            this.tlg6.Fn6.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg6.Fn6.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg6.Fn6.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg6.Fn6.Cmd.Fn=in.readByte();
        this.tlg6.Fn6.ID=in.readInt();
        for (int i=0;i<7;i++){
            this.tlg6.Sinh.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg6.Sinh.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg6.Sinh.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg6.Sinh.Cmd.Fn=in.readByte();
        this.tlg6.Sinh.ID=in.readInt();




        for (int i=0;i<7;i++){
            this.tlg7.Fn7.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg7.Fn7.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg7.Fn7.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg7.Fn7.Cmd.Fn=in.readByte();
        this.tlg7.Fn7.ID=in.readInt();
        for (int i=0;i<7;i++){
            this.tlg7.XXX.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg7.XXX.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg7.XXX.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg7.XXX.Cmd.Fn=in.readByte();
        this.tlg7.XXX.ID=in.readInt();





        for (int i=0;i<7;i++){
            this.tlg8.Fn8.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg8.Fn8.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg8.Fn8.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg8.Fn8.Cmd.Fn=in.readByte();
        this.tlg8.Fn8.ID=in.readInt();
        for (int i=0;i<7;i++){
            this.tlg8.Emerg.Cmd.AktiImp[i]=in.readByte();
        }
        this.tlg8.Emerg.Cmd.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.tlg8.Emerg.Cmd.NeutImp[i]=in.readByte();
        }
        this.tlg7.XXX.Cmd.Fn=in.readByte();
        this.tlg7.XXX.ID=in.readInt();




        this.TlgVerAdr2.ValidVAdr=in.readByte();
        this.TlgVerAdr2.DuzAdr=in.readByte();
        for (int i=0;i<7;i++){
            this.TlgVerAdr2.TlgAdr.Cmd.AktiImp[i]=in.readByte();
        }
        this.TlgVerAdr2.TlgAdr.ID=in.readInt();

        for (int i=0;i<5;i++) {
            this.SinhTime[i] = in.readInt();
        }
        this.RTCSinh=in.readByte();
        this.WDaySinh=in.readByte();
        this.CPWBRTIME=in.readInt();

        for (int i=0;i<3;i++){
            this.CLOGENFLGS[i]=in.readInt();
        }
*/

    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        for (int i=0;i<7;i++){
            dest.writeByte(TlgRel1.Uk.AktiImp[i]);
        }
        dest.writeByte(TlgRel1.Uk.BrAkImp);
        for (int i=0;i<7;i++){
            dest.writeByte(TlgRel1.Uk.NeutImp[i]); //////RU SAM STAO 28.8
        }
 /*
        this.TlgRel1.Uk.Fn=in.readByte();

        for (int i=0;i<7;i++){
            this.TlgRel1.Isk.AktiImp[i]=in.readByte();
        }
        this.TlgRel1.Isk.BrAkImp=in.readByte();
        for (int i=0;i<7;i++){
            this.TlgRel1.Isk.NeutImp[i]=in.readByte();
        }
        this.TlgRel1.Isk.Fn=in.readByte();
        this.TlgRel1.ID=in.readInt();
*/

    }


}
