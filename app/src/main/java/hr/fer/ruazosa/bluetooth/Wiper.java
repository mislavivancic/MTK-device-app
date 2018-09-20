package hr.fer.ruazosa.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mislav on 2.8.2018..
 */

public class Wiper implements Parcelable{
    byte status;
    int Tswdly;
    int Twiper;
    int TBlockPrePro;

    public Wiper(){

    }


    public static final Parcelable.Creator<Wiper> CREATOR = new Parcelable.Creator<Wiper>() {
        public Wiper createFromParcel(Parcel in) {
            return new Wiper(in);
        }

        public Wiper[] newArray(int size) {
            return new Wiper[size];
        }
    };
    public Wiper(Wiper wiper){
        this.status=wiper.status;
        this.TBlockPrePro=wiper.TBlockPrePro;
        this.Twiper=wiper.Twiper;
        this.Tswdly=wiper.Twiper;
    }
    public Wiper(Parcel in){
        this.status = in.readByte();
        this.Tswdly=in.readInt();
        this.Twiper=in.readInt();
        this.TBlockPrePro=in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(status);
        dest.writeInt(Tswdly);
        dest.writeInt(Twiper);
        dest.writeInt(TBlockPrePro);
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public int getTswdly() {
        return Tswdly;
    }

    public void setTswdly(int Tswdly) {
        this.Tswdly = Tswdly;
    }

    public int getTwiper() {
        return Twiper;
    }

    public void setTwiper(int Twiper) {
        this.Twiper = Twiper;
    }

    public int getTBlockPrePro() {
        return TBlockPrePro;
    }

    public void setTBlockPrePro(int TBlockPrePro) {
        this.TBlockPrePro = TBlockPrePro;
    }

}
