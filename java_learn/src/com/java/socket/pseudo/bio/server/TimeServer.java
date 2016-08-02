package com.java.socket.pseudo.bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * <p>Description: 伪异步IOServer类<p>
 * <p>伪异步IO采用线程池实现，因此避免了位每个客户端请求都创建一个独立的线程造成线程资源造成的问题
 * 但是由于他底层依然采用同步阻塞模型，因此无法从根本上解决问题
 * <p>
 * @author shadow
 * @date 2016年8月7日
 */
public class TimeServer {
	
	private final static int port = 8080;
	
	private final static int maxPoolSize = 10;
	
	private final static int queueSize = 10000;
	
	public static void main(String[] args) throws IOException {
		
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("The time server start in port: " + port);
			Socket socket = null;
			/**
			 * 由于线程池和消息队列是有界的，无论客户端并发数量有多大，都不会导致线程个数过于膨胀或内存溢出
			 * 相对于传统的一连接一线程的模型， 是一种改良
			 */
			TimeServerHanderExecutePool  executePool = new TimeServerHanderExecutePool(maxPoolSize, queueSize);
			while(true) {
				socket = serverSocket.accept();
				executePool.execute(new TimeServerHandler(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(serverSocket != null) {
				System.out.println("The time server close");
				serverSocket.close();
				serverSocket = null;
			}
		}
		
	}
}
