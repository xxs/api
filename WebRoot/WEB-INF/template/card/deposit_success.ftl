<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>成功案例<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />

<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/card/js/base.js"></script>
<script type="text/javascript" src="${base}/template/card/js/card.js"></script>
<#include "/WEB-INF/template/card/head.ftl">
</head>
<body class="memberCenter">
	<#include "/WEB-INF/template/card/header.ftl">
	<div class="column">
	<div class="column_left">
        <ul>
			<@cards_list type="hot" count=11; cardsList>
				<#list cardsList as cards>
					<li>
						<a href="${base}/card/cards!detail.action?id=${cards.id}" title="${cards.name}">${substring(cards.name, 24, "...")}</a>
					</li>
				</#list>
				<li>
					<a href="${base}/card/cards!list.action" class="red">跟多卡种  &gt;&gt;</a>
				</li>
			</@cards_list>
		</ul>
    </div>
    <div class="column_right">
   	    <div class="column_right_1"><p>您尚未登陆，请点击<a href="${base}/card/promotion.action">登陆</a>立即参与到分享二维码，推荐会员拿提成活动</div>
        <div class="column_right_2">
            <#list pager.result as deposit>
            <div class="column_right_2_shang">
            	<span> <strong>${deposit.member.username}(${deposit.member.email})</strong>
            	<p>${deposit.createDate}在名臣福利平台<#if (deposit.payment?? && deposit.payment.order??)!>
						<a href="order!view.action?id=${deposit.payment.order.id}">
								${action.getText("DepositType." + deposit.depositType)}
						</a>
						<#else>
							${action.getText("DepositType." + deposit.depositType)}
						</#if>
						<#if deposit.credit != 0>
							${deposit.credit?string(currencyFormat)}
						<#else>
							${deposit.debit?string(currencyFormat)}
						</#if>
				</p>
					
            </div>     
			</#list>
          </div>
        </div>
    </div>
</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/card/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/card/footer.ftl">
</body>
</html>