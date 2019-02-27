package hr.fer.ruazosa.bluetooth;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;


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


    String CSS = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
            "<html>\n" +
            "\t<head>\n" +
            "     <meta content=\"text/html; charset=windows-1250\" http-equiv=\"Content-Type\">\n" +
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=7\" > \n" +
            "\t\t<title>Lista parametara</title>\n" +
            "\t\t<style>\n" +
            "/*! normalize.css v3.0.2 | MIT License | git.io/normalize */\n" +
            "\n" +
            "/**\n" +
            " * 1. Set default font family to sans-serif.\n" +
            " * 2. Prevent iOS text size adjust after orientation change, without disabling\n" +
            " *    user zoom.\n" +
            " */\n" +
            "\n" +
            "html {\n" +
            "  font-family: sans-serif; /* 1 */\n" +
            "  -ms-text-size-adjust: 100%; /* 2 */\n" +
            "  -webkit-text-size-adjust: 100%; /* 2 */\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Remove default margin.\n" +
            " */\n" +
            "\n" +
            "body {\n" +
            "  margin: 0;\n" +
            "}\n" +
            "\n" +
            "/* HTML5 display definitions\n" +
            "   ========================================================================== */\n" +
            "\n" +
            "/**\n" +
            " * Correct `block` display not defined for any HTML5 element in IE 8/9.\n" +
            " * Correct `block` display not defined for `details` or `summary` in IE 10/11\n" +
            " * and Firefox.\n" +
            " * Correct `block` display not defined for `main` in IE 11.\n" +
            " */\n" +
            "\n" +
            "article,\n" +
            "aside,\n" +
            "details,\n" +
            "figcaption,\n" +
            "figure,\n" +
            "footer,\n" +
            "header,\n" +
            "hgroup,\n" +
            "main,\n" +
            "menu,\n" +
            "nav,\n" +
            "section,\n" +
            "summary {\n" +
            "  display: block;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * 1. Correct `inline-block` display not defined in IE 8/9.\n" +
            " * 2. Normalize vertical alignment of `progress` in Chrome, Firefox, and Opera.\n" +
            " */\n" +
            "\n" +
            "audio,\n" +
            "canvas,\n" +
            "progress,\n" +
            "video {\n" +
            "  display: inline-block; /* 1 */\n" +
            "  vertical-align: baseline; /* 2 */\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Prevent modern browsers from displaying `audio` without controls.\n" +
            " * Remove excess height in iOS 5 devices.\n" +
            " */\n" +
            "\n" +
            "audio:not([controls]) {\n" +
            "  display: none;\n" +
            "  height: 0;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Address `[hidden]` styling not present in IE 8/9/10.\n" +
            " * Hide the `template` element in IE 8/9/11, Safari, and Firefox < 22.\n" +
            " */\n" +
            "\n" +
            "[hidden],\n" +
            "template {\n" +
            "  display: none;\n" +
            "}\n" +
            "\n" +
            "/* Links\n" +
            "   ========================================================================== */\n" +
            "\n" +
            "/**\n" +
            " * Remove the gray background color from active links in IE 10.\n" +
            " */\n" +
            "\n" +
            "a {\n" +
            "  background-color: transparent;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Improve readability when focused and also mouse hovered in all browsers.\n" +
            " */\n" +
            "\n" +
            "a:active,\n" +
            "a:hover {\n" +
            "  outline: 0;\n" +
            "}\n" +
            "\n" +
            "/* Text-level semantics\n" +
            "   ========================================================================== */\n" +
            "\n" +
            "/**\n" +
            " * Address styling not present in IE 8/9/10/11, Safari, and Chrome.\n" +
            " */\n" +
            "\n" +
            "abbr[title] {\n" +
            "  border-bottom: 1px dotted;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Address style set to `bolder` in Firefox 4+, Safari, and Chrome.\n" +
            " */\n" +
            "\n" +
            "b,\n" +
            "strong {\n" +
            "  font-weight: bold;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Address styling not present in Safari and Chrome.\n" +
            " */\n" +
            "\n" +
            "dfn {\n" +
            "  font-style: italic;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Address variable `h1` font-size and margin within `section` and `article`\n" +
            " * contexts in Firefox 4+, Safari, and Chrome.\n" +
            " */\n" +
            "\n" +
            "h1 {\n" +
            "  font-size: 2em;\n" +
            "  margin: 0.67em 0;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Address styling not present in IE 8/9.\n" +
            " */\n" +
            "\n" +
            "mark {\n" +
            "  background: #ff0;\n" +
            "  color: #000;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Address inconsistent and variable font size in all browsers.\n" +
            " */\n" +
            "\n" +
            "small {\n" +
            "  font-size: 80%;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Prevent `sub` and `sup` affecting `line-height` in all browsers.\n" +
            " */\n" +
            "\n" +
            "sub,\n" +
            "sup {\n" +
            "  font-size: 75%;\n" +
            "  line-height: 0;\n" +
            "  position: relative;\n" +
            "  vertical-align: baseline;\n" +
            "}\n" +
            "\n" +
            "sup {\n" +
            "  top: -0.5em;\n" +
            "}\n" +
            "\n" +
            "sub {\n" +
            "  bottom: -0.25em;\n" +
            "}\n" +
            "\n" +
            "/* Embedded content\n" +
            "   ========================================================================== */\n" +
            "\n" +
            "/**\n" +
            " * Remove border when inside `a` element in IE 8/9/10.\n" +
            " */\n" +
            "\n" +
            "img {\n" +
            "  border: 0;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Correct overflow not hidden in IE 9/10/11.\n" +
            " */\n" +
            "\n" +
            "svg:not(:root) {\n" +
            "  overflow: hidden;\n" +
            "}\n" +
            "\n" +
            "/* Grouping content\n" +
            "   ========================================================================== */\n" +
            "\n" +
            "/**\n" +
            " * Address margin not present in IE 8/9 and Safari.\n" +
            " */\n" +
            "\n" +
            "figure {\n" +
            "  margin: 1em 40px;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Address differences between Firefox and other browsers.\n" +
            " */\n" +
            "\n" +
            "hr {\n" +
            "  -moz-box-sizing: content-box;\n" +
            "  box-sizing: content-box;\n" +
            "  height: 0;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Contain overflow in all browsers.\n" +
            " */\n" +
            "\n" +
            "pre {\n" +
            "  overflow: auto;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Address odd `em`-unit font size rendering in all browsers.\n" +
            " */\n" +
            "\n" +
            "code,\n" +
            "kbd,\n" +
            "pre,\n" +
            "samp {\n" +
            "  font-family: monospace, monospace;\n" +
            "  font-size: 1em;\n" +
            "}\n" +
            "\n" +
            "/* Forms\n" +
            "   ========================================================================== */\n" +
            "\n" +
            "/**\n" +
            " * Known limitation: by default, Chrome and Safari on OS X allow very limited\n" +
            " * styling of `select`, unless a `border` property is set.\n" +
            " */\n" +
            "\n" +
            "/**\n" +
            " * 1. Correct color not being inherited.\n" +
            " *    Known issue: affects color of disabled elements.\n" +
            " * 2. Correct font properties not being inherited.\n" +
            " * 3. Address margins set differently in Firefox 4+, Safari, and Chrome.\n" +
            " */\n" +
            "\n" +
            "button,\n" +
            "input,\n" +
            "optgroup,\n" +
            "select,\n" +
            "textarea {\n" +
            "  color: inherit; /* 1 */\n" +
            "  font: inherit; /* 2 */\n" +
            "  margin: 0; /* 3 */\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Address `overflow` set to `hidden` in IE 8/9/10/11.\n" +
            " */\n" +
            "\n" +
            "button {\n" +
            "  overflow: visible;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Address inconsistent `text-transform` inheritance for `button` and `select`.\n" +
            " * All other form control elements do not inherit `text-transform` values.\n" +
            " * Correct `button` style inheritance in Firefox, IE 8/9/10/11, and Opera.\n" +
            " * Correct `select` style inheritance in Firefox.\n" +
            " */\n" +
            "\n" +
            "button,\n" +
            "select {\n" +
            "  text-transform: none;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * 1. Avoid the WebKit bug in Android 4.0.* where (2) destroys native `audio`\n" +
            " *    and `video` controls.\n" +
            " * 2. Correct inability to style clickable `input` types in iOS.\n" +
            " * 3. Improve usability and consistency of cursor style between image-type\n" +
            " *    `input` and others.\n" +
            " */\n" +
            "\n" +
            "button,\n" +
            "html input[type=\"button\"], /* 1 */\n" +
            "input[type=\"reset\"],\n" +
            "input[type=\"submit\"] {\n" +
            "  -webkit-appearance: button; /* 2 */\n" +
            "  cursor: pointer; /* 3 */\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Re-set default cursor for disabled elements.\n" +
            " */\n" +
            "\n" +
            "button[disabled],\n" +
            "html input[disabled] {\n" +
            "  cursor: default;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Remove inner padding and border in Firefox 4+.\n" +
            " */\n" +
            "\n" +
            "button::-moz-focus-inner,\n" +
            "input::-moz-focus-inner {\n" +
            "  border: 0;\n" +
            "  padding: 0;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Address Firefox 4+ setting `line-height` on `input` using `!important` in\n" +
            " * the UA stylesheet.\n" +
            " */\n" +
            "\n" +
            "input {\n" +
            "  line-height: normal;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * It's recommended that you don't attempt to style these elements.\n" +
            " * Firefox's implementation doesn't respect box-sizing, padding, or width.\n" +
            " *\n" +
            " * 1. Address box sizing set to `content-box` in IE 8/9/10.\n" +
            " * 2. Remove excess padding in IE 8/9/10.\n" +
            " */\n" +
            "\n" +
            "input[type=\"checkbox\"],\n" +
            "input[type=\"radio\"] {\n" +
            "  box-sizing: border-box; /* 1 */\n" +
            "  padding: 0; /* 2 */\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Fix the cursor style for Chrome's increment/decrement buttons. For certain\n" +
            " * `font-size` values of the `input`, it causes the cursor style of the\n" +
            " * decrement button to change from `default` to `text`.\n" +
            " */\n" +
            "\n" +
            "input[type=\"number\"]::-webkit-inner-spin-button,\n" +
            "input[type=\"number\"]::-webkit-outer-spin-button {\n" +
            "  height: auto;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * 1. Address `appearance` set to `searchfield` in Safari and Chrome.\n" +
            " * 2. Address `box-sizing` set to `border-box` in Safari and Chrome\n" +
            " *    (include `-moz` to future-proof).\n" +
            " */\n" +
            "\n" +
            "input[type=\"search\"] {\n" +
            "  -webkit-appearance: textfield; /* 1 */\n" +
            "  -moz-box-sizing: content-box;\n" +
            "  -webkit-box-sizing: content-box; /* 2 */\n" +
            "  box-sizing: content-box;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Remove inner padding and search cancel button in Safari and Chrome on OS X.\n" +
            " * Safari (but not Chrome) clips the cancel button when the search input has\n" +
            " * padding (and `textfield` appearance).\n" +
            " */\n" +
            "\n" +
            "input[type=\"search\"]::-webkit-search-cancel-button,\n" +
            "input[type=\"search\"]::-webkit-search-decoration {\n" +
            "  -webkit-appearance: none;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Define consistent border, margin, and padding.\n" +
            " */\n" +
            "\n" +
            "fieldset {\n" +
            "  border: 1px solid #c0c0c0;\n" +
            "  margin: 0 2px;\n" +
            "  padding: 0.35em 0.625em 0.75em;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * 1. Correct `color` not being inherited in IE 8/9/10/11.\n" +
            " * 2. Remove padding so people aren't caught out if they zero out fieldsets.\n" +
            " */\n" +
            "\n" +
            "legend {\n" +
            "  border: 0; /* 1 */\n" +
            "  padding: 0; /* 2 */\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Remove default vertical scrollbar in IE 8/9/10/11.\n" +
            " */\n" +
            "\n" +
            "textarea {\n" +
            "  overflow: auto;\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Don't inherit the `font-weight` (applied by a rule above).\n" +
            " * NOTE: the default cannot safely be changed in Chrome and Safari on OS X.\n" +
            " */\n" +
            "\n" +
            "optgroup {\n" +
            "  font-weight: bold;\n" +
            "}\n" +
            "\n" +
            "/* Tables\n" +
            "   ========================================================================== */\n" +
            "\n" +
            "/**\n" +
            " * Remove most spacing between table cells.\n" +
            " */\n" +
            "\n" +
            "table {\n" +
            "  border-collapse: collapse;\n" +
            "  border-spacing: 0;\n" +
            "}\n" +
            "\n" +
            "td,\n" +
            "th {\n" +
            "  padding: 0;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "/*\n" +
            "* Skeleton V2.0.4\n" +
            "* Copyright 2014, Dave Gamache\n" +
            "* www.getskeleton.com\n" +
            "* Free to use under the MIT license.\n" +
            "* http://www.opensource.org/licenses/mit-license.php\n" +
            "* 12/29/2014\n" +
            "*/\n" +
            "\n" +
            "\n" +
            "/* Table of contents\n" +
            "-------------------------\n" +
            "- Grid\n" +
            "- Base Styles\n" +
            "- Typography\n" +
            "- Links\n" +
            "- Buttons\n" +
            "- Forms\n" +
            "- Lists\n" +
            "- Code\n" +
            "- Tables\n" +
            "- Spacing\n" +
            "- Utilities\n" +
            "- Clearing\n" +
            "- Media Queries\n" +
            "*/\n" +
            "\n" +
            "/* CUSTOM */\n" +
            "/*------------------------- */\n" +
            "\n" +
            "\n" +
            "th  {border: 1px solid #888; background:#D2D2D2; } /*header*/\n" +
            ".impAkt  {background: #F7FF4A; } /*zel*/\n" +
            ".impNeAkt{background: #E87671 ;} /*crv*/\n" +
            "\n" +
            ".impAkt  {background: #B8FF40; } /*zel*/\n" +
            ".impNeAkt{background: #FF897F ;} /*crv*/\n" +
            "\n" +
            ".impNeutr{background: WhiteSmoke ;}\n" +
            "\n" +
            "\n" +
            ".impAkt  {background: #c4efb4 ; } /*zel*/\n" +
            ".impNeAkt{background: #f2c2c2 ;} /*crv*/\n" +
            ".impNeutr{background: #f5f5f5 ;}\n" +
            "\n" +
            "\n" +
            ".impAkt  ,.impNeAkt,.impNeutr {text-align: center; border: 1px solid #aaa;min-width: 16px;max-width: 16px;}/* width:15px;*/\n" +
            "\n" +
            ".impTable {border-spacing: 1px;width: 1200px;}\n" +
            "\n" +
            "table.impTable th,table.impTable td {padding: 1px 2px;}\n" +
            "\n" +
            "/* Grid\n" +
            "------------------------- */\n" +
            ".container {\n" +
            "  position: relative;\n" +
            "  width: 100%;\n" +
            "  /* max-width: 960px; */\n" +
            "  margin: 0 auto;\n" +
            "  padding: 0 20px;\n" +
            "  box-sizing: border-box; }\n" +
            ".column,\n" +
            ".columns {\n" +
            "  width: 100%;\n" +
            "  float: left;\n" +
            "  box-sizing: border-box; }\n" +
            "\n" +
            "/* For devices larger than 400px */\n" +
            "@media (min-width: 400px) {\n" +
            "  .container {\n" +
            "    width: 85%;\n" +
            "    padding: 0; }\n" +
            "}\n" +
            "\n" +
            "/* For devices larger than 550px */\n" +
            "@media (min-width: 550px) {\n" +
            "  .container {\n" +
            "    width: 90%; }\n" +
            "  .column,\n" +
            "  .columns {\n" +
            "    margin-left: 4%; }\n" +
            "  .column:first-child,\n" +
            "  .columns:first-child {\n" +
            "    margin-left: 0; }\n" +
            "\n" +
            "  .one.column,\n" +
            "  .one.columns                    { width: 4.66666666667%; }\n" +
            "  .two.columns                    { width: 13.3333333333%; }\n" +
            "  .three.columns                  { width: 22%;            }\n" +
            "  .four.columns                   { width: 30.6666666667%; }\n" +
            "  .five.columns                   { width: 39.3333333333%; }\n" +
            "  .six.columns                    { width: 48%;            }\n" +
            "  .seven.columns                  { width: 56.6666666667%; }\n" +
            "  .eight.columns                  { width: 65.3333333333%; }\n" +
            "  .nine.columns                   { width: 74.0%;          }\n" +
            "  .ten.columns                    { width: 82.6666666667%; }\n" +
            "  .eleven.columns                 { width: 91.3333333333%; }\n" +
            "  .twelve.columns                 { width: 100%; margin-left: 0; }\n" +
            "\n" +
            "  .one-third.column               { width: 30.6666666667%; }\n" +
            "  .two-thirds.column              { width: 65.3333333333%; }\n" +
            "\n" +
            "  .one-half.column                { width: 48%; }\n" +
            "\n" +
            "  /* Offsets */\n" +
            "  .offset-by-one.column,\n" +
            "  .offset-by-one.columns          { margin-left: 8.66666666667%; }\n" +
            "  .offset-by-two.column,\n" +
            "  .offset-by-two.columns          { margin-left: 17.3333333333%; }\n" +
            "  .offset-by-three.column,\n" +
            "  .offset-by-three.columns        { margin-left: 26%;            }\n" +
            "  .offset-by-four.column,\n" +
            "  .offset-by-four.columns         { margin-left: 34.6666666667%; }\n" +
            "  .offset-by-five.column,\n" +
            "  .offset-by-five.columns         { margin-left: 43.3333333333%; }\n" +
            "  .offset-by-six.column,\n" +
            "  .offset-by-six.columns          { margin-left: 52%;            }\n" +
            "  .offset-by-seven.column,\n" +
            "  .offset-by-seven.columns        { margin-left: 60.6666666667%; }\n" +
            "  .offset-by-eight.column,\n" +
            "  .offset-by-eight.columns        { margin-left: 69.3333333333%; }\n" +
            "  .offset-by-nine.column,\n" +
            "  .offset-by-nine.columns         { margin-left: 78.0%;          }\n" +
            "  .offset-by-ten.column,\n" +
            "  .offset-by-ten.columns          { margin-left: 86.6666666667%; }\n" +
            "  .offset-by-eleven.column,\n" +
            "  .offset-by-eleven.columns       { margin-left: 95.3333333333%; }\n" +
            "\n" +
            "  .offset-by-one-third.column,\n" +
            "  .offset-by-one-third.columns    { margin-left: 34.6666666667%; }\n" +
            "  .offset-by-two-thirds.column,\n" +
            "  .offset-by-two-thirds.columns   { margin-left: 69.3333333333%; }\n" +
            "\n" +
            "  .offset-by-one-half.column,\n" +
            "  .offset-by-one-half.columns     { margin-left: 52%; }\n" +
            "\n" +
            "}\n" +
            "\n" +
            "\n" +
            "/* Base Styles\n" +
            "------------------------- */\n" +
            "/* NOTE\n" +
            "html is set to 62.5% so that all the REM measurements throughout Skeleton\n" +
            "are based on 10px sizing. So basically 1.5rem = 15px :) */\n" +
            "html {\n" +
            "/*   font-size: 62.5%; \n" +
            "  font-size: 39.0625%; */\n" +
            "  font-size: 27.0%; \n" +
            "  }\n" +
            "body {\n" +
            "  font-size: 2.7em; /* currently ems cause chrome bug misinterpreting rems on body element */\n" +
            "  line-height: 1.6;\n" +
            "  font-weight: 400;\n" +
            "  font-family: \"Raleway\", \"HelveticaNeue\", \"Helvetica Neue\", Helvetica, Arial, sans-serif;\n" +
            "  color: #222; }\n" +
            "\n" +
            "\n" +
            "/* Typography\n" +
            "------------------------- */\n" +
            "h1, h2, h3, h4, h5, h6 {\n" +
            "  margin-top: 0;\n" +
            "  margin-bottom: 2rem;\n" +
            "  font-weight: 300; \n" +
            "  \n" +
            "  border-bottom: 1px solid;\n" +
            "  margin-top: 35px;\n" +
            "  }\n" +
            "h1 { font-size: 4.0rem; line-height: 1.2;  letter-spacing: -.1rem;}\n" +
            "h2 { font-size: 3.6rem; line-height: 1.25; letter-spacing: -.1rem; color: #5ea226;}\n" +
            "h3 { font-size: 3.0rem; line-height: 1.3;  letter-spacing: -.1rem; }\n" +
            "h4 { font-size: 2.4rem; line-height: 1.35; letter-spacing: -.08rem; }\n" +
            "h5 { font-size: 1.8rem; line-height: 1.5;  letter-spacing: -.05rem; }\n" +
            "h6 { font-size: 1.5rem; line-height: 1.6;  letter-spacing: 0; }\n" +
            "\n" +
            "/* Larger than phablet */\n" +
            "@media (min-width: 550px) {\n" +
            "  h1 { font-size: 5.0rem; }\n" +
            "  h2 { font-size: 4.2rem; }\n" +
            "  h3 { font-size: 3.6rem; }\n" +
            "  h4 { font-size: 3.0rem; }\n" +
            "  h5 { font-size: 2.4rem; }\n" +
            "  h6 { font-size: 1.5rem; }\n" +
            "}\n" +
            "\n" +
            "p {\n" +
            "  margin-top: 0; }\n" +
            "\n" +
            "\n" +
            "/* Links\n" +
            "------------------------- */\n" +
            "a {\n" +
            "  color: #1EAEDB; }\n" +
            "a:hover {\n" +
            "  color: #0FA0CE; }\n" +
            "\n" +
            "\n" +
            "/* Buttons\n" +
            "------------------------- */\n" +
            ".button,\n" +
            "button,\n" +
            "input[type=\"submit\"],\n" +
            "input[type=\"reset\"],\n" +
            "input[type=\"button\"] {\n" +
            "  display: inline-block;\n" +
            "  height: 38px;\n" +
            "  padding: 0 30px;\n" +
            "  color: #555;\n" +
            "  text-align: center;\n" +
            "  font-size: 11px;\n" +
            "  font-weight: 600;\n" +
            "  line-height: 38px;\n" +
            "  letter-spacing: .1rem;\n" +
            "  text-transform: uppercase;\n" +
            "  text-decoration: none;\n" +
            "  white-space: nowrap;\n" +
            "  background-color: transparent;\n" +
            "  border-radius: 4px;\n" +
            "  border: 1px solid #bbb;\n" +
            "  cursor: pointer;\n" +
            "  box-sizing: border-box; }\n" +
            ".button:hover,\n" +
            "button:hover,\n" +
            "input[type=\"submit\"]:hover,\n" +
            "input[type=\"reset\"]:hover,\n" +
            "input[type=\"button\"]:hover,\n" +
            ".button:focus,\n" +
            "button:focus,\n" +
            "input[type=\"submit\"]:focus,\n" +
            "input[type=\"reset\"]:focus,\n" +
            "input[type=\"button\"]:focus {\n" +
            "  color: #333;\n" +
            "  border-color: #888;\n" +
            "  outline: 0; }\n" +
            ".button.button-primary,\n" +
            "button.button-primary,\n" +
            "input[type=\"submit\"].button-primary,\n" +
            "input[type=\"reset\"].button-primary,\n" +
            "input[type=\"button\"].button-primary {\n" +
            "  color: #FFF;\n" +
            "  background-color: #33C3F0;\n" +
            "  border-color: #33C3F0; }\n" +
            ".button.button-primary:hover,\n" +
            "button.button-primary:hover,\n" +
            "input[type=\"submit\"].button-primary:hover,\n" +
            "input[type=\"reset\"].button-primary:hover,\n" +
            "input[type=\"button\"].button-primary:hover,\n" +
            ".button.button-primary:focus,\n" +
            "button.button-primary:focus,\n" +
            "input[type=\"submit\"].button-primary:focus,\n" +
            "input[type=\"reset\"].button-primary:focus,\n" +
            "input[type=\"button\"].button-primary:focus {\n" +
            "  color: #FFF;\n" +
            "  background-color: #1EAEDB;\n" +
            "  border-color: #1EAEDB; }\n" +
            "\n" +
            "\n" +
            "/* Forms\n" +
            "------------------------- */\n" +
            "input[type=\"email\"],\n" +
            "input[type=\"number\"],\n" +
            "input[type=\"search\"],\n" +
            "input[type=\"text\"],\n" +
            "input[type=\"tel\"],\n" +
            "input[type=\"url\"],\n" +
            "input[type=\"password\"],\n" +
            "textarea,\n" +
            "select {\n" +
            "  height: 38px;\n" +
            "  padding: 6px 10px; /* The 6px vertically centers text on FF, ignored by Webkit */\n" +
            "  background-color: #fff;\n" +
            "  border: 1px solid #D1D1D1;\n" +
            "  border-radius: 4px;\n" +
            "  box-shadow: none;\n" +
            "  box-sizing: border-box; }\n" +
            "/* Removes awkward default styles on some inputs for iOS */\n" +
            "input[type=\"email\"],\n" +
            "input[type=\"number\"],\n" +
            "input[type=\"search\"],\n" +
            "input[type=\"text\"],\n" +
            "input[type=\"tel\"],\n" +
            "input[type=\"url\"],\n" +
            "input[type=\"password\"],\n" +
            "textarea {\n" +
            "  -webkit-appearance: none;\n" +
            "     -moz-appearance: none;\n" +
            "          appearance: none; }\n" +
            "textarea {\n" +
            "  min-height: 65px;\n" +
            "  padding-top: 6px;\n" +
            "  padding-bottom: 6px; }\n" +
            "input[type=\"email\"]:focus,\n" +
            "input[type=\"number\"]:focus,\n" +
            "input[type=\"search\"]:focus,\n" +
            "input[type=\"text\"]:focus,\n" +
            "input[type=\"tel\"]:focus,\n" +
            "input[type=\"url\"]:focus,\n" +
            "input[type=\"password\"]:focus,\n" +
            "textarea:focus,\n" +
            "select:focus {\n" +
            "  border: 1px solid #33C3F0;\n" +
            "  outline: 0; }\n" +
            "label,\n" +
            "legend {\n" +
            "  display: block;\n" +
            "  margin-bottom: .5rem;\n" +
            "  font-weight: 600; }\n" +
            "fieldset {\n" +
            "  padding: 0;\n" +
            "  border-width: 0; }\n" +
            "input[type=\"checkbox\"],\n" +
            "input[type=\"radio\"] {\n" +
            "  display: inline; }\n" +
            "label > .label-body {\n" +
            "  display: inline-block;\n" +
            "  margin-left: .5rem;\n" +
            "  font-weight: normal; }\n" +
            "\n" +
            "\n" +
            "/* Lists\n" +
            "------------------------- */\n" +
            "ul {\n" +
            "  list-style: circle inside; }\n" +
            "ol {\n" +
            "  list-style: decimal inside; }\n" +
            "ol, ul {\n" +
            "  padding-left: 0;\n" +
            "  margin-top: 0; }\n" +
            "ul ul,\n" +
            "ul ol,\n" +
            "ol ol,\n" +
            "ol ul {\n" +
            "  margin: 1.5rem 0 1.5rem 3rem;\n" +
            "  font-size: 90%; }\n" +
            "li {\n" +
            "  margin-bottom: 1rem; }\n" +
            "\n" +
            "\n" +
            "/* Code\n" +
            "------------------------- */\n" +
            "code {\n" +
            "  padding: .2rem .5rem;\n" +
            "  margin: 0 .2rem;\n" +
            "  font-size: 90%;\n" +
            "  white-space: nowrap;\n" +
            "  background: #F1F1F1;\n" +
            "  border: 1px solid #E1E1E1;\n" +
            "  border-radius: 4px; }\n" +
            "pre > code {\n" +
            "  display: block;\n" +
            "  padding: 1rem 1.5rem;\n" +
            "  white-space: pre; }\n" +
            "\n" +
            "\n" +
            "/* Tables\n" +
            "------------------------- */\n" +
            "\n" +
            "table th, table td {\n" +
            "    border: 1px solid #aaa;\n" +
            "    border-collapse: collapse;\n" +
            "    padding: 2px 5px;\n" +
            "}\n" +
            "\n" +
            "table {\n" +
            "    font-family: \"Helvetica Neue\",Helvetica,sans-serif;\n" +
            "    font-size: 12px;\n" +
            "\twidth: 50%;\n" +
            "\t  page-break-inside: avoid;\n" +
            "}\n" +
            "\n" +
            " table tr td, table tr th {\n" +
            "        page-break-inside: avoid;\n" +
            "    }\n" +
            "table thead {\n" +
            "  background: SteelBlue;\n" +
            "  color: white;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "table tbody tr:nth-child(even) {\n" +
            "  background: WhiteSmoke;\n" +
            "}\n" +
            "\n" +
            "table th {\n" +
            "/*   background: SteelBlue;\n" +
            "  color: white;\n" +
            " */  \n" +
            "  white-space: nowrap;\n" +
            "  text-align: left;\n" +
            "     border-right: 1px solid #aaa;\n" +
            "  \n" +
            "  \n" +
            "}\n" +
            "\n" +
            " .active {background-color: #f5f5f5; }\n" +
            " .active:hover {background-color: #e8e8e8; }\n" +
            " .success {background-color: #dff0d8; }\n" +
            " .success:hover  {background-color: #d0e9c6; }\n" +
            " .info {background-color: #d9edf7; }\n" +
            " .info:hover {background-color: #c4e3f3; }\n" +
            " .warning {background-color: #fcf8e3; }\n" +
            " .warning:hover {background-color: #faf2cc; }\n" +
            " .danger{background-color: #f2dede; }\n" +
            " .danger:hover {background-color: #ebcccc; }\n" +
            "\n" +
            "\n" +
            "\n" +
            "/* Spacing\n" +
            "------------------------- */\n" +
            "button,\n" +
            ".button {\n" +
            "  margin-bottom: 1rem; }\n" +
            "input,\n" +
            "textarea,\n" +
            "select,\n" +
            "fieldset {\n" +
            "  margin-bottom: 1.5rem; }\n" +
            "pre,\n" +
            "blockquote,\n" +
            "dl,\n" +
            "figure,\n" +
            "table,\n" +
            "p,\n" +
            "ul,\n" +
            "ol,\n" +
            "form {\n" +
            "  margin-bottom: 2.5rem; }\n" +
            "\n" +
            "\n" +
            "/* Utilities\n" +
            "------------------------- */\n" +
            ".u-full-width {\n" +
            "  width: 100%;\n" +
            "  box-sizing: border-box; }\n" +
            ".u-max-full-width {\n" +
            "  max-width: 100%;\n" +
            "  box-sizing: border-box; }\n" +
            ".u-pull-right {\n" +
            "  float: right; }\n" +
            ".u-pull-left {\n" +
            "  float: left; }\n" +
            "\n" +
            "\n" +
            "/* Misc\n" +
            "------------------------- */\n" +
            "hr {\n" +
            "  margin-top: 3rem;\n" +
            "  margin-bottom: 3.5rem;\n" +
            "  border-width: 0;\n" +
            "  border-top: 1px solid #E1E1E1; }\n" +
            "\n" +
            "\n" +
            "/* Clearing\n" +
            "------------------------- */\n" +
            "\n" +
            "/* Self Clearing Goodness */\n" +
            ".container:after,\n" +
            ".row:after,\n" +
            ".u-cf {\n" +
            "  content: \"\";\n" +
            "  display: table;\n" +
            "  clear: both; }\n" +
            "\n" +
            "\n" +
            "/* Media Queries\n" +
            "------------------------- */\n" +
            "/*\n" +
            "Note: The best way to structure the use of media queries is to create the queries\n" +
            "near the relevant code. For example, if you wanted to change the styles for buttons\n" +
            "on small devices, paste the mobile query code up in the buttons section and style it\n" +
            "there.\n" +
            "*/\n" +
            "\n" +
            "\n" +
            "/* Larger than mobile */\n" +
            "@media (min-width: 400px) {}\n" +
            "\n" +
            "/* Larger than phablet (also point when grid becomes active) */\n" +
            "@media (min-width: 550px) {}\n" +
            "\n" +
            "/* Larger than tablet */\n" +
            "@media (min-width: 750px) {}\n" +
            "\n" +
            "/* Larger than desktop */\n" +
            "@media (min-width: 1000px) {}\n" +
            "\n" +
            "/* Larger than Desktop HD */\n" +
            "@media (min-width: 1200px) {}\n" +
            "</style>\n" +
            "<style>table {margin:20pt;} span{font-size:12pt;}</style>\n" +
            "\t</head>";


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



    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



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
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());


        String filename = "measurment.htm";
        OutputStreamWriter htmlWriter;
        try {
            htmlWriter = new OutputStreamWriter(getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE));
            htmlWriter.write(CSS);
            htmlWriter.write("<body><div class=\"container\">");
            displayValues(wiper, ponPoffStrs, tlgAbstrs, strLoadMngs, m_PProg_R1, m_PProg_R2, m_PProg_R3, m_PProg_R4, oprij, htmlWriter);
            htmlWriter.write("</div></body></html>");
            htmlWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        String wholeFile = readFromFile(filename, getApplicationContext());

        webView.loadData(wholeFile, "text/html", "UTF-8");
         setContentView(webView);
        System.out.println();
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
                htmlWriter.write("<td>Yes</td>");
            } else {
                htmlWriter.write("<td>No</td>");
            }
        }
        htmlWriter.write("</tr>");

        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Retrigerable</th>");
        for (int i = 0; i < 4; i++) {
            if ((wiper[i].status & WIPPER_RETRIG_MASK) != 0x00) {
                htmlWriter.write("<td>Yes</td>");
            } else {
                htmlWriter.write("<td>No</td>");
            }
        }
        htmlWriter.write("</tr>");


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Activation command</th>");
        for (int i = 0; i < 4; i++) {
            if ((wiper[i].status & WIPER_ON_MASK) != 0x00) {
                htmlWriter.write("<td>a</td>");
            } else if ((wiper[i].status & WIPER_OFF_MASK) != 0x00) {
                htmlWriter.write("<td>b</td>");
            } else {
                htmlWriter.write("<td>XX</td>");
            }

        }
        htmlWriter.write("</tr>");


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Switching delay</th>");
        for (int i = 0; i < 4; i++) {
            htmlWriter.write("<td>" + GetHMSfromInt(wiper[i].Tswdly) + "</td>");
        }
        htmlWriter.write("</tr>");


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Wiper time</th>");

        for (int i = 0; i < 4; i++) {
            htmlWriter.write("<td>" + GetHMSfromInt(0000) + "</td>");
        }
        htmlWriter.write("</tr>");


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Scheduled switching activation delay</th>");

        for (int i = 0; i < 4; i++) {
            htmlWriter.write("<td>" + GetHMSfromInt(wiper[i].TBlockPrePro) + "</td>");

        }
        htmlWriter.write("</tr>");


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Loop enable</th>");


        for (int i = 0; i < 4; i++) {
            if ((wiper[i].status & LOOP_DISEB_MASK) == 0x00) {
                htmlWriter.write("<td>Yes</td>");

            } else {
                htmlWriter.write("<td>No</td>");

            }
        }
        htmlWriter.write("</tr>");


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Duration in position</th>");

        for (int i = 0; i < 4; i++) {

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
                htmlWriter.write("<td>Yes</td>");
            } else {
                htmlWriter.write("<td>No</td>");
            }
        }
        htmlWriter.write("</tr>");

        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Power supply loss time</th>");
        for (int i = 0; i < 4; i++) {
            htmlWriter.write("<td>" + GetHMSfromInt(ponPoffStr[i].Tlng) + "</td>");

        }
        htmlWriter.write("</tr>");

        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Loss of supply(short) - action</th>");
        for (int i = 0; i < 4; i++) {
            byte ss = (byte) (ponPoffStr[i].OnPonExe & 0x7F);
            if (ss < 10) {
                htmlWriter.write("<td>" + PoffS[ss] + "</td>");

            } else {
                htmlWriter.write("<td>XX</td>");
            }


        }
        htmlWriter.write("</tr>");


        int[] ign = {0, 0, 0, 0};
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Loss of supply(long) - ignore action </th>");
        for (int i = 0; i < 4; i++) {
            if ((ponPoffStr[i].lperIgno & PON_LPERIOD_DIS_MASK) != 0) {
                htmlWriter.write("<td>Yes</td>");
                ign[i] = 1;
            } else {
                htmlWriter.write("<td>No</td>");
            }
        }
        htmlWriter.write("</tr>");

        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Loss of supply(long) - action</th>");
        for (int i = 0; i < 4; i++) {
            byte ss = (ponPoffStr[i].lOnPonExe);
            if (ign[i] == 1) {
                htmlWriter.write("<td>/</td>");
                continue;
            }
            if (ss < 6) {
                htmlWriter.write("<td>" + PoffL[ss] + "</td>");
            } else {
                htmlWriter.write("<td>XX</td>");
            }
        }
        htmlWriter.write("</tr>");


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Switching delay - min</th>");
        for (int i = 0; i < 4; i++) {
            htmlWriter.write("<td>" + GetHMSfromInt(ponPoffStr[i].TminSwdly) + "</td>");

        }
        htmlWriter.write("</tr>");

        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Switching delay - max</th>");
        for (int i = 0; i < 4; i++) {
            htmlWriter.write("<td>" + GetHMSfromInt(ponPoffStr[i].TrndSwdly) + "</td>");
        }
        htmlWriter.write("</tr>");

        htmlWriter.write("<tr>");
        htmlWriter.write("<th> Scheduled switching activation delay</th>");
        for (int i = 0; i < 4; i++) {
            htmlWriter.write("<td>" + GetHMSfromInt(ponPoffStr[i].TBlockPrePro) + "</td>");
        }
        htmlWriter.write("</tr>");

        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Action</th>");
        for (int i = 0; i < 4; i++) {
            byte ss = (byte) ponPoffStr[i].OnPoffExe;
            if (ss < 3) {
                htmlWriter.write("<td>" + PoffAct[ss] + "</td>");
            } else {
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
            htmlWriter.write("<td>" + GetHMSfromInt(tlgAbstrs[i].TDetect) + "</td>");
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Absence time</th>");
        for (int i = 0; i < 4; i++) {
            byte ss = tlgAbstrs[i].RestOn;
            if (ss < 0x0F) {
                htmlWriter.write("<td>" + rst[ss] + "</td>");
            } else {
                htmlWriter.write("<td>XX</td>");
            }
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>On timer restart event disables work schedule</th>");

        for (int i = 0; i < 4; i++) {
            if ((tlgAbstrs[i].OnRes & 0x01) != 0) {
                htmlWriter.write("<td>Yes</td>");
            } else {
                htmlWriter.write("<td>No</td>");
            }
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Action</th>");


        for (int i = 0; i < 4; i++) {
            byte ss = (byte) (tlgAbstrs[i].OnTaExe & 0x0F);
            if (ss < 0x0F) {
                htmlWriter.write("<td>" + act[ss] + "</td>");
            } else {
                htmlWriter.write("<td>XX</td>");
            }
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Learn function disabled</th>");
        for (int i = 0; i < 4; i++) {
            if ((tlgAbstrs[i].OnTaExe & TLGA_ON_DISLRN) != 0) {
                htmlWriter.write("<td>Yes</td>");
            } else {
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
                htmlWriter.write("<td>24 h</td>");
            } else {
                htmlWriter.write("<td>7 days</td>");
            }
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Position</th>");

        for (int i = 0; i < 4; i++) {
            if ((strLoadMng[i].RelPos & LEARN_R_ON_MASK) != 0) {
                htmlWriter.write("<td>a</td>");
            } else if ((strLoadMng[i].RelPos & LEARN_R_OFF_MASK) != 0) {
                htmlWriter.write("<td>b</td>");
            } else {
                htmlWriter.write("<td>XX</td>");
            }
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Min</th>");


        for (int i = 0; i < 4; i++) {
            htmlWriter.write("<td>" + GetHMfromInt(strLoadMng[i].TPosMin) + "</td>");
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Max</th>");

        for (int i = 0; i < 4; i++) {
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
                htmlWriter.write("<td>Yes</td>");
            } else {
                htmlWriter.write("<td>No</td>");
            }
        }
        htmlWriter.write("</tr>");
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Inverted logic</th>");

        for (int i = 0; i < 4; i++) {
            int msk = 0x80 >> i;
            if ((oprij.PolUKRe & msk) != 0) {
                htmlWriter.write("<td>Yes</td>");
            } else {
                htmlWriter.write("<td>No</td>");
            }

        }
        htmlWriter.write("</tr>");
        htmlWriter.write("</table>");
        //Relay settings END


        //Work schedules


        //Work schedules - Time pairs Relay

        //Svaki element builderWorkSchedTimePairs ce imat cijelu tablicu u sebi (Work schedules - Time pairs Relay 1)
        StringBuilder[] builderWorkSchedTimePairs = new StringBuilder[4];
        for (int i = 0; i < 4; i++) {
            builderWorkSchedTimePairs[i] = new StringBuilder();
        }

        StringBuilder[] builderWorkSchedTimeDays = new StringBuilder[4];
        for (int i = 0; i < 4; i++) {
            builderWorkSchedTimeDays[i] = new StringBuilder();
        }

        showTimePairs(m_PProg_R1, m_PProg_R2, m_PProg_R3, m_PProg_R4, builderWorkSchedTimePairs);

        //Work schedules - Time pairs Relay END
        for (int relej = 1; relej <= 4; relej++) {
            if ((oprij.VOpRe.StaPrij & (0x80 >> (relej - 1))) == 0) {
                continue;
            }
            switch (relej) {

                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }


            //Work schedules - Time pairs Relay Days

            GetRelAkProg(relej, builderWorkSchedTimeDays);

            //Work schedules - Time pairs Relay Days END


        }
        //Work schedules - Time pairs Relay (DAYS) ispis
        for (int i = 0; i < 4; i++) {
            htmlWriter.write("<h2>Work schedules - Time pairs Relay " + (i + 1) + "</h2>");
            htmlWriter.write(builderWorkSchedTimeDays[i].toString());
            htmlWriter.write(builderWorkSchedTimePairs[i].toString());
        }
        //Work schedules - Time pairs Relay (DAYS) ispis


        //General
        DisplayGeneral(htmlWriter);
        //General END

        GetReallocRel();

        if (fVis_RefPrij) {


            //Switching delay
            htmlWriter.write("<h2>Switching delay</h2>");
            htmlWriter.write("<table>");


            htmlWriter.write("<tr>");


            htmlWriter.write("<th></th>");
            htmlWriter.write("<th>Relay 1</th>");
            htmlWriter.write("<th>Relay 2</th>");
            htmlWriter.write("<th>Relay 3</th>");
            htmlWriter.write("<th>Relay 4</th>");

            htmlWriter.write("</tr>");


            htmlWriter.write("<tr>");
            htmlWriter.write("<th>Delay a(hh:mm:ss)</th>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR1.KRelDela, 't') + "</td>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR2.KRelDela, 't') + "</td>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR3.KRelDela, 't') + "</td>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR4.KRelDela, 't') + "</td>");
            htmlWriter.write("</tr>");


            htmlWriter.write("<tr>");
            htmlWriter.write("<th>Delay a(hh:mm:ss)</th>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR1.KRelDela, 'm') + "</td>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR2.KRelDela, 'm') + "</td>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR3.KRelDela, 'm') + "</td>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR4.KRelDela, 'm') + "</td>");
            htmlWriter.write("</tr>");


            htmlWriter.write("<tr>");
            htmlWriter.write("<th>Delay b(hh:mm:ss)</th>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR1.KRelDelb, 't') + "</td>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR2.KRelDelb, 't') + "</td>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR3.KRelDelb, 't') + "</td>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR4.KRelDelb, 't') + "</td>");
            htmlWriter.write("</tr>");


            htmlWriter.write("<tr>");
            htmlWriter.write("<th>Delay b(hh:mm:ss)</th>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR1.KRelDelb, 'm') + "</td>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR2.KRelDelb, 'm') + "</td>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR3.KRelDelb, 'm') + "</td>");
            htmlWriter.write("<td>" + GetZatez(oprij.KlOpR4.KRelDelb, 'm') + "</td>");
            htmlWriter.write("</tr>");

            htmlWriter.write("<table>");

            //Switching delay END


        }

        //Telegrami
        if (fVis_RefPrij) {
            htmlWriter.write("<h2>Classic telegram</h2>");
            htmlWriter.write("<table>");

            GetRasterHeadStringH(htmlWriter);

            GetRasterHeadStringTop(htmlWriter);

            GetRasterHeadStringBottom(htmlWriter);

            GetRasterString(m_op50Prij.TlgRel1.Uk, 1, 'a', htmlWriter);
            GetRasterString(m_op50Prij.TlgRel1.Isk, 1, 'b', htmlWriter);

            GetRasterString(m_op50Prij.TlgRel2.Uk, 2, 'a', htmlWriter);
            GetRasterString(m_op50Prij.TlgRel2.Isk, 2, 'b', htmlWriter);

            GetRasterString(m_op50Prij.TlgRel3.Uk, 3, 'a', htmlWriter);
            GetRasterString(m_op50Prij.TlgRel3.Isk, 3, 'b', htmlWriter);

            GetRasterString(m_op50Prij.TlgRel4.Uk, 4, 'a', htmlWriter);
            GetRasterString(m_op50Prij.TlgRel4.Isk, 4, 'b', htmlWriter);
            htmlWriter.write("</table>");


        }

        if (fVis_Cz96P) {

            htmlWriter.write("<h2>Additional telegrams</h2>");
            htmlWriter.write("<table>");

            GetRasterHeadStringH(htmlWriter);

            GetRasterHeadStringTop(htmlWriter);

            GetRasterHeadStringBottom(htmlWriter);

            GetRasterString(m_op50Prij.tlg[0].Fn1.Cmd, 1, 'a', htmlWriter);
            GetRasterString(m_op50Prij.tlg[1].Fn1.Cmd, 1, 'b', htmlWriter);

            GetRasterString(m_op50Prij.tlg[2].Fn1.Cmd, 2, 'a', htmlWriter);
            GetRasterString(m_op50Prij.tlg[3].Fn1.Cmd, 2, 'b', htmlWriter);

            GetRasterString(m_op50Prij.tlg[4].Fn1.Cmd, 3, 'a', htmlWriter);
            GetRasterString(m_op50Prij.tlg[5].Fn1.Cmd, 3, 'b', htmlWriter);

            GetRasterString(m_op50Prij.tlg[6].Fn1.Cmd, 4, 'a', htmlWriter);
            GetRasterString(m_op50Prij.tlg[7].Fn1.Cmd, 4, 'b', htmlWriter);
            htmlWriter.write("</table>");

        }
        if (fVis_RefPrij && fVis_Cz96P) {
            htmlWriter.write("<h2>Synchronization telegrams</h2>");
            htmlWriter.write("<table>");

            GetRasterHeadStringH(htmlWriter);

            GetRasterHeadStringTop(htmlWriter);

            GetRasterHeadStringBottom(htmlWriter);

            for (int i = 0; i < 5; i++) {
                GetRasterStringSync(m_TelegSync[i].Cmd, i, htmlWriter);
            }
            htmlWriter.write("</table>");

        }

        if (fVis_RefPrij && fVis_Cz96P) {
            htmlWriter.write("<h2>Synchronization telegrams - day of the week</h2>");
            htmlWriter.write("<table>");


            GetRasterHeadStringH(htmlWriter);

            GetRasterHeadStringTop(htmlWriter);

            GetRasterHeadStringBottom(htmlWriter);

            for (int i = 0; i < 8; i++) {
                GetRasterString(m_TlgFnD[i].Cmd, i, 'a', htmlWriter);
            }
            htmlWriter.write("</table>");

        }

        //Telegrami END

        //Event log
        GetUserRecOpt(htmlWriter);
        //Event log END


        System.out.print("");

        if (fVis_Versacom) {
            //  dispTimePairs();
        }

        //Logic function
        htmlWriter.write("<h2>Logic function</h2>");


        GetRelInterLock(htmlWriter);
        //Logic function END

        if (fVis_Realoc) {


            htmlWriter.write("<h2>Relay switching assignment</h2>");
            htmlWriter.write("<table>");
            htmlWriter.write("<tr>");
            htmlWriter.write("<th></th>");
            htmlWriter.write("<th colspan=\"4\">Relay switching assignment</th>");
            htmlWriter.write("</tr>");


            htmlWriter.write("<tr>");
            htmlWriter.write("<th></th>");
            for (int i = 1; i < 5; i++) {
                htmlWriter.write("<th>" + String.format("Relay %d", i) + "</th>");
            }
            htmlWriter.write("</tr>");


            int x = 3;
            for (int i = 0; i < 4; i++) {
                htmlWriter.write("<tr>");
                htmlWriter.write("<th>" + String.format("Relay %d a", i + 1) + "</th>");

                htmlWriter.write("<td>" + PPRealoc(i, 1, m_Realloc[i].rel_on) + "</td>");

                htmlWriter.write("<td>" + PPRealoc(i, 2, m_Realloc[i].rel_on) + "</td>");

                htmlWriter.write("<td>" + PPRealoc(i, 3, m_Realloc[i].rel_on) + "</td>");

                htmlWriter.write("<td>" + PPRealoc(i, 4, m_Realloc[i].rel_on) + "</td>");


                htmlWriter.write("</tr>");

                x++;

                htmlWriter.write("<tr>");

                htmlWriter.write("<th>" + String.format("Relay %d b", i + 1) + "</th>");

                htmlWriter.write("<td>" + PPRealoc(i, 1, m_Realloc[i].rel_of) + "</td>");

                htmlWriter.write("<td>" + PPRealoc(i, 2, m_Realloc[i].rel_of) + "</td>");

                htmlWriter.write("<td>" + PPRealoc(i, 3, m_Realloc[i].rel_of) + "</td>");

                htmlWriter.write("<td>" + PPRealoc(i, 4, m_Realloc[i].rel_of) + "</td>");

                htmlWriter.write("</tr>");

                x++;

            }

            htmlWriter.write("</table>");

        }


    }

    private void GetRelInterLock(OutputStreamWriter htmlWriter) throws IOException {

        htmlWriter.write("<table>");


        for (int rel = 0; rel < 3 * 2; rel++) {
            int cfg = m_RelInterLock[rel].PcCnfg[0];
            String res = UnPackLadderSTR(rel, cfg);
            if (rel < 3) {

                htmlWriter.write("<tr><th>" + String.format("R%d %s", rel + 1, "a") + "</th><td>" + res + "</td></tr>");

            } else {

                htmlWriter.write("<tr><th>" + String.format("R%d %s", rel % 3 + 1, "b") + "</th><td>" + res + "</td></tr>");

            }
        }

        htmlWriter.write("</table>");
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


    private void GetRelAkProg(int brrel, StringBuilder[] builderWorkSchedTimeDays) throws IOException {
        String strItem, strx, r, tmps;
        String adani[] = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun", "Ho."};


        String[] temp = new String[8];
        builderWorkSchedTimeDays[brrel - 1].append("<table>");
        builderWorkSchedTimeDays[brrel - 1].append("<tr>");
        builderWorkSchedTimeDays[brrel - 1].append("<th>Work Schedules</th>");
        builderWorkSchedTimeDays[brrel - 1].append("<th>Active</th>");

        for (String s : adani) {
            builderWorkSchedTimeDays[brrel - 1].append("<th>" + s + "</th>");

        }

        builderWorkSchedTimeDays[brrel].append("</tr>");
        for (int pItem = 0; pItem < 16; pItem++) {
            Opprog PrPro;
            int AkPro;


            switch (brrel) {
                case 1:
                    if (m_PProg_R1[pItem].AkTim != 0) {

                        builderWorkSchedTimeDays[brrel - 1].append("<tr>");


                        builderWorkSchedTimeDays[brrel - 1].append("<td>" + (pItem + 1) + "</td>");
                        builderWorkSchedTimeDays[brrel - 1].append("<td>" + (((iVtmask[pItem] & oprij.VOpRe.VAkProR1) != 0) ? "Yes" : "No") + "</td>");


                        for (int iItem = 7; iItem >= 0; iItem--) {
                            if ((bVtmask[iItem] & (m_PProg_R1[pItem].DanPr)) != 0) {
                                temp[iItem] = "+";

                            } else {
                                temp[iItem] = "-";

                            }

                        }
                        for (int i = 7; i >= 0; i--) {
                            builderWorkSchedTimeDays[brrel - 1].append("<td>" + temp[i] + "</td>");
                        }
                        builderWorkSchedTimeDays[brrel - 1].append("</tr>");

                    }
                    break;
                case 2:
                    if (m_PProg_R2[pItem].AkTim != 0) {
                        builderWorkSchedTimeDays[brrel - 1].append("<tr>");


                        for (int cntTemp = 0; cntTemp < 10; cntTemp++) {
                        }
                        builderWorkSchedTimeDays[brrel - 1].append("<td>" + (pItem + 1) + "</td>");
                        builderWorkSchedTimeDays[brrel - 1].append("<td>" + (((iVtmask[pItem] & oprij.VOpRe.VAkProR1) != 0) ? "Yes" : "No") + "</td>");


                        for (int iItem = 7; iItem >= 0; iItem--) {
                            if ((bVtmask[iItem] & (m_PProg_R2[pItem].DanPr)) != 0) {

                                temp[iItem] = "+";
                            } else {

                                temp[iItem] = "-";
                            }
                        }
                        for (int i = 7; i >= 0; i--) {
                            builderWorkSchedTimeDays[brrel - 1].append("<td>" + temp[i] + "</td>");
                        }
                        builderWorkSchedTimeDays[brrel - 1].append("</tr>");

                    }
                    break;

                case 3:
                    if (m_PProg_R3[pItem].AkTim != 0) {
                        builderWorkSchedTimeDays[brrel - 1].append("<tr>");


                        builderWorkSchedTimeDays[brrel - 1].append("<td>" + (pItem + 1) + "</td>");
                        builderWorkSchedTimeDays[brrel - 1].append("<td>" + (((iVtmask[pItem] & oprij.VOpRe.VAkProR1) != 0) ? "Yes" : "No") + "</td>");


                        for (int iItem = 7; iItem >= 0; iItem--) {
                            if ((bVtmask[iItem] & (m_PProg_R3[pItem].DanPr)) != 0) {
                                temp[iItem] = "+";
                            } else {
                                temp[iItem] = "-";
                            }
                        }
                        for (int i = 7; i >= 0; i--) {
                            builderWorkSchedTimeDays[brrel - 1].append("<td>" + temp[i] + "</td>");
                        }
                        builderWorkSchedTimeDays[brrel - 1].append("</tr>");

                    }
                    break;

                case 4:
                    if (m_PProg_R4[pItem].AkTim != 0) {
                        builderWorkSchedTimeDays[brrel - 1].append("<tr>");


                        builderWorkSchedTimeDays[brrel - 1].append("<td>" + (pItem + 1) + "</td>");
                        builderWorkSchedTimeDays[brrel - 1].append("<td>" + (((iVtmask[pItem] & oprij.VOpRe.VAkProR1) != 0) ? "Yes" : "No") + "</td>");


                        for (int iItem = 7; iItem >= 0; iItem--) {
                            if ((bVtmask[iItem] & (m_PProg_R4[pItem].DanPr)) != 0) {
                                temp[iItem] = "+";
                            } else {
                                temp[iItem] = "-";
                            }
                        }
                        for (int i = 7; i >= 0; i--) {
                            builderWorkSchedTimeDays[brrel - 1].append("<td>" + temp[i] + "</td>");
                        }
                        builderWorkSchedTimeDays[brrel - 1].append("</tr>");

                    }
                    break;
            }

        }
        builderWorkSchedTimeDays[brrel].append("</table>");
        System.out.println();                       //GRESKA GRESKA GRESKA
    }

    private void GetUserRecOpt(OutputStreamWriter htmlWriter) throws IOException {
        int[] m_LogEnFlgs = new int[2];
        m_LogEnFlgs[0] = m_op50Prij.CLOGENFLGS[0];
        m_LogEnFlgs[1] = m_op50Prij.CLOGENFLGS[1];


        htmlWriter.write("<h2>Event log</h2>");
        htmlWriter.write("<table>");


        htmlWriter.write("<tr><th colspan=\"2\" >Common Log</th></tr>");
        htmlWriter.write("<tr><td>Power on/off time</td><td>" + ((m_LogEnFlgs[0] & SNE_POFF) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><td>Synchronization telegram - tim</td><td>" + ((m_LogEnFlgs[0] & SNE_SHT) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><td>Synchronization telegram - day</td><td>" + ((m_LogEnFlgs[0] & SNE_SHD) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><td>Local change of time</td><td>" + ((m_LogEnFlgs[0] & SNE_LSINH) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><th colspan=\"2\" >RTC Log</th></tr>");


        htmlWriter.write("<tr><td>Oscillator fail</td><td>" + ((m_LogEnFlgs[0] & SNE_RTC_OF) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><td>RTC stop</td><td>" + ((m_LogEnFlgs[0] & SNE_RTC_ST) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><td>Battery low</td><td>" + ((m_LogEnFlgs[0] & SNE_RTC_BL) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><th colspan=\"2\" >Relay Log</th></tr>");


        htmlWriter.write("<tr><td>Relay switched by telegram</td><td>" + ((m_LogEnFlgs[1] & REL_ON) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><td>Relay switched by program</td><td>" + ((m_LogEnFlgs[1] & PRO_REL_X) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><td>Start Wiper</td><td>" + ((m_LogEnFlgs[1] & REL_WIP_S) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><td>End Wiper</td><td>" + ((m_LogEnFlgs[1] & REL_WIP_R) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><td>Telegram absence start</td><td>" + ((m_LogEnFlgs[1] & REL_TA_S) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><td>Telegram absence restart</td><td>" + ((m_LogEnFlgs[1] & REL_TA_R) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><td>Work schedule disabled</td><td>" + ((m_LogEnFlgs[1] & REL_PROBLOCK) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><td>Work schedule enabled</td><td>" + ((m_LogEnFlgs[1] & REL_PROUNBLOCK) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><th colspan=\"2\" >Telegram Log</th></tr>");


        htmlWriter.write("<tr><td>Log all telegrams</td><td>" + ((m_LogEnFlgs[0] & OPT_LOG_TLG) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><td>Log telegrams for this receiver</td><td>" + ((m_LogEnFlgs[0] & OPT_LOG_MYTLG) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("<tr><td>Log only telegrams which change the state</td><td>" + ((m_LogEnFlgs[0] & OPT_LOG_REPTLG) != 0 ? "Yes" : "No") + "</td></tr>");


        htmlWriter.write("</table>");

    }

    private void GetRasterStringSync(TelegCMD t, int x, OutputStreamWriter htmlWriter) throws IOException {


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Unknown</th>");
        htmlWriter.write("<th>" + GetSyncTime(m_op50Prij.SinhTime[x], m_HWVerPri) + "</th>");


        for (int ibimp = 0; ibimp < 50; ibimp++) {
            int nBitNumber = ibimp % 8;
            int nByteNumber = ibimp / 8;

            int N = t.NeutImp[nByteNumber] & (0x80 >> nBitNumber);
            int A = t.AktiImp[nByteNumber] & (0x80 >> nBitNumber);

            if (IsCZ44raster && ibimp == 44) {
                break;
            }
            if (A != 0 && N != 0) {
                htmlWriter.write("<td class=\"impNeAkt\"><b>+</b></td>");
            } else if (A == 0 && N != 0) {
                htmlWriter.write("<td class=\"impAkt\"><b>-</b></td>");
            } else {
                htmlWriter.write("<td class=\"impNeutr\"></td>");

            }

        }
        htmlWriter.write("</tr>");


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

    private void GetRasterString(TelegCMD t, int num, char ch, OutputStreamWriter htmlWriter) throws IOException {


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Unknown</th>");
        htmlWriter.write("<th>" + String.format("R%d %c", num, ch) + "</th>");


        for (int ibimp = 0; ibimp < 50; ibimp++) {
            int nBitNumber = ibimp % 8;
            int nByteNumber = ibimp / 8;

            int N = t.NeutImp[nByteNumber] & (0x80 >> nBitNumber);
            int A = t.AktiImp[nByteNumber] & (0x80 >> nBitNumber);

            if (IsCZ44raster && ibimp == 44) {
                break;
            }
            if (A != 0 && N != 0) {
                htmlWriter.write("<td class=\"impNeAkt\"><b>+</b></td>");
            } else if (A == 0 && N != 0) {
                htmlWriter.write("<td class=\"impAkt\"><b>-</b></td>");
            } else {
                htmlWriter.write("<td class=\"impNeutr\"></td>");
            }

        }
        htmlWriter.write("</tr>");


    }

    private void GetRasterHeadStringH(OutputStreamWriter htmlWriter) throws IOException {


        htmlWriter.write("<tr>");


        htmlWriter.write("<th>Name</th>");
        htmlWriter.write("<th>Telegram</th>");
        htmlWriter.write("<th colspan=\"4\">A</th>");
        htmlWriter.write("<th colspan=\"8\">B</th>");


        for (int i = 4; i < 20; i++) {
            htmlWriter.write("<th colspan=\"2\">" + String.format("DP%d", i - 3) + "</th>");

        }


        htmlWriter.write("</tr>");

    }

    private void GetRasterHeadStringTop(OutputStreamWriter htmlWriter) throws IOException {

        htmlWriter.write("<tr>");


        htmlWriter.write("<th></th>");
        htmlWriter.write("<th></th>");

        for (int i = 1; i < 5; i++) {
            htmlWriter.write("<th>" + String.format("%d", i) + "</th>");

        }

        for (int i = 1; i < 9; i++) {
            htmlWriter.write("<th>" + String.format("%d", i) + "</th>");
        }

        for (int i = 14; i < 46; i += 2) {
            htmlWriter.write("<th>Z</th>");
            htmlWriter.write("<th>V</th>");


        }


        htmlWriter.write("<tr>");

    }

    private void GetRasterHeadStringBottom(OutputStreamWriter htmlWriter) throws IOException {

        htmlWriter.write("<tr>");


        htmlWriter.write("<th></th>");
        htmlWriter.write("<th></th>");


        for (int i = 2; i < 46; i++) {
            htmlWriter.write("<th>" + String.format("%d", i - 1) + "</th>");
        }


        htmlWriter.write("</tr>");

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

    private void showTimePairs(Opprog[] m_PProg_R1, Opprog[] m_PProg_R2, Opprog[] m_PProg_R3, Opprog[] m_PProg_R4, StringBuilder[] builderWorkSchedTimePairs) throws IOException {
        if (fVis_Versacom) {

            for (int relej = 1; relej <= 4; relej++) {
                builderWorkSchedTimePairs[relej - 1].append("<table>");
                builderWorkSchedTimePairs[relej - 1].append("<tr>");
                builderWorkSchedTimePairs[relej - 1].append("<th>Work Schedulestest1</th>");
                builderWorkSchedTimePairs[relej - 1].append("<th>Time pair test2</th>");
                builderWorkSchedTimePairs[relej - 1].append("<th>T- atest3</th>");
                builderWorkSchedTimePairs[relej - 1].append("<th>T- b test4</th>");
                builderWorkSchedTimePairs[relej - 1].append("</tr>");
                for (int rp = 0; rp < 16; rp++) {
                    GetRelVremPar(relej, rp, m_PProg_R1, m_PProg_R2, m_PProg_R3, m_PProg_R4, builderWorkSchedTimePairs[relej - 1]);
                }
                builderWorkSchedTimePairs[relej - 1].append("</table>");
            }
            System.out.print("");
        }
    }

    private void GetRelVremPar(int relej, int rp, Opprog[] m_PProg_R1, Opprog[] m_PProg_R2, Opprog[] m_PProg_R3,
                               Opprog[] m_PProg_R4, StringBuilder builderWorkSchedTimePairs) throws IOException {
        int cnt = 0;

        switch (relej) {
            case 1:


                for (int iItem = 0; iItem < (int) m_CFG.cNpar; iItem++) {

                    if ((m_PProg_R1[rp].AkTim & iVtmask[iItem]) != 0) {

                        builderWorkSchedTimePairs.append("<tr>");


                        cnt++;
                        if (cnt == 1) {
                            builderWorkSchedTimePairs.append("<td>" + String.valueOf(cntWork1 + 1) + "</td>");
                            cntWork1++;

                        } else {
                            builderWorkSchedTimePairs.append("<td></td>");
                        }

                        builderWorkSchedTimePairs.append("<td>" + String.format("%02d", iItem + 1) + "</td>");


                        builderWorkSchedTimePairs.append("<td>" + String.format("%02d:%02d", (m_PProg_R1[rp].Tpro[iItem].Ton) / 60, (m_PProg_R1[rp].Tpro[iItem].Ton) % 60) + "</td>");
                        builderWorkSchedTimePairs.append("<td>" + String.format("%02d:%02d", (m_PProg_R1[rp].Tpro[iItem].Toff) / 60, (m_PProg_R1[rp].Tpro[iItem].Toff) % 60) + "</td>");


                        cnt1++;
                        builderWorkSchedTimePairs.append("</tr>");
                    }


                }
                break;
            case 2:
                for (int iItem = 0; iItem < (int) m_CFG.cNpar; iItem++) {
                    if ((m_PProg_R2[rp].AkTim & iVtmask[iItem]) != 0) {
                        builderWorkSchedTimePairs.append("<tr>");


                        cnt++;
                        if (cnt == 1) {
                            builderWorkSchedTimePairs.append("<td>" + String.valueOf(cntWork2 + 1) + "</td>");
                            cntWork2++;
                        } else {
                            builderWorkSchedTimePairs.append("<td></td>");

                        }

                        builderWorkSchedTimePairs.append("<td>" + String.format("%02d", iItem + 1) + "</td>");


                        builderWorkSchedTimePairs.append("<td>" + String.format("%02d:%02d", (m_PProg_R2[rp].Tpro[iItem].Ton) / 60, (m_PProg_R2[rp].Tpro[iItem].Ton) % 60) + "</td>");
                        builderWorkSchedTimePairs.append("<td>" + String.format("%02d:%02d", (m_PProg_R2[rp].Tpro[iItem].Toff) / 60, (m_PProg_R2[rp].Tpro[iItem].Toff) % 60) + "</td>");


                        cnt2++;
                        builderWorkSchedTimePairs.append("</tr>");

                    }

                }

                break;
            case 3:

                for (int iItem = 0; iItem < (int) m_CFG.cNpar; iItem++) {
                    if ((m_PProg_R3[rp].AkTim & iVtmask[iItem]) != 0) {

                        builderWorkSchedTimePairs.append("<tr>");

                        cnt++;
                        if (cnt == 1) {
                            builderWorkSchedTimePairs.append("<td>" + String.valueOf(cntWork3 + 1) + "</td>");
                            cntWork3++;
                        } else {
                            builderWorkSchedTimePairs.append("<td></td>");

                        }


                        builderWorkSchedTimePairs.append("<td>" + String.format("%02d", iItem + 1) + "</td>");


                        builderWorkSchedTimePairs.append("<td>" + String.format("%02d:%02d", (m_PProg_R3[rp].Tpro[iItem].Ton) / 60, (m_PProg_R3[rp].Tpro[iItem].Ton) % 60) + "</td>");
                        builderWorkSchedTimePairs.append("<td>" + String.format("%02d:%02d", (m_PProg_R3[rp].Tpro[iItem].Toff) / 60, (m_PProg_R3[rp].Tpro[iItem].Toff) % 60) + "</td>");


                        cnt3++;
                        builderWorkSchedTimePairs.append("</tr>");

                    }

                }

                break;

        }
    }

    public void DisplayGeneral(OutputStreamWriter htmlWriter) throws IOException {
        TableRow[] generalTR = new TableRow[17];
        TextView[] generalTV = new TextView[17 * 2];
        String str = "";

        htmlWriter.write("<h2>General</h2>");
        htmlWriter.write("<table>");

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
        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Device type</th>");
        htmlWriter.write("<td>" + String.format("MTK-%d-%s-V-%d", m_tip + 1, str, m_SWVerPri) + "</td>");
        htmlWriter.write("</tr>");


        if (fVis_RefPrij) {
            htmlWriter.write("<tr>");
            htmlWriter.write("<th>HDO frequency</th>");

            int broj;
            if (m_SWVerPri >= 90) {
                broj = m_ParFilteraCF.BROJ;
                if (broj >= 0) {
                    htmlWriter.write("<td>" + String.format("%4.2f Hz", TbParFilteraVer9[broj].fre) + "</td>");

                }
            } else {
                broj = m_ParFiltera.BROJ;
                if (broj >= 0) {
                    if (m_SWVerPri < 80) {
                        htmlWriter.write("<td>" + String.format("%4.2f Hz", TbParFiltera[broj].fre) + "</td>");

                    } else {
                        htmlWriter.write("<td>" + String.format("%4.2f Hz", TbParFiltera9_8MHz[broj].fre) + "</td>");
                    }


                }
            }
            htmlWriter.write("</tr>");


            htmlWriter.write("<tr>");
            htmlWriter.write("<th>Raster</th>");
            String[] rra = {"Semagyr 50a", "Ricontic b", "Pulsadis(EdF)", "Inematic 2000", "ZPA-I-I", "ZPA-I-Ik", "CEZ 50D", "CEZ 50K"};
            htmlWriter.write("<td>" + rra[data.m_BrojRast] + "</td>");
            IsCZ44raster = (data.m_BrojRast == 4 || data.m_BrojRast == 5);
            IsCZRaster = ((data.m_BrojRast) > 3) && ((data.m_BrojRast) < 8);
            htmlWriter.write("</tr>");


            htmlWriter.write("<tr>");
            htmlWriter.write("<th>Sensitivity</th>");

            htmlWriter.write("<td>" + String.format("%4.2f %%", data.m_Utf_posto) + "</td>");
            htmlWriter.write("</tr>");


            htmlWriter.write("<tr>");
            htmlWriter.write("<th>Tel. raster time base</th>");

            if ((m_op50Prij.RTCSinh & 0x80) != 0) {// m_General50.m_RTCSinh
                htmlWriter.write("<td>Network(50Hz)</td>");
            } else {
                htmlWriter.write("<td>Clock</td>");
            }
            htmlWriter.write("</tr>");


        }

        htmlWriter.write("<tr>");
        htmlWriter.write("<th>RTC time base</th>");

        if ((m_op50Prij.RTCSinh & 0x03) != 0) {// m_General50.m_RasTSinh = 1;
            htmlWriter.write("<td>Quartz 32768 Hz</td>");
        } else {
            htmlWriter.write("<td>Network(50Hz)</td>");
        }
        htmlWriter.write("</tr>");


        if (!fVis_VersacomPS) {
            String[] rtcloss = {"None", "a", "b", "Same as PWON", "Continue with 50Hz"};


            htmlWriter.write("<tr>");
            htmlWriter.write("<th>RTC Loss action</th>");

            int inx = (byte) ((m_op50Prij.RTCSinh >> TIM_LOSS_RTC_POS) & 0x0F);

            if (inx > 3) {
                inx = 0;
            }

            htmlWriter.write("<td>" + rtcloss[inx] + "</td>");

            htmlWriter.write("</tr>");
        }


        if (fVis_RefPrij) {


            if (fVis_VersacomPS) {

                htmlWriter.write("<tr>");
                htmlWriter.write("<th>Address length of telegram DIN-43861-301</th>");

                htmlWriter.write("<td>" + String.format("%d", oprij.VDuzAdr) + "</td>");


                htmlWriter.write("</tr>");


            }

            if (!fVis_Cz95P) {

                htmlWriter.write("<tr>");
                htmlWriter.write("<th>synchronization telegram - day >> time</th>");


                htmlWriter.write("<td>" + String.format("%d", oprij.VDuzAdr) + "</td>");

                htmlWriter.write("</tr>");


            }


            if (fVis_VersacomPS) {

                htmlWriter.write("<tr>");
                htmlWriter.write("<th>ID</th>");


                htmlWriter.write("<td>" + String.format("%d", oprij.VIdBr) + "</td>");

                htmlWriter.write("</tr>");


            }


            if (!fVis_Cz95P) {

                htmlWriter.write("<tr>");
                htmlWriter.write("<th>24h cycle - active </th>");

                String datstr = (oprij.ParFlags & 0x1) != 0 ? "Yes" : "No";
                htmlWriter.write("<td>" + datstr + "</td>");
                htmlWriter.write("</tr>");


                htmlWriter.write("<tr>");
                htmlWriter.write("<th>24h cycle - delay </th>");


                htmlWriter.write("<td>" + String.format("%02d:%02d", oprij.Dly24H / 60, oprij.Dly24H % 60) + "</td>");

                htmlWriter.write("</tr>");


            } else {

                htmlWriter.write("<tr>");
                htmlWriter.write("<th>Track relay position after time synchronization </th>");

                String datstr = (m_op50Prij.RTCSinh & SINH_REL_POS_MASK) != 0 ? "Yes" : "No";

                htmlWriter.write("<td>" + datstr + "</td>");

                htmlWriter.write("</tr>");


            }
        }


        htmlWriter.write("<tr>");
        htmlWriter.write("<th>Power bridging time</th>");

        float timebridge = (m_op50Prij.CPWBRTIME * 5);

        htmlWriter.write("<td>" + String.format("%.2f s", timebridge / 1000) + "</td>");

        htmlWriter.write("</tr>");


        htmlWriter.write("</table>");

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
                    System.out.println(receiveString);
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

