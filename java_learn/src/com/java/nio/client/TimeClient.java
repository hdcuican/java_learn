package com.java.nio.client;

/**
 * 
 * <p>Description: NIO模型客户端<p>
 * @author shadow
 * @date 2016年8月7日
 */
public class TimeClient {
	
	private static final String host = "127.0.0.1";
	
	private static final int port = 8080;
	
	public static void main(String[] args) {
		new Thread(new TimeClientHandler(host, port), "TimeClient-thread-10").start();
	}

}
