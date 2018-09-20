package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 1.8.2018..
 */

public class Mgaddr
{
    public int i;
    public int reg;
    public int typ;
    public int objectt;
    public int group;


    public Mgaddr(int i){
        this.i=i;
        reg=i&0x1F;
        typ=(i>>5)&0x7;
        objectt=(i>>8)&0x17;
        group=(i>>12)&0x17;
    }

    public void updt(){
        this.reg=i&0x1F;
        this.typ=(i&0xE0)>>5;
        this.objectt=(i&0xF00)>>8;
        this.group=(i&0xF000)>>12;
    }
}
