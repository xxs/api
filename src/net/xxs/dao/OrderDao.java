package net.xxs.dao;

import java.util.Date;
import java.util.List;

import net.xxs.bean.Pager;
import net.xxs.entity.Brand;
import net.xxs.entity.Member;
import net.xxs.entity.Order;
import net.xxs.entity.PaymentConfig;
import net.xxs.entity.Order.OrderStatus;

/**
 * Dao接口 - 订单
 */

public interface OrderDao extends BaseDao<Order, String> {
	
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
	 *            会员
	 *            
	 * @param pager
	 *            Pager对象
	 *            
	 * @return 订单分页对象
	 */
	public Pager getOrderPager(Member member, Pager pager);
	
	public Pager getOrderPager(Date beginDate, Date endDate,Order order,Pager pager);
	/**
	 * 根据会员、订单状态获取订单
	 * 
	 * @param member
	 *            会员
	 *            
	 * @param orderStatus
	 *            订单状态
	 *            
	 * @return 已完成的订单
	 */
	public List<Order> getOrderList(Member member, OrderStatus orderStatus);
	
	/**
	 * 获取未处理订单数
	 *            
	 * @return 未处理订单数
	 */
	public Long getUnprocessedOrderCount();
	
}