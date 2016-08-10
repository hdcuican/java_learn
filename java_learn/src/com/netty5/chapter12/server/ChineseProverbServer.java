package com.netty5.chapter12.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年8月5日
 */
public class ChineseProverbServer {
	
	private static final int port = 8080;
	
	public static void main(String[] args) throws Exception {
		EventLoopGroup loopGroup = new NioEventLoopGroup();
		try{
			Bootstrap b = new Bootstrap();
			b.group(loopGroup).channel(NioDatagramChannel.class)
			.option(ChannelOption.SO_BROADCAST, true)
			.handler(new ChannelProverbServerHandler());
			ChannelFuture f = b.bind(port).sync();
			f.channel().closeFuture().sync();
		}finally{
			loopGroup.shutdownGracefully();
		}
		
	}

}
