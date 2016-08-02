package com.java.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientHandler implements Runnable{
	
	private String host;
	
	private int port;
	
	private Selector selector;
	
	private SocketChannel socketChannel;
	
	private volatile boolean stop;
	
	public TimeClientHandler(String host, int port) {
		try {
			this.host = host;
			this.port = port;
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		try {
			doConnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(!stop) {
			try {
				selector.select(1000);
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
				System.exit(1);
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
		if(key.isValid()) {
			SocketChannel channel = (SocketChannel) key.channel();
			if(key.isConnectable()) {
				if(channel.finishConnect()) {
					channel.register(selector, SelectionKey.OP_READ);
					doWrite(channel);
				}else{
					System.exit(1); //链接失败， 进程退出
				}
			}
			
			if(key.isReadable()) {
				ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
				int readBytes = channel.read(byteBuffer);
				if(readBytes > 0) {
					byteBuffer.flip();
					byte[] bytes = new  byte[byteBuffer.remaining()];
					byteBuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("Now is :  " + body);
				   // this.stop = true;
				}else if(readBytes < 0) {//对链路关闭
					key.cancel();
					channel.close();
				} else {
					//读到0字节 忽略
				}
			}
		}
		
	}
	
	private void doConnect() throws IOException {
		//如果直连成功，则注册到多路复用器，发送请求消息，读应答
		if(socketChannel.connect(new InetSocketAddress(host, port))) {
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);
		}else{
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}
	
	private void doWrite(SocketChannel channel) throws IOException {
		byte[] bytes = "QUERY TIME　ORDER".getBytes();
		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
		byteBuffer.put(bytes);
		byteBuffer.flip();
		channel.write(byteBuffer);
		if(!byteBuffer.hasRemaining()) {
			System.out.println("Send order to server succeed!");
		}
	}

}
