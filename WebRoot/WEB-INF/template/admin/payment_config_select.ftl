<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>请选择支付方式类型 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
</head>
<body class="list paymentConfig">
	<div class="bar">
		请选择支付方式种类
	</div>
	<div class="body">
		<div class="blank"></div>
		<table id="listTable" class="listTable">
			<#list paymentProductList as paymentProduct>
				<tr>
					<td>
						<#if paymentProduct.logoPath != "">
							<img src="${base}${paymentProduct.logoPath}" />
						<#else>
							&nbsp;
						</#if>
					</td>
					<td>
						<strong>${paymentProduct.name}</strong>
					</td>
					<td>
						${paymentProduct.description}
					</td>
					<td>
						<a href="payment_config!add.action?paymentConfig.paymentConfigType=online&paymentConfig.paymentProductId=${paymentProduct.id}" class="formButton">添 加</a>
					</td>
				</tr>
			</#list>
		</table>
	</div>
</body>
</html>