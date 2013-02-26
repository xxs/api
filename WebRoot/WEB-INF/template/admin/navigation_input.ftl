<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑导航 - XXS</title>
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
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");

	var $systemUrl = $("#systemUrl");
	var $url = $("#url");

	// 将选择的系统内容地址填充至链接地址中
	$systemUrl.change( function() {
		$url.val($systemUrl.val());
	});
	
	// 链接地址内容修改时,系统内容选择框修改为不选择任何项目
	$url.keypress( function() {
		$systemUrl.val("");
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"navigation.name": "required",
			"navigation.url": "required",
			"navigation.orderList": 	"digits"
		},
		messages: {
			"navigation.name": "请填写名称",
			"navigation.url": "请填写链接地址",
			"navigation.orderList": 	"排序必须为零或正整数"
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
		<#if isAddAction>添加导航<#else>编辑导航</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>navigation!save.action<#else>navigation!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						名称: 
					</th>
					<td>
						<input type="text" id="name" name="navigation.name" class="formText" value="${(navigation.name)!}" />	 
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						系统内容: 
					</th>
					<td>
						<select id="systemUrl">
							<option value="">------------</option>
							<option value="<#noparse>${base}/</#noparse>"<#if ("$" + "{base}/" == navigation.url)!> selected</#if>>网站首页</option>
							<option value="<#noparse>${base}</#noparse>/card/leave_message.htm"<#if ("$" + "{base}/card/leave_message.htm" == navigation.url)!> selected</#if>>在线留言</option>
							<option value="<#noparse>${base}</#noparse>/card/deposit_success.htm"<#if ("$" + "{base}/card/deposit_success.htm" == navigation.url)!> selected</#if>>成功案例</option>
							<#list articleCategoryTreeList as articleCategoryTree>
								<option value="<#noparse>${base}</#noparse>${articleCategoryTree.url}"<#if (("$" + "{base}" + articleCategoryTree.url) == navigation.url)!> selected</#if>>
									<#if articleCategoryTree.grade != 0>
										<#list 1..articleCategoryTree.grade as i>
											&nbsp;&nbsp;
										</#list>
									</#if>
									${articleCategoryTree.name}
								</option>
							</#list>
							<#list cardsCategoryTreeList as cardsCategoryTree>
								<option value="<#noparse>${base}</#noparse>${cardsCategoryTree.url}"<#if (("$" + "{base}" + cardsCategoryTree.url) == navigation.url)!> selected</#if>>
									<#if cardsCategoryTree.grade != 0>
										<#list 1..cardsCategoryTree.grade as i>
											&nbsp;&nbsp;
										</#list>
									</#if>
									${cardsCategoryTree.name}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						链接地址: 
					</th>
					<td>
						<input type="text" id="url" name="navigation.url" class="formText" value="${(navigation.url)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						位置: 
					</th>
					<td>
						<select name="navigation.navigationPosition">
							<option value="top" <#if (navigation.navigationPosition == "top")!> selected</#if>>
								${action.getText("NavigationPosition.top")}
							</option>
							<option value="middle" <#if (isAddAction || navigation.navigationPosition == "middle")!> selected</#if>>
								${action.getText("NavigationPosition.middle")}
							</option>
							<option value="bottom" <#if (navigation.navigationPosition == "bottom")!> selected</#if>>
								${action.getText("NavigationPosition.bottom")}
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<label>
							<@checkbox name="navigation.isVisible" value="${(navigation.isVisible)!true}" />显示
						</label>
						<label>
							<@checkbox name="navigation.isBlankTarget" value="${(navigation.isBlankTarget)!false}" />在新窗口中打开
						</label>
					</td>
				</tr>
				<tr>
					<th>
						排序: 
					</th>
					<td>
						<input type="text" name="navigation.orderList" class="formText" value="${(navigation.orderList)!}" title="只允许输入零或正整数" />
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