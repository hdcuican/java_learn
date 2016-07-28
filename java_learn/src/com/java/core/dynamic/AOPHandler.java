package com.java.core.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月28日
 */
public class AOPHandler implements InvocationHandler{
	
	private Object target;
	
	public AOPHandler(Object target) {
		this.target = target;
	}

	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("方法调用前 先执行我。。。。。");
		Object proxyObject = method.invoke(target, args);
		System.out.println("方法调用后 再执行我。。。。。");
		return proxyObject;
	}

}

