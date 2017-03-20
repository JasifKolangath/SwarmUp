package com.phacsin.jasif.swarmup;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * A service that process each file transfer request i.e Intent by opening a
 * socket connection with the WiFi Direct Group Owner and writing the file
 */
public class DataTransferService extends IntentService {

    private static final int SOCKET_TIMEOUT = 5000;
    public static final String ACTION_SEND_FILE = "com.example.android.wifidirect.SEND_FILE";
    public static final String EXTRAS_FILE_PATH = "file_url";
    public static final String EXTRAS_NUMBER_1 = "number_1";
    public static final String EXTRAS_NUMBER_2 = "number_2";
    public static final String EXTRAS_NUMBER_3 = "number_3";

    public static final String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public static final String EXTRAS_GROUP_OWNER_PORT = "go_port";
    public static final String EXTRAS_RESULT = "result";
    public static final String ACTION_MyIntentService = "action_intent";
    private long i;


    public DataTransferService(String name) {
        super(name);
    }

    public DataTransferService() {
        super("DataTransferService");
    }

    /*
     * (non-Javadoc)
     * @see android.app.IntentService#onHandleIntent(android.content.Intent)
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        Context context = getApplicationContext();
        if (intent.getAction().equals(ACTION_SEND_FILE)) {
            String number1 = intent.getExtras().getString(EXTRAS_NUMBER_1);
            String number2 = intent.getExtras().getString(EXTRAS_NUMBER_2);
            String lower = intent.getExtras().getString(EXTRAS_NUMBER_3);
            String host = intent.getExtras().getString(EXTRAS_GROUP_OWNER_ADDRESS);
            Socket socket = new Socket();
            int port = intent.getExtras().getInt(EXTRAS_GROUP_OWNER_PORT);

            try {
                Log.d(ConnectedDeviceActivity.TAG, "Opening client socket - ");
                socket.bind(null);
                socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);

                Log.d(ConnectedDeviceActivity.TAG, "Client socket - " + socket.isConnected());
                DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
               /* ContentResolver cr = context.getContentResolver();
                try {
                    is = cr.openInputStream(Uri.parse(fileUri));
                } catch (FileNotFoundException e) {
                    Log.d(ConnectedDeviceActivity.TAG, e.toString());
                } */
                stream.writeUTF(number1);
                stream.writeUTF(number2);
                long result_dele = 0;
                for(i=Long.parseLong(lower);i<Long.parseLong(number1);i++) {
                    result_dele+=i;
                }
                Log.d(ConnectedDeviceActivity.TAG, "lower: " + lower);
                Log.d(ConnectedDeviceActivity.TAG, "mid: " + number1);
                Log.d(ConnectedDeviceActivity.TAG, "dele Sum: " + String.valueOf(result_dele));

                Log.d(ConnectedDeviceActivity.TAG, "Client: Data written");
                String result = dis.readUTF();
                long res = Long.parseLong(result) + result_dele;
                Log.d(ConnectedDeviceActivity.TAG, "Client: Data retrieved " + res);
                Intent intentUpdate = new Intent();
                intentUpdate.setAction(ACTION_MyIntentService);
                intentUpdate.addCategory(Intent.CATEGORY_DEFAULT);
                intentUpdate.putExtra(EXTRAS_RESULT, String.valueOf(res));
                sendBroadcast(intentUpdate);

            } catch (IOException e) {
                Log.e(ConnectedDeviceActivity.TAG, e.getMessage());
            } finally {
                if (socket != null) {
                    if (socket.isConnected()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            // Give up
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }
}