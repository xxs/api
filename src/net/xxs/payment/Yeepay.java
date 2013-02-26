package net.xxs.payment;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.xxs.bean.Setting.CurrencyType;
import net.xxs.entity.Order;
import net.xxs.entity.PaymentConfig;
import net.xxs.util.SettingUtil;

import org.apache.commons.lang.StringUtils;

/**
 * 易宝支付
 */

public class Yeepay extends BasePaymentProduct {
	
	//public static final String PAYMENT_URL = "https://www.yeepay.com/app-merchant-proxy/node";// 支付请求URL
	public static final String PAYMENT_URL = "http://tech.yeepay.com:8080/robot/debug.action";// 支付请求URL
	public static final String RETURN_URL = ":8080/card/payment!payreturn.action";// 回调处理URL
	public static final String NOTIFY_URL = ":8080/card/payment!paynotify.action";// 消息通知URL
	public static final String SHOW_URL = "/";// 充值卡显示URL
	
	// 支持货币种类
	public static final CurrencyType[] currencyType = {CurrencyType.CNY};
	
	@Override
	public String getPaymentUrl() {
		return PAYMENT_URL;
	}
	
	@Override
	public String getOrderSn(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return null;
		}
		String r6Order = httpServletRequest.getParameter("r6_Order");
		if (StringUtils.isEmpty(r6Order)) {
			return null;
		}
		return r6Order;
	}
	
	@Override
	public BigDecimal getPaymentAmount(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return null;
		}
		String r3Amt = httpServletRequest.getParameter("r3_Amt");
		if (StringUtils.isEmpty(r3Amt)) {
			return null;
		}
		return new BigDecimal(r3Amt);
	}
	
	public boolean isPaySuccess(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return false;
		}
		String r1Code = httpServletRequest.getParameter("r1_Code");
		if (StringUtils.equals(r1Code, "1")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Map<String, String> getParameterMap(PaymentConfig paymentConfig, String paymentSn, BigDecimal paymentAmount, HttpServletRequest httpServletRequest) {
		String p0_Cmd = "Buy";// 业务类型
		String p1_MerId = paymentConfig.getBargainorId();// 商户编号
		String p2_Order = paymentSn;// 支付编号
		String p3_Amt = paymentAmount.toString();// 总金额（单位：元）
		String p4_Cur = "CNY";// 支付币种（CNY：人民币）
		String p5_Pid = paymentSn;// 充值卡名称
		String p6_Pcat = "";// 充值卡种类
		String p7_Pdesc = paymentSn;// 充值卡描述
		String p8_Url = SettingUtil.getSetting().getCardUrl() + RETURN_URL + "?paymentsn=" + paymentSn;// 回调处理URL
		String p9_SAF = "0";// 是否需要填写送货地址（1：是、0：否）
		String pa_MP = "card" + "xx";// 商户数据
		String pd_FrpId = "";// 支付通道编码
		String pr_NeedResponse = "1";// 是否需要应答机制（1：是、0：否）
		String key = paymentConfig.getBargainorKey();// 密钥
		
		// 生成签名
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(p0_Cmd);
		stringBuffer.append(p1_MerId);
		stringBuffer.append(p2_Order);
		stringBuffer.append(p3_Amt);
		stringBuffer.append(p4_Cur);
		stringBuffer.append(p5_Pid);
		stringBuffer.append(p6_Pcat);
		stringBuffer.append(p7_Pdesc);
		stringBuffer.append(p8_Url);
		stringBuffer.append(p9_SAF);
		stringBuffer.append(pa_MP);
		stringBuffer.append(pd_FrpId);
		stringBuffer.append(pr_NeedResponse);
		String hmac = hmacSign(stringBuffer.toString(), key);
		
		// 参数处理
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("p0_Cmd", p0_Cmd);
		parameterMap.put("p1_MerId", p1_MerId);
		parameterMap.put("p2_Order", p2_Order);
		parameterMap.put("p3_Amt", p3_Amt);
		parameterMap.put("p4_Cur", p4_Cur);
		parameterMap.put("p5_Pid", p5_Pid);
		parameterMap.put("p6_Pcat", p6_Pcat);
		parameterMap.put("p7_Pdesc", p7_Pdesc);
		parameterMap.put("p8_Url", p8_Url);
		parameterMap.put("p9_SAF", p9_SAF);
		parameterMap.put("pa_MP", pa_MP);
		parameterMap.put("pd_FrpId", pd_FrpId);
		parameterMap.put("pr_NeedResponse", pr_NeedResponse);
		parameterMap.put("hmac", hmac);
		
		return parameterMap;
	}

	@Override
	public boolean verifySign(PaymentConfig paymentConfig, HttpServletRequest httpServletRequest) {
		// 获取参数
		String p1_MerId = httpServletRequest.getParameter("p1_MerId");
		String r0_Cmd = httpServletRequest.getParameter("r0_Cmd");
		String r1_Code = httpServletRequest.getParameter("r1_Code");
		String r2_TrxId = httpServletRequest.getParameter("r2_TrxId");
		String r3_Amt = httpServletRequest.getParameter("r3_Amt");
		String r4_Cur = httpServletRequest.getParameter("r4_Cur");
		String r5_Pid = httpServletRequest.getParameter("r5_Pid");
		String r6_Order = httpServletRequest.getParameter("r6_Order");
		String r7_Uid = httpServletRequest.getParameter("r7_Uid");
		String r8_MP = httpServletRequest.getParameter("r8_MP");
		String r9_BType = httpServletRequest.getParameter("r9_BType");
		String hmac = httpServletRequest.getParameter("hmac");
		
		// 验证支付签名
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(p1_MerId);
		stringBuffer.append(r0_Cmd);
		stringBuffer.append(r1_Code);
		stringBuffer.append(r2_TrxId);
		stringBuffer.append(r3_Amt);
		stringBuffer.append(r4_Cur);
		stringBuffer.append(r5_Pid);
		stringBuffer.append(r6_Order);
		stringBuffer.append(r7_Uid);
		stringBuffer.append(r8_MP);
		stringBuffer.append(r9_BType);
		if (StringUtils.equals(hmac, hmacSign(stringBuffer.toString(), paymentConfig.getBargainorKey()))) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String getPayreturnMessage(String paymentSn) {
		return "SUCCESS<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" /><title>页面跳转中..</title></head><body onload=\"javascript: document.forms[0].submit();\"><form action=\"" + SettingUtil.getSetting().getCardUrl() + RESULT_URL + "\"><input type=\"hidden\" name=\"paymentsn\" value=\"" + paymentSn + "\" /></form></body></html>";
	}
	
	// HMAC加密算法
	private String hmacSign(String value, String key) {
		String encodingCharset = "UTF-8";
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keys[];
		byte values[];
		try {
			keys = key.getBytes(encodingCharset);
			values = value.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			keys = key.getBytes();
			values = value.getBytes();
		}

		Arrays.fill(k_ipad, keys.length, 64, (byte) 54);
		Arrays.fill(k_opad, keys.length, 64, (byte) 92);
		for (int i = 0; i < keys.length; i++) {
			k_ipad[i] = (byte) (keys[i] ^ 0x36);
			k_opad[i] = (byte) (keys[i] ^ 0x5c);
		}

		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		messageDigest.update(k_ipad);
		messageDigest.update(values);
		byte digest[] = messageDigest.digest();
		messageDigest.reset();
		messageDigest.update(k_opad);
		messageDigest.update(digest, 0, 16);
		digest = messageDigest.digest();
		if (digest == null) {
			return null;
		}
		StringBuffer stringBuffer = new StringBuffer(digest.length * 2);
		for (int i = 0; i < digest.length; i++) {
			int current = digest[i] & 0xff;
			if (current < 16)
				stringBuffer.append("0");
			stringBuffer.append(Integer.toString(current, 16));
		}
		return stringBuffer.toString();
	}
	
	@Override
	public String getPaynotifyMessage() {
		return null;
	}

	@Override
	public String getQueryUrl() {
		return null;
	}

	@Override
	public PaymentResult cardPay(PaymentConfig paymentConfig,Order order, HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public PaymentResult cardQuery(PaymentConfig paymentConfig,
			String paymentSn, HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public String getPaySn(HttpServletRequest httpServletRequest) {
		return null;
	}

}