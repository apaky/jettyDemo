package com.jettyDemo.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class EnvironmentConfigUtils {
	static Logger logger = Logger.getLogger(EnvironmentConfigUtils.class);
	static EnvironmentConfigUtils ec;
	private static Map<String, Properties> register = new HashMap<String, Properties>();

	public static Map<String, Properties> getProperties() {
		return register;
	}

	@SuppressWarnings("resource")
	public static Properties getProperties(String fileName) {
		InputStream is = null;
		Properties p = null;
		try {
			p = register.get(fileName);
			if (p == null) {
				System.out.println("=====cache[" + fileName
						+ "] is null,Read from properties file!");
				try {
					is = new FileInputStream(fileName);
				} catch (Exception e) {
					try {
						if (fileName.startsWith("/")) {
							is = EnvironmentConfigUtils.class
									.getResourceAsStream(fileName);
						} else {
							is = EnvironmentConfigUtils.class
									.getResourceAsStream("/" + fileName);
						}
					} catch (Exception e2) {
						logger.error("InputStream erro:" + e2.getMessage());
						e.printStackTrace();
					}
				}
				p = new Properties();
				p.load(is);
				register.put(fileName, p);
				is.close();
			}
		} catch (Exception e) {
			logger.error("read properties error!", e);
			e.printStackTrace();
		}
		return p;
	}

	public static String getPropertyValue(String fileName, String strKey) {
		Properties p = getProperties(fileName);
		try {
			return p.getProperty(strKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 清空properties缓存
	 * 
	 * @param fileName
	 *            properties文件相对路径(eg: /constants.properties);若为null表示全部清空
	 */
	private static boolean clean(String fileName, String uuid) {
		try {
			if (StringUtils.isBlank(fileName)) {
				register.clear();
			} else {
				Properties p = register.get(fileName);
				if (p != null) {
					register.put(fileName, null);
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println(uuid
					+ "`===clean caches for properties error! key=" + fileName);

			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 清理properties缓存并重新加载
	 */
	public static boolean reloadProperties(String filePath, String uuid) {
		try {
			clean(filePath, uuid);
			getProperties(filePath);
			return true;
		} catch (Exception e) {
			System.out
					.println(uuid
							+ "`===reload caches for properties error! key="
							+ filePath);
			e.printStackTrace();
		}
		return false;
	}
}