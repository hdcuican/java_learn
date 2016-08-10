package com.netty5.chapter05.delimiter.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoClientHandler extends ChannelHandlerAdapter{
	
	private int counter;
	
	public EchoClientHandler() {
	}
	
	/**
	 * 当客户端和服务端TCP链路建立成功后， 会调用此方法
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for(int i = 0; i < 100; i++) {
			ctx.writeAndFlush(Unpooled.copiedBuffer("QUERY TIME ORDER$_".getBytes()));
		}
		
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String body = (String) msg;
		System.out.println("Now is : " + body + " ; the counter is " + ++counter);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
}
