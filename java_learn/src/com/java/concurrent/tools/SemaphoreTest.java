package com.java.concurrent.tools;

import java.util.concurrent.Semaphore;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class SemaphoreTest {
	
	private static final Semaphore MAX_SEMAP_HORE = new Semaphore(6);
	
	public static void main(String[] args) {
		for(int i = 0; i < 20; i++) {
			final int num = i;
			new Thread(){
				public void run() {
					try {
						MAX_SEMAP_HORE.acquire(2);
						System.out.println("线程" + num + "得到执行");
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally{
						MAX_SEMAP_HORE.release(2);
					}
				};
			}.start();
		}
	}

}
