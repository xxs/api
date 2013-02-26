<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>商户列表 - XXS</title>
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
		会员商户管理&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="member_business!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='member_business!add.action'" value="添加商户" hidefocus />
				&nbsp;&nbsp;
				<label>查找: </label>
				<select name="pager.searchBy">
					<option value="businessName"<#if pager.searchBy == "businessName"> selected</#if>>
						商户名称
					</option>
					<option value="realName"<#if pager.searchBy == "realName"> selected</#if>>
						真实姓名
					</option>
					<option value="icp"<#if pager.searchBy == "icp"> selected</#if>>
						ICP证备案号
					</option>
					<option value="linkMan"<#if pager.searchBy == "linkMan"> selected</#if>>
						联系人
					</option>
					<option value="url"<#if pager.searchBy == "url"> selected</#if>>
						商城网址
					</option>
					<option value="member"<#if pager.searchBy == "member"> selected</#if>>
						关联会员名
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
						<a href="#" class="sort" name="businessName" hidefocus>商户名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="realName" hidefocus>真实姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="icp" hidefocus>ICP证备案号</a>
					</th>
					<th>
						<a href="#" class="sort" name="linkMan" hidefocus>联系人</a>
					</th>
					<th>
						<a href="#" class="sort" name="url" hidefocus>商城网址</a>
					</th>
					<th>
						<a href="#" class="sort" name="businessType" hidefocus>商户类型</a>
					</th>
					<th>
						<a href="#" class="sort" name="member" hidefocus>关联会员名</a>
					</th>
					<th>
						<a href="#" class="sort" name="resultType" hidefocus>状态</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as memberBusiness>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${memberBusiness.id}" />
						</td>
						<td>
							${memberBusiness.businessName}
						</td>
						<td>
							${memberBusiness.realName}
						</td>
						<td>
							${memberBusiness.icp}
						</td>
						<td>
							${memberBusiness.linkMan}
						</td>
						<td>
							<a href="${memberBusiness.url}" target="_block">${memberBusiness.url}</a>
						</td>
						<td>
							${action.getText("BusinessType." + memberBusiness.businessType)}
						</td>
						<td>
							${memberBusiness.member.username}
						</td>
						<td>
							${action.getText("ResultType." + memberBusiness.resultType)}
						</td>
						<td>
							<a href="member_business!edit.action?id=${memberBusiness.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="member_business!delete.action" value="删 除" disabled hidefocus />
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