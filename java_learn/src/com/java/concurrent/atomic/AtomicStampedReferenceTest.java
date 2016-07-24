package com.java.concurrent.atomic;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicStampedReferenceTest {
	
	public static final AtomicReference<String> ATOMIC_REFERENCE = new AtomicReference<>("abc");
	private static final Random RANDOM = new Random();
	
	public static void main(String[] args) throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		Thread[] threads = new Thread[100];
		for(int i =0 ; i < threads.length; i++){
			final int num = i;
			threads[i] = new Thread(new Runnable() {
				
				@Override
				public void run() {
					String oldValue = ATOMIC_REFERENCE.get();
					
					try {
						latch.await();
						Thread.sleep(RANDOM.nextInt()&1000);
						if(ATOMIC_REFERENCE.compareAndSet(oldValue, oldValue + num)) {
							System.out.println(num);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			threads[i].start();
		}
		
		Thread.sleep(200);
		latch.countDown();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(RANDOM.nextInt()&300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while(!ATOMIC_REFERENCE.compareAndSet(ATOMIC_REFERENCE.get(), "abc"));
				System.out.println("-----------------");
			}
		}).start();;
	}

}
