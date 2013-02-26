package net.xxs.dao.impl;

import java.util.List;

import net.xxs.dao.PaymentDiscountDao;
import net.xxs.entity.Brand;
import net.xxs.entity.PaymentConfig;
import net.xxs.entity.PaymentDiscount;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 支付通道设置
 */

@Repository("paymentDiscountDaoImpl")
public class PaymentDiscountDaoImpl extends BaseDaoImpl<PaymentDiscount, String> implements PaymentDiscountDao {

	public PaymentDiscount getPaymentDiscountByPaymentConfigAndBrand(
			PaymentConfig paymentConfig, Brand brand) {
		String hql = "from PaymentDiscount as paymentDiscount where paymentDiscount.paymentConfig.id = :paymentConfigid and paymentDiscount.brand.id = :brandid";
		return (PaymentDiscount) getSession().createQuery(hql).setParameter("paymentConfigid", paymentConfig.getId()).setParameter("brandid", brand.getId()).uniqueResult();
	}
	@SuppressWarnings("unchecked")
	public List<PaymentDiscount> getPaymentDiscountByBrand(Brand brand) {
		String hql = "from PaymentDiscount as paymentDiscount where paymentDiscount.brand.id = :brandid order by paymentDiscount.paymentConfig.orderList";
		return getSession().createQuery(hql).setParameter("brandid", brand.getId()).list();
	}

}