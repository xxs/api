package net.xxs.action.card;

import javax.annotation.Resource;

import net.xxs.entity.Business;
import net.xxs.service.BusinessService;
import net.xxs.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
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
public class PasswordAction extends BaseCardAction {

	private static final long serialVersionUID = 6887411316513565001L;
	
	private Business business;
	private String oldPassword;
	private String oldSafeAnswer;
	
	@Resource(name = "businessServiceImpl")
	private BusinessService businessService;
	
	// 密码修改
	public String edit() {
		business = getLoginBusiness();
		return INPUT;
	}
	// 密码密保
	public String safe() {
		business = getLoginBusiness();
		return "question";
	}
	// 提现密码
	public String withdrawPwd() {
		business = getLoginBusiness();
		return "withdraw_pwd";
	}

	// 密码更新
	@Validations(
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "新密码长度必须在${minLength}到${maxLength}之间!") 
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		Business persistent = getLoginBusiness();
		if (StringUtils.isNotEmpty(oldPassword) && StringUtils.isNotEmpty(business.getPassword())) {
			String oldPasswordMd5 = StringUtil.md5(oldPassword);
			if (!StringUtils.equals(persistent.getPassword(), oldPasswordMd5)) {
				addActionError("旧密码不正确!");
				return ERROR;
			}
			String newPasswordMd5 = StringUtil.md5(business.getPassword());
			persistent.setPassword(newPasswordMd5);
		}
		if (StringUtils.isNotEmpty(business.getSafeQuestion()) && StringUtils.isNotEmpty(business.getSafeAnswer())) {
			persistent.setSafeQuestion(business.getSafeQuestion());
			persistent.setSafeAnswer(business.getSafeAnswer());
		}
		businessService.update(persistent);
		return SUCCESS;
	}
	// 密保更新
	@Validations(
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "新密码长度必须在${minLength}到${maxLength}之间!") 
		}
	)
	@InputConfig(resultName = "error")
	public String updateSafeQuestion() {
		Business persistent = getLoginBusiness();
//				addActionError("旧密码不正确!");
//				return ERROR;
//			}
//			String newPasswordMd5 = StringUtil.md5(member.getPassword());
//			persistent.setPassword(newPasswordMd5);
//		}
		if(StringUtils.isNotEmpty(persistent.getSafeQuestion())){
			if(businessService.verifySafeQuestion(persistent,business.getSafeQuestion(), business.getSafeAnswer())){
				if (StringUtils.isNotEmpty(business.getSafeQuestion()) && StringUtils.isNotEmpty(business.getSafeAnswer())) {
					persistent.setSafeQuestion(business.getSafeQuestion());
					persistent.setSafeAnswer(business.getSafeAnswer());
				}
			}else{
				addActionError("旧密保答案验证失败!");
				return ERROR;
			}
		}
		businessService.update(persistent);
		return SUCCESS;
	}


	public Business getBusiness() {
		return business;
	}
	public void setBusiness(Business business) {
		this.business = business;
	}
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getOldSafeAnswer() {
		return oldSafeAnswer;
	}
	public void setOldSafeAnswer(String oldSafeAnswer) {
		this.oldSafeAnswer = oldSafeAnswer;
	}

}