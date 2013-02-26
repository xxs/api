package net.xxs.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import net.xxs.util.SettingUtil;

/**
 * 实体类 - 支付配置
 */

@Entity
public class PaymentConfig extends BaseEntity {

	private static final long serialVersionUID = -7950849648189504426L;
	
	// 支付配置类型（预存款、线下支付、在线支付）
	public enum PaymentConfigType {
		deposit, offline, online
	};
	
	// 支付手续费类型（按比例收费、固定费用）
	public enum PaymentFeeType {
		scale, fixed
	}
	
	private PaymentConfigType paymentConfigType;// 支付配置类型
	private String paymentProductId;// 支付产品标识
	private String name;// 支付方式名称
	private String bargainorId;// 商家ID
	private String bargainorKey;// 商户私钥
	private PaymentFeeType paymentFeeType;// 支付手续费类型
	private BigDecimal paymentFee;// 支付费用
	private Boolean isDefault;// 是否默认
	private Boolean isEnabled;// 是否可用
	private String description;// 介绍
	private Integer orderList;// 排序
	
	private Set<PaymentDiscount> paymentDiscountSet = new HashSet<PaymentDiscount>();// 费率设置
	private Set<Order> orderSet = new HashSet<Order>();// 订单
	
	@Enumerated
	@Column(nullable = false, updatable = false)
	public PaymentConfigType getPaymentConfigType() {
		return paymentConfigType;
	}

	public void setPaymentConfigType(PaymentConfigType paymentConfigType) {
		this.paymentConfigType = paymentConfigType;
	}
	
	@Column(updatable = false)
	public String getPaymentProductId() {
		return paymentProductId;
	}

	public void setPaymentProductId(String paymentProductId) {
		this.paymentProductId = paymentProductId;
	}

	@Column(nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBargainorId() {
		return bargainorId;
	}

	public void setBargainorId(String bargainorId) {
		this.bargainorId = bargainorId;
	}

	public String getBargainorKey() {
		return bargainorKey;
	}

	public void setBargainorKey(String bargainorKey) {
		this.bargainorKey = bargainorKey;
	}

	@Enumerated
	@Column(nullable = false)
	public PaymentFeeType getPaymentFeeType() {
		return paymentFeeType;
	}

	public void setPaymentFeeType(PaymentFeeType paymentFeeType) {
		this.paymentFeeType = paymentFeeType;
	}

	@Column(nullable = false, precision = 15, scale = 5)
	public BigDecimal getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(BigDecimal paymentFee) {
		this.paymentFee = SettingUtil.setPriceScale(paymentFee);
	}

	@Column(length = 3000)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getOrderList() {
		return orderList;
	}
	
	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	
	@OneToMany(mappedBy = "paymentConfig", fetch = FetchType.LAZY)
	public Set<Order> getOrderSet() {
		return orderSet;
	}

	public void setOrderSet(Set<Order> orderSet) {
		this.orderSet = orderSet;
	}

	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (paymentFee == null || paymentFee.compareTo(new BigDecimal(0)) < 0) {
			paymentFee = new BigDecimal(0);
		}
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
		if (StringUtils.isEmpty(description)) {
			description = null;
		}
		if (isDefault == null) {
			isDefault = false;
		}
		if (isEnabled == null) {
			isEnabled = true;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (paymentFee == null || paymentFee.compareTo(new BigDecimal(0)) < 0) {
			paymentFee = new BigDecimal(0);
		}
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
		if (StringUtils.isEmpty(description)) {
			description = null;
		}
		if (isDefault == null) {
			isDefault = false;
		}
		if (isEnabled == null) {
			isEnabled = true;
		}
	}
	
	/**
	 * 根据总金额计算支付费用
	 * 
	 * @return 支付费用
	 */
	@Transient
	public BigDecimal getPaymentFee(BigDecimal totalAmount) {
		BigDecimal paymentFee = new BigDecimal(0);
		if (paymentFeeType == PaymentFeeType.scale){
			paymentFee = totalAmount.multiply(this.paymentFee.divide(new BigDecimal(100)));
		} else {
			paymentFee = this.paymentFee;
		}
		return SettingUtil.setPriceScale(paymentFee);
	}
	@OneToMany(mappedBy = "paymentConfig", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate asc")
	public Set<PaymentDiscount> getPaymentDiscountSet() {
		return paymentDiscountSet;
	}

	public void setPaymentDiscountSet(Set<PaymentDiscount> paymentDiscountSet) {
		this.paymentDiscountSet = paymentDiscountSet;
	}
	@Column(nullable = false)
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	@Column(nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	
}