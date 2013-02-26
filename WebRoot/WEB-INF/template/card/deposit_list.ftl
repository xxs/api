<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>交易列表<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<#include "/WEB-INF/template/card/member_head.ftl">
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
							<th>操作类型</th>
							<th>存入金额</th>
							<th>支出金额</th>
							<th>当前余额</th>
							<th>日期</th>
							<th>推荐奖励单据号</th>
							<th>被推荐人</th>
						</tr>
						<#list pager.result as deposit>
							<tr>
								<td>
									<#if (deposit.payment?? && deposit.payment.order??)!>
										<a href="order!view.action?id=${deposit.payment.order.id}">
											${action.getText("DepositType." + deposit.depositType)}
										</a>
									<#else>
										${action.getText("DepositType." + deposit.depositType)}
									</#if>
								</td>
								<td>
									<#if deposit.credit != 0>
										${deposit.credit?string(currencyFormat)}
									<#else>
										-
									</#if>
								</td>
								<td>
									<#if deposit.debit != 0>
										${deposit.debit?string(currencyFormat)}
									<#else>
										-
									</#if>
								</td>
								<td>
									${deposit.balance?string(currencyFormat)}
								</td>
								<td>
									<span title="${deposit.createDate?string("yyyy-MM-dd HH:mm:ss")}">${deposit.createDate}</span>
								</td>
								<td>
									${deposit.orderSn}
								</td>
								<td>
									${deposit.referrer}
								</td>
							</tr>
						</#list>
					</table>
					<@pagination pager=pager baseUrl="/card/deposit!list.action">
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