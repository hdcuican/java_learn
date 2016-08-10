package com.netty5.chapter07.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 <p>Description: netty服务端  使用ObjectEncoder和ObjectDecoder实现普通PoJO的序列化和反序列化
 * DelimiterBasedFrameDecoder用于对使用分割符结尾的消息进行自动解码
 * <p>
 * @author shadow
 * @date 2016年8月7日
 */
public class SubReqClient {
	
	private final static int port = 8080;
	
	public void connect(int port, String host) throws InterruptedException {
		
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//解码
					ch.pipeline().addLast(new ObjectDecoder(1024*1024, ClassResolvers
							.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
					//编码
					ch.pipeline().addLast(new ObjectEncoder());
					ch.pipeline().addLast(new SubReqClientHandler());
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
			new SubReqClient().connect(port, "127.0.0.1");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
