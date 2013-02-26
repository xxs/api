<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.security.BadCredentialsException"%>
<%@page import="net.xxs.util.SpringUtil"%>
<%@page import="net.xxs.service.AdminService"%>
<%@page import="net.xxs.entity.Admin"%>
<%@page import="net.xxs.util.SettingUtil"%>
<%@page import="net.xxs.bean.Setting"%>
<%@page import="org.springframework.security.DisabledException"%>
<%@page import="org.springframework.security.LockedException"%>
<%@page import="org.springframework.security.AccountExpiredException"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%
	response.setHeader("progma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);

	final String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";

	String base = request.getContextPath();
	ApplicationContext applicationContext = SpringUtil.getApplicationContext();
	if (applicationContext == null) {
%>
	<p>系统出现异常</p>
<%
	return;
}

AdminService adminService = (AdminService) SpringUtil.getBean("adminServiceImpl");
Setting setting = SettingUtil.getSetting();
String adminLoginProcessingUrl = setting.getAdminLoginProcessingUrl();
String message = null;
String username = null;

String error = StringUtils.trim(request.getParameter("error"));
if (StringUtils.equalsIgnoreCase(error, "captcha")) {
	message = "验证码错误,请重新输入!";
} else {
	Exception springSecurityLastException = (Exception) session.getAttribute(SPRING_SECURITY_LAST_EXCEPTION);
	if (springSecurityLastException != null) {
		if (springSecurityLastException instanceof BadCredentialsException) {
			username = ((String) session.getAttribute("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
			Admin admin = adminService.getAdminByUsername(username);
			if (admin != null) {
				int loginFailureLockCount = setting.getLoginFailureLockCount();
				int loginFailureCount = admin.getLoginFailureCount();
				if (setting.getIsLoginFailureLock() && loginFailureLockCount - loginFailureCount <= 3) {
					message = "若连续" + loginFailureLockCount + "次密码输入错误,您的账号将被锁定!";
				} else {
					message = "您的用户名或密码错误!";
				}
			} else {
				message = "您的用户名或密码错误!";
			}
		} else if (springSecurityLastException instanceof DisabledException) {
			message = "您的账号已被禁用,无法登录!";
		} else if (springSecurityLastException instanceof LockedException) {
			message = "您的账号已被锁定,无法登录!";
		} else if (springSecurityLastException instanceof AccountExpiredException) {
			message = "您的账号已过期,无法登录!";
		} else {
			message = "出现未知错误,无法登录!";
		}
		session.removeAttribute(SPRING_SECURITY_LAST_EXCEPTION);
	}
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理中心 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="<%=base%>/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=base%>/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=base%>/template/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=base%>/template/admin/js/base.js"></script>
<script type="text/javascript" src="<%=base%>/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $loginForm = $("#loginForm");
	var $username = $("#username");
	var $password = $("#password");
	var $captcha = $("#captcha");
	var $captchaImage = $("#captchaImage");
	var $isRememberUsername = $("#isRememberUsername");

	// 判断"记住用户名"功能是否默认选中,并自动填充登录用户名
	if(getCookie("adminUsername") != null) {
		$isRememberUsername.attr("checked", true);
		$username.val(getCookie("adminUsername"));
		$password.focus();
	} else {
		$isRememberUsername.attr("checked", false);
		$username.focus();
	}

	// 提交表单验证,记住登录用户名
	$loginForm.submit( function() {
		if ($username.val() == "") {
			$.dialog({type: "warn", content: "请输入您的用户名!", modal: true, autoCloseTime: 3000});
			return false;
		}
		if ($password.val() == "") {
			$.dialog({type: "warn", content: "请输入您的密码!", modal: true, autoCloseTime: 3000});
			return false;
		}
		if ($captcha.val() == "") {
			$.dialog({type: "warn", content: "请输入您的验证码!", modal: true, autoCloseTime: 3000});
			return false;
		}
		
		if ($isRememberUsername.attr("checked") == true) {
			var expires = new Date();
			expires.setTime(expires.getTime() + 1000 * 60 * 60 * 24 * 7);
			setCookie("adminUsername", $username.val(), expires);
		} else {
			removeCookie("adminUsername");
		}
		
	});

	// 刷新验证码
	$captchaImage.click( function() {
		var timestamp = (new Date()).valueOf();
		var imageSrc = $captchaImage.attr("src");
		if(imageSrc.indexOf("?") >= 0) {
			imageSrc = imageSrc.substring(0, imageSrc.indexOf("?"));
		}
		imageSrc = imageSrc + "?timestamp=" + timestamp;
		$captchaImage.attr("src", imageSrc);
	});

	<%if (message != null) {%>
		$.dialog({type: "error", content: "<%=message%>", modal: true, autoCloseTime: 3000});
	<%}%>

});
</script>
</head>
<body class="login">
	<script type="text/javascript">

		// 登录页面若在框架内，则跳出框架
		if (self != top) {
			top.location = self.location;
		};

	</script>
	<div class="blank"></div>
	<div class="blank"></div>
	<div class="blank"></div>
	<div class="body">
		<form id="loginForm" action="<%=base%><%=adminLoginProcessingUrl%>" method="post">
            <table class="loginTable">
            	<tr>
            		<td rowspan="3">
            			<img src="<%=base%>/template/admin/images/login_logo.gif" alt="XXS管理中心" />
            		</td>
                    <th>
                    	用户名:
                    </th>
					<td>
                    	<input type="text" id="username" name="j_username" class="formText" />
                    </td>
                </tr>
                <tr>
					<th>
						密&nbsp;&nbsp;&nbsp;码:
					</th>
                    <td>
                    	<input type="password" id="password" name="j_password" class="formText" />
                    </td>
                </tr>
                <tr>
                	<th>
                		验证码:
                	</th>
                    <td>
                    	<input type="text" id="captcha" name="j_captcha" class="formText captcha" />
                   		<img id="captchaImage" class="captchaImage" src="<%=base%>/captcha.jpeg" alt="换一张" />
                    </td>
                </tr>
                <tr>
                	<td>
                		&nbsp;
                	</td>
                	<th>
                		&nbsp;
                	</th>
                    <td>
                    	<label>
                    		<input type="checkbox" id="isRememberUsername" />&nbsp;记住用户名
                    	</label>
                    </td>
                </tr>
                <tr>
                	<td>
                		&nbsp;
                	</td>
                	<th>
                		&nbsp;
                	</th>
                    <td>
                        <input type="button" class="homeButton" value="" onclick="window.open('<%=base%>/')" hidefocus /><input type="submit" class="submitButton" value="登 录" hidefocus />
                    </td>
                </tr>
            </table>
        </form>
	</div>
</body>
</html>