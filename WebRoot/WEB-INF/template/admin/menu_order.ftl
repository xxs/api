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
		<@sec.authorize ifAnyGranted="ROLE_ORDER,ROLE_PAYMENT,ROLE_REFUND">
			<dl>
				<dt>
					<span>订单管理</span>
				</dt>
				<@sec.authorize ifAnyGranted="ROLE_ORDER">
					<dd>
						<a href="order!list.action" target="mainFrame">订单列表</a>
					</dd>
				</@sec.authorize>
			</dl>
		</@sec.authorize>
		<@sec.authorize ifAnyGranted="ROLE_WITHDRAW">
			<dl>
				<dt>
					<span>提现管理</span>
				</dt>
				<dd>
					<a href="withdraw!list.action" target="mainFrame">提现单列表</a>
				</dd>
				<dd>
					<a href="withdraw!applying.action" target="mainFrame">新提现申请</a>
				</dd>
				<dd>
					<a href="withdraw!setting.action" target="mainFrame">提现设置</a>
				</dd>
			</dl>
		</@sec.authorize>
	</div>
</body>
</html>