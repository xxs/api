package net.xxs.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.xxs.action.admin.BaseAdminAction;
import net.xxs.action.card.BaseCardAction;
import net.xxs.bean.LogConfig;
import net.xxs.bean.MemberLogConfig;
import net.xxs.entity.Log;
import net.xxs.entity.Member;
import net.xxs.entity.MemberLog;
import net.xxs.service.AdminService;
import net.xxs.service.LogService;
import net.xxs.service.MemberLogService;
import net.xxs.service.MemberService;
import net.xxs.util.LogConfigUtil;
import net.xxs.util.MemberLogConfigUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 拦截器 - 管理日志
 */

public class LogInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 276741467699160227L;

	@Resource(name = "logServiceImpl")
	private LogService logService;
	@Resource(name = "memberLogServiceImpl")
	private MemberLogService memberLogService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		String result = invocation.invoke();
		
		Object action = invocation.getAction();
		String actionClass = action.getClass().getName();
		String actionMethod = invocation.getProxy().getMethod();
		
		if (action instanceof BaseAdminAction) {
			if (!StringUtils.equals(result, BaseAdminAction.ERROR)) {
				List<LogConfig> allLogConfigList = LogConfigUtil.getAllLogConfigList();
				if (allLogConfigList != null) {
					for (LogConfig logConfig : allLogConfigList) {
						if (StringUtils.equals(logConfig.getActionClass(), actionClass) && StringUtils.equals(logConfig.getActionMethod(), actionMethod)) {
							BaseAdminAction baseAdminAction = (BaseAdminAction) action;
							HttpServletRequest request= ServletActionContext.getRequest();
							
							String logInfo = baseAdminAction.getLogInfo();
							String operator = adminService.getLoginAdmin().getUsername();
							if(operator == null) {
								operator = "未知";
							}
							String ip = request.getRemoteAddr();
							String operation = logConfig.getOperation();
							System.out.println("执行了："+operator);
							Log log = new Log();
							log.setOperation(operation);
							log.setOperator(operator);
							log.setActionClass(actionClass);
							log.setActionMethod(actionMethod);
							log.setIp(ip);
							log.setInfo(logInfo);
							logService.save(log);
							break;
						}
					}
				}
			}
		}
		if (action instanceof BaseCardAction) {
			if (!StringUtils.equals(result, BaseCardAction.ERROR)) {
				List<MemberLogConfig> allMemberLogConfigList = MemberLogConfigUtil.getAllMemberLogConfigList();
				if (allMemberLogConfigList != null) {
					for (MemberLogConfig memberLogConfig : allMemberLogConfigList) {
						if (StringUtils.equals(memberLogConfig.getActionClass(), actionClass) && StringUtils.equals(memberLogConfig.getActionMethod(), actionMethod)) {
							BaseCardAction baseCardAction = (BaseCardAction) action;
							HttpServletRequest request= ServletActionContext.getRequest();
							String logInfo = baseCardAction.getLogInfo();
							String memberId = (String)request.getSession().getAttribute(Member.MEMBER_ID_SESSION_NAME);
							if (StringUtils.isEmpty(memberId)) {
								return null;
							}
							Member loginMember = memberService.get(memberId);
							
							String operator = loginMember.getUsername();
							if(operator == null) {
								operator = "未知";
							}
							String ip = request.getRemoteAddr();
							String operation = memberLogConfig.getOperation();
							MemberLog memberLog = new MemberLog();
							memberLog.setOperation(operation);
							memberLog.setOperator(operator);
							memberLog.setActionClass(actionClass);
							memberLog.setActionMethod(actionMethod);
							memberLog.setIp(ip);
							memberLog.setInfo(logInfo);
							memberLogService.save(memberLog);
							break;
						}
					}
				}
			}
		}
		return null;
	}

}