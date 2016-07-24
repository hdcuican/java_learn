/**
 * 
 */
package com.java.concurrent.atomic.updater;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class AtomicIntegrFiledUpdaterTest {
	
		static class A {
				volatile int intValue = 10;
		}
		
		public static final AtomicIntegerFieldUpdater<A> ATOMIC_INTEGER_FIELD_UPDATER =
								AtomicIntegerFieldUpdater.newUpdater(A.class, "intValue");
		
		public static void main(String[] args) throws InterruptedException {
			    final CountDownLatch startLatch = new CountDownLatch(1);
			    final A a = new A();
				for(int i = 0; i < 100; i++) {
						final int num = i;
						new Thread(new Runnable() {
							@Override
							public void run() {
								   try {
									startLatch.await();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
									if(ATOMIC_INTEGER_FIELD_UPDATER.compareAndSet(a, 10, 100)) {
										   System.out.println("第" + num + "线程成功修改成功");
									}
							}
						}).start();;
				}
			   Thread.sleep(200);
			   startLatch.countDown();
			   
		}
}
