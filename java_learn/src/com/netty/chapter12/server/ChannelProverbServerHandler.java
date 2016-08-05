package com.netty.chapter12.server;

import java.util.concurrent.ThreadLocalRandom;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * <p>Decsription: netty服务端 UDP通信服务类</p>
 * @author  shadow
 * @date  2016年8月5日
 */
public class ChannelProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket>{
	
	private static final String[] DICTORY = {"好雨知时节，当春乃发生。", "随风潜入夜，润物细无声。",
											 "野径云俱黑，江船火独明。", "晓看红湿处，花重锦官城。"};
	
	private String nextQuote() {
		int quotedId = ThreadLocalRandom.current().nextInt(DICTORY.length);
		return DICTORY[quotedId];
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.SimpleChannelInboundHandler#messageReceived(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext cxt,
			DatagramPacket packet) throws Exception {
		String req = packet.content().toString(CharsetUtil.UTF_8);
		System.out.println(req);
		if("谚语字典查询？".equals(req)){
			cxt.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("谚语查询结果： " + nextQuote(),
					CharsetUtil.UTF_8), packet.sender()));
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
