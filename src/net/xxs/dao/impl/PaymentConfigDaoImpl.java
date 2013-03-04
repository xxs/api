package net.xxs.dao.impl;

import java.util.List;
import java.util.Set;

import net.xxs.dao.PaymentConfigDao;
import net.xxs.entity.Order;
import net.xxs.entity.PaymentConfig;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 支付配置
 */

@Repository("paymentConfigDaoImpl")
public class PaymentConfigDaoImpl extends BaseDaoImpl<PaymentConfig, String> implements PaymentConfigDao {

	@SuppressWarnings("unchecked")
	public List<PaymentConfig> getPaymentConfigList() {
		String hql = "from PaymentConfig as paymentConfig order by paymentConfig.orderList asc";
		return getSession().createQuery(hql).list();
	}
	
	// 关联处理
	@Override
	public void delete(PaymentConfig paymentConfig) {
		Set<Order> orderSet = paymentConfig.getOrderSet();
		if (orderSet != null) {
			for (Order order : orderSet) {
				order.setPaymentConfig(null);
			}
		}
		
		super.delete(paymentConfig);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		PaymentConfig paymentConfig = load(id);
		this.delete(paymentConfig);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			PaymentConfig paymentConfig = load(id);
			this.delete(paymentConfig);
		}
	}

}