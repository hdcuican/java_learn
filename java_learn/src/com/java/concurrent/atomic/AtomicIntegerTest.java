package com.java.concurrent.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月22日
 */
public class AtomicIntegerTest {
	
	private static AtomicInteger value = new AtomicInteger(0);
	
	private static volatile  int  volatile_Value  = 0; 

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch endLatch = new CountDownLatch(100);
		for(int i = 0; i < 100; i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					try {
						startLatch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					};
					for(int j = 0; j < 10000; j++) {
						value.incrementAndGet();
						volatile_Value++;
					}
					endLatch.countDown();
				}
			});
		}
		Thread.sleep(200);
		startLatch.countDown();
		endLatch.await();
		System.out.println("Atomic 运行结果：" + value.get());
		//因 i++非原子性操作， 多核计算机运行下 值小于10000000
		System.out.println("volatile 运行结果：" + volatile_Value);
		executorService.shutdown();
	}

}
