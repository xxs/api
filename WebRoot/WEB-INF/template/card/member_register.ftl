<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会员注册<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<#if (article.metaKeywords)! != ""><meta name="keywords" content="${article.metaKeywords}" /></#if>
<#if (article.metaDescription)! != ""><meta name="description" content="${article.metaDescription}" /></#if>

<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>

<#include "/WEB-INF/template/card/head.ftl">
<script type="text/javascript">
	$().ready(function() {
			var $registerForm = $("#registerForm");
			var $registerMemberUsername = $("#registerMemberUsername");
			var $registerMemberPassword = $("#registerMemberPassword");
			var $registerReMemberPassword = $("#registerReMemberPassword");
			var $registerMemberEmail = $("#registerMemberEmail");
			var $registerMemberReferrer = $("#registerMemberReferrer");
			var $registerCaptcha = $("#registerCaptcha");
			var $registerCaptchaImage = $("#registerCaptchaImage");
			var $registerIsAgreeAgreement = $("#registerIsAgreeAgreement");
			var $registerShowAgreement = $("#registerShowAgreement");
			var $submitButton = $("#submitButton");
			
			// 刷新验证码图片
			$registerCaptchaImage.click( function() {
				$registerCaptchaImage.attr("src", xxs.base + "/captcha.jpeg?timestamp" + (new Date()).valueOf());
			});
			
			$registerShowAgreement.click( function() {
				$.showAgreement();
			});
			
			// 表单验证
			$submitButton.click( function() {
				if ($.trim($registerMemberUsername.val()) == "") {
					$registerMemberUsername.focus();
					$.dialog({type: "warn", content: "请输入用户名!"});
					return false;
				}
				if (!/^[\u0391-\uFFE5\w]+$/.test($registerMemberUsername.val())) {
					$registerMemberUsername.focus();
					$.dialog({type: "warn", content: "用户名只允许包含中文、英文、数字和下划线!"});
					return false;
				}
				if ($.trim($registerMemberUsername.val()).length < 2 || $.trim($registerMemberUsername.val()).length > 20) {
					$registerMemberUsername.focus();
					$.dialog({type: "warn", content: "用户名长度只允许在2-20之间!"});
					return false;
				}
				if ($.trim($registerMemberPassword.val()) == "") {
					$registerMemberPassword.focus();
					$.dialog({type: "warn", content: "请输入密码!"});
					return false;
				}
				if ($.trim($registerMemberPassword.val()).length < 4 || $.trim($registerMemberPassword.val()).length > 20) {
					$registerMemberPassword.focus();
					$.dialog({type: "warn", content: "密码长度只允许在4-20之间!"});
					return false;
				}
				if ($.trim($registerReMemberPassword.val()) == "") {
					$registerReMemberPassword.focus();
					$.dialog({type: "warn", content: "请输入重复密码!"});
					return false;
				}
				if ($.trim($registerReMemberPassword.val()) != $.trim($registerMemberPassword.val())) {
					$registerReMemberPassword.focus();
					$.dialog({type: "warn", content: "两次密码输入不相同!"});
					return false;
				}
				if ($.trim($registerMemberEmail.val()) == "") {
					$registerMemberEmail.focus();
					$.dialog({type: "warn", content: "请输入E-mail!"});
					return false;
				}
				if (!/^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test($registerMemberEmail.val())) {
					$registerMemberEmail.focus();
					$.dialog({type: "warn", content: "E-mail格式错误!"});
					return false;
				}
				if ($.trim($registerMemberReferrer.val()) == "") {
					$registerMemberReferrer.focus();
					$.dialog({type: "warn", content: "请输入推荐人!"});
					return false;
				}
				if ($.trim($registerCaptcha.val()) == "") {
					$registerCaptcha.focus();
					$.dialog({type: "warn", content: "请输入验证码!"});
					return false;
				}
				if (!$registerIsAgreeAgreement.attr("checked")) {
					$registerIsAgreeAgreement.focus();
					$.dialog({type: "warn", content: "注册前必须阅读并同意《注册协议》!"});
					return false;
				}
				$.ajax({
						url: xxs.base + "/card/member!checkUsername.action",
						data: {"member.username": $registerMemberUsername.val()},
						type: "POST",
						cache: false,
						success: function(data) {
							if (data == "true") {
								$.ajax({
									url: xxs.base + "/card/member!checkReferrer.action",
									data: {"member.referrer": $registerMemberReferrer.val()},
									type: "POST",
									cache: false,
									success: function(data) {
										if (data == "true") {
											$.ajax({
												url: xxs.base + "/card/member!ajaxRegister.action",
												data: $registerForm.serialize(),
												type: "POST",
												dataType: "json",
												cache: false,
												success: function(data) {
													if (data.status == "success") {
														$.dialog({type: data.status, content: data.message});
														window.location.href ="${base}/card/member_center!index.action";
													}else{
														$.dialog({type: data.status, content: data.message});
														return false;
													}
												}
											});
											return false;
										} else {
											$registerMemberUsername.focus();
											$.dialog({type: "warn", content: "推荐人不存在,请重新输入!"});
											$registerCaptchaImage.attr("src", xxs.base + "/captcha.jpeg?timestamp" + (new Date()).valueOf());
											return false;
										}
									}
								});
								//return false;
							} else {
								$registerMemberUsername.focus();
								$.dialog({type: "warn", content: "用户名已存在,请重新输入!"});
								$registerCaptchaImage.attr("src", xxs.base + "/captcha.jpeg?timestamp" + (new Date()).valueOf());
								return false;
							}
						}
					});
			});
		
		$.showAgreement = function() {
			if ($("#agreement").length == 0) {
				var agreementHtml = '<div id="agreementContent" class="agreementContent"></div>';
				
				function agreeAgreement() {
					var $registerIsAgreeAgreement = $("#registerIsAgreeAgreement");
					$registerIsAgreeAgreement.attr("checked", true);
				}
			
				$.dialog({title: "注册协议", content: agreementHtml, ok: "同 意", cancel: "取 消", id: "agreement", className: "agreement", width: 520, okCallback: agreeAgreement});
				
				var $agreementContent = $("#agreementContent");
				
				$.ajax({
					url: xxs.base + "/html/register_agreement.html",
					datatype: "html",
					beforeSend: function(data) {
						$agreementContent.html('<span class="loadingIcon">&nbsp;</span> 加载中...');
					},
					success: function(data) {
						$agreementContent.html(data);
					}
				});
			}
		}
});
</script>
</head>
<body class="login">
	<!--标志-->
	<div class="logo">
		<div class="logo_995">
	    	<div class="logo_left"><a href="${base}/" ><img src="/template/card/images/logo.jpg" alt="${(setting.shopName)!}" width="162" height="46" border="0" /></a><span>用户注册</span><div class="shu"></div></div>
		</div>
	    <div class="clear"></div>
	</div>

	<div class="blank"></div>
	<div class="body">
		<div class="loginDetail">
			<div class="top1">会员注册</div>
			<div class="middle">
				<form id="registerForm" autocomplete="off" action="${base}/card/member!ajaxRegister.action">
					<table>
						<tr>
							<th>用户名: </th>
							<td>
								<input type="text" id="registerMemberUsername" name="member.username" class="formText" title="用户名只允许包含中文、英文、数字和下划线!" />
							</td>
						</tr>
						<tr>
							<th>密&nbsp;&nbsp;&nbsp;码: </th>
							<td>
								<input type="password" id="registerMemberPassword" name="member.password" class="formText" />
							</td>
						</tr>
						<tr>
							<th>重复密码: </th>
							<td>
								<input type="password" id="registerReMemberPassword" name="reMemberPassword" class="formText" title="密码长度只允许在4-20之间!" />
							</td>
						</tr>
						<tr>
							<th>E-mail: </th>
							<td>
								<input type="text" id="registerMemberEmail" name="member.email" class="formText" />
							</td>
						</tr>
						<tr>
							<th>推荐人: </th>
							<td>
								<input type="text" id="registerMemberReferrer" name="member.referrer" value="${(member.username)!}" class="formText" title="填写后不能修改!"/>
							</td>
						</tr>
						<tr>
							<th>验证码: </th>
							<td>
								<input type="text" id="registerCaptcha" name="j_captcha" class="formText captcha" />
								<img id="registerCaptchaImage" class="captchaImage" src="${base}/captcha.jpeg" alt="换一张" />
							</td>
						</tr>
						<tr>
							<th>&nbsp;</th>
							<td>
								<label>
									<input type="checkbox" id="registerIsAgreeAgreement" name="isAgreeAgreement" value="true" checked />已阅读并同意
									<a id="registerShowAgreement" class="showAgreement" href="javascript: void(0);" title="查看会员注册协议">《会员注册协议》</a>
								</label>
							</td>
						</tr>
						<tr>
							<th>&nbsp;</th>
							<td>
								<input type="button" id="submitButton" class="formButton" value="注  册" hidefocus />
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