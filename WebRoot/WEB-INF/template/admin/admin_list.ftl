<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理员列表 - XXS</title>
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
		管理员列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="admin!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='admin!add.action'" value="添加管理员" hidefocus />
				&nbsp;&nbsp;
				<select name="pager.searchBy">
					<option value="username"<#if pager.searchBy == "username"> selected</#if>>
						用户名
					</option>
					<option value="name"<#if pager.searchBy == "name"> selected</#if>>
						姓名
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
						<a href="#" class="sort" name="username" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="sort" name="email" hidefocus>E-mail</a>
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="department" hidefocus>所属部门</a>
					</th>
					<th>
						<a href="#" class="sort" name="loginDate" hidefocus>最后登录时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="loginIp" hidefocus>最后登录IP</a>
					</th>
					<th>
						<span>状态</span>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>创建日期</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as admin>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${admin.id}" />
						</td>
						<td>
							${admin.username}
						</td>
						<td>
							${admin.email}
						</td>
						<td>
							${(admin.name)!}
						</td>
						<td>
							${(admin.department)!}
						</td>
						<td>
							<#if admin.loginDate??>
								<span title="${admin.loginDate?string("yyyy-MM-dd HH:mm:ss")}">${admin.loginDate}</span>
							<#else>
								&nbsp;
							</#if>
						</td>
						<td>
							${(admin.loginIp)!}
						</td>
						<td>
							<#if admin.isAccountEnabled && !admin.isAccountLocked && !admin.isAccountExpired && !admin.isCredentialsExpired>
								<span class="green">正常</span>
							<#elseif !admin.isAccountEnabled>
								<span class="red"> 未启用 </span>
							<#elseif admin.isAccountLocked>
								<span class="red"> 已锁定 </span>
							<#elseif admin.isAccountExpired>
								<span class="red"> 已过期 </span>
							<#elseif admin.isCredentialsExpired>
								<span class="red"> 凭证过期 </span>
							</#if>
						</td>
						<td>
							<span title="${admin.createDate?string("yyyy-MM-dd HH:mm:ss")}">${admin.createDate}</span>
						</td>
						<td>
							<a href="admin!edit.action?id=${admin.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="admin!delete.action" value="删 除" disabled hidefocus />
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