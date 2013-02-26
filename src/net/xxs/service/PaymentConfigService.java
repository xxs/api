package net.xxs.service;

import java.util.List;

import net.xxs.entity.PaymentConfig;

/**
 * Service接口 - 支付配置
 */

public interface PaymentConfigService extends BaseService<PaymentConfig, String> {
	
	/**
	 * 获取非预存款类型的支付配置
	 * 
	 * @return 支付配置
	 */
	public List<PaymentConfig> getNonDepositPaymentConfigList();
	
	/**
	 * 获取非预存款、线下支付方式的支付配置
	 * 
	 * @return 支付配置
	 */
	public List<PaymentConfig> getNonDepositOfflinePaymentConfigList();
	
}