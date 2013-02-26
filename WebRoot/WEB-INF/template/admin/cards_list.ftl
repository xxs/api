<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>充值卡列表 - XXS</title>
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
		充值卡列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="cards!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='cards!add.action'" value="添加充值卡" hidefocus />
				&nbsp;&nbsp;
				<label>查找: </label>
				<select name="pager.searchBy">
					<option value="name"<#if pager.searchBy == "name"> selected</#if>>
						充值卡名称
					</option>
					<option value="cardsSn"<#if pager.searchBy == "cardsSn"> selected</#if>>
						充值卡编号
					</option>
				</select>
				<input type="text" name="pager.keyword" value="${pager.keyword!}" />
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				&nbsp;&nbsp;
				<label>每页显示: </label>
				<select name="pager.pageSize" id="pageSize">
					<option value="10"<#if pager.pageSize == 10> selected</#if>>
						10
					</option>
					<option value="20"<#if pager.pageSize == 20> selected</#if>>
						20
					</option>
					<option value="50"<#if pager.pageSize == 50> selected</#if>>
						50
					</option>
					<option value="100"<#if pager.pageSize == 100> selected</#if>>
						100
					</option>
				</select>
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>充值卡名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="cardsSn" hidefocus>充值卡编号</a>
					</th>
					<th>
						<a href="#" class="sort" name="price" hidefocus>销售价</span>
					</th>
					<th>
						<a href="#" class="sort" name="isMarketable" hidefocus>上架</a>
					</th>
					<th>
						<a href="#" class="sort" name="isBest" hidefocus>精品</a>
					</th>
					<th>
						<a href="#" class="sort" name="isNew" hidefocus>新品</a>
					</th>
					<th>
						<a href="#" class="sort" name="isHot" hidefocus>热销</a>
					</th>
					<th>
						<a href="#" class="sort" name="orderList" hidefocus>排序</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as cards>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${cards.id}" />
						</td>
						<td>
							<span title="${cards.name}">
								${substring(cards.name, 30, "...")}
							</span>
						</td>
						<td>
							${cards.cardsSn}
						</td>
						<td>
							${cards.price?string(currencyFormat)}
						</td>
						<td>
							<span class="${cards.isMarketable?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							<span class="${cards.isBest?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							<span class="${cards.isNew?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							<span class="${cards.isHot?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							<span >${cards.orderList}</span>
						</td>
						<td>
							<a href="cards!edit.action?id=${cards.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="cards!delete.action" value="删 除" disabled hidefocus />
					</div>
					<div class="pager">
						<#include "/WEB-INF/template/admin/pager.ftl" />
					</div>
				</div>
			<#else>
				<div class="noRecord">没有找到任何记录!</div>
			</#if>
		</form>
	</div>
</body>
</html>