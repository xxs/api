<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会员等级列表 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
</head>
<body class="list">
	<div class="bar">
		会员等级管理&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="member_rank!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='member_rank!add.action'" value="添加等级" hidefocus />
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>等級名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="lossrate" hidefocus>手续费率</a>
					</th>
					<th>
						<a href="#" class="sort" name="lossrate" hidefocus>提成率</a>
					</th>
					<th>
						<a href="#" class="sort" name="score" hidefocus>所需积分</a>
					</th>
					<th>
						<a href="#" class="sort" name="isDefault" hidefocus>是否默认</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as memberRank>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${memberRank.id}" />
						</td>
						<td>
							${memberRank.name}
						</td>
						<td>
							${memberRank.lossrate}%
						</td>
						<td>
							${memberRank.benefits}%
						</td>
						<td>
							${memberRank.score}
						</td>
						<td>
							<span class="${memberRank.isDefault?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							<a href="member_rank!edit.action?id=${memberRank.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="member_rank!delete.action" value="删 除" disabled hidefocus />
					</div>
					<div class="pager">
						<#include "/WEB-INF/template/admin/pager.ftl" />
					</div>
				<div>
			<#else>
				<div class="noRecord">没有找到任何记录!</div>
			</#if>
		</form>
	</div>
</body>
</html>