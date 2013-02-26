<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>提现列表 - XXS</title>
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
		提现列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="withdraw!list.action" method="post">
			<div class="listBar">
				<label>查找: </label>
				<select name="pager.searchBy">
					<option value="withdrawSn"<#if pager.searchBy == "withdrawSn"> selected</#if>>
						单据号
					</option>
					<option value="member.username"<#if pager.searchBy == "member.username"> selected</#if>>
						申请人
					</option>
					<option value="memo"<#if pager.searchBy == "memo"> selected</#if>>
						备注信息
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
						<a href="#" class="sort" name="withdrawSn" hidefocus>单据号</a>    
					</th>
					<th>
						<a href="#" class="sort" name="member" hidefocus>申请人</a>    
					</th>
					<th>
						<a href="#" class="sort" name="memberBank" hidefocus>提现账户</a>    
					</th>
					<th>
						<a href="#" class="sort" name="message" hidefocus>操作类型</a>    
					</th>
					<th>
						<a href="#" class="sort" name="money" hidefocus>提现金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="totalMoney" hidefocus>实发金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>日期</a>
					</th>
					<th>
						<a href="#" class="sort" name="withdrawStatus" hidefocus>状态</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as withdraw>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${withdraw.id}" />
						</td>
						<td>
							${withdraw.withdrawSn}
						</td>
						<td>
							${(withdraw.member.username)!"-"}
						</td>
						<td>
							${(withdraw.memberBank.banknum)!"-"}
						</td>
						<td>
							${withdraw.message}
						</td>
						<td>
							${withdraw.money?string(currencyFormat)}
						</td>
						<td>
							${withdraw.totalMoney?string(currencyFormat)}
						</td>
						<td>
							${withdraw.createDate}
						</td>
						<td>
							${action.getText("WithdrawStatus." + withdraw.withdrawStatus)}
						</td>
						<td>
							<a href="withdraw!process.action?id=${withdraw.id}" title="处理">[处理]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
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