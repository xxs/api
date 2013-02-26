package net.xxs.dao.impl;

import net.xxs.dao.OrderLogDao;
import net.xxs.entity.OrderLog;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 订单日志
 */

@Repository("orderLogDaoImpl")
public class OrderLogDaoImpl extends BaseDaoImpl<OrderLog, String> implements OrderLogDao {
	
}