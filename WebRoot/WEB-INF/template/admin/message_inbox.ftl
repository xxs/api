<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>收件箱 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $showMessageIcon = $("#listTable .showMessage").prev("span");
	var $showMessage = $("#listTable .showMessage");
	var $messageContentTr = $("#listTable .messageContentTr");
	var $deleteButton = $("#listTable .deleteButton");
	
	// 显示消息内容
	$showMessage.click( function() {
		var $this = $(this);
		var $thisMessageContentTr = $this.parent().parent().next(".messageContentTr");
		if ($.trim($thisMessageContentTr.find(".messageContent").text()) == "") {
			var messageId = $this.attr("messageId");
			$.ajax({
				url: "message!ajaxShowMessage.action",
				data: {"id": messageId},
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				beforeSend: function(data) {
					$thisMessageContentTr.find(".messageContent").html('<span class="loadingIcon">&nbsp;</span> 加载中...');
				},
				success: function(data) {
					if (data.status == "success") {
						$thisMessageContentTr.find(".messageContent").html(htmlEscape(data.content));
					} else {
						$.message({type: data.status, content: data.message});
					}
				}
			});
		}
		
		var $thisShowMessageContentIcon = $this.prev("span");
		if ($thisShowMessageContentIcon.hasClass("downIcon")) {
			$thisMessageContentTr.show();
			$thisShowMessageContentIcon.removeClass("downIcon").addClass("upIcon");
		} else {
			$thisMessageContentTr.hide();
			$thisShowMessageContentIcon.removeClass("upIcon").addClass("downIcon");
		}
	});
	
	// 删除消息
	$deleteButton.click( function() {
		$messageContentTr.hide();
		$showMessageIcon.removeClass("upIcon").addClass("downIcon");
	});
	
})
</script>
</head>
<body class="list message">
	<div class="bar">
		收件箱&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="message!inbox.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='message!send.action'" value="发送消息" hidefocus />
				<label>查找: </label>
				<select name="pager.searchBy">
					<option value="fromMember.username"<#if pager.searchBy == "fromMember.username"> selected</#if>>
						发件人
					</option>
					<option value="title"<#if pager.searchBy == "title"> selected</#if>>
						标题
					</option>
				</select>
				<input type="text" name="pager.keyword" value="${pager.keyword!}" />
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
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
						<a href="#" class="sort" name="title" hidefocus>标题</a>
					</th>
					<th>
						<a href="#" class="sort" name="fromMember" hidefocus>发件人</a>
					</th>
					<th>
						<a href="#" class="sort" name="isRead" hidefocus>已读</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>日期</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as message>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${message.id}" />
						</td>
						<td>
							<span class="downIcon">&nbsp;</span>
							<a class="showMessage" href="#" messageId="${message.id}">${message.title}</a>
						</td>
						<td>
							${message.fromMember.username}
						</td>
						<td>
							<#if message.isRead>
								是
							<#else>
								<span class="red">否</span>
							</#if>
						</td>
						<td>
							${message.createDate?string("yyyy-MM-dd HH: mm")}
						</td>
						<td>
							<a href="message!reply.action?id=${message.id}">[回复]</a>
						</td>
					</tr>
					<tr class="messageContentTr">
						<td>&nbsp;</td>
						<td colspan="5" class="messageContent"></td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="message!delete.action" value="删 除" disabled hidefocus />
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