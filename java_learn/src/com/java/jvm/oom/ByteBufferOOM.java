package com.java.jvm.oom;

import java.nio.ByteBuffer;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月22日
 */
public class ByteBufferOOM {
	
	private static int MB_1 = 1024 * 1024;
	
	public static void main(String[] args) {
		ByteBuffer.allocateDirect(MB_1 * 257);
	}

}
