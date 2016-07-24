package com.java.concurrent.atomic;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnSafeTest {
	
	int intV = 2;
	
	static int staticIntV;

	public static void main(String[] args) throws Exception, SecurityException {
		Field field1 = UnSafeTest.class.getDeclaredField("intV");
		Field field2 = UnSafeTest.class.getDeclaredField("staticIntV");
		Unsafe unsafe = getUnSafe();
		System.out.println(unsafe.objectFieldOffset(field1));
		System.out.println(unsafe.staticFieldOffset(field2));
		
		
	}
	
	public static Unsafe getUnSafe() throws IllegalArgumentException, IllegalAccessException {
		Field field = Unsafe.class.getDeclaredFields()[0];
		field.setAccessible(true);
		Unsafe unsafe = (Unsafe)field.get(null);
		return unsafe;
	}
}
