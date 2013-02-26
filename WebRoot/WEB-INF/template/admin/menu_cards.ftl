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
		<@sec.authorize ifAnyGranted="ROLE_CARDS,ROLE_CARDS_NOTIFY">
			<dl>
				<dt>
					<span>充值卡管理&nbsp;</span>
				</dt>
				<@sec.authorize ifAnyGranted="ROLE_CARDS">
					<dd>
						<a href="cards!list.action" target="mainFrame">充值卡列表</a>
					</dd>
					<dd>
						<a href="cards!add.action" target="mainFrame">添加充值卡</a>
					</dd>
				</@sec.authorize>
			</dl>
		</@sec.authorize>
		<@sec.authorize ifAnyGranted="ROLE_SPECIFICATION">
			<dl>
				<dt>
					<span>充值卡规格管理&nbsp;</span>
				</dt>
				<dd>
					<a href="specification!list.action" target="mainFrame">充值卡规格</a>
				</dd>
				<dd>
					<a href="specification!add.action" target="mainFrame">添加规格</a>
				</dd>
			</dl>
		</@sec.authorize>
		<@sec.authorize ifAnyGranted="ROLE_BRAND">
			<dl>
				<dt>
					<span>品牌管理&nbsp;</span>
				</dt>
				<dd>
					<a href="brand!list.action" target="mainFrame">品牌列表</a>
				</dd>
				<dd>
					<a href="brand!add.action" target="mainFrame">添加品牌</a>
				</dd>
			</dl>
		</@sec.authorize>
	</div>
</body>
</html>