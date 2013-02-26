<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会员注册项管理 - XXS</title>
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
		会员注册项管理&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="member_attribute!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='member_attribute!add.action'" value="添加注册项" hidefocus />
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="attributeType" hidefocus>类型</a>
					</th>
					<th>
						<a href="#" class="sort" name="isRequired" hidefocus>是否必填</a>
					</th>
					<th>
						<a href="#" class="sort" name="isEnabled" hidefocus>是否启用</a>
					</th>
					<th>
						<a href="#" class="sort" name="orderList" hidefocus>排序</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as memberAttribute>
					<tr>
						<td>
							<input type="checkbox"<#if memberAttribute.systemAttributeType??> disabled title="系统默认注册项不允许删除!"<#else> name="ids" value="${memberAttribute.id}"</#if> />
						</td>
						<td>
							${memberAttribute.name}
						</td>
						<td>
							<#if memberAttribute.systemAttributeType??>
								${action.getText("SystemAttributeType." + memberAttribute.systemAttributeType)}
								[系统默认]
							<#else>
								${action.getText("AttributeType." + memberAttribute.attributeType)}
							</#if>
						</td>
						<td>
							<span class="${memberAttribute.isRequired?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							<span class="${memberAttribute.isEnabled?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							${memberAttribute.orderList}
						</td>
						<td>
							<a href="member_attribute!edit.action?id=${memberAttribute.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="member_attribute!delete.action" value="删 除" disabled hidefocus />
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