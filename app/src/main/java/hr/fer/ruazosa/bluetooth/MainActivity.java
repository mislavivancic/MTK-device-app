package hr.fer.ruazosa.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button connectBtn;
    Button runBtn;
    Button closeBtn;
    Button connectedBtn;
    Button uBtn;





    TextView statusView;

    private int REQUEST_ENABLE_BT=1;
    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> pairedDevices;
    BluetoothDevice device;
    BluetoothSocket tmp = null;
    BluetoothSocket mmSocket = null;
    BluetoothSocket tempSocket=null;
    List<Character> input=new LinkedList<>();
    Data data;
    Wiper[] wiper;
    PonPoffStr[] ponPoffStrs;
    TlgAbstr[] tlgAbstrs;
    StrLoadMng[] strLoadMngs;







    Opprog[] m_PProg_R1;
    Opprog[] m_PProg_R2;
    Opprog[] m_PProg_R3;
    Opprog[] m_PProg_R4;

    Oprij m_opPrij;

    //static {System.loadLibrary("native-lib");}


    // HELM HS05 MAC address: "00:07:80:F5:ED:BF"
    // UUID: "00001101-0000-1000-8000-00805f9b34fb"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        connectBtn=findViewById(R.id.connectBtn);
        runBtn=findViewById(R.id.runBtn);
        closeBtn=findViewById(R.id.closeBtn);
        connectedBtn=findViewById(R.id.connectedBtn);
        uBtn=findViewById(R.id.ubtn);
        statusView=findViewById(R.id.textView);






            pairedDevices=mBluetoothAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    // There are paired devices. Get the name and address of each paired device.
                    for (BluetoothDevice device : pairedDevices) {
                        String deviceName = device.getName();
                        String deviceHardwareAddress = device.getAddress(); // MAC address
                        System.out.println(deviceName + "-->" + deviceHardwareAddress);
                    }

                    device=mBluetoothAdapter.getRemoteDevice("00:07:80:F5:ED:BF");
                    ParcelUuid a=device.getUuids()[0];



                    System.out.println(device.getName());
                    System.out.println(a.toString());


                }



        if(!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent,REQUEST_ENABLE_BT);
       }

       connectBtn.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v){
               device=mBluetoothAdapter.getRemoteDevice("00:07:80:F5:ED:BF");
               ConnectThread connectThread=new ConnectThread(device);
               statusView.setText("Connected to: "+device.getName());

           }
       });

        runBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectThread connectThread=new ConnectThread(device);
                connectThread.start();

            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectThread connectThread=new ConnectThread(device);
                ConnectedThread2 writeThread=new ConnectedThread2(tempSocket);
                ConnectedThread readThread=new ConnectedThread(tempSocket);
                writeThread.cancel();
                readThread.cancel();
                connectThread.cancel();
                Toast.makeText(MainActivity.this,"Connection closed",Toast.LENGTH_SHORT).show();
            }
        });

        connectedBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectedThread2 writeThread=new ConnectedThread2(tempSocket);
                ConnectedThread readThread=new ConnectedThread(tempSocket);
                statusView.setText("Reading...");

                //connectedThread.start();
                writeThread.start();
                readThread.start();



                //ackBtn.performClick();
                //uBtn.performClick();
            }
        });



        uBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (Character c:input){
                    System.out.print(c);
                }
                byte[] MtkVer=new byte[39];
                byte [] dataTemp=new byte[input.size()];
                for (int i=0;i<39;i++){
                    MtkVer[i]=(byte)input.get(i).charValue();
                }

                for (int i=0;i<input.size()-39;i++){
                    dataTemp[i]=(byte) input.get(i+39).charValue();
                }
          /*
                wiper=new Wiper[4];
                wiper[0]=new Wiper();
                wiper[1]=new Wiper();
                wiper[2]=new Wiper();
                wiper[3]=new Wiper();

                ponPoffStrs=new PonPoffStr[4];
                ponPoffStrs[0]=new PonPoffStr();
                ponPoffStrs[1]=new PonPoffStr();
                ponPoffStrs[2]=new PonPoffStr();
                ponPoffStrs[3]=new PonPoffStr();

                tlgAbstrs=new TlgAbstr[4];
                tlgAbstrs[0]=new TlgAbstr();
                tlgAbstrs[1]=new TlgAbstr();
                tlgAbstrs[2]=new TlgAbstr();
                tlgAbstrs[3]=new TlgAbstr();

                strLoadMngs=new StrLoadMng[4];
                strLoadMngs[0]=new StrLoadMng();
                strLoadMngs[1]=new StrLoadMng();
                strLoadMngs[2]=new StrLoadMng();
                strLoadMngs[3]=new StrLoadMng();




                m_PProg_R1=new Opprog[16];
                m_PProg_R2=new Opprog[16];
                m_PProg_R3=new Opprog[16];
                m_PProg_R4=new Opprog[16];

                for (int i=0;i<16;i++){
                    m_PProg_R1[i]=new Opprog();
                    for (int j=0;j<14;j++){
                        m_PProg_R1[i].Tpro[j]=new Tonoff();
                    }
                }
                for (int i=0;i<16;i++){
                    m_PProg_R2[i]=new Opprog();
                    for (int j=0;j<14;j++){
                        m_PProg_R2[i].Tpro[j]=new Tonoff();
                    }
                }
                for (int i=0;i<16;i++){
                    m_PProg_R3[i]=new Opprog();
                    for (int j=0;j<14;j++){
                        m_PProg_R3[i].Tpro[j]=new Tonoff();
                    }
                }
                for (int i=0;i<16;i++){
                    m_PProg_R4[i]=new Opprog();
                    for (int j=0;j<14;j++){
                        m_PProg_R4[i].Tpro[j]=new Tonoff();
                    }
                }

                m_opPrij=new Oprij();
                m_opPrij.init();



                data=new Data(dataTemp);
               data.processData(wiper,ponPoffStrs,tlgAbstrs,strLoadMngs,m_PProg_R1,m_PProg_R2,m_PProg_R3,m_PProg_R4);

*/
               Bundle b=new Bundle();
                b.putByteArray("data",dataTemp);
                b.putByteArray("MTKVer",MtkVer);


                Intent wiperIntent=new Intent(MainActivity.this,SecondActivity.class);

               /*
                Bundle b=new Bundle();
                b.putParcelable("wiper1",wiper[0]);
                b.putParcelable("wiper2",wiper[1]);
                b.putParcelable("wiper3",wiper[2]);
                b.putParcelable("wiper4",wiper[3]);

                b.putParcelable("PonPoff1",ponPoffStrs[0]);
                b.putParcelable("PonPoff2",ponPoffStrs[1]);
                b.putParcelable("PonPoff3",ponPoffStrs[2]);
                b.putParcelable("PonPoff4",ponPoffStrs[3]);

                b.putParcelable("tlg1",tlgAbstrs[0]);
                b.putParcelable("tlg2",tlgAbstrs[1]);
                b.putParcelable("tlg3",tlgAbstrs[2]);
                b.putParcelable("tlg4",tlgAbstrs[3]);

                b.putParcelable("str1",strLoadMngs[0]);
                b.putParcelable("str2",strLoadMngs[1]);
                b.putParcelable("str3",strLoadMngs[2]);
                b.putParcelable("str4",strLoadMngs[3]);

                 b.putParcelableArray("PProg1",m_PProg_R1);
                b.putParcelableArray("PProg2",m_PProg_R2);
               b.putParcelableArray("PProg3",m_PProg_R3);
               b.putParcelableArray("PProg4",m_PProg_R4);
*/
         //      b.putParcelable("opPrij",m_opPrij);
                wiperIntent.putExtras(b);
                startActivity(wiperIntent);
                finish();
            }
        });

      //  showWiperBtn.setOnClickListener(new View.OnClickListener() {
        //    public void onClick(View v) {
         //       Intent wiperIntent=new Intent(MainActivity.this,SecondActivity.class);
          //      Bundle b=new Bundle();
           //     b.putParcelableArray("key",wiper);
            //    wiperIntent.putExtras(b);
            //    startActivity(wiperIntent);
             //   finish();

        //    }
        //});

    }

   //private native String getNativeString();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(MainActivity.this,"Bluetooth ENABLED",Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(MainActivity.this,"Bluetooth wasn't ENABLED",Toast.LENGTH_SHORT).show();
            }
        }
    }//onActivityResult


    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;


        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
                System.out.println("Socket's create() method did not fail");
            } catch (IOException e) {
                System.out.println("Socket's create() method failed");
                //Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
            tempSocket=tmp;

        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            Looper.prepare();
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    System.out.println("Could not close the client socket");
                    //Log.e(TAG, "Could not close the client socket", closeException);
                }
                return;
            }

            if(mmSocket.isConnected()){
                System.out.println("CONNECTED");
                statusView.setText("Connected and ready to read");
                        //.makeText(MainActivity.this,"Connected and ready to read",Toast.LENGTH_SHORT).show();

            }
           // System.out.println("Connection type: " + mmSocket.getConnectionType());

                // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
          //////////////// manageMyConnectedSocket(mmSocket);
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
                System.out.println("CLOSED");
            } catch (IOException e) {
                System.out.println("Could not close the client socket");
                //Log.e(TAG, "Could not close the client socket", e);
            }
        }

    }
    private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
        public static final int MESSAGE_TOAST = 2;

        // ... (Add other message types here as needed.)
    }


        private class ConnectedThread extends Thread {
            private final BluetoothSocket mmSocket;
            private final InputStream mmInStream;
            private final OutputStream mmOutStream;
            private byte[] mmBuffer; // mmBuffer store for the stream
            private Handler mHandler;

            public ConnectedThread(BluetoothSocket socket) {

                mmSocket = socket;
                InputStream tmpIn = null;
                OutputStream tmpOut = null;

                // Get the input and output streams; using temp objects because
                // member streams are final.
                try {
                    tmpIn = socket.getInputStream();
                } catch (IOException e) {
                    Log.e("DEBUGG", "Error occurred when creating input stream", e);
                }
                try {
                    tmpOut = socket.getOutputStream();
                } catch (IOException e) {
                    Log.e("DEBUGG", "Error occurred when creating output stream", e);
                }

                mmInStream = tmpIn;
                mmOutStream = tmpOut;
                System.out.println("CONNECTED2");
               /* try{
                    this.sleep(1000);
                }catch (InterruptedException e){
                    //ignore
                }
                System.out.println("Slept 1 sec");*/
            }

            public void run() {
                System.out.println("Entered .run()");
                mmBuffer = new byte[1024];
                Looper.prepare();
                mHandler=new Handler();
                int word;
                int numBytes; // bytes returned from read()
                StringBuilder sb=new StringBuilder();

                // Keep listening to the InputStream until an exception occurs.
                while (true) {
                    try {
                        // Read from the InputStream.
                       word=mmInStream.read();
                       System.out.print((char) word);
                       input.add((Character.valueOf((char)word)));
                       if (word==0x21){


                           statusView.setText("Completed reading");
                       }

                        //resultView.append((Character.valueOf((char)word)));
                       // resultView.append((String.valueOf((char)word)));
                        // numBytes = mmInStream.read(mmBuffer,0,100);
                        // Send the obtained bytes to the UI activity.
                        //System.out.println(mmBuffer.toString());
                        //Message readMsg = mHandler.obtainMessage(MessageConstants.MESSAGE_READ, numBytes, -1,mmBuffer);
                       // readMsg.sendToTarget();
                        //mHandler.handleMessage(readMsg);
                    } catch (IOException e) {
                        Log.d("DEBUGG", "Input stream was disconnected", e);
                        Toast.makeText(MainActivity.this,"Input stream was disconnected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                System.out.println("Exited loop");
            }

            // Call this from the main activity to send data to the remote device.
           public void write(byte[] bytes) {
                try {
                    mmOutStream.write(hexStringToByteArray("2F3F210D0A"));

                    // Share the sent message with the UI activity.
                  //  Message writtenMsg = mHandler.obtainMessage(MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
                   // writtenMsg.sendToTarget();
                } catch (IOException e) {
                    Log.e("DEBUGG", "Error occurred when sending data", e);

                    // Send a failure message back to the activity.
                   // Message writeErrorMsg =mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                    Bundle bundle = new Bundle();
                    bundle.putString("toast",
                            "Couldn't send data to the other device");
                    //writeErrorMsg.setData(bundle);
                    //mHandler.sendMessage(writeErrorMsg);
                }
            }
            public void write2(byte[] bytes) {
                try {

                    mmOutStream.write(hexStringToByteArray("063034360D0A"));

                    //Share the sent message with the UI activity.
                    //  Message writtenMsg = mHandler.obtainMessage(MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
                    // writtenMsg.sendToTarget();
                } catch (IOException e) {
                    Log.e("DEBUGG", "Error occurred when sending data", e);

                    // Send a failure message back to the activity.
                    // Message writeErrorMsg =mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                    Bundle bundle = new Bundle();
                    bundle.putString("toast",
                            "Couldn't send data to the other device");
                    //writeErrorMsg.setData(bundle);
                    //mHandler.sendMessage(writeErrorMsg);
                }
            }
            public void write3(byte[] bytes) {
                try {
                    mmOutStream.write(hexStringToByteArray("015503560D0A"));
                    // Share the sent message with the UI activity.
                    //  Message writtenMsg = mHandler.obtainMessage(MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
                    // writtenMsg.sendToTarget();
                } catch (IOException e) {
                    Log.e("DEBUGG", "Error occurred when sending data", e);

                    // Send a failure message back to the activity.
                    // Message writeErrorMsg =mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                    Bundle bundle = new Bundle();
                    bundle.putString("toast",
                            "Couldn't send data to the other device");
                    //writeErrorMsg.setData(bundle);
                    //mHandler.sendMessage(writeErrorMsg);
                }
            }

            // Call this method from the main activity to shut down the connection.
            public void cancel() {
                try {
                    mmSocket.close();
                } catch (IOException e) {
                    Log.e("DEBUGG", "Could not close the connect socket", e);
                }
            }
        }






    private class ConnectedThread2 extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream

        public ConnectedThread2(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
            // member streams are final.
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.e("DEBUGG", "Error occurred when creating input stream", e);
            }
            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e("DEBUGG", "Error occurred when creating output stream", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
            System.out.println("CONNECTED2");
               /* try{
                    this.sleep(1000);
                }catch (InterruptedException e){
                    //ignore
                }
                System.out.println("Slept 1 sec");*/
        }

        public void run() {
            try {
                mmOutStream.write(hexStringToByteArray("2F3F210D0A"));

                // Share the sent message with the UI activity.
                //  Message writtenMsg = mHandler.obtainMessage(MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
                // writtenMsg.sendToTarget();
            } catch (IOException e) {
                Log.e("DEBUGG", "Error occurred when sending data", e);

                // Send a failure message back to the activity.
                // Message writeErrorMsg =mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
                //writeErrorMsg.setData(bundle);
                //mHandler.sendMessage(writeErrorMsg);
            }
            try{
                TimeUnit.MILLISECONDS.sleep(4000);
            }catch(InterruptedException e){
                System.out.println("Gasi sve");
            }
            try {

                mmOutStream.write(hexStringToByteArray("063034360D0A"));

                //Share the sent message with the UI activity.
                //  Message writtenMsg = mHandler.obtainMessage(MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
                // writtenMsg.sendToTarget();
            } catch (IOException e) {
                Log.e("DEBUGG", "Error occurred when sending data", e);

                // Send a failure message back to the activity.
                // Message writeErrorMsg =mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
                //writeErrorMsg.setData(bundle);
                //mHandler.sendMessage(writeErrorMsg);
            }
            try{
                TimeUnit.MILLISECONDS.sleep(2000);
            }catch(InterruptedException e){
                System.out.println("Gasi sve");
            }
            try {
                mmOutStream.write(hexStringToByteArray("015503560D0A"));
                // Share the sent message with the UI activity.
                //  Message writtenMsg = mHandler.obtainMessage(MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
                // writtenMsg.sendToTarget();
            } catch (IOException e) {
                Log.e("DEBUGG", "Error occurred when sending data", e);

                // Send a failure message back to the activity.
                // Message writeErrorMsg =mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
                //writeErrorMsg.setData(bundle);
                //mHandler.sendMessage(writeErrorMsg);
            }


        }



        // Call this method from the main activity to shut down the connection.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e("DEBUGG", "Could not close the connect socket", e);
            }
        }
    }
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}

