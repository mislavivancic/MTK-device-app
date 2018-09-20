package hr.fer.ruazosa.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mislav on 24.8.2018..
 */

public class Oprij implements Parcelable{
    byte DuzAdr;
    byte VDuzAdr;
    Klopr KlOpR1;
    Klopr KlOpR2;
    Klopr KlOpR3;
    Klopr KlOpR4;
    int	Dly24H;
    byte PolUKRe;
    int	VerKomAdr;
    int	VAdrPrij;
    Vadrr VAdrR1;
    Vadrr VAdrR2;
    Vadrr VAdrR3;
    Vadrr VAdrR4;
    int	VC1R1;
    int	VC1R2;
    int	VC1R3;
    int	VC1R4;
    byte[] CRelXSw;			//R1-R4
    Voprel VOpRe;
    byte VIdBr;
    byte ParFlags;
    byte StaR1PwON_OFF;
    byte StaR2PwON_OFF;
    byte StaR3PwON_OFF;
    byte StaR4PwON_OFF;
    byte VCRel1Tu;
    byte VCRel2Tu;
    byte VCRel3Tu;
    byte VCRel4Tu;
    byte StaAsat;


    byte AsatKorOn;
    byte AsatKorOff;
    byte PromjZLjU;
    byte FlagLjVr;

    public Oprij(){
        this.CRelXSw=new byte[4];
        this.KlOpR1=new Klopr();
        this.KlOpR2=new Klopr();
        this.KlOpR3=new Klopr();
        this.KlOpR4=new Klopr();
        this.VAdrR1=new Vadrr();
        this.VAdrR2=new Vadrr();
        this.VAdrR3=new Vadrr();
        this.VAdrR4=new Vadrr();
        this.VOpRe=new Voprel();
    }

    public static final Parcelable.Creator<Oprij> CREATOR = new Parcelable.Creator<Oprij>() {
        public Oprij createFromParcel(Parcel in) {
            return new Oprij(in);
        }

        public Oprij[] newArray(int size) {
            return new Oprij[size];
        }
    };
    public Oprij(Oprij oprij){
        this.DuzAdr=oprij.DuzAdr;
        this.VDuzAdr=oprij.VDuzAdr;
        this.KlOpR1=oprij.KlOpR1;
        this.KlOpR2=oprij.KlOpR2;
        this.KlOpR3=oprij.KlOpR3;
        this.KlOpR4=oprij.KlOpR4;
        this.Dly24H=oprij.Dly24H;
        this.PolUKRe=oprij.PolUKRe;
        this.VerKomAdr=oprij.VerKomAdr;
        this.VAdrPrij=oprij.VAdrPrij;
        this.VAdrR1=oprij.VAdrR1;
        this.VAdrR2=oprij.VAdrR2;
        this.VAdrR3=oprij.VAdrR3;
        this.VAdrR4=oprij.VAdrR4;
        this.VC1R1=oprij.VC1R1;
        this.VC1R2=oprij.VC1R2;
        this.VC1R3=oprij.VC1R3;
        this.VC1R4=oprij.VC1R4;
        this.CRelXSw=oprij.CRelXSw;
        this.VOpRe=oprij.VOpRe;
        this.VIdBr=oprij.VIdBr;
        this.ParFlags=oprij.ParFlags;
        this.StaR1PwON_OFF=oprij.StaR1PwON_OFF;
        this.StaR2PwON_OFF=oprij.StaR2PwON_OFF;
        this.StaR3PwON_OFF=oprij.StaR3PwON_OFF;
        this.StaR4PwON_OFF=oprij.StaR4PwON_OFF;
        this.VCRel1Tu=oprij.VCRel1Tu;
        this.VCRel2Tu=oprij.VCRel2Tu;
        this.VCRel3Tu=oprij.VCRel3Tu;
        this.VCRel4Tu=oprij.VCRel4Tu;
        this.StaAsat=oprij.StaAsat;
        this.AsatKorOn=oprij.AsatKorOn;
        this.AsatKorOff=oprij.AsatKorOff;
        this.PromjZLjU=oprij.PromjZLjU;
        this.FlagLjVr=oprij.FlagLjVr;
    }
    public Oprij(Parcel in){
                this.DuzAdr=in.readByte();
                this.VDuzAdr=in.readByte();

                this.KlOpR1.KlAdr=in.readInt();
                this.KlOpR1.KlNeA=in.readInt();
                this.KlOpR1.KImRa=in.readByte();
                this.KlOpR1.KImRb=in.readByte();
                this.KlOpR1.KRelDela=in.readInt();
                this.KlOpR1.KRelDelb=in.readInt();

        this.KlOpR2.KlAdr=in.readInt();
        this.KlOpR2.KlNeA=in.readInt();
        this.KlOpR2.KImRa=in.readByte();
        this.KlOpR2.KImRb=in.readByte();
        this.KlOpR2.KRelDela=in.readInt();
        this.KlOpR2.KRelDelb=in.readInt();

        this.KlOpR3.KlAdr=in.readInt();
        this.KlOpR3.KlNeA=in.readInt();
        this.KlOpR3.KImRa=in.readByte();
        this.KlOpR3.KImRb=in.readByte();
        this.KlOpR3.KRelDela=in.readInt();
        this.KlOpR3.KRelDelb=in.readInt();

        this.KlOpR4.KlAdr=in.readInt();
        this.KlOpR4.KlNeA=in.readInt();
        this.KlOpR4.KImRa=in.readByte();
        this.KlOpR4.KImRb=in.readByte();
        this.KlOpR4.KRelDela=in.readInt();
        this.KlOpR4.KRelDelb=in.readInt();


                this.Dly24H=in.readInt();
                this.PolUKRe=in.readByte();
                this.VerKomAdr=in.readInt();
                this.VAdrPrij=in.readInt();


                this.VAdrR1.VAdrRA=in.readByte();
                this.VAdrR1.VAdrRB=in.readByte();
                this.VAdrR1.VAdrRC=in.readByte();
                this.VAdrR1.VAdrRD=in.readByte();

        this.VAdrR2.VAdrRA=in.readByte();
        this.VAdrR2.VAdrRB=in.readByte();
        this.VAdrR2.VAdrRC=in.readByte();
        this.VAdrR2.VAdrRD=in.readByte();

        this.VAdrR3.VAdrRA=in.readByte();
        this.VAdrR3.VAdrRB=in.readByte();
        this.VAdrR3.VAdrRC=in.readByte();
        this.VAdrR3.VAdrRD=in.readByte();

        this.VAdrR4.VAdrRA=in.readByte();
        this.VAdrR4.VAdrRB=in.readByte();
        this.VAdrR4.VAdrRC=in.readByte();
        this.VAdrR4.VAdrRD=in.readByte();




                this.VC1R1=in.readInt();
                this.VC1R2=in.readInt();
                this.VC1R3=in.readInt();
                this.VC1R4=in.readInt();
                this.CRelXSw[0]=in.readByte();
        this.CRelXSw[1]=in.readByte();
        this.CRelXSw[2]=in.readByte();
        this.CRelXSw[3]=in.readByte();


                this.VOpRe.StaPrij=in.readByte();
                this.VOpRe.VAkProR1=in.readInt();
                this.VOpRe.VAkProR2=in.readInt();
                this.VOpRe.VAkProR3=in.readInt();
                this.VOpRe.VAkProR4=in.readInt();


                this.VIdBr=in.readByte();
                this.ParFlags=in.readByte();
                this.StaR1PwON_OFF=in.readByte();
                this.StaR2PwON_OFF=in.readByte();
                this.StaR3PwON_OFF=in.readByte();
                this.StaR4PwON_OFF=in.readByte();
                this.VCRel1Tu=in.readByte();
                this.VCRel2Tu=in.readByte();
                this.VCRel3Tu=in.readByte();
                this.VCRel4Tu=in.readByte();
                this.StaAsat=in.readByte();


                this.AsatKorOn=in.readByte();
                this.AsatKorOff=in.readByte();
                this.PromjZLjU=in.readByte();
                this.FlagLjVr=in.readByte();
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(DuzAdr);
        dest.writeByte(VDuzAdr);

        dest.writeInt(KlOpR1.KlAdr);
        dest.writeInt(KlOpR1.KlNeA);
        dest.writeByte(KlOpR1.KImRa);
        dest.writeByte(KlOpR1.KImRb);
        dest.writeInt(KlOpR1.KRelDela);
        dest.writeInt(KlOpR1.KRelDelb);

        dest.writeInt(KlOpR2.KlAdr);
        dest.writeInt(KlOpR2.KlNeA);
        dest.writeByte(KlOpR2.KImRa);
        dest.writeByte(KlOpR2.KImRb);
        dest.writeInt(KlOpR2.KRelDela);
        dest.writeInt(KlOpR2.KRelDelb);

        dest.writeInt(KlOpR3.KlAdr);
        dest.writeInt(KlOpR3.KlNeA);
        dest.writeByte(KlOpR3.KImRa);
        dest.writeByte(KlOpR3.KImRb);
        dest.writeInt(KlOpR3.KRelDela);
        dest.writeInt(KlOpR3.KRelDelb);

        dest.writeInt(KlOpR4.KlAdr);
        dest.writeInt(KlOpR4.KlNeA);
        dest.writeByte(KlOpR4.KImRa);
        dest.writeByte(KlOpR4.KImRb);
        dest.writeInt(KlOpR4.KRelDela);
        dest.writeInt(KlOpR4.KRelDelb);

        dest.writeInt(Dly24H);
        dest.writeByte(PolUKRe);
        dest.writeInt(VerKomAdr);
        dest.writeInt(VAdrPrij);

        dest.writeByte(VAdrR1.VAdrRA);
        dest.writeByte(VAdrR1.VAdrRB);
        dest.writeByte(VAdrR1.VAdrRC);
        dest.writeByte(VAdrR1.VAdrRD);

        dest.writeByte(VAdrR2.VAdrRA);
        dest.writeByte(VAdrR2.VAdrRB);
        dest.writeByte(VAdrR2.VAdrRC);
        dest.writeByte(VAdrR2.VAdrRD);

        dest.writeByte(VAdrR3.VAdrRA);
        dest.writeByte(VAdrR3.VAdrRB);
        dest.writeByte(VAdrR3.VAdrRC);
        dest.writeByte(VAdrR3.VAdrRD);

        dest.writeByte(VAdrR4.VAdrRA);
        dest.writeByte(VAdrR4.VAdrRB);
        dest.writeByte(VAdrR4.VAdrRC);
        dest.writeByte(VAdrR4.VAdrRD);

        dest.writeInt(VC1R1);
        dest.writeInt(VC1R2);
        dest.writeInt(VC1R3);
        dest.writeInt(VC1R4);

        dest.writeByte(CRelXSw[0]);
        dest.writeByte(CRelXSw[1]);
        dest.writeByte(CRelXSw[2]);
        dest.writeByte(CRelXSw[3]);

        dest.writeByte(VOpRe.StaPrij);
        dest.writeInt(VOpRe.VAkProR1);
        dest.writeInt(VOpRe.VAkProR2);
        dest.writeInt(VOpRe.VAkProR3);
        dest.writeInt(VOpRe.VAkProR4);


        dest.writeByte(VIdBr);
        dest.writeByte(ParFlags);
        dest.writeByte(StaR1PwON_OFF);
        dest.writeByte(StaR2PwON_OFF);
        dest.writeByte(StaR3PwON_OFF);
        dest.writeByte(StaR4PwON_OFF);
        dest.writeByte(VCRel1Tu);
        dest.writeByte(VCRel2Tu);
        dest.writeByte(VCRel3Tu);
        dest.writeByte(VCRel4Tu);
        dest.writeByte(StaAsat);

        dest.writeByte(AsatKorOn);
        dest.writeByte(AsatKorOff);
        dest.writeByte(PromjZLjU);
        dest.writeByte(FlagLjVr);


    }

    public void init(){
        KlOpR1.KlAdr = 0x820400;
        KlOpR1.KlNeA = 0x820400;
        KlOpR1.KImRa = 0;
        KlOpR1.KImRb = 0;
        KlOpR1.KRelDela = 0;
        KlOpR1.KRelDelb = 0;
        KlOpR2.KlAdr = 0x820400;
        KlOpR2.KlNeA = 0x820400;
        KlOpR2.KImRa = 0;
        KlOpR2.KImRb = 0;
        KlOpR2.KRelDela = 0;
        KlOpR2.KRelDelb = 0;
        KlOpR3.KlAdr = 0x820400;
        KlOpR3.KlNeA = 0x820400;
        KlOpR3.KImRa = 00;
        KlOpR3.KImRb = 00;
        KlOpR3.KRelDela = 0;
        KlOpR3.KRelDelb = 0;
        KlOpR4.KlAdr = 0x00;
        KlOpR4.KlNeA = 0x00;
        KlOpR4.KImRa = 00;
        KlOpR4.KImRb = 00;
        KlOpR4.KRelDela = 0;
        KlOpR4.KRelDelb = 0;
        Dly24H = 2;
        PolUKRe = 0;		//A=UK
        VAdrR1.VAdrRA = 1;
        VAdrR1.VAdrRB = 1;
        VAdrR1.VAdrRC = 1;
        VAdrR1.VAdrRD = 1;
        VAdrR2.VAdrRA = 1;
        VAdrR2.VAdrRB = 2;
        VAdrR2.VAdrRC = 2;
        VAdrR2.VAdrRD = 2;
        VAdrR3.VAdrRA = 1;
        VAdrR3.VAdrRB = 2;
        VAdrR3.VAdrRC = 2;
        VAdrR3.VAdrRD = 3;
        VAdrR4.VAdrRA = 0;
        VAdrR4.VAdrRB = 0;
        VAdrR4.VAdrRC = 0;
        VAdrR4.VAdrRD = 0;
        VC1R1 = 0;
        VC1R2 = 0x0;
        VC1R3 = 0x0;
        VC1R4 = 0x0;

        for (int uIndex = 0; uIndex < 4; uIndex++)	{
            CRelXSw[uIndex] = 0;
        }

        VOpRe.StaPrij =(byte)0xE1;
        VOpRe.VAkProR1 = 0x000;
        VOpRe.VAkProR2 = 0x000;
        VOpRe.VAkProR3 = 0x000;
        VOpRe.VAkProR4 = 0x000;

        VCRel1Tu = 0;
        VCRel2Tu = 0;
        VCRel3Tu = 0;
        VCRel4Tu = 0;

        VIdBr = 0x02;
        ParFlags = 0x0;
        StaR1PwON_OFF = 0;
        StaR2PwON_OFF = 0;
        StaR3PwON_OFF = 0;
        StaR4PwON_OFF = 0;

        StaAsat = 0x00;
        AsatKorOff = 0;
        AsatKorOn = 0;
        PromjZLjU = 0;
    }
}
