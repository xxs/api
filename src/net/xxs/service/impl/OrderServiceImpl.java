package net.xxs.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import net.xxs.bean.Pager;
import net.xxs.dao.OrderDao;
import net.xxs.entity.Member;
import net.xxs.entity.Order;
import net.xxs.service.OrderService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 订单
 */

@Service("orderServiceImpl")
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {
	
	@Resource(name = "orderDaoImpl")
	private OrderDao orderDao;

	@Resource(name = "orderDaoImpl")
	public void setBaseDao(OrderDao orderDao) {
		super.setBaseDao(orderDao);
	}
	
	@Transactional(readOnly = true)
	public String getLastOrderSn() {
		return orderDao.getLastOrderSn();
	}
	
	@Transactional(readOnly = true)
	public Pager getOrderPager(Member member, Pager pager) {
		return orderDao.getOrderPager(member, pager);
	}
	
	@Transactional(readOnly = true)
	public Long getUnprocessedOrderCount() {
		return orderDao.getUnprocessedOrderCount();
	}

	public Pager getOrderPager(Date beginDate, Date endDate, Order order,
			Pager pager) {
		return orderDao.getOrderPager(beginDate, endDate, order, pager);
	}

	public Order getOrderByOrderSn(String orderSn) {
		return orderDao.getOrderByOrderSn(orderSn);
	}
	
}