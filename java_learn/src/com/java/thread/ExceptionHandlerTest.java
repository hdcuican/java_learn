package com.java.thread;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class ExceptionHandlerTest {
	
	/**
	 * 
	 * <p>Decsription: 对run方法没有捕获的异常进行捕获处理</p>
	 * @author  shadow
	 * @date  2016年7月24日
	 */
	static class TestExceptionHandler implements UncaughtExceptionHandler {

		/* (non-Javadoc)
		 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable)
		 */
		@Override
		public void uncaughtException(Thread t, Throwable e) {
			System.out.println("线程出现异常： ");
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i = 1 / 0;
				System.out.println(i);
			}
		});
		t.setUncaughtExceptionHandler(new TestExceptionHandler());
		t.start();
	}

}
