package net.xxs.action.card;

import javax.annotation.Resource;

import net.xxs.entity.Business;
import net.xxs.service.BusinessService;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

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
	
	@Resource(name = "businessServiceImpl")
	private BusinessService businessService;
	
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



}