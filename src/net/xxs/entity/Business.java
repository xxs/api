package net.xxs.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import net.xxs.entity.Withdraw.WithdrawStatus;
import net.xxs.util.SerialNumberUtil;

/**
 * 实体类 - 商户信息
 */

@Entity
public class Business extends BaseEntity {

	private static final long serialVersionUID = -2006176757545537231L;
	
	public static final String BUSINESS_ID_SESSION_NAME = "businessId";// 保存登陆商户ID的Session名称
	public static final String BUSINESS_ANSWER_SESSION_NAME = "businessAnswer";// 保存登录商户密保的Session名称
	public static final String BUSINESS_USERNAME_COOKIE_NAME = "businessUsername";// 保存登录商户用户名的Cookie名称
	public static final String PASSWORD_RECOVER_KEY_SEPARATOR = "_";// 密码找回Key分隔符
	public static final int PASSWORD_RECOVER_KEY_PERIOD = 10080;// 密码找回Key有效时间（单位：分钟）
	
	// 商户类型
	public enum BusinessType {
		personal, enterprise
	};
	// 商户状态
	public enum ResultType {
		apply, success, lose
	};
	
	
	private String username;			// 用户名
	private String password;			// 密码
	private String withdrawPwd;			// 提现密码
	private String email;				// E-mail
	private String passwordRecoverKey;	// 密码找回Key
	private BigDecimal deposit;			// 预存款
	private BusinessType businessType;	//商户类型
	private String realName;			//真实姓名
	private String businessNumber;		//身份证号（营业执照号码）
	private String businessName;		//商户名称
	private String city;				//商户地区
	private String scope;				//经营范围
	private String url;					//商城网址
	private String icp;					//ICP证备案号
	private String linkMan;				//联系人
	private String tel; 				//业务电话
	private String phone; 				//业务手机
	private String serverTel; 			//客服热线
	private String serverTime; 			//客服工作时间
	private String QQ;					//即时通讯 
	private String address;				//通讯地址
	private String zipcode;				//邮编
	private String memo;				//是否通过备注
	private String BusinessNum;  		//商户号
	private String BusinessKey;			//秘钥
	private BigDecimal lossrate;			//提现折扣
	
	private String safeQuestion;		// 密码保护问题
	private String safeAnswer;			// 密码保护问题答案
	private Boolean isAccountEnabled;	// 账号是否启用
	private Integer loginFailureCount;	// 连续登录失败的次数
	private Date lockedDate;			// 账号锁定日期
	private String registerIp;			// 注册IP
	private String loginIp;				// 最后登录IP
	private Date loginDate;				// 最后登录日期
	
	private ResultType resultType;								//审核是否通过
	private Set<Order> orderSet = new HashSet<Order>();			// 订单
	private Set<Deposit> depositSet = new HashSet<Deposit>();	// 预存款
	private Set<Bank> bankSet = new HashSet<Bank>();		//银行卡信息
	private Set<Withdraw> withdrawSet = new HashSet<Withdraw>();// 提现申请
	
	
	@Enumerated
	@Column(nullable = false)
	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}
	@Enumerated
	@Column(nullable = false)
	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	@Column(nullable = false)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	@Column(nullable = false, unique = true)
	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}
	@Column(nullable = false, unique = true)
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	@Column(nullable = false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	@Column(nullable = false)
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	@Column(nullable = false, unique = true)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@Column(nullable = false)
	public String getIcp() {
		return icp;
	}

	public void setIcp(String icp) {
		this.icp = icp;
	}
	@Column(nullable = false)
	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	@Column(nullable = false)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	@Column(nullable = false)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(nullable = false)
	public String getServerTel() {
		return serverTel;
	}

	public void setServerTel(String serverTel) {
		this.serverTel = serverTel;
	}
	
	public String getServerTime() {
		return serverTime;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}
	@Column(nullable = false)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(nullable = false)
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Column(nullable = true, updatable = false, unique = true)
	public String getBusinessNum() {
		return BusinessNum;
	}

	public void setBusinessNum(String businessNum) {
		BusinessNum = businessNum;
	}

	@Column(nullable = true, unique = true)
	public String getBusinessKey() {
		return BusinessKey;
	}

	public void setBusinessKey(String businessKey) {
		BusinessKey = businessKey;
	}
	
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordRecoverKey() {
		return passwordRecoverKey;
	}

	public void setPasswordRecoverKey(String passwordRecoverKey) {
		this.passwordRecoverKey = passwordRecoverKey;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
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

	public Boolean getIsAccountEnabled() {
		return isAccountEnabled;
	}

	public void setIsAccountEnabled(Boolean isAccountEnabled) {
		this.isAccountEnabled = isAccountEnabled;
	}

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

	public Set<Order> getOrderSet() {
		return orderSet;
	}

	public void setOrderSet(Set<Order> orderSet) {
		this.orderSet = orderSet;
	}

	public Set<Deposit> getDepositSet() {
		return depositSet;
	}

	public void setDepositSet(Set<Deposit> depositSet) {
		this.depositSet = depositSet;
	}

	public Set<Bank> getBankSet() {
		return bankSet;
	}

	public void setBankSet(Set<Bank> bankSet) {
		this.bankSet = bankSet;
	}

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
		BusinessNum = SerialNumberUtil.buildBusinessNumber();
		BusinessKey = SerialNumberUtil.buildBusinessKey();
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		
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

	public BigDecimal getLossrate() {
		return lossrate;
	}

	public void setLossrate(BigDecimal lossrate) {
		this.lossrate = lossrate;
	}

}