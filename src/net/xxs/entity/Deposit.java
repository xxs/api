package net.xxs.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import net.xxs.util.SettingUtil;

import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 预存款
 */

@Entity
public class Deposit extends BaseEntity {

	private static final long serialVersionUID = 4527727387983423232L;
	
	// 预存款操作类型（会员销卡、会员支付、后台代支付、后台代扣费、后台代充值、会员提现、推荐会员提成）
	public enum DepositType {
		memberRecharge, memberPayment, adminRecharge, adminChargeback, adminPayment, memberWithdraw, benefits
	};
	
	private DepositType depositType;// 预存款操作类型
	private BigDecimal credit;// 存入金额
	private BigDecimal debit;// 支出金额
	private BigDecimal balance;// 当前余额
	private BigDecimal lossrate;// 手续费率 (只有提现单涉及)
	private String referrer;// 被推荐人名字 (只有提现单涉及，记录预存款来源)
	private String orderSn;// 被推荐人操作的订单号 (只有提现单涉及，记录预存款来源)
	
	private Member member;// 会员
	private Order order;// 订单
	
	@Enumerated
	@Column(nullable = false, updatable = false)
	public DepositType getDepositType() {
		return depositType;
	}

	public void setDepositType(DepositType depositType) {
		this.depositType = depositType;
	}
	
	@Column(nullable = false, updatable = false)
	public BigDecimal getCredit() {
		return credit;
	}
	
	public void setCredit(BigDecimal credit) {
		this.credit = SettingUtil.setPriceScale(credit);
	}
	
	@Column(nullable = false, updatable = false)
	public BigDecimal getDebit() {
		return debit;
	}
	
	public void setDebit(BigDecimal debit) {
		this.debit = SettingUtil.setPriceScale(debit);
	}
	@Column(updatable = false)	
	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	@Column(updatable = false)
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	@Column(updatable = false)
	public BigDecimal getLossrate() {
		return lossrate;
	}

	public void setLossrate(BigDecimal lossrate) {
		this.lossrate = lossrate;
	}

	@Column(nullable = false, updatable = false)
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = SettingUtil.setPriceScale(balance);
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_deposit_member")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@OneToOne(mappedBy = "deposit", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@ForeignKey(name = "fk_deposit_order")
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	

}