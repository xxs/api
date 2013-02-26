package net.xxs.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 会员银行卡信息
 */

@Entity
public class MemberBank extends BaseEntity {

	private static final long serialVersionUID = 3599029355500655209L;

	public static final Integer MAX_MEMBERBANK_COUNT = 5;// 会员体现账户最大保存数,为null则无限制
	
	private String banknum;
	private String bankname;
	private String openname;
	private String bankcity;
	private String bankdetail;
	private String memo;
	private Boolean isDefault;
	private Member member; // 会员
	
	private Set<Withdraw> withdrawSet = new HashSet<Withdraw>();// 提现记录

	@Column(nullable = false, unique = true)
	public String getBanknum() {
		return banknum;
	}

	public void setBanknum(String banknum) {
		this.banknum = banknum;
	}
	@Column(nullable = false)
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	@Column(nullable = false)
	public String getOpenname() {
		return openname;
	}

	public void setOpenname(String openname) {
		this.openname = openname;
	}
	@Column(nullable = false)
	public String getBankdetail() {
		return bankdetail;
	}

	public void setBankdetail(String bankdetail) {
		this.bankdetail = bankdetail;
	}
	@Column(length = 3000)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Column(nullable = false)
	public String getBankcity() {
		return bankcity;
	}

	public void setBankcity(String bankcity) {
		this.bankcity = bankcity;
	}
	
	@Column(nullable = false)
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_member_bank_member")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@OneToMany(mappedBy = "memberBank", fetch = FetchType.LAZY)
	@OrderBy("createDate desc")	
	public Set<Withdraw> getWithdrawSet() {
		return withdrawSet;
	}

	public void setWithdrawSet(Set<Withdraw> withdrawSet) {
		this.withdrawSet = withdrawSet;
	}
	

	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (isDefault == null) {
			isDefault = false;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (isDefault == null) {
			isDefault = false;
		}
	}

}