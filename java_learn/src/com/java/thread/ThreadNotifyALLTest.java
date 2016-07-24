package com.java.thread;


/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class ThreadNotifyALLTest {
	
	private static Object object = new Object();
	
	static class WaitThread extends Thread {
		
		public WaitThread(String name) {
			super(name);
		}
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			synchronized (object) {
				String threadName = Thread.currentThread().getName();
				System.out.println(System.currentTimeMillis() + "  " + threadName + "  start...");
				try {
					object.wait();
					Thread.sleep(2000);
					System.out.println(System.currentTimeMillis() + "  " + threadName + "  end...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
			synchronized (object) {
				String threadName = Thread.currentThread().getName();
				System.out.println(System.currentTimeMillis() + "  " +threadName + "  start...");
				try {
					Thread.sleep(3000);
					System.out.println(System.currentTimeMillis() + "  " + threadName + "  end...");
					object.notifyAll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread notifyThread  = new NotifyThread("notify-threaad-1");
		Thread waitThread1  = new WaitThread("wait-thread-1");
		Thread waitThread2  = new WaitThread("wait-thread-2");
		Thread waitThread3  = new WaitThread("wait-thread-3");
		waitThread1.start();
		waitThread2.start();
		waitThread3.start();
		Thread.sleep(1000);
		notifyThread.start();
	}

}
