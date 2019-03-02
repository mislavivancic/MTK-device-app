package hr.fer.ruazosa.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button closeConnectionButton;
    Button connectAndReadButton;
    Button showMeasurmentButton;

    ProgressBar progressBar;

    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();


    TextView statusView;

    private int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> pairedDevices;
    BluetoothDevice device;
    BluetoothSocket tempSocket = null;
    List<Character> input = new LinkedList<>();

    boolean CONNECTED = false;

    ConnectThread createSocketThread;
    ConnectThread connectThread;
    ConnectedThread2 writeThread;
    ConnectedThread readThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        statusView = findViewById(R.id.textView);

        progressBar = findViewById(R.id.progressbar);


        //Buttons
        showMeasurmentButton = findViewById(R.id.ubtn);
        connectAndReadButton = findViewById(R.id.oneBtn);
        closeConnectionButton = findViewById(R.id.closeBtn);


        showMeasurmentButton.setEnabled(false);
        closeConnectionButton.setEnabled(false);
        progressBar.setVisibility(View.INVISIBLE);

        pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice tempDevice : pairedDevices) {
                String deviceName = tempDevice.getName();
                String deviceHardwareAddress = tempDevice.getAddress(); // MAC address
                System.out.println(deviceName + "-->" + deviceHardwareAddress);
                if (deviceName.contains("HELM HS05 ")) {
                    device = mBluetoothAdapter.getRemoteDevice(deviceHardwareAddress);
                    break;
                }
            }

            if (device == null) {
                Toast.makeText(MainActivity.this, "Connecting failed", Toast.LENGTH_LONG).show();
            }

            //device = mBluetoothAdapter.getRemoteDevice("00:07:80:F5:ED:BF");
            ParcelUuid a = device.getUuids()[0];


            System.out.println(device.getName());
            System.out.println(a.toString());


        }


        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }


        connectAndReadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Create Socket
                device = mBluetoothAdapter.getRemoteDevice("00:07:80:F5:ED:BF");
                createSocketThread = new ConnectThread(device);
                statusView.setText("Trying to connect to: " + device.getName());

                showMeasurmentButton.setEnabled(false);
                closeConnectionButton.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);

                //Connect to device
                connectThread = new ConnectThread(device);
                connectThread.start();


                //Start reading
                writeThread = new ConnectedThread2(tempSocket);
                readThread = new ConnectedThread(tempSocket);


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mProgressStatus < 100) {
                            mProgressStatus++;
                            android.os.SystemClock.sleep(550);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(mProgressStatus);
                                }
                            });

                        }
                    }

                }).start();

                //connectedThread.start();
                writeThread.start();
                readThread.start();

                connectAndReadButton.setEnabled(false);

            }
        });

        showMeasurmentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                for (Character c : input) {
                    System.out.print(c);
                }
                byte[] MtkVer = new byte[39];
                byte[] dataTemp = new byte[input.size()];
                for (int i = 0; i < 39; i++) {
                    MtkVer[i] = (byte) input.get(i).charValue();
                }

                for (int i = 0; i < input.size() - 39; i++) {
                    dataTemp[i] = (byte) input.get(i + 39).charValue();
                }

                Bundle b = new Bundle();
                b.putByteArray("data", dataTemp);
                b.putByteArray("MTKVer", MtkVer);


                Intent wiperIntent = new Intent(MainActivity.this, SecondActivity.class);


                wiperIntent.putExtras(b);
                startActivity(wiperIntent);
                finish();
            }
        });


        closeConnectionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                connectAndReadButton.setEnabled(true);


                writeThread.cancel();
                readThread.cancel();
                connectThread.cancel();
                mProgressStatus = 0;
                Toast.makeText(MainActivity.this, "Connection closed", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(MainActivity.this, "Bluetooth ENABLED", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "Bluetooth wasn't ENABLED", Toast.LENGTH_SHORT).show();
            }
        }
    }


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
            tempSocket = tmp;

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
                }
                return;
            }

            if (mmSocket.isConnected()) {
                System.out.println("CONNECTED");
                statusView.setText("Connected and ready to read");

                CONNECTED = true;
            }

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

        }

        public void run() {
            System.out.println("Entered .run()");

            try {
                this.sleep(4000);
            } catch (InterruptedException e) {
                //ignore
            }
            statusView.setText("Rading data...");
            mmBuffer = new byte[1024];
            Looper.prepare();
            mHandler = new Handler();
            int word;


            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    // System.out.print(" k");
                    // Read from the InputStream.
                    word = mmInStream.read();
                    //System.out.print((char) word);
                    input.add((Character.valueOf((char) word)));
                    if (word == 0x21) {


                        statusView.setText("Completed reading");
                        progressBar.setVisibility(View.INVISIBLE);
                        showMeasurmentButton.setEnabled(true);
                        closeConnectionButton.setEnabled(true);
                    }

                } catch (IOException e) {
                    Log.d("DEBUGG", "Input stream was disconnected", e);
                    Toast.makeText(MainActivity.this, "Input stream was disconnected", Toast.LENGTH_SHORT).show();
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
                try {
                    this.sleep(5000);
                } catch (InterruptedException e) {
                    //ignore
                }
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
            try {
                TimeUnit.MILLISECONDS.sleep(5000);
            } catch (InterruptedException e) {
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
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
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
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

}

