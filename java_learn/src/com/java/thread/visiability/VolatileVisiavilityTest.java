package com.java.thread.visiability;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class VolatileVisiavilityTest {
	
	static class ThreadA extends Thread {
		
		private volatile boolean flag;
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while(!flag) {
				//do..
			}
			System.out.println(flag);
		}
		
		public void readyOn() {
			flag = true;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ThreadA threadA = new ThreadA();
		threadA.start();
		//保证线程A已启动
		Thread.sleep(200);
		threadA.readyOn();
		System.out.println(threadA.flag);
	}
	

}
