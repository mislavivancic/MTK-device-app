package hr.fer.ruazosa.bluetooth;


import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;


public class SecondActivity extends AppCompatActivity {


    private int SNE_POFF = 0x0001;
    private int SNE_PON = 0x0002;
    private int SNE_RTC_ST = 0x0004;
    private int SNE_RTC_OF = 0x0008;
    private int SNE_SHT = 0x0010;    //
    private int SNE_SHD = 0x0020;    //
    private int SNE_RTC_BL = 0x0040;    //
    private int SNE_RTC_OOK = 0x0080;
    private int SNE_LSINH = 0x0100;  //
    private int SNE_WPAROK = 0x0400; //
    private int SNE_WPARERR = 0x0800; //

    private int OPT_LOG_MYTLG = 0x4000;
    private int OPT_LOG_TLG = 0x8000;
    private int OPT_LOG_REPTLG = 0x2000;

    private int REL_ON = 0x0001;
    private int REL_OFF = 0x0002;
    private int REL_PROBLOCK = 0x0004;
    private int REL_PROUNBLOCK = 0x0008;
    private int REL_WIP_S = 0x0010;
    private int REL_WIP_R = 0x0020;
    private int REL_TA_S = 0x0040;
    private int REL_TA_R = 0x0080;
    private int PRO_REL_X = 0x0100;
    private int TEL_REL_X = 0x0200;
    private int CLP_REL_X = 0x0400;
    private int PON_REL_X = 0x0800;

    private int PRO_REL_ON = 0x0101;
    private int PRO_REL_OFF = 0x0102;

    private final int WIPER_DISEB_MASK = 0x80;
    private final int WIPPER_RETRIG_MASK = 0x01;
    private final int WIPER_ON_MASK = 0x40;
    private final int WIPER_OFF_MASK = 0x20;
    private final int LOOP_DISEB_MASK = 0x08;
    private final int PON_DISLRN_I_W_MASK = 0x80;
    private final int PON_LPERIOD_DIS_MASK = 0x80;
    private final int TLGA_ON_DISLRN = 0x80;
    private final int LEARN_7DAYS_MASK = 0x10;
    private final int LEARN_R_ON_MASK = 0x40;
    private final int LEARN_R_OFF_MASK = 0x20;

    private final int TIM_LOSS_RTC_POS = 0x02;
    private final int SINH_REL_POS_MASK = 0x40;

    private int cnt1 = 0;
    private int cnt2 = 0;
    private int cnt3 = 0;

    private int cntWork1 = 0;
    private int cntWork2 = 0;
    private int cntWork3 = 0;
    //"-S ","-SN ","-SPA ","-PAS ","-PA ","-PASN ","-SPN ","-PS ",0
    private final String[] CTipPrij = {"-S ", "-SN ", "-SPA ", "-PAS ", "-PA ", "-PASN ", "-SPN ", "-PS "};
    private String MTKVerStr;

    private Str_Parfil_Ver9[] TbParFilteraVer9 = {
            new Str_Parfil_Ver9((byte) 21, (byte) 21, 0x0A20, 0, 1800, 1520, 0x0492, 0x0101, 0, 175.00),
            new Str_Parfil_Ver9((byte) 22, (byte) 22, 0x0730, 0, 2650, 2450, 0x045D, 0x0101, 1, 183.3333333)
            , new Str_Parfil_Ver9((byte) 23, (byte) 23, 0x058A, 0, 2150, 2000, 0x042D, 0x0101, 2, 194.00),
            new Str_Parfil_Ver9((byte) 25, (byte) 25, 0x0920, 0, 2430, 2375, 0x03D7, 0x0101, 3, 208.3333333),
            new Str_Parfil_Ver9((byte) 26, (byte) 26, 0x0A40, 0, 3800, 3600, 0x03B1, 0x0101, 4, 216.6666667),
            new Str_Parfil_Ver9((byte) 27, (byte) 27, 0x0A20, 0, 1800, 1520, 0x038E, 0x0102, 5, 225),
            new Str_Parfil_Ver9((byte) 28, (byte) 28, 0x0A20, 0, 1800, 1520, 0x036E, 0x0102, 6, 233.3333333),
            new Str_Parfil_Ver9((byte) 32, (byte) 16, 0x0A20, 0, 1800, 1520, 0x0300, 0x0102, 7, 266.6666667),
            new Str_Parfil_Ver9((byte) 34, (byte) 34, 0x0DE0, 0, 5000, 4860, 0x02D3, 0x0101, 8, 283.3333333),
            new Str_Parfil_Ver9((byte) 38, (byte) 38, 0x0C20, 0, 2520, 2400, 0x0287, 0x0002, 9, 316.66666670),
            new Str_Parfil_Ver9((byte) 20, (byte) 20, 0x0A20, 0, 1800, 1520, 0x0266, 0x0102, 10, 333.33333331),
            new Str_Parfil_Ver9((byte) 22, (byte) 22, 0x0A20, 0, 1800, 1520, 0x022F, 0x0102, 11, 366.66666672),
            new Str_Parfil_Ver9((byte) 23, (byte) 23, 0x0A20, 0, 1800, 1520, 0x0216, 0x0102, 12, 383.33333333),
            new Str_Parfil_Ver9((byte) 25, (byte) 25, 0x0A20, 0, 2880, 2700, 0x01EC, 0x0100, 13, 416.66666674),
            new Str_Parfil_Ver9((byte) 25, (byte) 25, 0x0A20, 0, 2790, 2700, 0x01EB, 0x0100, 14, 420.00),
            new Str_Parfil_Ver9((byte) 26, (byte) 26, 0x0A20, 0, 1800, 1520, 0x01D9, 0x0100, 15, 433.33333336),


    };

    private Str_Parfil[] TbParFiltera = {
            new Str_Parfil((byte) 21, (byte) 21, 0, 0, 0x120, 0x80, 0xDB7, (byte) 2, 2, 175.00),
            new Str_Parfil((byte) 22, (byte) 22, 0, 0, 0x120, 0x80, 0x0D17, (byte) 2, 1, 183.3333333),
            new Str_Parfil((byte) 23, (byte) 23, 0, 0, 0x120, 0x80, 0x0C86, (byte) 2, 2, 191.6666667),
            new Str_Parfil((byte) 25, (byte) 25, 0, 0, 0x116, 0xF2, 0x0B85, (byte) 2, 3, 208.3333333),
            new Str_Parfil((byte) 26, (byte) 26, 0, 0, 0x116, 0xF2, 0x0B13, (byte) 2, 4, 216.6666667),
            new Str_Parfil((byte) 27, (byte) 27, 0, 0, 0x120, 0x80, 0x0AAB, (byte) 2, 5, 225),
            new Str_Parfil((byte) 28, (byte) 28, 0, 0, 0x120, 0x80, 0x0A49, (byte) 2, 6, 233.3333333),
            new Str_Parfil((byte) 32, (byte) 16, 0, 0, 0x120, 0x80, 0x0900, (byte) 2, 7, 266.6666667),
            new Str_Parfil((byte) 34, (byte) 17, 0, 0, 0x108, 0xB0, 0x0878, (byte) 2, 8, 283.3333333),
            new Str_Parfil((byte) 38, (byte) 19, 0, 0, 0x108, 0xB0, 0x0794, (byte) 2, 9, 316.6666667),
            new Str_Parfil((byte) 20, (byte) 20, 0, 0, 0x120, 0x80, 0x0733, (byte) 2, 10, 333.3333333),
            new Str_Parfil((byte) 22, (byte) 22, 0, 0, 0x120, 0x80, 0x068C, (byte) 2, 11, 366.6666667),
            new Str_Parfil((byte) 23, (byte) 23, 0, 0, 0x120, 0x80, 0x0643, (byte) 2, 12, 383.3333333),
            new Str_Parfil((byte) 25, (byte) 25, 0, 0, 0x120, 0x80, 0x05C2, (byte) 3, 13, 416.6666667),
            new Str_Parfil((byte) 25, (byte) 25, 0, 0, 0x136, 0xF6, 0x05C2, (byte) 3, 14, 420.00),
            new Str_Parfil((byte) 26, (byte) 26, 0, 0, 0x120, 0x80, 0x058A, (byte) 3, 15, 433.3333333)


    };

    private Str_Parfil[] TbParFiltera9_8MHz = {
            new Str_Parfil((byte) 21, (byte) 21, 0, 0, 0x120, 0x80, 0x1249, (byte) 2, 2, 175.00),
            new Str_Parfil((byte) 22, (byte) 22, 0, 0, 0x120, 0x80, 0x1174, (byte) 2, 1, 183.3333333),
            new Str_Parfil((byte) 23, (byte) 23, 0, 0, 0x120, 0x80, 0x10B2, (byte) 2, 2, 191.6666667),
            new Str_Parfil((byte) 25, (byte) 25, 0, 0, 0x116, 0xF2, 0x0F5C, (byte) 2, 3, 208.3333333),
            new Str_Parfil((byte) 26, (byte) 26, 0, 0, 0x116, 0xF2, 0x0EC5, (byte) 2, 4, 216.6666667),
            new Str_Parfil((byte) 27, (byte) 27, 0, 0, 0x120, 0x80, 0x0E39, (byte) 2, 5, 225),
            new Str_Parfil((byte) 28, (byte) 28, 0, 0, 0x120, 0x80, 0x0DB7, (byte) 2, 6, 233.3333333),
            new Str_Parfil((byte) 32, (byte) 16, 0, 0, 0x120, 0x80, 0x0C00, (byte) 2, 7, 266.6666667),
            new Str_Parfil((byte) 34, (byte) 17, 0, 0, 0x108, 0xB0, 0x0B4B, (byte) 2, 8, 283.3333333),
            new Str_Parfil((byte) 38, (byte) 19, 0, 0, 0x108, 0xB0, 0x0A1B, (byte) 2, 9, 316.6666667),
            new Str_Parfil((byte) 20, (byte) 20, 0, 0, 0x120, 0x80, 0x099A, (byte) 2, 10, 333.3333333),
            new Str_Parfil((byte) 22, (byte) 22, 0, 0, 0x120, 0x80, 0x08BA, (byte) 2, 11, 366.6666667),
            new Str_Parfil((byte) 23, (byte) 23, 0, 0, 0x120, 0x80, 0x0859, (byte) 2, 12, 383.3333333),
            new Str_Parfil((byte) 25, (byte) 25, 0, 0, 0x120, 0x80, 0x07AE, (byte) 3, 13, 416.6666667),
            new Str_Parfil((byte) 25, (byte) 25, 0, 0, 0x136, 0xF6, 0x079E, (byte) 3, 14, 420.00),
            new Str_Parfil((byte) 26, (byte) 26, 0, 0, 0x120, 0x80, 0x0762, (byte) 3, 15, 433.3333333)


    };


    private LadderNets[] pLCfg = {
            //	RelState 		RelNr 			isSeries 		notsernotpar 	conectshort
            new LadderNets(1, new byte[]{00, 00, 00, 00}, new byte[]{00, 00, 00, 00}, new boolean[]{true, true, true, true}, new boolean[]{true, true, true, true}, new boolean[]{false, false, false, false}),    //----------------------
            new LadderNets(2, new byte[]{15, 00, 00, 00}, new byte[]{15, 00, 00, 00}, new boolean[]{true, false, false, false}, new boolean[]{true, false, false, false}, new boolean[]{false, false, false, false}),    // ----| |--------------
            new LadderNets(3, new byte[]{00, 00, 15, 00}, new byte[]{00, 00, 15, 00}, new boolean[]{false, false, true, false}, new boolean[]{false, false, true, false}, new boolean[]{false, false, false, false}),    //---------------| |---
            new LadderNets(4, new byte[]{15, 15, 00, 00}, new byte[]{15, 15, 00, 00}, new boolean[]{false, false, true, false}, new boolean[]{true, false, true, false}, new boolean[]{false, false, false, false}),    //   r00 || r01
            new LadderNets(5, new byte[]{00, 00, 15, 15}, new byte[]{00, 00, 15, 15}, new boolean[]{true, false, false, false}, new boolean[]{true, false, true, false}, new boolean[]{false, false, false, false}),    //   r10 || r11
            new LadderNets(6, new byte[]{15, 00, 15, 00}, new byte[]{15, 00, 15, 00}, new boolean[]{true, false, true, false}, new boolean[]{true, false, true, false}, new boolean[]{false, false, false, false}),    //   r00 &  r10
            new LadderNets(7, new byte[]{15, 15, 15, 00}, new byte[]{15, 15, 15, 00}, new boolean[]{false, false, true, false}, new boolean[]{true, false, true, false}, new boolean[]{false, false, false, false}),    //  (r00 || r01)  &  r10
            new LadderNets(8, new byte[]{15, 00, 15, 15}, new byte[]{15, 00, 15, 15}, new boolean[]{true, false, false, false}, new boolean[]{true, false, true, false}, new boolean[]{false, false, false, false}),    //   r00 & (r10 || r11)
            new LadderNets(9, new byte[]{15, 00, 15, 15}, new byte[]{15, 00, 15, 15}, new boolean[]{true, false, true, true}, new boolean[]{false, false, false, false}, new boolean[]{false, true, false, false}),    //	(r00 & r01)  & r11
            new LadderNets(10, new byte[]{15, 15, 15, 00}, new byte[]{15, 15, 15, 00}, new boolean[]{true, true, true, false}, new boolean[]{false, false, false, false}, new boolean[]{false, false, false, true}),    //	(r00 & r01)  & r01
            new LadderNets(13, new byte[]{15, 15, 15, 15}, new byte[]{15, 15, 15, 15}, new boolean[]{true, true, true, true}, new boolean[]{false, false, false, false}, new boolean[]{false, false, false, false}),    //	(r00 &  r10 ) || (r10  &  r11)
            new LadderNets(14, new byte[]{15, 15, 15, 15}, new byte[]{15, 15, 15, 15}, new boolean[]{false, false, false, false}, new boolean[]{false, false, false, false}, new boolean[]{false, false, false, false}),    //  (r00 ||r10 )   & (r10 || r11)

    };

    private LadderState[] LS = new LadderState[8];

    private final int TIP_S = 0;
    private final int TIP_SN = 1;
    private final int TIP_SPA = 2;
    private final int TIP_PAS = 3;
    private final int TIP_PA = 4;
    private final int TIP_PASN = 5;
    private final int TIP_SPN = 6;
    private final int TIP_PS = 7;


    boolean fVis_VersacomPS;
    boolean fVis_Versacom;
    boolean fVis_Uklsat;
    boolean fVis_Prazdani;
    boolean fVis_Sezone;
    boolean fVis_Asat;
    boolean fVis_RefPrij;
    boolean fVis_TBAS;
    boolean fVis_DUZADR;
    boolean fVis_Realoc;
    boolean fVis_Cz95P;
    boolean fVis_Cz96P;


    boolean IsCZRaster = false;
    boolean IsCZ44raster = false;


    private int m_SWVerPri;
    private int m_HWVerPri;

    private final int m_tip = 1;


    private final String[] PoffS = {"None", "a", "b", "a with delayed sched.", "b with delayed sched.", "As before power loss", "Switch to work sched.", "Enable work sched.", "Disable work sched.", "Cyclic switching"};
    private final String[] PoffL = {"None", "a", "b", "a with delayed sched.", "b with delayed sched.", "As before power loss"};
    private final String[] PoffAct = {"None", "a", "b"};
    private final String[] rst = {"----", "valid start \nimpulse", "valid telegram", "telegram for this receiver", "telegram for this relay"};
    private final String[] act = {"---", "a", "b", "Enable work schedule", "Disable work schedule", "Switch to work sched.", "Switch to loop schedule"};
    private final int[] iVtmask = {0x8000, 0x4000, 0x2000, 0x1000, 0x0800, 0x0400, 0x0200, 0x0100, 0x0080, 0x0040, 0x0020, 0x0010, 0x0008, 0x0004, 0x0002, 0x0001};
    private final int[] bVtmask = {0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01};

    private final String[] schDays = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun", "Ho."};


    TextView[] wipEnable;
    TextView[] retrig;
    TextView[] actComm;
    TextView[] wipTime;
    TextView[] schedSwitActDelay;
    TextView[] loopEnbl;
    TextView[] durationInPos;
    TextView[] switchDelay;

    //Arrival and loss of supply
    TextView[] stopLearn;
    TextView[] powerSupply;
    TextView[] lossOfSupplyShort;
    TextView[] lossOfSupplyLongIgn;
    TextView[] lossOfSupplyLong;
    TextView[] switchDelayMin;
    TextView[] switchDelayMax;
    TextView[] schedSwitchActDel;
    TextView[] lossOfSupplyAction;


    TextView[] absenceTime;
    TextView[] resetTimer;
    TextView[] onTimeRestartEvent;
    TextView[] action;
    TextView[] learnFunctionDisabled;


    //Learn functions
    TextView[] learnPeriod;
    TextView[] position;
    TextView[] minTV;
    TextView[] maxTV;


    TextView[][] workSchedTime1;
    TextView[][] workSchedTime2;
    TextView[][] workSchedTime3;

    TextView[][] timePair1;
    TextView[][] timePair2;
    TextView[][] timePair3;


    TextView[][] Ta_test1;
    TextView[][] Ta_test2;
    TextView[][] Ta_test3;


    TextView[][] Tb_test1;
    TextView[][] Tb_test2;
    TextView[][] Tb_test3;

    TableRow[] workTB1;
    TableRow[] workTB2;
    TableRow[] workTB3;

    TextView[] relayInstalled;
    TextView[] invertedLogic;

    //TableRow.LayoutParams params;

    TableLayout tableLayout;


    //Data needed

    //List<Character> input=new LinkedList<>();

    Wiper[] wiper;
    PonPoffStr[] ponPoffStrs;
    TlgAbstr[] tlgAbstrs;
    StrLoadMng[] strLoadMngs;


    List<Character> MtkVer = new LinkedList<>();

    Opprog[] m_PProg_R1;
    Opprog[] m_PProg_R2;
    Opprog[] m_PProg_R3;
    Opprog[] m_PProg_R4;

    byte[] input;
    Data data;
    byte[] MTKVER;


    Str_Parfil_Ver9 m_ParFilteraCF = new Str_Parfil_Ver9();
    Str_Parfil m_ParFiltera = new Str_Parfil();

    Cfg_Par_Hwsw m_CFG = new Cfg_Par_Hwsw();

    Oprij50 m_op50Prij = new Oprij50();

    Rreallc[] m_Realloc = new Rreallc[4];
    Oprij oprij = new Oprij();


    Telegram[] m_TelegSync = new Telegram[5];
    Telegram[] m_TlgFnD = new Telegram[8];

    IntrlockStr[] m_RelInterLock = new IntrlockStr[8];

    //private TextView txtviewExample = new TextView(this);

    TableRow.LayoutParams paramsExample = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tableLayout = findViewById(R.id.table_layout);
        // paramsExample.gravity= Gravity.FILL_HORIZONTAL;
        paramsExample.setMargins(2, 1, 1, 1);


        wiper = new Wiper[4];
        wiper[0] = new Wiper();
        wiper[1] = new Wiper();
        wiper[2] = new Wiper();
        wiper[3] = new Wiper();

        ponPoffStrs = new PonPoffStr[4];
        ponPoffStrs[0] = new PonPoffStr();
        ponPoffStrs[1] = new PonPoffStr();
        ponPoffStrs[2] = new PonPoffStr();
        ponPoffStrs[3] = new PonPoffStr();

        tlgAbstrs = new TlgAbstr[4];
        tlgAbstrs[0] = new TlgAbstr();
        tlgAbstrs[1] = new TlgAbstr();
        tlgAbstrs[2] = new TlgAbstr();
        tlgAbstrs[3] = new TlgAbstr();

        strLoadMngs = new StrLoadMng[4];
        strLoadMngs[0] = new StrLoadMng();
        strLoadMngs[1] = new StrLoadMng();
        strLoadMngs[2] = new StrLoadMng();
        strLoadMngs[3] = new StrLoadMng();

        m_RelInterLock[0] = new IntrlockStr();
        m_RelInterLock[1] = new IntrlockStr();
        m_RelInterLock[2] = new IntrlockStr();
        m_RelInterLock[3] = new IntrlockStr();
        m_RelInterLock[4] = new IntrlockStr();
        m_RelInterLock[5] = new IntrlockStr();
        m_RelInterLock[6] = new IntrlockStr();
        m_RelInterLock[7] = new IntrlockStr();


        m_PProg_R1 = new Opprog[16];
        m_PProg_R2 = new Opprog[16];
        m_PProg_R3 = new Opprog[16];
        m_PProg_R4 = new Opprog[16];

        for (int i = 0; i < 16; i++) {
            m_PProg_R1[i] = new Opprog();
            for (int j = 0; j < 14; j++) {
                m_PProg_R1[i].Tpro[j] = new Tonoff();
            }
        }
        for (int i = 0; i < 16; i++) {
            m_PProg_R2[i] = new Opprog();
            for (int j = 0; j < 14; j++) {
                m_PProg_R2[i].Tpro[j] = new Tonoff();
            }
        }
        for (int i = 0; i < 16; i++) {
            m_PProg_R3[i] = new Opprog();
            for (int j = 0; j < 14; j++) {
                m_PProg_R3[i].Tpro[j] = new Tonoff();
            }
        }
        for (int i = 0; i < 16; i++) {
            m_PProg_R4[i] = new Opprog();
            for (int j = 0; j < 14; j++) {
                m_PProg_R4[i].Tpro[j] = new Tonoff();
            }
        }

        input = getIntent().getByteArrayExtra("data");


        Oprij tempOprij = new Oprij();

        oprij.init();

        for (int i = 0; i < 8; i++) {
            LS[i] = new LadderState();
        }

        m_Realloc[0] = new Rreallc();
        m_Realloc[1] = new Rreallc();
        m_Realloc[2] = new Rreallc();
        m_Realloc[3] = new Rreallc();


        for (int i = 0; i < 5; i++) {
            m_TelegSync[i] = new Telegram();
        }
        for (int i = 0; i < 8; i++) {
            m_TlgFnD[i] = new Telegram();
        }

        MTKVER = getIntent().getByteArrayExtra("MTKVer");

        for (int i = 0; i < MTKVER.length - 1; i++) {
            MtkVer.add(Character.valueOf((char) MTKVER[i]));
        }
        MTKVerStr = getStringRepresentation(MtkVer);
        GetVerPri(MtkVer, MTKVerStr);

        data = new Data(input, m_SWVerPri, m_CFG, m_HWVerPri);
        data.processData(wiper, ponPoffStrs, tlgAbstrs, strLoadMngs, m_PProg_R1, m_PProg_R2, m_PProg_R3, m_PProg_R4, m_ParFilteraCF,
                m_ParFiltera, oprij, m_op50Prij, m_Realloc, m_TelegSync, m_TlgFnD, m_RelInterLock);


        //


        System.out.print("a");


        fVis_VersacomPS = (m_HWVerPri != TIP_PS);
        fVis_Versacom = ((m_HWVerPri != TIP_S) && (m_HWVerPri != TIP_SN) && (m_HWVerPri != TIP_SPN));
        fVis_Uklsat = ((m_HWVerPri) == TIP_SPA) || ((m_HWVerPri) == TIP_S) || ((m_HWVerPri) == TIP_SN) || ((m_HWVerPri) == TIP_SPN);
        fVis_Prazdani = (((m_SWVerPri) >= 94) && ((m_HWVerPri == TIP_S) || (m_HWVerPri == TIP_SN) || (m_HWVerPri == TIP_SPN)));
        fVis_Sezone = (fVis_Versacom && ((m_HWVerPri) != TIP_PA)) || (fVis_Uklsat && (m_SWVerPri >= 95));
        fVis_Sezone = fVis_Sezone && (m_SWVerPri >= 80) && (m_HWVerPri != TIP_PS);
        fVis_Asat = ((m_HWVerPri == TIP_PASN) || (m_HWVerPri == TIP_SN) || (m_HWVerPri == TIP_SPN));
        fVis_RefPrij = ((m_HWVerPri != TIP_S) && (m_HWVerPri != TIP_SN));
        fVis_TBAS = (m_HWVerPri != TIP_PA);
        fVis_DUZADR = (m_HWVerPri != TIP_SPN);
        fVis_Realoc = ((m_SWVerPri) >= 82);

        fVis_Cz95P = ((m_SWVerPri) >= 95);
        fVis_Cz96P = ((m_SWVerPri) >= 96);

        System.out.print("a");
   /*
        Wiper[] wiper=new Wiper[4];
        Wiper wiper1;
        Wiper wiper2;
        Wiper wiper3;
        Wiper wiper4;

        PonPoffStr[] ponPoffStr=new PonPoffStr[4];
        PonPoffStr ponPoffStr1;
        PonPoffStr ponPoffStr2;
        PonPoffStr ponPoffStr3;
        PonPoffStr ponPoffStr4;

        TlgAbstr[] tlgAbstr=new TlgAbstr[4];
        TlgAbstr tlgAbstr1;
        TlgAbstr tlgAbstr2;
        TlgAbstr tlgAbstr3;
        TlgAbstr tlgAbstr4;

        StrLoadMng[] strLoadMng=new StrLoadMng[4];
        StrLoadMng strLoadMng1;
        StrLoadMng strLoadMng2;
        StrLoadMng strLoadMng3;
        StrLoadMng strLoadMng4;




        Opprog[] m_PProg_R1=new Opprog[16];
        Opprog[] m_PProg_R2=new Opprog[16];
        Opprog[] m_PProg_R3=new Opprog[16];
        Opprog[] m_PProg_R4=new Opprog[16];

        Parcelable[] in1;
        Parcelable[] in2;
        Parcelable[] in3;
        Parcelable[] in4;

*/
// .setBackgroundResource(R.color.whiteColor);
// .setLayoutParams(paramsExample);

        //-------INICIJALIZACIJA TEXTVIEW-------
        wipEnable = new TextView[4];
        wipEnable[0] = findViewById(R.id.WiperEnable1);
        wipEnable[0].setBackgroundResource(R.color.whiteColor);
        wipEnable[0].setLayoutParams(paramsExample);
        wipEnable[1] = findViewById(R.id.WiperEnable2);
        wipEnable[1].setBackgroundResource(R.color.whiteColor);
        wipEnable[1].setLayoutParams(paramsExample);
        wipEnable[2] = findViewById(R.id.WiperEnable3);
        wipEnable[2].setBackgroundResource(R.color.whiteColor);
        wipEnable[2].setLayoutParams(paramsExample);
        wipEnable[3] = findViewById(R.id.WiperEnable4);
        wipEnable[3].setBackgroundResource(R.color.whiteColor);
        wipEnable[3].setLayoutParams(paramsExample);


        retrig = new TextView[4];
        retrig[0] = findViewById(R.id.Retrig1);
        retrig[1] = findViewById(R.id.Retrig2);
        retrig[2] = findViewById(R.id.Retrig3);
        retrig[3] = findViewById(R.id.Retrig4);

        retrig[0].setBackgroundResource(R.color.whiteColor);
        retrig[1].setBackgroundResource(R.color.whiteColor);
        retrig[2].setBackgroundResource(R.color.whiteColor);
        retrig[3].setBackgroundResource(R.color.whiteColor);

        retrig[0].setLayoutParams(paramsExample);
        retrig[1].setLayoutParams(paramsExample);
        retrig[2].setLayoutParams(paramsExample);
        retrig[3].setLayoutParams(paramsExample);


        actComm = new TextView[4];
        actComm[0] = findViewById(R.id.ActComm1);
        actComm[1] = findViewById(R.id.ActComm2);
        actComm[2] = findViewById(R.id.ActComm3);
        actComm[3] = findViewById(R.id.ActComm4);

        wipTime = new TextView[4];
        wipTime[0] = findViewById(R.id.WiperTime1);
        wipTime[1] = findViewById(R.id.WiperTime2);
        wipTime[2] = findViewById(R.id.WiperTime3);
        wipTime[3] = findViewById(R.id.WiperTime4);

        schedSwitActDelay = new TextView[4];
        schedSwitActDelay[0] = findViewById(R.id.Sched1);
        schedSwitActDelay[1] = findViewById(R.id.Sched2);
        schedSwitActDelay[2] = findViewById(R.id.Sched3);
        schedSwitActDelay[3] = findViewById(R.id.Sched4);

        loopEnbl = new TextView[4];
        loopEnbl[0] = findViewById(R.id.Loop1);
        loopEnbl[1] = findViewById(R.id.Loop2);
        loopEnbl[2] = findViewById(R.id.Loop3);
        loopEnbl[3] = findViewById(R.id.Loop4);

        durationInPos = new TextView[4];
        durationInPos[0] = findViewById(R.id.Duration1);
        durationInPos[1] = findViewById(R.id.Duration2);
        durationInPos[2] = findViewById(R.id.Duration3);
        durationInPos[3] = findViewById(R.id.Duration4);

        switchDelay = new TextView[4];
        switchDelay[0] = findViewById(R.id.Switch1);
        switchDelay[1] = findViewById(R.id.Switch2);
        switchDelay[2] = findViewById(R.id.Switch3);
        switchDelay[3] = findViewById(R.id.Switch4);

        //Arrival and learn


        stopLearn = new TextView[4];
        stopLearn[0] = findViewById(R.id.StopRelay1);
        stopLearn[1] = findViewById(R.id.StopRelay2);
        stopLearn[2] = findViewById(R.id.StopRelay3);
        stopLearn[3] = findViewById(R.id.StopRelay4);

        powerSupply = new TextView[4];
        powerSupply[0] = findViewById(R.id.PowerSupplyRelay1);
        powerSupply[1] = findViewById(R.id.PowerSupplyRelay2);
        powerSupply[2] = findViewById(R.id.PowerSupplyRelay3);
        powerSupply[3] = findViewById(R.id.PowerSupplyRelay4);

        lossOfSupplyShort = new TextView[4];
        lossOfSupplyShort[0] = findViewById(R.id.LossShortRelay1);
        lossOfSupplyShort[1] = findViewById(R.id.LossShortRelay2);
        lossOfSupplyShort[2] = findViewById(R.id.LossShortRelay3);
        lossOfSupplyShort[3] = findViewById(R.id.LossShortRelay4);

        lossOfSupplyLongIgn = new TextView[4];
        lossOfSupplyLongIgn[0] = findViewById(R.id.LossLongIgnRelay1);
        lossOfSupplyLongIgn[1] = findViewById(R.id.LossLongIgnRelay2);
        lossOfSupplyLongIgn[2] = findViewById(R.id.LossLongIgnRelay3);
        lossOfSupplyLongIgn[3] = findViewById(R.id.LossLongIgnRelay4);

        lossOfSupplyLong = new TextView[4];
        lossOfSupplyLong[0] = findViewById(R.id.LossLongRelay1);
        lossOfSupplyLong[1] = findViewById(R.id.LossLongRelay2);
        lossOfSupplyLong[2] = findViewById(R.id.LossLongRelay3);
        lossOfSupplyLong[3] = findViewById(R.id.LossLongRelay4);

        switchDelayMin = new TextView[4];
        switchDelayMin[0] = findViewById(R.id.SwitchDelMinRelay1);
        switchDelayMin[1] = findViewById(R.id.SwitchDelMinRelay2);
        switchDelayMin[2] = findViewById(R.id.SwitchDelMinRelay3);
        switchDelayMin[3] = findViewById(R.id.SwitchDelMinRelay4);

        switchDelayMax = new TextView[4];
        switchDelayMax[0] = findViewById(R.id.SwitchDelMaxRelay1);
        switchDelayMax[1] = findViewById(R.id.SwitchDelMaxRelay2);
        switchDelayMax[2] = findViewById(R.id.SwitchDelMaxRelay3);
        switchDelayMax[3] = findViewById(R.id.SwitchDelMaxRelay4);

        schedSwitchActDel = new TextView[4];
        schedSwitchActDel[0] = findViewById(R.id.SchedSwitchActRelay1);
        schedSwitchActDel[1] = findViewById(R.id.SchedSwitchActRelay2);
        schedSwitchActDel[2] = findViewById(R.id.SchedSwitchActRelay3);
        schedSwitchActDel[3] = findViewById(R.id.SchedSwitchActRelay4);

        lossOfSupplyAction = new TextView[4];
        lossOfSupplyAction[0] = findViewById(R.id.LossOfSupplyActionRelay1);
        lossOfSupplyAction[1] = findViewById(R.id.LossOfSupplyActionRelay2);
        lossOfSupplyAction[2] = findViewById(R.id.LossOfSupplyActionRelay3);
        lossOfSupplyAction[3] = findViewById(R.id.LossOfSupplyActionRelay4);


        //Telegram absence

        absenceTime = new TextView[4];
        absenceTime[0] = findViewById(R.id.AbsenceRelay1);
        absenceTime[1] = findViewById(R.id.AbsenceRelay2);
        absenceTime[2] = findViewById(R.id.AbsenceRelay3);
        absenceTime[3] = findViewById(R.id.AbsenceRelay4);

        resetTimer = new TextView[4];
        resetTimer[0] = findViewById(R.id.ResetTimerRelay1);
        resetTimer[1] = findViewById(R.id.ResetTimerRelay2);
        resetTimer[2] = findViewById(R.id.ResetTimerRelay3);
        resetTimer[3] = findViewById(R.id.ResetTimerRelay4);

        onTimeRestartEvent = new TextView[4];
        onTimeRestartEvent[0] = findViewById(R.id.OnTimerRestartRelay1);
        onTimeRestartEvent[1] = findViewById(R.id.OnTimerRestartRelay2);
        onTimeRestartEvent[2] = findViewById(R.id.OnTimerRestartRelay3);
        onTimeRestartEvent[3] = findViewById(R.id.OnTimerRestartRelay4);

        action = new TextView[4];
        action[0] = findViewById(R.id.ActionRelay1);
        action[1] = findViewById(R.id.ActionRelay2);
        action[2] = findViewById(R.id.ActionRelay3);
        action[3] = findViewById(R.id.ActionRelay4);

        learnFunctionDisabled = new TextView[4];
        learnFunctionDisabled[0] = findViewById(R.id.LearnFunctionRelay1);
        learnFunctionDisabled[1] = findViewById(R.id.LearnFunctionRelay2);
        learnFunctionDisabled[2] = findViewById(R.id.LearnFunctionRelay3);
        learnFunctionDisabled[3] = findViewById(R.id.LearnFunctionRelay4);


        //Learn functions

        learnPeriod = new TextView[4];
        learnPeriod[0] = findViewById(R.id.LearnPeriodRelay1);
        learnPeriod[1] = findViewById(R.id.LearnPeriodRelay2);
        learnPeriod[2] = findViewById(R.id.LearnPeriodRelay3);
        learnPeriod[3] = findViewById(R.id.LearnPeriodRelay4);

        position = new TextView[4];
        position[0] = findViewById(R.id.PositionRelay1);
        position[1] = findViewById(R.id.PositionRelay2);
        position[2] = findViewById(R.id.PositionRelay3);
        position[3] = findViewById(R.id.PositionRelay4);

        minTV = new TextView[4];
        minTV[0] = findViewById(R.id.MinRelay1);
        minTV[1] = findViewById(R.id.MinRelay2);
        minTV[2] = findViewById(R.id.MinRelay3);
        minTV[3] = findViewById(R.id.MinRelay4);

        maxTV = new TextView[4];
        maxTV[0] = findViewById(R.id.MaxRelay1);
        maxTV[1] = findViewById(R.id.MaxRelay2);
        maxTV[2] = findViewById(R.id.MaxRelay3);
        maxTV[3] = findViewById(R.id.MaxRelay4);


        //relay settings
        relayInstalled = new TextView[4];
        relayInstalled[0] = findViewById(R.id.RelayInstallRelay1);
        relayInstalled[1] = findViewById(R.id.RelayInstallRelay2);
        relayInstalled[2] = findViewById(R.id.RelayInstallRelay3);
        relayInstalled[3] = findViewById(R.id.RelayInstallRelay4);

        invertedLogic = new TextView[4];
        invertedLogic[0] = findViewById(R.id.InvertedLogicRelay1);
        invertedLogic[1] = findViewById(R.id.InvertedLogicRelay2);
        invertedLogic[2] = findViewById(R.id.InvertedLogicRelay3);
        invertedLogic[3] = findViewById(R.id.InvertedLogicRelay4);

        //Work schedules - Time pairs
        workSchedTime1 = new TextView[50][16];
        //  workSchedTime1[0]=findViewById(R.id.Workschedulestest11Res);
        //  workSchedTime1[1]=findViewById(R.id.TimePairTest21Res);
        //  workSchedTime1[2]=findViewById(R.id.Tatest31Res);
        //  workSchedTime1[3]=findViewById(R.id.Tbtest41Res);

        workSchedTime2 = new TextView[50][16];
        //  workSchedTime2[0]=findViewById(R.id.Workschedulestest12Res);
        //  workSchedTime2[1]=findViewById(R.id.TimePairTest22Res);
        //  workSchedTime2[2]=findViewById(R.id.Tatest32Res);
        //  workSchedTime2[3]=findViewById(R.id.Tbtest42Res);

        workSchedTime3 = new TextView[50][16];
        //  workSchedTime3[0]=findViewById(R.id.Workschedulestest13Res);
        //  workSchedTime3[1]=findViewById(R.id.TimePairTest23Res);
        //  workSchedTime3[2]=findViewById(R.id.Tatest33Res);
        //  workSchedTime3[3]=findViewById(R.id.Tbtest43Res);

        timePair1 = new TextView[50][16];
        timePair2 = new TextView[50][16];
        timePair3 = new TextView[50][16];

        Ta_test1 = new TextView[50][16];
        Ta_test2 = new TextView[50][16];
        Ta_test3 = new TextView[50][16];

        Tb_test1 = new TextView[50][16];
        Tb_test2 = new TextView[50][16];
        Tb_test3 = new TextView[50][16];

        workTB1 = new TableRow[100];
        workTB2 = new TableRow[100];
        workTB3 = new TableRow[100];

        for (int i = 0; i < 4; i++) {
            m_RelInterLock[i].wBitsOn = 0xFFFF;
            m_RelInterLock[i].wBitsOff = 0xFFFF;
            m_RelInterLock[i].PcCnfg[0] = 1;
            m_RelInterLock[i].PcCnfg[1] = 1;

        }
        for (int i = 4; i < 8; i++) {
            m_RelInterLock[i].wBitsOn = 0;
            m_RelInterLock[i].wBitsOff = 0;
            m_RelInterLock[i].PcCnfg[0] = 0;
            m_RelInterLock[i].PcCnfg[1] = 0;

        }
        String filename = "Measurment.htm";
        String fileContents = "Hello world!";
        OutputStreamWriter htmlWriter;
        try {
            htmlWriter = new OutputStreamWriter(getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE));
            htmlWriter.write("<html><body><div class=\"container\">");
            displayValues(wiper, ponPoffStrs, tlgAbstrs, strLoadMngs, m_PProg_R1, m_PProg_R2, m_PProg_R3, m_PProg_R4, oprij, htmlWriter);
            htmlWriter.write("</div></body></html>");
            htmlWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        System.out.println("pokusaj citanja->>>" + readFromFile(filename, getApplicationContext()));

//tempOprij=getIntent().getParcelableExtra("opPrij");


        System.out.println("AAAAA");
    }

    private void displayValues(Wiper[] wiper, PonPoffStr[] ponPoffStr, TlgAbstr[] tlgAbstrs, StrLoadMng[] strLoadMng, Opprog[] m_PProg_R1, Opprog[] m_PProg_R2, Opprog[] m_PProg_R3, Opprog[] m_PProg_R4, Oprij oprij, OutputStreamWriter htmlWriter) throws IOException {


        //Wiper and Closed Loop
        htmlWriter.write("<h2>Wiper and Closed Loop</h2>");
        htmlWriter.write("<table>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th></th>");
        for (int i = 0; i < 4; i++) {
            htmlWriter.write("<th>Relay " + (i + 1) + "</th>");
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Wiper Enable</th>");
        for (int i = 0; i < 4; i++) {
            if ((wiper[i].status & WIPER_DISEB_MASK) == 0x00) {
                wipEnable[i].setText("Yes");
                htmlWriter.write("<td>Yes</td>");
            } else {
                wipEnable[i].setText("No");
                htmlWriter.write("<td>No</td>");
            }
        }
        htmlWriter.write("</tr>");

        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Retrigerable</th>");
        for (int i = 0; i < 4; i++) {
            if ((wiper[i].status & WIPPER_RETRIG_MASK) != 0x00) {
                retrig[i].setText("Yes");
                htmlWriter.write("<td>Yes</td>");
            } else {
                retrig[i].setText("No");
                htmlWriter.write("<td>No</td>");
            }
        }
        htmlWriter.write("</tr>");


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Activation command</th>");
        for (int i = 0; i < 4; i++) {
            if ((wiper[i].status & WIPER_ON_MASK) != 0x00) {
                actComm[i].setText("a");
                htmlWriter.write("<td>a</td>");
            } else if ((wiper[i].status & WIPER_OFF_MASK) != 0x00) {
                actComm[i].setText("b");
                htmlWriter.write("<td>b</td>");
            } else {
                actComm[i].setText("XX");
                htmlWriter.write("<td>XX</td>");
            }

        }
        htmlWriter.write("</tr>");


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Switching delay</th>");
        for (int i = 0; i < 4; i++) {
            wipTime[i].setText(GetHMSfromInt(wiper[i].Tswdly));
            htmlWriter.write("<td>" + GetHMSfromInt(wiper[i].Tswdly) + "</td>");
        }
        htmlWriter.write("</tr>");


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Wiper time</th>");

        for (int i = 0; i < 4; i++) {
            switchDelay[i].setText(GetHMSfromInt(0000));
            htmlWriter.write("<td>" + GetHMSfromInt(0000) + "</td>");
        }
        htmlWriter.write("</tr>");


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Scheduled switching activation delay</th>");

        for (int i = 0; i < 4; i++) {
            schedSwitActDelay[i].setText(GetHMSfromInt(wiper[i].TBlockPrePro));
            htmlWriter.write("<td>" + GetHMSfromInt(wiper[i].TBlockPrePro) + "</td>");

        }
        htmlWriter.write("</tr>");


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Loop enable</th>");


        for (int i = 0; i < 4; i++) {
            if ((wiper[i].status & LOOP_DISEB_MASK) == 0x00) {
                loopEnbl[i].setText("Yes");
                htmlWriter.write("<td>Yes</td>");

            } else {
                loopEnbl[i].setText("No");
                htmlWriter.write("<td>No</td>");

            }
        }
        htmlWriter.write("</tr>");


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Duration in position</th>");

        for (int i = 0; i < 4; i++) {

            durationInPos[i].setText(GetHMSfromInt(wiper[i].Twiper));
            htmlWriter.write("<td>" + GetHMSfromInt(wiper[i].Twiper) + "</td>");

        }
        htmlWriter.write("</tr>");
        htmlWriter.write("</table>");

        //Wiper and Closed Loop END


        //Arival and loss of supply

        htmlWriter.write("<h2>Arival and loss of supply</h2>");
        htmlWriter.write("<table>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th></th>");
        for (int i = 0; i < 4; i++) {
            htmlWriter.write("<th>Relay " + (i + 1) + "</th>");
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Stop learn and wiper functions</th>");
        for (int i = 0; i < 4; i++) {
            if ((ponPoffStr[i].OnPonExe & PON_DISLRN_I_W_MASK) != 0) {
                stopLearn[i].setText("Yes");
                htmlWriter.write("<td>Yes</td>");
            } else {
                stopLearn[i].setText("No");
                htmlWriter.write("<td>No</td>");
            }
        }
        htmlWriter.write("</tr>");

        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Power supply loss time</th>");
        for (int i = 0; i < 4; i++) {
            powerSupply[i].setText(GetHMSfromInt(ponPoffStr[i].Tlng));
            htmlWriter.write("<td>" + GetHMSfromInt(ponPoffStr[i].Tlng) + "</td>");

        }
        htmlWriter.write("</tr>");

        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Loss of supply(short) - action</th>");
        for (int i = 0; i < 4; i++) {
            byte ss = (byte) (ponPoffStr[i].OnPonExe & 0x7F);
            if (ss < 10) {
                lossOfSupplyShort[i].setText(PoffS[ss]);
                htmlWriter.write("<td>" + PoffS[ss] + "</td>");

            } else {
                lossOfSupplyShort[i].setText("XX");
                htmlWriter.write("<td>XX</td>");
            }


        }
        htmlWriter.write("</tr>");


        int[] ign = {0, 0, 0, 0};
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Loss of supply(long) - ignore action </th>");
        for (int i = 0; i < 4; i++) {
            if ((ponPoffStr[i].lperIgno & PON_LPERIOD_DIS_MASK) != 0) {
                lossOfSupplyLongIgn[i].setText("Yes");
                htmlWriter.write("<td>Yes</td>");
                ign[i] = 1;
            } else {
                lossOfSupplyLongIgn[i].setText("No");
                htmlWriter.write("<td>No</td>");
            }
        }
        htmlWriter.write("</tr>");

        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Loss of supply(long) - action</th>");
        for (int i = 0; i < 4; i++) {
            byte ss = (ponPoffStr[i].lOnPonExe);
            if (ign[i] == 1) {
                lossOfSupplyLong[i].setText("/");
                htmlWriter.write("<td>/</td>");
                continue;
            }
            if (ss < 6) {
                lossOfSupplyLong[i].setText(PoffL[ss]);
                htmlWriter.write("<td>" + PoffL[ss] + "</td>");
            } else {
                lossOfSupplyLong[i].setText("XX");
                htmlWriter.write("<td>XX</td>");
            }
        }
        htmlWriter.write("</tr>");


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Switching delay - min</th>");
        for (int i = 0; i < 4; i++) {
            switchDelayMin[i].setText(GetHMSfromInt(ponPoffStr[i].TminSwdly));
            htmlWriter.write("<td>" + GetHMSfromInt(ponPoffStr[i].TminSwdly) + "</td>");

        }
        htmlWriter.write("</tr>");

        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Switching delay - max</th>");
        for (int i = 0; i < 4; i++) {
            switchDelayMax[i].setText(GetHMSfromInt(ponPoffStr[i].TrndSwdly));
            htmlWriter.write("<td>" + GetHMSfromInt(ponPoffStr[i].TrndSwdly) + "</td>");
        }
        htmlWriter.write("</tr>");

        htmlWriter.write("<tr>");
        htmlWriter.write("<th> Scheduled switching activation delay</th>");
        for (int i = 0; i < 4; i++) {
            schedSwitchActDel[i].setText(GetHMSfromInt(ponPoffStr[i].TBlockPrePro));
            htmlWriter.write("<td>" + GetHMSfromInt(ponPoffStr[i].TBlockPrePro) + "</td>");
        }
        htmlWriter.write("</tr>");

        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Action</th>");
        for (int i = 0; i < 4; i++) {
            byte ss = (byte) ponPoffStr[i].OnPoffExe;
            if (ss < 3) {
                lossOfSupplyAction[i].setText(PoffAct[ss]);
                htmlWriter.write("<td>" + PoffAct[ss] + "</td>");
            } else {
                lossOfSupplyAction[i].setText("XX");
                htmlWriter.write("<td>XX</td>");
            }
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("</table>");
        //Arival and loss of supply END


        //Telegram absence

        htmlWriter.write("<h2>Telegram</h2>");
        htmlWriter.write("<table>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th></th>");
        for (int i = 0; i < 4; i++) {
            htmlWriter.write("<th>Relay " + (i + 1) + "</th>");
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Absence time</th>");
        for (int i = 0; i < 4; i++) {
            absenceTime[i].setText(GetHMSfromInt(tlgAbstrs[i].TDetect));
            htmlWriter.write("<td>" + GetHMSfromInt(tlgAbstrs[i].TDetect) + "</td>");
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Absence time</th>");
        for (int i = 0; i < 4; i++) {
            byte ss = tlgAbstrs[i].RestOn;
            if (ss < 0x0F) {
                resetTimer[i].setText(rst[ss]);
                htmlWriter.write("<td>" + rst[ss] + "</td>");
            } else {
                resetTimer[i].setText("XX");
                htmlWriter.write("<td>XX</td>");
            }
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>On timer restart event disables work schedule</th>");

        for (int i = 0; i < 4; i++) {
            if ((tlgAbstrs[i].OnRes & 0x01) != 0) {
                onTimeRestartEvent[i].setText("Yes");
                htmlWriter.write("<td>Yes</td>");
            } else {
                onTimeRestartEvent[i].setText("No");
                htmlWriter.write("<td>No</td>");
            }
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Action</th>");


        for (int i = 0; i < 4; i++) {
            byte ss = (byte) (tlgAbstrs[i].OnTaExe & 0x0F);
            if (ss < 0x0F) {
                action[i].setText(act[ss]);
                htmlWriter.write("<td>" + act[ss] + "</td>");
            } else {
                action[i].setText("XX");
                htmlWriter.write("<td>XX</td>");
            }
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Learn function disabled</th>");
        for (int i = 0; i < 4; i++) {
            if ((tlgAbstrs[i].OnTaExe & TLGA_ON_DISLRN) != 0) {
                learnFunctionDisabled[i].setText("Yes");
                htmlWriter.write("<td>Yes</td>");
            } else {
                learnFunctionDisabled[i].setText("No");
                htmlWriter.write("<td>No</td>");
            }
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("</table>");
        //Telegram absence END


        //Learn functions

        htmlWriter.write("<h2>Learn functions</h2>");
        htmlWriter.write("<table>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th></th>");
        for (int i = 0; i < 4; i++) {
            htmlWriter.write("<th>Relay " + (i + 1) + "</th>");
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Learn period</th>");
        for (int i = 0; i < 4; i++) {
            if ((strLoadMng[i].Status & LEARN_7DAYS_MASK) == 0) {
                learnPeriod[i].setText("24 h");
                htmlWriter.write("<td>24 h</td>");
            } else {
                learnPeriod[i].setText("7 days");
                htmlWriter.write("<td>7 days</td>");
            }
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Position</th>");

        for (int i = 0; i < 4; i++) {
            if ((strLoadMng[i].RelPos & LEARN_R_ON_MASK) != 0) {
                position[i].setText("a");
                htmlWriter.write("<td>a</td>");
            } else if ((strLoadMng[i].RelPos & LEARN_R_OFF_MASK) != 0) {
                position[i].setText("b");
                htmlWriter.write("<td>b</td>");
            } else {
                position[i].setText("XX");
                htmlWriter.write("<td>XX</td>");
            }
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Min</th>");


        for (int i = 0; i < 4; i++) {
            minTV[i].setText(GetHMfromInt(strLoadMng[i].TPosMin));
            htmlWriter.write("<td>" + GetHMfromInt(strLoadMng[i].TPosMin) + "</td>");
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Max</th>");

        for (int i = 0; i < 4; i++) {
            maxTV[i].setText(GetHMfromInt(strLoadMng[i].TPosMax));
            htmlWriter.write("<td>" + GetHMfromInt(strLoadMng[i].TPosMax) + "</td>");
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("</table>");
        //Learn functions END


        //Relay settings
        htmlWriter.write("<h2>Relay settings</h2>");
        htmlWriter.write("<table>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th></th>");
        for (int i = 0; i < 4; i++) {
            htmlWriter.write("<th>Relay " + (i + 1) + "</th>");
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Relay installed(active) </th>");
        for (int i = 0; i < 4; i++) {
            int msk = 0x80 >> i;
            if ((oprij.VOpRe.StaPrij & msk) != 0) {
                relayInstalled[i].setText("Yes");
                htmlWriter.write("<td>Yes</td>");
            } else {
                relayInstalled[i].setText("No");
                htmlWriter.write("<td>No</td>");
            }
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Inverted logic</th>");

        for (int i = 0; i < 4; i++) {
            int msk = 0x80 >> i;
            if ((oprij.PolUKRe & msk) != 0) {
                invertedLogic[i].setText("Yes");
                htmlWriter.write("<td>Yes</td>");
            } else {
                invertedLogic[i].setText("No");
                htmlWriter.write("<td>No</td>");
            }

        }
        htmlWriter.write("</tr>");
        htmlWriter.write("</table>");
        //Relay settings END



        TableRow workSchedTitleTR1 = new TableRow(this);
        TableRow workSchedTitleTR2 = new TableRow(this);
        TableRow workSchedTitleTR3 = new TableRow(this);

        TextView workSchedTitleTV1 = new TextView(this);
        TextView workSchedTitleTV2 = new TextView(this);
        TextView workSchedTitleTV3 = new TextView(this);

        workSchedTitleTV1.setTextSize(16);
        workSchedTitleTV1.setText("Work schedules - Time pairs Relay 1");
        workSchedTitleTV1.setBackgroundResource(R.color.colorPrimary);
        workSchedTitleTV1.setLayoutParams(paramsExample);


        workSchedTitleTV2.setTextSize(16);
        workSchedTitleTV2.setText("Work schedules - Time pairs Relay 2");
        workSchedTitleTV2.setBackgroundResource(R.color.colorPrimary);
        workSchedTitleTV2.setLayoutParams(paramsExample);

        workSchedTitleTV3.setTextSize(16);
        workSchedTitleTV3.setText("Work schedules - Time pairs Relay 3");
        workSchedTitleTV3.setBackgroundResource(R.color.colorPrimary);
        workSchedTitleTV3.setLayoutParams(paramsExample);

        workSchedTitleTR1.addView(workSchedTitleTV1);
        workSchedTitleTR2.addView(workSchedTitleTV2);
        workSchedTitleTR3.addView(workSchedTitleTV3);


        TableRow workSchedColumnTR1 = new TableRow(this);
        TextView workSchedTest11 = new TextView(this);
        workSchedTest11.setTextSize(16);
        workSchedTest11.setText("Work Schedulestest1");
        workSchedTest11.setBackgroundResource(R.color.colorPrimaryDark);
        workSchedTest11.setLayoutParams(paramsExample);

        TextView timePair11 = new TextView(this);
        timePair11.setTextSize(16);
        timePair11.setText("Time pair test2");
        timePair11.setBackgroundResource(R.color.colorPrimaryDark);
        timePair11.setLayoutParams(paramsExample);
        TextView TaTest11 = new TextView(this);
        TaTest11.setTextSize(16);
        TaTest11.setText("T- a test3");
        TaTest11.setBackgroundResource(R.color.colorPrimaryDark);
        TaTest11.setLayoutParams(paramsExample);
        TextView TbTest11 = new TextView(this);
        TbTest11.setTextSize(16);
        TbTest11.setText("T- b test4");
        TbTest11.setBackgroundResource(R.color.colorPrimaryDark);
        TbTest11.setLayoutParams(paramsExample);
        workSchedColumnTR1.addView(workSchedTest11);
        workSchedColumnTR1.addView(timePair11);
        workSchedColumnTR1.addView(TaTest11);
        workSchedColumnTR1.addView(TbTest11);


        TableRow workSchedColumnTR2 = new TableRow(this);
        TextView workSchedTest12 = new TextView(this);
        workSchedTest12.setTextSize(16);
        workSchedTest12.setText("Work Schedulestest1");
        workSchedTest12.setBackgroundResource(R.color.colorPrimaryDark);
        workSchedTest12.setLayoutParams(paramsExample);
        TextView timePair12 = new TextView(this);
        timePair12.setTextSize(16);
        timePair12.setText("Time pair test2");
        timePair12.setBackgroundResource(R.color.colorPrimaryDark);
        timePair12.setLayoutParams(paramsExample);
        TextView TaTest12 = new TextView(this);
        TaTest12.setTextSize(16);
        TaTest12.setText("T- a test3");
        TaTest12.setBackgroundResource(R.color.colorPrimaryDark);
        TaTest12.setLayoutParams(paramsExample);
        TextView TbTest12 = new TextView(this);
        TbTest12.setTextSize(16);
        TbTest12.setText("T- b test4");
        TbTest12.setBackgroundResource(R.color.colorPrimaryDark);
        TbTest12.setLayoutParams(paramsExample);
        workSchedColumnTR2.addView(workSchedTest12);
        workSchedColumnTR2.addView(timePair12);
        workSchedColumnTR2.addView(TaTest12);
        workSchedColumnTR2.addView(TbTest12);


        TableRow workSchedColumnTR3 = new TableRow(this);
        TextView workSchedTest13 = new TextView(this);
        workSchedTest13.setTextSize(16);
        workSchedTest13.setText("Work Schedulestest1");
        workSchedTest13.setBackgroundResource(R.color.colorPrimaryDark);
        workSchedTest13.setLayoutParams(paramsExample);
        TextView timePair13 = new TextView(this);
        timePair13.setTextSize(16);
        timePair13.setText("Time pair test2");
        timePair13.setBackgroundResource(R.color.colorPrimaryDark);
        timePair13.setLayoutParams(paramsExample);
        TextView TaTest13 = new TextView(this);
        TaTest13.setTextSize(16);
        TaTest13.setText("T- a test3");
        TaTest13.setBackgroundResource(R.color.colorPrimaryDark);
        TaTest13.setLayoutParams(paramsExample);
        TextView TbTest13 = new TextView(this);
        TbTest13.setTextSize(16);
        TbTest13.setText("T- b test4");
        TbTest13.setBackgroundResource(R.color.colorPrimaryDark);
        TbTest13.setLayoutParams(paramsExample);
        workSchedColumnTR3.addView(workSchedTest13);
        workSchedColumnTR3.addView(timePair13);
        workSchedColumnTR3.addView(TaTest13);
        workSchedColumnTR3.addView(TbTest13);

        TableRow[] workSchedulesRow = new TableRow[4];
        TableLayout[] workSchedulesLayout = new TableLayout[4];
        for (int i = 0; i < 4; i++) {
            workSchedulesRow[i] = new TableRow(this);
            workSchedulesLayout[i] = new TableLayout(this);
            workSchedulesRow[i].addView(workSchedulesLayout[i]);
        }


        TableRow[] timePairsTR = new TableRow[4];
        TextView[][] timePairsTV = new TextView[4][10];

        for (int i = 0; i < 10; i++) {
            timePairsTV[0][i] = new TextView(this);
            timePairsTV[1][i] = new TextView(this);
            timePairsTV[2][i] = new TextView(this);
            timePairsTV[3][i] = new TextView(this);

            timePairsTV[0][i].setBackgroundResource(R.color.title);
            timePairsTV[1][i].setBackgroundResource(R.color.title);
            timePairsTV[2][i].setBackgroundResource(R.color.title);
            timePairsTV[3][i].setBackgroundResource(R.color.title);

            timePairsTV[0][i].setLayoutParams(paramsExample);
            timePairsTV[1][i].setLayoutParams(paramsExample);
            timePairsTV[2][i].setLayoutParams(paramsExample);
            timePairsTV[3][i].setLayoutParams(paramsExample);

        }


        showTimePairs(m_PProg_R1, m_PProg_R2, m_PProg_R3, m_PProg_R4, workTB1, workTB2, workTB3);

        for (int relej = 1; relej <= 4; relej++) {
            if ((oprij.VOpRe.StaPrij & (0x80 >> (relej - 1))) == 0) {
                continue;
            }
            switch (relej) {

                case 1:
                    tableLayout.addView(workSchedTitleTR1);
                    break;
                case 2:
                    tableLayout.addView(workSchedTitleTR2);
                    break;
                case 3:
                    tableLayout.addView(workSchedTitleTR3);
                    break;
                case 4:
                    break;
            }

            timePairsTR[relej - 1] = new TableRow(this);
            timePairsTV[relej - 1][0].setText("Work Schedules");
            timePairsTV[relej - 1][1].setText("Active");


            for (int kkk = 0; kkk < 8; kkk++) {
                timePairsTV[relej - 1][kkk + 2].setText(schDays[kkk]);

            }

            for (int i = 0; i < 10; i++) {
                timePairsTR[relej - 1].addView(timePairsTV[relej - 1][i]);
            }
            workSchedulesLayout[relej - 1].addView(timePairsTR[relej - 1]);
            // tableLayout.addView(workSchedulesRow[relej-1]);


            GetRelAkProg(relej, workSchedulesLayout[relej - 1], workSchedulesRow[relej - 1]);
            switch (relej) {
                case 1:

                    tableLayout.addView(workSchedColumnTR1);

                    for (int j = 0; j < 50; j++) {
                        if (workTB1[j] != null) {
                            tableLayout.addView(workTB1[j]);
                        }
                    }
                    break;
                case 2:

                    tableLayout.addView(workSchedColumnTR2);
                    for (int i = 0; i < 50; i++) {
                        if (workTB2[i] != null) {
                            tableLayout.addView(workTB2[i]);
                        }
                    }
                    break;
                case 3:

                    tableLayout.addView(workSchedColumnTR3);
                    for (int i = 0; i < 50; i++) {
                        if (workTB3[i] != null) {
                            tableLayout.addView(workTB3[i]);
                        }
                    }
                    break;
                default:
                    break;

            }

        }


        TableRow tbr = new TableRow(this);

        TextView[] tx = new TextView[4];
        for (int i = 0; i < 4; i++) {
            tx[i] = new TextView(this);
            tx[i].setText("test " + i);
            //tx[i].setLayoutParams(params);
            tx[i].setTextSize(17);
            tbr.addView(tx[i]);
        }
        tableLayout.addView(tbr);

        DisplayGeneral();

        GetReallocRel();

        if (fVis_RefPrij) {

            TableRow switchDelayTitleTR = new TableRow(this);
            TextView switchDelayTitle = new TextView(this);
            switchDelayTitleTR.addView(switchDelayTitle);
            switchDelayTitle.setText("Switching delay");
            switchDelayTitle.setLayoutParams(paramsExample);
            switchDelayTitle.setBackgroundResource(R.color.title);

            tableLayout.addView(switchDelayTitleTR);

            TableRow switchingDelayRow = new TableRow(this);
            TableLayout switchingDelayLayout = new TableLayout(this);
            switchingDelayRow.addView(switchingDelayLayout);


            TableRow switchDelayColumns = new TableRow(this);
            TextView[] switchDelayColumnsTV = new TextView[5];
            for (int i = 0; i < 5; i++) {
                switchDelayColumnsTV[i] = new TextView(this);
                switchDelayColumns.setLayoutParams(paramsExample);
                switchDelayColumns.setBackgroundResource(R.color.title);
                switchDelayColumns.addView(switchDelayColumnsTV[i]);

            }
            switchDelayColumnsTV[0].setText("");
            switchDelayColumnsTV[1].setText("Relay 1");
            switchDelayColumnsTV[2].setText("Relay 2");
            switchDelayColumnsTV[3].setText("Relay 3");
            switchDelayColumnsTV[4].setText("Relay 4");

            switchingDelayLayout.addView(switchDelayColumns);


            TableRow[] switchDelayTR = new TableRow[4];
            TextView[][] switchDelayTV = new TextView[4][5];
            for (int i = 0; i < 4; i++) {
                switchDelayTR[i] = new TableRow(this);
                for (int j = 0; j < 5; j++) {
                    switchDelayTV[i][j] = new TextView(this);
                    switchDelayTV[i][j].setBackgroundResource(R.color.whiteColor);
                    switchDelayTV[i][j].setLayoutParams(paramsExample);
                    switchDelayTR[i].addView(switchDelayTV[i][j]);
                }
            }

            switchDelayTV[0][0].setText("Delay a(hh:mm:ss)");
            switchDelayTV[1][0].setText("Delay a(hh:mm:ss)");
            switchDelayTV[2][0].setText("Delay b(hh:mm:ss)");
            switchDelayTV[3][0].setText("Delay b(hh:mm:ss)");


            switchDelayTV[0][1].setText(GetZatez(oprij.KlOpR1.KRelDela, 't'));
            switchDelayTV[0][2].setText(GetZatez(oprij.KlOpR2.KRelDela, 't'));
            switchDelayTV[0][3].setText(GetZatez(oprij.KlOpR3.KRelDela, 't'));
            switchDelayTV[0][4].setText(GetZatez(oprij.KlOpR4.KRelDela, 't'));


            switchDelayTV[1][1].setText(GetZatez(oprij.KlOpR1.KRelDela, 'm'));
            switchDelayTV[1][2].setText(GetZatez(oprij.KlOpR2.KRelDela, 'm'));
            switchDelayTV[1][3].setText(GetZatez(oprij.KlOpR3.KRelDela, 'm'));
            switchDelayTV[1][4].setText(GetZatez(oprij.KlOpR4.KRelDela, 'm'));


            switchDelayTV[2][1].setText(GetZatez(oprij.KlOpR1.KRelDelb, 't'));
            switchDelayTV[2][2].setText(GetZatez(oprij.KlOpR2.KRelDelb, 't'));
            switchDelayTV[2][3].setText(GetZatez(oprij.KlOpR3.KRelDelb, 't'));
            switchDelayTV[2][4].setText(GetZatez(oprij.KlOpR4.KRelDelb, 't'));

            switchDelayTV[3][1].setText(GetZatez(oprij.KlOpR1.KRelDelb, 'm'));
            switchDelayTV[3][2].setText(GetZatez(oprij.KlOpR2.KRelDelb, 'm'));
            switchDelayTV[3][3].setText(GetZatez(oprij.KlOpR3.KRelDelb, 'm'));
            switchDelayTV[3][4].setText(GetZatez(oprij.KlOpR4.KRelDelb, 'm'));

            for (int i = 0; i < 4; i++) {
                switchingDelayLayout.addView(switchDelayTR[i]);


            }
            tableLayout.addView(switchingDelayRow);

        }
        if (fVis_RefPrij) {
            TableRow TRclassicTeleTitle = new TableRow(this);
            TextView TitleClassicTele = new TextView(this);
            TitleClassicTele.setText("Classic telegram");
            TRclassicTeleTitle.addView(TitleClassicTele);
            tableLayout.addView(TRclassicTeleTitle);

            GetRasterHeadStringH();

            GetRasterHeadStringTop();

            GetRasterHeadStringBottom();

            GetRasterString(m_op50Prij.TlgRel1.Uk, 1, 'a');
            GetRasterString(m_op50Prij.TlgRel1.Isk, 1, 'b');

            GetRasterString(m_op50Prij.TlgRel2.Uk, 2, 'a');
            GetRasterString(m_op50Prij.TlgRel2.Isk, 2, 'b');

            GetRasterString(m_op50Prij.TlgRel3.Uk, 3, 'a');
            GetRasterString(m_op50Prij.TlgRel3.Isk, 3, 'b');

            GetRasterString(m_op50Prij.TlgRel4.Uk, 4, 'a');
            GetRasterString(m_op50Prij.TlgRel4.Isk, 4, 'b');


        }

        if (fVis_Cz96P) {
            TableRow TRadditionalTeleTitle = new TableRow(this);
            TextView TitleadditionalTele = new TextView(this);
            TitleadditionalTele.setText("Additional telegrams");
            TRadditionalTeleTitle.addView(TitleadditionalTele);
            tableLayout.addView(TRadditionalTeleTitle);

            GetRasterHeadStringH();

            GetRasterHeadStringTop();

            GetRasterHeadStringBottom();

            GetRasterString(m_op50Prij.tlg[0].Fn1.Cmd, 1, 'a');
            GetRasterString(m_op50Prij.tlg[1].Fn1.Cmd, 1, 'b');

            GetRasterString(m_op50Prij.tlg[2].Fn1.Cmd, 2, 'a');
            GetRasterString(m_op50Prij.tlg[3].Fn1.Cmd, 2, 'b');

            GetRasterString(m_op50Prij.tlg[4].Fn1.Cmd, 3, 'a');
            GetRasterString(m_op50Prij.tlg[5].Fn1.Cmd, 3, 'b');

            GetRasterString(m_op50Prij.tlg[6].Fn1.Cmd, 4, 'a');
            GetRasterString(m_op50Prij.tlg[7].Fn1.Cmd, 4, 'b');

        }
        if (fVis_RefPrij && fVis_Cz96P) {
            TableRow TRsyncTeleTitle = new TableRow(this);
            TextView TitlesyncTele = new TextView(this);
            TitlesyncTele.setText("Synchronization telegrams");
            TRsyncTeleTitle.addView(TitlesyncTele);
            tableLayout.addView(TRsyncTeleTitle);

            GetRasterHeadStringH();

            GetRasterHeadStringTop();

            GetRasterHeadStringBottom();

            for (int i = 0; i < 5; i++) {
                GetRasterStringSync(m_TelegSync[i].Cmd, i);
            }
        }

        if (fVis_RefPrij && fVis_Cz96P) {
            TableRow TRsyncTeleTitle = new TableRow(this);
            TextView TitlesyncTele = new TextView(this);
            TitlesyncTele.setText("Synchronization telegrams - day of the week");
            TRsyncTeleTitle.addView(TitlesyncTele);
            tableLayout.addView(TRsyncTeleTitle);

            GetRasterHeadStringH();

            GetRasterHeadStringTop();

            GetRasterHeadStringBottom();

            for (int i = 0; i < 8; i++) {
                GetRasterString(m_TlgFnD[i].Cmd, i, 'a');
            }
        }

        GetUserRecOpt();
        System.out.print("");

        if (fVis_Versacom) {
            //  dispTimePairs();
        }


        TableRow logicFunctionsTR = new TableRow(this);
        TextView logicFunctionsTV = new TextView(this);
        logicFunctionsTV.setText("Logic functions");
        logicFunctionsTV.setLayoutParams(paramsExample);
        logicFunctionsTV.setBackgroundResource(R.color.title);
        logicFunctionsTR.addView(logicFunctionsTV);
        tableLayout.addView(logicFunctionsTR);

        GetRelInterLock();

        if (fVis_Realoc) {
            TableRow[] relaySwitchAssignTR = new TableRow[11];
            TextView[][] relaySwitchAssignTV = new TextView[11][5];

            for (int i = 0; i < 11; i++) {
                relaySwitchAssignTR[i] = new TableRow(this);
                for (int j = 0; j < 5; j++) {

                    relaySwitchAssignTV[i][j] = new TextView(this);
                    relaySwitchAssignTR[i].addView(relaySwitchAssignTV[i][j]);
                    if (i > 2) {
                        relaySwitchAssignTV[i][j].setBackgroundResource(R.color.whiteColor);
                        relaySwitchAssignTV[i][j].setLayoutParams(paramsExample);


                    }
                }
            }

            relaySwitchAssignTV[0][0].setText("Relay switching assignment");
            relaySwitchAssignTV[0][0].setLayoutParams(paramsExample);
            relaySwitchAssignTV[0][0].setBackgroundResource(R.color.title);//title
            relaySwitchAssignTV[1][1].setText("Relay switching assignment");
            relaySwitchAssignTV[1][1].setLayoutParams(paramsExample);
            relaySwitchAssignTV[1][1].setBackgroundResource(R.color.title);//tableTittle
            for (int i = 1; i < 5; i++) {
                relaySwitchAssignTV[2][i].setText(String.format("Relay %d", i));
            }
            int x = 3;
            for (int i = 0; i < 4; i++) {
                relaySwitchAssignTV[x][0].setText(String.format("Relays %d a", i + 1));
                relaySwitchAssignTV[x][1].setText(PPRealoc(i, 1, m_Realloc[i].rel_on));
                relaySwitchAssignTV[x][2].setText(PPRealoc(i, 2, m_Realloc[i].rel_on));
                relaySwitchAssignTV[x][3].setText(PPRealoc(i, 3, m_Realloc[i].rel_on));
                relaySwitchAssignTV[x][4].setText(PPRealoc(i, 4, m_Realloc[i].rel_on));

                x++;

                relaySwitchAssignTV[x][0].setText(String.format("Relays %d b", i + 1));
                relaySwitchAssignTV[x][1].setText(PPRealoc(i, 1, m_Realloc[i].rel_of));
                relaySwitchAssignTV[x][2].setText(PPRealoc(i, 2, m_Realloc[i].rel_of));
                relaySwitchAssignTV[x][3].setText(PPRealoc(i, 3, m_Realloc[i].rel_of));
                relaySwitchAssignTV[x][4].setText(PPRealoc(i, 4, m_Realloc[i].rel_of));

                x++;

            }

            for (int i = 0; i < 11; i++) {
                tableLayout.addView(relaySwitchAssignTR[i]);
            }
        }


    }

    private void GetRelInterLock() {

        TableRow[] logicFuncTR = new TableRow[6];
        TextView[][] logicFuncTV = new TextView[6][2];

        for (int i = 0; i < 6; i++) {
            logicFuncTR[i] = new TableRow(this);
            for (int j = 0; j < 2; j++) {

                logicFuncTV[i][j] = new TextView(this);
                logicFuncTR[i].addView(logicFuncTV[i][j]);
            }
        }


        for (int rel = 0; rel < 3 * 2; rel++) {
            int cfg = m_RelInterLock[rel].PcCnfg[0];
            String res = UnPackLadderSTR(rel, cfg);
            if (rel < 3) {
                logicFuncTV[rel][0].setText(String.format("R%d %s", rel + 1, "a"));
                logicFuncTV[rel][0].setLayoutParams(paramsExample);
                logicFuncTV[rel][0].setBackgroundResource(R.color.title);
                logicFuncTV[rel][1].setText(res);
                logicFuncTV[rel][1].setLayoutParams(paramsExample);
                logicFuncTV[rel][1].setBackgroundResource(R.color.whiteColor);
            } else {
                logicFuncTV[rel][0].setText(String.format("R%d %s", rel % 3 + 1, "b"));
                logicFuncTV[rel][0].setLayoutParams(paramsExample);
                logicFuncTV[rel][0].setBackgroundResource(R.color.title);
                logicFuncTV[rel][1].setText(res);
                logicFuncTV[rel][1].setLayoutParams(paramsExample);
                logicFuncTV[rel][1].setBackgroundResource(R.color.whiteColor);
            }
        }
        for (int i = 0; i < 6; i++) {
            tableLayout.addView(logicFuncTR[i]);
        }
    }

    private String UnPackLadderSTR(int rel, int PCCNFG) {
        int cfg, tmp;
        int idx;

        byte[] state = new byte[4];
        byte[] varRel = new byte[4];
        String[] RR = new String[4];
        for (int i = 0; i < 4; i++) {
            RR[i] = new String();
        }

        for (int ii = 0; ii < 4; ii++) {
            varRel[ii] = 0;
            state[ii] = 0;
            RR[ii] = "";
        }
        cfg = PCCNFG;
        tmp = (byte) ((cfg >> 13) & 0x07);
        if (tmp != 0) {
            varRel[0] = (byte) (tmp & 0x3);
            state[0] = (tmp & 0x04) != 0 ? (byte) 00 : (byte) 00;
            RR[0].format("%sR%d", (tmp & 0x04) != 0 ? "NOT " : "", tmp & 0x3);
        }

        tmp = (byte) ((cfg >> 10) & 0x07);
        if (tmp != 0) {
            varRel[2] = (byte) (tmp & 0x3);
            state[2] = (tmp & 0x04) != 0 ? (byte) 00 : (byte) 00;
            RR[2].format("%sR%d", (tmp & 0x04) != 0 ? "NOT " : "", tmp & 0x3);
        }

        tmp = (byte) ((cfg >> 7) & 0x07);
        if (tmp != 0) {
            varRel[1] = (byte) (tmp & 0x3);
            state[1] = (tmp & 0x04) != 0 ? (byte) 00 : (byte) 00;
            RR[1].format("%sR%d", (tmp & 0x04) != 0 ? "NOT " : "", tmp & 0x3);
        }

        tmp = (byte) ((cfg >> 4) & 0x07);
        if (tmp != 0) {
            varRel[3] = (byte) (tmp & 0x3);
            state[3] = (tmp & 0x04) != 0 ? (byte) 00 : (byte) 00;
            RR[3].format("%sR%d", (tmp & 0x04) != 0 ? "NOT " : "", tmp & 0x3);
        }
        idx = cfg & 0x0F;

        boolean find = false;
        int j;
        for (j = 0; j < 12; j++) {

            if (pLCfg[j].Idx == idx) {
                find = true;
                break;
            }
        }

        if (find) {


            for (int i = 0; i < 4; i++) {
                LS[0].m_RelState[i] = (byte) (pLCfg[j].RelState[i] & state[i]);
                LS[0].m_RelNr[i] = (byte) (pLCfg[j].RelNr[i] & varRel[i]);
                LS[0].m_isSeries[i] = pLCfg[j].isSeries[i];
                LS[0].m_notsernotpar[i] = pLCfg[j].notsernotpar[i];
                LS[0].m_conectshort[i] = pLCfg[j].conectshort[i];
            }
        }
        String res = "";

        byte relst = 0;
        byte relser = 0;
        for (int k = 0; k < 4; k++) {
            if (LS[0].m_RelNr[k] != 0) relst |= (1 << k);
            if (LS[0].m_isSeries[k]) relser |= (1 << k);
        }

        if (relst == 0b0000) {
            res = String.format("NONE", RR[0], RR[1], RR[2], RR[3]);
        } else if (relst == 0b0111) {
            if (LS[0].m_conectshort[3])
                res = String.format("(%s AND %s) OR %s ", RR[0], RR[2], RR[1]);
            else
                res = String.format("(%s OR %s) AND %s ", RR[0], RR[1], RR[2]);

        } else if (relst == 0b1101) {
            if (LS[0].m_conectshort[1])
                res = String.format("(%s AND %s) OR %s ", RR[0], RR[2], RR[3]);
            else
                res = String.format("%s AND (%s OR %s) ", RR[0], RR[2], RR[3]);

        } else if (relst == 0b0011) res.format("%s OR %s", RR[0], RR[1]);
        else if (relst == 0b1100) res.format("%s OR %s", RR[2], RR[3]);
        else if (relst == 0b0001) res.format("%s ", RR[0]);
        else if (relst == 0b0100) res.format("%s ", RR[2]);
        else if (relst == 0b0101) res.format("%s AND %s", RR[0], RR[2]);
        else if (relst == 0b1111) {
            if (LS[0].m_isSeries[0])
                res = String.format("(%s AND %s) OR (%s AND %s)", RR[0], RR[1], RR[2], RR[3]);
            else
                res = String.format("(%s OR %s) AND (%s OR %s)", RR[0], RR[1], RR[2], RR[3]);


        }
        return res;
    }

    private void dispTimePairs() {
        TableRow[] timePairsTR = new TableRow[4];
        TextView[][] timePairsTV = new TextView[4][10];

        for (int i = 0; i < 10; i++) {
            timePairsTV[0][i] = new TextView(this);
            timePairsTV[1][i] = new TextView(this);
            timePairsTV[2][i] = new TextView(this);
            timePairsTV[3][i] = new TextView(this);

        }
        for (int relej = 1; relej <= 4; relej++) {
            if ((oprij.VOpRe.StaPrij & (0x80 >> (relej - 1))) == 0) {
                continue;
            }
            timePairsTR[relej - 1] = new TableRow(this);
            timePairsTV[relej - 1][0].setText("Work Schedules");
            timePairsTV[relej - 1][1].setText("Active");

            for (int kkk = 0; kkk < 8; kkk++) {
                timePairsTV[relej - 1][kkk + 2].setText(schDays[kkk]);
            }

            for (int i = 0; i < 10; i++) {
                timePairsTR[relej - 1].addView(timePairsTV[relej - 1][i]);
            }
            tableLayout.addView(timePairsTR[relej - 1]);

            //  GetRelAkProg(relej);

        }
    }

    private void GetRelAkProg(int brrel, TableLayout workSchedulesLayout, TableRow workSchedulesRow) {
        String strItem, strx, r, tmps;
        String adani[] = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun", "Ho."};

        TableRow[] relAkProgTR = new TableRow[16];

        TextView[][] relAkProTV = new TextView[16][10];


        for (int pItem = 0; pItem < 16; pItem++) {
            Opprog PrPro;
            int AkPro;
            relAkProgTR[pItem] = new TableRow(this);


            switch (brrel) {
                case 1:
                    if (m_PProg_R1[pItem].AkTim != 0) {


                        for (int cntTemp = 0; cntTemp < 10; cntTemp++) {
                            relAkProTV[pItem][cntTemp] = new TextView(this);
                            relAkProgTR[pItem].addView(relAkProTV[pItem][cntTemp]);
                        }
                        relAkProTV[pItem][0].setText(String.format("%d", pItem + 1));
                        relAkProTV[pItem][0].setBackgroundResource(R.color.whiteColor);
                        relAkProTV[pItem][0].setLayoutParams(paramsExample);

                        relAkProTV[pItem][1].setText(((iVtmask[pItem] & oprij.VOpRe.VAkProR1) != 0) ? "Yes" : "No");
                        relAkProTV[pItem][1].setBackgroundResource(R.color.whiteColor);
                        relAkProTV[pItem][1].setLayoutParams(paramsExample);

                        for (int iItem = 7; iItem >= 0; iItem--) {
                            if ((bVtmask[iItem] & (m_PProg_R1[pItem].DanPr)) != 0) {
                                relAkProTV[pItem][9 - iItem].setText("+");
                                relAkProTV[pItem][9 - iItem].setBackgroundResource(R.color.whiteColor);
                                relAkProTV[pItem][9 - iItem].setLayoutParams(paramsExample);
                            } else {
                                relAkProTV[pItem][9 - iItem].setText("-");
                                relAkProTV[pItem][9 - iItem].setBackgroundResource(R.color.whiteColor);
                                relAkProTV[pItem][9 - iItem].setLayoutParams(paramsExample);
                            }
                        }

                    }
                    workSchedulesLayout.addView(relAkProgTR[pItem]);
                    break;
                case 2:
                    if (m_PProg_R2[pItem].AkTim != 0) {


                        for (int cntTemp = 0; cntTemp < 10; cntTemp++) {
                            relAkProTV[pItem][cntTemp] = new TextView(this);
                            relAkProgTR[pItem].addView(relAkProTV[pItem][cntTemp]);
                        }
                        relAkProTV[pItem][0].setText(String.format("%d", pItem + 1));
                        relAkProTV[pItem][0].setBackgroundResource(R.color.whiteColor);
                        relAkProTV[pItem][0].setLayoutParams(paramsExample);

                        relAkProTV[pItem][1].setText(((iVtmask[pItem] & oprij.VOpRe.VAkProR1) != 0) ? "Yes" : "No");
                        relAkProTV[pItem][1].setBackgroundResource(R.color.whiteColor);
                        relAkProTV[pItem][1].setLayoutParams(paramsExample);

                        for (int iItem = 7; iItem >= 0; iItem--) {
                            if ((bVtmask[iItem] & (m_PProg_R2[pItem].DanPr)) != 0) {
                                relAkProTV[pItem][9 - iItem].setText("+");
                                relAkProTV[pItem][9 - iItem].setBackgroundResource(R.color.whiteColor);
                                relAkProTV[pItem][9 - iItem].setLayoutParams(paramsExample);
                            } else {
                                relAkProTV[pItem][9 - iItem].setText("-");
                                relAkProTV[pItem][9 - iItem].setBackgroundResource(R.color.whiteColor);
                                relAkProTV[pItem][9 - iItem].setLayoutParams(paramsExample);
                            }
                        }

                    }
                    workSchedulesLayout.addView(relAkProgTR[pItem]);
                    break;

                case 3:
                    if (m_PProg_R3[pItem].AkTim != 0) {


                        for (int cntTemp = 0; cntTemp < 10; cntTemp++) {
                            relAkProTV[pItem][cntTemp] = new TextView(this);
                            relAkProgTR[pItem].addView(relAkProTV[pItem][cntTemp]);
                        }
                        relAkProTV[pItem][0].setText(String.format("%d", pItem + 1));
                        relAkProTV[pItem][0].setBackgroundResource(R.color.whiteColor);
                        relAkProTV[pItem][0].setLayoutParams(paramsExample);

                        relAkProTV[pItem][1].setText(((iVtmask[pItem] & oprij.VOpRe.VAkProR1) != 0) ? "Yes" : "No");
                        relAkProTV[pItem][1].setBackgroundResource(R.color.whiteColor);
                        relAkProTV[pItem][1].setLayoutParams(paramsExample);

                        for (int iItem = 7; iItem >= 0; iItem--) {
                            if ((bVtmask[iItem] & (m_PProg_R3[pItem].DanPr)) != 0) {
                                relAkProTV[pItem][9 - iItem].setText("+");
                                relAkProTV[pItem][9 - iItem].setBackgroundResource(R.color.whiteColor);
                                relAkProTV[pItem][9 - iItem].setLayoutParams(paramsExample);
                            } else {
                                relAkProTV[pItem][9 - iItem].setText("-");
                                relAkProTV[pItem][9 - iItem].setBackgroundResource(R.color.whiteColor);
                                relAkProTV[pItem][9 - iItem].setLayoutParams(paramsExample);
                            }
                        }

                    }
                    workSchedulesLayout.addView(relAkProgTR[pItem]);
                    break;

                case 4:
                    if (m_PProg_R4[pItem].AkTim != 0) {


                        for (int cntTemp = 0; cntTemp < 10; cntTemp++) {
                            relAkProTV[pItem][cntTemp] = new TextView(this);
                            relAkProgTR[pItem].addView(relAkProTV[pItem][cntTemp]);
                        }
                        relAkProTV[pItem][0].setText(String.format("%d", pItem + 1));
                        relAkProTV[pItem][0].setBackgroundResource(R.color.whiteColor);
                        relAkProTV[pItem][0].setLayoutParams(paramsExample);

                        relAkProTV[pItem][1].setText(((iVtmask[pItem] & oprij.VOpRe.VAkProR1) != 0) ? "Yes" : "No");
                        relAkProTV[pItem][1].setBackgroundResource(R.color.whiteColor);
                        relAkProTV[pItem][1].setLayoutParams(paramsExample);

                        for (int iItem = 7; iItem >= 0; iItem--) {
                            if ((bVtmask[iItem] & (m_PProg_R4[pItem].DanPr)) != 0) {
                                relAkProTV[pItem][9 - iItem].setText("+");
                                relAkProTV[pItem][9 - iItem].setBackgroundResource(R.color.whiteColor);
                                relAkProTV[pItem][9 - iItem].setLayoutParams(paramsExample);
                            } else {
                                relAkProTV[pItem][9 - iItem].setText("-");
                                relAkProTV[pItem][9 - iItem].setBackgroundResource(R.color.whiteColor);
                                relAkProTV[pItem][9 - iItem].setLayoutParams(paramsExample);
                            }
                        }

                    }
                    workSchedulesLayout.addView(relAkProgTR[pItem]);
                    break;
            }

        }
        tableLayout.addView(workSchedulesRow);
        System.out.println();                       //GRESKA GRESKA GRESKA
    }

    private void GetUserRecOpt() {
        int[] m_LogEnFlgs = new int[2];
        m_LogEnFlgs[0] = m_op50Prij.CLOGENFLGS[0];
        m_LogEnFlgs[1] = m_op50Prij.CLOGENFLGS[1];


        TableRow[] eventLogTR = new TableRow[23];
        for (int i = 0; i < 23; i++) {
            eventLogTR[i] = new TableRow(this);
        }
        TextView title = new TextView(this);
        title.setText("Event log");
        eventLogTR[0].addView(title);

        TextView[] eventLog = new TextView[40];
        for (int i = 0; i < 40; i++) {
            eventLog[i] = new TextView(this);
            eventLog[i].setLayoutParams(paramsExample);
            eventLog[i].setBackgroundResource(R.color.whiteColor);
        }

        eventLog[0].setText("Common Log");
        eventLogTR[1].addView(eventLog[0]);

        eventLog[1].setText("Power on/off time");
        eventLog[2].setText((m_LogEnFlgs[0] & SNE_POFF) != 0 ? "Yes" : "No");
        eventLogTR[2].addView(eventLog[1]);
        eventLogTR[2].addView(eventLog[2]);

        eventLog[3].setText("Synchronization telegram - time");
        eventLog[4].setText((m_LogEnFlgs[0] & SNE_SHT) != 0 ? "Yes" : "No");
        eventLogTR[3].addView(eventLog[3]);
        eventLogTR[3].addView(eventLog[4]);

        eventLog[5].setText("Synchronization telegram - day");
        eventLog[6].setText((m_LogEnFlgs[0] & SNE_SHD) != 0 ? "Yes" : "No");
        eventLogTR[4].addView(eventLog[5]);
        eventLogTR[4].addView(eventLog[6]);

        eventLog[7].setText("Local change of time");
        eventLog[8].setText((m_LogEnFlgs[0] & SNE_LSINH) != 0 ? "Yes" : "No");
        eventLogTR[5].addView(eventLog[7]);
        eventLogTR[5].addView(eventLog[8]);

        eventLog[9].setText("RTC Log");
        eventLogTR[6].addView(eventLog[9]);

        eventLog[10].setText("Oscillator fail");
        eventLog[11].setText((m_LogEnFlgs[0] & SNE_RTC_OF) != 0 ? "Yes" : "No");
        eventLogTR[7].addView(eventLog[10]);
        eventLogTR[7].addView(eventLog[11]);

        eventLog[12].setText("RTC stop");
        eventLog[13].setText((m_LogEnFlgs[0] & SNE_RTC_ST) != 0 ? "Yes" : "No");
        eventLogTR[8].addView(eventLog[12]);
        eventLogTR[8].addView(eventLog[13]);


        eventLog[14].setText("Battery low");
        eventLog[15].setText((m_LogEnFlgs[0] & SNE_RTC_BL) != 0 ? "Yes" : "No");
        eventLogTR[9].addView(eventLog[14]);
        eventLogTR[9].addView(eventLog[15]);


        eventLog[16].setText("Relay Log");
        eventLogTR[10].addView(eventLog[16]);

        eventLog[17].setText("Relay switched by telegram");
        eventLog[18].setText((m_LogEnFlgs[1] & REL_ON) != 0 ? "Yes" : "No");
        eventLogTR[11].addView(eventLog[17]);
        eventLogTR[11].addView(eventLog[18]);

        eventLog[19].setText("Relay switched by program");
        eventLog[20].setText((m_LogEnFlgs[1] & PRO_REL_X) != 0 ? "Yes" : "No");
        eventLogTR[12].addView(eventLog[19]);
        eventLogTR[12].addView(eventLog[20]);


        eventLog[21].setText("Start Wiper");
        eventLog[22].setText((m_LogEnFlgs[1] & REL_WIP_S) != 0 ? "Yes" : "No");
        eventLogTR[13].addView(eventLog[21]);
        eventLogTR[13].addView(eventLog[22]);

        eventLog[23].setText("End Wiper");
        eventLog[24].setText((m_LogEnFlgs[1] & REL_WIP_R) != 0 ? "Yes" : "No");
        eventLogTR[14].addView(eventLog[23]);
        eventLogTR[14].addView(eventLog[24]);

        eventLog[25].setText("Telegram absence start");
        eventLog[26].setText((m_LogEnFlgs[1] & REL_TA_S) != 0 ? "Yes" : "No");
        eventLogTR[15].addView(eventLog[25]);
        eventLogTR[15].addView(eventLog[26]);


        eventLog[27].setText("Telegram absence restart");
        eventLog[28].setText((m_LogEnFlgs[1] & REL_TA_R) != 0 ? "Yes" : "No");
        eventLogTR[16].addView(eventLog[27]);
        eventLogTR[16].addView(eventLog[28]);

        eventLog[29].setText("Work schedule disabled");
        eventLog[30].setText((m_LogEnFlgs[1] & REL_PROBLOCK) != 0 ? "Yes" : "No");
        eventLogTR[17].addView(eventLog[29]);
        eventLogTR[17].addView(eventLog[30]);

        eventLog[31].setText("Work schedule enabled");
        eventLog[32].setText((m_LogEnFlgs[1] & REL_PROUNBLOCK) != 0 ? "Yes" : "No");
        eventLogTR[18].addView(eventLog[31]);
        eventLogTR[18].addView(eventLog[32]);

        eventLog[33].setText("Telegram Log");
        eventLogTR[19].addView(eventLog[33]);

        eventLog[34].setText("Log all telegrams");
        eventLog[35].setText((m_LogEnFlgs[0] & OPT_LOG_TLG) != 0 ? "Yes" : "No");
        eventLogTR[20].addView(eventLog[34]);
        eventLogTR[20].addView(eventLog[35]);

        eventLog[36].setText("Log telegrams for this receiver");
        eventLog[37].setText((m_LogEnFlgs[0] & OPT_LOG_MYTLG) != 0 ? "Yes" : "No");
        eventLogTR[21].addView(eventLog[36]);
        eventLogTR[21].addView(eventLog[37]);

        eventLog[38].setText("Log only telegrams which change the state");
        eventLog[39].setText((m_LogEnFlgs[0] & OPT_LOG_REPTLG) != 0 ? "Yes" : "No");
        eventLogTR[22].addView(eventLog[38]);
        eventLogTR[22].addView(eventLog[39]);

        for (int i = 0; i < 23; i++) {
            tableLayout.addView(eventLogTR[i]);
        }

    }

    private void GetRasterStringSync(TelegCMD t, int x) {
        TableRow telegramStatTR = new TableRow(this);
        TextView[] emptyTv = new TextView[5];
        for (int i = 0; i < 5; i++) {
            emptyTv[i] = new TextView(this);
            telegramStatTR.addView(emptyTv[i]);
        }
        TextView[] telegramStat = new TextView[50];
        TextView unkwnTv = new TextView(this);
        unkwnTv.setText("Unknown");
        TextView rTv = new TextView(this);
        rTv.setText(GetSyncTime(m_op50Prij.SinhTime[x], m_HWVerPri));

        telegramStatTR.addView(unkwnTv);
        telegramStatTR.addView(rTv);

        for (int i = 0; i < 50; i++) {
            telegramStat[i] = new TextView(this);
            telegramStatTR.addView(telegramStat[i]);
        }

        for (int ibimp = 0; ibimp < 50; ibimp++) {
            int nBitNumber = ibimp % 8;
            int nByteNumber = ibimp / 8;

            int N = t.NeutImp[nByteNumber] & (0x80 >> nBitNumber);
            int A = t.AktiImp[nByteNumber] & (0x80 >> nBitNumber);

            if (IsCZ44raster && ibimp == 44) {
                break;
            }
            if (A != 0 && N != 0) {
                telegramStat[ibimp].setText(" + ");
            } else if (A == 0 && N != 0) {
                telegramStat[ibimp].setText(" - ");
            } else {
                telegramStat[ibimp].setText("  ");
            }

        }
        tableLayout.addView(telegramStatTR);

    }

    private String GetSyncTime(int t, int ver) {
        String str, datstr, tstr;
        int stime, tmpi;

        String[] DanSyncTg = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun", "Time only"};
        if (ver == TIP_PA) {
            stime = t & 0x000FFFFF;
            datstr = String.format("%02d:%02d:%02d", ((stime / 60 / 60) % 24), ((stime / 60) % 60), (stime % 60));
            tmpi = stime / 60 / 60 / 24;
            if ((t & 0x00800000) == 0) {
                tmpi = 7;
            }
        } else {
            Uni4byt rtctime = new Uni4byt();
            rtctime.i = t;
            rtctime.updatei();
            datstr = String.format("%02d:%02d:%02d", (rtctime.b[2] & 0x1F), (rtctime.b[1]), (rtctime.b[0]));
            tmpi = rtctime.b[2] >> 5;
            if (tmpi == 0) {
                tmpi = 7;
            } else {
                tmpi--;
            }
        }
        tstr = String.format("%s>> %s", DanSyncTg[tmpi % 8], datstr);
        return tstr;

    }

    private void GetRasterString(TelegCMD t, int num, char ch) {
        TableRow telegramStatTR = new TableRow(this);
        TextView[] emptyTv = new TextView[5];
        for (int i = 0; i < 5; i++) {
            emptyTv[i] = new TextView(this);
            telegramStatTR.addView(emptyTv[i]);
        }
        TextView[] telegramStat = new TextView[50];
        TextView unkwnTv = new TextView(this);
        unkwnTv.setText("Unknown");
        TextView rTv = new TextView(this);
        rTv.setText(String.format("R%d %c", num, ch));

        telegramStatTR.addView(unkwnTv);
        telegramStatTR.addView(rTv);

        for (int i = 0; i < 50; i++) {
            telegramStat[i] = new TextView(this);

            telegramStat[i].setBackgroundResource(R.color.whiteColor);
            telegramStat[i].setLayoutParams(paramsExample);
            telegramStatTR.addView(telegramStat[i]);
        }

        for (int ibimp = 0; ibimp < 50; ibimp++) {
            int nBitNumber = ibimp % 8;
            int nByteNumber = ibimp / 8;

            int N = t.NeutImp[nByteNumber] & (0x80 >> nBitNumber);
            int A = t.AktiImp[nByteNumber] & (0x80 >> nBitNumber);

            if (IsCZ44raster && ibimp == 44) {
                break;
            }
            if (A != 0 && N != 0) {
                telegramStat[ibimp].setText(" + ");
                telegramStat[ibimp].setBackgroundResource(R.color.colorRed);
            } else if (A == 0 && N != 0) {
                telegramStat[ibimp].setText(" - ");
                telegramStat[ibimp].setBackgroundResource(R.color.colorGreen);
            } else {
                telegramStat[ibimp].setText("  ");
            }

        }
        tableLayout.addView(telegramStatTR);

    }

    private void GetRasterHeadStringH() {
        TableRow subTitle1TR = new TableRow(this);
        TextView[] emptyTv = new TextView[5];
        for (int i = 0; i < 5; i++) {
            emptyTv[i] = new TextView(this);
            subTitle1TR.addView(emptyTv[i]);
        }
        TextView[] subTitle1 = new TextView[20];
        for (int i = 0; i < 20; i++) {
            subTitle1[i] = new TextView(this);
        }
        //column spans
        TableRow.LayoutParams twoColumnSpan = new TableRow.LayoutParams();
        twoColumnSpan.span = 2;
        twoColumnSpan.setMargins(3, 3, 3, 3);


        TableRow.LayoutParams fourColumnSpan = new TableRow.LayoutParams();
        fourColumnSpan.span = 4;
        fourColumnSpan.setMargins(3, 3, 3, 3);

        TableRow.LayoutParams eightColumnSpan = new TableRow.LayoutParams();
        eightColumnSpan.span = 8;
        eightColumnSpan.setMargins(3, 3, 3, 3);

        subTitle1[2].setLayoutParams(fourColumnSpan);
        subTitle1[3].setLayoutParams(eightColumnSpan);


        //TableRow.LayoutParams paramsExample = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);

        for (int i = 4; i < 20; i++) {
            subTitle1[i].setLayoutParams(twoColumnSpan);
            subTitle1[i].setBackgroundResource(R.color.whiteColor);

            // subTitle1[i].setLayoutParams(paramsExample);
        }


        subTitle1[0].setText("Name");
        subTitle1[1].setText("Telegram");
        subTitle1[2].setText("A");
        subTitle1[3].setText("B");


        subTitle1[0].setBackgroundResource(R.color.whiteColor);
        subTitle1[1].setLayoutParams(paramsExample);
        subTitle1[0].setLayoutParams(paramsExample);
        subTitle1[1].setBackgroundResource(R.color.whiteColor);
        subTitle1[2].setBackgroundResource(R.color.whiteColor);
        subTitle1[3].setBackgroundResource(R.color.whiteColor);


        for (int i = 4; i < 20; i++) {
            subTitle1[i].setText(String.format("DP%d", i - 3));
        }


        for (int i = 0; i < 20; i++) {
            subTitle1TR.addView(subTitle1[i]);
        }


        tableLayout.addView(subTitle1TR);
    }

    private void GetRasterHeadStringTop() {
        TableRow subTitleTopTR = new TableRow(this);
        TextView[] emptyTv = new TextView[5];
        for (int i = 0; i < 5; i++) {
            emptyTv[i] = new TextView(this);
            subTitleTopTR.addView(emptyTv[i]);
        }
        TextView[] subTitleTop1 = new TextView[46];
        for (int i = 0; i < 46; i++) {
            subTitleTop1[i] = new TextView(this);
            subTitleTop1[i].setLayoutParams(paramsExample);
            subTitleTop1[i].setBackgroundResource(R.color.whiteColor);
        }


        subTitleTop1[0].setText("");
        subTitleTop1[1].setText("");

        for (int i = 1; i < 5; i++) {
            subTitleTop1[i + 1].setText(String.format("%d ", i));
        }

        for (int i = 1; i < 9; i++) {
            subTitleTop1[i + 5].setText(String.format("%d ", i));
        }

        for (int i = 14; i < 46; i += 2) {
            subTitleTop1[i].setText("  Z ");
            subTitleTop1[i + 1].setText("  V ");

        }


        for (int i = 0; i < 46; i++) {
            subTitleTopTR.addView(subTitleTop1[i]);
        }


        tableLayout.addView(subTitleTopTR);
    }

    private void GetRasterHeadStringBottom() {
        TableRow subTitleBotTR = new TableRow(this);

        TextView[] emptyTv = new TextView[5];
        for (int i = 0; i < 5; i++) {
            emptyTv[i] = new TextView(this);
            subTitleBotTR.addView(emptyTv[i]);
        }
        TextView[] subTitleBot1 = new TextView[46];
        for (int i = 0; i < 46; i++) {
            subTitleBot1[i] = new TextView(this);
            subTitleBot1[i].setBackgroundResource(R.color.whiteColor);
            subTitleBot1[i].setLayoutParams(paramsExample);
        }


        subTitleBot1[0].setText("");
        subTitleBot1[1].setText("");


        for (int i = 2; i < 46; i++) {
            subTitleBot1[i].setText(String.format("%d", i - 1));
        }


        for (int i = 0; i < 46; i++) {
            subTitleBotTR.addView(subTitleBot1[i]);
        }


        tableLayout.addView(subTitleBotTR);
    }

    private String GetZatez(int zz, char NT) {
        String r = "";
        if (NT == 'm')
            if ((zz & 0xC00000) != 0) {
                r = "Random";
            } else {
                r = "Fixed";
            }

        if (NT == 't') {
            zz = zz & 0x0FFFFF;
            r = String.format(("%02d:%02d:%02d"), zz / 3600, (zz % 3600) / 60, (zz % 3600) % 60);
        }
        return r;
    }

    private String PPRealoc(int i, int n, int y) {
        byte z = (byte) y;
        int k = 2 * (4 - n);
        int msk = (y >> k) & 0x03;
        String r = "";

        if ((i + 1) == n) r = "/";
        else if (msk == 0x00) r = "";
        else if (msk == 0x01) r = "b";
        else if (msk == 0x02) r = "a";
        else if (msk == 0x03) r = "";
        return r;
    }

    private void GetReallocRel() {

        m_Realloc[0].rel_on = 0x00;

        m_Realloc[0].rel_of = 0x00;

        m_Realloc[1].rel_on = 0x00;

        m_Realloc[1].rel_of = 0x00;

        m_Realloc[2].rel_on = 0x00;

        m_Realloc[2].rel_of = 0x00;

        m_Realloc[3].rel_on = 0x00;

        m_Realloc[3].rel_of = 0x00;

    }
/*
    private byte GetRelSw(){
        byte val = 0;
        for (int uIndex = 0; uIndex < 4; uIndex++)
        {
            val <<=2;
            val = val | (GetStatButon( &pRelBtnRow[uIndex])  & 3);
            //	val <<=2;
        }
        return val;
    }

    private byte GetStatButon( CTStateSwitchBtn* pReallocRx)
    {
        if(!(pReallocRx->IsWindowEnabled()))return 0;
        byte state = 0;
        VARIANT vv=	pReallocRx->get_Value();
        if(vv.vt == 1)
        {
            state = 0;
        }
        else if(vv.iVal == 0)
        {
            state = 1;
        }
        else
        {
            state = 2;
        }
        return (state);
    }


   private  void RefReallocRel()
    {
     //   static UINT aGrRRSTATIC[] = { IDC_STATIC_RR1,IDC_STATIC_RR2,IDC_STATIC_RR3,
      //          IDC_STATIC_RRR1,IDC_STATIC_RRR2,IDC_STATIC_RRR3,IDC_STATIC_RRR4,
      //          IDC_STATIC_RARU1,IDC_STATIC_RARU2,IDC_STATIC_RARU3,IDC_STATIC_RARU4,
      //          IDC_STATIC_RARI1,IDC_STATIC_RARI2,IDC_STATIC_RARI3,IDC_STATIC_RARI4};

        // static UINT aGrREALLOCR1U[] = {IDC_REALLOCR1UK4,IDC_REALLOCR1UK3,IDC_REALLOCR1UK2,IDC_REALLOCR1UK1};
        // static UINT aGrREALLOCR1I[] = {IDC_REALLOCR1ISK4,IDC_REALLOCR1ISK3,IDC_REALLOCR1ISK2,IDC_REALLOCR1ISK1};
        // static UINT aGrREALLOCR2U[] = {IDC_REALLOCR2UK4,IDC_REALLOCR2UK3,IDC_REALLOCR2UK2,IDC_REALLOCR2UK1};
        // static UINT aGrREALLOCR2I[] = {IDC_REALLOCR2ISK4,IDC_REALLOCR2ISK3,IDC_REALLOCR2ISK2,IDC_REALLOCR2ISK1};
        // static UINT aGrREALLOCR3U[] = {IDC_REALLOCR3UK4,IDC_REALLOCR3UK3,IDC_REALLOCR3UK2,IDC_REALLOCR3UK1};
        // static UINT aGrREALLOCR3I[] = {IDC_REALLOCR3ISK4,IDC_REALLOCR3ISK3,IDC_REALLOCR3ISK2,IDC_REALLOCR3ISK1};
        // static UINT aGrREALLOCR4U[] = {IDC_REALLOCR4UK4,IDC_REALLOCR4UK3,IDC_REALLOCR4UK2,IDC_REALLOCR4UK1};
        // static UINT aGrREALLOCR4I[] = {IDC_REALLOCR4ISK4,IDC_REALLOCR4ISK3,IDC_REALLOCR4ISK2,IDC_REALLOCR4ISK1};

     boolean fVisible=((m_SWVerPri) >= 82);

     CWnd *pwnd;
     for (int uIndex = 0; uIndex < SIZEOF_ARRAY(aGrRRSTATIC); uIndex++)
     {
         pwnd  = GetDlgItem(aGrRRSTATIC[uIndex]);
         pwnd->ShowWindow(fVisible);
     }


        SetRelSw(mmCtlReAllocR1U,m_Realloc[0].rel_on,fVisible);

        SetRelSw(mmCtlReAllocR1I,m_Realloc[0].rel_of,fVisible);

        SetRelSw(mmCtlReAllocR2U,m_Realloc[1].rel_on,fVisible);

        SetRelSw(mmCtlReAllocR2I,m_Realloc[1].rel_of,fVisible);


        SetRelSw(mmCtlReAllocR3U,m_Realloc[2].rel_on,fVisible);

        SetRelSw(mmCtlReAllocR3I,m_Realloc[2].rel_of,fVisible);


        SetRelSw(mmCtlReAllocR4U,m_Realloc[3].rel_on,fVisible);

        SetRelSw(mmCtlReAllocR4I,m_Realloc[3].rel_of,fVisible);
    }
*/

    private String GetHMSfromInt(int time) {
        int hh, mm, ss;
        if (time >= (24 * 3600)) {
            time = 0;
        }
        ss = time % 60;
        time /= 60;
        mm = time % 60;

        hh = time / 60;

        return String.format("%02d:%02d:%02d", hh, mm, ss);
    }

    private String GetHMfromInt(int time) {
        int hh, mm, ss;
        if (time >= (24 * 60)) {
            time = 0;
        }
        mm = time % 60;
        hh = time / 60;

        ss = 0;


        return String.format("%02d:%02d:%02d", hh, mm, ss);
    }

    private void showTimePairs(Opprog[] m_PProg_R1, Opprog[] m_PProg_R2, Opprog[] m_PProg_R3, Opprog[] m_PProg_R4, TableRow[] workTB1, TableRow[] workTB2, TableRow[] workTB3) {
        if (fVis_Versacom) {

            for (int relej = 1; relej <= 4; relej++) {
                for (int rp = 0; rp < 16; rp++) {
                    GetRelVremPar(relej, rp, m_PProg_R1, m_PProg_R2, m_PProg_R3, m_PProg_R4, workTB1, workTB2, workTB3);
                }
            }
            System.out.print("");
        }
    }

    private void GetRelVremPar(int relej, int rp, Opprog[] m_PProg_R1, Opprog[] m_PProg_R2, Opprog[] m_PProg_R3, Opprog[] m_PProg_R4, TableRow[] workTB1, TableRow[] workTB2, TableRow[] workTB3) {
        int cnt = 0;

        switch (relej) {
            case 1:

                for (int iItem = 0; iItem < (int) m_CFG.cNpar; iItem++) {
                    if ((m_PProg_R1[rp].AkTim & iVtmask[iItem]) != 0) {

                        workTB1[cnt1] = new TableRow(this);
                        workSchedTime1[cnt1][iItem] = new TextView(this);
                        timePair1[cnt1][iItem] = new TextView(this);
                        Ta_test1[cnt1][iItem] = new TextView(this);
                        Tb_test1[cnt1][iItem] = new TextView(this);

                        workSchedTime1[cnt1][iItem].setLayoutParams(paramsExample);
                        timePair1[cnt1][iItem].setLayoutParams(paramsExample);
                        Ta_test1[cnt1][iItem].setLayoutParams(paramsExample);
                        Tb_test1[cnt1][iItem].setLayoutParams(paramsExample);

                        workSchedTime1[cnt1][iItem].setBackgroundResource(R.color.whiteColor);
                        timePair1[cnt1][iItem].setBackgroundResource(R.color.whiteColor);
                        Ta_test1[cnt1][iItem].setBackgroundResource(R.color.whiteColor);
                        Tb_test1[cnt1][iItem].setBackgroundResource(R.color.whiteColor);

                        cnt++;
                        if (cnt == 1) {
                            workSchedTime1[cnt1][iItem].setText(String.valueOf(cntWork1 + 1));
                            cntWork1++;
                        }

                        timePair1[cnt1][iItem].setText(String.format("%02d", iItem + 1));

                        Ta_test1[cnt1][iItem].setText(String.format("%02d:%02d", (m_PProg_R1[rp].Tpro[iItem].Ton) / 60, (m_PProg_R1[rp].Tpro[iItem].Ton) % 60));
                        Tb_test1[cnt1][iItem].setText(String.format("%02d:%02d", (m_PProg_R1[rp].Tpro[iItem].Toff) / 60, (m_PProg_R1[rp].Tpro[iItem].Toff) % 60));


                        workTB1[cnt1].addView(workSchedTime1[cnt1][iItem]);
                        workTB1[cnt1].addView(timePair1[cnt1][iItem]);
                        workTB1[cnt1].addView(Ta_test1[cnt1][iItem]);
                        workTB1[cnt1].addView(Tb_test1[cnt1][iItem]);

                        //   tableLayout.addView(workTB1[iItem]);
                        cnt1++;
                    }

                }

                break;
            case 2:
                for (int iItem = 0; iItem < (int) m_CFG.cNpar; iItem++) {
                    if ((m_PProg_R2[rp].AkTim & iVtmask[iItem]) != 0) {

                        workTB2[cnt2] = new TableRow(this);
                        workSchedTime2[cnt2][iItem] = new TextView(this);
                        timePair2[cnt2][iItem] = new TextView(this);
                        Ta_test2[cnt2][iItem] = new TextView(this);
                        Tb_test2[cnt2][iItem] = new TextView(this);

                        workSchedTime2[cnt2][iItem].setLayoutParams(paramsExample);
                        timePair2[cnt2][iItem].setLayoutParams(paramsExample);
                        Ta_test2[cnt2][iItem].setLayoutParams(paramsExample);
                        Tb_test2[cnt2][iItem].setLayoutParams(paramsExample);

                        workSchedTime2[cnt2][iItem].setBackgroundResource(R.color.whiteColor);
                        timePair2[cnt2][iItem].setBackgroundResource(R.color.whiteColor);
                        Ta_test2[cnt2][iItem].setBackgroundResource(R.color.whiteColor);
                        Tb_test2[cnt2][iItem].setBackgroundResource(R.color.whiteColor);

                        cnt++;
                        if (cnt == 1) {
                            workSchedTime2[cnt2][iItem].setText(String.valueOf(cntWork2 + 1));
                            cntWork2++;
                        }

                        timePair2[cnt2][iItem].setText(String.format("%02d", iItem + 1));

                        Ta_test2[cnt2][iItem].setText(String.format("%02d:%02d", (m_PProg_R2[rp].Tpro[iItem].Ton) / 60, (m_PProg_R2[rp].Tpro[iItem].Ton) % 60));
                        Tb_test2[cnt2][iItem].setText(String.format("%02d:%02d", (m_PProg_R2[rp].Tpro[iItem].Toff) / 60, (m_PProg_R2[rp].Tpro[iItem].Toff) % 60));


                        workTB2[cnt2].addView(workSchedTime2[cnt2][iItem]);
                        workTB2[cnt2].addView(timePair2[cnt2][iItem]);
                        workTB2[cnt2].addView(Ta_test2[cnt2][iItem]);
                        workTB2[cnt2].addView(Tb_test2[cnt2][iItem]);

                        //   tableLayout.addView(workTB2[iItem]);
                        cnt2++;
                    }

                }

                break;
            case 3:

                for (int iItem = 0; iItem < (int) m_CFG.cNpar; iItem++) {
                    if ((m_PProg_R3[rp].AkTim & iVtmask[iItem]) != 0) {

                        workTB3[cnt3] = new TableRow(this);
                        workSchedTime3[cnt3][iItem] = new TextView(this);
                        timePair3[cnt3][iItem] = new TextView(this);
                        Ta_test3[cnt3][iItem] = new TextView(this);
                        Tb_test3[cnt3][iItem] = new TextView(this);

                        workSchedTime3[cnt3][iItem].setLayoutParams(paramsExample);
                        timePair3[cnt3][iItem].setLayoutParams(paramsExample);
                        Ta_test3[cnt3][iItem].setLayoutParams(paramsExample);
                        Tb_test3[cnt3][iItem].setLayoutParams(paramsExample);

                        workSchedTime3[cnt3][iItem].setBackgroundResource(R.color.whiteColor);
                        timePair3[cnt3][iItem].setBackgroundResource(R.color.whiteColor);
                        Ta_test3[cnt3][iItem].setBackgroundResource(R.color.whiteColor);
                        Tb_test3[cnt3][iItem].setBackgroundResource(R.color.whiteColor);

                        cnt++;
                        if (cnt == 1) {
                            workSchedTime3[cnt3][iItem].setText(String.valueOf(cntWork3 + 1));

                            cntWork3++;
                        }

                        timePair3[cnt3][iItem].setText(String.format("%02d", iItem + 1));

                        Ta_test3[cnt3][iItem].setText(String.format("%02d:%02d", (m_PProg_R3[rp].Tpro[iItem].Ton) / 60, (m_PProg_R3[rp].Tpro[iItem].Ton) % 60));
                        Tb_test3[cnt3][iItem].setText(String.format("%02d:%02d", (m_PProg_R3[rp].Tpro[iItem].Toff) / 60, (m_PProg_R3[rp].Tpro[iItem].Toff) % 60));


                        workTB3[cnt3].addView(workSchedTime3[cnt3][iItem]);
                        workTB3[cnt3].addView(timePair3[cnt3][iItem]);
                        workTB3[cnt3].addView(Ta_test3[cnt3][iItem]);
                        workTB3[cnt3].addView(Tb_test3[cnt3][iItem]);

                        //   tableLayout.addView(workTB3[iItem]);
                        cnt3++;
                    }

                }

                break;

        }
    }

    public void DisplayGeneral() {
        TableRow[] generalTR = new TableRow[17];
        TextView[] generalTV = new TextView[17 * 2];
        String str = "";

        switch (m_HWVerPri) {
            case TIP_S:
                str = "S";
                break;
            case TIP_SN:
                str = "SN";
                break;
            case TIP_SPA:
                str = "SPA";
                break;
            case TIP_PAS:
                str = "PAS";
                break;
            case TIP_PA:
                str = "PA";
                break;
            case TIP_PASN:
                str = "PASN";
                break;
            case TIP_SPN:
                str = "SPN";
                break;
            case TIP_PS:
                str = "PS";
                break;

        }

        TableRow TRdeviceType = new TableRow(this);
        TextView deviceType = new TextView(this);
        TextView deviceTypeRes = new TextView(this);
        deviceType.setText("Device type");
        deviceType.setLayoutParams(paramsExample);
        deviceType.setBackgroundResource(R.color.title);
        deviceTypeRes.setText(String.format("MTK-%d-%s-V-%d", m_tip + 1, str, m_SWVerPri));
        deviceTypeRes.setLayoutParams(paramsExample);
        deviceTypeRes.setBackgroundResource(R.color.whiteColor);
        TRdeviceType.addView(deviceType);
        TRdeviceType.addView(deviceTypeRes);

        tableLayout.addView(TRdeviceType);

        if (fVis_RefPrij) {
            TableRow TRHDOfrequency = new TableRow(this);
            TextView HDOfrequency = new TextView(this);
            TextView HDOfrequencyRes = new TextView(this);
            HDOfrequency.setText("HDO frequency ");
            HDOfrequency.setLayoutParams(paramsExample);
            HDOfrequency.setBackgroundResource(R.color.title);
            HDOfrequencyRes.setLayoutParams(paramsExample);
            HDOfrequencyRes.setBackgroundResource(R.color.whiteColor);
            TRHDOfrequency.addView(HDOfrequency);
            TRHDOfrequency.addView(HDOfrequencyRes);

            int broj;
            if (m_SWVerPri >= 90) {
                broj = m_ParFilteraCF.BROJ;
                if (broj >= 0) {
                    HDOfrequencyRes.setText(String.format("%4.2f Hz", TbParFilteraVer9[broj].fre));
                }
            } else {
                broj = m_ParFiltera.BROJ;
                if (broj >= 0) {
                    if (m_SWVerPri < 80) {
                        HDOfrequencyRes.setText(String.format("%4.2f Hz", TbParFiltera[broj].fre)); //ptabpar=(STR_PARFIL *)&TbParFiltera[broj];
                    } else {
                        HDOfrequencyRes.setText(String.format("%4.2f Hz", TbParFiltera9_8MHz[broj].fre)); //ptabpar=(STR_PARFIL *)&TbParFiltera9_8MHz[broj];
                    }


                }
            }

            tableLayout.addView(TRHDOfrequency);


            TableRow TRraster = new TableRow(this);
            TextView raster = new TextView(this);
            TextView rasterRes = new TextView(this);
            raster.setText("Raster");
            raster.setLayoutParams(paramsExample);
            raster.setBackgroundResource(R.color.title);
            rasterRes.setLayoutParams(paramsExample);
            rasterRes.setBackgroundResource(R.color.whiteColor);
            TRraster.addView(raster);
            TRraster.addView(rasterRes);
            String[] rra = {"Semagyr 50a", "Ricontic b", "Pulsadis(EdF)", "Inematic 2000", "ZPA-I-I", "ZPA-I-Ik", "CEZ 50D", "CEZ 50K"};
            rasterRes.setText(rra[data.m_BrojRast]);
            IsCZ44raster = (data.m_BrojRast == 4 || data.m_BrojRast == 5);
            IsCZRaster = ((data.m_BrojRast) > 3) && ((data.m_BrojRast) < 8);

            tableLayout.addView(TRraster);


            TableRow TRSensitivity = new TableRow(this);
            TextView sensitivity = new TextView(this);
            TextView sensitivityRes = new TextView(this);
            sensitivity.setText("Sensitivity");
            sensitivity.setLayoutParams(paramsExample);
            sensitivity.setBackgroundResource(R.color.title);
            sensitivityRes.setLayoutParams(paramsExample);
            sensitivityRes.setBackgroundResource(R.color.whiteColor);
            TRSensitivity.addView(sensitivity);
            TRSensitivity.addView(sensitivityRes);

            sensitivityRes.setText(String.format("%4.2f %%", data.m_Utf_posto));

            tableLayout.addView(TRSensitivity);


            TableRow TRTelRaster = new TableRow(this);
            TextView telRaster = new TextView(this);
            TextView telRasterRes = new TextView(this);
            telRaster.setText("Tel. raster time base");
            telRaster.setLayoutParams(paramsExample);
            telRaster.setBackgroundResource(R.color.title);
            telRasterRes.setLayoutParams(paramsExample);
            telRasterRes.setBackgroundResource(R.color.whiteColor);

            TRTelRaster.addView(telRaster);
            TRTelRaster.addView(telRasterRes);

            if ((m_op50Prij.RTCSinh & 0x80) != 0) {// m_General50.m_RTCSinh
                telRasterRes.setText("Network(50Hz)");
            } else {
                telRasterRes.setText("Clock");
            }


            tableLayout.addView(TRTelRaster);
        }


        TableRow TRRTCTimeBase = new TableRow(this);
        TextView RTCTimeBase = new TextView(this);
        TextView RTCTimeBaseRes = new TextView(this);
        RTCTimeBase.setText("RTC time base");
        RTCTimeBase.setLayoutParams(paramsExample);
        RTCTimeBase.setBackgroundResource(R.color.title);
        RTCTimeBaseRes.setLayoutParams(paramsExample);
        RTCTimeBaseRes.setBackgroundResource(R.color.whiteColor);

        TRRTCTimeBase.addView(RTCTimeBase);
        TRRTCTimeBase.addView(RTCTimeBaseRes);

        if ((m_op50Prij.RTCSinh & 0x03) != 0) {// m_General50.m_RasTSinh = 1;
            RTCTimeBaseRes.setText(String.format("Quartz 32768 Hz"));
        } else {
            RTCTimeBaseRes.setText(String.format("Network(50Hz)"));
        }

        tableLayout.addView(TRRTCTimeBase);


        if (!fVis_VersacomPS) {
            String[] rtcloss = {"None", "a", "b", "Same as PWON", "Continue with 50Hz"};

            TableRow TRRTCLossAction = new TableRow(this);
            TextView RTCLossAction = new TextView(this);
            TextView RTCLossActionRes = new TextView(this);
            RTCLossAction.setText("RTC Loss action");
            RTCLossAction.setLayoutParams(paramsExample);
            RTCLossAction.setBackgroundResource(R.color.title);
            RTCLossActionRes.setLayoutParams(paramsExample);
            RTCLossActionRes.setBackgroundResource(R.color.whiteColor);

            TRRTCLossAction.addView(RTCLossAction);
            TRRTCLossAction.addView(RTCLossActionRes);


            int inx = (byte) ((m_op50Prij.RTCSinh >> TIM_LOSS_RTC_POS) & 0x0F);

            if (inx > 3) {
                inx = 0;
            }

            RTCLossActionRes.setText(rtcloss[inx]);


            tableLayout.addView(TRRTCLossAction);
        }


        if (fVis_RefPrij) {


            if (fVis_VersacomPS) {

                TableRow TRAdressLength = new TableRow(this);
                TextView AdressLength = new TextView(this);
                TextView AdressLengthRes = new TextView(this);
                AdressLength.setText("Address length of telegram DIN-43861-301 ");
                AdressLength.setLayoutParams(paramsExample);
                AdressLength.setBackgroundResource(R.color.title);
                AdressLengthRes.setLayoutParams(paramsExample);
                AdressLengthRes.setBackgroundResource(R.color.whiteColor);
                TRAdressLength.addView(AdressLength);
                TRAdressLength.addView(AdressLengthRes);


                AdressLength.setText(String.format("%d", oprij.VDuzAdr));

                tableLayout.addView(TRAdressLength);

            }

            if (!fVis_Cz95P) {


                TableRow TRSyncTelegram = new TableRow(this);
                TextView SyncTelegram = new TextView(this);
                TextView SyncTelegramRes = new TextView(this);
                SyncTelegram.setText("synchronization telegram - day >> time ");
                SyncTelegram.setLayoutParams(paramsExample);
                SyncTelegram.setBackgroundResource(R.color.title);
                SyncTelegramRes.setLayoutParams(paramsExample);
                SyncTelegramRes.setBackgroundResource(R.color.whiteColor);

                TRSyncTelegram.addView(SyncTelegram);
                TRSyncTelegram.addView(SyncTelegramRes);


                SyncTelegram.setText(String.format("%d", oprij.VDuzAdr));

                tableLayout.addView(TRSyncTelegram);

//Kasnije napravit

                // m_htmlParamFile << "        <tr>\n";
                // m_htmlParamFile << "            <th> "<< CMsg(IDSI_SYNCTGTIME) << "(hh:mm:ss)</th>\n";
                // m_htmlParamFile << TD( GetSyncTime(pFrameWnd->m_op50Prij.SinhTime[0],pFrameWnd->m_HWVerPri)  );
                // m_htmlParamFile << "        </tr>\n";
            }


            if (fVis_VersacomPS) {
                TableRow TRID = new TableRow(this);
                TextView ID = new TextView(this);
                TextView IDRes = new TextView(this);
                ID.setText("ID");
                ID.setLayoutParams(paramsExample);
                ID.setBackgroundResource(R.color.title);
                IDRes.setLayoutParams(paramsExample);
                IDRes.setBackgroundResource(R.color.whiteColor);

                TRID.addView(ID);
                TRID.addView(IDRes);


                IDRes.setText(String.format("%d", oprij.VIdBr));

                tableLayout.addView(TRID);


            }


            if (!fVis_Cz95P) {

                TableRow TRIDSI_24HC_ACT = new TableRow(this);
                TextView IDSI_24HC_ACT = new TextView(this);
                TextView IDSI_24HC_ACTRes = new TextView(this);
                IDSI_24HC_ACT.setText("24h cycle - active ");
                IDSI_24HC_ACT.setLayoutParams(paramsExample);
                IDSI_24HC_ACT.setBackgroundResource(R.color.title);
                IDSI_24HC_ACTRes.setLayoutParams(paramsExample);
                IDSI_24HC_ACTRes.setBackgroundResource(R.color.whiteColor);

                TRIDSI_24HC_ACT.addView(IDSI_24HC_ACT);
                TRIDSI_24HC_ACT.addView(IDSI_24HC_ACTRes);
                String datstr = (oprij.ParFlags & 0x1) != 0 ? "Yes" : "No";

                IDSI_24HC_ACTRes.setText(datstr);

                tableLayout.addView(TRIDSI_24HC_ACT);


                TableRow TRIDSI_24HC_DLY = new TableRow(this);
                TextView IDSI_24HC_DLY = new TextView(this);
                TextView IDSI_24HC_DLYRes = new TextView(this);
                IDSI_24HC_DLY.setText("24h cycle - delay ");
                IDSI_24HC_DLY.setLayoutParams(paramsExample);
                IDSI_24HC_DLY.setBackgroundResource(R.color.title);
                IDSI_24HC_DLYRes.setLayoutParams(paramsExample);
                IDSI_24HC_DLYRes.setBackgroundResource(R.color.whiteColor);

                TRIDSI_24HC_DLY.addView(IDSI_24HC_DLY);
                TRIDSI_24HC_DLY.addView(IDSI_24HC_DLYRes);

                IDSI_24HC_ACTRes.setText(String.format("%02d:%02d", oprij.Dly24H / 60, oprij.Dly24H % 60));

                tableLayout.addView(IDSI_24HC_DLY);


            } else {
                TableRow TRIDSI_SYNC_TRACK = new TableRow(this);
                TextView IDSI_SYNC_TRACK = new TextView(this);
                TextView IDSI_SYNC_TRACKRes = new TextView(this);
                IDSI_SYNC_TRACK.setText("Track relay position after time synchronization ");
                IDSI_SYNC_TRACK.setLayoutParams(paramsExample);
                IDSI_SYNC_TRACK.setBackgroundResource(R.color.title);
                IDSI_SYNC_TRACKRes.setLayoutParams(paramsExample);
                IDSI_SYNC_TRACKRes.setBackgroundResource(R.color.whiteColor);

                TRIDSI_SYNC_TRACK.addView(IDSI_SYNC_TRACK);
                TRIDSI_SYNC_TRACK.addView(IDSI_SYNC_TRACKRes);
                String datstr = (m_op50Prij.RTCSinh & SINH_REL_POS_MASK) != 0 ? "Yes" : "No";

                IDSI_SYNC_TRACKRes.setText(datstr);

                tableLayout.addView(TRIDSI_SYNC_TRACK);


            }
        }


        TableRow TRPowerBridge = new TableRow(this);
        TextView PowerBridge = new TextView(this);
        TextView PowerBridgeRes = new TextView(this);
        PowerBridge.setText("Power bridging time");
        PowerBridge.setLayoutParams(paramsExample);
        PowerBridge.setBackgroundResource(R.color.title);
        PowerBridgeRes.setLayoutParams(paramsExample);
        PowerBridgeRes.setBackgroundResource(R.color.whiteColor);

        TRPowerBridge.addView(PowerBridge);
        TRPowerBridge.addView(PowerBridgeRes);

        float timebridge = (m_op50Prij.CPWBRTIME * 5);

        PowerBridgeRes.setText(String.format("%.2f s", timebridge / 1000));

        tableLayout.addView(TRPowerBridge);


        TableRow TRParameters = new TableRow(this);
        TextView Parameters = new TextView(this);
        Parameters.setText("Parameters");


        TRParameters.addView(Parameters);
        tableLayout.addView(TRParameters);

    }

    public void GetVerPri(List<Character> str, String MTKStr) {
        int cnt;
        char ch;
        int i;
        String tempStr;
        m_HWVerPri = -1;
        m_SWVerPri = 0;
        if (str.indexOf(Character.valueOf((char) '\n')) > 8) {
            cnt = 0;

            do {
                tempStr = CTipPrij[cnt];
                if (MTKStr.contains(tempStr)) {
                    m_HWVerPri = cnt;
                    i = str.indexOf('V');
                    if (i > 6) {
                        ch = str.get(i + 1);
                        if ((ch >= '0') && (ch <= '9')) {
                            m_SWVerPri = (ch - '0') * 10;
                            ch = str.get(i + 3);
                            if ((ch >= '0') && (ch <= '9'))
                                m_SWVerPri += ch - '0';
                        }
                    }
                    break;
                    //return;
                }
                tempStr = CTipPrij[++cnt];
            } while (cnt < 8);
        }

        i = str.indexOf(';');
        if (i > 8) {
            byte[] dbuf = new byte[32];
            int j = StrCpyhextobuf(str, dbuf, i + 1);
            if (j >= 3) {
                m_CFG.cBrparam = dbuf[0];
                int a = (dbuf[1] & 0xFF);
                int b = (dbuf[2] & 0xFF);
                m_CFG.cID = 256 * a + b;

                i = m_CFG.cBrparam - 2;

                // for (int k=0;k<i;k++){
                //     m_CFG.
                // }
                m_CFG.cPcbRev = dbuf[3];
                m_CFG.cNrel = dbuf[4];
                m_CFG.cRtc = dbuf[5];
                m_CFG.cNprog = dbuf[6];
                m_CFG.cNpar = dbuf[7];
            }


        }
    }

    private int StrCpyhextobuf(List<Character> str, byte[] buf, int nIndex) {
        int len = (str.size() - nIndex) / 2;
        int i = 0;

        byte lb, hb;

        int err = 0;
        int glIndex = 0;
        while (i++ < len) {
            hb = (byte) str.get(nIndex++).charValue();
            lb = (byte) str.get(nIndex++).charValue();
            if ((hb == ')') || (lb == ')')) {
                break;
            }
            if ((hb == '\r') || (lb == '\r')) {
                break;
            }
            if ((hb == '\n') || (lb == '\n')) {
                break;
            }

            hb = HextoD(hb, lb);

            buf[glIndex++] = hb;

        }


        //CString str2;
//	str2.Format("nIndex=%02X chk=%02X \r\n",nIndex,(BYTE)chksum);
//	ShowData(str2);
//	if(chksum !=0)return(-1);
        return (i - 1);//makni cheksum
    }

    private byte HextoD(byte hb, byte lb) {
        byte mb;
        mb = 0;

        if (((hb >= '0') && (hb <= '9')) || ((hb >= 'A') && (hb <= 'F')) ||
                ((lb >= '0') && (lb <= '9')) || ((lb >= 'A') && (lb <= 'F'))) {
            mb = (hb >= 'A') ? (byte) ((hb - '7') * 16) : (byte) ((hb - '0') * 16);


            mb = (lb >= 'A') ? (byte) (mb + (lb - '7')) : (byte) (mb + (lb - '0'));
        }

        return (mb);
    }


    public String getStringRepresentation(List<Character> list) {
        StringBuilder builder = new StringBuilder(list.size());
        for (Character ch : list) {
            builder.append(ch);
        }
        return builder.toString();
    }

    public void writeHTML() throws IOException {
        String html = "<html><head><title>Title</title></head><body>This is random text.</body></html>";
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("myfile.html", Context.MODE_PRIVATE));
        outputStreamWriter.write(html);
        outputStreamWriter.close();
    }


    private String readFromFile(String filename, Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(filename);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}

