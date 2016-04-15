package com.flint.android.wiffy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;

public class Server {
	ServerSocket serverSock;
	Socket clientSock;
	int serverPort; 
	
	public Server() {
		createServerSocket();
		initServerPort();
		listenAndAcceptConnection();
	}
	
	public void createServerSocket() {
		try {
//			serverSock = new ServerSocket(0);
			serverSock = new ServerSocket(7117);
			Log.d(WiffyActivity.TAG, "Server: Socket opened");
//			WiffyActivity.writeToTV("Server: Socket opened");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initServerPort() {
		serverPort = serverSock.getLocalPort();
		Log.d(WiffyActivity.TAG, "Server port: " + serverPort);
//		WiffyActivity.writeToTV("Server port: " + serverPort);
	}
	
	public void listenAndAcceptConnection() {
		try {
			Log.d(WiffyActivity.TAG, "Server: listening for connexns...");
//			WiffyActivity.writeToTV("Server: listening for connexns...");
			clientSock = serverSock.accept();
			Log.d(WiffyActivity.TAG, "Server: connection done");
//			WiffyActivity.writeToTV("Server: Connection done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
