package com.jettyDemo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jettyDemo.dao.SysDao;
import com.jettyDemo.exception.ServiceException;

@Service("sysService")
@Scope("singleton")
public class SystemServiceImpl implements SystemService {

	@Resource
	private SysDao sysDao;

	@Override
	public String getVal(String name) throws ServiceException {
		try {
			List<String> vals = this.sysDao.findValByName(name);
			return vals.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("error:" + e.getMessage());
		}

	}

	@Override
	public String getDataDir() throws Exception {
		return this.getVal("data_path");
	}

}
