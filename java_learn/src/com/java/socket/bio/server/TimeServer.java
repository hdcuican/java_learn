package com.java.socket.bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * <p>Description: 同步阻塞IOServer类<p>
 * <p>同步阻塞IO模型最大的问题是缺乏弹性伸缩能力，当客户端并发访问量增加时，服务端的线程个数和
 * 客户端的并发访问数呈1:1的比例，由于线程是Java虚拟机非常宝贵的系统资源，当线程数膨胀之后，
 * 系统的性能将急剧下降，随着并发访问数量的增加，系统会出现线程堆栈溢出，创建新线程失败等问题。
 * 并最终导致进程宕机或者僵死，不能对外提供服务。
 * <p>
 * @author shadow
 * @date 2016年8月7日
 */
public class TimeServer {
	
	private final static int port = 8080;
	
	public static void main(String[] args) throws IOException {
		
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("The time server start in port: " + port);
			Socket socket = null;
			while(true) {
				//线程阻塞在这里 指导有客户端连接进来
				socket = serverSocket.accept();
				new Thread(new TimeServerHandler(socket)).start();
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
