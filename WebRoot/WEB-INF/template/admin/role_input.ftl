<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑角色 - XXS</title>
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
	
	var $allChecked = $("#validateForm .allChecked");
	
	$allChecked.click( function() {
		var $this = $(this);
		var $thisCheckbox = $this.parent().parent().find(":checkbox");
		if ($thisCheckbox.filter(":checked").size() > 0) {
			$thisCheckbox.attr("checked", false);
		} else {
			$thisCheckbox.attr("checked", true);
		}
		return false;
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"role.name": "required"
		},
		messages: {
			"role.name": "请填写角色名称"
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	$.validator.addMethod("roleAuthorityListRequired", $.validator.methods.required, "请选择管理权限");
	
	$.validator.addClassRules("roleAuthorityList", {
		roleAuthorityListRequired: true
	});
	
})
</script>
</head>
<body class="input role">
	<div class="bar">
		<#if isAddAction>添加角色<#else>编辑角色</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>role!save.action<#else>role!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						角色名称: 
					</th>
					<td>
						<input type="text" name="role.name" class="formText" value="${(role.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						描述: 
					</th>
					<td>
						<textarea name="role.description" class="formTextarea">${(role.description)!}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">充值卡管理: </a>
					</th>
					<td>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_CARDS"<#if (isAddAction || role.authorityList.contains("ROLE_CARDS"))!> checked</#if> />充值卡管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_SPECIFICATION"<#if (isAddAction || role.authorityList.contains("ROLE_SPECIFICATION"))!> checked</#if> />充值卡规格管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BRAND"<#if (isAddAction || role.authorityList.contains("ROLE_BRAND"))!> checked</#if> />充值卡品牌管理
						</label>
					</td>
				</tr>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">订单处理: </a>
					</th>
					<td>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ORDER"<#if (isAddAction || role.authorityList.contains("ROLE_ORDER"))!> checked</#if> />订单管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WITHDRAW"<#if (isAddAction || role.authorityList.contains("ROLE_WITHDRAW"))!> checked</#if> />提现管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_PAYMENT"<#if (isAddAction || role.authorityList.contains("ROLE_PAYMENT"))!> checked</#if> />收款管理
						</label>
					</td>
				</tr>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">会员管理: </a>
					</th>
					<td>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_MEMBER"<#if (isAddAction || role.authorityList.contains("ROLE_MEMBER"))!> checked</#if> />会员管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_MEMBER_RANK"<#if (isAddAction || role.authorityList.contains("ROLE_MEMBER_RANK"))!> checked</#if> />会员等级管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_MEMBER_ATTRIBUTE"<#if (isAddAction || role.authorityList.contains("ROLE_MEMBER_ATTRIBUTE"))!> checked</#if> />会员注册项管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_MEMBER_BANK"<#if (isAddAction || role.authorityList.contains("ROLE_MEMBER_BANK"))!> checked</#if> />银行卡管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_MEMBER_BUSINESS"<#if (isAddAction || role.authorityList.contains("ROLE_MEMBER_BUSINESS"))!> checked</#if> />商户管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_LEAVE_MESSAGE"<#if (isAddAction || role.authorityList.contains("ROLE_LEAVE_MESSAGE"))!> checked</#if> />在线留言管理
						</label>
					</td>
				</tr>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">页面内容管理: </a>
					</th>
					<td>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_NAVIGATION"<#if (isAddAction || role.authorityList.contains("ROLE_NAVIGATION"))!> checked</#if> />导航管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ARTICLEE"<#if (isAddAction || role.authorityList.contains("ROLE_ARTICLEE"))!> checked</#if> />文章管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ARTICLE_CATEGORY"<#if (isAddAction || role.authorityList.contains("ROLE_ARTICLE_CATEGORY"))!> checked</#if> />文章分类管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_FRIEND_LINK"<#if (isAddAction || role.authorityList.contains("ROLE_FRIEND_LINK"))!> checked</#if> />友情链接管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_PAGE_TEMPLATE"<#if (isAddAction || role.authorityList.contains("ROLE_PAGE_TEMPLATE"))!> checked</#if> />页面模板管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_MAIL_TEMPLATE"<#if (isAddAction || role.authorityList.contains("ROLE_MAIL_TEMPLATE"))!> checked</#if> />邮件模板管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_CACHE"<#if (isAddAction || role.authorityList.contains("ROLE_CACHE"))!> checked</#if> />缓存管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BUILD_HTML"<#if (isAddAction || role.authorityList.contains("ROLE_BUILD_HTML"))!> checked</#if> />生成静态管理
						</label>
					</td>
				</tr>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">管理员管理: </a>
					</th>
					<td>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ADMIN"<#if (isAddAction || role.authorityList.contains("ROLE_ADMIN"))!> checked</#if> />管理员管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ROLE"<#if (isAddAction || role.authorityList.contains("ROLE_ROLE"))!> checked</#if> />角色管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_MESSAGE"<#if (isAddAction || role.authorityList.contains("ROLE_MESSAGE"))!> checked</#if> />站内消息管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_LOG"<#if (isAddAction || role.authorityList.contains("ROLE_LOG"))!> checked</#if> />日志管理
						</label>
					</td>
				</tr>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">网站设置: </a>
					</th>
					<td>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_SETTING"<#if (isAddAction || role.authorityList.contains("ROLE_SETTING"))!> checked</#if> />系统设置
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_INSTANT_MESSAGING"<#if (isAddAction || role.authorityList.contains("ROLE_INSTANT_MESSAGING"))!> checked</#if> />在线客服
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_PAYMENT_CONFIG"<#if (isAddAction || role.authorityList.contains("ROLE_PAYMENT_CONFIG"))!> checked</#if> />支付方式管理
						</label>
					</td>
				</tr>
				<#if (role.isSystem)!false>
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<span class="warnInfo"><span class="icon">&nbsp;</span>系统提示: </b>系统内置角色不允许修改!</span>
						</td>
					</tr>
				</#if>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>