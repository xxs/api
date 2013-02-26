<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>推广链接<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<#include "/WEB-INF/template/card/member_head.ftl">
<script type="text/javascript" src="${base}/template/common/js/jquery.qrcode.min.js"></script>
<script>
jQuery(function(){
	jQuery('#output').qrcode("http://116.255.199.171/card/member!register.action?id=${member.id}");
})
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
			<div class="fangz">推广链接 </div>
			<div class="red">
				<p>推广链接：<input type="text" class="formText" value="http://116.255.199.171/card/${member.username}.html"/> 
					&nbsp;<a class="formButton">复制</a>
					&nbsp;<a class="formButton" href="member!register.action?id=${member.username}" target="_block">测试</a>
				</p>
			</div>
			<div class="hei">分享二维码，获取更多的推荐用户</div>
		</div>
		<div id="output"></div>
	</div>
	</div>
</div>
<div class="clear"></div>
<#include "/WEB-INF/template/card/member_footer.ftl">
</body>
</html>