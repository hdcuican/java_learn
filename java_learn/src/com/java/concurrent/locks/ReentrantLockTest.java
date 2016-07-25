package com.java.concurrent.locks;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月25日
 */
public class ReentrantLockTest {
	
	private static int count;
	
	private static final int THREAD_NUM = 100;
	
	private static ReentrantLock lock = new ReentrantLock();
	
	static class ThreadA extends Thread {
		
		private CountDownLatch latch;
		
		public ThreadA(CountDownLatch latch) {
			this.latch = latch;
		}
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			try{
				lock.lock();
				for(int i = 0; i < 1000; i++) {
					count++;
				}
				latch.countDown();
			}finally{
				lock.unlock();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException  {
		final CountDownLatch endDownLatch = new CountDownLatch(THREAD_NUM);
		ExecutorService executorService = Executors.newCachedThreadPool();
		Thread[] threads = new Thread[THREAD_NUM];
		for(int i = 0; i < THREAD_NUM; i ++) {
			threads[i] = new ThreadA(endDownLatch);
			executorService.submit(threads[i]);
		}
		
		endDownLatch.await();
		
		System.out.println(count);
		executorService.shutdown();
	}

}
