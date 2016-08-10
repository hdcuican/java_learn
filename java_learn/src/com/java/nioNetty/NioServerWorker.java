package com.java.nioNetty;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

import com.java.nioNetty.pool.NioSelectorRunnablePool;
import com.java.nioNetty.pool.Worker;

/**
 * <p>
 * Decsription: worker实现类
 * </p>
 * 
 * @author shadow
 * @date 2016年8月10日
 */
public class NioServerWorker extends AbstractNioSelector implements Worker {

	/**
	 * @param executor
	 * @param threadName
	 * @param selectorRunnablePool
	 */
	public NioServerWorker(Executor executor, String threadName,
			NioSelectorRunnablePool selectorRunnablePool) {
		super(executor, threadName, selectorRunnablePool);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.java.nioNetty.pool.Worker#registerNewChannelTask(java.nio.channels
	 * .SocketChannel)
	 */
	@Override
	public void registerNewChannelTask(final SocketChannel channel) {
		final Selector selector = this.selector;
		registerTask(new Runnable() {
			@Override
			public void run() {
				try {
					// 将客户端注册到selector中
					channel.register(selector, SelectionKey.OP_READ);
				} catch (ClosedChannelException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.java.nioNetty.AbstractNioSelector#select(java.nio.channels.Selector)
	 */
	@Override
	protected int select(Selector selector) throws IOException {
		return selector.select(500);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.java.nioNetty.AbstractNioSelector#process(java.nio.channels.Selector)
	 */
	@Override
	protected void process(Selector selector) throws IOException {
		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		if (selectedKeys.isEmpty()) {
			return;
		}
		Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();
		while (ite.hasNext()) {
			SelectionKey key = (SelectionKey) ite.next();
			ite.remove();

			// 得到事件发生的Socket通道
			SocketChannel channel = (SocketChannel) key.channel();

			// 数据总长度
			int ret = 0;
			boolean failure = true;
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			// 读取数据
			try {
				ret = channel.read(buffer);
				failure = false;
			} catch (Exception e) {
			}
			// 判断是否连接已断开
			if (ret <= 0 || failure) {
				key.cancel();
				System.out.println("客户端断开连接");
			} else {
				System.out.println("收到数据:" + new String(buffer.array()));

				// 回写数据
				ByteBuffer outBuffer = ByteBuffer.wrap("收到\n".getBytes());
				channel.write(outBuffer);// 将消息回送给客户端
			}
		}
	}

}
