package com.netty5.chapter11;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 
 * <p>
 * Decsription:
 * </p>
 * 
 * @author shadow
 * @date 2016年8月6日
 */
public class NettyServer {
	public static void main(String[] args) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();

		try {

			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new ChildChannelHandler());

			System.out.println("服务端开启等待客户端连接 ... ...");

			Channel ch = b.bind(7777).sync().channel();

			ch.closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	
	public static class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel e) throws Exception {
			e.pipeline().addLast(new HttpServerCodec());
			e.pipeline().addLast(new HttpObjectAggregator(65536));
			e.pipeline().addLast(new ChunkedWriteHandler());
			e.pipeline().addLast(new WebSocketServerHandler());
		}
	}

}