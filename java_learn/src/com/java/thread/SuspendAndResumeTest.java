package com.java.thread;


/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class SuspendAndResumeTest {
	
	static class A implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName + "  start....");
			Thread.currentThread().suspend();
			System.out.println(threadName + "  end....");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(new A(), "thread-1");
		Thread thread2 = new Thread(new A(), "thread-2");
		thread1.start();
		//保证线程1已得到执行
		Thread.sleep(200);
		thread2.start();
		thread1.resume();
		//线程2 resume方法执行发生在suspend之前， 线程2无法被唤醒 产生死锁
		thread2.resume();
	}

}
