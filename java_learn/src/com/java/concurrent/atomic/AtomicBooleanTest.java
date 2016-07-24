/**
 * 
 */
package com.java.concurrent.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * Decsription:
 * </p>
 * 
 * @author shadow
 * @date 2016年7月24日
 */
public class AtomicBooleanTest {

	public static final AtomicBoolean BOOLEAN = new AtomicBoolean();

	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					if (BOOLEAN.compareAndSet(Boolean.FALSE, Boolean.TRUE)) {
						System.out.println("修改成功");
					}
				}
			});
			thread.start();
		}

	}

}
