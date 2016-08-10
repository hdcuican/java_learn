package com.java.nioNetty.pool;

import java.nio.channels.ServerSocketChannel;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年8月10日
 */
public interface Boss {
	/**
	 * 加入一个新的serverChannel
	 * @param serverChannel
	 */
	public void registerAcceptChannelTask(ServerSocketChannel serverChannel);

}
