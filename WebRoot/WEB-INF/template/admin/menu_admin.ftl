<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理菜单 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body class="menu">
	<div class="body">
		<@sec.authorize ifAnyGranted="ROLE_ADMIN,ROLE_ROLE">
			<dl>
				<dt>
					<span>管理员&nbsp;</span>
				</dt>
				<@sec.authorize ifAnyGranted="ROLE_ADMIN">
					<dd>
						<a href="admin!list.action" target="mainFrame">管理员列表</a>
					</dd>
				</@sec.authorize>
				<@sec.authorize ifAnyGranted="ROLE_ROLE">
					<dd>
						<a href="role!list.action" target="mainFrame">角色管理</a>
					</dd>
				</@sec.authorize>
			</dl>
		</@sec.authorize>
		<@sec.authorize ifAnyGranted="ROLE_MESSAGE">
			<dl>
				<dt>
					<span>站内消息&nbsp;</span>
				</dt>
				<dd>
					<a href="message!inbox.action" target="mainFrame">收件箱</a>
				</dd>
				<dd>
					<a href="message!outbox.action" target="mainFrame">发件箱</a>
				</dd>
			</dl>
		</@sec.authorize>
		<@sec.authorize ifAnyGranted="ROLE_LOG">
			<dl>
				<dt>
					<span>操作日志&nbsp;</span>
				</dt>
				<dd>
					<a href="log!list.action" target="mainFrame">查看日志</a>
				</dd>
			</dl>
		</@sec.authorize>
	</div>
</body>
</html>