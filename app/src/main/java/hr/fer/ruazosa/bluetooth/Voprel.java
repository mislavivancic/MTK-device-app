package hr.fer.ruazosa.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mislav on 24.8.2018..
 */

public class Voprel implements Parcelable{
    byte StaPrij;
    int	VAkProR1;
    int	VAkProR2;
    int	VAkProR3;
    int	VAkProR4;

    public Voprel(){

    }

    public static final Parcelable.Creator<Voprel> CREATOR = new Parcelable.Creator<Voprel>() {
        public Voprel createFromParcel(Parcel in) {
            return new Voprel(in);
        }

        public Voprel[] newArray(int size) {
            return new Voprel[size];
        }
    };
    public Voprel(Voprel voprel){
        this.StaPrij=voprel.StaPrij;
        this.VAkProR1=voprel.VAkProR1;
        this.VAkProR2=voprel.VAkProR2;
        this.VAkProR3=voprel.VAkProR3;
        this.VAkProR4=voprel.VAkProR4;
    }
    public Voprel(Parcel in){
        this.StaPrij=in.readByte();
        this.VAkProR1=in.readInt();
        this.VAkProR2=in.readInt();
        this.VAkProR3=in.readInt();
        this.VAkProR4=in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(StaPrij);
        dest.writeInt(VAkProR1);
        dest.writeInt(VAkProR2);
        dest.writeInt(VAkProR3);
        dest.writeInt(VAkProR4);


    }

}
