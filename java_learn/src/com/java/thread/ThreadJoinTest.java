/**
 * 
 */
package com.java.thread;


/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class ThreadJoinTest {
	
		static class Computer extends Thread {
			private int start;
			private int end;
			private int result;
			private int []array;
			
			public Computer(int []array , int start , int end) {
				this.array = array;
				this.start = start;
				this.end = end;
			}
			
			public void run() {
				for(int i = start; i < end ; i++) {
					result += array[i];
				}
			}
			
			public int getResult() {
				return result;
			}
		}
		
		public static final int COUNTET = 10000;
		public static void main(String[] args) throws InterruptedException {
				int[] arrays = new int[COUNTET];
				int num;
				for(int i = 0; i < COUNTET; i++) {
					    num = i+1;
						arrays[i] = num;
				}
			   Computer computer1 = new Computer(arrays, 0, COUNTET/2);
			   Computer computer2 = new Computer(arrays, COUNTET/2, COUNTET);
			   computer1.start();
			   computer2.start();
			   computer1.join();
			   computer2.join();
			   int result = computer1.getResult() + computer2.getResult();
			   System.out.println("最终计算结果： " +  result);
		}

}
