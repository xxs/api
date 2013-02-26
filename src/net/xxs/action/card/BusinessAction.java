package net.xxs.action.card;

import javax.annotation.Resource;

import net.xxs.entity.Member;
import net.xxs.entity.MemberBusiness;
import net.xxs.service.MemberBusinessService;

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
	
	private Member member;
	private MemberBusiness memberBusiness;
	
	@Resource(name = "memberBusinessServiceImpl")
	private MemberBusinessService memberBusinessService;
	
	// 商户信息
	public String info() {
		member = getLoginMember();
		return "info";
	}
	// 商户信息
	public String doc() {
		member = getLoginMember();
		return "doc";
	}
	// 商户信息
	public String help() {
		member = getLoginMember();
		return "help";
	}
	// 账户
	public String edit() {
		memberBusiness = memberBusinessService.get(id);
		return LIST;
	}
	// 设置默认
	public String check() {
		member = getLoginMember();
		memberBusiness = memberBusinessService.get(id);
		memberBusinessService.update(memberBusiness);
		return LIST;
	}

	// 账户更新
	@InputConfig(resultName = "error")
	public String update() {
		memberBusinessService.update(memberBusiness);
		return SUCCESS;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	public MemberBusiness getMemberBusiness() {
		return memberBusiness;
	}
	public void setMemberBusiness(MemberBusiness memberBusiness) {
		this.memberBusiness = memberBusiness;
	}


}