package com.java.thread.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>Decsription: 调度线程池</p>
 * @author  shadow
 * @date  2016年8月12日
 */
public class ScheduledExecutorServiceTest {
	
	public static void main(String[] args) {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
		service.scheduleWithFixedDelay(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					System.out.println(System.currentTimeMillis() / 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 0, 2, TimeUnit.SECONDS);
	}

}
