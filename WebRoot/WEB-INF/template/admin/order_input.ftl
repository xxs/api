<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑订单 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	var $tab = $("#tab");
	var $areaSelect = $("#areaSelect");
	var $totalProductQuantity = $("#totalProductQuantity");
	var $totalProductPrice = $("#totalProductPrice");
	var $deliveryFee = $("#deliveryFee");
	var $paymentFee = $("#paymentFee");
	var $totalAmount = $("#totalAmount");
	var $productPrice = $("#orderItemTable input.productPrice");
	var $productQuantity = $("#orderItemTable input.productQuantity");
	var $deleteOrderItem = $("#orderItemTable .deleteOrderItem");
	
	var totalProductPrice = parseFloat("${order.totalProductPrice}");// 充值卡总价格
	var deliveryFee = parseFloat("${order.deliveryFee}");// 配送费用
	var paymentFee = parseFloat("${order.paymentFee}");// 支付费用

	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	// 地区选择菜单
	$areaSelect.lSelect({
		url: "${base}/card/area!ajaxArea.action"// AJAX数据获取url
	});
	
	// 修改充值卡总金额
	function modifyTotalProductPrice() {
		totalProductPrice = 0.0;
		$("#orderItemTable input.productPrice").each(function(){
			var $this = $(this);
			var productPrice = $this.val();
			var productQuantity = $this.parent().parent().find("input.productQuantity").val();
			totalProductPrice = floatAdd(totalProductPrice, floatMul(productPrice, productQuantity));
		});
		$totalProductPrice.text(currencyFormat(totalProductPrice));
	}
	
	// 修改订单总金额
	function modifyTotalAmount() {
		var totalAmount = floatAdd(floatAdd(totalProductPrice, deliveryFee), paymentFee);
		$totalAmount.text(currencyFormat(totalAmount));
	}
	
	// 根据配送费用修改订单总金额
	$deliveryFee.change( function() {
		$this = $(this);
		var deliveryFeeValue = $this.val();
		if (!/^(([0-9]+\.?[0-9]+)|[0-9])$/.test(deliveryFeeValue)) {
			$this.val(deliveryFee);
			$.message({type: "warn", content: "配送费用输入有误!"});
		} else {
			deliveryFee = deliveryFeeValue;
			modifyTotalAmount();
		}
	});
	
	// 根据支付费用修改订单总金额
	$paymentFee.change( function() {
		$this = $(this);
		var paymentFeeValue = $this.val();
		if (!/^(([0-9]+\.?[0-9]+)|[0-9])$/.test(paymentFeeValue)) {
			$this.val(paymentFee);
			$.message({type: "warn", content: "支付手续费输入有误!"});
		} else {
			paymentFee = paymentFeeValue;
			modifyTotalAmount();
		}
	});
	
	// 记录初始充值卡价格
	$productPrice.each(function(){
		$this = $(this);
		$this.data("previousProductPrice", $this.val());
	});
	
	// 记录初始充值卡购买数
	$productQuantity.each(function(){
		$this = $(this);
		$this.data("previousProductQuantity", $this.val());
	});
	
	// 充值卡价格修改
	$productPrice.change( function() {
		$this = $(this);
		var productPriceValue = $this.val();
		if (!/^(([0-9]+\.?[0-9]+)|[0-9])$/.test(productPriceValue)) {
			var previousProductPrice = $this.data("previousProductPrice");
			$this.val(previousProductPrice);
			$.message({type: "warn", content: "充值卡价格输入有误!"});
		} else {
			$this.data("previousProductPrice", productPriceValue);
			modifyTotalProductPrice();
			modifyTotalAmount();
		}
	});
	
	// 充值卡数量修改
	$productQuantity.change( function() {
		$this = $(this);
		var productQuantityValue = $this.val();
		var availableStore = $this.attr("availableStore");
		if (!/^[0-9]*[1-9][0-9]*$/.test(productQuantityValue)) {
			var previousProductQuantity = $this.data("previousProductQuantity");
			$this.val(previousProductQuantity);
			$.message({type: "warn", content: "充值卡数量输入有误!"});
		} else {
			if (availableStore != null && parseInt(productQuantityValue) > parseInt(availableStore)) {
				var previousProductQuantity = $this.data("previousProductQuantity");
				$this.val(previousProductQuantity);
				$.message({type: "warn", content: "充值卡数量超出可用库存数!"});
				return false;
			}
			$this.data("previousProductQuantity", productQuantityValue);
			modifyTotalProductPrice();
			modifyTotalAmount();
		}
	});
	
	// 删除订单项
	$deleteOrderItem.click( function() {
		$this = $(this);
		if ($("#orderItemTable input.productPrice").size() <= 1) {
			$.dialog({type: "warn", content: "请保留至少一个充值卡!", modal: true, autoCloseTime: 2000});
			return false;
		}
		$.dialog({type: "warn", content: "您确定要删除吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: deleteOrderItem});
		function deleteOrderItem() {
			$this.parent().parent().remove();
			modifyTotalProductPrice();
			modifyTotalAmount();
		}
		return false;
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		onfocusout: false,
		onkeyup: false,
		onclick: false,
		rules: {
			"order.deliveryFee": {
				required: true,
				min: 0
			},
			"order.paymentFee": {
				required: true,
				min: 0
			},
			"order.totalProductWeight": {
				required: true,
				min: 0
			},
			"order.shipName": "required",
			"shipAreaId": "required",
			"order.shipAddress": "required",
			"order.shipMobile": {
				requiredOne: "#orderShipPhone"
			}
		},
		messages: {
			"order.deliveryFee": {
				required: "请填写配送费用",
				min: "配送费用必须为零或正数"
			},
			"order.paymentFee": {
				required: "请填写支付手续费",
				min: "支付手续费必须为零或正数"
			},
			"order.totalProductWeight": {
				required: "请填写充值卡总重量",
				min: "充值卡总重量必须为零或正数"
			},
			"order.shipName": "请填写收货人姓名",
			"shipAreaId": "请选择收货地区",
			"order.shipAddress": "请填写收货地址",
			"order.shipMobile": {
				requiredOne: "电话、手机必须填写其中一项"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	$.validator.addMethod("priceRequired", $.validator.methods.required, "请填写充值卡价格");
	$.validator.addMethod("priceMin", $.validator.methods.min, "充值卡价格必须为零或正数");
	$.validator.addMethod("productQuantityPositiveInteger", $.validator.methods.positiveInteger, "充值卡数量必须为正整数");
	
	$.validator.addClassRules("productPrice", {
		priceRequired: true,
		priceMin: 0
	});
	
	$.validator.addClassRules("productQuantity", {
		productQuantityPositiveInteger: true
	});

});
</script>
</head>
<body class="input">
	<div class="bar">编辑订单</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="order!update.action" method="post">
			<input type="hidden" name="id" value="${order.id}" />
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="订单信息" hidefocus />
				</li>
				<li>
					<input type="button" value="充值卡信息" hidefocus />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						订单编号: 
					</th>
					<td>
						${order.orderSn}
					</td>
					<th>
						下单时间: 
					</th>
					<td>
						${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
					</td>
				</tr>
				<tr>
					<th>
						充值卡总金额: 
					</th>
					<td>
						<span id="totalProductPrice" class="red">${order.totalProductPrice?string(currencyFormat)}</span>
					</td>
					<th>
						订单总金额: 
					</th>
					<td>
						<span id="totalAmount" class="red">${order.totalAmount?string(currencyFormat)}</span>
					</td>
				</tr>
				<tr>
					<th>
						配送方式: 
					</th>
					<td>
						<select name="deliveryType.id">
							<#list allDeliveryTypeList as deliveryType>
								<option value="${deliveryType.id}"<#if (deliveryType == order.deliveryType)!> selected</#if>>
									${deliveryType.name}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
					<th>
						支付方式: 
					</th>
					<td>
						<select name="paymentConfig.id">
							<option value="">货到付款</option>
							<#list allPaymentConfigList as paymentConfig>
								<option value="${paymentConfig.id}"<#if (paymentConfig == order.paymentConfig)!> selected</#if>>
									${paymentConfig.name}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						配送费用: 
					</th>
					<td>
						<input type="text" id="deliveryFee" name="order.deliveryFee" class="formText" value="${order.deliveryFee}" />
						<label class="requireField">*</label>
					</td>
					<th>
						支付手续费: 
					</th>
					<td>
						<input type="text" id="paymentFee" name="order.paymentFee" class="formText" value="${order.paymentFee}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						充值卡总重量: 
					</th>
					<td>
						<input type="text" name="order.totalProductWeight" class="formText" value="${order.totalProductWeight}" title="单位: 克" />
					</td>
					<th>
						附言: 
					</th>
					<td>
						${(order.memo)!}
					</td>
				</tr>
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<th>
						收货人姓名: 
					</th>
					<td>
						<input type="text" name="order.shipName" class="formText" value="${order.shipName}" />
					</td>
					<th>
						收货地区: 
					</th>
					<td>
						<input type="text" id="areaSelect" name="shipAreaId" class="hidden" value="${(order.shipArea.id)!}" defaultSelectedPath="${(order.shipArea.path)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						收货地址: 
					</th>
					<td>
						<input type="text" name="order.shipAddress" class="formText" value="${order.shipAddress}" />
					</td>
					<th>
						邮编: 
					</th>
					<td>
						<input type="text" name="order.shipZipCode" class="formText" value="${order.shipZipCode}" />
					</td>
				</tr>
				<tr>
					<th>
						电话: 
					</th>
					<td>
						<input type="text" id="orderShipPhone" name="order.shipPhone" class="formText" value="${order.shipPhone}" />
					</td>
					<th>
						手机: 
					</th>
					<td>
						<input type="text" name="order.shipMobile" class="formText" value="${order.shipMobile}" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>
				<#if order.member??>
					<#assign member = order.member />
					<tr>
						<th>
							用户名: 
						</th>
						<td>
							${member.username}
						</td>
						<th>
							会员等级: 
						</th>
						<td>
							${member.memberRank.name}
							<#if member.memberRank.preferentialScale != 100>
								<span class="red">[优惠百分比: ${member.memberRank.preferentialScale}%]</span>
							</#if>
						</td>
					</tr>
					<tr>
						<th>
							E-mail: 
						</th>
						<td>
							${member.email}
						</td>
						<th>
							注册日期: 
						</th>
						<td>
							${member.createDate}
						</td>
					</tr>
					<tr>
						<th>
							最后登录日期: 
						</th>
						<td>
							${member.loginDate}
						</td>
						<th>
							最后登录IP: 
						</th>
						<td>
							${member.loginIp}
						</td>
					</tr>
					<tr>
						<th>
							预存款余额: 
						</th>
						<td>
							${member.deposit?string(currencyFormat)}
						</td>
						<th>
							积分: 
						</th>
						<td>
							${member.score}
						</td>
					</tr>
				<#else>
					<tr>
						<th>
							会员状态: 
						</th>
						<td colspan="3">
							<span class="red">会员不存在</span>
						</td>
					</tr>
				</#if>
			</table>
			<table id="orderItemTable" class="inputTable tabContent">
				<tr class="title">
					<th>货号</th>
					<th>充值卡名称</th>
					<th>价格</th>
					<th>购买数量</th>
					<th>删除</th>
				</tr>
				<#list order.orderItemSet as orderItem>
					<#assign product = orderItem.product />
					<tr>
						<td>
							<input type="hidden" name="orderItemList[${orderItem_index}].id" value="${orderItem.id}" />
							<a href="${base}${orderItem.cardsHtmlPath}" target="_blank">
								${orderItem.productSn}
							</a>
						</td>
						<td>
							<a href="${base}${orderItem.cardsHtmlPath}" target="_blank">
								${orderItem.productName}
							</a>
						</td>
						<td>
							<input type="text" name="orderItemList[${orderItem_index}].productPrice" class="formText productPrice" value="${orderItem.productPrice}" style="width: 50px;" />
						</td>
						<td>
							<#if product.store??>
								<#if (setting.storeFreezeTime == "payment" && order.paymentStatus == "unpaid") || (setting.storeFreezeTime == "ship" && order.shippingStatus == "unshipped")>
									<#assign availableStore = product.store - product.freezeStore />
								<#else>
									<#assign availableStore = product.store - product.freezeStore + orderItem.totalProductQuantity />
								</#if>
							</#if>
							<input type="text" name="orderItemList[${orderItem_index}].productQuantity" class="formText productQuantity"<#if product.store??> availableStore="${availableStore}"</#if> value="${orderItem.productQuantity}" style="width: 50px;" />
						</td>
						<td>
							<a href="javascript: void(0);" class="deleteOrderItem">删除</a>
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