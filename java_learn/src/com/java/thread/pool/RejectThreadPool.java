
package com.java.thread.pool;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>Decsription: 自定义线程丢弃策略</p>
 * @author  shadow
 * @date  2016年8月12日
 */
public class RejectThreadPool {
	
	static class MyTask implements Runnable {
		
		private String name;
		
		public MyTask(String name){
			this.name = name;
		}

		@Override
		public void run() {
			System.out.println("当前正在执行线程名为： " + name + "的线程");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0, 
				TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),
				Executors.defaultThreadFactory(),
				new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.out.println(((MyTask) r).name + " is discard.");
					}
				}) {
		};
		
		for(int i = 0; i < 100; i++) {
			executorService.execute(new MyTask("thread-"+i));
			Thread.sleep(10);
		}
		
		executorService.shutdown();
		
	}

}
