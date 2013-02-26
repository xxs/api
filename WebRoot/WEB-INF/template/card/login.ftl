<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会员登录<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<#if (article.metaKeywords)! != ""><meta name="keywords" content="${article.metaKeywords}" /></#if>
<#if (article.metaDescription)! != ""><meta name="description" content="${article.metaDescription}" /></#if>

<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>

<#include "/WEB-INF/template/card/head.ftl">
<script type="text/javascript">
	$().ready(function() {
	
		var $loginForm = $("#loginForm");
		var $loginRedirectUrl = $("#loginRedirectUrl");
		var $memberUsername = $("#memberUsername");
		var $memberPassword = $("#memberPassword");
		var $captcha = $("#captcha");
		var $captchaImage = $("#captchaImage");
		var $submitButton = $("#submitButton");
		
		$loginRedirectUrl.val(getParameter("loginRedirectUrl"));
		
		// 刷新验证码图片
		$captchaImage.click( function() {
			$captchaImage.attr("src", xxs.base + "/captcha.jpeg?timestamp" + (new Date()).valueOf());
		});
	
		// 表单验证
		$loginForm.submit( function() {
			if ($.trim($memberUsername.val()) == "") {
				$.dialog({type: "warn", content: "请输入用户名!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if ($.trim($memberPassword.val()) == "") {
				$.dialog({type: "warn", content: "请输入密码!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if ($.trim($captcha.val()) == "") {
				$.dialog({type: "warn", content: "请输入验证码!", modal: true, autoCloseTime: 3000});
				return false;
			}
		});
		
		// 获取参数
		function getParameter(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
			var r = window.location.search.substr(1).match(reg);
			if (r != null) {
				return decodeURIComponent(r[2]);
			} else {
				return null;
			}
		}
	
	})
</script>
</head>
<body class="login">
	<!--标志-->
	<div class="logo">
		<div class="logo_995">
	    	<div class="logo_left"><a href="${base}/" ><img src="/template/card/images/logo.jpg" alt="${(setting.shopName)!}" width="162" height="46" border="0" /></a><span>用户登录</span><div class="shu"></div></div>
		</div>
	    <div class="clear"></div>
	</div>

	<div class="blank"></div>
	<div class="body">
		<div class="loginDetail">
			<div class="top1">会员登录</div>
			<div class="middle">
				<form id="loginForm" action="${base}/card/member!login.action" method="post">
					<input type="hidden" id="loginRedirectUrl" name="loginRedirectUrl" />
					<table>
						<tr>
							<th>用户名</th>
							<td>
								<input type="text" id="memberUsername" name="member.username" class="formText" />
							</td>
						</tr>
						<tr>
							<th>密&nbsp;&nbsp;&nbsp;码</th>
							<td>
								<input type="password" id="memberPassword" name="member.password" class="formText" />
							</td>
						</tr>
						<tr>
							<th>验证码</th>
							<td>
								<input type="text" id="captcha" name="j_captcha" class="formText captcha" />
								<img id="captchaImage" class="captchaImage" src="${base}/captcha.jpeg" alt="换一张" />
							</td>
						</tr>
						<tr>
							<th>&nbsp;</th>
							<td>
								<span class="warnIcon">&nbsp;</span>
								<a href="${base}/card/member!passwordRecover.action">忘记了密码? 点击找回!</a>
							</td>
						</tr>
						<tr>
							<th>&nbsp;</th>
							<td>
								<input type="submit" id="submitButton" class="formButton" value="登 录" hidefocus />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="bottom"></div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/card/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/card/footer.ftl">
</body>
</html>