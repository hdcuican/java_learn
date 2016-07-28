package com.java.core.dynamic.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.java.core.dynamic.HelloImpl;

public class CglibProxyTest {

	@SuppressWarnings("unchecked")
	public static <T> T createProxy(Class<T> targetClass) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setUseCache(false);
		enhancer.setCallback(new MethodInterceptor() {
			public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
				System.out.println("方法调用前 先执行我。。。。。");
				Object obj = methodProxy.invokeSuper(object, args);
				System.out.println("方法调用后 再执行我。。。。。");
				return obj;
			}
		});
		return (T)enhancer.create();
	}

	public static void main(String[] args) {
		HelloImpl helloImpl = createProxy(HelloImpl.class);
		helloImpl.sayHello();
		helloImpl.sayHI();
	}
}
