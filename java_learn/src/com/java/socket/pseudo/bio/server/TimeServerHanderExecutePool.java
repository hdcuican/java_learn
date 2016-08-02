package com.java.socket.pseudo.bio.server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * 
 * <p>Description: 伪异步IO的线程池<p>
 * @author shadow
 * @date 2016年8月7日
 */
public class TimeServerHanderExecutePool {
	
	private ExecutorService executorService;
	
	public TimeServerHanderExecutePool(int maxPoolSize, int queueSize) {
		executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 
				maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
	}
	
	public void execute(Runnable task) {
		executorService.execute(task);
	}

}
