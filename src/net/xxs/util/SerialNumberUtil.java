package net.xxs.util;

import java.util.UUID;

import net.xxs.service.CardsService;
import net.xxs.service.MemberBusinessService;
import net.xxs.service.OrderService;
import net.xxs.service.ProductService;
import net.xxs.service.WithdrawService;

import org.apache.commons.lang.StringUtils;

/**
 * 工具类 - 编号生成
 */

public class SerialNumberUtil {
	
	public static final String CARDS_SN_PREFIX = "SN_";// 充值卡编号前缀
	public static final String PRODUCT_SN_PREFIX = "SN_";// 货品编号前缀
	
	public static final String ORDER_SN_PREFIX = "DD";// 订单编号前缀
	public static final long ORDER_SN_FIRST = 100000L;// 订单编号起始数
	public static final long ORDER_SN_STEP = 1L;// 订单编号步长
	
	public static final String WITHDRAW_SN_PREFIX = "TX";// 提现编号前缀
	public static final long WITHDRAW_SN_FIRST = 100000L;// 提现编号起始数
	public static final long WITHDRAW_SN_STEP = 1L;// 提现编号步长
	
	public static final String BUSINESS_Number_PREFIX = "SH";// 商户编号前缀
	
	public static Long lastOrderSnNumber;
	public static Long lastPaymentSnNumber;
	public static Long lastWithdrawSnNumber;

	static {
		// 订单编号
		OrderService orderService = (OrderService) SpringUtil.getBean("orderServiceImpl");
		String lastOrderSn = orderService.getLastOrderSn();
		if (StringUtils.isNotEmpty(lastOrderSn)) {
			lastOrderSnNumber = Long.parseLong(StringUtils.removeStartIgnoreCase(lastOrderSn, ORDER_SN_PREFIX));
		} else {
			lastOrderSnNumber = ORDER_SN_FIRST;
		}
		
		// 提现编号
		WithdrawService withdrawService = (WithdrawService) SpringUtil.getBean("withdrawServiceImpl");
		String lastWithdrawSn = withdrawService.getLastWithdrawSn();
		if (StringUtils.isNotEmpty(lastWithdrawSn)) {
			lastWithdrawSnNumber = Long.parseLong(StringUtils.removeStartIgnoreCase(lastWithdrawSn, WITHDRAW_SN_PREFIX));
		} else {
			lastWithdrawSnNumber = WITHDRAW_SN_FIRST;
		}
	}
	
	/**
	 * 生成充值卡编号
	 * 
	 * @return 充值卡编号
	 */
	public static String buildCardsSn() {
		CardsService cardsService = (CardsService) SpringUtil.getBean("cardsServiceImpl");
		String cardsSn;
		do {
			String uuid = UUID.randomUUID().toString();
			cardsSn = CARDS_SN_PREFIX + (uuid.substring(0, 8) + uuid.substring(9, 13)).toUpperCase();
		} while (cardsService.isExistByCardsSn(cardsSn));
		return cardsSn;
	}
	
	/**
	 * 生成货品编号
	 * 
	 * @return 充值卡编号
	 */
	public static String buildProductSn() {
		ProductService productService = (ProductService) SpringUtil.getBean("productServiceImpl");
		String productSn;
		do {
			String uuid = UUID.randomUUID().toString();
			productSn = PRODUCT_SN_PREFIX + (uuid.substring(0, 8) + uuid.substring(9, 13)).toUpperCase();
		} while (productService.isExistByProductSn(productSn));
		return productSn;
	}
	
	/**
	 * 生成订单编号
	 * 
	 * @return 订单编号
	 */
	public synchronized static String buildOrderSn() {
		lastOrderSnNumber += ORDER_SN_STEP;
		return ORDER_SN_PREFIX + lastOrderSnNumber;
	}
	
	/**
	 * 生成提现编号
	 * 
	 * @return 提现编号
	 */
	public synchronized static String buildWithdrawSn() {
		lastWithdrawSnNumber += WITHDRAW_SN_STEP;
		return WITHDRAW_SN_PREFIX + lastWithdrawSnNumber;
	}
	
	/**
	 * 生成商户编号
	 * 
	 * @return 商户编号
	 */
	public static String buildBusinessNumber() {
		MemberBusinessService memberBusinessService = (MemberBusinessService) SpringUtil.getBean("memberBusinessServiceImpl");
		String businessNumber;
		do {
			String uuid = UUID.randomUUID().toString();
			businessNumber = BUSINESS_Number_PREFIX + (uuid.substring(0, 8) + uuid.substring(9, 13)).toUpperCase();
		} while (memberBusinessService.isExistByBusinessNumber(businessNumber));
		return businessNumber;
	}
	
	/**
	 * 生成商户Key
	 * 
	 * @return 商户Key
	 */
	public synchronized static String buildBusinessKey() {
		return UUID.randomUUID().toString().replace("-","");
	}
}