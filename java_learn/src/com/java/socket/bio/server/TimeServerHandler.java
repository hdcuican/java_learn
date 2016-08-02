package com.java.socket.bio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * <p>Description: 同步阻塞Server处理线程类<p>
 * @author shadow
 * @date 2016年8月7日
 */
public class TimeServerHandler implements Runnable{
	
	private Socket socket;
	
	public TimeServerHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out = new PrintWriter(this.socket.getOutputStream(), true);
			String currentTime = null;
			String body = null;
			while(true){
				//线程阻塞在这里 直到客户端有数据发过来
				body = in.readLine();
				if(body == null) {
					break;
				}
				System.out.println("The time server receive order :" + body);
				currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
						new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
				
				out.println(currentTime);
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(in != null){
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if(out != null){
				out.close();
				out = null;
			}
			if(socket != null){
				try {
					socket.close();
					socket = null;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		
	}

}
