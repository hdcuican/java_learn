package com.java.core.dynamic;
public class HelloImpl implements HelloInterface {

	/* (non-Javadoc)
	 * @see com.java.core.dynamic.HelloInterface#sayHello()
	 */
	@Override
	public void sayHello() {
		System.out.println("Hello");
	}

	/* (non-Javadoc)
	 * @see com.java.core.dynamic.HelloInterface#sayHI()
	 */
	@Override
	public void sayHI() {
		System.out.println("HI");
	}
	
}
