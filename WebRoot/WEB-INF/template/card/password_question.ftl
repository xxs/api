<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>修改密保<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<#include "/WEB-INF/template/card/member_head.ftl">
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $questionForm = $("#questionForm");

	// 表单验证
	$questionForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"oldPassword": {
				requiredTo: "#password"
			},
			"member.password": {
				minlength: 4,
				maxlength: 20,
				requiredTo: "#oldPassword"
			},
			"rePassword": {
				equalTo: "#password"
			},
			"member.safeQuestion": {
				requiredTo: "#memberSafeAnswer"
			},
			"member.safeAnswer": {
				requiredTo: "#memberSafeQuestion"
			}
		},
		messages: {
			"oldPassword": {
				requiredTo: "请填写旧密码"
			},
			"member.password": {
				minlength: "密码长度必须大于等于4",
				maxlength: "密码长度必须小于等于20",
				requiredTo: "请填写新密码"
			},
			"rePassword": {
				equalTo: "两次密码输入不一致"
			},
			"member.safeQuestion": {
				requiredTo: "请填写安全问题"
			},
			"member.safeAnswer": {
				requiredTo: "请填写安全答案"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});

});
</script>
</head>
<body class="memberCenter">
	<#include "/WEB-INF/template/card/member_header.ftl">
	<div class="content">
	<div class="contentLeft">
		<#include "/WEB-INF/template/card/menu_center.ftl">
	</div>
	<div class="contentRight">
		<div class="katong">
			<div class="fangz"><#if member.safeQuestion == "">设置密保<#else>修改密保</#if> </div>
			<div class="red">注意：问题或答案最多可输入15个字。请保证您的密码问题不出现泄密，以保障您的隐私。 </div>
			<div class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果设置答案的时候有带符号、空格等的，验证时也都必须输入完整</div>
			<div class="hei">示例：问题：我最喜欢的收卡网站？</div>
			<div class="hei">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;答案：名臣福利</div>
			<div class="memberCenter">
				<form id="questionForm" action="password!updateSafeQuestion.action" method="post">
				<#if member.safeQuestion == "">
					<table class="inputTable tabContent">
						<tr>
							<th>
								安全问题: 
							</th>
							<td>
								<input type="text" id="memberSafeQuestion" name="member.safeQuestion" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								安全答案: 
							</th>
							<td>
								<input type="text" id="memberSafeAnswer" name="member.safeAnswer" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								&nbsp;
							</th>
							<td>
								<span class="warnInfo"><span class="icon">&nbsp;</span>系统提示: 若安全问题留空则保持不变</span>
							</td>
						</tr>
						<tr>
							<th>
								&nbsp;
							</th>
							<td>
								<input type="submit"  class="formButton" value="提 交" hidefocus />
							</td>
						</tr>
					</table>
					<#else>
					<table class="inputTable tabContent">
						<tr>
							<th>
								旧密保问题: 
							</th>
							<td>
								${member.safeQuestion}
							</td>
						</tr>
						<tr>
							<th>
								旧密保答案: 
							</th>
							<td>
								<input type="text" id="oldSafeAnswer" name="oldSafeAnswer" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								新密保问题: 
							</th>
							<td>
								<input type="text" id="memberSafeQuestion" name="member.safeQuestion" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								新密保答案: 
							</th>
							<td>
								<input type="text" id="memberSafeAnswer" name="member.safeAnswer" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								&nbsp;
							</th>
							<td>
								<input type="submit"  class="formButton" value="提 交" hidefocus />
							</td>
						</tr>
					</table>
					</#if>
					</form>
			</div>
		</div>
	</div>
</div>
<div class="clear"></div>
	<#include "/WEB-INF/template/card/member_footer.ftl">
</body>
</html>