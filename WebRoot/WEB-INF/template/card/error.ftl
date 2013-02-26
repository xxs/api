<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>提示信息<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/card/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/card/css/card.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/card/js/base.js"></script>
<script type="text/javascript" src="${base}/template/card/js/card.js"></script>
<script type="text/javascript">
$().ready( function() {
	
	function redirectUrl() {
		<#if redirectUrl??>
			window.location.href = "${redirectUrl}"
		<#else>
			window.history.back();
		</#if>
	}
	
	<@compress single_line = true>
		$.dialog({type: "error", title: "操作提示", content: 
		"<#if (errorMessages?? && errorMessages?size > 0) || (fieldErrors?? && fieldErrors?size > 0)>
			<#if errorMessages??>
				<#list errorMessages as errorMessage>
					${errorMessage}&nbsp;
				</#list>
			</#if>
			<#if fieldErrors??>
				<#list fieldErrors.keySet() as fieldErrorKey>
					<#list fieldErrors[fieldErrorKey] as fieldErrorValue>
						${fieldErrorValue}&nbsp;
					</#list>	
				</#list>
			</#if>
		<#else>
			您的操作出现错误!
		</#if>
		", ok: "确定", okCallback: redirectUrl, cancelCallback: redirectUrl, width: 380, modal: true});
	</@compress>

});
</script>
</head>
<body class="error">
</body>
</html>