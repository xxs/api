<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>在线留言设置 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
</head>
<body class="input">
	<div class="bar">
		在线留言设置
	</div>
	<div class="body">
		<form id="validateForm" action="leave_message!settingUpdate.action" method="post">
			<table class="inputTable">
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<label>
							<@checkbox name="isLeaveMessageEnabled" value="${setting.isLeaveMessageEnabled}" />启用在线留言功能
						</label>
						<label>
							<@checkbox name="isLeaveMessageCaptchaEnabled" value="${setting.isLeaveMessageCaptchaEnabled}" />启用验证码
						</label>
					</td>
				</tr>
				<tr>
					<th>
						显示方式: 
					</th>
					<td>
						<select name="leaveMessageDisplayType">
							<#list leaveMessageDisplayTypeList as list>
								<option value="${list}"<#if setting.leaveMessageDisplayType == list> selected</#if>>
									${action.getText("LeaveMessageDisplayType." + list)}
								</label>
							</#list>
						</select>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>