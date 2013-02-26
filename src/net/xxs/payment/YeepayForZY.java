package net.xxs.payment;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.xxs.bean.Setting.CurrencyType;
import net.xxs.entity.Order;
import net.xxs.entity.PaymentConfig;
import net.xxs.util.SettingUtil;

import org.apache.commons.lang.StringUtils;

import com.yeepay.DigestUtil;
import com.yeepay.HttpUtils;

/**
 * 易宝支付 专业版
 */

public class YeepayForZY extends BasePaymentProduct {

	public static final String PAYMENT_URL = "https://www.yeepay.com/app-merchant-proxy/command.action";// 正式支付请求URL
	public static final String RETURN_URL = "/card/payment!payreturn.action";// 回调处理URL
	public static final String SHOW_URL = "/";// 充值卡显示URL

	private static final String P0_CMD = "ChargeCardDirect"; // 请求命令名称

	// 支持货币种类
	public static final CurrencyType[] currencyType = { CurrencyType.CNY };

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
		String r3Amt = httpServletRequest.getParameter("p3_Amt");
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
	public Map<String, String> getParameterMap(PaymentConfig paymentConfig,
			String paymentSn, BigDecimal paymentAmount,
			HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public boolean verifySign(PaymentConfig paymentConfig,
			HttpServletRequest httpServletRequest) {
		// 业务类型
		String r0_Cmd = httpServletRequest.getParameter("r0_Cmd");
		// 支付结果
		String r1_Code = httpServletRequest.getParameter("r1_Code");
		// 商户编号
		String p1_MerId = httpServletRequest.getParameter("p1_MerId");
		// 商户订单号
		String p2_Order = httpServletRequest.getParameter("p2_Order");
		// 成功金额
		String p3_Amt = httpServletRequest.getParameter("p3_Amt");
		// 支付方式
		String p4_FrpId = httpServletRequest.getParameter("p4_FrpId");
		// 卡序列号组
		String p5_CardNo = httpServletRequest.getParameter("p5_CardNo");
		// 确认金额组
		String p6_confirmAmount = httpServletRequest
				.getParameter("p6_confirmAmount");
		// 实际金额组
		String p7_realAmount = httpServletRequest.getParameter("p7_realAmount");
		// 卡状态组
		String p8_cardStatus = httpServletRequest.getParameter("p8_cardStatus");
		// 扩展信息
		String p9_MP = httpServletRequest.getParameter("p9_MP");
		// 支付余额 注：此项仅为订单成功,并且需要订单较验时才会有值。失败订单的余额返部返回原卡密中
		String pb_BalanceAmt = httpServletRequest.getParameter("pb_BalanceAmt");
		// 余额卡号 注：此项仅为订单成功,并且需要订单较验时才会有值
		String pc_BalanceAct = httpServletRequest.getParameter("pc_BalanceAct");
		// 签名数据
		String hmac = httpServletRequest.getParameter("hmac");
		String keyValue = paymentConfig.getBargainorKey();// 密钥
		// 验证hmac
		String newHmac = DigestUtil.getHmac(new String[] { r0_Cmd, r1_Code,
				p1_MerId, p2_Order, p3_Amt, p4_FrpId, p5_CardNo,
				p6_confirmAmount, p7_realAmount, p8_cardStatus, p9_MP,
				pb_BalanceAmt, pc_BalanceAct }, keyValue);

		// 验证支付签名
		if (StringUtils.equals(hmac, newHmac)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getPayreturnMessage(String paymentSn) {
		return "SUCCESS";
	}

	@Override
	public String getPaynotifyMessage() {
		return null;
	}

	@Override
	public String getQueryUrl() {
		return null;
	}

	/**
	 * 消费请求 该方法是根据《易宝支付非银行卡支付专业版接口文档 v3.0》对发起支付请求进行的封装
	 * 具体参数含义请仔细阅读《易宝支付非银行卡支付专业版接口文档 v3.0》 商户订单号
	 * 
	 * @param p2_Order
	 *            订单金额
	 * @param p3_Amt
	 *            是否较验订单金额
	 * @param p4_verifyAmt
	 *            产品名称
	 * @param p5_Pid
	 *            产品类型
	 * @param p6_Pcat
	 *            产品描述
	 * @param p7_Pdesc
	 *            通知地址
	 * @param p8_Url
	 *            扩展信息
	 * @param pa_MP
	 *            卡面额组
	 * @param pa7_cardAmt
	 *            卡号组
	 * @param pa8_cardNo
	 *            支付方式
	 * @param pd_FrpId
	 *            通知是否需要应答
	 * @param pr_NeedResponse
	 *            用户ID
	 * @param pz_userId
	 *            用户注册时间
	 * @param pz1_userRegTime
	 * @return
	 */
	@Override
	public PaymentResult cardPay(PaymentConfig paymentConfig,Order order, HttpServletRequest httpServletRequest) {
		String p0_Cmd = P0_CMD; // 业务类型（非银行卡专业版支付请求固定值“ChargeCardDirect”）
		String p1_MerId = paymentConfig.getBargainorId(); // 商户编号
		String p2_Order = order.getOrderSn();// 商户订单号
		String p3_Amt = order.getAmount().toString();// 支付金额（单位：元）
		String p4_verifyAmt = "false";// 是否校验金额 （值：true校验金额; false不校验金额）
		String p5_Pid = "";// 充值卡名称(选填项)
		String p6_Pcat = "";// 充值卡种类(选填项)
		String p7_Pdesc = "";// 充值卡描述(选填项)
		String p8_Url = SettingUtil.getSetting().getCardUrl() + RETURN_URL + "?ordersn=" + order.getOrderSn();// 回调处理URL
		String pa_MP = "";// 扩展信息(选填项)
		String pa7_cardAmt = order.getAmount().toString();// 面额组合
		String pa8_cardNo = order.getCardNum();// 卡号组合
		String pa9_cardPwd = order.getCardPwd();// 秘钥组合
		String pd_FrpId = order.getCardCode().toUpperCase();// 通道编码
		String pr_NeedResponse = "1";// 应答机制
		String pz_userId = order.getMember().getId();// 会员ID（payment中的member可以查询到）
		String pz1_userRegTime = order.getMember().getCreateDate().toString();// 会员注册时间（payment中的member可以查询到）
		String keyValue = paymentConfig.getBargainorKey();// 密钥

		// 生成hmac，保证交易信息不被篡改,关于hmac详见《易宝支付非银行卡支付专业版接口文档 v3.0》
		String hmac = "";
		hmac = DigestUtil.getHmac(new String[] { p0_Cmd, p1_MerId, p2_Order,
				p3_Amt, p4_verifyAmt, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, pa_MP,
				pa7_cardAmt, pa8_cardNo, pa9_cardPwd, pd_FrpId,
				pr_NeedResponse, pz_userId, pz1_userRegTime }, keyValue);

		// 封装请求参数，参数说明详见《易宝支付非银行卡支付专业版接口文档 v3.0》
		Map reqParams = new HashMap();
		reqParams.put("p0_Cmd", p0_Cmd);
		reqParams.put("p1_MerId", p1_MerId);
		reqParams.put("p2_Order", p2_Order);
		reqParams.put("p3_Amt", p3_Amt);
		reqParams.put("p4_verifyAmt", p4_verifyAmt);
		reqParams.put("p5_Pid", p5_Pid);
		reqParams.put("p6_Pcat", p6_Pcat);
		reqParams.put("p7_Pdesc", p7_Pdesc);
		reqParams.put("p8_Url", p8_Url);
		reqParams.put("pa_MP", pa_MP);
		reqParams.put("pa7_cardAmt", pa7_cardAmt);
		reqParams.put("pa8_cardNo", pa8_cardNo);
		reqParams.put("pa9_cardPwd", pa9_cardPwd);
		reqParams.put("pd_FrpId", pd_FrpId);
		reqParams.put("pr_NeedResponse", pr_NeedResponse);
		reqParams.put("pz_userId", pz_userId);
		reqParams.put("pz1_userRegTime", pz1_userRegTime);
		reqParams.put("hmac", hmac);
		List responseStr = null;
		// 创建非银行卡专业版消费请求结果
		PaymentResult paymentResult = new PaymentResult();
		try {
			// 发起支付请求
			responseStr = HttpUtils.URLPost(PAYMENT_URL, reqParams);
		} catch (Exception e) {
			System.out.println("提交post失败");
			paymentResult.setReason("提交失败");
		}
		if (responseStr.size() == 0) {
			System.out.println("没有响应返回结果");
			paymentResult.setReason("没有响应返回结果");
		}
		
		// 解析易宝支付返回的消费请求结果,关于返回结果数据详见《易宝支付非银行卡支付专业版接口文档 v3.0》
		for (int t1 = 0; t1 < responseStr.size(); t1++) {
			String currentResult = (String) responseStr.get(t1);
			System.out.println("responseStr[" + t1 + "]:" + currentResult);
		}
		for (int t = 0; t < responseStr.size(); t++) {
			String currentResult = (String) responseStr.get(t);
			System.out.println("responseStr[" + t + "]:" + currentResult);
			if (currentResult == null || currentResult.equals("")) {
				continue;
			}
			int i = currentResult.indexOf("=");
			if (i >= 0) {
				String sKey = currentResult.substring(0, i);
				String sValue = currentResult.substring(i + 1);
				if (sKey.equals("r0_Cmd")) {
					paymentResult.setCmd(sValue);
				}else if (sKey.equals("r1_Code")) {
					paymentResult.setCode(sValue);
				} else if (sKey.equals("r6_Order")) {
					paymentResult.setOrderSn(sValue);
				} else if (sKey.equals("rq_ReturnMsg")) {
					paymentResult.setReturnMsg(sValue);
				} else if (sKey.equals("hmac")) {
					paymentResult.setHmac(sValue);
				} else {
					System.out.println("返回结果中含有非法参数");
					paymentResult.setReason("返回结果中含有非法参数");
				}
			} else {
				System.out.println("返回结果中含有非法参数");
				paymentResult.setReason("返回结果中含有非法参数");
			}
		}
		// 不成功情况
		if (!paymentResult.getCode().equals("1")) {
			System.out.println("errorCode:" + paymentResult.getCode() + ";errorMessage:"+ paymentResult.getReturnMsg());
			paymentResult.setIsSuccess(false);
			paymentResult.setReason("提交失败原因:"+ paymentResult.getReturnMsg());
		}else{
			paymentResult.setIsSuccess(true);
			paymentResult.setReason("提交成功");
		}
		String newHmac = "";
		newHmac = DigestUtil.getHmac(
				new String[] { paymentResult.getCmd(), paymentResult.getCode(),
						paymentResult.getOrderSn(), paymentResult.getReturnMsg() }, keyValue);
		// hmac不一致则抛出异常
		if (!newHmac.equals(paymentResult.getHmac())) {
			System.out.println("交易签名被篡改");
			paymentResult.setIsSuccess(false);
			paymentResult.setReason("交易签名被篡改");
		}
		return (paymentResult);
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