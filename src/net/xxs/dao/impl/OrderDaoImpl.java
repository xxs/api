package net.xxs.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.xxs.bean.Pager;
import net.xxs.dao.OrderDao;
import net.xxs.entity.Member;
import net.xxs.entity.Order;
import net.xxs.entity.Order.OrderStatus;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 订单
 */

@Repository("orderDaoImpl")
public class OrderDaoImpl extends BaseDaoImpl<Order, String> implements
		OrderDao {

	@SuppressWarnings("unchecked")
	public String getLastOrderSn() {
		String hql = "from Order as order order by order.createDate desc";
		List<Order> orderList = getSession().createQuery(hql).setFirstResult(0)
				.setMaxResults(1).list();
		if (orderList != null && orderList.size() > 0) {
			return orderList.get(0).getOrderSn();
		} else {
			return null;
		}
	}

	public Pager getOrderPager(Member member, Pager pager) {
		return super.findPager(pager, Restrictions.eq("member", member));
	}

	@SuppressWarnings("unchecked")
	public List<Order> getOrderList(Member member, OrderStatus orderStatus) {
		String hql = "from Order as order where order.member = :member and order.orderStatus = :orderStatus";
		return getSession().createQuery(hql).setParameter("member", member)
				.setParameter("orderStatus", orderStatus).list();
	}

	public Long getUnprocessedOrderCount() {
		String hql = "select count(*) from Order as order where order.orderStatus = :orderStatus";
		return (Long) getSession().createQuery(hql)
				.setParameter("orderStatus", OrderStatus.paymenting)
				.uniqueResult();
	}

	// 保存对象时,自动更新充值卡ID集合
	@Override
	public String save(Order order) {
		return super.save(order);
	}

	// 更新对象时,自动更新充值卡ID集合
	@Override
	public void update(Order order) {
		super.update(order);
	}

	public Pager getOrderPager(Date beginDate, Date endDate,Order order,Pager pager) {
		List <Criterion> lists = new ArrayList<Criterion>();
		if(beginDate!=null){
			lists.add(Restrictions.gt("createDate", beginDate));
		}
		if(endDate!=null){
			lists.add(Restrictions.lt("createDate", endDate));
		}
		if(order.getOrderSn()!=null&&!order.getOrderSn().isEmpty()){
			lists.add(Restrictions.eq("orderSn", order.getOrderSn()));
		}
		if(order.getCardNum()!=null&&!order.getCardNum().isEmpty()){
			lists.add(Restrictions.eq("cardNum", order.getCardNum()));
		}
		if(order.getMember()!=null){
			lists.add(Restrictions.ge("member", order.getMember()));
		}
		if(order.getPaymentConfig()!=null&&!"".equals(order.getPaymentConfig().getId())){
			lists.add(Restrictions.ge("paymentConfig", order.getPaymentConfig()));
		}
		if(order.getOrderStatus()!=null){
			lists.add(Restrictions.ge("orderStatus", order.getOrderStatus()));
		}
		if(order.getBrandId()!=null&&!order.getBrandId().isEmpty()){
			lists.add(Restrictions.ge("brandId", order.getBrandId()));
		}
		Criterion [] c = (Criterion []) lists.toArray(new Criterion[lists.size()]);
		return super.findPager(pager,c);
	}

	public Order getOrderByOrderSn(String orderSn) {
		String hql = "from Order as order where lower(order.orderSn) = lower(:ordersn)";
		Order order = (Order)getSession().createQuery(hql).setParameter("ordersn", orderSn).uniqueResult();
		if (order != null) {
			return order;
		} else {
			return null;
		}
	}

}