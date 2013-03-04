package net.xxs.dao;

import java.util.List;

import net.xxs.entity.PaymentConfig;

/**
 * Dao接口 - 支付配置
 */

public interface PaymentConfigDao extends BaseDao<PaymentConfig, String> {

	/**
	 * 获取支付配置
	 * 
	 * @return 支付配置
	 */
	public List<PaymentConfig> getPaymentConfigList();
	

}