package com.java.nioNetty;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import com.java.nioNetty.pool.NioSelectorRunnablePool;

/**
 * <p>Decsription: 抽象selector线程类</p>
 * @author  shadow
 * @date  2016年8月10日
 */
public abstract class AbstractNioSelector implements Runnable{
	
	//线程池
	private Executor executor;
	
	//选择器
	protected Selector selector;
	
	//选择器wakenUp状态标记
	final AtomicBoolean wakenUp = new AtomicBoolean();
	
	//任务队列
	private final Queue<Runnable> taskQuene = new ConcurrentLinkedQueue<Runnable>();
	//线程名称
	private String threadName;
	
	/**
	 * 线程管理对象
	 */
	protected NioSelectorRunnablePool selectorRunnablePool;
	
	
	AbstractNioSelector(Executor executor, String threadName, NioSelectorRunnablePool selectorRunnablePool){
		this.executor = executor;
		this.threadName = threadName;
		this.selectorRunnablePool = selectorRunnablePool;
		openSelector();
	}
	
	private void openSelector(){
		try {
			this.selector = Selector.open();
		} catch (IOException e) {
			throw new RuntimeException("Failed to create a selector.");
		}
		executor.execute(this);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Thread.currentThread().setName(this.threadName);
		
		while(true) {
			try{
				wakenUp.set(false);
				select(selector);
				processTaskQueue();
				process(selector);
			}catch(Exception e){
				
			}
		}
	}
	
	/**
	 * 注册一个任务并激活selector
	 * 
	 * @param task
	 */
	protected final void registerTask(Runnable task) {
		taskQuene.add(task);
		Selector selector = this.selector;
		if (selector != null) {
			if (wakenUp.compareAndSet(false, true)) {
				selector.wakeup();
			}
		} else {
			taskQuene.remove(task);
		}
	}

	
	/**
	 * 执行队列里的任务
	 */
	private void processTaskQueue() {
		for(;;){
			final Runnable runnable = taskQuene.poll();
			if(runnable == null){
				break;
			}
			runnable.run();
		}
	}
	/**
	 * select抽象方法
	 * 
	 * @param selector
	 * @return
	 * @throws IOException
	 */
	protected abstract int select(Selector selector) throws IOException;

	/**
	 * selector的业务处理
	 * 
	 * @param selector
	 * @throws IOException
	 */
	protected abstract void process(Selector selector) throws IOException;
	
	
	/**
	 * 获取线程管理对象
	 * @return
	 */
	public NioSelectorRunnablePool getSelectorRunnablePool() {
		return selectorRunnablePool;
	}
	

}
