package com.java.core.reflect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java.core.annotation.Table;
import com.java.core.annotation.User;
import com.java.core.util.BeanUtil;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月30日
 */
public class ReflectTest {
	
	public static void main(String[] args) throws Exception {
		Table tableAnnotation = User.class.getAnnotation(Table.class);
		System.out.println(tableAnnotation.name());
		
		Map<String , String> map = new HashMap<String, String>();
		map.put("name", "张三");
		map.put("age", "20");
		User user = BeanUtil.convertMapTOBean(map, User.class);
		System.out.println(user);
		
		List<Method> methods = BeanUtil.getMethods(User.class);
		for(Method method : methods) {
			String paramNames = "(";
			Class<?>[] paramClass = method.getParameterTypes();
			for(int i = 0; i < paramClass.length; i++){
				paramNames+=paramClass[i].getName() ;
				if(i != paramClass.length-1)
				paramNames+=",";
			}
			paramNames += ")";
			System.out.println(
					method.getReturnType() + " " +method.getName() + paramNames);
		}
	}

}
