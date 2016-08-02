package com.netty.chapter07.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.apache.commons.lang.StringUtils;

import com.netty.chapter07.protobuf.SubscribeReqProto;
import com.netty.chapter07.protobuf.SubscribeRespProto;

public class SubReqServerHandler extends ChannelHandlerAdapter{
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
		if(StringUtils.equals("cc", req.getUserName())) {
			System.out.println("Service accept client subscribe req : [" + req.toString() + "]");
			ctx.writeAndFlush(resp(req.getSubReqID()));
		}
	}
	
	private SubscribeRespProto.SubscribeResp resp(int subReqID) {
		SubscribeRespProto.SubscribeResp.Builder builder = 
				SubscribeRespProto.SubscribeResp.newBuilder();
		builder.setSubReqID(subReqID);
		builder.setDesc("2222222");
		builder.setRespCode("0");
		return builder.build();
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

}
