<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑会员等级 - XXS</title>
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
			"memberRank.name": {
				required: true,
				<#if isAddAction>
					remote: "member_rank!checkName.action"
				<#else>
					remote: "member_rank!checkName.action?oldValue=${memberRank.name?url}"
				</#if>
			},
			"memberRank.lossrate": {
				required: true,
				min: 0
			},
			"memberRank.benefits": {
				required: true,
				min: 0
			},
			"memberRank.score": {
				required: true,
				digits: true
			}
		},
		messages: {
			"memberRank.name": {
				required: "请填写等级名称",
				remote: "等级名称已存在"
			},
			"memberRank.lossrate": {
				required: "请填写手续费率",
				min: "手续费率必须为零或正数"
			},
			"memberRank.benefits": {
				required: "请填写提成了率",
				min: "提成率必须为零或正数"
			},
			"memberRank.score": {
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
		<#if isAddAction>添加会员等级<#else>编辑会员等级</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>member_rank!save.action<#else>member_rank!update.action</#if>" method="post">
			<table class="inputTable">
				<input type="hidden" name="id" value="${id}" />
				<tr>
					<th>
						等级名称: 
					</th>
					<td>
						<input type="text" name="memberRank.name" class="formText" value="${(memberRank.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						手续费率: 
					</th>
					<td>
						<input type="text" name="memberRank.lossrate" class="formText" value="${(memberRank.lossrate)!"100"}" title="单位: %, 若输入90,表示该会员等级以充值卡价格的90%进行销售" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						提成率: 
					</th>
					<td>
						<input type="text" name="memberRank.benefits" class="formText" value="${(memberRank.benefits)!"100"}" title="单位: %, 若输入90,表示该会员等级以充值卡价格的90%进行销售" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						所需积分: 
					</th>
					<td>
						<input type="text" name="memberRank.score" class="formText" value="${(memberRank.score)!}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<label>
							<@checkbox name="memberRank.isDefault" value="${(memberRank.isDefault)!false}" />默认
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