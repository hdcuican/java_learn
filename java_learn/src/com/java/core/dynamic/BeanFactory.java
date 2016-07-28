package com.java.core.dynamic;

import java.lang.reflect.Proxy;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月28日
 */
public class BeanFactory {
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String className, Class<T> clazz) throws Exception {
		Object object = Class.forName(className).newInstance();
		return (T)Proxy.newProxyInstance(object.getClass().getClassLoader(), 
				object.getClass().getInterfaces(), new AOPHandler(object));
	}
	
	public static void main(String[] args) throws Exception {
		HelloInterface helloInterface = getBean("com.java.core.dynamic.HelloImpl",
				HelloInterface.class);
		helloInterface.sayHello();
		helloInterface.sayHI();
	}
	
}
