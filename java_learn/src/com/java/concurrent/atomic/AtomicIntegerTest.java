package com.java.concurrent.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
	
	private static AtomicInteger value = new AtomicInteger(0);
	

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final CountDownLatch latch = new CountDownLatch(100);
		for(int i = 0; i < 100; i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					for(int j = 0; j < 10000; j++) {
						value.incrementAndGet();
					}
					latch.countDown();
				}
			});
		}
		latch.await();
		System.out.println(value);
		executorService.shutdown();
	}

}
