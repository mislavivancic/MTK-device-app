package hr.fer.ruazosa.bluetooth;

/**
 * Created by mislav on 12.9.2018..
 */

public class LadderState {
    byte[] m_RelState=new byte[4];
    byte[] m_RelNr=new byte[4];
    boolean[] m_isSeries=new boolean[4];
    boolean[] m_notsernotpar=new boolean[4];
    boolean[] m_conectshort=new boolean[4];


    public LadderState(byte[] m_RelState, byte[] m_RelNr, boolean[] m_isSeries, boolean[] m_notsernotpar, boolean[] m_conectshort) {
        this.m_RelState = m_RelState;
        this.m_RelNr = m_RelNr;
        this.m_isSeries = m_isSeries;
        this.m_notsernotpar = m_notsernotpar;
        this.m_conectshort = m_conectshort;
    }

    public LadderState(){

    }
}
