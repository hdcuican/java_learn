package com.netty5.chapter12.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * <p>Decsription:  netty客户端 UDP通信客户端类</p>
 * @author  shadow
 * @date  2016年8月5日
 */
public class ChineseProverbClient {
	
	private static final int port = 8080;
	
	public static void main(String[] args) throws Exception {
		EventLoopGroup loopGroup = new NioEventLoopGroup();
		try{
			Bootstrap b = new Bootstrap();
			b.group(loopGroup).channel(NioDatagramChannel.class)
			.option(ChannelOption.SO_BROADCAST, true)
			.handler(new ChannelProverbClientHandler());
			Channel ch = b.bind(0).sync().channel();
			//向网段内所有的机器广播UDP消息
			ch.writeAndFlush(
					new DatagramPacket(Unpooled.copiedBuffer("谚语字典查询？", CharsetUtil.UTF_8),
					new InetSocketAddress("255.255.255.255", port))).sync();
			//客户端等待15s接受服务端的应答消息，然后退出释放资源
			if(!ch.closeFuture().await(15000)){
				System.out.println("查询超时");
			}
		}finally{
			loopGroup.shutdownGracefully();
		}
		
	}

}
