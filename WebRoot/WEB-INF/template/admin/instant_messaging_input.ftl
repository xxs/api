<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑在线客服 - XXS</title>
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

	var $addQqButton = $("#addQqButton");
	var $addMsnButton = $("#addMsnButton");
	var $addWangwangButton = $("#addWangwangButton");
	var $addSkypeButton = $("#addSkypeButton");
	var $instantMessagingTable = $("#instantMessagingTable");

	var instantMessagingIndex = ${(instantMessagingList?size)!"0"};

	// 增加QQ在线客服
	$addQqButton.click( function() {
		
		<@compress single_line = true>
			var instantMessagingTrHtml = 
			'<tr>
				<th>&nbsp;</th>
				<td>
					<input type="hidden" name="instantMessagingList[' + instantMessagingIndex + '].instantMessagingType" value="qq" />
					<span class="instantMessagingType">QQ: </span>&nbsp;
					<input type="text" name="instantMessagingList[' + instantMessagingIndex + '].value" class="formText instantMessagingListValue" />
					&nbsp;&nbsp;标题: &nbsp;
					<input type="text" name="instantMessagingList[' + instantMessagingIndex + '].title" class="formText instantMessagingListTitle" />
					&nbsp;&nbsp;排序: &nbsp;
					<input type="text" name="instantMessagingList[' + instantMessagingIndex + '].orderList" class="formText instantMessagingListOrderList" style="width: 30px;" />
					&nbsp;&nbsp;
					<span class="deleteIcon deleteInstantMessagingIcon" title="删 除">&nbsp;</span>
				</td>
			</tr>';
		</@compress>
		
		$instantMessagingTable.append(instantMessagingTrHtml);
		instantMessagingIndex ++;
	});
	
	// 增加MSN在线客服
	$addMsnButton.click( function() {
		
		<@compress single_line = true>
			var instantMessagingTrHtml = 
			'<tr>
				<th>&nbsp;</th>
				<td>
					<input type="hidden" name="instantMessagingList[' + instantMessagingIndex + '].instantMessagingType" value="msn" />
					<span class="instantMessagingType">MSN: </span>&nbsp;
					<input type="text" name="instantMessagingList[' + instantMessagingIndex + '].value" class="formText instantMessagingListValue" />
					&nbsp;&nbsp;标题: &nbsp;
					<input type="text" name="instantMessagingList[' + instantMessagingIndex + '].title" class="formText instantMessagingListTitle" />
					&nbsp;&nbsp;排序: &nbsp;
					<input type="text" name="instantMessagingList[' + instantMessagingIndex + '].orderList" class="formText instantMessagingListOrderList" style="width: 30px;" />
					&nbsp;&nbsp;
					<span class="deleteIcon deleteInstantMessagingIcon" title="删 除">&nbsp;</span>
				</td>
			</tr>';
		</@compress>
		
		$instantMessagingTable.append(instantMessagingTrHtml);
		instantMessagingIndex ++;
	});
	
	// 增加旺旺在线客服
	$addWangwangButton.click( function() {
	
		<@compress single_line = true>
			var instantMessagingTrHtml = 
			'<tr>
				<th>&nbsp;</th>
				<td>
					<input type="hidden" name="instantMessagingList[' + instantMessagingIndex + '].instantMessagingType" value="wangwang" />
					<span class="instantMessagingType">旺旺: </span>&nbsp;
					<input type="text" name="instantMessagingList[' + instantMessagingIndex + '].value" class="formText instantMessagingListValue" />
					&nbsp;&nbsp;标题: &nbsp;
					<input type="text" name="instantMessagingList[' + instantMessagingIndex + '].title" class="formText instantMessagingListTitle" />
					&nbsp;&nbsp;排序: &nbsp;
					<input type="text" name="instantMessagingList[' + instantMessagingIndex + '].orderList" class="formText instantMessagingListOrderList" style="width: 30px;" />
					&nbsp;&nbsp;
					<span class="deleteIcon deleteInstantMessagingIcon" title="删 除">&nbsp;</span>
				</td>
			</tr>';
		</@compress>
		
		$instantMessagingTable.append(instantMessagingTrHtml);
		instantMessagingIndex ++;
	});
	
	// 增加Skype在线客服
	$addSkypeButton.click( function() {
		
		<@compress single_line = true>
			var instantMessagingTrHtml = 
			'<tr>
				<th>&nbsp;</th>
				<td>
					<input type="hidden" name="instantMessagingList[' + instantMessagingIndex + '].instantMessagingType" value="skype" />
					<span class="instantMessagingType">Skype: </span>&nbsp;
					<input type="text" name="instantMessagingList[' + instantMessagingIndex + '].value" class="formText instantMessagingListValue" />
					&nbsp;&nbsp;标题: &nbsp;
					<input type="text" name="instantMessagingList[' + instantMessagingIndex + '].title" class="formText instantMessagingListTitle" />
					&nbsp;&nbsp;排序: &nbsp;
					<input type="text" name="instantMessagingList[' + instantMessagingIndex + '].orderList" class="formText instantMessagingListOrderList" style="width: 30px;" />
					&nbsp;&nbsp;
					<span class="deleteIcon deleteInstantMessagingIcon" title="删 除">&nbsp;</span>
				</td>
			</tr>';
		</@compress>
		
		$instantMessagingTable.append(instantMessagingTrHtml);
		instantMessagingIndex ++;
	});
	
	// 删除在线客服
	$("#instantMessagingTable .deleteInstantMessagingIcon").live("click", function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "您确定要删除吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: deleteInstantMessaging});
		function deleteInstantMessaging() {
			$this.parent().parent().remove();
		}
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"instantMessagingTitle": {
				required: true
			}
		},
		messages: {
			"instantMessagingTitle": {
				required: "请填写在线客服标题"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	$.validator.addMethod("instantMessagingListValueRequired", $.validator.methods.required, "请填写在线客服账号");
	$.validator.addMethod("instantMessagingListTitleRequired", $.validator.methods.required, "请填写在线客服账号标题");
	$.validator.addMethod("instantMessagingListOrderListDigits", $.validator.methods.digits, "排序必须为零或正整数");
	
	$.validator.addClassRules("instantMessagingListValue", {
		instantMessagingListValueRequired: true
	});
	$.validator.addClassRules("instantMessagingListTitle", {
		instantMessagingListTitleRequired: true
	});
	$.validator.addClassRules("instantMessagingListOrderList", {
		instantMessagingListOrderListDigits: true
	});

});
</script>
</head>
<body class="input instantMessaging">
	<div class="bar">
		编辑在线客服
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="instant_messaging!update.action" method="post">
			<table id="instantMessagingTable" class="inputTable">
				<tr>
					<th>
						在线客服标题: 
					</th>
					<td>
						<input type="text" name="instantMessagingTitle" class="formText" value="${(setting.instantMessagingTitle)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						在线客服位置: 
					</th>
					<td>
						<#list instantMessagingPositionList as instantMessagingPosition>
							<label>
								<input type="radio" name="instantMessagingPosition" value="${instantMessagingPosition}"<#if (instantMessagingPosition == setting.instantMessagingPosition)!> checked</#if> />
								${action.getText("InstantMessagingPosition." + instantMessagingPosition)}
							</label>
						</#list>
					</td>
				</tr>
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<label>
							<@checkbox name="isInstantMessagingEnabled" value="${setting.isInstantMessagingEnabled}" />启用
						</label>
					</td>
				</tr>
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<th>
						添加在线客服
					</th>
					<td>
						<input type="button" id="addQqButton" class="formButton" value="添加QQ" hidefocus />&nbsp;&nbsp;
						<input type="button" id="addMsnButton" class="formButton" value="添加MSN" hidefocus />&nbsp;&nbsp;
						<input type="button" id="addWangwangButton" class="formButton" value="添加旺旺" hidefocus />&nbsp;&nbsp;
						<input type="button" id="addSkypeButton" class="formButton" value="添加Skype" hidefocus />
					</td>
				</tr>
				<#list instantMessagingList as instantMessaging>
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<input type="hidden" name="instantMessagingList[${instantMessaging_index}].instantMessagingType" value="${instantMessaging.instantMessagingType}" />
							<span class="instantMessagingType">${action.getText("InstantMessagingType." + instantMessaging.instantMessagingType)}: </span>&nbsp;
							<input type="text" name="instantMessagingList[${instantMessaging_index}].value" class="formText instantMessagingListValue" value="${instantMessaging.value}" />
							&nbsp;&nbsp;标题: &nbsp;
							<input type="text" name="instantMessagingList[${instantMessaging_index}].title" class="formText instantMessagingListTitle" value="${instantMessaging.title}" />
							&nbsp;&nbsp;排序: &nbsp;
							<input type="text" name="instantMessagingList[${instantMessaging_index}].orderList" class="formText instantMessagingListOrderList" style="width: 30px;" value="${instantMessaging.orderList}" />
							&nbsp;&nbsp;
							<span class="deleteIcon deleteInstantMessagingIcon" title="删 除">&nbsp;</span>
						</td>
					</tr>
				</#list>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>