package hr.fer.ruazosa.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mislav on 24.8.2018..
 */

public class Vadrr implements Parcelable {
    byte VAdrRA;
    byte VAdrRB;
    byte VAdrRC;
    byte VAdrRD;


    public Vadrr(){

    }
    public static final Parcelable.Creator<Vadrr> CREATOR = new Parcelable.Creator<Vadrr>() {
        public Vadrr createFromParcel(Parcel in) {
            return new Vadrr(in);
        }

        public Vadrr[] newArray(int size) {
            return new Vadrr[size];
        }
    };
    public Vadrr(Vadrr vadrr){
        this.VAdrRA=vadrr.VAdrRA;
        this.VAdrRB=vadrr.VAdrRB;
        this.VAdrRC=vadrr.VAdrRC;
        this.VAdrRD=vadrr.VAdrRD;

    }
    public Vadrr(Parcel in){
        this.VAdrRA=in.readByte();
        this.VAdrRB=in.readByte();
        this.VAdrRC=in.readByte();
        this.VAdrRD=in.readByte();
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(VAdrRA);
        dest.writeByte(VAdrRB);
        dest.writeByte(VAdrRC);
        dest.writeByte(VAdrRD);



    }
}
