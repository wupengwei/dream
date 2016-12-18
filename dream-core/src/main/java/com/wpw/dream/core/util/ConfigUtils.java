package com.wpw.dream.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件处理公共类
 * @author Administrator
 *
 */
public class ConfigUtils
{
	/**
	 * 根据key获取对应的value
	 * @param key
	 * @return
	 */
	public static String getValue(String propotiFileName, String key)
	{
		String result = "";
		InputStream inputStream = ConfigUtils.class.getClassLoader().getResourceAsStream(propotiFileName);
		Properties p = new Properties();

		try {
			p.load(inputStream);
			result = p.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}


		return result;
	}

}