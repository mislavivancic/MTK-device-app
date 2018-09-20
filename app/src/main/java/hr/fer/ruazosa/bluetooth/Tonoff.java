package hr.fer.ruazosa.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mislav on 1.8.2018..
 */

public class Tonoff {
   // byte status;
     int Toff;
     int Toffb;
     int Ton;
     int Tonb;

    public Tonoff(){

    }
    public Tonoff(int value){
        Toff=value&0x7FF;
        Toffb=(value>>11)&0x1;
        Ton=(value>>12)&0x7FF;
        Tonb=(value>>23)&0x1;
    }


/*
    public static final Parcelable.Creator<Tonoff> CREATOR = new Parcelable.Creator<Tonoff>() {
        public Tonoff createFromParcel(Parcel in) {
            return new Tonoff(in);
        }

        public Tonoff[] newArray(int size) {
            return new Tonoff[size];
        }
    };

    public Tonoff(Parcel in){
        //this.status=in.readByte();
        this.Toff=in.readInt();
        this.Toffb=in.readInt();
        this.Ton=in.readInt();
        this.Tonb=in.readInt();
    }

     @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(status);
        dest.writeInt(Toff);
        dest.writeInt(Toffb);
        dest.writeInt(Ton);
        dest.writeInt(Tonb);
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
*/
    public Tonoff(Tonoff tonoff){
       // this.status=tonoff.status;
       this.Toff=tonoff.Toff;
       this.Toffb=tonoff.Toffb;
       this.Ton=tonoff.Ton;
       this.Tonb=tonoff.Tonb;
    }

}
