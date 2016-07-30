package com.java.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.java.core.annotation.Column;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月30日
 */
public class BeanUtil {
	
	public static List<Field>  getFileds(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<Field>();
		Set<String> fieldNames = new HashSet<String>();
		Class<?> classTemp = clazz;
		if(classTemp != Object.class) {
			Field[] fileds = classTemp.getDeclaredFields();
			for(Field field : fileds) {
				if(!fieldNames.contains(field.getName())){
					fieldList.add(field);
					fieldNames.add(field.getName());
				}
			}
		}
		return fieldList;
	}
	
	public static List<Method>  getMethods(Class<?> clazz) {
		List<Method> methodList = new ArrayList<Method>();
		Set<String> fieldNames = new HashSet<String>();
		Class<?> classTemp = clazz;
		if(classTemp != Object.class) {
			Method[] methods = classTemp.getDeclaredMethods();
			for(Method method : methods) {
				if(!fieldNames.contains(method.getName())){
					methodList.add(method);
					fieldNames.add(method.getName());
				}
			}
		}
		return methodList;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T convertMapTOBean(Map<String, String> map, Class<T> clazz) throws Exception {
		Object object = clazz.newInstance();
		List<Field> fields = getFileds(clazz);
		for(Field field : fields) {
			if(!Modifier.isFinal(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
				if(!field.isAccessible()) {
					field.setAccessible(Boolean.TRUE);
					Column column = field.getAnnotation(Column.class);
					if(column != null) {
						String value = map.get(column.name());
						Class<?> filedType = field.getType();
						if(filedType == String.class) {
							field.set(object, value);
						}else if(filedType == Integer.class || filedType == int.class) {
							field.set(object, Integer.valueOf(value));
						}else if(filedType == Long.class || filedType == long.class) {
							field.setLong(object , Long.valueOf(value));
						}else{
							//TODO
						}
					}
				}
			}
		}
		return (T) object;
	}

}
