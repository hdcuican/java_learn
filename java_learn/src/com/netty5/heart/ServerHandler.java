package com.netty5.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerHandler extends SimpleChannelInboundHandler<String>{

	/* (non-Javadoc)
	 * @see io.netty.channel.SimpleChannelInboundHandler#messageReceived(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext cxt, String msg)
			throws Exception {
		System.out.println(msg);
		cxt.writeAndFlush("hi");
		
	}
	
	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelHandlerAdapter#userEventTriggered(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	public void userEventTriggered(final ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if(evt instanceof IdleStateEvent) {
			IdleStateEvent 	event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
                System.out.println("--- Reader Idle ---");
                ctx.writeAndFlush("读取等待：客户端你在吗... ...\r\n");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("--- Write Idle ---");
                ctx.writeAndFlush("写入等待：客户端你在吗... ...\r\n");
            } else if (event.state() == IdleState.ALL_IDLE) {
                System.out.println("--- All_IDLE ---");
                ctx.writeAndFlush("全部时间：客户端你在吗... ...\r\n");
            }
		}else{
			super.userEventTriggered(ctx, evt);
		}
	}
	
	/* 客户端连接
	 * (non-Javadoc)
	 * @see io.netty.channel.ChannelHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive");
		super.channelActive(ctx);
	}
	
	/* 客户端断开
	 * (non-Javadoc)
	 * @see io.netty.channel.ChannelHandlerAdapter#channelInactive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelInactive");
		super.channelInactive(ctx);
	}
	
	/* 异常
	 * (non-Javadoc)
	 * @see io.netty.channel.ChannelHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}
	

}
