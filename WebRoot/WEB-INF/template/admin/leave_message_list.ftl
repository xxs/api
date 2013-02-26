<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>在线留言列表 - XXS</title>
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
		在线留言列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="leave_message!list.action" method="post">
			<div class="listBar">
				<select name="pager.searchBy">
					<option value="username"<#if pager.searchBy == "username"> selected</#if>>
						发送人
					</option>
					<option value="title"<#if pager.searchBy == "title"> selected</#if>>
						标题
					</option>
					<option value="content"<#if pager.searchBy == "content"> selected</#if>>
						内容
					</option>
					<option value="contact"<#if pager.searchBy == "contact"> selected</#if>>
						联系方式
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
						<a href="#" class="sort" name="username" hidefocus>发送人</a>
					</th>
					<th>
						<a href="#" class="sort" name="title" hidefocus>标题</a>
					</th>
					<th>
						<a href="#" class="sort" name="content" hidefocus>内容</a>
					</th>
					<th>
						<span>已回复</span>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>发送日期</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as leaveMessage>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${leaveMessage.id}" />
						</td>
						<td>
							${(leaveMessage.username)!"游客"}
						</td>
						<td>
							<span title="${leaveMessage.title}">
								${substring(leaveMessage.title, 30, "...")}
							</span>
						</td>
						<td>
							<span title="${leaveMessage.content}">
								${substring(leaveMessage.content, 60, "...")}
							</span>
						</td>
						<td>
							<#if (leaveMessage.replyLeaveMessageSet?? && leaveMessage.replyLeaveMessageSet?size > 0)>
								<span class="trueIcon">&nbsp;</span>
							<#else>
								<span class="falseIcon">&nbsp;</span>
							</#if>
						</td>
						<td>
							<span title="${leaveMessage.createDate?string("yyyy-MM-dd HH:mm:ss")}">${leaveMessage.createDate}</span>
						</td>
						<td>
							<a href="leave_message!view.action?id=${leaveMessage.id}" title="查看/回复">[查看/回复]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="leave_message!delete.action" value="删 除" disabled hidefocus />
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