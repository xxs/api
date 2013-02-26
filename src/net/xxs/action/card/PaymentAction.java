package net.xxs.action.card;

import java.math.BigDecimal;

import javax.annotation.Resource;

import net.xxs.entity.Brand;
import net.xxs.entity.Deposit;
import net.xxs.entity.Deposit.DepositType;
import net.xxs.entity.Member;
import net.xxs.entity.Order;
import net.xxs.entity.Order.OrderStatus;
import net.xxs.entity.OrderLog;
import net.xxs.entity.OrderLog.OrderLogType;
import net.xxs.entity.PaymentDiscount;
import net.xxs.payment.BasePaymentProduct;
import net.xxs.service.BrandService;
import net.xxs.service.DepositService;
import net.xxs.service.MemberService;
import net.xxs.service.OrderLogService;
import net.xxs.service.OrderService;
import net.xxs.service.PaymentDiscountService;
import net.xxs.util.PaymentProductUtil;
import net.xxs.util.SettingUtil;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 支付处理
 */

@ParentPackage("card")
@InterceptorRefs({
		@InterceptorRef(value = "memberVerifyInterceptor", params = {
				"excludeMethods", "payreturn,paynotify,result" }),
		@InterceptorRef(value = "cardStack") })
public class PaymentAction extends BaseCardAction {

	private static final long serialVersionUID = -4817743897444468581L;

	private BigDecimal rechargeAmount;// 充值金额
	private String paymentUrl;// 支付请求URL
	private String ordersn;// 支付编号
	private String payreturnMessage;// 支付返回信息
	private String paynotifyMessage;// 支付通知信息

	private Order order;// 订单

	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "orderLogServiceImpl")
	private OrderLogService orderLogService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "paymentDiscountServiceImpl")
	private PaymentDiscountService paymentDiscountService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	
	// 支付回调处理
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "paymentsn", message = "支付编号不允许为空!") })
	@InputConfig(resultName = "error")
	public String payreturn() {
		System.out.println("支付回调处理......");
		order = orderService.getOrderByOrderSn(ordersn);
		if (order == null) {
			addActionError("订单不存在!");
			return ERROR;
		}
		BasePaymentProduct paymentProduct = PaymentProductUtil.getPaymentProduct(order.getPaymentConfig().getPaymentProductId());
		if (paymentProduct == null) {
			addActionError("支付产品不存在!");
			return ERROR;
		}

		BigDecimal totalAmount = paymentProduct.getPaymentAmount(getRequest());
		boolean isSuccess = paymentProduct.isPaySuccess(getRequest());
		payreturnMessage = paymentProduct.getPayreturnMessage(null);//获取回送关键字

		if (!paymentProduct.verifySign(order.getPaymentConfig(), getRequest())) {
			addActionError("支付签名错误!");
			return ERROR;
		}
		if (!isSuccess) {
			addActionError("支付失败!");
			return ERROR;
		}

		if (order.getOrderStatus() == OrderStatus.paid) {
			return "result";
		}
		
		if (order.getOrderStatus() == OrderStatus.invalid) {
			addActionError("交易状态错误!");
			return ERROR;
		}
		if (totalAmount.compareTo(order.getAmount()) != 0) {
			addActionError("交易金额错误!");
			return ERROR;
		}

		order.setPaySn(paymentProduct.getPaySn(getRequest()));
		order.setPaidAmount(order.getAmount().add(totalAmount));
		order.setOrderStatus(OrderStatus.paid);
		orderService.update(order);

		// ---------支付成功后为用户添加上预存款，并计算提现率
		System.out.println("支付金额为totalAmount：" + totalAmount);
		Member member = order.getMember();
		System.out.println("提现人为：" + member.getUsername());
		System.out.println("用户提现前的预存款：" + member.getDeposit());
		Brand brand = brandService.get(order.getBrandId());
		PaymentDiscount paymentDiscount = paymentDiscountService.getPaymentDiscountByPaymentConfigAndBrand(order.getPaymentConfig(), brand);
		System.out.println("订单成功金额为：" + totalAmount);
		BigDecimal tempAmount = new BigDecimal(0);
		if (null == paymentDiscount) {
			System.out.println("没有找到相应的通道折扣率配置");
			addActionError("没有找到通道折扣率设置!");
			return ERROR;
		} else {
			tempAmount = totalAmount.multiply(paymentDiscount.getDiscount());
		}
		System.out.println("计算折扣率后的应为会员充值的金额为：" + tempAmount);
		member.setDeposit(member.getDeposit().add(tempAmount));
		System.out.println("用户提现后的预存款：" + member.getDeposit());
		memberService.update(member);

		Deposit deposit = new Deposit();
		deposit.setDepositType(DepositType.memberRecharge);
		deposit.setCredit(totalAmount);
		deposit.setDebit(new BigDecimal(0));
		deposit.setBalance(member.getDeposit());
		deposit.setLossrate(paymentDiscount.getDiscount());
		deposit.setMember(member);
		deposit.setOrder(order);
		depositService.save(deposit);
		// 为推荐人加提成
		if (!"".equals(member.getReferrer()) && null != member.getReferrer()) {
			Member referrerMember = memberService.getMemberByUsername(member.getReferrer());
			System.out.println("推荐人为：" + referrerMember.getUsername());
			System.out.println("推荐人提成前的预存款为：" + referrerMember.getDeposit());
			System.out.println("推荐人提成率为："+ referrerMember.getMemberRank().getBenefits());
			System.out.println("待提成的金额为：" + tempAmount);
			System.out.println("提成的金额为："+ tempAmount.multiply(BigDecimal.valueOf(referrerMember.getMemberRank().getBenefits())));
			referrerMember.setDeposit(referrerMember.getDeposit().add(tempAmount.multiply(BigDecimal.valueOf(referrerMember.getMemberRank().getBenefits()))));
			System.out.println("推荐人提成后的预存款为：" + referrerMember.getDeposit());
			memberService.update(referrerMember);
			// 记录提成增加的预存款
			Deposit benefits = new Deposit();
			benefits.setDepositType(DepositType.benefits);
			benefits.setCredit(tempAmount.multiply(BigDecimal.valueOf(referrerMember.getMemberRank().getBenefits())));
			benefits.setDebit(new BigDecimal(0));
			benefits.setBalance(referrerMember.getDeposit());
			deposit.setLossrate(new BigDecimal(0));
			benefits.setReferrer(member.getUsername());
			benefits.setOrderSn(order.getOrderSn());
			benefits.setMember(referrerMember);
			benefits.setOrder(order);
			depositService.save(benefits);
		}

		// 订单日志
		String logInfo = "支付总金额: " + SettingUtil.currencyFormat(order.getAmount());
		
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.payment);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(null);
		orderLog.setInfo(logInfo);
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		return "payreturn";
	}

	// 支付结果
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "paymentsn", message = "支付编号不允许为空!") })
	@InputConfig(resultName = "error")
	public String result() {
		System.out.println("支付结果......");
		order = orderService.getOrderByOrderSn(ordersn);
		if (order == null|| order.getOrderStatus() !=OrderStatus.paid) {
			addActionError("参数错误!");
			return ERROR;
		}
		return "result";
	}

	// 支付通知处理
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "paymentsn", message = "支付编号不允许为空!") })
	@InputConfig(resultName = "error")
	public String paynotify() {
		return "paynotify";
	}


}