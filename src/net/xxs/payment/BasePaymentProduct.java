package net.xxs.payment;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.xxs.bean.Setting.CurrencyType;
import net.xxs.entity.Order;
import net.xxs.entity.PaymentConfig;

import org.apache.commons.lang.StringUtils;

/**
 * 基类 - 支付产品
 */

public abstract class BasePaymentProduct {
	
	protected static final String RESULT_URL = "/card/payment!result.action";// 支付结果显示URL
	
	protected String id;// 支付产品标识
	protected String name;// 支付产品名称
	protected String description;// 支付产品描述
	protected String bargainorIdName;// 商户ID参数名称
	protected String bargainorKeyName;// 密钥参数名称
	protected CurrencyType[] currencyTypes;// 支持货币类型
	protected String logoPath;// 支付产品LOGO路径
	
	/**
	 * 获取支付请求URL
	 * 
	 * @return 支付请求URL
	 */
	public abstract String getPaymentUrl();
	
	/**
	 * 获取查询请求URL
	 * 
	 * @return 查询URL
	 */
	public abstract String getQueryUrl();
	
	/**
	 * 获取支付编号
	 * 
	 * @param httpServletRequest
	 *            httpServletRequest
	 * 
	 * @return 支付编号
	 */
	public abstract String getOrderSn(HttpServletRequest httpServletRequest);
	
	/**
	 * 获取支付金额（单位：元）
	 * 
	 * @param httpServletRequest
	 *            httpServletRequest
	 * 
	 * @return 支付金额
	 */
	public abstract BigDecimal getPaymentAmount(HttpServletRequest httpServletRequest);
	/**
	 * 获取支付流水号
	 * 
	 * @param httpServletRequest
	 *            httpServletRequest
	 * 
	 * @return 支付流水号
	 */
	public abstract String getPaySn(HttpServletRequest httpServletRequest);
	
	/**
	 * 判断是否支付成功
	 * 
	 * @param httpServletRequest
	 *            httpServletRequest
	 * 
	 * @return 是否支付成功
	 */
	public abstract boolean isPaySuccess(HttpServletRequest httpServletRequest);
	
	/**
	 * 获取参数
	 * 
	 * @param paymentSn
	 *            支付编号
	 *            
	 * @param paymentAmount
	 *            支付金额
	 * 
	 * @param httpServletRequest
	 *            httpServletRequest
	 * 
	 * @return 在线支付参数
	 */
	public abstract Map<String, String> getParameterMap(PaymentConfig paymentConfig, String paymentSn, BigDecimal paymentAmount, HttpServletRequest httpServletRequest);
	
	/**
	 *  点卡信息支付
	 * 
	 * @param paymentSn
	 *            支付编号
	 *            
	 * @param paymentAmount
	 *            支付金额
	 * 
	 * @param httpServletRequest
	 *            httpServletRequest
	 * 
	 * @return 结果PayMentResult
	 */
	public abstract PaymentResult cardPay(PaymentConfig paymentConfig, Order order, HttpServletRequest httpServletRequest);
	
	/**
	 *  点卡支付结果查询
	 * 
	 * @param paymentSn
	 *            支付编号
	 *            
	 * @param paymentAmount
	 *            支付金额
	 * 
	 * @param httpServletRequest
	 *            httpServletRequest
	 * 
	 * @return 结果PayMentResult
	 */
	public abstract PaymentResult cardQuery(PaymentConfig paymentConfig, String orderSn, HttpServletRequest httpServletRequest);

	/**
	 * 验证签名
	 * 
	 * @param httpServletRequest
	 *            httpServletRequest
	 * 
	 * @return 是否验证通过
	 */
	public abstract boolean verifySign(PaymentConfig paymentConfig, HttpServletRequest httpServletRequest);
	
	/**
	 * 根据支付编号获取支付返回信息
	 * 
	 * @param paymentSn
	 *            支付编号
	 * 
	 * @return 支付返回信息
	 */
	public abstract String getPayreturnMessage(String paymentSn);
	
	/**
	 * 获取支付通知信息
	 * 
	 * @return 支付通知信息
	 */
	public abstract String getPaynotifyMessage();
	
	/**
	 * 根据参数集合组合参数字符串（忽略空值参数）
	 * 
	 * @param httpServletRequest
	 *            httpServletRequest
	 * 
	 * @return 参数字符串
	 */
	protected String getParameterString(Map<String, String> parameterMap) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String key : parameterMap.keySet()) {
			String value = parameterMap.get(key);
			if (StringUtils.isNotEmpty(value)) {
				stringBuffer.append("&" + key + "=" + value);
			}
		}
		stringBuffer.deleteCharAt(0);
		return stringBuffer.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBargainorIdName() {
		return bargainorIdName;
	}

	public void setBargainorIdName(String bargainorIdName) {
		this.bargainorIdName = bargainorIdName;
	}

	public String getBargainorKeyName() {
		return bargainorKeyName;
	}

	public void setBargainorKeyName(String bargainorKeyName) {
		this.bargainorKeyName = bargainorKeyName;
	}

	public CurrencyType[] getCurrencyTypes() {
		return currencyTypes;
	}

	public void setCurrencyTypes(CurrencyType[] currencyTypes) {
		this.currencyTypes = currencyTypes;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

}