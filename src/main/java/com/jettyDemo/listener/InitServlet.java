package com.jettyDemo.listener;

import java.util.Properties;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.jettyDemo.dao.SysDao;
import com.jettyDemo.util.EnvironmentConfigUtils;

public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1587553201925741431L;
	private static final Logger logger = Logger.getLogger(InitServlet.class);
	private static String uuid = UUID.randomUUID().toString();

	/**
	 * 初始化servlet
	 */
	@Override
	public void init() throws ServletException {
		try {
			logger.info(uuid + "`============初始化 START============");
			testCreateDBTables();
			testReadFromPOM();
			logger.info(uuid + "`============初始化 END============");
		} catch (Exception e) {
			logger.error(uuid + "`============初始化 ERROR============>"
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	public static void testReadFromPOM() {
		// java代码读取pom.xml的参数
		Properties p = EnvironmentConfigUtils
				.getProperties("/applicatioin.properties");
		String name = p.getProperty("name");
		String version = p.getProperty("version");
		String artifactId = p.getProperty("artifactId");
		String packaging = p.getProperty("packaging");
		logger.info(String
				.format("read project params from pom.xml: name = %s ; version = %s ; artifactId = %s ; packaging = %s",
						name, version, artifactId, packaging));
	}

	private static void testCreateDBTables() throws Exception {

		// 创建系统相关数据表system,并根据系统填入默认路径
		SysDao sysDao = (SysDao) SpringUtil.getBean("sysDao");
		if (sysDao.sysTableCount() == 0) {

			sysDao.createSysTable();
			sysDao.insert("data_path", "/this/is/a/test/data");

			logger.info(uuid + "`数据表system创建完成!");
		} else {
			logger.info(uuid + "`数据表system已存在,无需创建");
		}
	}
}
