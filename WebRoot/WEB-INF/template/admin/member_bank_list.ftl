<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>银行卡列表 - XXS</title>
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
		会员银行卡管理&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="member_bank!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='member_bank!add.action'" value="添加银行卡" hidefocus />
				&nbsp;&nbsp;
				<label>查找: </label>
				<select name="pager.searchBy">
					<option value="banknum"<#if pager.searchBy == "banknum"> selected</#if>>
						银行账号
					</option>
					<option value="openname"<#if pager.searchBy == "openname"> selected</#if>>
						开户姓名
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
						<a href="#" class="sort" name="banknum" hidefocus>银行账号</a>
					</th>
					<th>
						<a href="#" class="sort" name="openname" hidefocus>开户姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="bankname" hidefocus>所属银行</a>
					</th>
					<th>
						<a href="#" class="sort" name="bankcity" hidefocus>银行归属地</a>
					</th>
					<th>
						<a href="#" class="sort" name="bankdetail" hidefocus>所属支行</a>
					</th>
					<th>
						<a href="#" class="sort" name="member" hidefocus>所属会员名</a>
					</th>
					<th>
						<a href="#" class="sort" name="isDefault" hidefocus>是否默认</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as memberBank>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${memberBank.id}" />
						</td>
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
							${memberBank.member.username}
						</td>
						<td>
							<span class="${memberBank.isDefault?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							<a href="member_bank!edit.action?id=${memberBank.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="member_bank!delete.action" value="删 除" disabled hidefocus />
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