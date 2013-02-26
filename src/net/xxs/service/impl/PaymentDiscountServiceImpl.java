package net.xxs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.xxs.dao.PaymentDiscountDao;
import net.xxs.entity.Brand;
import net.xxs.entity.PaymentConfig;
import net.xxs.entity.PaymentDiscount;
import net.xxs.service.PaymentDiscountService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 支付方式配置
 */

@Service("paymentDiscountServiceImpl")
public class PaymentDiscountServiceImpl extends BaseServiceImpl<PaymentDiscount, String> implements PaymentDiscountService {
	
	@Resource(name = "paymentDiscountDaoImpl")
	private PaymentDiscountDao paymentDiscountDao;

	@Resource(name = "paymentDiscountDaoImpl")
	public void setBaseDao(PaymentDiscountDao paymentDiscountDao) {
		super.setBaseDao(paymentDiscountDao);
	}
	@Transactional(readOnly = true)
	public PaymentDiscount getPaymentDiscountByPaymentConfigAndBrand(
			PaymentConfig paymentConfig, Brand brand) {
		return paymentDiscountDao.getPaymentDiscountByPaymentConfigAndBrand(paymentConfig, brand);
	}
	@Transactional(readOnly = true)
	public List<PaymentDiscount> getPaymentDiscountByBrand(Brand brand) {
		return paymentDiscountDao.getPaymentDiscountByBrand(brand);
	}

}