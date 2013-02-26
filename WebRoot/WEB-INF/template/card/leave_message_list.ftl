<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>在线留言<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<#include "/WEB-INF/template/card/head.ftl">
<style type="text/css">
<!--
.leaveMessage {
	width: 960px;
	line-height: 18px;
	margin:20px auto 0 auto;
}
.sendTable {
	width: 960px;
	line-height: 30px;
	border: 1px solid #c7dbe5;
}

.sendTable .title td {
	height: 30px;
	padding-left: 10px;
	text-align: left;
	font-weight: bold;
	background-color: #ecf2f8;
}

.sendTable th {
	text-align: right;
	font-weight: normal;
}

.sendTable td {
	padding: 5px;
}

.sendTable .captcha {
	width: 95px;
	margin-right: 5px;
	text-transform: uppercase;
}

.sendTable .captchaImage {
	vertical-align: middle;
	cursor: pointer;
}
-->
</style>
</head>
<body class="singlePage">
	<#include "/WEB-INF/template/card/header.ftl">
	<div class="body leaveMessage">
		<div class="singlePageDetail">
				<div class="blank"></div>
				<form id="leaveMessageForm" method="post" autocomplete="off">
					<table class="sendTable">
						<tr class="title">
							<td width="200">
								发布留言
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<th>
								标题: 
							</th>
							<td>
								<input type="text" id="leaveMessageTitle" name="leaveMessage.title" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								留言内容: 
							</th>
							<td>
								<textarea id="leaveMessageContent" name="leaveMessage.content" class="formTextarea"></textarea>
							</td>
						</tr>
						<tr>
							<th>
								联系方式: 
							</th>
							<td>
								<input type="text" name="leaveMessage.contact" class="formText" />
							</td>
						</tr>
						<#if setting.isLeaveMessageCaptchaEnabled>
							<tr>
			                	<th>
			                		验证码: 
			                	</th>
			                    <td>
			                    	<input type="text" id="leaveMessageCaptcha" name="j_captcha" class="formText captcha" />
			                   		<img id="leaveMessageCaptchaImage" class="captchaImage" src="${base}/captcha.jpeg" alt="换一张" />
			                    </td>
			                </tr>
		                </#if>
						<tr>
							<th>
								&nbsp;
							</th>
							<td>
								<input type="submit" class="formButton" value="提 交" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/card/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/card/footer.ftl">
	<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
	<script type="text/javascript" src="${base}/template/card/js/base.js"></script>
	<script type="text/javascript" src="${base}/template/card/js/card.js"></script>
	<script type="text/javascript">
		$().ready(function() {
		$.dialog({type: 'success', content: '成功成功', modal: true, autoCloseTime: 3000});
			var $leaveMessage = $("#leaveMessage");
			var $leaveMessageForm = $("#leaveMessageForm");
			var $leaveMessageTitle = $("#leaveMessageTitle");
			var $leaveMessageContent = $("#leaveMessageContent");
			var $leaveMessageCaptcha = $("#leaveMessageCaptcha");
			var $leaveMessageCaptchaImage = $("#leaveMessageCaptchaImage");
			
			// 刷新在线留言验证码图片
			function leaveMessageCaptchaImageRefresh() {
				$leaveMessageCaptchaImage.attr("src", xxs.base + "/captcha.jpeg?timestamp=" + (new Date()).valueOf());
			}
			
			// 刷新在线留言验证码图片
			$leaveMessageCaptchaImage.click( function() {
				leaveMessageCaptchaImageRefresh();
			});
			
			$leaveMessageForm.submit( function() {
				if ($.trim($leaveMessageTitle.val()) == "") {
					$.dialog({type: "warn", content: "请输入标题!", modal: true, autoCloseTime: 3000});
					return false;
				}
				if ($.trim($leaveMessageContent.val()) == "") {
					$.dialog({type: "warn", content: "请输入留言内容!", modal: true, autoCloseTime: 3000});
					return false;
				}
				<#if setting.isLeaveMessageCaptchaEnabled>
					if ($.trim($leaveMessageCaptcha.val()) == "") {
						$.dialog({type: "warn", content: "请输入验证码!", modal: true, autoCloseTime: 3000});
						return false;
					}
				</#if>
				
				$.ajax({
					url: "${base}/card/leave_message!ajaxSave.action",
					data: $leaveMessageForm.serialize(),
					type: "POST",
					dataType: "json",
					cache: false,
					beforeSend: function() {
						$leaveMessageForm.find("submit").attr("disabled", true);
					},
					success: function(data) {
						$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
						if (data.status == "success") {
							<#if setting.leaveMessageDisplayType == "direct">
								var username = getCookie("memberUsername");
								if (username == null) {
									username = "游客";
								}
								var leaveMessageItemHtml = '<div class="leaveMessageItem"><p><span class="red">' + username + '</span> ' + new Date().toLocaleDateString() + '</p><p><pre>' + htmlEscape($leaveMessageContent.val()) + '</pre></p></div><div class="blank"></div>';
								$leaveMessage.prepend(leaveMessageItemHtml);
							</#if>
							$leaveMessageContent.val("");
						}
					},
					complete: function() {
						$leaveMessageForm.find("submit").attr("disabled", false);
						$leaveMessageCaptcha.val("");
						leaveMessageCaptchaImageRefresh();
					}
				});
				return false;
			});
		})
	</script>
</body>
</html>