<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑个人资料 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"currentPassword": {
				remote: "admin_profile!checkCurrentPassword.action"
			},
			"admin.password": {
				requiredTo: "#currentPassword",
				minlength: 4,
				maxlength: 	 20
			},
			"rePassword": {
				equalTo: "#password"
			},
			"admin.email": {
				required: true,
				email: true
			}
		},
		messages: {
			"currentPassword": {
				remote: "当前密码错误"
			},
			"admin.password": {
				requiredTo: "请填写新密码",
				minlength: "密码必须大于等于4",
				maxlength: 	 "密码必须小于等于20"
			},
			"rePassword": {
				equalTo: "两次密码输入不一致"
			},
			"admin.email": {
				required: "请填写E-mail",
				email: "E-mail格式不正确"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
})
</script>
</head>
<body class="input">
	<div class="bar">
		编辑个人资料
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="admin_profile!update.action" method="post">
			<table class="inputTable">
				<tr>
					<th>
						用户名: 
					</th>
					<td>
						${(admin.username)!}
					</td>
				</tr>
				<tr>
					<th>
						姓&nbsp;&nbsp;&nbsp;名: 
					</th>
					<td>
						${(admin.name)!}
					</td>
				</tr>
				<tr>
					<th>
						部&nbsp;&nbsp;&nbsp;门: 
					</th>
					<td>
						${(admin.department)!}
					</td>
				</tr>
				<tr>
					<th>
						当前密码: 
					</th>
					<td>
						<input type="password" id="currentPassword" name="currentPassword" class="formText"  />
					</td>
				</tr>
				<tr>
					<th>
						新密码: 
					</th>
					<td>
						<input type="password" id="password" name="admin.password" class="formText" title="密码长度只允许在4-20之间" />
					</td>
				</tr>
				<tr>
					<th>
						确认新密码: 
					</th>
					<td>
						<input type="password" name="rePassword" class="formText" />
					</td>
				</tr>
				<tr>
					<th>
						E-mail: 
					</th>
					<td>
						<input type="text" name="admin.email" class="formText" value="${(admin.email)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<span class="warnInfo"><span class="icon">&nbsp;</span>系统提示: 如需修改密码请先填写当前密码,若留空则密码保持不变</span>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>