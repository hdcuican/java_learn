package com.java.nio.server;

/**
 * 
 * <p>Description: NIO模型服务端<p>
 * @author shadow
 * @date 2016年8月7日
 */
public class TimeServer {
	
	private final static int port = 8080;
	
	public static void main(String[] args) {
		MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
		new Thread(timeServer, "timeServer-thread-01").start();
	}

}
