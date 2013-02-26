package net.xxs.service;

import java.util.Date;

import net.xxs.bean.Pager;
import net.xxs.entity.Member;
import net.xxs.entity.Order;

/**
 * Service接口 - 订单
 */

public interface OrderService extends BaseService<Order, String> {

	/**
	 * 获取最后生成的订单编号
	 * 
	 * @return 订单编号
	 */
	public String getLastOrderSn();
	/**
	 * 根据订单号获取订单
	 * 
	 * @return 订单
	 */
	public Order getOrderByOrderSn(String orderSn);
	
	/**
	 * 根据Member、Pager获取订单分页对象
	 * 
	 * @param member
	 *            Member对象
	 *            
	 * @param pager
	 *            Pager对象
	 *            
	 * @return 订单分页对象
	 */
	public Pager getOrderPager(Member member, Pager pager);
	
	public Pager getOrderPager(Date beginDate, Date endDate,Order order,Pager pager);
	
	/**
	 * 获取未处理订单数
	 *            
	 * @return 未处理订单数
	 */
	public Long getUnprocessedOrderCount();
	
}