package com.flint.android.wiffy;

import java.util.List;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WiffyActivity extends ActionBarActivity {
	static TextView mainTV;
	WifiManager myWifiManager;
	WifiScanReceiver wsr;	
	boolean wasWifiEnabled;
	List<ScanResult> accessPoints;
	
	Button sendButton;
	Button receiveButton;
	
	Server server;
	Client client;
	
    public static final String TAG = "WiffyActivity";
    public static final String motoAP_IP = "192.168.43.13";
    public static final int serverPort = 7117;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wiffy);

		mainTV = (TextView) findViewById(R.id.message_log_textview);
		sendButton = (Button) findViewById(R.id.send_button);
		sendButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// whoever presses "Send", starts the SERVER
				if(server == null) {
					writeToTV("Starting server...");
					Thread thread = new Thread(new Runnable()
					{
						@Override
						public void run() {
							try {
								server = new Server();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					thread.start();
				} else {
					writeToTV("Server already running!");
				}
			}
		});
		
		receiveButton = (Button) findViewById(R.id.receive_button);
		receiveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// whoever presses "Receive", starts the CLIENT
				if(client == null) {
					writeToTV("Starting client...");
					Thread thread = new Thread(new Runnable()
					{
						@Override
						public void run() {
							try {
								client = new Client(motoAP_IP, serverPort);
							} catch (Exception e) {
								e.printStackTrace();
								
							}
						}
					});
					thread.start();
				} else {
					writeToTV("Client already running!");
				}
			}
		});

		myWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wasWifiEnabled = myWifiManager.isWifiEnabled();
		myWifiManager.setWifiEnabled(true);
		while(!myWifiManager.isWifiEnabled()) {}
		
		performScan();
	}
	
	public void performScan() {
		wsr = new WifiScanReceiver(myWifiManager, accessPoints);
		myWifiManager.startScan();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			myWifiManager.setWifiEnabled(wasWifiEnabled);
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public static void writeToTV(String msg) {
		mainTV.append("\n" + msg);
	}
	
	@Override
	public void onResume() {
		super.onResume(); // always call superclass method first!		
		registerReceiver(wsr, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
	}

	@Override
	protected void onPause() {
		super.onPause(); // always call superclass method first!
		unregisterReceiver(wsr);
		int netId = myWifiManager.getConnectionInfo().getNetworkId();
		writeToTV("Removing n/w with netid: " + netId);
		myWifiManager.removeNetwork(netId);
		writeToTV("Saving configuration...");
		myWifiManager.saveConfiguration();
	}
}