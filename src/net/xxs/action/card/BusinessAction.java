package net.xxs.action.card;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.xxs.bean.Setting;
import net.xxs.entity.Business;
import net.xxs.service.BusinessService;
import net.xxs.service.MailService;
import net.xxs.util.CaptchaUtil;
import net.xxs.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 密码、安全问题
 */

@ParentPackage("card")
@InterceptorRefs({
	@InterceptorRef(value = "memberVerifyInterceptor"),
	@InterceptorRef(value = "cardStack")
})
public class BusinessAction extends BaseCardAction {

	private static final long serialVersionUID = 8691965706902473480L;
	
	private Business business;
	private String loginRedirectUrl;
	private Boolean isAgreeAgreement;
	private String passwordRecoverKey;
	
	@Resource(name = "businessServiceImpl")
	private BusinessService businessService;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	
	// 商户登录
	@SuppressWarnings("unchecked")
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "business.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "business.password", message = "密码不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String login() throws Exception {
		if (!CaptchaUtil.validateCaptchaByRequest(getRequest())) {
			addActionError("验证码输入错误!");
			return ERROR;
		}
		Setting setting = getSetting();
		Business loginBusiness = businessService.getBusinessByEmail(business.getEmail());
		if (loginBusiness != null) {
			// 解除会员账户锁定
			if (loginBusiness.getIsAccountLocked()) {
				if (setting.getIsLoginFailureLock()) {
					int loginFailureLockTime = setting.getLoginFailureLockTime();
					if (loginFailureLockTime > 0) {
						Date lockedDate = loginBusiness.getLockedDate();
						Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
						if (new Date().after(unlockDate)) {
							loginBusiness.setLoginFailureCount(0);
							loginBusiness.setIsAccountLocked(false);
							loginBusiness.setLockedDate(null);
							businessService.update(loginBusiness);
						}
					}
				} else {
					loginBusiness.setLoginFailureCount(0);
					loginBusiness.setIsAccountLocked(false);
					loginBusiness.setLockedDate(null);
					businessService.update(loginBusiness);
				}
			}
			if (!loginBusiness.getIsAccountEnabled()) {
				addActionError("您的账号已被禁用,无法登录!");
				return ERROR;
			}
			if (loginBusiness.getIsAccountLocked()) {
				addActionError("您的账号已被锁定,无法登录!");
				return ERROR;
			}
			if (!businessService.verifyBusiness(business.getEmail(), business.getPassword())) {
				if (setting.getIsLoginFailureLock()) {
					int loginFailureLockCount = setting.getLoginFailureLockCount();
					int loginFailureCount = loginBusiness.getLoginFailureCount() + 1;
					if (loginFailureCount >= setting.getLoginFailureLockCount()) {
						loginBusiness.setIsAccountLocked(true);
						loginBusiness.setLockedDate(new Date());
					}
					loginBusiness.setLoginFailureCount(loginFailureCount);
					businessService.update(loginBusiness);
					if (setting.getIsLoginFailureLock() && loginFailureLockCount - loginFailureCount <= 3) {
						addActionError("若连续" + loginFailureLockCount + "次密码输入错误,您的账号将被锁定!");
						return ERROR;
					} else {
						addActionError("您的用户名或密码错误!");
						return ERROR;
					}
				} else {
					addActionError("用户名或密码错误!");
					return ERROR;
				}
			}
		} else {
			addActionError("用户名或密码错误!");
			return ERROR;
		}
		loginBusiness.setLoginIp(getRequest().getRemoteAddr());
		loginBusiness.setLoginDate(new Date());
		businessService.update(loginBusiness);
		
		// 防止Session Fixation攻击
		HttpSession httpSession = getRequest().getSession();
		Enumeration enumeration = httpSession.getAttributeNames();
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			sessionMap.put(key, httpSession.getAttribute(key));
		}
		httpSession.invalidate();
		httpSession = getRequest().getSession(true);
		for (String key : sessionMap.keySet()) {
			Object value = sessionMap.get(key);
			httpSession.setAttribute(key, value);
		}
		
		// 写入会员登录Session
		httpSession.setAttribute(business.BUSINESS_ID_SESSION_NAME, loginBusiness.getId());
		
		setCookie(business.BUSINESS_USERNAME_COOKIE_NAME, URLEncoder.encode(business.getEmail().toLowerCase(), "UTF-8"));
		
		
		if (StringUtils.isNotEmpty(loginRedirectUrl)) {
			redirectUrl = loginRedirectUrl;
		}
		logInfo = "会员登录: " +loginBusiness.getEmail();
		return REDIRECT;
	}
	// Ajax验证会员是否登录
	@InputConfig(resultName = "ajaxError")
	public String ajaxMemberVerify() throws Exception {
		Business loginBusiness = getLoginBusiness();
		if (loginBusiness != null) {
			return ajax(true);
		} else {
			removeSession(Business.BUSINESS_ID_SESSION_NAME);
			removeCookie(Business.BUSINESS_USERNAME_COOKIE_NAME);
			return ajax(false);
		}
	}
	// Ajax会员密保验证
	@SuppressWarnings("unchecked")
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "member.safeQuestion", message = "密保问题不允许为空!"),
			@RequiredStringValidator(fieldName = "member.safeAnswer", message = "密保答案不允许为空!")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxSafeQuestion() throws Exception {
		Business loginBusiness = getLoginBusiness();
		if (!businessService.verifySafeQuestion(loginBusiness,business.getSafeQuestion(), business.getSafeAnswer())) {
			return ajax(Status.error, "密保验证错误!");
		}
		loginBusiness.setLoginIp(getRequest().getRemoteAddr());
		loginBusiness.setLoginDate(new Date());
		businessService.update(loginBusiness);
		
		// 防止Session Fixation攻击
		HttpSession httpSession = getRequest().getSession();
		Enumeration enumeration = httpSession.getAttributeNames();
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			sessionMap.put(key, httpSession.getAttribute(key));
		}
		httpSession.invalidate();
		httpSession = getRequest().getSession(true);
		for (String key : sessionMap.keySet()) {
			Object value = sessionMap.get(key);
			httpSession.setAttribute(key, value);
		}
		
		// 写入会员密保Session
		httpSession.setAttribute(Business.BUSINESS_ANSWER_SESSION_NAME, loginBusiness.getSafeAnswer());
		
		return ajax(Status.success, "密保验证通过!");
	}
	
	// Ajax会员登录验证
	@SuppressWarnings("unchecked")
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxLogin() throws Exception {
		if (!CaptchaUtil.validateCaptchaByRequest(getRequest())) {
			return ajax(Status.error, "验证码输入错误!");
		}
		
		Setting setting = getSetting();
		Business loginBusiness = businessService.getBusinessByEmail(business.getEmail());
		if (loginBusiness != null) {
			// 解除会员账户锁定
			if (loginBusiness.getIsAccountLocked()) {
				if (setting.getIsLoginFailureLock()) {
					int loginFailureLockTime = setting.getLoginFailureLockTime();
					if (loginFailureLockTime > 0) {
						Date lockedDate = loginBusiness.getLockedDate();
						Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
						if (new Date().after(unlockDate)) {
							loginBusiness.setLoginFailureCount(0);
							loginBusiness.setIsAccountLocked(false);
							loginBusiness.setLockedDate(null);
							businessService.update(loginBusiness);
						}
					}
				} else {
					loginBusiness.setLoginFailureCount(0);
					loginBusiness.setIsAccountLocked(false);
					loginBusiness.setLockedDate(null);
					businessService.update(loginBusiness);
				}
			}
			if (!loginBusiness.getIsAccountEnabled()) {
				return ajax(Status.error, "您的账号已被禁用,无法登录!");
			}
			if (loginBusiness.getIsAccountLocked()) {
				return ajax(Status.error, "您的账号已被锁定,无法登录!");
			}
			if (!businessService.verifyBusiness(business.getEmail(), business.getPassword())) {
				if (setting.getIsLoginFailureLock()) {
					int loginFailureLockCount = setting.getLoginFailureLockCount();
					int loginFailureCount = loginBusiness.getLoginFailureCount() + 1;
					if (loginFailureCount >= setting.getLoginFailureLockCount()) {
						loginBusiness.setIsAccountLocked(true);
						loginBusiness.setLockedDate(new Date());
					}
					loginBusiness.setLoginFailureCount(loginFailureCount);
					businessService.update(loginBusiness);
					if (setting.getIsLoginFailureLock() && loginFailureLockCount - loginFailureCount <= 3) {
						return ajax(Status.error, "若连续" + loginFailureLockCount + "次密码输入错误,您的账号将被锁定!");
					} else {
						return ajax(Status.error, "您的用户名或密码错误!");
					}
				} else {
					return ajax(Status.error, "用户名或密码错误!");
				}
			}
		} else {
			return ajax(Status.error, "用户名或密码错误!");
		}
		loginBusiness.setLoginIp(getRequest().getRemoteAddr());
		loginBusiness.setLoginDate(new Date());
		businessService.update(loginBusiness);
		
		// 防止Session Fixation攻击
		HttpSession httpSession = getRequest().getSession();
		Enumeration enumeration = httpSession.getAttributeNames();
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			sessionMap.put(key, httpSession.getAttribute(key));
		}
		httpSession.invalidate();
		httpSession = getRequest().getSession(true);
		for (String key : sessionMap.keySet()) {
			Object value = sessionMap.get(key);
			httpSession.setAttribute(key, value);
		}
		
		// 写入会员登录Session
		httpSession.setAttribute(Business.BUSINESS_ID_SESSION_NAME, loginBusiness.getId());
		
		// 写入会员登录Cookie
		setCookie(Business.BUSINESS_USERNAME_COOKIE_NAME, URLEncoder.encode(business.getEmail().toLowerCase(), "UTF-8"));
		
		return ajax(Status.success, "会员登录成功!");
	}
	
	// 会员注销
	public String logout() {
		removeSession(Business.BUSINESS_ID_SESSION_NAME);
		removeCookie(Business.BUSINESS_USERNAME_COOKIE_NAME);
		return REDIRECT;
	}

	// 检查用户名是否存在
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String checkUsername() {
		String username = business.getEmail();
		if (businessService.isExistByBusinessName(username)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}

	// Ajax会员注册
	@SuppressWarnings("unchecked")
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!"),
			@RequiredStringValidator(fieldName = "member.email", message = "E-mail不允许为空!"),
			@RequiredStringValidator(fieldName = "member.referrer", message = "推荐人不允许为空!")
		}, 
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "member.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "member.referrer", minLength = "2", maxLength = "20", message = "推荐人长度必须在${minLength}到${maxLength}之间!")
		}, 
		emails = { 
			@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") 
		}, 
		regexFields = { 
			@RegexFieldValidator(fieldName = "member.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!"), 
			@RegexFieldValidator(fieldName = "member.referrer", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "推荐人只允许包含中文、英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxRegister() throws Exception {
		if (isAgreeAgreement == null || isAgreeAgreement == false) {
			return ajax(Status.error, "必须同意注册协议才可进行注册操作!");
		}
		if (!getSetting().getIsRegisterEnabled()) {
			return ajax(Status.error, "本站注册功能现已关闭!");
		}
		if (!CaptchaUtil.validateCaptchaByRequest(getRequest())) {
			return ajax(Status.error, "验证码输入错误!");
		}
		
		business.setEmail(business.getEmail().toLowerCase());
		business.setPassword(StringUtil.md5(business.getPassword()));
		business.setSafeQuestion(null);
		business.setSafeAnswer(null);
		business.setDeposit(new BigDecimal(0));
		business.setIsAccountEnabled(true);
		business.setIsAccountLocked(false);
		business.setLoginFailureCount(0);
		business.setLockedDate(null);
		business.setRegisterIp(getRequest().getRemoteAddr());
		business.setLoginIp(getRequest().getRemoteAddr());
		business.setLoginDate(new Date());
		business.setPasswordRecoverKey(null);
		businessService.save(business);
		
		// 写入会员登录Session
		setSession(Business.BUSINESS_ID_SESSION_NAME, business.getId());
		
		// 写入会员登录Cookie
		setCookie(Business.BUSINESS_USERNAME_COOKIE_NAME, URLEncoder.encode(business.getEmail().toLowerCase(), "UTF-8"));
		
		return ajax(Status.success, "会员注册成功!");
	}
	
	// 密码找回
	public String passwordRecover() throws Exception {
		return "password_recover";
	}
	
	// 发送密码找回邮件
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "member.email", message = "E-mail不允许为空!") 
		}, 
		emails = { 
			@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") 
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxSendPasswordRecoverMail() throws Exception {
		Business persistent = businessService.getBusinessByEmail(business.getEmail());
		if (persistent == null || !StringUtils.equalsIgnoreCase(persistent.getEmail(), business.getEmail())) {
			return ajax(Status.error, "用户名或E-mail输入错误!");
		}
		if (StringUtils.isNotEmpty(persistent.getSafeQuestion()) && StringUtils.isNotEmpty(persistent.getSafeAnswer())) {
			if (StringUtils.isEmpty(business.getSafeAnswer())) {
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put(STATUS_PARAMETER_NAME, Status.warn);
				jsonMap.put(MESSAGE_PARAMETER_NAME, "请填写密码保护问题答案!");
				jsonMap.put("safeQuestion", persistent.getSafeQuestion());
				return ajax(jsonMap);
			}
			if (!StringUtils.equalsIgnoreCase(persistent.getSafeAnswer(), business.getSafeAnswer())) {
				return ajax(Status.error, "密码保护答案错误!");
			}
		}
		persistent.setPasswordRecoverKey(businessService.buildPasswordRecoverKey());
		businessService.update(persistent);
		mailService.sendPasswordRecoverMail(persistent);
		return ajax(Status.success, "系统已发送邮件到您的E-mail,请根据邮件提示操作!");
	}
	
	// 密码修改
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "id", message = "会员ID不允许为空!"),
			@RequiredStringValidator(fieldName = "passwordRecoverKey", message = "passwordRecoverKey不允许为空!") 
		}
	)
	@InputConfig(resultName = "error")
	public String passwordModify() throws Exception {
		Business persistent = businessService.get(id);
		if (persistent == null || !StringUtils.equalsIgnoreCase(persistent.getPasswordRecoverKey(), passwordRecoverKey)) {
			addActionError("对不起,此密码找回链接已失效!");
			return ERROR;
		}
		Date passwordRecoverKeyBuildDate = businessService.getPasswordRecoverKeyBuildDate(passwordRecoverKey);
		Date passwordRecoverKeyExpiredDate = DateUtils.addMinutes(passwordRecoverKeyBuildDate, Business.PASSWORD_RECOVER_KEY_PERIOD);
		if (new Date().after(passwordRecoverKeyExpiredDate)) {
			addActionError("对不起,此密码找回链接已过期!");
			return ERROR;
		}
		return "password_modify";
	}
	
	// 密码更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "id", message = "会员ID不允许为空!"),
			@RequiredStringValidator(fieldName = "passwordRecoverKey", message = "passwordRecoverKey不允许为空!"),
			@RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!")
		}
	)
	@InputConfig(resultName = "error")
	public String passwordUpdate() throws Exception {
		Business persistent = businessService.get(id);
		if (persistent == null || !StringUtils.equalsIgnoreCase(persistent.getPasswordRecoverKey(), passwordRecoverKey)) {
			addActionError("对不起,此密码找回链接已失效!");
			return ERROR;
		}
		Date passwordRecoverKeyBuildDate = businessService.getPasswordRecoverKeyBuildDate(passwordRecoverKey);
		Date passwordRecoverKeyExpiredDate = DateUtils.addMinutes(passwordRecoverKeyBuildDate, Business.PASSWORD_RECOVER_KEY_PERIOD);
		if (new Date().after(passwordRecoverKeyExpiredDate)) {
			addActionError("对不起,此密码找回链接已过期!");
			return ERROR;
		}
		persistent.setPassword(StringUtil.md5(business.getPassword()));
		persistent.setPasswordRecoverKey(null);
		businessService.update(persistent);
		
		redirectUrl = getContextPath() + "/";
		addActionMessage("密码修改成功!");
		return SUCCESS;
	}
	// 会员注册页面
	public String register() throws Exception {
		if(null != id && !id.isEmpty()){
			business = businessService.getBusinessByEmail(id);
			return "register";
		}else{
			return REGISTER;
		}
	}
	// 商户信息
	public String info() {
		business = getLoginBusiness();
		return "info";
	}
	// 商户信息
	public String doc() {
		business = getLoginBusiness();
		return "doc";
	}
	// 商户信息
	public String help() {
		business = getLoginBusiness();
		return "help";
	}
	// 账户
	public String edit() {
		business = businessService.get(id);
		return LIST;
	}
	// 设置默认
	public String check() {
		business = getLoginBusiness();
		business = businessService.get(id);
		businessService.update(business);
		return LIST;
	}

	// 账户更新
	@InputConfig(resultName = "error")
	public String update() {
		businessService.update(business);
		return SUCCESS;
	}
	public Business getBusiness() {
		return business;
	}
	public void setBusiness(Business business) {
		this.business = business;
	}
	public String getLoginRedirectUrl() {
		return loginRedirectUrl;
	}
	public void setLoginRedirectUrl(String loginRedirectUrl) {
		this.loginRedirectUrl = loginRedirectUrl;
	}
	public Boolean getIsAgreeAgreement() {
		return isAgreeAgreement;
	}
	public void setIsAgreeAgreement(Boolean isAgreeAgreement) {
		this.isAgreeAgreement = isAgreeAgreement;
	}
	public String getPasswordRecoverKey() {
		return passwordRecoverKey;
	}
	public void setPasswordRecoverKey(String passwordRecoverKey) {
		this.passwordRecoverKey = passwordRecoverKey;
	}

}