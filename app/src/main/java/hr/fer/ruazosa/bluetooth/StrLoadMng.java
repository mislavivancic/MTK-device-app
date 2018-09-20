package hr.fer.ruazosa.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mislav on 22.8.2018..
 */

public class StrLoadMng implements Parcelable {
    byte status;
    byte Status;
    byte  RelPos;
    int TPosMin;
    int TPosMax;


    public StrLoadMng(){

    }

    public static final Parcelable.Creator<StrLoadMng> CREATOR = new Parcelable.Creator<StrLoadMng>() {
        public StrLoadMng createFromParcel(Parcel in) {
            return new StrLoadMng(in);
        }

        public StrLoadMng[] newArray(int size) {
            return new StrLoadMng[size];
        }
    };
    public StrLoadMng(StrLoadMng strLoadMng){
        this.status=strLoadMng.status;
        this.Status=strLoadMng.Status;
        this.RelPos=strLoadMng.RelPos;
        this.TPosMin=strLoadMng.TPosMin;
        this.TPosMax=strLoadMng.TPosMax;

    }
    public StrLoadMng(Parcel in){
        this.status=in.readByte();
        this.Status=in.readByte();
        this.RelPos=in.readByte();
        this.TPosMin=in.readInt();
        this.TPosMax=in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(status);
        dest.writeByte(Status);
        dest.writeByte(RelPos);
        dest.writeInt(TPosMin);
        dest.writeInt(TPosMax);


    }
}
