package com.java.concurrent.atomic.reference;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
/**
 * <p>Decsription:  模拟线程操作ABA问题</p>
 * @author  shadow
 * @date  2016年7月22日
 */
public class AtomicReferenceABATest {
	
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
							System.out.println("线程" + num + "对对象进行了修改");
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
					e.printStackTrace();
				}
				while(!ATOMIC_REFERENCE.compareAndSet(ATOMIC_REFERENCE.get(), "abc"));
				System.out.println("已经改回来。。。");
			}
		}).start();;
	}

}
