package net.xxs.dao.impl;

import java.util.List;
import java.util.Set;

import net.xxs.dao.PaymentConfigDao;
import net.xxs.entity.Order;
import net.xxs.entity.PaymentConfig;
import net.xxs.entity.PaymentConfig.PaymentConfigType;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 支付配置
 */

@Repository("paymentConfigDaoImpl")
public class PaymentConfigDaoImpl extends BaseDaoImpl<PaymentConfig, String> implements PaymentConfigDao {

	@SuppressWarnings("unchecked")
	public List<PaymentConfig> getNonDepositPaymentConfigList() {
		String hql = "from PaymentConfig as paymentConfig where paymentConfig.paymentConfigType != :paymentConfigType order by paymentConfig.orderList asc";
		return getSession().createQuery(hql).setParameter("paymentConfigType", PaymentConfigType.deposit).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PaymentConfig> getNonDepositOfflinePaymentConfigList() {
		String hql = "from PaymentConfig as paymentConfig where paymentConfig.paymentConfigType != :paymentConfigTypeDeposit and paymentConfig.paymentConfigType != :paymentConfigTypeOffline order by paymentConfig.orderList asc";
		return getSession().createQuery(hql).setParameter("paymentConfigTypeDeposit", PaymentConfigType.deposit).setParameter("paymentConfigTypeOffline", PaymentConfigType.offline).list();
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