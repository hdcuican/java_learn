package com.netty5.chapter13.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.RandomAccessFile;

public class FileServerHandler extends SimpleChannelInboundHandler<String>{
	
	private static final String CR = System.getProperty("line.separator");
	
	/* (non-Javadoc)
	 * @see io.netty.channel.SimpleChannelInboundHandler#messageReceived(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext cxt, String msg)
			throws Exception {
		File file = new File(msg);
		if(file.exists()) {
			if(!file.isFile()){
				cxt.writeAndFlush("No file " + file + CR);
			}
			cxt.writeAndFlush("file " + file.length() + CR);
			RandomAccessFile randomAccessFile = new RandomAccessFile(msg, "r");
			FileRegion fileRegion = new DefaultFileRegion(randomAccessFile.getChannel(), 0, randomAccessFile.length());
			cxt.write(fileRegion);
			cxt.writeAndFlush(CR);
			randomAccessFile.close();
		}else{
			cxt.writeAndFlush("File not found: " + file + CR);
		}
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
