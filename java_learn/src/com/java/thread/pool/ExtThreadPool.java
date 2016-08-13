package com.java.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>Decsription: 自定义扩展线程池</p>
 * @author  shadow
 * @date  2016年8月12日
 */
public class ExtThreadPool {
	
	static class MyTask implements Runnable {
		
		private String name;
		
		public MyTask(String name){
			this.name = name;
		}

		@Override
		public void run() {
			System.out.println("当前正在执行线程名为： " + name + "的线程");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		//类比一个固定大小的线程池
		ExecutorService executorService = new ThreadPoolExecutor(10, 10, 0, 
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()) {
			@Override
			protected void beforeExecute(Thread t, Runnable runnable) {
				System.out.println("准备执行线程： " + ((MyTask) runnable).name);
			}
			
			@Override
			protected void afterExecute(Runnable runnable, Throwable t) {
				System.out.println("线程： " + ((MyTask) runnable).name + "准备执行完成");
			}
			@Override
			protected void terminated() {
				System.out.println("线程池退出");
			}
		};
		
		for(int i = 0; i < 100; i++) {
			executorService.execute(new MyTask("thread-"+i));
			Thread.sleep(10);
		}
		
		executorService.shutdown();
		
	}

}
