package com.java.concurrent.tools;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class CyclicBarrierTest {
	
	private static final int THREAD_COUNT = 5;
	static CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_COUNT, new Runnable() {
		
		@Override
		public void run() {
			System.out.println("我是导游， 本次活动结束， 准备下一环节！");
		}
	});
	
	public static void main(String[] args) {
		for(int i = 0; i < THREAD_COUNT; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						System.out.println("我是线程" + Thread.currentThread().getName() + 
								" 到达旅游景点");
						Thread.sleep(new Random().nextInt(1000));
						cyclicBarrier.await();
						System.out.println("我是线程" + Thread.currentThread().getName() + 
								" 开始骑车");
						Thread.sleep(new Random().nextInt(1000));
						cyclicBarrier.await();
						System.out.println("我是线程" + Thread.currentThread().getName() + 
								" 开始爬山");
						cyclicBarrier.await();
						System.out.println("我是线程" + Thread.currentThread().getName() + 
								" 开始乘车回家");
						Thread.sleep(new Random().nextInt(1000));
						cyclicBarrier.await();
						System.out.println("我是线程" + Thread.currentThread().getName() + 
								" 到家了");
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			}, String.valueOf(i)).start();;
		}
	}

}
