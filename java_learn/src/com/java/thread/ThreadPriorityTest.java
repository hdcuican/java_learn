package com.java.thread;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月25日
 */
public class ThreadPriorityTest extends Thread {
	
	public ThreadPriorityTest(String name) {
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		System.out.println("我是"+this.getName()+"线程， 我在执行。。。。");
	}
	
	public static void main(String[] args) {
		Thread LOW_PRIORITY_THREAD = new ThreadPriorityTest("low-priority-thread");
		Thread HiGH_PRIORITY_THREAD = new ThreadPriorityTest("high-priority-thread");
		
		LOW_PRIORITY_THREAD.setPriority(Thread.MIN_PRIORITY);
		HiGH_PRIORITY_THREAD.setPriority(Thread.MAX_PRIORITY);
		
		LOW_PRIORITY_THREAD.start();
		HiGH_PRIORITY_THREAD.start();
	}

}
