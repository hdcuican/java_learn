package com.netty.chapter05.delimiter.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 <p>Description: netty服务端  使用DelimiterBasedFrameDecoder解决Tcp粘包导致的读半包问题
 * DelimiterBasedFrameDecoder用于对使用分割符结尾的消息进行自动解码
 * <p>
 * @author shadow
 * @date 2016年8月7日
 */
public class EchoClient {
	
	private final static int port = 8080;
	
	public void connect(int port, String host) throws InterruptedException {
		
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new EchoClientHandler());
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
			new EchoClient().connect(port, "127.0.0.1");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
