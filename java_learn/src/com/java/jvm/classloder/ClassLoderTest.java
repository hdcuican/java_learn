package com.java.jvm.classloder;


/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月30日
 */
public class ClassLoderTest {
	
	public static void main(String[] args) {
		ClassLoader classLoader =  ClassLoderTest.class.getClassLoader();
		System.out.println(classLoader);
		System.out.println(classLoader.getParent());
		System.out.println(classLoader.getParent().getParent());
	}

}
