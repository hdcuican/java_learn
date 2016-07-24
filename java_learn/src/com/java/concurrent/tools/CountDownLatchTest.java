package com.java.concurrent.tools;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class CountDownLatchTest {
	
	private static final int GROUP_SIZE = 5;
	
	private static void processOneGroup(final String groupNmae) throws InterruptedException {
		final CountDownLatch preCountDown = new CountDownLatch(GROUP_SIZE);
		final CountDownLatch startCountDown = new CountDownLatch(1);
		final CountDownLatch endCountDown = new CountDownLatch(GROUP_SIZE);
		System.out.println("====分组" + groupNmae + "比赛开始");
		for(int i = 0; i < GROUP_SIZE ; i++) {
			final int num = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println("线程" + num +"已经准备就绪！");
					preCountDown.countDown();
					try {
						startCountDown.await();
						Thread.sleep(new Random().nextInt(1000));
						endCountDown.countDown();
						System.out.println("线程" + num +"已经执行完成！");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}).start();;
		}
		
		preCountDown.await();
		System.out.println("各就各位....");
		startCountDown.countDown();
		endCountDown.await();
		System.out.println("比赛结束....");
	}
	
	public static void main(String[] args) throws InterruptedException {
		processOneGroup("分组1");
		processOneGroup("分组2");
	}

}
