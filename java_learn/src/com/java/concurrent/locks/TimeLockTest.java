package com.java.concurrent.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月28日
 */
public class TimeLockTest implements Runnable{
	
	private static ReentrantLock lock = new ReentrantLock();

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			if(lock.tryLock(5, TimeUnit.SECONDS)) {
				System.out.println("线程" + Thread.currentThread().getName() + "得到锁， 开始执行。。。");
				Thread.sleep(6000L);
			}else{
				System.out.println("线程" + Thread.currentThread().getName()+"在规定时间内没有获得锁。。。");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new TimeLockTest(), "thread-1");
		Thread t2 = new Thread(new TimeLockTest(), "thread-2");
		
		t1.start();
		//保证thread-1已得到执行
		Thread.sleep(200);
		t2.start();
	}

}
