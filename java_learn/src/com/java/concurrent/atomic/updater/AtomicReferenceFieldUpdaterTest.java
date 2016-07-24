/**
 * 
 */
package com.java.concurrent.atomic.updater;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;


/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class AtomicReferenceFieldUpdaterTest {
	
		static class A {
				volatile String  strValue = "abc";
		}
		
		public static final AtomicReferenceFieldUpdater<A,  String> ATOMIC_REFERENCE_FIELD_UPDATER
							=  AtomicReferenceFieldUpdater.newUpdater(A.class, String.class, "strValue");
		
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
								if(ATOMIC_REFERENCE_FIELD_UPDATER.compareAndSet(a, "abc", "def")) {
									   System.out.println("第" + num + "线程成功修改成功");
									   System.out.println("被修改后对象的值为："  +  ATOMIC_REFERENCE_FIELD_UPDATER.get(a));
								}
						}
					}).start();;
			}
		   Thread.sleep(200);
		   startLatch.countDown();
	}

}
