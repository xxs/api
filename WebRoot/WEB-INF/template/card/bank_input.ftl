<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑账户信息<#if setting.isShowPoweredInfo> - XXS</#if></title>
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
		<#include "/WEB-INF/template/card/menu_center.ftl">
	</div>
	<div class="contentRight">
		<div class="katong">
			<div class="fangz"><#if isAddAction>添加账户<#else>编辑账户</#if></div>
			<div class="red">注：请一定正确选择卡面值提交,否则造成损失商户自行承担； </div>
			<div class="hei">卡信息提交成功后，可在<a href="#">订单查询</a>页面查询支付结果。处理结果以订单查询页为准。</div>
			<div class="memberCenter">
			<form id="withdrawRechargeForm66" action="<#if isAddAction>bank!save.action<#else>bank!update.action</#if>" method="post">
						<input type="hidden" name="redirectUrl" value="${redirectUrl}" />
						<table class="inputTable">
							<input type="hidden" name="id" value="${id}" />
							<tr>
								<th>
									所属会员名: 
								</th>
								<td>
									<input type="hidden" name="memberBank.member.username" value="${loginMember.username}" />
									${loginMember.username}
								</td>
							</tr>
							<tr>
								<th>
									银行账号: 
								</th>
								<td>
									<input type="text" name="memberBank.banknum" class="formText" value="${(memberBank.banknum)!}" />
									<label class="requireField">*</label>
								</td>
							</tr>
							<tr>
								<th>
									开户姓名: 
								</th>
								<td>
									<input type="text" name="memberBank.openname" class="formText" value="${(memberBank.openname)!}" title="" />
									<label class="requireField">*</label>
								</td>
							</tr>
							<tr>
								<th>
									所属银行: 
								</th>
								<td>
									<input type="text" name="memberBank.bankname" class="formText" value="${(memberBank.bankname)!}" title="" />
									<label class="requireField">*</label>
								</td>
							</tr>
							<tr>
								<th>
									银行归属地: 
								</th>
								<td>
									<input type="text" name="memberBank.bankcity" class="formText" value="${(memberBank.bankcity)!}" title="" />
									<label class="requireField">*</label>
								</td>
							</tr>
							<tr>
								<th>
									所属支行: 
								</th>
								<td>
									<input type="text" name="memberBank.bankdetail" class="formText" value="${(memberBank.bankdetail)!}" title="只允许输入零或正整数" />
									<label class="requireField">*</label>
								</td>
							</tr>
							<tr>
								<th>
									设置: 
								</th>
								<td>
									<label>
										<@checkbox name="memberBank.isDefault" value="${(memberBank.isDefault)!false}" />  设置为默认
									</label>
								</td>
							</tr>
							<tr>
								<th>
									&nbsp;
								</th>
								<td>
									
									<input type="submit" class="formButton" value="提   交" hidefocus />
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