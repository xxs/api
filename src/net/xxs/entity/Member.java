package net.xxs.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import net.xxs.bean.Gender;
import net.xxs.entity.MemberAttribute.AttributeType;
import net.xxs.entity.MemberAttribute.SystemAttributeType;
import net.xxs.entity.Withdraw.WithdrawStatus;
import net.xxs.util.JsonUtil;
import net.xxs.util.ReflectionUtil;
import net.xxs.util.SettingUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 会员
 */

@Entity
public class Member extends BaseEntity {

	private static final long serialVersionUID = 1533130686714725835L;
	
	public static final String MEMBER_ID_SESSION_NAME = "memberId";// 保存登录会员ID的Session名称
	public static final String MEMBER_ANSWER_SESSION_NAME = "memberAnswer";// 保存登录会员密保的Session名称
	public static final String MEMBER_USERNAME_COOKIE_NAME = "memberUsername";// 保存登录会员用户名的Cookie名称
	public static final String MEMBER_QUESTION_COOKIE_NAME = "memberQuestion";// 保存登录会员用户名的Cookie名称
	public static final String PASSWORD_RECOVER_KEY_SEPARATOR = "_";// 密码找回Key分隔符
	public static final int PASSWORD_RECOVER_KEY_PERIOD = 10080;// 密码找回Key有效时间（单位：分钟）
	public static final int MEMBER_ATTRIBUTE_VALUE_PROPERTY_COUNT = 20;// 会员注册项值对象属性个数
	public static final String MEMBER_ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "memberAttributeValue";// 会员注册项值对象属性名称前缀
	
	private String username;// 用户名
	private String password;// 密码
	private String withdrawPwd;// 提现密码
	private String email;// E-mail
	private String safeQuestion;// 密码保护问题
	private String safeAnswer;// 密码保护问题答案
	private String passwordRecoverKey;// 密码找回Key
	private Integer score;// 积分
	private BigDecimal deposit;// 预存款
	private Boolean isAccountEnabled;// 账号是否启用
	private Boolean isAccountLocked;// 账号是否锁定
	private Integer loginFailureCount;// 连续登录失败的次数
	private Date lockedDate;// 账号锁定日期
	private String registerIp;// 注册IP
	private String loginIp;// 最后登录IP
	private Date loginDate;// 最后登录日期
	private String name;// 姓名
	private Gender gender;// 性别
	private Date birth;// 出生日期
	private String address;// 地址
	private String zipCode;// 邮编
	private String phone;// 电话
	private String mobile;// 手机
	private String referrer;// 推荐人
	private String memberAttributeValue0;// 会员注册项值0
	private String memberAttributeValue1;// 会员注册项值1
	private String memberAttributeValue2;// 会员注册项值2
	private String memberAttributeValue3;// 会员注册项值3
	private String memberAttributeValue4;// 会员注册项值4
	private String memberAttributeValue5;// 会员注册项值5
	private String memberAttributeValue6;// 会员注册项值6
	private String memberAttributeValue7;// 会员注册项值7
	private String memberAttributeValue8;// 会员注册项值8
	private String memberAttributeValue9;// 会员注册项值9
	private String memberAttributeValue10;// 会员注册项值10
	private String memberAttributeValue11;// 会员注册项值11
	private String memberAttributeValue12;// 会员注册项值12
	private String memberAttributeValue13;// 会员注册项值13
	private String memberAttributeValue14;// 会员注册项值14
	private String memberAttributeValue15;// 会员注册项值15
	private String memberAttributeValue16;// 会员注册项值16
	private String memberAttributeValue17;// 会员注册项值17
	private String memberAttributeValue18;// 会员注册项值18
	private String memberAttributeValue19;// 会员注册项值19
	
	private MemberRank memberRank;// 会员等级
	private MemberBusiness memberBusiness ; //商户信息
	
	private Set<Withdraw> withdrawSet = new HashSet<Withdraw>();// 提现申请
	private Set<Message> inboxMessageSet = new HashSet<Message>();// 收件箱消息
	private Set<Message> outboxMessageSet = new HashSet<Message>();// 发件箱消息
	private Set<Order> orderSet = new HashSet<Order>();// 订单
	private Set<Deposit> depositSet = new HashSet<Deposit>();// 预存款
	//新添加选项
	private Set<MemberBank> memberBankSet = new HashSet<MemberBank>();//银行卡信息
	

	@Column(nullable = false, updatable = false, unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getWithdrawPwd() {
		return withdrawPwd;
	}

	public void setWithdrawPwd(String withdrawPwd) {
		this.withdrawPwd = withdrawPwd;
	}

	@Column(nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSafeQuestion() {
		return safeQuestion;
	}

	public void setSafeQuestion(String safeQuestion) {
		this.safeQuestion = safeQuestion;
	}

	public String getSafeAnswer() {
		return safeAnswer;
	}

	public void setSafeAnswer(String safeAnswer) {
		this.safeAnswer = safeAnswer;
	}
	
	public String getPasswordRecoverKey() {
		return passwordRecoverKey;
	}

	public void setPasswordRecoverKey(String passwordRecoverKey) {
		this.passwordRecoverKey = passwordRecoverKey;
	}
	
	@Column(nullable = false)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(nullable = false, precision = 15, scale = 5)
	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = SettingUtil.setPriceScale(deposit);
	}
	
	@Column(nullable = false)
	public Boolean getIsAccountEnabled() {
		return isAccountEnabled;
	}

	public void setIsAccountEnabled(Boolean isAccountEnabled) {
		this.isAccountEnabled = isAccountEnabled;
	}

	@Column(nullable = false)
	public Boolean getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(Boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	@Column(nullable = false)
	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}
	
	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}
	
	@Column(nullable = false, updatable = false)
	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}
	
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getMemberAttributeValue0() {
		return memberAttributeValue0;
	}

	public void setMemberAttributeValue0(String memberAttributeValue0) {
		this.memberAttributeValue0 = memberAttributeValue0;
	}

	public String getMemberAttributeValue1() {
		return memberAttributeValue1;
	}

	public void setMemberAttributeValue1(String memberAttributeValue1) {
		this.memberAttributeValue1 = memberAttributeValue1;
	}

	public String getMemberAttributeValue2() {
		return memberAttributeValue2;
	}

	public void setMemberAttributeValue2(String memberAttributeValue2) {
		this.memberAttributeValue2 = memberAttributeValue2;
	}

	public String getMemberAttributeValue3() {
		return memberAttributeValue3;
	}

	public void setMemberAttributeValue3(String memberAttributeValue3) {
		this.memberAttributeValue3 = memberAttributeValue3;
	}

	public String getMemberAttributeValue4() {
		return memberAttributeValue4;
	}

	public void setMemberAttributeValue4(String memberAttributeValue4) {
		this.memberAttributeValue4 = memberAttributeValue4;
	}

	public String getMemberAttributeValue5() {
		return memberAttributeValue5;
	}

	public void setMemberAttributeValue5(String memberAttributeValue5) {
		this.memberAttributeValue5 = memberAttributeValue5;
	}

	public String getMemberAttributeValue6() {
		return memberAttributeValue6;
	}

	public void setMemberAttributeValue6(String memberAttributeValue6) {
		this.memberAttributeValue6 = memberAttributeValue6;
	}

	public String getMemberAttributeValue7() {
		return memberAttributeValue7;
	}

	public void setMemberAttributeValue7(String memberAttributeValue7) {
		this.memberAttributeValue7 = memberAttributeValue7;
	}

	public String getMemberAttributeValue8() {
		return memberAttributeValue8;
	}

	public void setMemberAttributeValue8(String memberAttributeValue8) {
		this.memberAttributeValue8 = memberAttributeValue8;
	}

	public String getMemberAttributeValue9() {
		return memberAttributeValue9;
	}

	public void setMemberAttributeValue9(String memberAttributeValue9) {
		this.memberAttributeValue9 = memberAttributeValue9;
	}

	public String getMemberAttributeValue10() {
		return memberAttributeValue10;
	}

	public void setMemberAttributeValue10(String memberAttributeValue10) {
		this.memberAttributeValue10 = memberAttributeValue10;
	}

	public String getMemberAttributeValue11() {
		return memberAttributeValue11;
	}

	public void setMemberAttributeValue11(String memberAttributeValue11) {
		this.memberAttributeValue11 = memberAttributeValue11;
	}

	public String getMemberAttributeValue12() {
		return memberAttributeValue12;
	}

	public void setMemberAttributeValue12(String memberAttributeValue12) {
		this.memberAttributeValue12 = memberAttributeValue12;
	}

	public String getMemberAttributeValue13() {
		return memberAttributeValue13;
	}

	public void setMemberAttributeValue13(String memberAttributeValue13) {
		this.memberAttributeValue13 = memberAttributeValue13;
	}

	public String getMemberAttributeValue14() {
		return memberAttributeValue14;
	}

	public void setMemberAttributeValue14(String memberAttributeValue14) {
		this.memberAttributeValue14 = memberAttributeValue14;
	}

	public String getMemberAttributeValue15() {
		return memberAttributeValue15;
	}

	public void setMemberAttributeValue15(String memberAttributeValue15) {
		this.memberAttributeValue15 = memberAttributeValue15;
	}

	public String getMemberAttributeValue16() {
		return memberAttributeValue16;
	}

	public void setMemberAttributeValue16(String memberAttributeValue16) {
		this.memberAttributeValue16 = memberAttributeValue16;
	}

	public String getMemberAttributeValue17() {
		return memberAttributeValue17;
	}

	public void setMemberAttributeValue17(String memberAttributeValue17) {
		this.memberAttributeValue17 = memberAttributeValue17;
	}

	public String getMemberAttributeValue18() {
		return memberAttributeValue18;
	}

	public void setMemberAttributeValue18(String memberAttributeValue18) {
		this.memberAttributeValue18 = memberAttributeValue18;
	}

	public String getMemberAttributeValue19() {
		return memberAttributeValue19;
	}

	public void setMemberAttributeValue19(String memberAttributeValue19) {
		this.memberAttributeValue19 = memberAttributeValue19;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_member_member_rank")
	public MemberRank getMemberRank() {
		return memberRank;
	}

	public void setMemberRank(MemberRank memberRank) {
		this.memberRank = memberRank;
	}

	@OneToMany(mappedBy = "toMember", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate asc")
	public Set<Message> getInboxMessageSet() {
		return inboxMessageSet;
	}

	public void setInboxMessageSet(Set<Message> inboxMessageSet) {
		this.inboxMessageSet = inboxMessageSet;
	}

	@OneToMany(mappedBy = "fromMember", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate asc")
	public Set<Message> getOutboxMessageSet() {
		return outboxMessageSet;
	}

	public void setOutboxMessageSet(Set<Message> outboxMessageSet) {
		this.outboxMessageSet = outboxMessageSet;
	}

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	@OrderBy("createDate desc")
	public Set<Withdraw> getWithdrawSet() {
		return withdrawSet;
	}

	public void setWithdrawSet(Set<Withdraw> withdrawSet) {
		this.withdrawSet = withdrawSet;
	}

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	@OrderBy("createDate desc")
	public Set<Order> getOrderSet() {
		return orderSet;
	}

	public void setOrderSet(Set<Order> orderSet) {
		this.orderSet = orderSet;
	}

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate asc")
	public Set<Deposit> getDepositSet() {
		return depositSet;
	}

	public void setDepositSet(Set<Deposit> depositSet) {
		this.depositSet = depositSet;
	}
	
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate asc")
	public Set<MemberBank> getMemberBankSet() {
		return memberBankSet;
	}

	public void setMemberBankSet(Set<MemberBank> memberBankSet) {
		this.memberBankSet = memberBankSet;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_member_member_business")
	public MemberBusiness getMemberBusiness() {
		return memberBusiness;
	}
	
	public void setMemberBusiness(MemberBusiness memberBusiness) {
		this.memberBusiness = memberBusiness;
	}


	// 获取会员注册项值
	@Transient
	public Object getMemberAttributeValue(MemberAttribute memberAttribute) {
		if (memberAttribute == null) {
			return null;
		}
		SystemAttributeType systemAttributeType = memberAttribute.getSystemAttributeType();
		AttributeType attributeType = memberAttribute.getAttributeType();
		if (systemAttributeType != null) {
			if (systemAttributeType == SystemAttributeType.name) {
				return name;
			}
			if (systemAttributeType == SystemAttributeType.gender) {
				return gender;
			}
			if (systemAttributeType == SystemAttributeType.birth) {
				return birth;
			}
			if (systemAttributeType == SystemAttributeType.address) {
				return address;
			}
			if (systemAttributeType == SystemAttributeType.zipCode) {
				return zipCode;
			}
			if (systemAttributeType == SystemAttributeType.phone) {
				return phone;
			}
			if (systemAttributeType == SystemAttributeType.mobile) {
				return mobile;
			}
			if (systemAttributeType == SystemAttributeType.referrer) {
				return referrer;
			}
		} else if(attributeType != null) {
			String propertyName = MEMBER_ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
			String propertyValue = (String) ReflectionUtil.invokeGetterMethod(this, propertyName);
			if (StringUtils.isNotEmpty(propertyValue)) {
				if (attributeType == AttributeType.checkbox) {
					return JsonUtil.toObject(propertyValue, ArrayList.class);
				} else {
					return propertyValue;
				}
			}
		} else {
			throw new IllegalArgumentException("memberAttribute error");
		}
		return null;
	}
	

	// 设置会员注册项值
	@SuppressWarnings("unchecked")
	@Transient
	public void setMemberAttributeValue(MemberAttribute memberAttribute, Object memberAttributeValue) {
		if (memberAttribute == null) {
			return;
		}
		SystemAttributeType systemAttributeType = memberAttribute.getSystemAttributeType();
		AttributeType attributeType = memberAttribute.getAttributeType();
		if (systemAttributeType != null) {
			if (systemAttributeType == SystemAttributeType.name) {
				name = memberAttributeValue.toString();
			} else if (systemAttributeType == SystemAttributeType.gender) {
				gender = Gender.valueOf(memberAttributeValue.toString());
			} else if (systemAttributeType == SystemAttributeType.birth) {
				try {
					String[] pattern = new String[]{"yyyy-MM","yyyyMM","yyyy/MM", "yyyyMMdd","yyyy-MM-dd","yyyy/MM/dd", "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss"};
					birth = DateUtils.parseDate(memberAttributeValue.toString(), pattern);
				} catch (ParseException e) {
					throw new IllegalArgumentException("memberAttributeValue type error");
				}
			}  else if (systemAttributeType == SystemAttributeType.address) {
				address = memberAttributeValue.toString();
			} else if (systemAttributeType == SystemAttributeType.zipCode) {
				zipCode = memberAttributeValue.toString();
			} else if (systemAttributeType == SystemAttributeType.phone) {
				phone = memberAttributeValue.toString();
			} else if (systemAttributeType == SystemAttributeType.mobile) {
				mobile = memberAttributeValue.toString();
			} else if (systemAttributeType == SystemAttributeType.referrer) {
				referrer = memberAttributeValue.toString();
			}
		} else if(attributeType != null) {
			String propertyName = MEMBER_ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
			if (memberAttribute.getAttributeType() == AttributeType.checkbox) {
				if (memberAttributeValue instanceof ArrayList) {
					for (String value : (ArrayList<String>) memberAttributeValue) {
						if (!memberAttribute.getOptionList().contains(value)) {
							throw new IllegalArgumentException("memberAttributeValue type error");
						}
					}
				} else if (memberAttributeValue instanceof String[]) {
					for (String value : (String[]) memberAttributeValue) {
						if (!memberAttribute.getOptionList().contains(value)) {
							throw new IllegalArgumentException("memberAttributeValue type error");
						}
					}
				} else {
					throw new IllegalArgumentException("memberAttributeValue type error");
				}
				ReflectionUtil.invokeSetterMethod(this, propertyName, JsonUtil.toJson(memberAttributeValue));
			} else {
				ReflectionUtil.invokeSetterMethod(this, propertyName, memberAttributeValue);
			}
		} else {
			throw new IllegalArgumentException("memberAttributeValue error");
		}
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (score == null || score < 0) {
			score = 0;
		}
		if (deposit == null || deposit.compareTo(new BigDecimal(0)) < 0) {
			deposit = new BigDecimal(0);
		}
		if (isAccountEnabled == null) {
			isAccountEnabled = false;
		}
		if (isAccountLocked == null) {
			isAccountLocked = false;
		}
		if (loginFailureCount == null || loginFailureCount < 0) {
			loginFailureCount = 0;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (score == null || score < 0) {
			score = 0;
		}
		if (deposit == null || deposit.compareTo(new BigDecimal(0)) < 0) {
			deposit = new BigDecimal(0);
		}
		if (isAccountEnabled == null) {
			isAccountEnabled = false;
		}
		if (isAccountLocked == null) {
			isAccountLocked = false;
		}
		if (loginFailureCount == null || loginFailureCount < 0) {
			loginFailureCount = 0;
		}
	}
	
	/**
	 * 设置所有会员注册项值为null
	 * 
	 */
	@Transient
	public void setMemberAttributeValueToNull() {
		for (int i = 0; i < MEMBER_ATTRIBUTE_VALUE_PROPERTY_COUNT; i ++) {
			String propertyName = MEMBER_ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
			ReflectionUtil.invokeSetterMethod(this, propertyName, null, String.class);
		}
	}
	/**
	 * 获取会员提现的总金额
	 */
	@Transient
	public BigDecimal getTotalWithdrawMoneySuccess() {
		BigDecimal totolMoney = new BigDecimal(0);
		for(Withdraw withdraw:withdrawSet){
			if(withdraw.getWithdrawStatus().equals(WithdrawStatus.success)){
				totolMoney = totolMoney.add(withdraw.getMoney());
			}	
		}	
		return totolMoney;
	}
	/**
	 * 获取会员提现中的金额（即冻结）
	 */
	@Transient
	public BigDecimal getTotalWithdrawMoneyApplying() {
		BigDecimal totolMoney = new BigDecimal(0);
		for(Withdraw withdraw:withdrawSet){
			if(withdraw.getWithdrawStatus().equals(WithdrawStatus.apply)){
				totolMoney = totolMoney.add(withdraw.getMoney());
			}	
		}	
		return totolMoney;
	}
}