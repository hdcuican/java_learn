package com.java.thread;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTaskTest {
	
	static class CallImpl implements Callable<String> {
		
		private String name;
		
		public CallImpl(String name) {
			this.name = name;
		}
		public String call() {
			try {
				Random random = new Random();
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "线程:" + name + "执行完成！";
		}
	}

	public static void main(String []args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		List<Future<String>> list = Arrays.asList(
			executorService.submit(new CallImpl("t1")) , 
			executorService.submit(new CallImpl("t2")) ,
			executorService.submit(new CallImpl("t3"))
		);
		for(Future<String> future : list) {
			String result = future.get();//阻塞 直到得到所有线程执行结果
			System.out.println(result + "\t" + System.currentTimeMillis());
		}
		executorService.shutdown();
	}
}
