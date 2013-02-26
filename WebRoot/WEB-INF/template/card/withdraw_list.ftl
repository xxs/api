<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>预存款提现<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<#include "/WEB-INF/template/card/member_head.ftl">
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript">
$().ready( function() {
	
	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $withdrawRechargeForm = $("#withdrawRechargeForm");
	
	// 表单验证
	$withdrawRechargeForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"rechargeAmount": {
				required: true,
				positive: true
			},
			"paymentConfig.id": "required"
		},
		messages: {
			"rechargeAmount": {
				required: "请填写充值金额",
				positive: "充值金额必须为正数"
			},
			"paymentConfig.id": "请选择支付方式"
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
});
</script>
</head>
<body class="memberCenter">
	<#include "/WEB-INF/template/card/member_header.ftl">
	<div class="content">
	<div class="contentLeft">
		<#include "/WEB-INF/template/card/menu_deposit.ftl">
	</div>
	<div class="contentRight">
		<div class="katong">
			<div class="fangz">交易明细</a></div>
			<div class="red">注：请一定正确选择卡面值提交,否则造成损失商户自行承担； </div>
			<div class="hei">卡信息提交成功后，可在<a href="#">订单查询</a>页面查询支付结果。处理结果以订单查询页为准。</div>
			<div class="memberCenter">
			<table class="listTable">
						<tr>
							<th>操作信息</th>
							<th>提现金额</th>
							<th>收款账号</th>
							<th>实发金额</th>
							<th>申请日期</th>
							<th>状态</th>
						</tr>
						<#list pager.result as withdraw>
							<tr>
								<td>
										${withdraw.message}
								</td>
								<td>
										${withdraw.money?string(currencyFormat)}
								</td>
								<td>
										${withdraw.memo}
								</td>
								<td>
										${withdraw.totalMoney?string(currencyFormat)}
									
								</td>
								<td>
									<span title="${withdraw.createDate?string("yyyy-MM-dd HH:mm:ss")}">${withdraw.createDate}</span>
								</td>
								<td>
									${action.getText("WithdrawStatus." + withdraw.withdrawStatus)}
								</td>
							</tr>
						</#list>
					</table>
					<@pagination pager=pager baseUrl="/card/withdraw!list.action">
         				<#include "/WEB-INF/template/card/pager.ftl">
         			</@pagination>
				</div>
		</div>
	</div>
	</div>
</div>
<div class="clear"></div>
	<#include "/WEB-INF/template/card/member_footer.ftl">
</body>
</html>