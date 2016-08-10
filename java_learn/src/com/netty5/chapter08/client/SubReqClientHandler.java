package com.netty5.chapter08.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.netty5.chapter07.pojo.SubscribeReq;
import com.netty5.chapter08.protobuf.SubscribeReqProto;

public class SubReqClientHandler extends ChannelHandlerAdapter{
	
	
	public SubReqClientHandler() {
	}
	
	/**
	 * 当客户端和服务端TCP链路建立成功后， 会调用此方法
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for(int i = 0; i < 100; i++) {
			ctx.write(subReq(i));
		}
		ctx.flush();
	}
	
	private SubscribeReqProto.SubscribeReq subReq(int i) {
		SubscribeReqProto.SubscribeReq.Builder builder =
				SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setAddrsss("浙江杭州");
		builder.setProductName("aaaaaddd");
		builder.setSubReqID(i);
		builder.setUserName("cc");
		return builder.build();
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("Receive server response : [" + msg + "]");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
}
