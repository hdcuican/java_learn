package com.java.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayTest {
	
	private static AtomicIntegerArray integerArray = new AtomicIntegerArray(10);
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final CountDownLatch latch = new CountDownLatch(10);
		Thread[] threads = new Thread[10];
		for(int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new Runnable() {
				
				@Override
				public void run() {
					for(int j = 0; j < 10000; j++) {
						integerArray.incrementAndGet(j % 10);
					}
					latch.countDown();
				}
			
			});
		}
		for(int i = 0; i < threads.length; i++) {
			executorService.execute(threads[i]);
		}
		latch.await();
		System.out.println(integerArray);
		executorService.shutdown();
	}

}
