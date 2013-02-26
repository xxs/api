package net.xxs.listener;

import java.util.Date;

import javax.annotation.Resource;

import net.xxs.bean.Setting;
import net.xxs.entity.Admin;
import net.xxs.service.AdminService;
import net.xxs.util.SettingUtil;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.Authentication;
import org.springframework.security.event.authentication.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.event.authentication.AuthenticationSuccessEvent;
import org.springframework.security.ui.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * 监听器 - 后台登录成功、登录失败处理
 */

@Component("adminSecurityListener")
public class AdminSecurityListener implements ApplicationListener {

	@Resource(name = "adminServiceImpl")
	private AdminService adminService;

	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		// 登录成功：记录登录IP、清除登录失败次数
		if (applicationEvent instanceof AuthenticationSuccessEvent) {
			AuthenticationSuccessEvent authenticationSuccessEvent = (AuthenticationSuccessEvent) applicationEvent;
			Authentication authentication = (Authentication) authenticationSuccessEvent.getSource();
			String loginIp = ((WebAuthenticationDetails)authentication.getDetails()).getRemoteAddress();
			Admin admin = (Admin) authentication.getPrincipal();
			admin.setLoginIp(loginIp);
			admin.setLoginDate(new Date());
			Setting setting = SettingUtil.getSetting();
			if (setting.getIsLoginFailureLock() == false) {
				return;
			}
			admin.setLoginFailureCount(0);
			adminService.update(admin);
		}

		// 登录失败：增加登录失败次数
		if (applicationEvent instanceof AuthenticationFailureBadCredentialsEvent) {
			AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent = (AuthenticationFailureBadCredentialsEvent) applicationEvent;
			Authentication authentication = (Authentication) authenticationFailureBadCredentialsEvent.getSource();
			String loginUsername = authentication.getName();
			Setting setting = SettingUtil.getSetting();
			if (setting.getIsLoginFailureLock() == false) {
				return;
			}
			Admin admin = adminService.getAdminByUsername(loginUsername);
			if (admin != null) {
				int loginFailureCount = admin.getLoginFailureCount() + 1;
				if (loginFailureCount >= setting.getLoginFailureLockCount()) {
					admin.setIsAccountLocked(true);
					admin.setLockedDate(new Date());
				}
				admin.setLoginFailureCount(loginFailureCount);
				adminService.update(admin);
			}
		}
	}

}