package com.netty5.chapter08.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import com.netty5.chapter08.protobuf.SubscribeReqProto;

/**
 * 
 * <p>Description: netty服务端  使用Google的ProtoBuf实现PoJO的序列化和反序列化
 * <p>
 * @author shadow
 * @date 2016年8月7日
 */
public class SubReqServer {
	
	private static final int port = 8080;
	
	public void bind(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.handler(new LoggingHandler(LogLevel.INFO))
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
			ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
			/**
			 * ProtobufDecoder仅仅负责解码， 它不支持读半包， 因此在ProtobufDecoder之前使用ProtobufVarint32FrameDecoder
			 * 来处理半包
			 */
			ch.pipeline().addLast(new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance()));
			ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
			ch.pipeline().addLast(new ProtobufEncoder());
			ch.pipeline().addLast(new SubReqServerHandler());
		}
    }
		
    public static void main(String[] args) throws Exception {
		new SubReqServer().bind(port);
	}

}
