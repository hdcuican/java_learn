package com.netty5.chapter05.fixedlength.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 
 * <p>Description: netty服务端  使用FixedLengthFrameDecoder解决Tcp粘包导致的读半包问题
 * FixedLengthFrameDecoder用于对固定长度的消息进行自动解码 无论一次收到多少数据包 他都会按照设置的固定长度进行解码，
 * 如果是半包消息，会缓存半包消息并等待下个包到达后进行拼包，直到读到一个完整的包
 * <p>
 * @author shadow
 * @date 2016年8月7日
 */
public class EchoServer {
	
	private static final int port = 8080;
	
	public void bind(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childHandler(new ChildChannelHandler());
			// 绑定端口 同步等待成功
			ChannelFuture f = b.bind(port).sync();
			// 等待服务端监听端口关闭
			f.channel().closeFuture().sync ();
		}finally{
			//优雅的退出 释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}
	
    static class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
			ch.pipeline().addLast(new StringDecoder());
			ch.pipeline().addLast(new EchoServerHandler());
		}
    }
		
    public static void main(String[] args) throws Exception {
		new EchoServer().bind(port);
	}

}
