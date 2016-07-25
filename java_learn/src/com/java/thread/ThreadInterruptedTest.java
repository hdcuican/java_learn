package com.java.thread;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月25日
 */
public class ThreadInterruptedTest {
	
	static class ThreadA extends Thread {
		 /* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while(true) {
				if(Thread.interrupted()) {
					System.out.println("线程被中断了，退出循环。。。。。");
					break;
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					System.out.println("线程在休眠中响应中断。。。。。");
					Thread.currentThread().interrupt();
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ThreadA threadA = new ThreadA();
		threadA.start();
		//保证线程A被执行
		Thread.sleep(500);
		threadA.interrupt();
	}

}
