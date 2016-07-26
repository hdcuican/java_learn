package com.java.concurrent.atomic.reference;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicStampedReference;
/**
 * <p>Decsription: 使用AtomicStampedReference处理ABA问题</p>
 * @author  shadow
 * @date  2016年7月22日
 */
public class AtomicStampedReferenceTest {
	
	public static final AtomicStampedReference<String> ATOMIC_REFERENCE = new AtomicStampedReference<String>("abc", 0);
	private static final Random RANDOM = new Random();
	
	public static void main(String[] args) throws InterruptedException {
		final CountDownLatch satrtLatch = new CountDownLatch(1);
		Thread[] threads = new Thread[100];
		for(int i =0 ; i < threads.length; i++){
			final int num = i;
			threads[i] = new Thread(new Runnable() {
				
				@Override
				public void run() {
					String oldValue = ATOMIC_REFERENCE.getReference();
					int stamp = ATOMIC_REFERENCE.getStamp();
					
					try {
						satrtLatch.await();
						Thread.sleep(RANDOM.nextInt()&1000);
						if(ATOMIC_REFERENCE.compareAndSet(oldValue, oldValue + num, stamp, stamp+1)) {
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
		satrtLatch.countDown();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(RANDOM.nextInt()&300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int stamp = ATOMIC_REFERENCE.getStamp();
				while(!ATOMIC_REFERENCE.compareAndSet(ATOMIC_REFERENCE.getReference(), "abc", stamp, stamp+1));
				System.out.println("改回来了。。。。。");
			}
		}).start();;
	}

}
