package net.xxs.service.impl;

import javax.annotation.Resource;

import net.xxs.dao.MemberLogDao;
import net.xxs.entity.BusinessLog;
import net.xxs.service.BusinessLogService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 日志
 */

@Service("memberLogServiceImpl")
public class BusinessLogServiceImpl extends BaseServiceImpl<BusinessLog, String> implements BusinessLogService {

	@Resource(name = "memberLogDaoImpl")
	public void setBaseDao(MemberLogDao memberLogDao) {
		super.setBaseDao(memberLogDao);
	}

}