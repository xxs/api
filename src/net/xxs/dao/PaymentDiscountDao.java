package net.xxs.dao;

import java.util.List;

import net.xxs.entity.Brand;
import net.xxs.entity.PaymentConfig;
import net.xxs.entity.PaymentDiscount;

/**
 * Dao接口 - 支付通道
 */

public interface PaymentDiscountDao extends BaseDao<PaymentDiscount, String> {
	/**
	 * 获取非预存款类型的支付配置
	 * 
	 * @return 支付配置
	 */
	public PaymentDiscount getPaymentDiscountByPaymentConfigAndBrand(PaymentConfig paymentConfig , Brand brand);
	/**
	 * 获取非预存款类型的支付配置list
	 * 
	 * @return 支付配置list
	 */
	public List<PaymentDiscount> getPaymentDiscountByBrand(Brand brand);
}