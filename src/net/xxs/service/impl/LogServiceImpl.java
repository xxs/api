package net.xxs.service.impl;

import javax.annotation.Resource;

import net.xxs.dao.LogDao;
import net.xxs.entity.Log;
import net.xxs.service.LogService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 日志
 */

@Service("logServiceImpl")
public class LogServiceImpl extends BaseServiceImpl<Log, String> implements LogService {

	@Resource(name = "logDaoImpl")
	public void setBaseDao(LogDao logDao) {
		super.setBaseDao(logDao);
	}

}