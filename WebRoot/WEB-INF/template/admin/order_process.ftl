<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>处理订单 - XXS</title>
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
	var $paymentTabButton = $("#paymentTabButton");
	var $shippingTabButton = $("#shippingTabButton");
	var $refundTabButton = $("#refundTabButton");
	var $reshipTabButton = $("#reshipTabButton");

	var $areaSelect = $(".areaSelect");
	var $paymentForm = $("#paymentForm");
	var $shippingForm = $("#shippingForm");
	var $refundForm = $("#refundForm");
	var $reshipForm= $("#reshipForm");
	var $paymentProcessButton = $("#paymentProcessButton");
	var $shippingProcessButton = $("#shippingProcessButton");
	var $completedProcessButton = $("#completedProcessButton");
	var $refundProcessButton = $("#refundProcessButton");
	var $reshipProcessButton = $("#reshipProcessButton");
	var $invalidProcessButton = $("#invalidProcessButton");
	var $shippingDeliveryQuantity = $("#shippingForm .shippingDeliveryQuantity");
	var $reshipDeliveryQuantity = $("#reshipForm .reshipDeliveryQuantity");
	
	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	// 地区选择菜单
	$areaSelect.lSelect({
		url: "${base}/card/area!ajaxArea.action"// AJAX数据获取url
	});

	var tabs = $("ul.tab").tabs();
	
	// 订单支付
	$paymentProcessButton.click( function() {
		tabs.click(2);
	});
	
	// 订单发货
	$shippingProcessButton.click( function() {
		tabs.click(3);
	});
	
	// 订单完成
	$completedProcessButton.click( function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "订单完成后将不允许对此订单进行任何操作,确认执行?", ok: "确 定", cancel: "取 消", width: 420, modal: true, okCallback: orderCompleted});
		function orderCompleted() {
			$.ajax({
				url: "order!completed.action",
				data: {id: "${order.id}"},
				type: "POST",
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$this.attr("disabled", true);
				},
				success: function(data) {
					$.message({type: data.status, content: data.message});
					if (data.status == "success") {
						$paymentTabButton.attr("disabled", true);
						$shippingTabButton.attr("disabled", true);
						$refundTabButton.attr("disabled", true);
						$reshipTabButton.attr("disabled", true);
						
						$paymentProcessButton.attr("disabled", true);
						$shippingProcessButton.attr("disabled", true);
						$completedProcessButton.attr("disabled", true);
						$refundProcessButton.attr("disabled", true);
						$reshipProcessButton.attr("disabled", true);
						$invalidProcessButton.attr("disabled", true);
					} else {
						$this.attr("disabled", true);
					}
				}
			});
		}
	});
	
	// 退款
	$refundProcessButton.click( function() {
		tabs.click(4);
	});
	
	// 退货
	$reshipProcessButton.click( function() {
		tabs.click(5);
	});
	
	// 作废
	$invalidProcessButton.click( function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "订单作废后将不允许对此订单进行任何操作,确认执行?", ok: "确 定", cancel: "取 消", modal: true, okCallback: orderInvalid});
		function orderInvalid() {
			$.ajax({
				url: "order!invalid.action",
				data: {id: "${order.id}"},
				type: "POST",
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$this.attr("disabled", true);
				},
				success: function(data) {
					$.message({type: data.status, content: data.message});
					if (data.status == "success") {
						$paymentTabButton.attr("disabled", true);
						$shippingTabButton.attr("disabled", true);
						$refundTabButton.attr("disabled", true);
						$reshipTabButton.attr("disabled", true);
						
						$paymentProcessButton.attr("disabled", true);
						$shippingProcessButton.attr("disabled", true);
						$completedProcessButton.attr("disabled", true);
						$refundProcessButton.attr("disabled", true);
						$reshipProcessButton.attr("disabled", true);
						$invalidProcessButton.attr("disabled", true);
					} else {
						$this.attr("disabled", false);
					}
				}
			});
		}
	});
	
	// 订单支付表单验证
	$paymentForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"payment.totalAmount": {
				required: true,
				positive: true,
				max: ${order.amount - order.paidAmount}
			}
		},
		messages: {
			"payment.totalAmount": {
				required: "请输入付款金额",
				positive: "付款金额必须为正数",
				max: "付款金额必须小于等于${order.amount - order.paidAmount}"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	// 发货数修改
	$shippingDeliveryQuantity.change( function() {
		var $this = $(this);
		var maxDeliveryQuantity = $this.attr("maxDeliveryQuantity");
		if (/^[0-9]*[1-9][0-9]*$/.test($this.val()) && parseInt($this.val()) > parseInt(maxDeliveryQuantity)) {
			$.dialog({type: "warn", content: "本次发货数超出应发货数!", modal: true, autoCloseTime: 2000});
			$this.val(maxDeliveryQuantity);
			return false;
		}
	});
	
	// 订单发货表单验证
	$shippingForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"shipping.deliveryFee": {
				required: true,
				min: 0
			},
			"shipping.shipName": "required",
			"shipAreaId": "required",
			"shipping.shipAddress": "required",
			"shipping.shipZipCode": "required",
			"shipping.shipMobile": {
				"requiredOne": "#shipPhone"
			}
		},
		messages: {
			"shipping.deliveryFee": {
				required: "请输入配送费用",
				min: "配送费用必须为零或正数"
			},
			"shipping.shipName": "请输入收货人姓名",
			"shipAreaId": "请选择收货地区",
			"shipping.shipAddress": "请输入收货地址",
			"shipping.shipZipCode": "请输入邮编",
			"shipping.shipMobile": {
				"requiredOne": "电话、手机必须填写其中一项"
			}
		},
		submitHandler: function(form) {
			var totalShippingDeliveryQuantity = 0;
			$shippingDeliveryQuantity.each(function(){
				var $this = $(this);
				totalShippingDeliveryQuantity += $this.val();
			});
			if (totalShippingDeliveryQuantity == 0) {
				$.dialog({type: "warn", content: "发货总数不允许为0!", modal: true, autoCloseTime: 2000});
			} else {
				$(form).find(":submit").attr("disabled", true);
				form.submit();
			}
		}
	});
	
	$.validator.addMethod("shippingDeliveryQuantityRequired", $.validator.methods.required, "请填写本次发货数");
	$.validator.addMethod("shippingDeliveryQuantityDigits", $.validator.methods.digits, "本次发货数必须为零或正整数");
	
	$.validator.addClassRules("shippingDeliveryQuantity", {
		shippingDeliveryQuantityRequired: true,
		shippingDeliveryQuantityDigits: 0
	});
	
	// 订单退款表单验证
	$refundForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"refund.totalAmount": {
				required: true,
				positive: true,
				max: ${order.paidAmount}
			}
		},
		messages: {
			"refund.totalAmount": {
				required: "请输入退款金额",
				positive: "退款金额必须为正数",
				max: "退款金额必须小于等于${order.paidAmount}"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	// 退货数修改
	$reshipDeliveryQuantity.change( function() {
		var $this = $(this);
		var maxDeliveryQuantity = $this.attr("maxDeliveryQuantity");
		if (/^[0-9]*[1-9][0-9]*$/.test($this.val()) && parseInt($this.val()) > parseInt(maxDeliveryQuantity)) {
			$.dialog({type: "warn", content: "本次退货数超出已发货数!", modal: true, autoCloseTime: 2000});
			$this.val(maxDeliveryQuantity);
			return false;
		}
	});
	
	// 订单退货表单验证
	$reshipForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"reship.deliveryFee": {
				required: true,
				min: 0
			},
			"reship.reshipName": "required",
			"reshipAreaId": "required",
			"reship.reshipAddress": "required",
			"reship.reshipZipCode": "required",
			"reship.reshipMobile": {
				"requiredOne": "#reshipPhone"
			}
		},
		messages: {
			"reship.deliveryFee": {
				required: "请输入物流费用",
				min: "配送费用必须为零或正数"
			},
			"reship.reshipName": "请输入退货人姓名",
			"reshipAreaId": "请选择退货地区",
			"reship.reshipAddress": "请输入退货地址",
			"reship.reshipZipCode": "请输入邮编",
			"reship.reshipMobile": {
				"requiredOne": "电话、手机必须填写其中一项"
			}
		},
		submitHandler: function(form) {
			var totalReshipDeliveryQuantity = 0;
			$reshipDeliveryQuantity.each(function(){
				var $this = $(this);
				totalReshipDeliveryQuantity += $this.val();
			});
			if (totalReshipDeliveryQuantity == 0) {
				$.dialog({type: "warn", content: "退货总数不允许为0!", modal: true, autoCloseTime: 2000});
			} else {
				$(form).find(":submit").attr("disabled", true);
				form.submit();
			}
		}
	});
	
	$.validator.addMethod("reshipDeliveryQuantityRequired", $.validator.methods.required, "请填写本次退货数");
	$.validator.addMethod("reshipDeliveryQuantityDigits", $.validator.methods.digits, "本次退货数必须为零或正整数");
	
	$.validator.addClassRules("reshipDeliveryQuantity", {
		reshipDeliveryQuantityRequired: true,
		reshipDeliveryQuantityDigits: 0
	});

});
</script>
</head>
<body class="input">
	<div class="bar">处理订单</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="订单信息" hidefocus />
			</li>
		</ul>
		<div class="tabContent">
			<table class="inputTable">
				<tr>
					<th>
						订单状态操作: 
					</th>
					<td>
						<input type="button" id="paymentProcessButton" name="paymentProcess" class="formButton" value="订单支付"<#if order.orderStatus == "completed" || order.orderStatus == "invalid" || order.paymentStatus == "paid" || order.paymentStatus == "partRefund" || order.paymentStatus == "refunded"> disabled</#if> hidefocus />
						<input type="button" id="completedProcessButton" name="completedProcess" class="formButton" value="订单完成"<#if order.orderStatus == "completed" || order.orderStatus == "invalid"> disabled</#if> hidefocus />
						<input type="button" id="invalidProcessButton" name="invalidProcess" class="formButton" value="作废"<#if order.orderStatus == "completed" || order.orderStatus == "invalid" || order.paymentStatus != "unpaid" || order.shippingStatus != "unshipped"> disabled</#if> hidefocus />
					</td>
				</tr>
				<tr>
					<th>
						订单状态: 
					</th>
					<td colspan="3">
						<span class="red">
							[${action.getText("OrderStatus." + order.orderStatus)}]
						</span>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>
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
						<span id="amount" class="red">${order.amount?string(currencyFormat)}</span>
					</td>
					<th>
						订单总金额: 
					</th>
					<td>
						<span id="amount" class="red">${order.amount?string(currencyFormat)}</span>&nbsp;&nbsp;
						<strong class="red">[已付金额: ${order.paidAmount?string(currencyFormat)}]</strong>
					</td>
				</tr>
				<tr>
				<th>货号</th>
					<td>
						<a href="${base}${order.cardsHtmlPath}" target="_blank">
							${order.productSn}
						</a>
					</td>
					<th>充值卡名称</th>
					<td>
						<a href="${base}${order.cardsHtmlPath}" target="_blank">
							${order.productName}
						</a>
					</td>
				</tr>
					<tr>
						<th>卡号</th>
						<td>
							${order.cardNum}
						</td>
					<th>
						密码: 
					</th>
					<td>
						${order.cardPwd}
					</td>
				</tr>
					<tr>
						<th>价格</th>
						<td>
							${order.amount?string(currencyFormat)}
						</td>
					<th>
						支付方式: 
					</th>
					<td>
						${order.paymentConfigName}
					</td>
				</tr>
				<tr>
					<th>
						附言: 
					</th>
					<td colspan="3">
						${(order.memo)!}
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
					<tr>
						<td colspan="4">
							<div class="buttonArea">
								<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
							</div>
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
		</div>
	</div>
</body>
</html>