package com.flint.android.wiffy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {
	private WifiP2pManager mManager;
	private Channel mChannel;
	private WiffyActivity mActivity;

	public WifiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel, WiffyActivity activity) {
		super();
		this.mManager = manager;
		this.mChannel = channel;
		this.mActivity = activity;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
			// check to see if wifi is enabled and notify appropriate activity
			int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
			if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
				// wifi p2p is enabled
			} else {
				// otherwise
			}
		} else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
			// call WifiP2pManager.requestPeers() to get a list of peers
		} else if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
			// respond to new connection or disconnections
		} else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
			// respond to this device's wifi state changing
		}
	}
}

