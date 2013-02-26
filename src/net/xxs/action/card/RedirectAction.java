package net.xxs.action.card;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * 前台Action类 - 站内URL跳转
 */

@ParentPackage("card")
@Results({
	@Result(name = "index", location = "/", type = "redirect"),
	@Result(name = "memberCenter", location = "/card/member_center!index.action", type = "redirect"),
	@Result(name = "urlRedirect", location = "${redirectUrl}", type = "redirect"),
})
public class RedirectAction extends BaseCardAction {

	private static final long serialVersionUID = 5832516589562009249L;
	
	private static final String INDEX_REDIRECT_URL_PARAM_VALUE = "index";// 首页跳转参数值
	private static final String MEMBER_CENTER_REDIRECT_URL_PARAM_VALUE = "memberCenter";// 会员中心跳转参数值
	
	private String redirectUrl;
	
	@Override
	public String execute() throws Exception {
		if (StringUtils.isEmpty(redirectUrl) || redirectUrl.equals(INDEX_REDIRECT_URL_PARAM_VALUE)) {
			return INDEX_REDIRECT_URL_PARAM_VALUE;
		} else if (redirectUrl.equals(MEMBER_CENTER_REDIRECT_URL_PARAM_VALUE)) {
			return MEMBER_CENTER_REDIRECT_URL_PARAM_VALUE;
		} else if (redirectUrl.startsWith("/")) {
			return "urlRedirect";
		} else {
			return INDEX_REDIRECT_URL_PARAM_VALUE;
		}
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}