<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>账户列表<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<#include "/WEB-INF/template/card/member_head.ftl">
<script type="text/javascript">
$().ready( function() {
	// 修改前验证密保问题
	$(".xiugai").bind("click",function(event){
		$redirectUrl = event.target.name;
		$question = $("#question").val();
		//alert("123:"+$redirectUrl);
		$.showQuestionVerifyWindow($question,$redirectUrl);
		return false;
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
			<div class="fangz">账户列表 </div>
			<div class="red">注：请一定正确选择卡面值提交,否则造成损失商户自行承担； </div>
			<div class="hei">可点击<a class="formButton" href="bank!add.action">添加账户</a>页面添加</div>
			<input value="${member.safeQuestion}" type="hidden" id="question"/>
			<div class="memberCenter">
			<table class="listTable">
				<tr>
					<th>账号	</th>
					<th>开户名</th>
					<th>开户银行</th>
					<th>开户城市</th>
					<th>支行明细</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
					<#list member.memberBankSet as memberBank>
						<tr>
							<td>
								${memberBank.banknum}
							</td>
							<td>
								${memberBank.openname}
							</td>
							<td>
								${memberBank.bankname}
							</td>
							<td>
								${memberBank.bankcity}
							</td>
							<td>
								${memberBank.bankdetail}
							</td>
							<td>
								${memberBank.isDefault?string('是','否')}
							</td>
							<td>
								<a name="bank!check.action?id=${memberBank.id}" class="xiugai">[默认]</a>
								<a name="bank!edit.action?id=${memberBank.id}" class="xiugai">[修改]</a>
							</td>
						</tr>
					</#list>		
				</table>
			</div>
		</div>
	</div>
</div>
<div class="clear"></div>
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/card/member_footer.ftl">
</body>
</html>