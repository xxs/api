<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>支付结果<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/card/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/card/css/card.css" rel="stylesheet" type="text/css" />
<!--[if lte IE 6]>
	<script type="text/javascript" src="${base}/template/common/js/belatedPNG.js"></script>
	<script type="text/javascript">
		// 解决IE6透明PNG图片BUG
		DD_belatedPNG.fix(".belatedPNG");
	</script>
<![endif]-->
</head>
<body class="paymentResult">
	<#include "/WEB-INF/template/card/header.ftl">
	<div class="body">
		<div class="blank"></div>
		<div class="paymentResultDetail">
			<#if payment.paymentType == "deposit">
				<h2>
					<span class="icon success">&nbsp;</span>
					预存款支付成功,支付总金额<span class="red">${payment.totalAmount?string(currencyFormat)}</span>
				</h2>
			<#elseif payment.paymentType == "offline">
				<h2>
					<span class="icon success">&nbsp;</span>
					请根据您选择的支付方式进行付款,支付总金额<span class="red">${payment.totalAmount?string(currencyFormat)}</span>
				</h2>
				<p>
					${(payment.paymentConfig.description)!}
				</p>
			<#else>
				<h2>
					<span class="icon success">&nbsp;</span>
					在线支付成功,支付总金额<span class="red">${payment.totalAmount?string(currencyFormat)}</span>
				</h2>
			</#if>
			<div class="buttonArea">
				<input type="submit" class="formButton" onclick="location.href='${base}/'" value="返回首页" />
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/card/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/card/footer.ftl">
	<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
	<script type="text/javascript" src="${base}/template/card/js/base.js"></script>
	<script type="text/javascript" src="${base}/template/card/js/card.js"></script>
</body>
</html>