/**
 * 
 */
package com.java.thread;


/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class ThreadBaseTest {
	
	public static void main(String[] args) {
		Thread thread1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				 System.out.println(Thread.currentThread().getName());
			}
		}, "thread-1");
		
		Thread thread2 = new Thread("thread-2") {
			@Override
			public void run() {
				 System.out.println(Thread.currentThread().getName());
			}
		};
		
		thread1.start();
		thread2.start();
		
		System.out.println(Thread.currentThread().getName());
		System.out.println("mian线程运行........");
	}

}
