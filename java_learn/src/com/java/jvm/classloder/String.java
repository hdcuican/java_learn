package com.java.jvm.classloder;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月30日
 */
public class String {
	public static void main(String[] args) {
		/**
		 * 错误: 在类 com.java.jvm.classloder.String 中找不到主方法, 请将主方法定义为:
   		 *	public static void main(String[] args)
   		 * Jdk类加载 双亲委托 String类已经在bootStarp ClassLoader中加载 所以java虚拟机找不到main方法
		 */
		System.out.println(String.class.getClassLoader());
	}

}
