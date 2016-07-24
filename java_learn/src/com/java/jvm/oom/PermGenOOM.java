package com.java.jvm.oom;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月22日
 */
public class PermGenOOM {
	
	public static void main(String[] args) {
		int i = 0;
		List<String> list = new ArrayList<>();
		for(;;) {
			list.add(("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+i).intern());
		}
	}

}
