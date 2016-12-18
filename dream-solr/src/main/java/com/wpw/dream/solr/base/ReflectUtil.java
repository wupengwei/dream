package com.wpw.dream.solr.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.wpw.solr.test.SupplyDemandIndex;

public class ReflectUtil {

	public static List<Field> getAllFields(Class<? extends Object> clazz) {
		List<Field> list = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			list.add(field);
		}
		return list;
	}

	public static <T> Object invokeGetterMethod(T obj, String property) {
		try {
			String methodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1, property.length());
			Method method = obj.getClass().getMethod(methodName);
			return method.invoke(obj, null);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		SupplyDemandIndex supplyDemandIndex = new SupplyDemandIndex();
		ReflectUtil.setFieldValue(supplyDemandIndex, Double.class, "price", 1.2);
		System.out.println(supplyDemandIndex.getPrice());
	}
	
	public static <T> void setFieldValue(T obj, Class<?> propertyClass, String propertyName, Object propertyValue) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
			String methodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1, propertyName.length());
			Method method = obj.getClass().getMethod(methodName, propertyClass);
			if (propertyClass.equals(Integer.class)) {
				propertyValue = Integer.valueOf(propertyValue.toString());
			} else if (propertyClass.equals(Double.class)) {
				propertyValue = Double.valueOf(propertyValue.toString());
			} else if (propertyClass.equals(Float.class)) {
				propertyValue = Float.valueOf(propertyValue.toString());
			} else if (propertyClass.equals(Long.class)) {
				propertyValue = Long.valueOf(propertyValue.toString());
			} else if (propertyClass.equals(Boolean.class)) {
				propertyValue = Boolean.valueOf(propertyValue.toString());
			}
			method.invoke(obj, propertyValue);
	}

}
