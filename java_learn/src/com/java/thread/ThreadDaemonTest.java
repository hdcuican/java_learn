/**
 * 
 */
package com.java.thread;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class ThreadDaemonTest {
	
		public static void main(String[] args) throws InterruptedException {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						 for(;;) {
							 //do....
						 }
					}
				});
				//设置为守护线程
				thread.setDaemon(Boolean.TRUE);
				thread.start();
				Thread.sleep(200);
				System.out.println("main方法结束");
		}
}
