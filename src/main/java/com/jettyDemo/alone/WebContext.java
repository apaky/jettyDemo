package com.jettyDemo.alone;

import java.util.Properties;

import com.jettyDemo.util.EnvironmentConfigUtils;
import com.jettyDemo.util.OsUtil;
import com.jettyDemo.util.OsUtil.Os;

public class WebContext {

	private static String jarDir;// jar包父目录

	private static String JAR_NAME = null;

	/**
	 * 获取jar包父目录的绝对路径
	 */
	public static String getJarDir() {
		try {
			jarDir = WebServer.getShadedWarUrl();
			if (Os.isLinux) {
				// jar:file:/home/fj/git-workspace/qa/jettyDemo/target/jettyDemo-1.0-SNAPSHOT.jar!/META-INF/webapp/
				jarDir = jarDir.replace("jar:file:", "");
			} else {
				// jar:file:/D:/mytest/jettyDemo/jettyDemo-1.0-SNAPSHOT.jar!/META-INF/webapp/
				jarDir = jarDir.replace("jar:file:/", "");
				jarDir = jarDir.replace("/", OsUtil.FILE_SEPARATOR);
			}

			// "jettyDemo-1.0-SNAPSHOT.jar"
			if (JAR_NAME == null) {
				Properties p = EnvironmentConfigUtils
						.getProperties("/applicatioin.properties");
				String artifactId = p.getProperty("artifactId");
				String version = p.getProperty("version");
				String packaging = p.getProperty("packaging");
				JAR_NAME = artifactId + "-" + version + "." + packaging;
			}

			System.out.println("========================= JAR_NAME= "
					+ JAR_NAME);

			jarDir = jarDir.substring(0, jarDir.indexOf(JAR_NAME));
			return jarDir;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void setJarDir(String jarDir) {
		WebContext.jarDir = jarDir;
	}

}
