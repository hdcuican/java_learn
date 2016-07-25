package com.java.concurrent.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class LockConditionTest {
	
	private static ReentrantLock lock = new ReentrantLock();
	
	private static Condition condition = lock.newCondition();
	
	static class WaitThread extends Thread {
		
		public WaitThread(String name) {
			super(name);
		}
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
				try {
					lock.lock();
					String threadName = Thread.currentThread().getName();
					System.out.println(System.currentTimeMillis() + "  " + threadName + "  start...");
					condition.await();
					Thread.sleep(2000);
					System.out.println(System.currentTimeMillis() + "  " + threadName + "  end...");
					condition.signal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					lock.unlock();
				}
		}
	}
	
	static class NotifyThread extends Thread {
		
		public NotifyThread(String name) {
			super(name);
		}
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
				try {
					lock.lock();
					String threadName = Thread.currentThread().getName();
					System.out.println(System.currentTimeMillis() + "  " +threadName + "  start...");
					Thread.sleep(3000);
					System.out.println(System.currentTimeMillis() + "  " + threadName + "  end...");
					condition.signal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					lock.unlock();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread notifyThread  = new NotifyThread("notify-threaad-1");
		Thread waitThread1  = new WaitThread("wait-thread-1");
		Thread waitThread2  = new WaitThread("wait-thread-2");
		waitThread1.start();
		waitThread2.start();
		Thread.sleep(1000);
		notifyThread.start();
	}

}
