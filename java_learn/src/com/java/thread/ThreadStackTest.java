package com.java.thread;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class ThreadStackTest {
	
	private static void printStack(StackTraceElement[] stacks) {
		for(StackTraceElement element : stacks) {
			if(element != null) {
				System.out.println(element);
			}
		}
		System.out.println("..........\n");
	}
	
	private static StackTraceElement[] getStacksByThread() {
		return Thread.currentThread().getStackTrace();
	}
	
	private static StackTraceElement[] getStacksByException() {
		return new Exception().getStackTrace();
	}
	
	public static void main(String[] args) {
		printStack(getStacksByThread());
		printStack(getStacksByException());
	}

}
