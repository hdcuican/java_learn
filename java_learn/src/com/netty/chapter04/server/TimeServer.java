package com.netty.chapter04.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 
 * <p>Description: netty服务端  使用LineBasedFrameDecoder和 StringDecoder解决Tcp粘包导致的读半包问题
 * LineBasedFrameDecoder和 StringDecoder的组合就是按行切换的文本解码器
 * <p>
 * @author shadow
 * @date 2016年8月7日
 */
public class TimeServer {
	
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
			ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
			ch.pipeline().addLast(new StringDecoder());
			ch.pipeline().addLast(new TimeServerHandler());
		}
    }
		
    public static void main(String[] args) throws Exception {
		new TimeServer().bind(port);
	}

}
