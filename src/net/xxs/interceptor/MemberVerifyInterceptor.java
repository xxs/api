package net.xxs.interceptor;

import javax.servlet.http.HttpServletRequest;

import net.xxs.entity.Member;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.ognl.OgnlValueStack;

/**
 * 拦截器 - 判断会员是否登录
 */

public class MemberVerifyInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = -86246303854807787L;
	
	@Override
	public String doIntercept(ActionInvocation actionInvocation) throws Exception {
		String loginMemberId = (String) actionInvocation.getInvocationContext().getSession().get(Member.MEMBER_ID_SESSION_NAME);
		if (loginMemberId == null) {
			HttpServletRequest request = ServletActionContext.getRequest();
			String loginRedirectUrl = request.getServletPath();
			String queryString = request.getQueryString();
			if (StringUtils.isNotEmpty(queryString)) {
				loginRedirectUrl += "?" + queryString;
			}
			
			OgnlValueStack ognlValueStack = (OgnlValueStack)request.getAttribute("struts.valueStack");
			ognlValueStack.set("loginRedirectUrl", loginRedirectUrl);
			return "login";
		}
		return actionInvocation.invoke();
	}

}