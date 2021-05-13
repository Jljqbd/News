package com.org.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	private static Properties props;

	static {
		InputStream is = null;
		// System.out.println(ConfigManager.class.getResource(""));
		is = ConfigManager.class.getClassLoader().getResourceAsStream("database.properties");
		if (is == null) {
			throw new RuntimeException("找不到数据库配置文件");
		}
		props = new Properties();
		try {
			props.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("数据库加载错误");
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}

}
