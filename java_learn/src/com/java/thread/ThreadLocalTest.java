package com.java.thread;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月24日
 */
public class ThreadLocalTest {
	
	static class ResourceClass {
		public static final ThreadLocal<String> THREAD_LOCAL_1 = new ThreadLocal<>();
		public static final ThreadLocal<String> THREAD_LOCAL_2 = new ThreadLocal<>();
	}
	
	static class A {
		public 	void setOne(String value) {
			ResourceClass.THREAD_LOCAL_1.set(value);
		}
		
		public void setTwo(String value){
			ResourceClass.THREAD_LOCAL_2.set(value);
		}
	}
	
	static class B {
		public void dispaly() {
			System.out.println(ResourceClass.THREAD_LOCAL_1.get() + ":" + ResourceClass.THREAD_LOCAL_2.get());
		}
	}
	
	public static void main(String[] args) {
		final A a = new A();
		final B b = new B();
		for(int i = 0; i < 20 ; i++) {
			final String resouce1 = "key-" + i, resouce2 = " value = (" + i + ")";
			new Thread() {  
                public void run() {  
                    try {  
                        a.setOne(resouce1);
                        a.setTwo(resouce2);
                        b.dispaly();
                    }finally {  
                    	ResourceClass.THREAD_LOCAL_1.remove();
                    	ResourceClass.THREAD_LOCAL_2.remove();
                    }  
                }  
            }.start();
		}
	}

}
