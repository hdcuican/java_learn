package com.java.concurrent.locks;

import java.util.concurrent.locks.LockSupport;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月25日
 */
public class LockSupportTest {
	
	static class ThreadA extends Thread {
		 /* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName + "  start....");
			//是当前线程挂起
			LockSupport.park(); 
			System.out.println(threadName + "  end....");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new ThreadA();
		Thread t2 = new ThreadA();
		t1.start();
		Thread.sleep(500);
		LockSupport.unpark(t1);
		t2.start();
		LockSupport.unpark(t2);
	}

}
