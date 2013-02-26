<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑充值卡 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $tab = $("#tab");
	var $cardsForm = $("#cardsForm");
	var $specificationDisabledInfo = $("#infoTable .specificationDisabledInfo");
	var $cardsIsMarketable = $("#cardsIsMarketable");
	
	var $cardsImageTable = $("#cardsImageTable");
	var $addCardsImageButton = $("#addCardsImageButton");
	
	var $specificationTab = $("#specificationTab");
	var $specificationTable = $("#specificationTable");
	var $isSpecificationEnabled = $("#isSpecificationEnabled");
	var $specificationValueLoadingIcon  = $("#specificationValueLoadingIcon");
	var $specificationIds = $("#specificationSelect :checkbox");
	var $addProductButton = $("#addProductButton");
	var $productTable = $("#productTable");
	var $productTitle = $("#productTitle");
	var $specificationInsertTh = $("#specificationInsertTh");
	
	var $cardsTypeId = $("#cardsTypeId");
	var $cardsTypeLoadingIcon = $("#cardsTypeLoadingIcon");
	var $cardsAttributeTable = $("#cardsAttributeTable");
	var $cardsParameterTable = $("#cardsParameterTable");

	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	// 增加充值卡图片
	var cardsImageIndex = ${(cards.cardsImageList?size)!0};
	$addCardsImageButton.click( function() {
		
		<@compress single_line = true>
			var cardsImageTrHtml = 
			'<tr class="cardsImageTr">
				<td>
					<input type="file" name="cardsImageFileList[' + cardsImageIndex + ']" class="cardsImageFileList" />
				</td>
				<td>
					<input type="text" name="cardsImageList[' + cardsImageIndex + '].description" class="formText" />
				</td>
				<td>
					<input type="text" name="cardsImageList[' + cardsImageIndex + '].orderList" class="formText cardsImageOrderList" style="width: 50px;" />
				</td>
				<td>
					<span class="deleteIcon deleteCardsImage" title="删 除">&nbsp;</span>
				</td>
			</tr>';
		</@compress>
		
		$cardsImageTable.append(cardsImageTrHtml);
		cardsImageIndex ++;
	});
	
	// 删除充值卡图片
	$("#cardsImageTable .deleteCardsImage").live("click", function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "您确定要删除吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: deleteCardsImage});
		function deleteCardsImage() {
			$this.parent().parent().remove();
		}
	});
	
	// 切换充值卡规格
	$specificationTab.click( function() {
		if (!$isSpecificationEnabled.attr("checked")) {
			$.dialog({type: "warn", content: "确定启用充值卡规格?", ok: "确 定", cancel: "取 消", modal: true, okCallback: specificationEnabled});
			function specificationEnabled() {
				$isSpecificationEnabled.attr("checked", true);
				$specificationDisabledInfo.hide().find(":input").attr("disabled", true);
				$specificationTable.find(":checkbox, :button").not($isSpecificationEnabled).attr("disabled", false);
				$productTable.find(":input").attr("disabled", false);
			}
		}
	});
	
	// 开启/关闭充值卡规格
	$isSpecificationEnabled.click( function() {
		if ($isSpecificationEnabled.attr("checked")) {
			$specificationDisabledInfo.hide().find(":input").attr("disabled", true)
			$specificationTable.find(":checkbox, :button").not($isSpecificationEnabled).attr("disabled", false);
			$productTable.find(":input").attr("disabled", false);
		} else {
			$specificationDisabledInfo.show().find(":input").attr("disabled", false);
			$specificationTable.find(":checkbox, :button").not($isSpecificationEnabled).attr("disabled", true);
			$productTable.find(":input").attr("disabled", true);
		}
	});
	
	// 修改充值卡规格
	var specificationDatas = {};
	var specificationCheckedDatas = {};
	<#list (cards.specificationSet)! as specification>
		specificationCheckedDatas['${specification.id}'] = ${specification.json};
	</#list>
	$specificationIds.click( function() {
		$this = $(this);
		var id = $this.val();
		var specificationData = specificationDatas[id];
		if(typeof(specificationData) == "undefined") {
			$.ajax({
				url: "cards!ajaxSpecification.action",
				data: {id: id},
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				beforeSend: function() {
					$specificationValueLoadingIcon.show();
				},
				success: function(data) {
					specificationDatas[id] = data;
					specificationData = data;
				},
				complete: function() {
					$specificationValueLoadingIcon.hide();
				}
			});
		}

		if ($this.attr("checked") == true) {
			$specificationInsertTh.before('<th class="' + specificationData.id + '">' + specificationData.name + '</th>');
			
			var specificationValueOptionHtml = "";
			$.each(specificationData.specificationValueList, function(i, specificationValue) {
				specificationValueOptionHtml += ('<option value="' + specificationValue.id + '">' + specificationValue.name + '</option>');
			});
			
			$productTitle.nextAll("tr").each(function() {
				var productIndex = $(this).attr("productIndex");
				var specificationValueTdHtml = '<td class="' + specificationData.id + '"><select name="specificationValueIdsList[' + productIndex + ']">' + specificationValueOptionHtml + '</select></td>';
				$(this).find(".specificationInsertTd").before(specificationValueTdHtml);
			});
			
			specificationCheckedDatas[id] = specificationData;
		} else {
			$("." + id).remove();
			var specificationCheckedTempDatas = {};
			$.each(specificationCheckedDatas, function(i, specificationCheckedData) {
				if (specificationCheckedDatas[id].id != specificationCheckedData.id) {
					specificationCheckedTempDatas[i] = specificationCheckedData;
				}
			});
			specificationCheckedDatas = specificationCheckedTempDatas;
		}
	});
	
	// 增加货品
	var productIndex = ${(cards.productSet?size)!0};
	$addProductButton.click( function() {
		
		<@compress single_line = true>
			var productTrHtml = '<tr class="productTr" productIndex="' + productIndex + '">
				<td>
					<input type="text" id="productListProductSn' + productIndex + '" name="productList[' + productIndex + '].productSn" class="formText productListProductSn" style="width: 50px;" title="留空则由系统自动生成" />
				</td>';
		</@compress>
		
		$.each(specificationCheckedDatas, function(i, specificationCheckedData) {
			if (typeof(specificationCheckedData) != "undefined") {
				var specificationValueTdHtml = '<td class="' + specificationCheckedData.id + '"><select name="specificationValueIdsList[' + productIndex + ']">';
				$.each(specificationCheckedData.specificationValueList, function(j, specificationValue) {
					specificationValueTdHtml += ('<option value="' + specificationValue.id + '">' + specificationValue.name + '</option>');
				});
				specificationValueTdHtml += '</select></td>';
				productTrHtml += specificationValueTdHtml;
			}
		});
		
		<@compress single_line = true>
			productTrHtml += 
			'<td class="specificationInsertTd">
					<input type="text" name="productList[' + productIndex + '].price" class="formText productListPrice" value="0" style="width: 50px;" />
				</td>
				<td>
					<@checkbox name="productList[' + productIndex + '].isDefault" cssClass="productListIsDefault" value="false" />
				</td>
				<td>
					<@checkbox name="productList[' + productIndex + '].isMarketable" cssClass="productListIsMarketable" value="true" />
				</td>
				<td>
					<span class="deleteIcon deleteProduct" title="删 除">&nbsp;</span>
				</td>
			</tr>';
		</@compress>
		
		$productTable.append(productTrHtml);
		
		if ($("#productTable input.productListIsDefault:checked").length == 0) {
			$("#productTable input.productListIsDefault:first").attr("checked", true);
		}
		
		var $productListProductSn = $("#productListProductSn" + productIndex);
		$productListProductSn.rules("add", {
			remote: {
				url: "cards!checkProductSn.action",
				type: "post",
				cache: false,
				dataType: "json",
				data: {
					"defaultProduct.productSn": function () {
						return $productListProductSn.val();
					}
				}
			},
			messages: {
				remote: "货号已存在"
			}
		});
		productIndex ++;
	});
	
	// 删除货品
	$("#productTable .deleteProduct").live("click", function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "您确定要删除吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: deleteProduct});
		function deleteProduct() {
			$this.parent().parent().remove();
		}
	});
	
	// 修改货品默认
	$("#productTable input.productListIsDefault").live("click", function() {
		var $this = $(this);
		if (!$this.parent().next().find("input.productListIsMarketable").attr("checked")) {
			$.dialog({type: "warn", content: "请选择上架货品为默认货品!", modal: true, autoCloseTime: 3000});
			return false;
		}
		$("#productTable input.productListIsDefault").not($this).attr("checked", false);
	});
	
	// 充值卡上架
	$cardsIsMarketable.click( function() {
		if ($isSpecificationEnabled.attr("checked") && $("#cardsForm input.productListIsMarketable:checked").size() == 0) {
			$.dialog({type: "warn", content: "货品已全部下架后,充值卡无法上架!", modal: true, autoCloseTime: 3000});
			return false;
		}
	});
	
	// 货品全部下架
	$("#cardsForm input.productListIsMarketable").live("click", function() {
		var $this = $(this);
		if ($this.parent().prev().find("input.productListIsDefault").attr("checked")) {
			$.dialog({type: "warn", content: "默认货品无法下架!", modal: true, autoCloseTime: 3000});
			return false;
		}
		if ($isSpecificationEnabled.attr("checked") && $("#cardsForm input.productListIsMarketable:checked").size() == 0) {
			$.dialog({type: "warn", content: "货品已全部下架后,充值卡将自动下架!", modal: true, autoCloseTime: 3000});
			$cardsIsMarketable.attr("checked", false);
		}
	});
	
	// 修改充值卡类型
	var previousCardsTypeId = "${(cards.cardsType.id)!}";
	$cardsTypeId.change( function() {
		if (previousCardsTypeId != "") {
			$.dialog({type: "warn", content: "修改充值卡类型后当前“属性参数”数据将会丢失,是否继续!", width: 450, ok: "确 定", cancel: "取 消", modal: true, okCallback: cardsTypeChange, cancelCallback: cardsTypeReset});
		} else {
			cardsTypeChange();
		}
		
		function cardsTypeChange() {
			previousCardsTypeId = $cardsTypeId.val();
			
			if ($cardsTypeId.val() == "") {
				$cardsAttributeTable.hide();
				$cardsParameterTable.hide();
				return;
			}
			
			$.ajax({
				url: "cards!ajaxCardsAttribute.action",
				data: {id: $cardsTypeId.val()},
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				beforeSend: function() {
					$cardsTypeLoadingIcon.show();
					$("#cardsAttributeTable .cardsAttributeTr").remove();
				},
				success: function(data) {
					if (data != null) {
						var cardsAttributeTrHtml = "";
						$.each(data, function(i) {
							if (data[i].attributeType == "filter") {
								var optionHtml = '<option value="">请选择...</option>';
								$.each(data[i].optionList, function(j) {
									<@compress single_line = true>
										optionHtml += 
										'<option value="' + data[i].optionList[j] + '">'
											 + data[i].optionList[j] + 
										'</option>';
									</@compress>
								});
								<@compress single_line = true>
									cardsAttributeTrHtml += 
									'<tr class="cardsAttributeTr">
										<th>' + data[i].name + ': </th>
										<td>
											<select name="cardsAttributeValueMap[\'' + data[i].id + '\']">
												' + optionHtml + '
											</select>
										</td>
									</tr>';
								</@compress>
							} else {
								<@compress single_line = true>
									cardsAttributeTrHtml += 
									'<tr class="cardsAttributeTr">
										<th>' + data[i].name + ': </th>
										<td>
											<input type="text" name="cardsAttributeValueMap[\'' + data[i].id + '\']" class="formText" />
										</td>
									</tr>';
								</@compress>
							}
						});
						$cardsAttributeTable.append(cardsAttributeTrHtml);
						$cardsAttributeTable.show();
					} else {
						$cardsAttributeTable.hide();
					}
				},
				complete: function() {
					$cardsTypeLoadingIcon.hide();
				}
			});
			
			$.ajax({
				url: "cards!ajaxCardsParameter.action",
				data: {id: $cardsTypeId.val()},
				type: "POST",
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$cardsTypeLoadingIcon.show();
					$("#cardsParameterTable .cardsParameterTr").remove();
				},
				success: function(data) {
					if (data != null) {
						var cardsParameterTrHtml = "";
						$.each(data, function(i) {
							<@compress single_line = true>
								cardsParameterTrHtml += 
								'<tr class="cardsParameterTr">
									<th>' + data[i].name + ': </th>
									<td>
										<input type="text" name="cardsParameterValueMap[\'' + data[i].id + '\']" class="formText" />
									</td>
								</tr>';
							</@compress>
						});
						$cardsParameterTable.append(cardsParameterTrHtml);
						$cardsParameterTable.show();
					} else {
						$cardsParameterTable.hide();
					}
				},
				complete: function() {
					$cardsTypeLoadingIcon.hide();
				}
			});
		}
		
		function cardsTypeReset() {
			$cardsTypeId.val(previousCardsTypeId);
		}
	});
	
	// 表单验证
	$cardsForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"cards.cardsCategory.id": "required",
			"cards.name": "required",
			"cards.cardsSn": {
				remote: "cards!checkCardsSn.action<#if (cards.cardsSn)??>?oldValue=${cards.cardsSn?url}</#if>"
			},
			"defaultProduct.productSn": {
				remote: "cards!checkProductSn.action<#if (defaultProduct.productSn)??>?oldValue=${defaultProduct.productSn?url}</#if>"
			},
			"cards.score": {
				required: true,
				digits: true
			},
			"defaultProduct.price": {
				priceRequired: true,
				priceMin: 0
			},
			"defaultProduct.cost": {
				costMin: 0
			},
			"defaultProduct.marketPrice": {
				marketPriceMin: 0
			},
			"defaultProduct.weight": "weightDigits",
			"defaultProduct.store": "storeDigits"
		},
		messages: {
			"cards.cardsCategory.id": "请选择充值卡分类",
			"cards.name": "请填写充值卡名称",
			"cards.cardsSn": "充值卡编号已存在",
			"defaultProduct.productSn": "货号已存在",
			"cards.score": {
				required: "请填写积分",
				digits: "积分必须为零或正整数"
			}
		},
		submitHandler: function(form) {
			if ($isSpecificationEnabled.attr("checked") == true) {
				if ($("#specificationSelect input:checked").size() == 0) {
					$.dialog({type: "warn", content: "请选择至少一种充值卡规格!", modal: true, autoCloseTime: 3000});
					return false;
				}
				if ($("#productTable .productTr").size() == 0) {
					$.dialog({type: "warn", content: "请选择至少添加一种货品!", modal: true, autoCloseTime: 3000});
					return false;
				}
				if ($("#productTable input.productListIsDefault:checked").length == 0) {
					$.dialog({type: "warn", content: "必须选择一个默认货品!", modal: true, autoCloseTime: 3000});
					return false;
				}
				
				var isProductSnRepeat = false;
				var productSnArray = new Array();
				$("#productTable input.productListProductSn").each(function(i) {
					var $this = $(this);
					if ($.inArray($this.val(), productSnArray) >= 0) {
						isProductSnRepeat = true;
						return false;
					}
					if ($this.val() != "") {
						productSnArray.push($this.val());
					}
				});
				if (isProductSnRepeat) {
					$.dialog({type: "warn", content: "货号重复,请检查!", modal: true, autoCloseTime: 3000});
					return false;
				}
				
				var isSpecificationValueIdsRepeat = false;
				var specificationValueIdsStringArray = new Array();
				$("#productTable tr:gt(0)").each(function(i) {
					var specificationValueIdsString = "";
					$(this).find("select").each(function(i) {
						specificationValueIdsString += $(this).val() + ",";
					});
					if ($.inArray(specificationValueIdsString, specificationValueIdsStringArray) >= 0) {
						isSpecificationValueIdsRepeat = true;
						return false;
					}
					if (specificationValueIdsString != "") {
						specificationValueIdsStringArray.push(specificationValueIdsString);
					}
				});
				if (isSpecificationValueIdsRepeat) {
					$.dialog({type: "warn", content: "货品规格值重复,请检查!", modal: true, autoCloseTime: 3000});
					return false;
				}
			}
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	<#if (cards.isSpecificationEnabled)!>
		<#list cards.productSet as product>
			var $productListProductSn${product_index} = $("#productListProductSn${product_index}");
			$productListProductSn${product_index}.rules("add", {
				remote: {
					url: "cards!checkProductSn.action?oldValue=${product.productSn?url}",
					type: "post",
					cache: false,
					dataType: "json",
					data: {
						"defaultProduct.productSn": function () {
							return $productListProductSn${product_index}.val();
						}
					}
				},
				messages: {
					remote: "货号已存在"
				}
			});
		</#list>
	</#if>
	
	// 表单验证
	$.validator.addMethod("cardsImageFileListRequired", $.validator.methods.required, "请选择上传充值卡图片");
	$.validator.addMethod("cardsImageFileListImageFile", $.validator.methods.imageFile, "充值卡图片格式错误");
	$.validator.addMethod("cardsImageOrderListDigits", $.validator.methods.digits, "充值卡图片排序必须为零或正整数");
	$.validator.addMethod("priceRequired", $.validator.methods.required, "请填写销售价");
	$.validator.addMethod("priceMin", $.validator.methods.min, "销售价必须为零或正数");
	$.validator.addMethod("costMin", $.validator.methods.min, "成本价必须为零或正数");
	$.validator.addMethod("marketPriceMin", $.validator.methods.min, "市场价必须为零或正数");
	$.validator.addMethod("weightDigits", $.validator.methods.digits, "重量必须为零或正整数");
	$.validator.addMethod("storeDigits", $.validator.methods.digits, "库存必须为零或正整数");
	
	$.validator.addClassRules("cardsImageFileList", {
		cardsImageFileListRequired: true,
		cardsImageFileListImageFile: true
	});
	$.validator.addClassRules("cardsImageOrderList", {
		cardsImageOrderListDigits: true
	});
	$.validator.addClassRules("productListPrice", {
		priceRequired: true,
		priceMin: 0
	});
	$.validator.addClassRules("productListCost", {
		costMin: 0
	});
	$.validator.addClassRules("productListMarketPrice", {
		marketPriceMin: 0
	});
	$.validator.addClassRules("productListWeight", {
		weightDigits: true
	});
	$.validator.addClassRules("productListStore", {
		storeDigits: true
	});
	
})
</script>
<#if (cards.isSpecificationEnabled)!>
	<style type="text/css">
		.specificationDisabledInfo {
			display: none;
		}
	</style>
</#if>
</head>
<body class="input cards">
	<div class="bar">
		<#if isAddAction>添加充值卡<#else>编辑充值卡</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="cardsForm" action="<#if isAddAction>cards!save.action<#else>cards!update.action</#if>" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${id}" />
			<#if (!cards.isSpecificationEnabled)!>
				<input type="hidden" name="defaultProduct.id" value="${(defaultProduct.id)!}" />
			</#if>
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus />
				</li>
				<li>
					<input type="button" value="充值卡描述" hidefocus />
				</li>
				<li>
					<input type="button" value="充值卡图片" hidefocus />
				</li>
				<li>
					<input type="button" id="specificationTab" value="充值卡规格" hidefocus />
				</li>
			</ul>
			<table id="infoTable" class="inputTable tabContent">
				<tr>
					<th>
						充值卡名称: 
					</th>
					<td>
						<input type="text" name="cards.name" class="formText" value="${(cards.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						充值卡编号: 
					</th>
					<td>
						<input type="text" name="cards.cardsSn" class="formText" value="${(cards.cardsSn)!}" title="若留空则由系统自动生成" />
					</td>
				</tr>
				<tr class="specificationDisabledInfo">
					<th>
						货号: 
					</th>
					<td>
						<#if (!cards.isSpecificationEnabled)!>
							<input type="text" name="defaultProduct.productSn" class="formText" value="${(defaultProduct.productSn)!}" title="若留空则由系统自动生成" />
						<#else>
							<input type="text" name="defaultProduct.productSn" class="formText" title="若留空则由系统自动生成" />
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						品牌: 
					</th>
					<td>
						<select name="brandId">
							<option value="">请选择...</option>
							<#list allBrandList as brand>
								<option value="${brand.id}"<#if (brand == cards.brand)!> selected</#if>>
									${brand.name}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<#if setting.scoreType == "cardsSet">
					<tr>
						<th>
							积分: 
						</th>
						<td>
							<input type="text" name="cards.score" class="formText" value="${(cards.score)!"0"}" />
						</td>
					</tr>
				</#if>
				<tr class="specificationDisabledInfo">
					<th>
						销售价: 
					</th>
					<td>
						<input type="text" name="defaultProduct.price" class="formText" value="${(defaultProduct.price)!"0"}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<label>
							<@checkbox id="cardsIsMarketable" name="cards.isMarketable" value="${(cards.isMarketable)!true}" />上架
						</label>
						<label>
							<@checkbox name="cards.isBest" value="${(cards.isBest)!false}" />精品
						</label>
						<label>
							<@checkbox name="cards.isNew" value="${(cards.isNew)!false}" />新品
						</label>
						<label>
							<@checkbox name="cards.isHot" value="${(cards.isHot)!false}" />热销
						</label>
					</td>
				</tr>
				<tr>
					<th>
						排序: 
					</th>
					<td>
						<input type="text" name="cards.orderList" class="formText" value="${(cards.orderList)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						页面关键词: 
					</th>
					<td>
						<input type="text" name="cards.metaKeywords" class="formText" value="${(cards.metaKeywords)!}" />
					</td>
				</tr>
				<tr>
					<th>
						页面描述: 
					</th>
					<td>
						<textarea name="cards.metaDescription" class="formTextarea">${(cards.metaDescription)!}</textarea>
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<td>
						<textarea id="editor" name="cards.introduction" class="editor" style="width: 100%;">${(cards.introduction)!}</textarea>
					</td>
				</tr>
			</table>
			<table id="cardsImageTable" class="inputTable tabContent">
				<tr class="noneHover">
					<td colspan="5">
						<input type="button" id="addCardsImageButton" class="formButton" value="增加图片" hidefocus />
					</td>
				</tr>
				<tr class="title">
					<th>
						上传图片
					</th>
					<th>
						图片描述
					</th>
					<th>
						排序
					</th>
					<th>
						删除
					</th>
				</tr>
				<#list (cards.cardsImageList)! as cardsImage>
					<tr class="cardsImageTr">
						<td>
							<input type="hidden" name="cardsImageList[${cardsImage_index}].id" value="${cardsImage.id}" />
							<input type="hidden" name="cardsImageList[${cardsImage_index}].path" value="${cardsImage.path}" />
							<input type="hidden" name="cardsImageList[${cardsImage_index}].sourceImageFormatName" value="${cardsImage.sourceImageFormatName}" />
							<a href="${base}${cardsImage.thumbnailImagePath}" title="点击查看" target="_blank">
								<img src="${base}${cardsImage.thumbnailImagePath}" style="width: 50px; height: 50px;" />
							</a>
						</td>
						<td>
							<input type="text" name="cardsImageList[${cardsImage_index}].description" class="formText" value="${cardsImage.description}" />
						</td>
						<td>
							<input type="text" name="cardsImageList[${cardsImage_index}].orderList" class="formText cardsImageOrderList" value="${cardsImage.orderList}" style="width: 50px;" />
						</td>
						<td>
							<span class="deleteIcon deleteCardsImage" title="删 除">&nbsp;</span>
						</td>
					</tr>
				</#list>
			</table>
			<div class="tabContent">
				<table id="specificationTable" class="inputTable">
					<tr class="noneHover">
						<td colspan="9">
							<label class="red">
								<@checkbox id="isSpecificationEnabled" name="cards.isSpecificationEnabled" value="${(cards.isSpecificationEnabled)!false}" />启用充值卡规格
							</label>
						</td>
					</tr>
					<tr class="title">
						<th colspan="9">
							请选择充值卡规格<span id="specificationValueLoadingIcon" class="loadingIcon hidden">&nbsp;</span>
						</th>
					</tr>
					<tr class="noneHover">
						<td colspan="9">
							<div id="specificationSelect" class="specificationSelect">
								<ul>
									<#assign specificationSet = (cards.specificationSet)! />
									<#list allSpecificationList as specification>
										<li>
											<label title="充值卡规格值: <#list specification.specificationValueList as specificationValue>${specificationValue.name}&nbsp;</#list>">
												<input type="checkbox" name="specificationIds" value="${specification.id}"<#if (specificationSet.contains(specification))!> checked</#if><#if (!cards.isSpecificationEnabled)!true> disabled</#if> />${specification.name}
												<#if specification.memo??>
													<span class="gray">[${specification.memo}]</span>
												</#if>
											</label>
										</li>
									</#list>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="noneHover">
						<td colspan="9">
							<input type="button" id="addProductButton" class="formButton" value="增加货品"<#if (!cards.isSpecificationEnabled)!true> disabled</#if> hidefocus />
						</td>
					</tr>
				</table>
				<table id="productTable" class="inputTable">
					<tr id="productTitle" class="title">
						<th>
							货号
						</th>
						<#list (cards.specificationSet)! as specification>
							<th class="${specification.id}">
								${specification.name}
							</th>
						</#list>
						<th id="specificationInsertTh">
							销售价
						</th>
						<th>
							默认
						</th>
						<th>
							上架
						</th>
						<th>
							删除
						</th>
					</tr>
					<#if (cards.isSpecificationEnabled)!>
						<#list (cards.productSet)! as product>
							<tr class="productTr" productIndex="${product_index}">
								<td>
									<input type="hidden" name="productList[${product_index}].id" class="formText" value="${product.id}" />
									<input type="text" id="productListProductSn${product_index}" name="productList[${product_index}].productSn" class="formText productListProductSn" value="${product.productSn}" style="width: 50px;" title="留空则由系统自动生成" />
								</td>
								<#list (cards.specificationSet)! as specification>
									<td class="${specification.id}">
										<select name="specificationValueIdsList[${product_index}]">
											<#list specification.specificationValueList as specificationValue>
												<option value="${specificationValue.id}"<#if product.specificationValueList.contains(specificationValue)> selected</#if>>
													${specificationValue.name}
												</option>
											</#list>
										</select>
									</td>
								</#list>
								<td class="specificationInsertTd">
									<input type="text" name="productList[${product_index}].price" class="formText productListPrice" value="${product.price}" style="width: 50px;" />
								</td>
								<td>
									<@checkbox name="productList[${product_index}].isDefault" cssClass="productListIsDefault" value="${(product.isDefault)!false}" />
								</td>
								<td>
									<@checkbox name="productList[${product_index}].isMarketable" cssClass="productListIsMarketable" value="${(product.isMarketable)!true}" />
								</td>
								<td>
									<#assign hasUnfinishedOrder = false />
									<#assign orderSet = product.orderSet />
									<#if (orderSet?? && orderSet?size > 0)>
										<#list orderSet as order>
											<#if order.OrderStatus != "completed" && order.orderStatus != "invalid">
												<#assign hasUnfinishedOrder = true />
												<#break />
											</#if>
										</#list>
									</#if>
									<#if hasUnfinishedOrder>
										<span class="deleteIcon" title="存在未完成订单,无法删除" disabled>&nbsp;</span>
									<#else>
										<span class="deleteIcon deleteProduct" title="删 除">&nbsp;</span>
									</#if>
								</td>
							</tr>
						</#list>
					</#if>
				</table>
			</div>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>