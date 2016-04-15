package com.flint.android.wiffy;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	Socket clientSock;
	int serverPort;
	
	public Client() {
		connectToServer(WiffyActivity.motoAP_IP, WiffyActivity.serverPort);
	}

	public Client(String serverIP, int serverPort) {
		connectToServer(serverIP, serverPort);
	}
	
	public void connectToServer() {
		try {
			clientSock = new Socket(WiffyActivity.motoAP_IP, WiffyActivity.serverPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connectToServer(String dstName, int dstPort) {
		try {
			clientSock = new Socket(dstName, dstPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}