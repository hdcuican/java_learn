package com.java.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年8月12日
 */
public class ThreadPoolTest implements Runnable{

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
			System.out.println(Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Thread[] threads = new Thread[10];
		for(int i = 0; i < 10; i++) {
			threads[i] = new Thread(new ThreadPoolTest(), "thread-" + i);
		}
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for(int i = 0; i < 10; i++) {
			executorService.submit(threads[i]);
		}
		executorService.shutdown();
	}

}
