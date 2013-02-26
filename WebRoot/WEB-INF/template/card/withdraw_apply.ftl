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
			<div class="fangz">提现申请</a></div>
			<div class="red">注：请一定正确选择卡面值提交,否则造成损失商户自行承担； </div>
			<div class="hei">卡信息提交成功后，可在<a href="#">订单查询</a>页面查询支付结果。处理结果以订单查询页为准。</div>
			<div class="memberCenter">
			<form id="withdrawRechargeForm" action="withdraw!save.action" method="post">
						<input type="hidden" name="paymentType" value="recharge" />
						<table class="inputTable">
							<tr>
								<th>
									当前金额: 
								</th>
								<td>
									${loginMember.deposit?string(currencyFormat)}
								</td>
							</tr>
							<tr>
								<th>
									提现账户: 
								</th>
								<td>
										<#list loginMember.memberBankSet as memberBank>
											<input type="radio" name="memberBank.id" value="${memberBank.id}" <#if memberBank.isDefault >checked="checked"</#if> />${memberBank.banknum}(开户名：${memberBank.openname},开户银行:${memberBank.bankname})<br />
										</#list>
										<div class="hei">没有找到合适的账户，<a href="bank!add.action?redirectUrl=withdraw!apply.action">点击添加！</a>(最多添加5个账户，请谨慎操作)</div>
								</td>
							</tr>
							<tr>
								<th>
									提现金额: 
								</th>
								<td>
									<input type="text" name="withdraw.money" class="formText" />
									<label class="requireField">*</label>
								</td>
							</tr>
							<tr>
								<th>
									&nbsp;
								</th>
								<td>
									<input type="submit" class="formButton" value="申  请" hidefocus />
								</td>
							</tr>
						</table>
					</form>
				</div>
		</div>
	</div>
	</div>
</div>
<div class="clear"></div>
	<#include "/WEB-INF/template/card/member_footer.ftl">
</body>
</html>