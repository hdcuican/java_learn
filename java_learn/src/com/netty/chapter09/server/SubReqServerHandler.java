package com.netty.chapter09.server;

import org.apache.commons.lang.StringUtils;

import com.netty.chapter07.pojo.SubscibeResp;
import com.netty.chapter07.pojo.SubscribeReq;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqServerHandler extends ChannelHandlerAdapter{
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		SubscribeReq req = (SubscribeReq) msg;
		if(StringUtils.equals("cc", req.getUserName())) {
			System.out.println("Service accept client subscribe req : [" + req.toString() + "]");
			ctx.writeAndFlush(resp(req.getSubReqID()));
		}
	}
	
	private SubscibeResp resp(int subReqID) {
		SubscibeResp resp = new SubscibeResp();
		resp.setSubReqID(subReqID);
		resp.setRespCode(0);
		resp.setDesc("sssssssssssss");
		return resp;
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
