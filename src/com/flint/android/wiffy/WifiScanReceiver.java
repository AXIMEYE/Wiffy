package com.flint.android.wiffy;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.ListView;

public class WifiScanReceiver extends BroadcastReceiver {
	WifiManager myWifiManager;
	List<ScanResult> accessPoints;
	String wifis[];
	ListView lv;
    
	public WifiScanReceiver(WifiManager wm, List<ScanResult> ap) {
		myWifiManager = wm;
		accessPoints = ap;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		accessPoints = myWifiManager.getScanResults();
		wifis = new String[accessPoints.size()];

		for(int i = 0; i<accessPoints.size(); i++) {
			wifis[i] = ((accessPoints.get(i)).toString());
		}

//		lv.setAdapter(new ArrayAdapter<String>(context.getApplicationContext(), android.R.layout.simple_list_item_1, wifis));
		connectToAP(context);		
	}

	// chooses appropriate AP and preferably passes the AP to connectAP()  
	public void chooseAP() {
		
	}
	
	// connects to the appropriate "Receiver"
	public void connectToAP(Context context) {
		if(accessPoints != null) {
			WifiConfiguration wc = new WifiConfiguration();
			wc.SSID = "\"" + "motoAP" + "\"";
			wc.status = WifiConfiguration.Status.ENABLED;
			wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			wc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

			int netId = myWifiManager.addNetwork(wc);
			boolean b = myWifiManager.enableNetwork(netId, true);
			if(b == true) {
				Log.d(WiffyActivity.TAG, "Network association operation succeded!");
				WiffyActivity.writeToTV("N/w association operation succeded!");
				WiffyActivity.writeToTV("IP: " + myWifiManager.getConnectionInfo().getIpAddress());
				WiffyActivity.writeToTV("NetID: " + myWifiManager.getConnectionInfo().getNetworkId());
			}
		}		
	}

}
