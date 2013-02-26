<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑会员商户 - XXS</title>
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
			"memberBusiness.name": {
				required: true,
				<#if isAddAction>
					remote: "member_business!checkName.action"
				<#else>
					remote: "member_business!checkName.action?oldValue=${memberBusiness.name?url}"
				</#if>
			},
			"memberBusiness.preferentialScale": {
				required: true,
				min: 0
			},
			"memberBusiness.score": {
				required: true,
				digits: true
			}
		},
		messages: {
			"memberBusiness.name": {
				required: "请填写商户名称",
				remote: "商户名称已存在"
			},
			"memberBusiness.preferentialScale": {
				required: "请填写优惠百分比",
				min: "优惠百分比必须为零或正数"
			},
			"memberBusiness.score": {
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
		<#if isAddAction>添加会员商户<#else>编辑会员商户</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>member_business!save.action<#else>member_business!update.action</#if>" method="post">
			<table class="inputTable">
				<input type="hidden" name="id" value="${id}" />
				<tr>
					<th>
						所属会员名: 
					</th>
					<td>
						<#if isAddAction>
							<input type="text" name="memberBusiness.member.username" class="formText" value="${(memberBusiness.member.username)!}" title="所属会员不能为空" />
							<label class="requireField">*</label>
						<#else>
							<input type="hidden" name="memberBusiness.member.username" value="${(memberBusiness.member.username)!}" />
							${(memberBusiness.member.username)!}
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						商户类型: 
					</th>
					<td>
						<#list businessTypeList as businessType>
							<input type="radio" name="memberBusiness.businessType" value="${businessType}" />${action.getText("BusinessType." + businessType)}
						</#list>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						审核状态: 
					</th>
					<td>
						<#list resultTypeList as resultType>
							<input type="radio" name="memberBusiness.resultType" value="${resultType}" />${action.getText("ResultType." + resultType)}
						</#list>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						商户名称: 
					</th>
					<td>
						<input type="text" name="memberBusiness.businessName" class="formText" value="${(memberBusiness.businessName)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						真实姓名: 
					</th>
					<td>
						<input type="text" name="memberBusiness.realName" class="formText" value="${(memberBusiness.realName)!}" title="" />
						<label class="requireField">*</label>
					</td>
				<tr>
					<th>
						身份证号（营业执照）: 
					</th>
					<td>
						<input type="text" name="memberBusiness.businessNumber" class="formText" value="${(memberBusiness.businessNumber)!}" title="" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						商户地区: 
					</th>
					<td>
						<input type="text" name="memberBusiness.city" class="formText" value="${(memberBusiness.city)!}" title="" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						经营范围: 
					</th>
					<td>
						<input type="text" name="memberBusiness.scope" class="formText" value="${(memberBusiness.scope)!}" title="" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						商城网址: 
					</th>
					<td>
						<input type="text" name="memberBusiness.url" class="formText" value="${(memberBusiness.url)!}" title="" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						ICP证备案号: 
					</th>
					<td>
						<input type="text" name="memberBusiness.icp" class="formText" value="${(memberBusiness.icp)!}" title="" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						联系人: 
					</th>
					<td>
						<input type="text" name="memberBusiness.linkMan" class="formText" value="${(memberBusiness.linkMan)!}" title="" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						业务电话: 
					</th>
					<td>
						<input type="text" name="memberBusiness.tel" class="formText" value="${(memberBusiness.tel)!}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						业务手机: 
					</th>
					<td>
						<input type="text" name="memberBusiness.phone" class="formText" value="${(memberBusiness.phone)!}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						客服热线: 
					</th>
					<td>
						<input type="text" name="memberBusiness.serverTel" class="formText" value="${(memberBusiness.serverTel)!}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						客服工作时间: 
					</th>
					<td>
						<input type="text" name="memberBusiness.serverTime" class="formText" value="${(memberBusiness.serverTime)!}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						即时通讯（QQ）: 
					</th>
					<td>
						<input type="text" name="memberBusiness.QQ" class="formText" value="${(memberBusiness.QQ)!}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						通讯地址: 
					</th>
					<td>
						<input type="text" name="memberBusiness.address" class="formText" value="${(memberBusiness.address)!}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						邮政编码: 
					</th>
					<td>
						<input type="text" name="memberBusiness.zipcode" class="formText" value="${(memberBusiness.zipcode)!}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
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