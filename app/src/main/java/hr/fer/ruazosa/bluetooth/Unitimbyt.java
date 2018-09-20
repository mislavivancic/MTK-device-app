package hr.fer.ruazosa.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by mislav on 1.8.2018..
 */

public class Unitimbyt {
     Tonoff t;
     int i;
     byte[] b;

    public Unitimbyt(){
        t=new Tonoff();
        b=new byte[4];

    }

    public static final Parcelable.Creator<Unitimbyt> CREATOR = new Parcelable.Creator<Unitimbyt>() {
        public Unitimbyt createFromParcel(Parcel in) {
            return new Unitimbyt(in);
        }

        public Unitimbyt[] newArray(int size) {
            return new Unitimbyt[size];
        }
    };
    public Unitimbyt(Unitimbyt unitimbyt){
        this.b=unitimbyt.b;
        this.i=unitimbyt.i;
        this.t=unitimbyt.t;
    }
    public Unitimbyt(Parcel in){
        this.i = in.readInt();

    }

    public void updti(){
        this.i=0x0;
        int top1=0;
        top1=b[3];
        top1<<=32;
        top1&=0xFF000000;

        int top2=0;
        top2=b[2];
        top2<<=16;
        top2&=0x00FF0000;

        int top3=0;
        top3=b[1];
        top3<<=8;
        top3&=0x0000FF00;

        int top4=0;
        top4=b[0];
        top4&=0x000000FF;

        this.i=this.i|top1|top2|top3|top4;

       /* this.i=b[3];
        this.i<<=8;
        this.i|=b[2];
        this.i<<=8;
        this.i|=b[1];
        this.i<<=8;
        this.i|=b[0];
        */
    }
    public void updt(){
        t=new Tonoff(this.i);
        b[3]=(byte)((this.i>>32)&0xFF);
        b[2]=(byte)((this.i>>16)&0xFF);
        b[1]=(byte)((this.i>>8)&0xFF);
        b[0]=(byte)((this.i)&0xFF);
    }



}
