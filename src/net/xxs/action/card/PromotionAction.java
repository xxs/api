package net.xxs.action.card;

import net.xxs.entity.Member;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 前台Action类 - 推广链接
 */

@ParentPackage("card")
@InterceptorRefs({
	@InterceptorRef(value = "memberVerifyInterceptor"),
	@InterceptorRef(value = "cardStack")
})
public class PromotionAction extends BaseCardAction {

	private static final long serialVersionUID = -26661865759574779L;
	
	private Member member;
	
	// 推广链接页面
	public String execute() throws Exception {
		member = getLoginMember();
		return "link";
	}
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}