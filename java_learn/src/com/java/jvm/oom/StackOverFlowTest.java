package com.java.jvm.oom;

public class StackOverFlowTest {
	
	private static int stack_deep_length = 0;
	
	public static void main(String[] args) {
		try {
			sum(1,2,3, 4);
		} catch (Exception e) {
		} finally{
			System.out.println(stack_deep_length);
		}
		
	}
	
	public static void sum(int a, int b, int c, int d) {
		stack_deep_length++;
		sum(a,b, c, d);
	}

}
