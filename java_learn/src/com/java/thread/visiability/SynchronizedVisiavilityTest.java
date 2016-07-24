package com.java.thread.visiability;


/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class SynchronizedVisiavilityTest {
	
	static class ThreadA extends Thread {
		
		private boolean flag;
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while(!getFlag()) {
				//do..
			}
			System.out.println(flag);
		}
		
		public synchronized void  readyOn() {
			flag = true;
		}
		public synchronized boolean getFlag() {
			return flag;
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
