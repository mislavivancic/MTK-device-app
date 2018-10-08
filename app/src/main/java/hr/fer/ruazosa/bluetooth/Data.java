package hr.fer.ruazosa.bluetooth;

import java.util.IllegalFormatException;
import java.util.List;

/**
 * Created by mislav on 27.7.2018..
 */

public class Data {


    private final int TIP_S=0;
    private final int TIP_SN=1;
    private final int TIP_SPA=2;
    private final int TIP_PAS=3;
    private final int TIP_PA=4;
    private final int TIP_PASN=5;
    private final int TIP_SPN=6;
    private final int TIP_PS=7;


    private final byte[]  bVtmask={(byte) 0x80,(byte)0x40,(byte)0x20,(byte)0x10,(byte)0x08,(byte)0x04,(byte)0x02,(byte)0x01};

    private Str_Parfil_Ver9 [] TbParFilteraVer9={
            new Str_Parfil_Ver9( (byte)21,	(byte)21,	0x0A20,	0,	1800,	1520,	0x0492,	0x0101,	0,	175.00  ),
            new Str_Parfil_Ver9((byte)22,	(byte)22,	0x0730,	0,	2650,	2450,	0x045D,	0x0101,	1,	183.3333333)
            ,           new Str_Parfil_Ver9((byte)23,(byte)	23,	0x058A,	0,	2150,	2000,	0x042D,	0x0101,	2,	194.00	),
            new Str_Parfil_Ver9((byte)25,	(byte)25,	0x0920,	0,	2430,	2375,	0x03D7,	0x0101,	3,	208.3333333),
            new Str_Parfil_Ver9( (byte)26,(byte) 	26,	0x0A40,	0,	3800,	3600,	0x03B1,	0x0101,	4,	216.6666667),
            new Str_Parfil_Ver9((byte) 27,(byte)	27,	0x0A20,	0,	1800,	1520,	0x038E,	0x0102,	5,	225	),
            new Str_Parfil_Ver9((byte) 28,(byte)	28,	0x0A20,	0,	1800,	1520,	0x036E,	0x0102,	6,	233.3333333),
            new Str_Parfil_Ver9((byte) 32,(byte) 	16,	0x0A20,	0,	1800,	1520,	0x0300,	0x0102,	7,	266.6666667),
            new Str_Parfil_Ver9((byte) 34,(byte) 	34,	0x0DE0,	0,	5000,	4860,	0x02D3,	0x0101,	8,	283.3333333),
            new Str_Parfil_Ver9((byte) 38,(byte) 	38,	0x0C20,	0,	2520,	2400,	0x0287,	0x0002,	9,	316.66666670),
            new Str_Parfil_Ver9((byte)20,(byte)	20,	0x0A20,	0,	1800,	1520,	0x0266,	0x0102,	10,	333.33333331),
            new Str_Parfil_Ver9((byte)22,(byte) 22,	0x0A20,	0,	1800,	1520,	0x022F,	0x0102,	11,	366.66666672),
            new Str_Parfil_Ver9((byte)23,(byte)	23,	0x0A20,	0,	1800,	1520,	0x0216,	0x0102,	12,	383.33333333),
            new Str_Parfil_Ver9((byte) 25,(byte)	25,	0x0A20,	0,	2880,	2700,	0x01EC,	0x0100,	13,	416.66666674),
            new Str_Parfil_Ver9((byte) 25,(byte)	25,	0x0A20,	0,	2790,   2700,	0x01EB,	0x0100,	14,	420.00 	),
            new Str_Parfil_Ver9((byte) 26,(byte) 	26,	0x0A20,	0,	1800,	1520,	0x01D9,	0x0100,	15,	433.33333336),


    };

    private byte[] input;
    private char[] mline;
    private int m_Dateerr;
    private int m_cntxx;
    private byte gaHigh;
    private byte gaLow;
    private boolean check;
    int globalIndex=0;

    private int m_SWVerPri;
    private int m_HWVerPri;
    public int m_BrojRast;
    public Cfg_Par_Hwsw m_CFG;

    private final double UTFREFP=0.9;

    public double m_Utf_posto;

    private Opprog[] m_PProg_R1=new Opprog[16];
   private Opprog[] m_PProg_R2=new Opprog[16];
   private Opprog[] m_PProg_R3=new Opprog[16];
   private Opprog[] m_PProg_R4=new Opprog[16];

    //private PonPoffStr[] m_PonPoffRx;





    public Data(byte[] input,int m_SWVerPri,Cfg_Par_Hwsw m_CFG,int m_HWVerPri){
        this.input=input;
        this.mline=new char[256];
        this.m_SWVerPri=m_SWVerPri;
        this.m_CFG=m_CFG;


     //   m_PonPoffRx=new PonPoffStr[4];
     //   m_PonPoffRx[0]=new PonPoffStr();
     //   m_PonPoffRx[1]=new PonPoffStr();
     //   m_PonPoffRx[2]=new PonPoffStr();
     //   m_PonPoffRx[3]=new PonPoffStr();





    }

    public void processData(Wiper[] wiper,PonPoffStr[] m_PonPoffRx,TlgAbstr[]
            tlgAbstrs,StrLoadMng[] strLoadMngs,Opprog [] m_PProg_R1,Opprog []
            m_PProg_R2,Opprog [] m_PProg_R3,Opprog [] m_PProg_R4,
                            Str_Parfil_Ver9 m_ParFilteraCF,Str_Parfil m_ParFiltera,
                            Oprij m_opPrij,Oprij50 m_op50Prij,Rreallc[] m_Realloc,Telegram[] m_TelegSync,Telegram [] m_TlgFnD,IntrlockStr [] m_RelInterLock){
        m_Dateerr=0;
        m_cntxx=1;
        mline[0]=0;
        while(getMsgLine()){
            if(mline[0]=='!'){
                break;
            }
            getLineDat(wiper,m_PonPoffRx,tlgAbstrs,strLoadMngs,m_PProg_R1,m_PProg_R2,m_PProg_R3,m_PProg_R4,m_ParFilteraCF,m_ParFiltera
            ,m_opPrij,m_op50Prij,m_Realloc,m_TelegSync,m_TlgFnD,m_RelInterLock);
        }
    }

private boolean getMsgLine(){
    int i=0;
    while(m_cntxx<input.length) {
        if (input[m_cntxx] == 0x0D) {

            mline[i++] = (char) input[m_cntxx++];
            if (input[m_cntxx] == 0x0A) {
                mline[i++] = (char) input[m_cntxx++];
                mline[i++] = 0;

                return true;
            } else {
                mline[i++] = 0;
                m_Dateerr++;
                return false;
            }

        }
        mline[i++]=(char)input[m_cntxx++];
        }
        m_Dateerr++;
    return false;

}

private void getLineDat(Wiper [] wiper,PonPoffStr[] m_PonPoffRx,TlgAbstr[] m_TlgAbsensceRx,
                        StrLoadMng[] strLoadMngs,Opprog [] m_PProg_R1,Opprog [] m_PProg_R2,
                        Opprog [] m_PProg_R3,Opprog [] m_PProg_R4,Str_Parfil_Ver9 m_ParFilteraCF,Str_Parfil m_ParFiltera,
                            Oprij m_opPrij,Oprij50 m_op50Prij,Rreallc[] m_Realloc,Telegram[] m_TelegSync,Telegram [] m_TlgFnD,IntrlockStr[] m_RelInterLock){
    int i=0;
    Mgaddr m_gaddr=new Mgaddr(0);
    char bb;

    while(i<5){
        if (mline[i]=='('){
            break;
        }
        bb=HtoB(mline[i++]);
        if(check){
            m_gaddr.i<<=4;

            m_gaddr.i|=bb;



        }
        else{
            m_Dateerr++;
        }
    }
    m_gaddr.updt();

    if(!(mline[i++]==')')){
        m_Dateerr++;
    }
    i=5;
    byte[] dbuf=new byte[128];
    int j,k;

    for(j=0;j<128;j++){
        dbuf[j]=0;
        k=2;
        while(k!=0)
        {
            k--;
            if(mline[i]==')'){
                break;
            }
            bb=HtoB(mline[i++]);
            if(check)
            {
                dbuf[j] <<=4;
                dbuf[j] |=bb;
            }
            else{
                m_Dateerr++;
            }        }
        if(mline[i]==')'){
            break;
        }
    }
    unpackDatv9(dbuf,m_gaddr,wiper,m_PonPoffRx,m_TlgAbsensceRx,strLoadMngs,m_PProg_R1,m_PProg_R2,m_PProg_R3,m_PProg_R4,m_ParFilteraCF,m_ParFiltera,
            m_opPrij,m_op50Prij,m_Realloc,m_TelegSync,m_TlgFnD,m_RelInterLock);



}
private char HtoB(char ch){
    if(ch>='0' && ch<='9' ){
        check=true;
        return (char)(ch-'0');
    }
    if (ch >= 'A' && ch <= 'F'){
        check=true;
        return (char)(ch - 'A' + 0xA);
    }
    check=false;
    return ch;

}


private void unpackDatv9(byte[] dbuf,Mgaddr m_gaddr,Wiper [] wiper,
                         PonPoffStr [] m_PonPoffRx,TlgAbstr[] m_TlgAbsensceRx,
                         StrLoadMng[] strLoadMngs,Opprog [] m_PProg_R1,
                         Opprog [] m_PProg_R2,Opprog [] m_PProg_R3,Opprog [] m_PProg_R4,Str_Parfil_Ver9 m_ParFilteraCF,Str_Parfil m_ParFiltera,
                        Oprij m_opPrij,Oprij50 m_op50Prij,Rreallc[] m_Realloc,
                         Telegram[] m_TelegSync,Telegram [] m_TlgFnD,IntrlockStr[] m_RelInterLock){
    byte[] m_pbuf=dbuf;
    //-----nezz-----
    globalIndex=0;

    if(m_gaddr.group==0){
        //nesto
    }

    if(m_gaddr.group==3){
        GetRelInterLock(m_RelInterLock,m_pbuf);
    }


    if((m_gaddr.group>0)&&(m_gaddr.group<5)){
        globalIndex=0;
        getTparPar(m_gaddr.group,m_gaddr.objectt,m_pbuf,m_PProg_R1,m_PProg_R2,m_PProg_R3,m_PProg_R4);
    }


    if(m_gaddr.group==0x5){
        if(m_gaddr.objectt==0){
            getWiperData(wiper,m_pbuf);
        }
        else if (m_gaddr.objectt==1){
            GetPonPoffRDat(m_PonPoffRx,m_pbuf);
        }
        else if (m_gaddr.objectt==2){
            GetTlgAbsensceDat(m_TlgAbsensceRx,m_pbuf);
        }
        else if(m_gaddr.objectt==3){
            GetLearningDat(strLoadMngs,m_pbuf);
        }
    }

    if (m_gaddr.group==0x8){
        GetOprijParV9(m_gaddr,m_opPrij,m_op50Prij,m_pbuf,m_Realloc);
    }
    if (m_gaddr.group==0x9){
        GetTlg50Par(m_op50Prij,m_pbuf,m_gaddr,m_TelegSync,m_TlgFnD);
    }

    if (m_gaddr.group==0xC) {
        GetFriRPar(m_ParFilteraCF,m_ParFiltera,m_pbuf);
    }
}


private void GetRelInterLock(IntrlockStr[] m_RelInterLock,byte[] m_pbuf){
    for (int rel=0;rel<4;rel++){
        m_RelInterLock[rel].wBitsOn=SetOprelI(m_pbuf);
        m_RelInterLock[rel].wBitsOff=SetOprelI(m_pbuf);
        m_RelInterLock[rel].PcCnfg[0]=SetOprelI(m_pbuf);
        m_RelInterLock[rel].PcCnfg[1]=SetOprelI(m_pbuf);
    }
}

private void GetTlg50Par(Oprij50 m_op50Prij,byte[] m_pbuf,Mgaddr mgaddr,Telegram[] m_TelegSync,Telegram [] m_TlgFnD){
    if (m_SWVerPri >= 96) {
        GetTlg50ParV96(m_op50Prij,m_pbuf,mgaddr,m_TelegSync,m_TlgFnD);
        return;
    }
}
private  void GetTlg50ParV96(Oprij50 m_op50Prij,byte [] m_pbuf,Mgaddr mgaddr,Telegram[] m_TelegSync,Telegram [] m_TlgFnD){
    if (m_CFG.cID >= 0x8c) {
        GetTlgID50ParV96(m_op50Prij,m_pbuf,mgaddr,m_TelegSync,m_TlgFnD);
        return;
    }
    switch (mgaddr.objectt){
        case 0:
            storeDataTlgRel(m_op50Prij.TlgRel1,m_pbuf);
            break;
        case 1:
            storeDataTlgRel(m_op50Prij.TlgRel2,m_pbuf);
            break;
        case 2:
            storeDataTlgRel(m_op50Prij.TlgRel3,m_pbuf);
            break;
        case 3:
            storeDataTlgRel(m_op50Prij.TlgRel4,m_pbuf);
            break;

    }
}

    private void GetTlgID50ParV96(Oprij50 m_op50Prij,byte [] m_pbuf,Mgaddr mgaddr,Telegram[] m_TelegSync,Telegram [] m_TlgFnD){
        switch (mgaddr.objectt){
            case 0:
                storeDataTlgRel(m_op50Prij.TlgRel1,m_pbuf);
                break;
            case 1:
                storeDataTlgRel(m_op50Prij.TlgRel2,m_pbuf);
                break;
            case 2:
                storeDataTlgRel(m_op50Prij.TlgRel3,m_pbuf);
                break;
            case 3:
                storeDataTlgRel(m_op50Prij.TlgRel4,m_pbuf);
                break;
            case 4:
                storeDataTlgFn(m_op50Prij.tlg[0].Fn1,m_pbuf);
                storeDataTlgFn(m_op50Prij.tlg[1].Fn1,m_pbuf);
                storeDataTlgFn(m_op50Prij.tlg[2].Fn1,m_pbuf);
                break;
            case 5:
                storeDataTlgFn(m_op50Prij.tlg[3].Fn1,m_pbuf);
                storeDataTlgFn(m_op50Prij.tlg[4].Fn1,m_pbuf);
                break;
            case 6:
                storeDataTlgFn(m_op50Prij.tlg[5].Fn1,m_pbuf);
                storeDataTlgFn(m_op50Prij.tlg[6].Fn1,m_pbuf);
                storeDataTlgFn(m_op50Prij.tlg[7].Fn1,m_pbuf);
                break;
            case 8:
                storeDataTlgFn(m_TelegSync[0],m_pbuf);
                storeDataTlgFn(m_TelegSync[1],m_pbuf);
                break;
            case 9:
                storeDataTlgFn(m_TelegSync[2],m_pbuf);
                storeDataTlgFn(m_TelegSync[3],m_pbuf);
                storeDataTlgFn(m_TelegSync[4],m_pbuf);
                break;
            case 0x0A:
                storeDataTlgFn(m_TlgFnD[0],m_pbuf);
                storeDataTlgFn(m_TlgFnD[1],m_pbuf);
                storeDataTlgFn(m_TlgFnD[2],m_pbuf);
                break;
            case 0x0B:
                storeDataTlgFn(m_TlgFnD[3],m_pbuf);
                storeDataTlgFn(m_TlgFnD[4],m_pbuf);
                break;
            case 0x0C:
                storeDataTlgFn(m_TlgFnD[5],m_pbuf);
                storeDataTlgFn(m_TlgFnD[6],m_pbuf);
                storeDataTlgFn(m_TlgFnD[7],m_pbuf);
                break;
            default:
                break;
        }
    }

    private void storeDataTlgFn(Telegram fn,byte[] m_pbuf){
        for (int i=0;i<7;i++){
            fn.Cmd.AktiImp[i]=m_pbuf[globalIndex++];
        }

            fn.Cmd.BrAkImp=m_pbuf[globalIndex++];

        for (int i=0;i<7;i++){
            fn.Cmd.NeutImp[i]=m_pbuf[globalIndex++];
        }

        fn.Cmd.Fn=m_pbuf[globalIndex++];


        int temp=m_pbuf[globalIndex++];
        int tempUp=m_pbuf[globalIndex++];
        tempUp<<=8;
        fn.ID=temp|tempUp;
        System.out.print("");
    }
private void storeDataTlgRel(Telegrel TlgRel,byte[] m_pbuf){
    for (int i=0;i<7;i++){
        TlgRel.Uk.AktiImp[i]=m_pbuf[globalIndex++];
    }
    TlgRel.Uk.BrAkImp=m_pbuf[globalIndex++];
    for (int i=0;i<7;i++){
        TlgRel.Uk.NeutImp[i]=m_pbuf[globalIndex++];
    }
    TlgRel.Uk.Fn=m_pbuf[globalIndex++];

    for (int i=0;i<7;i++){
        TlgRel.Isk.AktiImp[i]=m_pbuf[globalIndex++];
    }
    TlgRel.Isk.BrAkImp=m_pbuf[globalIndex++];
    for (int i=0;i<7;i++){
        TlgRel.Isk.NeutImp[i]=m_pbuf[globalIndex++];
    }
    TlgRel.Isk.Fn=m_pbuf[globalIndex++];

    int temp=m_pbuf[globalIndex++];
    int tempUp=m_pbuf[globalIndex++];
    tempUp<<=8;
    TlgRel.ID=temp|tempUp;


}

  private int  SetOprel3I(byte[] m_pbuf){
    Uni4byt tempi=new Uni4byt();
    tempi.i=0;
    tempi.b[2]=m_pbuf[globalIndex++];
    tempi.b[1]=m_pbuf[globalIndex++];
    tempi.b[0]=m_pbuf[globalIndex++];
    tempi.update();
    return tempi.i;
  }

  private void getWiperData(Wiper []  m_WipersRx,byte [] m_pbuf){
      int uIndex;

      for(uIndex=0;uIndex<4;uIndex++){
          m_WipersRx[uIndex].status=(byte)(0x80+0x20);
      }
      setWiperRelData(m_WipersRx[0],m_pbuf);
      setWiperRelData(m_WipersRx[1],m_pbuf);
      setWiperRelData(m_WipersRx[2],m_pbuf);
      setWiperRelData(m_WipersRx[3],m_pbuf);
  }

  private void setWiperRelData(Wiper wipRelx,byte [] m_pbuf){
      wipRelx.status=m_pbuf[globalIndex++];
      wipRelx.Tswdly=SetOprel3I(m_pbuf);
      wipRelx.Twiper=SetOprel3I(m_pbuf);
      wipRelx.TBlockPrePro=SetOprel3I(m_pbuf);
  }


  // Ova metoda je gotova ali nisam mogo parcelable napravit pa sam komentirao


 private void getTparPar(int rel,int nProNum,byte[] m_pbuf,Opprog [] m_PProg_R1,Opprog [] m_PProg_R2,Opprog [] m_PProg_R3,Opprog [] m_PProg_R4){

      Opprog pPProg=new Opprog();
      Unitimbyt x=new Unitimbyt();
      int nrTpar=8;

      int m_SWVerPri=0x60;
      int m_HWVerPri=7;
      int TIP_SPA=2;
      int NR_TPAR_SPA=5;
      int NR_TPAR_MAX=14;

      if (rel>4 || rel<1) {
          rel=1;
      }


      if((m_SWVerPri >= 90) && (m_HWVerPri == TIP_SPA) ) {
          nrTpar = NR_TPAR_SPA;
      }
      else if((	m_SWVerPri>=40)&&(m_SWVerPri  < 96)){
          nrTpar = NR_TPAR_MAX;
      }

      else {
          nrTpar =11;
          //nrTpar = m_CFG.cNpar;
      }


      x.i=0;
      x.b[1]=m_pbuf[globalIndex++];

      if(	m_SWVerPri>=40){
          x.b[0]=m_pbuf[globalIndex++];
      }

      x.updti();


      System.out.print("a");

      switch (rel){
          case 1:
              m_PProg_R1[nProNum].AkTim=x.i;
              m_PProg_R1[nProNum].DanPr=m_pbuf[globalIndex++];

              for(int uIndex=0;uIndex<nrTpar;uIndex++){
                  x.i=SetOprel3I(m_pbuf);
                  x.updt();
                  m_PProg_R1[nProNum].Tpro[uIndex]=x.t;
              }
              break;
          case 2:
              m_PProg_R2[nProNum].AkTim=x.i;
              m_PProg_R2[nProNum].DanPr=m_pbuf[globalIndex++];

              for(int uIndex=0;uIndex<nrTpar;uIndex++){
                  x.i=SetOprel3I(m_pbuf);
                  x.updt();
                  m_PProg_R2[nProNum].Tpro[uIndex]=x.t;
              }
              break;
          case 3:
              m_PProg_R3[nProNum].AkTim=x.i;
              m_PProg_R3[nProNum].DanPr=m_pbuf[globalIndex++];

              for(int uIndex=0;uIndex<nrTpar;uIndex++){
                  x.i=SetOprel3I(m_pbuf);
                  x.updt();
                  m_PProg_R3[nProNum].Tpro[uIndex]=x.t;
              }
              break;
          case 4:
              m_PProg_R4[nProNum].AkTim=x.i;
              m_PProg_R4[nProNum].DanPr=m_pbuf[globalIndex++];

              for(int uIndex=0;uIndex<nrTpar;uIndex++){
                  x.i=SetOprel3I(m_pbuf);
                  x.updt();
                  m_PProg_R4[nProNum].Tpro[uIndex]=x.t;
              }
              break;

      }
System.out.print("a");

  }


  private void GetPonPoffRDat(PonPoffStr[] m_PonPoffRx,byte [] m_pbuf) {
      SetPonPoffReData(m_PonPoffRx[0],m_pbuf);
      SetPonPoffReData(m_PonPoffRx[1],m_pbuf);
      SetPonPoffReData(m_PonPoffRx[2],m_pbuf);
      SetPonPoffReData(m_PonPoffRx[3],m_pbuf);
  }

  private void SetPonPoffReData(PonPoffStr m_PonPoffRx,byte [] m_pbuf){
        m_PonPoffRx.OnPonExe=m_pbuf[globalIndex++];
        m_PonPoffRx.lperIgno=m_pbuf[globalIndex++];
        m_PonPoffRx.TminSwdly=SetOprel3I(m_pbuf);
        m_PonPoffRx.TrndSwdly=SetOprel3I(m_pbuf);
        m_PonPoffRx.Tlng=SetOprel3I(m_pbuf);

        if((m_PonPoffRx.Tlng&0x800000)!=0){
            m_PonPoffRx.lperIgno=(byte)0x80;
        }else{
            m_PonPoffRx.lperIgno=(byte)0x00;
        }

        m_PonPoffRx.Tlng &= 0x7fffff;
        m_PonPoffRx.lOnPonExe=m_pbuf[globalIndex++];
        m_PonPoffRx.OnPoffExe=m_pbuf[globalIndex++];
        m_PonPoffRx.TBlockPrePro=SetOprel3I(m_pbuf);
  }


  private void GetTlgAbsensceDat(TlgAbstr[] m_TlgAbsensceRx,byte[] m_pbuf){
      for (int i=0;i<4;i++){
          m_TlgAbsensceRx[i].OnRes=m_pbuf[globalIndex++];
          m_TlgAbsensceRx[i].TDetect=SetOprel3I(m_pbuf);
          m_TlgAbsensceRx[i].RestOn=m_pbuf[globalIndex++];
          m_TlgAbsensceRx[i].OnTaExe=m_pbuf[globalIndex++];

      }

  }

  private void GetLearningDat(StrLoadMng[] strLoadMngs,byte[] m_pbuf){
      for (int i=0;i<4;i++){
          strLoadMngs[i].Status=m_pbuf[globalIndex++];
          strLoadMngs[i].RelPos=m_pbuf[globalIndex++];
          strLoadMngs[i].TPosMin=SetOprel3I(m_pbuf);
          strLoadMngs[i].TPosMax=SetOprel3I(m_pbuf);

      }

  }

  private void GetFriRPar(Str_Parfil_Ver9 m_ParFilteraCF,Str_Parfil m_ParFiltera,byte[] m_pbuf){
      if(m_SWVerPri >=90) {
          GetFriRParVer9(m_ParFilteraCF, m_pbuf);
          return;
      }
          m_ParFiltera.NYM1=m_pbuf[globalIndex++];;
          m_ParFiltera.NYM2=m_pbuf[globalIndex++];;
          m_ParFiltera.YM_B1=SetOprelI(m_pbuf);
          m_ParFiltera.YM_B2=SetOprelI(m_pbuf);
          m_ParFiltera.UTHMIN=SetOprelI(m_pbuf);
          m_ParFiltera.UTLMAX=SetOprelI(m_pbuf);
          m_ParFiltera.PERIOD=SetOprelI(m_pbuf);
          m_ParFiltera.FORMAT=m_pbuf[globalIndex++];
          m_ParFiltera.BROJ=m_pbuf[globalIndex++];
          m_BrojRast=SetOprelI(m_pbuf);


  }

  public void GetFriRParVer9(Str_Parfil_Ver9 m_ParFilteraCF,byte [] m_pbuf){
      m_ParFilteraCF.NYM1=m_pbuf[globalIndex++];
      m_ParFilteraCF.NYM2=m_pbuf[globalIndex++];
      m_ParFilteraCF.K_V=SetOprelI(m_pbuf);
      m_ParFilteraCF.REZ=SetOprelI(m_pbuf);
      m_ParFilteraCF.UTHMIN=SetOprelI(m_pbuf);
      m_ParFilteraCF.UTLMAX=SetOprelI(m_pbuf);
      m_ParFilteraCF.PERIOD=SetOprelI(m_pbuf);
      m_ParFilteraCF.FORMAT=SetOprelI(m_pbuf);
      m_ParFilteraCF.BROJ=m_pbuf[globalIndex++];
      //m_pbuf++;
      globalIndex++;
      m_BrojRast=SetOprelI(m_pbuf);
     SetUfposto(m_ParFilteraCF);
  }

    private int  SetOprelI(byte[] m_pbuf){
        Uni4byt tempi=new Uni4byt();
        tempi.i=0;
        tempi.b[1]=m_pbuf[globalIndex++];
        tempi.b[0]=m_pbuf[globalIndex++];
        tempi.update();
        return tempi.i;
    }

    private void SetUfposto(Str_Parfil_Ver9 m_ParFilteraCF){
        int broj = m_ParFilteraCF.BROJ;
        if (broj>=0){

            int KvUt = m_ParFilteraCF.K_V;
            if (KvUt == 0)
            {
                KvUt = TbParFilteraVer9[broj].K_V;
            }

            double utth = (double)m_ParFilteraCF.UTHMIN;


            double uthmin = utth / (double)KvUt;

            uthmin = uthmin * 1.002;
            double utlmax = (double)(m_ParFilteraCF.UTLMAX) / (double)KvUt;
            utlmax = utlmax	 * 1.002;


            double UthminRef;
            int ith = GetFrUTHDefVer9(broj);
            if (ith!=0)
            {
                UthminRef = (double)ith;
                m_Utf_posto = (utth * (double)UTFREFP) / UthminRef;
            }
            else m_Utf_posto = 0;


        }
    }
    private  int GetFrUTHDefVer9(int broj){
        int Uth;
        if(broj>=0) {
            Uth = TbParFilteraVer9[broj].UTHMIN;
        }else {
            Uth = 0;
        }
        return Uth;
    }

    private void GetOprijParV9(Mgaddr m_gaddr,  Oprij m_opPrij, final Oprij50 m_op50Prij,  byte[] m_pbuf, Rreallc[] m_Realloc){
        switch (m_gaddr.objectt)
        {
            case 0:

               if (m_SWVerPri >= 96) GetKlDatVer96(m_op50Prij,m_opPrij,m_pbuf,m_Realloc);
              //  else pFrameWnd->GetKlDatVer9();
                break;
            case 1:
                if(m_SWVerPri >= 96) {
                    GetKl2VerDatVer96(m_opPrij,m_op50Prij,m_pbuf);
                }
                else {
                    //pFrameWnd->GetKl2VerDatVer9();
                }
                break;
            case 2:
                GetDaljPar(m_opPrij,m_pbuf);		//iz GetPg2Par();
                break;
            case 3:
  //              pFrameWnd->GetKl2SPNDatVer9();		// iz  GetKl2VerDatVer9() i GetPg2Par();
                break;


            default:
                break;
        }
    }
    private void GetDaljPar(Oprij m_opPrij,byte[] m_pbuf){
        m_opPrij.VOpRe.VAkProR1=SetOprelI(m_pbuf);
        m_opPrij.VOpRe.VAkProR2=SetOprelI(m_pbuf);
        m_opPrij.VOpRe.VAkProR3=SetOprelI(m_pbuf);
        m_opPrij.VOpRe.VAkProR4=SetOprelI(m_pbuf);

        m_opPrij.VOpRe.StaPrij=m_pbuf[globalIndex++];


        if(m_SWVerPri < 95)
        {
            m_opPrij.ParFlags=m_pbuf[globalIndex++];

            m_opPrij.StaR1PwON_OFF=m_pbuf[globalIndex++];
            m_opPrij.StaR2PwON_OFF=m_pbuf[globalIndex++];
            m_opPrij.StaR3PwON_OFF=m_pbuf[globalIndex++];
            m_opPrij.StaR4PwON_OFF=m_pbuf[globalIndex++];
        }
        else	{

            m_opPrij.ParFlags=0;

            m_opPrij.StaR1PwON_OFF=0;
            m_opPrij.StaR2PwON_OFF=0;
            m_opPrij.StaR3PwON_OFF=0;
            m_opPrij.StaR4PwON_OFF=0;
        }

    }

    private void GetKl2VerDatVer96(Oprij m_opPrij,Oprij50 m_op50Prij,byte[] m_pbuf){
        boolean incCik=false;
        if ((m_CFG.cID == 100) || (m_CFG.cID == 120)){
            incCik = true;
        }
        m_opPrij.Dly24H = 0;

        if (incCik){
            m_opPrij.VDuzAdr=m_pbuf[globalIndex++];
        }
        else{
            m_opPrij.VDuzAdr = 0;
        }
        m_opPrij.PolUKRe =m_pbuf[globalIndex++];


        if (m_CFG.cID == 100){
            m_opPrij.VIdBr =m_pbuf[globalIndex++];
        }
        m_op50Prij.RTCSinh =m_pbuf[globalIndex++];
        if (incCik){
            m_op50Prij.WDaySinh =m_pbuf[globalIndex++];
        }else{
            m_op50Prij.WDaySinh = 0;
        }


        if (m_CFG.cID == 100) {
		    globalIndex++;
            globalIndex++;
            globalIndex++;
		} else if( (m_CFG.cID == 130) || (m_CFG.cID == 0x8C)) {
            globalIndex++;
            globalIndex++;

        }



        for (int uIndex = 0; uIndex <5; uIndex++) {
            m_op50Prij.SinhTime[uIndex] = SetOprel4I(m_pbuf);
        }

        if (!incCik){
            return;
        }

        m_opPrij.CRelXSw[0] =m_pbuf[globalIndex++];
        m_opPrij.VCRel1Tu =m_pbuf[globalIndex++];
        m_opPrij.VC1R1 = SetOprelI(m_pbuf);
        m_opPrij.CRelXSw[1] =m_pbuf[globalIndex++];
        m_opPrij.VCRel2Tu =m_pbuf[globalIndex++];
        m_opPrij.VC1R2 = SetOprelI(m_pbuf);
        m_opPrij.CRelXSw[2] =m_pbuf[globalIndex++];
        m_opPrij.VCRel3Tu =m_pbuf[globalIndex++];
        m_opPrij.VC1R3 = SetOprelI(m_pbuf);
        m_opPrij.CRelXSw[3] =m_pbuf[globalIndex++];
        m_opPrij.VCRel4Tu =m_pbuf[globalIndex++];
        m_opPrij.VC1R4 = SetOprelI(m_pbuf);

        if ((m_CFG.cID == 120) || (m_HWVerPri == TIP_PS)){
            return;
        }

        SetVerAdrVer9(m_opPrij.VAdrR1,m_pbuf);
        SetVerAdrVer9(m_opPrij.VAdrR2,m_pbuf);
        SetVerAdrVer9(m_opPrij.VAdrR3,m_pbuf);
        SetVerAdrVer9(m_opPrij.VAdrR4,m_pbuf);

    }

    private int  SetOprel4I(byte[] m_pbuf){
        Uni4byt tempi=new Uni4byt();
        tempi.i=0;
        tempi.b[3]=m_pbuf[globalIndex++];
        tempi.b[2]=m_pbuf[globalIndex++];
        tempi.b[1]=m_pbuf[globalIndex++];
        tempi.b[0]=m_pbuf[globalIndex++];
        tempi.update();
        return tempi.i;
    }

    private void SetVerAdrVer9(Vadrr vadrRx,byte[] m_pbuf){
        byte adrxx;
        adrxx=m_pbuf[globalIndex++];
        vadrRx.VAdrRA=(adrxx==0)	? 0	:GetAdrNr(adrxx);
        vadrRx.VAdrRB=m_pbuf[globalIndex++];
        vadrRx.VAdrRC=m_pbuf[globalIndex++];
        vadrRx.VAdrRD=m_pbuf[globalIndex++];

    }

    private byte GetAdrNr(byte xxadr){
        byte i;
        for(i=0;i<8;i++){
            if(xxadr==bVtmask[i])
            {
                return (byte)(++i);
            }
        }
        return((byte)0);
    }


    private void GetKlDatVer96(Oprij50 m_op50Prij,Oprij m_opPrij,byte[] m_pbuf,Rreallc[] m_Realloc){

        GetKlDatVer9(m_opPrij,m_pbuf,m_Realloc);
        m_op50Prij.CPWBRTIME = SetOprelI(m_pbuf);
        m_op50Prij.CLOGENFLGS[0] = SetOprelI(m_pbuf);
        m_op50Prij.CLOGENFLGS[1] = SetOprelI(m_pbuf);
        m_op50Prij.CLOGENFLGS[2] = SetOprelI(m_pbuf);
    }

    private void GetKlDatVer9(Oprij m_opPrij,byte[] m_pbuf,Rreallc[] m_Realloc){
        SetDlyRelDV9(m_opPrij.KlOpR1,m_pbuf);
        SetDlyRelDV9(m_opPrij.KlOpR2,m_pbuf);
        SetDlyRelDV9(m_opPrij.KlOpR3,m_pbuf);
        SetDlyRelDV9(m_opPrij.KlOpR4,m_pbuf);
        for (int uIndex = 0; uIndex <4; uIndex++)
        {
            m_Realloc[uIndex].rel_on = m_pbuf[globalIndex++];
            m_Realloc[uIndex].rel_of = m_pbuf[globalIndex++];

        }
    }
    private void SetDlyRelDV9(Klopr Relx,byte[] m_pbuf){
        Relx.KRelDela=SetOprel4I(m_pbuf);
        Relx.KRelDelb=SetOprel4I(m_pbuf);

    }
}









































