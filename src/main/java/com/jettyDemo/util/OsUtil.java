package com.jettyDemo.util;

import java.io.File;

import com.jettyDemo.alone.Main;
import com.jettyDemo.alone.WebContext;

public class OsUtil {
	// 目录分隔符
	public static final String FILE_SEPARATOR = System
			.getProperty("file.separator");
	// 换行符
	public static final String LINE_SEPARATOR = System.getProperty(
			"line.separator", "/n");
	// 资源根目录
	private static String CLASSPATH;
	// 默认数据存放目录
	private static String defaultDataPDir = Os.getName() == Os.WIN ? "C:\\"
			: "/tmp/";

	static {
		// 在ide/tomcat容器中
		// 中直接获取类路径下的资源目录;eg:E:/order/002_ext/WebRoot/WEB-INF/classes/
		if (Main.IS_RUNNING_IN_IDE) {
			CLASSPATH = OsUtil.class.getClassLoader().getResource("").getPath();
		} else {
			// 嵌入jetty独立jar包执行,获取jar包父目录
			CLASSPATH = WebContext.getJarDir();
			defaultDataPDir = CLASSPATH;
		}
	}

	/**
	 * 获取默认task目录下的相关目录
	 */
	public static String getDefaultTaskDir(String dir) {
		return CLASSPATH + "task" + FILE_SEPARATOR + dir;
	}

	public static String getScriptPath(String scriptBasePath, String scriptType) {
		String taskScriptsDir = scriptBasePath.endsWith(File.separator) ? scriptBasePath
				+ scriptType
				: scriptBasePath + File.separator + scriptType;
		return taskScriptsDir;
	}

	/**
	 * 获取默认数据存放目录
	 */
	public static String getDefaultDataPath() {
		return defaultDataPDir;
	}

	/**
	 * 根据平台执行较复杂命令(包含管道 重定向等Runtime.exec(String)函数支持不了的特性)
	 */
	public static String[] getCmdHandleByOs(String cmd) {
		if (Os.getName() == Os.LINUX) {
			return new String[] { "sh", "-c", cmd };
		} else {
			return new String[] { "cmd.exe", "/C", cmd };
		}
	}

	public static class Os {
		private static String name;// WIN or LINUX
		private static String file_suffix;
		public static final String WIN = "win";
		public static final String LINUX = "linux";
		public static boolean isLinux = false;

		static {
			if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
				file_suffix = ".bat";
				name = "win";
			} else {
				file_suffix = ".sh";
				name = "linux";
				isLinux = true;
			}
		}

		public static String getFile_suffix() {
			return file_suffix;
		}

		public static String getName() {
			return name;
		}

	}

}
