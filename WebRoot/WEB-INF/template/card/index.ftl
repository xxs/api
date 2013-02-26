<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${(setting.shopName)!}<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<#if (setting.metaKeywords)! != ""><meta name="keywords" content="${setting.metaKeywords}" /></#if>
<#if (setting.metaDescription)! != ""><meta name="description" content="${setting.metaDescription}" /></#if>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/card/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/card/css/style.css" rel="stylesheet" type="text/css" />

	<!--[if lte IE 6]>
	<script type="text/javascript" src="${base}/template/common/js/belatedPNG.js"></script>
	<script type="text/javascript">
		// 解决IE6透明PNG图片BUG
		DD_belatedPNG.fix(".belatedPNG");
	</script>
	<![endif]-->
	<script type="text/javascript">
	$(function(){
		$("body").Sonline({
			Position:"right",//left或right
			Top:200,//顶部距离，默认200px
			Effect:false, //滚动或者固定两种方式，布尔值：true或false
			DefaultsOpen:false, //默认展开：true,默认收缩：false
			Qqlist:"734074125|xxs,435027|客服02,435027|客服03,435027|客服04,435027|客服05" //多个QQ用','隔开，QQ和客服名用'|'隔开
		});
	})	
	</script>
</head>
<body>
	<#include "/WEB-INF/template/card/header.ftl">
	<div id="flashBg" style="background-color: rgb(11, 11, 11); ">
	<div id="flashLine">
	<div id="flash">
	<a href="#" target="_blank" id="flash1" style="display: block; " name="#0b0b0b"><img src="${base}/template/card/images/01.jpg" width="980" height="393"></a>
	<a href="#" target="_blank" id="flash2" name="#194982" style="display: none; "><img src="${base}/template/card/images/02.jpg" width="980" height="393"></a>
	<a href="#" target="_blank" id="flash3" name="#04304b" style="display: none; "><img src="${base}/template/card/images/03.jpg" width="980" height="393"></a>
	  <div class="flash_bar">
	<div class="dq" id="f1" onclick="changeflash(1)"></div>
	<div class="no" id="f2" onclick="changeflash(2)"></div>
	<div class="no" id="f3" onclick="changeflash(3)"></div>
	  </div>
	</div>
	</div>
	</div>
	<!--内容区-->
	<div class="content_line">
		<#include "/WEB-INF/template/card/friend_link.ftl">
	</div>
	
	<#include "/WEB-INF/template/card/footer.ftl">
	<script type="text/javascript" src="${base}/template/card/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/template/card/js/jquery.Sonline.js"></script>
	<script type="text/javascript" src="${base}/template/card/js/base.js"></script>
	<script type="text/javascript" src="${base}/template/card/js/card.js"></script>
	<script>
	var currentindex=1;
	var length = $(".flash_bar div").length;
	
	$("#flashBg").css("background-color",$("#flash1").attr("name"));
	function changeflash(i) {	
	currentindex=i;
	
	for (j=1;j<=length;j++){
		if (j==i) 
		{$("#flash"+j).fadeIn("normal");
		$("#flash"+j).css("display","block");
		$("#f"+j).removeClass();
		$("#f"+j).addClass("dq");
		$("#flashBg").css("background-color",$("#flash"+j).attr("name"));
		}
		else
		{$("#flash"+j).css("display","none");
		$("#f"+j).removeClass();
		$("#f"+j).addClass("no");}
	}}
	function startAm(){
	timerID = setInterval("timer_tick()",5000);
	}
	function stopAm(){
	clearInterval(timerID);
	}
	function timer_tick() {
	    currentindex=currentindex>=length?1:currentindex+1;
		changeflash(currentindex);}
	$(document).ready(function(){
	$(".flash_bar div").mouseover(function(){stopAm();}).mouseout(function(){startAm();});
	startAm();
	});
	</script>
</body>
</html>