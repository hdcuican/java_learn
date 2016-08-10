package com.netty5.chapter04.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 
 * <p>Description: netty客户端 使用LineBasedFrameDecoder和 StringDecoder解决Tcp粘包导致的读半包问题
 * LineBasedFrameDecoder和 StringDecoder的组合就是按行切换的文本解码器<p>
 * @author shadow
 * @date 2016年8月7日
 */
public class TimeClient {
	
	private final static int port = 8080;
	
	public void connect(int port, String host) throws InterruptedException {
		
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new TimeClientHandler());
				}
				
			});
			
			//发送异步链接操作
			ChannelFuture f = b.connect(host, port).sync();
			//等待客户端链路关闭
			f.channel().closeFuture().sync();
		}finally{
			group.shutdownGracefully();
		}
		
	}
	
	public static void main(String[] args) {
		try {
			new TimeClient().connect(port, "127.0.0.1");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
