package net.xxs.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 收卡方式折扣
 */

@Entity
public class PaymentDiscount extends BaseEntity {

	private static final long serialVersionUID = 8861279308566205742L;

	private String code;// 通道编码
	private BigDecimal discount;// 折扣率
	private String face;//支持面额范围  将来转换为数组
	private String operator;// 操作员
	private String memo;// 备注
	
	private PaymentConfig paymentConfig;// 支付方式
	private Brand brand;// 品牌
	
	@Column(updatable = true)
	public String getOperator() {
		return operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Column(updatable = true, length = 3000)
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_payment_discount_payment_config")
	public PaymentConfig getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(PaymentConfig paymentConfig) {
		this.paymentConfig = paymentConfig;
	}
	@Column(updatable = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(updatable = true, length = 1000)
	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	@Column(updatable = true)
	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_payment_Discount_brand")
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (discount == null || discount.compareTo(new BigDecimal(0)) < 0) {
			discount = new BigDecimal(0);
		}
		if (StringUtils.isEmpty(operator)) {
			operator = "admin";
		}
		if (StringUtils.isEmpty(memo)) {
			memo = null;
		}
		if (StringUtils.isEmpty(face)) {
			face = "0";
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (discount == null || discount.compareTo(new BigDecimal(0)) < 0) {
			discount = new BigDecimal(0);
		}
		if (StringUtils.isEmpty(operator)) {
			operator = "admin";
		}
		if (StringUtils.isEmpty(memo)) {
			memo = null;
		}
		if (StringUtils.isEmpty(face)) {
			face = "0";
		}
	}
	

}