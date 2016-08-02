package com.java.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * <p>Description: 多路复用类 ， 它是一个独立的线程，负责轮询多路复用器Seletor,可以处理多个客户端的
 * 并发接入<p>
 * @author shadow
 * @date 2016年8月7日
 */
public class MultiplexerTimeServer implements Runnable{
	
	private Selector selector;
	
	private ServerSocketChannel serverSocketChannel;
	
	private volatile boolean stop;
	
	public MultiplexerTimeServer(int port) {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("The time server is start in port : " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		this.stop = true;
	}

	@Override
	public void run() {
		while(!stop) {
			try {
				selector.select();
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				SelectionKey key = null;
				while(it.hasNext()) {
					key = it.next();
					it.remove();
					try{
						handleInput(key);
					}catch (IOException e) {
						if(key != null) {
							key.cancel();
							if(key.channel() != null) {
								key.channel().close();
							}
						}
						e.printStackTrace();
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		//多路复用器关闭后， 所有注册在上面的channel 和 pipe等资源都会被自动去注册并关闭， 所以不需要重新释放资源 
		if(selector != null) {
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void handleInput(SelectionKey key) throws IOException {
		if(key.isAcceptable()) {
			ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
			SocketChannel channel = serverSocketChannel.accept();
			channel.configureBlocking(false);
			channel.register(selector, SelectionKey.OP_READ);
		}
		
		if(key.isReadable()) {
			SocketChannel channel = (SocketChannel) key.channel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			int readBytes = channel.read(byteBuffer);
			if(readBytes > 0) {
				byteBuffer.flip();
				byte[] bytes = new  byte[byteBuffer.remaining()];
				byteBuffer.get(bytes);
				String body = new String(bytes, "UTF-8");
				
				System.out.println("The time server receive order :" + body);
				String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
						new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
				
			    doWrite(channel, currentTime);
			}else if(readBytes < 0) {//对链路关闭
				key.cancel();
				channel.close();
			} else {
				//读到0字节 忽略
			}
		}
	}
	
	private void doWrite(SocketChannel channel, String response) throws IOException {
		if(StringUtils.isNotEmpty(response)) {
			byte[] bytes = response.getBytes();
			ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
			byteBuffer.put(bytes);
			byteBuffer.flip();
			channel.write(byteBuffer);
		}
	}

}
