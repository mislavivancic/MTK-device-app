package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 1.8.2018..
 */

public class Uni4byt {
    int i;
    byte[] b;

    public Uni4byt(){
        b=new byte[4];
        i|=b[3];
        i<<=8;
        i|=b[2];
        i<<=8;
        i|=b[1];
        i<<=8;
        i|=b[0];


    }
    public void update(){
        int top1=0;
        top1=b[3];
        top1<<=32;
        top1&=0xFF000000;

        int top2=0;
        top2=b[2];
        top2<<=16;
        top2&=0x00FF0000;

        int top3=0;
        top3=b[1];
        top3<<=8;
        top3&=0x0000FF00;

        int top4=0;
        top4=b[0];
        top4&=0x000000FF;

        this.i=this.i|top1|top2|top3|top4;
       /* i=i&0x0;
        i|=b[3];
        i<<=8;
        i|=b[2];
        i<<=8;
        i|=b[1];
        i<<=8;
        i|=b[0];
        */
    }
    public void updatei(){
        this.b[0]=(byte)(this.i&0xFF);
        this.b[1]=(byte)((this.i>>8)&0xFF);
        this.b[2]=(byte)((this.i>>16)&0xFF);
        this.b[3]=(byte)((this.i>>24)&0xFF);
    }
}
