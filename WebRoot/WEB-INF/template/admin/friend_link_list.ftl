<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>友情链接列表 - XXS</title>
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
		友情链接管理&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="friend_link!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='friend_link!add.action'" value="添加链接" hidefocus />
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
						<a href="#" class="sort" name="name" hidefocus>友情链接名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="url" hidefocus>链接地址</a>
					</th>
					<th>
						<a href="#" class="sort" name="logoPath" hidefocus>LOGO</a>
					</th>
					<th>
						<a href="#" class="sort" name="orderList" hidefocus>排序</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>添加时间</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as friendLink>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${friendLink.id}" />
						</td>
						<td>
							${friendLink.name}
						</td>
						<td>
							<#if (friendLink.url != "")!>
								<a href="${friendLink.url}" target="_blank">${friendLink.url}</a>
							</#if>
						</td>
						<td>
							<#if (friendLink.logoPath??)!>
								<a href="${base}${friendLink.logoPath}" target="_blank">查看</a>
							<#else>
								-
							</#if>
						</td>
						<td>
							${friendLink.orderList}
						</td>
						<td>
							<span title="${friendLink.createDate?string("yyyy-MM-dd HH:mm:ss")}">${friendLink.createDate}</span>
						</td>
						<td>
							<a href="friend_link!edit.action?id=${friendLink.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="friend_link!delete.action" value="删 除" disabled hidefocus />
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