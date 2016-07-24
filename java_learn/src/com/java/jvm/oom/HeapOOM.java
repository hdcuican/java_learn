package com.java.jvm.oom;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {  
	
	
	public static void main(String[] args) {
		List<String> strings = new ArrayList<String>();
		for(;;) {
			strings.add("aaaaaaaaaa");
		}
	}

}
