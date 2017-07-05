package com.estimote.showroom;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.connection.DeviceConnectionProvider;
import com.estimote.showroom.estimote.NearableID;
import com.estimote.showroom.estimote.Product;
import com.estimote.showroom.estimote.ShowroomManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    EditText editTextAddress, editTextPort, editTextMsg;
    Button buttonConnect, buttonDisconnect, buttonSend;
    TextView textViewState, textViewRx, textSend;

    ClientHandler clientHandler;
    ClientThread clientThread;

    private ShowroomManager showroomManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        editTextMsg = (EditText) findViewById(R.id.msgtosend);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonSend = (Button)findViewById(R.id.send);
        textViewState = (TextView)findViewById(R.id.state);
        textViewRx = (TextView)findViewById(R.id.received);
        textSend = (TextView)findViewById(R.id.descriptionLabel);

        buttonSend.setEnabled(false);
        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        buttonSend.setOnClickListener(buttonSendOnClickListener);
        clientHandler = new ClientHandler(this);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        DeviceConnectionProvider connectionProvider = new DeviceConnectionProvider(this);
        Map<NearableID, Product> products = new HashMap<>();
        products.put(new NearableID("f70815120671b2d9"), new Product("dog", "ID : f70815120671b2d9"));
        products.put(new NearableID("75272d8cbd2c38b6"), new Product("shoe", "ID : 75272d8cbd2c38b6"));
        products.put(new NearableID("797f9cba026ad0f4"), new Product("fridge", "ID : 797f9cba026ad0f4"));
        products.put(new NearableID("f70815120671b2d9"), new Product("dog", "ID : f70815120671b2d9"));
        products.put(new NearableID("e3e08453dab86271"), new Product("bag", "ID : e3e08453dab86271"));
        products.put(new NearableID("cfc50e35bd5eebb7"), new Product("bed", "ID : cfc50e35bd5eebb7"));
        products.put(new NearableID("918ba00396988f42"), new Product("door", "ID : 918ba00396988f42"));
        products.put(new NearableID("8f5d32c8badf6fcc"), new Product("bike", "ID : 8f5d32c8badf6fcc"));
        products.put(new NearableID("75272d8cbd2c38b6"), new Product("shoe", "ID : 75272d8cbd2c38b6"));
        products.put(new NearableID("4a31e535eeec4132"), new Product("chair", "ID : 4a31e535eeec4132"));
        products.put(new NearableID("26ae1cf28e5d0241"), new Product("car", "ID : 26ae1cf28e5d0241"));
        products.put(new NearableID("21dabfddad3b7767"), new Product("generic", "ID : 21dabfddad3b7767"));

        showroomManager = new ShowroomManager(this, products);
        showroomManager.setListener(new ShowroomManager.Listener() {
            @Override
            public void onProductPickup(Product product) {
                switch (product.getName()){
                    case "dog": {
                        ((TextView) findViewById(R.id.titleLabel)).setText(product.getName());
                        ((TextView) findViewById(R.id.descriptionLabel)).setText(product.getSummary());
                        findViewById(R.id.descriptionLabel).setVisibility(View.VISIBLE);
                        break;
                    }
                    case "shoe": {
                        ((TextView) findViewById(R.id.titleLabel2)).setText(product.getName());
                        ((TextView) findViewById(R.id.descriptionLabel2)).setText(product.getSummary());
                        findViewById(R.id.descriptionLabel3).setVisibility(View.VISIBLE);
                        break;
                    }
                    case "fridge": {
                        ((TextView) findViewById(R.id.titleLabel3)).setText(product.getName());
                        ((TextView) findViewById(R.id.descriptionLabel3)).setText(product.getSummary());
                        findViewById(R.id.descriptionLabel3).setVisibility(View.VISIBLE);
                        break;
                    }
                    case "bag": {
                        ((TextView) findViewById(R.id.titleLabel4)).setText(product.getName());
                        ((TextView) findViewById(R.id.descriptionLabel4)).setText(product.getSummary());
                        findViewById(R.id.descriptionLabel4).setVisibility(View.VISIBLE);
                        break;
                    }
                    case "bed": {
                        ((TextView) findViewById(R.id.titleLabel5)).setText(product.getName());
                        ((TextView) findViewById(R.id.descriptionLabel5)).setText(product.getSummary());
                        findViewById(R.id.descriptionLabel5).setVisibility(View.VISIBLE);
                        break;
                    }
                    case "door": {
                        ((TextView) findViewById(R.id.titleLabel6)).setText(product.getName());
                        ((TextView) findViewById(R.id.descriptionLabel6)).setText(product.getSummary());
                        findViewById(R.id.descriptionLabel6).setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
            @Override
            public void onProductPutdown(Product product) {
                ((TextView) findViewById(R.id.titleLabel)).setText("Pick up an object to learn more about it");
                findViewById(R.id.descriptionLabel).setVisibility(View.INVISIBLE);
            }
            @Override
            public void onConnectedToService() {
                // Handle your actions here. You are now connected to connection service.
                // For example: you can create DeviceConnection object here from connectionProvider.
            }
        });

    }
///////////////////////////////////////////////////////////////////////////////////
    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    clientThread = new ClientThread(
                            editTextAddress.getText().toString(),
                            Integer.parseInt(editTextPort.getText().toString()),
                            clientHandler);
                    clientThread.start();

                    buttonConnect.setEnabled(false);
                    buttonDisconnect.setEnabled(true);
                    buttonSend.setEnabled(true);
                }
            };
    View.OnClickListener buttonSendOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(clientThread != null){
                //String msgToSend = editTextMsg.getText().toString();
                //clientThread.txMsg(msgToSend);
                String descriptionLabel = textSend.getText().toString();
                clientThread.txMsg(descriptionLabel);
            }
        }
    };
    private void updateState(String state){
        textViewState.setText(state);
    }

    private void updateRxMsg(String rxmsg){
        textViewRx.append(rxmsg + "\n");
    }

    private void clientEnd(){
        clientThread = null;
        textViewState.setText("clientEnd()");
        buttonConnect.setEnabled(true);
        buttonSend.setEnabled(false);
    }

    public static class ClientHandler extends Handler {
        public static final int UPDATE_STATE = 0;
        public static final int UPDATE_MSG = 1;
        public static final int UPDATE_END = 2;
        private MainActivity parent;

        public ClientHandler(MainActivity parent) {
            super();
            this.parent = parent;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_STATE:
                    parent.updateState((String)msg.obj);
                    break;
                case UPDATE_MSG:
                    parent.updateRxMsg((String)msg.obj);
                    break;
                case UPDATE_END:
                    parent.clientEnd();
                    break;
                default:
                    super.handleMessage(msg);
            }

        }

    }
    ////////////////////////////////////////////////////////////////////////
    @Override
    protected void onResume() {
        super.onResume();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else {
            Log.d(TAG, "Starting ShowroomManager updates");
            showroomManager.startUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Stopping ShowroomManager updates");
        showroomManager.stopUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showroomManager.destroy();
    }
}
