package com.jettyDemo.api;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jettyDemo.service.SystemService;
import com.jettyDemo.util.EnvironmentConfigUtils;

@Controller("api")
public class TestController {
	private static Logger logger = Logger.getLogger(TestController.class);

	@Autowired
	private SystemService sysService;

	@RequestMapping(method = RequestMethod.GET, value = "/my/test")
	public String test1(
			@RequestParam(value = "param", required = false) String param,
			Model model) {
		String returnPage = null;
		String template = "{\"status\":%d,\"msg\":\"%s\"}";
		try {
			// test read param from pom.xml
			Properties p = EnvironmentConfigUtils
					.getProperties("/applicatioin.properties");
			String name = p.getProperty("artifactId");
			logger.info("read param from pom.xml: artifactId = " + name);

			// test sqliteDB
			String data_path = sysService.getDataDir();
			logger.info("read data from sqlite: system.data_path = "
					+ data_path);

			model.addAttribute("res", String.format(template, 1, "ok"));
			returnPage = "common/json";
		} catch (Exception e) {
			model.addAttribute("res", String.format(template, 1, "ok"));
			returnPage = "common/error";
		}
		return returnPage;
	}
}