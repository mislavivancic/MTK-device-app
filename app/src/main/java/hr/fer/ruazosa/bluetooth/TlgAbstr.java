package hr.fer.ruazosa.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mislav on 22.8.2018..
 */

public class TlgAbstr implements Parcelable {
    byte status;
    byte  OnRes;
    int  TDetect;
    byte RestOn;
    byte OnTaExe;

    public TlgAbstr(){

    }

    public static final Parcelable.Creator<TlgAbstr> CREATOR = new Parcelable.Creator<TlgAbstr>() {
        public TlgAbstr createFromParcel(Parcel in) {
            return new TlgAbstr(in);
        }

        public TlgAbstr[] newArray(int size) {
            return new TlgAbstr[size];
        }
    };
    public TlgAbstr(TlgAbstr tlgAbstr){
        this.status=tlgAbstr.status;
        this.OnRes=tlgAbstr.OnRes;
        this.TDetect=tlgAbstr.TDetect;
        this.RestOn=tlgAbstr.RestOn;
        this.OnTaExe=tlgAbstr.OnTaExe;

    }
    public TlgAbstr(Parcel in){
        this.status=in.readByte();
        this.OnRes=in.readByte();
        this.TDetect=in.readInt();
        this.RestOn=in.readByte();
        this.OnTaExe=in.readByte();
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(status);
        dest.writeByte(OnRes);
        dest.writeInt(TDetect);
        dest.writeByte(RestOn);
        dest.writeByte(OnTaExe);

    }

}
