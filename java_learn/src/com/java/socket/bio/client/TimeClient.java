package com.java.socket.bio.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * <p>Description: 同步阻塞IOClients类<p>
 * @author shadow
 * @date 2016年8月7日
 */
public class TimeClient {
	
	private final static int port = 8080;
	
	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			socket = new Socket("127.0.0.1", port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		    out.println("QUERY TIME ORDER");
			System.out.println("Send order to server success!");
			//线程阻塞在这里 直到服务端有数据返回
			String resp = in.readLine();
			System.out.println("Now is : " + resp);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out != null) {
				out.close();
			}
			if(in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(socket != null) {
				try {
					socket.close();
					socket = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
