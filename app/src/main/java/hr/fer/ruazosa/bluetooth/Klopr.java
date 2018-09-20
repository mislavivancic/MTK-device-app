package hr.fer.ruazosa.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mislav on 22.8.2018..
 */

public class Klopr implements Parcelable{
    int	KlAdr;
    int	KlNeA;
    byte KImRa;
    byte KImRb;
    int KRelDela;
    int KRelDelb;



    public Klopr(){

    }

    public static final Parcelable.Creator<Klopr> CREATOR = new Parcelable.Creator<Klopr>() {
        public Klopr createFromParcel(Parcel in) {
            return new Klopr(in);
        }

        public Klopr[] newArray(int size) {
            return new Klopr[size];
        }
    };
    public Klopr(Klopr klopr){
        this.KlAdr=klopr.KlAdr;
        this.KlNeA=klopr.KlNeA;
        this.KImRa=klopr.KImRa;
        this.KImRb=klopr.KImRb;
        this.KRelDela=klopr.KRelDela;
        this.KRelDelb=klopr.KRelDelb;

    }
    public Klopr(Parcel in){
        this.KlAdr=in.readInt();
        this.KlNeA=in.readInt();
        this.KImRa=in.readByte();
        this.KImRb=in.readByte();
        this.KRelDela=in.readInt();
        this.KRelDelb=in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(KlAdr);
        dest.writeInt(KlNeA);
        dest.writeByte(KImRa);
        dest.writeByte(KImRb);
        dest.writeInt(KRelDela);
        dest.writeInt(KRelDelb);


    }
}
