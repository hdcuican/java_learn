package com.java.nioNetty.pool;

import java.nio.channels.SocketChannel;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年8月10日
 */
public interface Worker {
	
	/**
	 * 加入一个新的客户端会话
	 * @param channel
	 */
	public void registerNewChannelTask(SocketChannel channel);

}
