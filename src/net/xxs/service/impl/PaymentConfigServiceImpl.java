package net.xxs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.xxs.dao.PaymentConfigDao;
import net.xxs.entity.PaymentConfig;
import net.xxs.service.PaymentConfigService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 支付方式配置
 */

@Service("paymentConfigServiceImpl")
public class PaymentConfigServiceImpl extends BaseServiceImpl<PaymentConfig, String> implements PaymentConfigService {
	
	@Resource(name = "paymentConfigDaoImpl")
	private PaymentConfigDao paymentConfigDao;
	
	@Resource(name = "paymentConfigDaoImpl")
	public void setBaseDao(PaymentConfigDao PaymentConfigDao) {
		super.setBaseDao(PaymentConfigDao);
	}
	
	@Cacheable(modelId = "paymentConfigCaching")
	@Transactional(readOnly = true)
	public List<PaymentConfig> getNonDepositPaymentConfigList() {
		return paymentConfigDao.getNonDepositPaymentConfigList();
	}
	
	@Cacheable(modelId = "paymentConfigCaching")
	@Transactional(readOnly = true)
	public List<PaymentConfig> getNonDepositOfflinePaymentConfigList() {
		return paymentConfigDao.getNonDepositOfflinePaymentConfigList();
	}
	
	@Override
	@Cacheable(modelId = "paymentConfigCaching")
	@Transactional(readOnly = true)
	public List<PaymentConfig> getAllList() {
		return paymentConfigDao.getAllList();
	}

	@Override
	@CacheFlush(modelId = "paymentConfigFlushing")
	public void delete(PaymentConfig paymentConfig) {
		paymentConfigDao.delete(paymentConfig);
	}

	@Override
	@CacheFlush(modelId = "paymentConfigFlushing")
	public void delete(String id) {
		paymentConfigDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "paymentConfigFlushing")
	public void delete(String[] ids) {
		paymentConfigDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "paymentConfigFlushing")
	public String save(PaymentConfig paymentConfig) {
		return paymentConfigDao.save(paymentConfig);
	}

	@Override
	@CacheFlush(modelId = "paymentConfigFlushing")
	public void update(PaymentConfig paymentConfig) {
		paymentConfigDao.update(paymentConfig);
	}

}