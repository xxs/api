<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑支付方式 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
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
			"paymentConfig.name": "required",
			<#if paymentConfig.paymentConfigType == "online">
				"paymentConfig.bargainorId": "required",
				"paymentConfig.bargainorKey": "required",
			</#if>
			"paymentConfig.paymentFee": {
				required: true,
				min: 0
			},
			"paymentConfig.orderList": "digits"
		},
		messages: {
			"paymentConfig.name": "请填写支付方式名称",
			<#if paymentConfig.paymentConfigType == "online">
				"paymentConfig.bargainorId": "请填写商户ID",
				"paymentConfig.bargainorKey": "请填写密钥",
			</#if>
			"paymentConfig.paymentFee": {
				required: "请填写支付费率/固定费用",
				min: "支付费率/固定费用必须为零或正数"
			},
			"paymentConfig.orderList": "排序必须为零或正整数"
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
		<#if isAddAction>添加支付方式<#else>编辑支付方式</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>payment_config!save.action<#else>payment_config!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="paymentConfig.paymentConfigType" value="${paymentConfig.paymentConfigType}" />
			<input type="hidden" name="paymentConfig.paymentProductId" value="${paymentConfig.paymentProductId}" />
			<table class="inputTable">
				<tr>
					<th>
						支付方式类型: 
					</th>
					<td>
						<#if paymentConfig.paymentConfigType != "online">
							${action.getText("PaymentConfigType." + paymentConfig.paymentConfigType)}
						<#else>
							${paymentProduct.name}
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						支付方式名称: 
					</th>
					<td>
						<input type="text" name="paymentConfig.name" class="formText" value="${(paymentConfig.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<#if paymentConfig.paymentConfigType == "online">
					<tr>
						<th>
							${(payment.bargainorIdName)!"商户ID"}: 
						</th>
						<td>
							<input type="text" name="paymentConfig.bargainorId" class="formText" value="${(paymentConfig.bargainorId)!}" />
							<label class="requireField">*</label>
						</td>
					</tr>
					<tr>
						<th>
							${(payment.bargainorKeyName)!"密钥"}: 
						</th>
						<td>
							<input type="text" name="paymentConfig.bargainorKey" class="formText" value="${(paymentConfig.bargainorKey)!}" />
							<label class="requireField">*</label>
						</td>
					</tr>
				</#if>
				<tr>
					<th>
						支付手续费设置: 
					</th>
					<td>
						<#list paymentFeeTypeList as list>
							<label class="requireField">
								<input type="radio" name="paymentConfig.paymentFeeType" value="${list}"<#if ((isAddAction && list == "scale") || list == paymentConfig.paymentFeeType)!> checked </#if>>
								${action.getText("PaymentFeeType." + list)}
							</label>
						</#list>
						<#if paymentConfig.paymentProductId == "yeepayforzy">
							222
						</#if>
					</td>
				</tr>
					<th>
						支付费率/固定费用: 
					</th>
					<td>
						<input type="text" name="paymentConfig.paymentFee" class="formText" value="${(paymentConfig.paymentFee)!"0"}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				</tr>
					<th>
						是否为推荐/默认：
					</th>
					<td>
						<@checkbox name="paymentConfig.isDefault" value="${(paymentConfig.isDefault)!false}" />选中为推荐/默认
						<label class="requireField">*</label>
					</td>
				</tr>
				</tr>
					<th>
						是否可用：
					</th>
					<td>
						<@checkbox name="paymentConfig.isEnabled" value="${(paymentConfig.isEnabled)!true}" />选中为可用
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						排序: 
					</th>
					<td>
						<input type="text" name="paymentConfig.orderList" class="formText" value="${(paymentConfig.orderList)!}" title="只允许输入零或正整数" />
					</td>
				</tr>
				<tr>
					<th>
						介绍: 
					</th>
					<td>
						<textarea name="paymentConfig.description" id="editor" class="editor">${(paymentConfig.description)!}</textarea>
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