package net.xxs.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import net.xxs.util.SerialNumberUtil;

import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 订单
 */

@Entity
public class Order extends BaseEntity {

	private static final long serialVersionUID = -8541323033439515148L;

	// 订单状态（ 未支付、支付中、已支付、支付失败、已作废）
	public enum OrderStatus {
		unpaid, paymenting, paid,failure,invalid
	};

	private String orderSn;// 订单编号
	private String brandId;// 品牌编号（为获取通道及费率单独添加）
	private OrderStatus orderStatus;// 订单状态
	private String paymentConfigName;// 支付方式名称
	private BigDecimal amount;// 应付金额
	private BigDecimal paidAmount;// 实付金额
	private String memo;// 附言
	private String paySn;//第三方支付生成的订单号
	private String retCode;//记录支付过程中的状态码
	private String retMsg;//状态码对应的消息
	
	private String productSn;// 充值卡货号
	private String productName;// 充值卡名称
	private String cardCode;// 充值卡通道编码
	private BigDecimal cardDiscount;// 按照用户选择的支付方式的折扣率（即时费率）
	private BigDecimal productPrice;// 充值卡价格（面额）
	private String cardNum;//卡号
	private String cardPwd;//密码
	
	private String bankName;// 收款银行名称
	private String bankAccount;// 收款银行账号
	
	private Product product;// 充值卡
	private Member member;// 会员
	private PaymentConfig paymentConfig;// 支付方式
	private Deposit deposit;// 预存款
	
	private Set<OrderLog> orderLogSet = new HashSet<OrderLog>();// 订单日志
	
	@Column(nullable = false, updatable = false, unique = true)
	public String getOrderSn() {
		return orderSn;
	}
	
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	@Enumerated
	@Column(nullable = false)
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(nullable = false, precision = 15, scale = 5)
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_order_deposit")
	public Deposit getDeposit() {
		return deposit;
	}

	public void setDeposit(Deposit deposit) {
		this.deposit = deposit;
	}
	
	@Column(nullable = false, precision = 15, scale = 5)
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	@Column(nullable = false)
	public String getPaymentConfigName() {
		return paymentConfigName;
	}

	public void setPaymentConfigName(String paymentConfigName) {
		this.paymentConfigName = paymentConfigName;
	}
	
	@Column(nullable = false)
	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	
	@Column(length = 100)
	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	@Column(length = 100)
	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	@Column(length = 3000)
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_order_member")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_order_payment_config")
	public PaymentConfig getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(PaymentConfig paymentConfig) {
		this.paymentConfig = paymentConfig;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate asc")
	public Set<OrderLog> getOrderLogSet() {
		return orderLogSet;
	}

	public void setOrderLogSet(Set<OrderLog> orderLogSet) {
		this.orderLogSet = orderLogSet;
	}

	// 保存处理
	@Override
	@Transient
	public void onSave() {
		orderSn = SerialNumberUtil.buildOrderSn();
		if (paidAmount == null || paidAmount.compareTo(new BigDecimal(0)) < 0) {
			paidAmount = new BigDecimal(0);
		}
	}
	
	@Column(nullable = false, updatable = false)
	public String getProductSn() {
		return productSn;
	}
	
	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}
	@Column(nullable = false, updatable = false)
	public String getProductName() {
		return productName;
	}
	
	public String getPaySn() {
		return paySn;
	}

	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Column(nullable = false, precision = 15, scale = 5)
	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	@Column(nullable = false, updatable = false)
	public BigDecimal getCardDiscount() {
		return cardDiscount;
	}

	public void setCardDiscount(BigDecimal cardDiscount) {
		this.cardDiscount = cardDiscount;
	}
	@Column(nullable = false, updatable = false)
	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	@Column(nullable = false, updatable = false)
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	@Column(nullable = false, updatable = false)
	public String getCardPwd() {
		return cardPwd;
	}
	
	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_order_product")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}