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
</script>
</head>
<body class="input">
	<div class="bar">
		<#if isAddAction>添加支付通道<#else>编辑支付通道</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>payment_config!saveDiscount.action<#else>payment_config!updateDiscount.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="paymentDiscount.paymentConfig.id" value="${paymentConfig.id}" />
			<table class="inputTable">
				<tr>
					<th>
						支付方式名称: 
					</th>
					<td>
						${(paymentConfig.name)!}
					</td>
				</tr>
				
				<tr>
					<th>
						卡种品牌: 
					</th>
					<td>
					<select name="paymentDiscount.brand.id" >
						<#list brandList as brand>
							<option value="${(brand.id)!}" <#if (paymentDiscount.brand.id == brand.id)!> selected</#if>>
								${(brand.name)!}
							</option>
						</#list>
					</select>						
					</td>
				</tr>
				<tr>
					<th>
						通道编码: 
					</th>
					<td>
						<input name="paymentDiscount.code" class="formText"  value="${(paymentDiscount.code)!}" />
					</td>
				</tr>
				<tr>
					<th>
						折扣率: 
					</th>
					<td>
						<input name="paymentDiscount.discount" class="formText"  value="${(paymentDiscount.discount)!}" />
					</td>
				</tr>
				<tr>
					<th>
						支持面额: 
					</th>
					<td>
						<input name="paymentDiscount.face" class="formText"  value="${(paymentDiscount.face)!}" />为0表示所有面额
					</td>
				</tr>
				<tr>
					<th>
						备注: 
					</th>
					<td>
						<input name="paymentDiscount.memo" class="formText"  value="${(paymentDiscount.memo)!}" />
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