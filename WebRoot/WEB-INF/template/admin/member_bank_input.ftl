<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑会员银行卡 - XXS</title>
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
			"memberBank.name": {
				required: true,
				<#if isAddAction>
					remote: "member_bank!checkName.action"
				<#else>
					remote: "member_bank!checkName.action?oldValue=${memberBank.name?url}"
				</#if>
			},
			"memberBank.preferentialScale": {
				required: true,
				min: 0
			},
			"memberBank.score": {
				required: true,
				digits: true
			}
		},
		messages: {
			"memberBank.name": {
				required: "请填写银行卡名称",
				remote: "银行卡名称已存在"
			},
			"memberBank.preferentialScale": {
				required: "请填写优惠百分比",
				min: "优惠百分比必须为零或正数"
			},
			"memberBank.score": {
				required: "请填写所需积分",
				digits: "所需积分必须为零或正整数"
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
		<#if isAddAction>添加会员银行卡<#else>编辑会员银行卡</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>member_bank!save.action<#else>member_bank!update.action</#if>" method="post">
			<table class="inputTable">
				<input type="hidden" name="id" value="${id}" />
				<tr>
					<th>
						所属会员名: 
					</th>
					<td>
						<#if isAddAction>
							<input type="text" name="memberBank.member.username" class="formText" value="${(memberBank.member.username)!}" title="所属会员不能为空" />
							<label class="requireField">*</label>
						<#else>
							<input type="hidden" name="memberBank.member.username" value="${(memberBank.member.username)!}" />
							${(memberBank.member.username)!}
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						银行账号: 
					</th>
					<td>
						<input type="text" name="memberBank.banknum" class="formText" value="${(memberBank.banknum)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						开户姓名: 
					</th>
					<td>
						<input type="text" name="memberBank.openname" class="formText" value="${(memberBank.openname)!}" title="" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						所属银行: 
					</th>
					<td>
						<input type="text" name="memberBank.bankname" class="formText" value="${(memberBank.bankname)!}" title="" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						银行归属地: 
					</th>
					<td>
						<input type="text" name="memberBank.bankcity" class="formText" value="${(memberBank.bankcity)!}" title="" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						所属支行: 
					</th>
					<td>
						<input type="text" name="memberBank.bankdetail" class="formText" value="${(memberBank.bankdetail)!}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<label>
							<@checkbox name="memberBank.isDefault" value="${(memberBank.isDefault)!false}" />  设置为默认
						</label>
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