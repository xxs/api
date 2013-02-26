<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>充值卡规格管理 - XXS</title>
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
		充值卡规格管理&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="specification!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='specification!add.action'" value="添加规格" hidefocus />
				&nbsp;&nbsp;
				<label>查找: </label>
				<select name="pager.searchBy">
					<option value="name"<#if pager.searchBy == "name"> selected</#if>>
						名称
					</option>
					<option value="memo"<#if pager.searchBy == "memo"> selected</#if>>
						备注
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
						<a href="#" class="sort" name="name" hidefocus>规格名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="specificationType" hidefocus>规格类型</a>
					</th>
					<th>
						<span>规格值</span>
					</th>
					<th>
						<span>排序</span>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as specification>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${specification.id}" />
						</td>
						<td>
							${specification.name}
							<#if specification.memo??>
								<span class="gray">[${specification.memo}]</span>
							</#if>
						</td>
						<td>
							${action.getText("SpecificationType." + specification.specificationType)}
						</td>
						<td>
							<#list specification.specificationValueList as specificationValue>
								${specificationValue.name}
								<#if (specificationValue_index > 10)>
									<#break />
								</#if>
							</#list>
						</td>
						<td>
							${specification.orderList}
						</td>
						<td>
							<a href="specification!edit.action?id=${specification.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="specification!delete.action" value="删 除" disabled hidefocus />
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