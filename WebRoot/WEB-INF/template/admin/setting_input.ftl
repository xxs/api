<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>系统设置 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	var $tab = $("#tab");
	var $isLoginFailureLockInput = $(".isLoginFailureLock");
	var $loginFailureLockCountTr = $("#loginFailureLockCountTr");
	var $loginFailureLockTimeTr = $("#loginFailureLockTimeTr");
	var $smtpFromMail = $("#smtpFromMail");
	var $smtpHost = $("#smtpHost");
	var $smtpPort = $("#smtpPort");
	var $smtpUsername = $("#smtpUsername");
	var $smtpPassword = $("#smtpPassword");
	var $smtpToMailWrap = $("#smtpToMailWrap");
	var $smtpToMail = $("#smtpToMail");
	var $smtpTest = $("#smtpTest");
	var $smtpTestStatus = $("#smtpTestStatus");
	var $scoreType = $(".scoreType");
	var $scoreScaleTr = $("#scoreScaleTr");
	var $scoreScale = $("#scoreScale");
	
	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	$isLoginFailureLockInput.click( function() {
		var $this = $(this);
		if($this.val() == "true") {
			$loginFailureLockCountTr.show();
			$loginFailureLockTimeTr.show();
		} else {
			$loginFailureLockCountTr.hide();
			$loginFailureLockTimeTr.hide();
		}
	});
	
	// 邮箱测试
	$smtpTest.click( function() {
		var $this = $(this);
		if ($this.val() == "邮箱测试") {
			$this.val("发送邮件");
			$smtpToMailWrap.show();
		} else {
			if ($.trim($smtpFromMail.val()) == "") {
				$.dialog({type: "warn", content: "请输入发件人邮箱!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if (!/^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test($.trim($smtpFromMail.val()))) {
				$.dialog({type: "warn", content: "发件人邮箱格式错误!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if ($.trim($smtpHost.val()) == "") {
				$.dialog({type: "warn", content: "请输入SMTP服务器地址!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if ($.trim($smtpPort.val()) == "") {
				$.dialog({type: "warn", content: "请输入SMTP服务器端口!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if (!/^[0-9]+$/.test($.trim($smtpPort.val()))) {
				$.dialog({type: "warn", content: "SMTP服务器端口格式错误!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if ($.trim($smtpUsername.val()) == "") {
				$.dialog({type: "warn", content: "请输入SMTP用户名!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if ($.trim($smtpPassword.val()) == "") {
				$.dialog({type: "warn", content: "请输入SMTP密码!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if ($.trim($smtpToMail.val()) == "") {
				$.dialog({type: "warn", content: "请输入收件人邮箱!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if (!/^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test($.trim($smtpToMail.val()))) {
				$.dialog({type: "warn", content: "收件人邮箱格式错误!", modal: true, autoCloseTime: 3000});
				return false;
			}
			
			$.ajax({
				url: "setting!ajaxSendSmtpTest.action",
				data: {"setting.smtpFromMail": $smtpFromMail.val(), "setting.smtpHost": $smtpHost.val(), "setting.smtpPort": $smtpPort.val(), "setting.smtpUsername": $smtpUsername.val(), "setting.smtpPassword": $smtpPassword.val(), "smtpToMail": $smtpToMail.val()},
				type: "POST",
				dataType: "json",
				cache: false,
				beforeSend: function(data) {
					$smtpTestStatus.html('<span class="loadingIcon">&nbsp;</span>正在发送测试邮件,请稍后...');
					$this.attr("disabled", true);
				},
				success: function(data) {
					$smtpTestStatus.empty();
					$this.attr("disabled", false);
					$.dialog({type: data.status, content: data.message, width: 400, ok: "确 定", modal: true});
				}
			});
		}
	});
	
	// 根据积分获取方式显示/隐藏“积分换算比率”
	$scoreType.click( function() {
		$this = $(this);
		if($this.val() == "orderAmount") {
			$scoreScale.removeClass("ignoreValidate");
			$scoreScaleTr.show();
		} else {
			$scoreScale.val("0");
			$scoreScale.addClass("ignoreValidate");
			$scoreScaleTr.hide();
		}
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		ignore: ".ignoreValidate",
		rules: {
			"setting.cardName": "required",
			"setting.cardUrl": "required",
			"cardLogo": "imageFile",
			"setting.email": "email",
			"setting.bigCardsImageWidth": {
				required: true,
				positiveInteger: true
			},
			"setting.bigCardsImageHeight": {
				required: true,
				positiveInteger: true
			},
			"setting.smallCardsImageWidth": {
				required: true,
				positiveInteger: true
			},
			"setting.smallCardsImageHeight": {
				required: true,
				positiveInteger: true
			},
			"setting.thumbnailCardsImageWidth": {
				required: true,
				positiveInteger: true
			},
			"setting.thumbnailCardsImageHeight": {
				required: true,
				positiveInteger: true
			},
			"defaultBigCardsImage": "imageFile",
			"defaultSmallCardsImage": "imageFile",
			"defaultThumbnailCardsImage": "imageFile",
			"watermarkImage": "imageFile",
			"setting.watermarkAlpha": {
				required: true,
				digits: true
			},
			"setting.loginFailureLockCount": {
				required: true,
				positiveInteger: true
			},
			"setting.loginFailureLockTime": {
				required: true,
				digits: true
			},
			"setting.smtpFromMail": {
				required: true,
				email: true
			},
			"setting.smtpHost": "required",
			"setting.smtpPort": {
				required: true,
				digits: true
			},
			"setting.smtpUsername": "required",
			"setting.currencyType": "required",
			"setting.currencySign": "required",
			"setting.currencyUnit": "required",
			"setting.scoreScale": {
				required: true,
				min: 0
			},
			"setting.buildHtmlDelayTime": {
				required: true,
				digits: true
			}
		},
		messages: {
			"setting.cardName": "请填写网店名称",
			"setting.cardUrl": "请填写网店网址",
			"cardLogo": "网店LOGO格式错误",
			"setting.email": "E-mail格式不正确",
			"setting.bigCardsImageWidth": {
				required: "请填写充值卡图片(大)的宽度",
				positiveInteger: "充值卡图片(大)的宽度必须为正整数"
			},
			"setting.bigCardsImageHeight": {
				required: "请填写充值卡图片(大)的高度",
				positiveInteger: "充值卡图片(大)的高度必须为正整数"
			},
			"setting.smallCardsImageWidth": {
				required: "请填写充值卡图片(小)的宽度",
				positiveInteger: "充值卡图片(小)的宽度必须为正整数"
			},
			"setting.smallCardsImageHeight": {
				required: "请填写充值卡图片(小)的高度",
				positiveInteger: "充值卡图片(小)的高度必须为正整数"
			},
			"setting.thumbnailCardsImageWidth": {
				required: "请填写充值卡缩略图的宽度",
				positiveInteger: "充值卡缩略图的宽度必须为正整数"
			},
			"setting.thumbnailCardsImageHeight": {
				required: "请填写充值卡缩略图的高度",
				positiveInteger: "充值卡缩略图的高度必须为正整数"
			},
			"defaultBigCardsImage": "默认充值卡图片(大)格式错误",
			"defaultSmallCardsImage": "默认充值卡图片(小)格式错误",
			"defaultThumbnailCardsImage": "默认缩略图格式错误",
			"watermarkImage": "水印图片格式错误",
			"setting.watermarkAlpha": {
				required: "请填写水印透明度",
				digits: "水印透明度必须为零或正整数"
			},
			"setting.loginFailureLockCount": {
				required: "请填写连续登录失败最大次数",
				positiveInteger: "连续登录失败最大次数请输入合法的正整数"
			},
			"setting.loginFailureLockTime": {
				required: "请填写自动解锁时间",
				digits: "自动解锁时间必须为零或正整数"
			},
			"setting.smtpFromMail": {
				required: "请填写发件人邮箱",
				email: "发件人邮箱格式错误"
			},
			"setting.smtpHost": "请填写SMTP服务器地址",
			"setting.smtpPort": {
				required: "请填写SMTP服务器端口",
				digits: "SMTP服务器端口必须为零或正整数"
			},
			"setting.smtpUsername": "请填写SMTP用户名",
			"setting.currencyType": "请选择货币种类",
			"setting.currencySign": "请填写货币符号",
			"setting.currencyUnit": "请填写货币单位",
			"setting.scoreScale": {
				required: "请填写积分换算比率",
				min: "积分换算比率必须为零或正数"
			},
			"setting.buildHtmlDelayTime": {
				required: "请填写HTML自动生成延时",
				digits: "HTML自动生成延时必须为零或正整数"
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
		系统设置
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" class="form" action="setting!update.action" enctype="multipart/form-data" method="post">
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="基本设置" hidefocus />
				</li>
				<li>
					<input type="button" value="显示设置" hidefocus />
				</li>
				<li>
					<input type="button" value="安全设置" hidefocus />
				</li>
				<li>
					<input type="button" value="邮件设置" hidefocus />
				</li>
				<li>
					<input type="button" value="其它设置" hidefocus />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						网店名称: 
					</th>
					<td>
						<input type="text" name="setting.cardName" class="formText" value="${setting.cardName}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						网店网址: 
					</th>
					<td>
						<input type="text" name="setting.cardUrl" class="formText" value="${setting.cardUrl}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						网店LOGO: 
					</th>
					<td>
						<input type="file" name="cardLogo" />
						<a href="${base}${setting.cardLogoPath}?random=${random}" target="_blank">查看</a>
					</td>
				</tr>
				<tr>
					<th>
						热门搜索关键词: 
					</th>
					<td>
						<input type="text" name="setting.hotSearch" class="formText" value="${setting.hotSearch}" title="页面显示的热门搜索关键字,多个关键字请以 , 分隔" />
					</td>
				</tr>
				<tr>
					<th>
						联系地址: 
					</th>
					<td>
						<input type="text" name="setting.address" class="formText" value="${setting.address}" />
					</td>
				</tr>
				<tr>
					<th>
						服务电话: 
					</th>
					<td>
						<input type="text" name="setting.phone" class="formText" value="${setting.phone}" />
					</td>
				</tr>
				<tr>
					<th>
						邮编: 
					</th>
					<td>
						<input type="text" name="setting.zipCode" class="formText" value="${setting.zipCode}" />
					</td>
				</tr>
				<tr>
					<th>
						E-mail: 
					</th>
					<td>
						<input type="text" name="setting.email" class="formText" value="${setting.email}" />
					</td>
				</tr>
				<tr>
					<th>
						备案号: 
					</th>
					<td>
						<input type="text" name="setting.certtext" class="formText" value="${setting.certtext}" title="填写您在工信部备案管理网站申请的备案编号" />
					</td>
				</tr>
				<tr>
					<th>
						首页页面关键词: 
					</th>
					<td>
						<input type="text" name="setting.metaKeywords" class="formText" value="${setting.metaKeywords}" title="多个关键字请以(,)分隔" />
					</td>
				</tr>
				<tr>
					<th>
						首页页面描述: 
					</th>
					<td>
						<textarea name="setting.metaDescription" class="formTextarea">${setting.metaDescription}</textarea>
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						充值卡图片(大): 
					</th>
					<td>
						<span class="fieldTitle">宽度: </span>
						<input type="text" name="setting.bigCardsImageWidth" class="formText" value="${setting.bigCardsImageWidth}" style="width: 50px;" title="单位: 像素, 只允许输入正整数" />
						<label class="requireField">*</label>
						<span class="fieldTitle">高度: </span>
						<input type="text" name="setting.bigCardsImageHeight" class="formText" value="${setting.bigCardsImageHeight}" style="width: 50px;" title="单位: 像素, 只允许输入正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						充值卡图片(小): 
					</th>
					<td>
						<span class="fieldTitle">宽度: </span>
						<input type="text" name="setting.smallCardsImageWidth" class="formText" value="${setting.smallCardsImageWidth}" style="width: 50px;" title="单位: 像素, 只允许输入正整数" />
						<label class="requireField">*</label>
						<span class="fieldTitle">高度: </span>
						<input type="text" name="setting.smallCardsImageHeight" class="formText" value="${setting.smallCardsImageHeight}" style="width: 50px;" title="单位: 像素, 只允许输入正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						充值卡缩略图: 
					</th>
					<td>
						<span class="fieldTitle">宽度: </span>
						<input type="text" name="setting.thumbnailCardsImageWidth" class="formText" value="${setting.thumbnailCardsImageWidth}" style="width: 50px;" title="单位: 像素, 只允许输入正整数" />
						<label class="requireField">*</label>
						<span class="fieldTitle">高度: </span>
						<input type="text" name="setting.thumbnailCardsImageHeight" class="formText" value="${setting.thumbnailCardsImageHeight}" style="width: 50px;" title="单位: 像素, 只允许输入正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						默认充值卡图片(大): 
					</th>
					<td>
						<input type="file" name="defaultBigCardsImage" />
						<a href="${base}${setting.defaultBigCardsImagePath}?random=${random}" target="_blank">查看</a>
					</td>
				</tr>
				<tr>
					<th>
						默认充值卡图片(小): 
					</th>
					<td>
						<input type="file" name="defaultSmallCardsImage" />
						<a href="${base}${setting.defaultSmallCardsImagePath}?random=${random}" target="_blank">查看</a>
					</td>
				</tr>
				<tr>
					<th>
						默认缩略图: 
					</th>
					<td>
						<input type="file" name="defaultThumbnailCardsImage" />
						<a href="${base}${setting.defaultThumbnailCardsImagePath}?random=${random}" target="_blank">查看</a>
					</td>
				</tr>
				<tr>
					<th>
						水印图片: 
					</th>
					<td>
						<input type="file" name="watermarkImage" />
						<a href="${base}${setting.watermarkImagePath}?random=${random}" target="_blank">查看</a>
					</td>
				</tr>
				<tr>
					<th>
						水印位置: 
					</th>
					<td>
						<#list watermarkPositionList as watermarkPosition>
							<label>
								<input type="radio" name="setting.watermarkPosition" value="${watermarkPosition}"<#if watermarkPosition == setting.watermarkPosition> checked</#if> />
								${action.getText("WatermarkPosition." + watermarkPosition)}&nbsp;
							</label>
						</#list>
					</td>
				</tr>
				<tr>
					<th>
						水印透明度: 
					</th>
					<td>
						<input type="text" name="setting.watermarkAlpha" class="formText" value="${setting.watermarkAlpha}" title="取值范围: 0-100,  0代表完全透明" />
						<label class="requireField">*</label>
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						会员注册设置: 
					</th>
					<td>
						<label>
							<@checkbox name="setting.isRegisterEnabled" value="${setting.isRegisterEnabled}" />开放注册
						</label>
					</td>
				</tr>
				<tr>
					<th>
						是否自动锁定账号: 
					</th>
					<td>
						<label><input type="radio" name="setting.isLoginFailureLock" class="isLoginFailureLock" value="true"<#if setting.isLoginFailureLock> checked</#if> />是</label>
						<label><input type="radio" name="setting.isLoginFailureLock" class="isLoginFailureLock" value="false"<#if !setting.isLoginFailureLock> checked</#if> />否</label>
					</td>
				</tr>
				<tr id="loginFailureLockCountTr"<#if !setting.isLoginFailureLock> class="hidden"</#if>>
					<th>
						连续登录失败最大次数: 
					</th>
					<td>
						<input type="text" name="setting.loginFailureLockCount" class="formText" value="${setting.loginFailureLockCount}" title="只允许输入正整数,当连续登录失败次数超过设定值时,系统将自动锁定该账号" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr id="loginFailureLockTimeTr"<#if !setting.isLoginFailureLock> class="hidden"</#if>>
					<th>
						自动解锁时间: 
					</th>
					<td>
						<input type="text" name="setting.loginFailureLockTime" class="formText" value="${setting.loginFailureLockTime}" title="只允许输入零或正整数,账号锁定后,自动解除锁定的时间,单位: 分钟,0表示永久锁定" />
						<label class="requireField">*</label>
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						发件人邮箱: 
					</th>
					<td>
						<input type="text" id="smtpFromMail" name="setting.smtpFromMail" class="formText" value="${setting.smtpFromMail}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						SMTP服务器地址: 
					</th>
					<td>
						<input type="text" id="smtpHost" name="setting.smtpHost" class="formText" value="${setting.smtpHost}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						SMTP服务器端口: 
					</th>
					<td>
						<input type="text" id="smtpPort" name="setting.smtpPort" class="formText" value="${setting.smtpPort}" title="默认端口为25" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						SMTP用户名: 
					</th>
					<td>
						<input type="text" id="smtpUsername" name="setting.smtpUsername" class="formText" value="${setting.smtpUsername}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						SMTP密码: 
					</th>
					<td>
						<input type="password" id="smtpPassword" name="setting.smtpPassword" class="formText" title="留空则不进行密码修改" />
					</td>
				</tr>
				<tr>
					<th>
						邮箱配置测试: 
					</th>
					<td>
						<span id="smtpToMailWrap" class="hidden">
							<div>收件人邮箱: </div>
							<input type="text" id="smtpToMail" name="smtpToMail" class="formText" />
						</span>
						<input type="button" id="smtpTest" class="formButton" value="邮箱测试" hidefocus />
						<span id="smtpTestStatus"></span>
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						货币种类: 
					</th>
					<td>
						<select id="currencyType" name="setting.currencyType">
							<option value="">请选择...</option>
							<#list currencyTypeList as currencyType>
								<option value="${currencyType}"<#if currencyType == setting.currencyType> selected</#if>>
									${action.getText("CurrencyType." + currencyType)}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						货币符号: 
					</th>
					<td>
						<input type="text" name="setting.currencySign" class="formText" value="${setting.currencySign}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						货币单位: 
					</th>
					<td>
						<input type="text" name="setting.currencyUnit" class="formText" value="${setting.currencyUnit}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						价格精确位数: 
					</th>
					<td>
						<select name="setting.priceScale">
							<option value="0" <#if setting.priceScale == 0> selected</#if>>
								无小数位
							</option>
							<option value="1" <#if setting.priceScale == 1> selected</#if>>
								1位小数
							</option>
							<option value="2" <#if setting.priceScale == 2> selected</#if>>
								2位小数
							</option>
							<option value="3" <#if setting.priceScale == 3> selected</#if>>
								3位小数
							</option>
							<option value="4" <#if setting.priceScale == 4> selected</#if>>
								4位小数
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						价格精确方式: 
					</th>
					<td>
						<select name="setting.priceRoundType">
							<#list roundTypeList as roundType>
								<option value="${roundType}"<#if roundType == setting.priceRoundType> selected</#if>>
									${action.getText("RoundType." + roundType)}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						积分获取方式: 
					</th>
					<td>
						<#list scoreTypeList as scoreType>
							<label>
								<input type="radio" name="setting.scoreType" class="scoreType" value="${scoreType}"<#if scoreType == setting.scoreType> checked</#if> />
								${action.getText("ScoreType." + scoreType)}&nbsp;
							</label>
						</#list>
					</td>
				</tr>
				<tr id="scoreScaleTr"<#if setting.scoreType != "orderAmount"> class="hidden"</#if>>
					<th>
						积分换算比率: 
					</th>
					<td>
						<input type="text" id="scoreScale" name="setting.scoreScale" class="formText" value="${setting.scoreScale}" title="每消费1元可获得积分数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						HTML自动生成延时: 
					</th>
					<td>
						<input type="text" name="setting.buildHtmlDelayTime" class="formText" value="${setting.buildHtmlDelayTime}" title="单位: 秒,设置延时值过小可能会造成服务器压力过大" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						GZIP设置: 
					</th>
					<td>
						<label>
							<@checkbox name="setting.isGzipEnabled" value="${setting.isGzipEnabled}" />启用
						</label>
					</td>
				</tr>
				<tr>
					<th>
						
					</th>
					<td>
						<span class="warnInfo">若您的WEB服务器已启用了GZIP功能,请关闭此处的GZIP功能</span>
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