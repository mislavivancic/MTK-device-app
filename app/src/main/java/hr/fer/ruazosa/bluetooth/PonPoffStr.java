package hr.fer.ruazosa.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mislav on 21.8.2018..
 */

public class PonPoffStr implements Parcelable{
    byte status;
    byte OnPonExe;
    byte lperIgno;
    int TminSwdly;
    int TrndSwdly;
    int Tlng;
    byte lOnPonExe;
    byte  OnPoffExe;
    int TBlockPrePro;


    public PonPoffStr(){

    }
    public static final Parcelable.Creator<PonPoffStr> CREATOR = new Parcelable.Creator<PonPoffStr>() {
        public PonPoffStr createFromParcel(Parcel in) {
            return new PonPoffStr(in);
        }

        public PonPoffStr[] newArray(int size) {
            return new PonPoffStr[size];
        }
    };
    public PonPoffStr(PonPoffStr ponPoffStr){
        this.OnPonExe=ponPoffStr.OnPonExe;
        this.lperIgno=ponPoffStr.lperIgno;
        this.TminSwdly=ponPoffStr.TminSwdly;
        this.TrndSwdly=ponPoffStr.TrndSwdly;
        this.Tlng=ponPoffStr.Tlng;
        this.lOnPonExe=ponPoffStr.lOnPonExe;
        this.OnPoffExe=ponPoffStr.OnPoffExe;
        this.TBlockPrePro=ponPoffStr.TBlockPrePro;
    }
    public PonPoffStr(Parcel in){
        this.status=in.readByte();
        this.OnPonExe=in.readByte();
        this.lperIgno=in.readByte();
        this.TminSwdly=in.readInt();
        this.TrndSwdly=in.readInt();
        this.Tlng=in.readInt();
        this.lOnPonExe=in.readByte();
        this.OnPoffExe=in.readByte();
        this.TBlockPrePro=in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(status);
        dest.writeByte(OnPonExe);
        dest.writeByte(lperIgno);
        dest.writeInt(TminSwdly);
        dest.writeInt(TrndSwdly);
        dest.writeInt(Tlng);
        dest.writeByte(lOnPonExe);
        dest.writeByte(OnPoffExe);
        dest.writeInt(TBlockPrePro);

    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
