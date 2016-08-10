package com.netty5.chapter12.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年8月5日
 */
public class ChannelProverbClientHandler extends SimpleChannelInboundHandler<DatagramPacket>{

	/* (non-Javadoc)
	 * @see io.netty.channel.SimpleChannelInboundHandler#messageReceived(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext cxt,
			DatagramPacket packet) throws Exception {
		String reqonse = packet.content().toString(CharsetUtil.UTF_8);
		if(reqonse.startsWith("谚语查询结果")){
			System.out.println(reqonse);
			cxt.close();
		}
	}
	
	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
		cause.printStackTrace();
	}

}
