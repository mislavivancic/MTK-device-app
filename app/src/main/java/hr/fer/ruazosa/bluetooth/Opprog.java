package hr.fer.ruazosa.bluetooth;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mislav on 1.8.2018..
 */

public class Opprog implements Parcelable{

     int AkTim;
     byte DanPr;
     Tonoff[] Tpro;



    public Opprog(){
        Tpro=new Tonoff[14];
        for (int i=0;i<14;i++){
            Tpro[i]=new Tonoff(0);
        }
        AkTim=0;
        DanPr=0x00;

    }
    public static final Parcelable.Creator<Opprog> CREATOR = new Parcelable.Creator<Opprog>() {
        public Opprog createFromParcel(Parcel in)
        {
            return new Opprog(in);
        }

        public Opprog[] newArray(int size) {

            return new Opprog[size];
        }
    };

    public Opprog(Parcel in){
        this.AkTim = in.readInt();
        this.DanPr=in.readByte();
       //this.Tpro=in.readParcelableArray(Tonoff.class.getClassLoader());
        this.Tpro=new Tonoff[16];
        for (int i=0;i<14;i++){
            Tpro[i]=new Tonoff();
            Tpro[i].Toff=in.readInt();
            Tpro[i].Toffb=in.readInt();
            Tpro[i].Ton=in.readInt();
            Tpro[i].Tonb=in.readInt();
        }
    }

    public Opprog(Opprog opprog){
        this.AkTim=opprog.AkTim;
        this.DanPr=opprog.DanPr;
        for(int i=0;i<14;i++){
            this.Tpro[i]=opprog.Tpro[i];
        }
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(AkTim);
        dest.writeByte(DanPr);

        for (int i=0;i<14;i++){
            dest.writeInt(Tpro[i].Toff);
            dest.writeInt(Tpro[i].Toffb);
            dest.writeInt(Tpro[i].Ton);
            dest.writeInt(Tpro[i].Tonb);
        }
    }

    public byte getStatus() {
        return 0x00;
    }

    public void setStatus(byte status) {
            }
}
