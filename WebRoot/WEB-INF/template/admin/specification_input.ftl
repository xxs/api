<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑充值卡规格 - XXS</title>
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

	var $specificationTable = $("#specificationTable");
	var $specificationSpecificationType = $("#specificationSpecificationType");
	var $addSpecificationValueButton = $("#addSpecificationValueButton");
	
	// 变更充值卡规格类型
	$specificationSpecificationType.change( function() {
		if ($(this).val() == "text") {
			$("#specificationTable :file").attr("disabled", true);
		} else {
			$("#specificationTable :file").attr("disabled", false);
		}
	})

	// 增加充值卡规格值
	var specificationValueIndex = ${(specification.specificationValueList?size)!1};
	$addSpecificationValueButton.click( function() {
		
		if ($specificationSpecificationType.val() == "text") {
			<@compress single_line = true>
				var specificationValueTrHtml = 
				'<tr class="specificationValueTr">
					<td>
						&nbsp;
					</td>
					<td>
						<input type="text" name="specificationValueList[' + specificationValueIndex + '].name" class="formText specificationValueListName" />
					</td>
					<td>
						<input type="file" name="specificationValueImageList[' + specificationValueIndex + ']" class="specificationValueImageList" disabled />
					</td>
					<td>
						<input type="text" name="specificationValueList[' + specificationValueIndex + '].orderList" class="formText specificationValueListOrderList" style="width: 30px;" />
					</td>
					<td>
						<span class="deleteIcon deleteSpecificationValueIcon" title="删 除">&nbsp;</span>
					</td>
				</tr>';
			</@compress>
		} else {
			<@compress single_line = true>
				var specificationValueTrHtml = 
				'<tr class="specificationValueTr">
					<td>
						&nbsp;
					</td>
					<td>
						<input type="text" name="specificationValueList[' + specificationValueIndex + '].name" class="formText specificationValueListName" />
					</td>
					<td>
						<input type="file" name="specificationValueImageList[' + specificationValueIndex + ']" class="specificationValueImageList" />
					</td>
					<td>
						<input type="text" name="specificationValueList[' + specificationValueIndex + '].orderList" class="formText specificationValueListOrderList" style="width: 30px;" />
					</td>
					<td>
						<span class="deleteIcon deleteSpecificationValueIcon" title="删 除">&nbsp;</span>
					</td>
				</tr>';
			</@compress>
		}
		
		$specificationTable.append(specificationValueTrHtml);
		specificationValueIndex ++;
	});
	
	// 删除充值卡规格值
	$("#specificationTable .deleteSpecificationValueIcon").live("click", function() {
		var $this = $(this);
		if ($specificationTable.find(".specificationValueTr").length <= 1) {
			$.dialog({type: "warn", content: "必须至少保留一个充值卡规格值!", modal: true, autoCloseTime: 3000});
		} else {
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
			"specification.name": "required",
			"specification.orderList": "digits"
		},
		messages: {
			"specification.name": "请填写规格名称",
			"specification.orderList": "排序必须为零或正整数"
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	$.validator.addMethod("specificationValueListNameRequired", $.validator.methods.required, "请填写规格值名称");
	$.validator.addMethod("specificationValueImageListImageFile", $.validator.methods.imageFile, "规格值图片格式错误");
	$.validator.addMethod("specificationValueListOrderListDigits", $.validator.methods.digits, "规格值排序必须为零或正整数");
	
	$.validator.addClassRules("specificationValueListName", {
		specificationValueListNameRequired: true
	});
	$.validator.addClassRules("specificationValueImageList", {
		specificationValueImageListImageFile: true
	});
	$.validator.addClassRules("specificationValueListOrderList", {
		specificationValueListOrderListDigits: true
	});

});
</script>
</head>
<body class="input specification">
	<div class="bar">
		<#if isAddAction>添加充值卡规格<#else>编辑充值卡规格</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>specification!save.action<#else>specification!update.action</#if>" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${id}" />
			<table id="specificationTable" class="inputTable">
				<tr class="titleTr">
					<th>
						规格名称: 
					</th>
					<td colspan="4">
						<input type="text" name="specification.name" class="formText" value="${(specification.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						规格类型: 
					</th>
					<td colspan="4">
						<select id="specificationSpecificationType" name="specification.specificationType">
							<#list specificationTypeList as list>
								<option value="${list}"<#if (list == specification.specificationType)!> selected</#if>>
									${action.getText("SpecificationType." + list)}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						备注: 
					</th>
					<td colspan="4">
						<input type="text" name="specification.memo" class="formText" value="${(specification.memo)!}" />
					</td>
				</tr>
				<tr>
					<th>
						排序: 
					</th>
					<td colspan="4">
						<input type="text" name="specification.orderList" class="formText" value="${(specification.orderList)!}" />
					</td>
				</tr>
				<#if (specification.cardsSet?? && specification.cardsSet?size > 0)!>
					<th>&nbsp;</th>
					<td colspan="4">
						<span class="warnInfo">此充值卡规格被引用,不允许修改规格值名称或删除规格值</span>
					</td>
				</#if>
				<tr class="noneHover">
					<th>&nbsp;</th>
					<td colspan="4">
						<input type="button" id="addSpecificationValueButton" class="formButton" value="增加规格值" hidefocus />
					</td>
				</tr>
				<tr class="title">
					<th>
						&nbsp;
					</th>
					<td>
						规格值名称
					</td>
					<td>
						规格值图片
					</td>
					<td>
						排序
					</td>
					<td>
						删除
					</td>
				</tr>
				<#if isAddAction>
					<tr class="specificationValueTr">
						<td>
							&nbsp;
						</td>
						<td>
							<input type="text" name="specificationValueList[0].name" class="formText specificationValueListName" />
						</td>
						<td>
							<input type="file" name="specificationValueImageList[0]" class="specificationValueImageList" disabled />
						</td>
						<td>
							<input type="text" name="specificationValueList[0].orderList" class="formText specificationValueListOrderList" style="width: 30px;" />
						</td>
						<td>
							<span class="deleteIcon deleteSpecificationValueIcon" title="删 除">&nbsp;</span>
						</td>
					</tr>
				</#if>
				<#if (specification.cardsSet?? && specification.cardsSet?size > 0)!>
					<#list (specification.specificationValueList)! as specificationValue>
						<tr class="specificationValueTr">
							<td>
								<input type="hidden" name="specificationValueList[${specificationValue_index}].id" value="${specificationValue.id}" />
								<input type="hidden" name="specificationValueList[${specificationValue_index}].imagePath" value="${specificationValue.imagePath}" />
								<input type="hidden" name="specificationValueList[${specificationValue_index}].name" value="${specificationValue.name}" />
							</td>
							<td>
								<input type="text" name="specificationValueList.name" class="formText" value="${specificationValue.name}" title="不允许修改" disabled />
							</td>
							<td>
								<#if (specification.specificationType == "text")!>
									<input type="file" name="specificationValueImageList[${specificationValue_index}]" class="specificationValueImageList" disabled />
								<#else>
									<input type="file" name="specificationValueImageList[${specificationValue_index}]" class="specificationValueImageList" />
								</#if>
								<#if specificationValue.imagePath??>
									&nbsp;&nbsp;<img src="${base}${specificationValue.imagePath}" style="width: 20px; height: 20px; padding: 1px; vertical-align: middle; border: 1px solid #b2d3f4;" />
								<#else>
									&nbsp;&nbsp;<img src="${base}/template/card/images/default_specification.gif" style="width: 20px; height: 20px; padding: 1px; vertical-align: middle; border: 1px solid #b2d3f4;" title="无规格图片" />
								</#if>
							</td>
							<td>
								<input type="text" name="specificationValueList[${specificationValue_index}].orderList" class="formText specificationValueListOrderList" value="${specificationValue.orderList}" style="width: 30px;" />
							</td>
							<td>
								<span class="deleteIcon" title="不允许删除">&nbsp;</span>
							</td>
						</tr>
					</#list>
				<#else>
					<#list (specification.specificationValueList)! as specificationValue>
						<tr class="specificationValueTr">
							<td>
								<input type="hidden" name="specificationValueList[${specificationValue_index}].id" value="${specificationValue.id}" />
								<input type="hidden" name="specificationValueList[${specificationValue_index}].imagePath" value="${specificationValue.imagePath}" />
							</td>
							<td>
								<input type="text" name="specificationValueList[${specificationValue_index}].name" class="formText specificationValueListName" value="${specificationValue.name}" />
							</td>
							<td>
								<#if (specification.specificationType == "text")!>
									<input type="file" name="specificationValueImageList[${specificationValue_index}]" class="specificationValueImageList" disabled />
								<#else>
									<input type="file" name="specificationValueImageList[${specificationValue_index}]" class="specificationValueImageList" />
								</#if>
								<#if specificationValue.imagePath??>
									&nbsp;&nbsp;<img src="${base}${specificationValue.imagePath}" style="width: 20px; height: 20px; padding: 1px; vertical-align: middle; border: 1px solid #b2d3f4;" />
								<#else>
									&nbsp;&nbsp;<img src="${base}/template/card/images/default_specification.gif" style="width: 20px; height: 20px; padding: 1px; vertical-align: middle; border: 1px solid #b2d3f4;" title="无规格图片" />
								</#if>
							</td>
							<td>
								<input type="text" name="specificationValueList[${specificationValue_index}].orderList" class="formText specificationValueListOrderList" value="${specificationValue.orderList}" style="width: 30px;" />
							</td>
							<td>
								<span class="deleteIcon deleteSpecificationValueIcon" title="删 除">&nbsp;</span>
							</td>
						</tr>
					</#list>
				</#if>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>