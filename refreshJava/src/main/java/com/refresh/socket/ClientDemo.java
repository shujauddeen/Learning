package com.refresh.socket;

import java.awt.im.InputContext;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.xml.sax.InputSource;

public class ClientDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String serverName = "localhost";
		int port = 6066;
		
		try{
			System.out.println("Connecting to " + serverName + " on port " + port);
			Socket client = new Socket(serverName, port);
			System.out.println("Connected tooooo " + client.getRemoteSocketAddress());
			
			OutputStream os = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(os);
			out.writeUTF("Message from " + client.getLocalSocketAddress());
			
			InputStream is = client.getInputStream();
			DataInputStream in = new DataInputStream(is);
			
			System.out.println("Server says " + in.readUTF());
			client.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
