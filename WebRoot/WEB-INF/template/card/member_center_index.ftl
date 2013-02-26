<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会员中心<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<#include "/WEB-INF/template/card/member_head.ftl">
<script type="text/javascript">
	$().ready( function() {
		$(".gonggao").bind("click",function(event){
			$content = event.target.name;
			$title = event.target.title;
			$.showAdviceWindow($title,$content);
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
				<table class="stateTable">
						<tr>
							<td colspan="4">
								您目前是[${loginMember.memberRank.name}],目前等级享受的提现手续费率为：${(1-loginMember.memberRank.lossrate)*100}%&nbsp;!
								下一等级将享受的提现手续费率为：${(1-loginMember.memberRank.lossrate)*100}%
							</td>
						</tr>
						<tr>
							<th>帐户总积分</th>
							<td>${loginMember.score}</td>
							<th>订单总数量</th>
							<td>
								${loginMember.orderSet?size}&nbsp;&nbsp;
								<a href="order!list.action">[查看订单列表]</a>
							</td>
						</tr>
						<tr>
							<th>预存款余额</th>
							<td>${loginMember.deposit?string(currencyFormat)}&nbsp;&nbsp;<a href="deposit!list.action">[预存款明细]</a></td>
							<th>未读消息数</th>
							<td>
								${unreadMessageCount}&nbsp;&nbsp;
								<!--<a href="message!inbox.action">[查看收件箱]</a>-->
							</td>
						</tr>
						<tr>
							<th>注册日期</th>
							<td>${loginMember.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
							<th>最后登录IP</th>
							<td>${loginMember.loginIp}</td>
						</tr>
					</table>
					<div class="blank"></div>
					<table class="listTable">
						<tr>
							<th>公告信息</th>
							<th>发布日期</th>
						</tr>
						<@article_list type="advice" count=10; articleList>
							<#list articleList as article>
							<tr>
								<td align="left">
								<a href="#" name="${article.content}" class="red gonggao" title="${article.title}">${substring(article.title, 150, "...")}</a>
							</td>
								<td>
								<span title="${article.createDate?string("yyyy-MM-dd HH:mm:ss")}">${article.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
								</td>
							</tr>	
							</#list>
						</@article_list>
					</table>
					<#include "/WEB-INF/template/card/pager.ftl">
		</div>
	</div>
</div>
<div class="blank"></div>
<#include "/WEB-INF/template/card/member_footer.ftl">
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
</body>
</html>