<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看在线留言 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {
	
	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	var $deleteReply = $(".deleteReply");
	
	$deleteReply.click( function() {
		var $this = $(this);
		var replyId = $this.attr("replyId");
		$.dialog({type: "warn", content: "您确定要删除吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: deleteReply});
		function deleteReply() {
			$.ajax({
				url: "leave_message!ajaxDeleteReply.action",
				data: {"id": replyId},
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					if (data.status == "success") {
						$("." + replyId).remove();
					}
					$.message({type: data.status, content: data.message});
				}
			});
		}
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"leaveMessage.title": {
				required: true
			},
			"leaveMessage.content": {
				required: true
			}
		},
		messages: {
			"leaveMessage.title": {
				required: "请填写回复标题"
			}, 
			"leaveMessage.content": {
				required: "请填写回复内容"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
})
</script>
</head>
<body class="input">
	<div class="bar">
		查看在线留言
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="leave_message!reply.action" method="post">
			<input type="hidden" name="id" value="${leaveMessage.id}" />
			<div class="tableItem">
				<table class="inputTable">
					<tr>
						<th colspan="2" class="title">
							留言
						</th>
					</tr>
					<tr>
						<th>
							发送人: 
						</th>
						<td>
							${(leaveMessage.username)!"游客"}
						</td>
					</tr>
					<tr>
						<th>
							标题: 
						</th>
						<td>
							${leaveMessage.title}
						</td>
					</tr>
					<tr>
						<th>
							内容: 
						</th>
						<td>
							${leaveMessage.content}
						</td>
					</tr>
					<tr>
						<th>
							联系方式: 
						</th>
						<td>
							${leaveMessage.contact}
						</td>
					</tr>
					<tr>
						<th>
							发送日期: 
						</th>
						<td>
							${leaveMessage.createDate?string("yyyy-MM-dd HH:mm:ss")}
						</td>
					</tr>
					<tr>
						<th>
							IP: 
						</th>
						<td>
							${leaveMessage.ip}
						</td>
					</tr>
				</table>
				<#if (leaveMessage.replyLeaveMessageSet?? && leaveMessage.replyLeaveMessageSet?size > 0)>
					<div class="blank"></div>
					<table class="inputTable">
						<tr>
							<th colspan="2" class="title">
								回复
							</th>
						</tr>
						<#list leaveMessage.replyLeaveMessageSet as replyLeaveMessage>
							<tr class="${replyLeaveMessage.id}">
								<th>
									回复标题: 
								</th>
								<td>
									${replyLeaveMessage.title}
									<a href="javascript: void(0);" class="red deleteReply" replyId="${replyLeaveMessage.id}">[删除]</a>
								</td>
							</tr>
							<tr class="${replyLeaveMessage.id}">
								<th>
									回复时间: 
								</th>
								<td>
									${replyLeaveMessage.createDate?string("yyyy-MM-dd HH: mm")}
								</td>
							</tr>
							<tr class="${replyLeaveMessage.id}">
								<th>
									回复内容: 
								</th>
								<td>
									${replyLeaveMessage.content}
								</td>
							</tr>
							<#if replyLeaveMessage_has_next>
								<tr class="${replyLeaveMessage.id}">
									<td colspan="2">
										&nbsp;
									</td>
								</tr>
							</#if>
						</#list>
					</table>
				</#if>
				<div class="blank"></div>
				<table class="inputTable">
					<tr>
						<th colspan="2" class="title">
							管理员回复
						</th>
					</tr>
					<tr>
						<th>
							回复标题: 
						</th>
						<td>
							<input type="text" name="leaveMessage.title" class="formText" />
						</td>
					</tr>
					<tr>
						<th>
							回复内容: 
						</th>
						<td>
							<textarea name="leaveMessage.content" class="formTextarea"></textarea>
						</td>
					</tr>
				</table>
				<div class="buttonArea">
					<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
					<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
				</div>
			</div>
		</form>
	</div>
</body>
</html>