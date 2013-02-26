package net.xxs.service.impl;

import javax.annotation.Resource;

import net.xxs.dao.OrderLogDao;
import net.xxs.entity.OrderLog;
import net.xxs.service.OrderLogService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 订单日志
 */

@Service("orderLogServiceImpl")
public class OrderLogServiceImpl extends BaseServiceImpl<OrderLog, String> implements OrderLogService {

	@Resource(name = "orderLogDaoImpl")
	public void setBaseDao(OrderLogDao orderLogDao) {
		super.setBaseDao(orderLogDao);
	}

}