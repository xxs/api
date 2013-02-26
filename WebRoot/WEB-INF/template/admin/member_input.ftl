<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑会员 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");

	var $areaSelect = $("#areaSelect");
	var $tab = $("#tab");
	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});

	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			<#if isAddAction>
				"member.username": {
					required: true,
					minlength: 2,
					maxlength: 20,
					username: true,
					remote: "member!checkUsername.action"
				},
			</#if>
			"member.password": {
				<#if isAddAction>
					required: true,
				</#if>
				minlength: 4,
				maxlength: 20
			},
			"rePassword": {
				<#if isAddAction>
					required: true,
				</#if>
				equalTo: "#password"
			},
			"member.email": {
				required: true,
				email: true
			},
			"member.score": {
				required: true,
				digits: true
			},
			"member.deposit": {
				required: true,
				min: 0
			}
			<#list memberAttributeList as memberAttribute>
				<#if memberAttribute.isRequired || memberAttribute.attributeType == "number" || memberAttribute.attributeType == "alphaint">
					,"memberAttributeValueMap['${memberAttribute.id}']": {
						<#if memberAttribute.isRequired>
							<#if memberAttribute.attributeType == "number" || memberAttribute.attributeType == "alphaint">
								required: true,
							<#else>
								required: true
							</#if>
						</#if>
						<#if memberAttribute.attributeType == "number">
							<#if memberAttribute.attributeType == "alphaint">
								number: true,
							<#else>
								number: true
							</#if>
						</#if>
						<#if memberAttribute.attributeType == "alphaint">
							lettersonly: true
						</#if>
					}
				</#if>
			</#list>
		},
		messages: {
			<#if isAddAction>
				"member.username": {
					required: "请填写用户名",
					minlength: "用户名长度必须大于等于2",
					maxlength: "用户名长度必须小于等于20",
					username: "用户名只允许包含中文、英文、数字和下划线",
					remote: "用户名已存在"
				},
			</#if>
			"member.password": {
				<#if isAddAction>
					required: "请填写密码",
				</#if>
				minlength: "密码长度必须大于等于4",
				maxlength: "密码长度必须小于等于20"
			},
			"rePassword": {
				<#if isAddAction>
					required: "请填写重复密码",
				</#if>
				equalTo: "两次密码输入不一致"
			},
			"member.email": {
				required: "请填写E-mail",
				email: "E-mail格式不正确"
			},
			"member.score": {
				required: "请填写积分",
				digits: "积分必须为零或正整数"
			},
			"member.deposit": {
				required: "请填写预存款",
				min: "预存款必须为零或正数"
			}
			<#list memberAttributeList as memberAttribute>
				<#if memberAttribute.isRequired || memberAttribute.attributeType == "number" || memberAttribute.attributeType == "alphaint">
					,"memberAttributeValueMap['${memberAttribute.id}']": {
						<#if memberAttribute.isRequired>
							<#if memberAttribute.attributeType == "number" || memberAttribute.attributeType == "alphaint">
								required: "请填写${memberAttribute.name}",
							<#else>
								required: "请填写${memberAttribute.name}"
							</#if>
						</#if>
						<#if memberAttribute.attributeType == "number">
							<#if memberAttribute.attributeType == "alphaint">
								number: "${memberAttribute.name}只允许输入数字",
							<#else>
								number: "${memberAttribute.name}只允许输入数字"
							</#if>
						</#if>
						<#if memberAttribute.attributeType == "alphaint">
							lettersonly: "${memberAttribute.name}只允许输入字母"
						</#if>
					}
				</#if>
			</#list>
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});

});
</script>
</head>
<body class="input">
	<div class="bar">
		<#if isAddAction>添加会员<#else>编辑会员</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>member!save.action<#else>member!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus />
				</li>
				<li>
					<input type="button" value="预存款信息" hidefocus />
				</li>
				<li>
					<input type="button" value="银行卡信息" hidefocus />
				</li>
				<li>
					<input type="button" value="商户信息" hidefocus />
				</li>
			</ul>
			<table id="infoTable" class="inputTable tabContent">
				<tr>
					<th>
						用户名: 
					</th>
					<td>
						<#if isAddAction>
							<input type="text" name="member.username" class="formText" />
							<label class="requireField">*</label>
						<#else>
							${member.username}
							<input type="hidden" name="member.username" value="${(member.username)!}" />
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						密 码: 
					</th>
					<td>
						<input type="password" id="password" name="member.password" class="formText" />
						<#if isAddAction><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr>
					<th>
						确认密码: 
					</th>
					<td>
						<input type="password" name="rePassword" class="formText" />
						<#if isAddAction><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr>
					<th>
						E-mail: 
					</th>
					<td>
						<input type="text" name="member.email" class="formText" value="${(member.email)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						积分: 
					</th>
					<td>
						<input type="text" name="member.score" class="formText" value="${(member.score)!"0"}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						预存款: 
					</th>
					<td>
						<input type="text" name="member.deposit" class="formText" value="${(member.deposit)!"0"}" title="只允许输入大于或等于零的数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						会员等级
					</th>
					<td>
						<select name="member.memberRank.id">
							<#list allMemberRankList as memberRank>
								<option value="${memberRank.id}"<#if ((isAddAction && memberRank.isDefault) || (isEditAction && memberRank == member.memberRank))!> selected</#if>>${memberRank.name}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<label>
							<@checkbox name="member.isAccountEnabled" value="${(member.isAccountEnabled)!true}" />启用
						</label>
					</td>
				</tr>
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						&nbsp;
					</td>
				</tr>
				<#if isEditAction>
					<tr>
						<th>
							注册日期
						</th>
						<td>
							${(member.createDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
					</tr>
					<tr>
						<th>
							注册IP
						</th>
						<td>
							${(member.registerIp)!}
						</td>
					</tr>
				</#if>
				<#list memberAttributeList as memberAttribute>
					<tr>
						<th>
							${memberAttribute.name}: 
						</th>
						<td>
							<#if memberAttribute.systemAttributeType??>
								<#if memberAttribute.systemAttributeType == "name">
									<input type="text" name="memberAttributeValueMap['${memberAttribute.id}']" class="formText" value="${(member.getMemberAttributeValue(memberAttribute))!}" />
									<#if memberAttribute.isRequired><label class="requireField">*</label></#if>
								<#elseif memberAttribute.systemAttributeType == "gender">
									<label><input type="radio" name="memberAttributeValueMap['${memberAttribute.id}']" value="male"<#if (member.getMemberAttributeValue(memberAttribute) == "male")!> checked</#if> />男</label>
									<label><input type="radio" name="memberAttributeValueMap['${memberAttribute.id}']" value="female"<#if (member.getMemberAttributeValue(memberAttribute) == "female")!> checked</#if> />女</label>
									<#if memberAttribute.isRequired><label class="requireField">*</label></#if>
								<#elseif memberAttribute.systemAttributeType == "birth">
									<input type="text" name="memberAttributeValueMap['${memberAttribute.id}']" class="formText" value="${(member.getMemberAttributeValue(memberAttribute))!}" onclick="WdatePicker()" />
									<#if memberAttribute.isRequired><label class="requireField">*</label></#if>
								<#elseif memberAttribute.systemAttributeType == "area">
									<input type="text" id="areaSelect" name="memberAttributeValueMap['${memberAttribute.id}']" class="hidden" value="${(member.getMemberAttributeValue(memberAttribute).id)!}" defaultSelectedPath="${(member.getMemberAttributeValue(memberAttribute).path)!}" />
									<#if memberAttribute.isRequired><label class="requireField">*</label></#if>
								<#elseif memberAttribute.systemAttributeType == "address">
									<input type="text" name="memberAttributeValueMap['${memberAttribute.id}']" class="formText" value="${(member.getMemberAttributeValue(memberAttribute))!}" />
									<#if memberAttribute.isRequired><label class="requireField">*</label></#if>
								<#elseif memberAttribute.systemAttributeType == "zipCode">
									<input type="text" name="memberAttributeValueMap['${memberAttribute.id}']" class="formText" value="${(member.getMemberAttributeValue(memberAttribute))!}" />
									<#if memberAttribute.isRequired><label class="requireField">*</label></#if>
								<#elseif memberAttribute.systemAttributeType == "phone">
									<input type="text" name="memberAttributeValueMap['${memberAttribute.id}']" class="formText" value="${(member.getMemberAttributeValue(memberAttribute))!}" />
									<#if memberAttribute.isRequired><label class="requireField">*</label></#if>
								<#elseif memberAttribute.systemAttributeType == "mobile">
									<input type="text" name="memberAttributeValueMap['${memberAttribute.id}']" class="formText" value="${(member.getMemberAttributeValue(memberAttribute))!}" />
									<#if memberAttribute.isRequired><label class="requireField">*</label></#if>
								<#elseif memberAttribute.systemAttributeType == "referrer">
									<input type="text" name="memberAttributeValueMap['${memberAttribute.id}']" class="formText" value="${(member.getMemberAttributeValue(memberAttribute))!}" />
									<#if memberAttribute.isRequired><label class="requireField">*</label></#if>
								</#if>
							<#else>
								<#if memberAttribute.attributeType == "text">
									<input type="text" name="memberAttributeValueMap['${memberAttribute.id}']" class="formText" value="${(member.getMemberAttributeValue(memberAttribute))!}" />
									<#if memberAttribute.isRequired><label class="requireField">*</label></#if>
								<#elseif memberAttribute.attributeType == "number">
									<input type="text" name="memberAttributeValueMap['${memberAttribute.id}']" class="formText" value="${(member.getMemberAttributeValue(memberAttribute))!}" />
									<#if memberAttribute.isRequired><label class="requireField">*</label></#if>
								<#elseif memberAttribute.attributeType == "alphaint">
									<input type="text" name="memberAttributeValueMap['${memberAttribute.id}']" class="formText" value="${(member.getMemberAttributeValue(memberAttribute))!}" />
									<#if memberAttribute.isRequired><label class="requireField">*</label></#if>
								<#elseif memberAttribute.attributeType == "select">
									<select name="memberAttributeValueMap['${memberAttribute.id}']">
										<option value="">请选择...</option>
										<#list memberAttribute.optionList as option>
											<option value="${option}"<#if (option == member.getMemberAttributeValue(memberAttribute))!> selected</#if>>
												${option}
											</option>
										</#list>
									</select>
									<#if memberAttribute.isRequired><label class="requireField">*</label></#if>
								<#elseif memberAttribute.attributeType == "checkbox">
									<#list memberAttribute.optionList as option>
										<label>
											<input type="checkbox" name="memberAttributeValueMap['${memberAttribute.id}']" value="${option}"<#if (member.getMemberAttributeValue(memberAttribute).contains(option))!> checked</#if> />${option}
										</label>
									</#list>
									<#if memberAttribute.isRequired><label class="requireField">*</label></#if>
								</#if>
							</#if>
						</td>
					</tr>
				</#list>
				<#if isEditAction>
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<span class="warnInfo"><span class="icon">&nbsp;</span>如需修改密码请填写密码,若留空则密码将保持不变</span>
						</td>
					</tr>
				</#if>
			</table>
			<table id="memberBankTable" class="inputTable tabContent">
				<tr class="title">
					<th>
						操作类型
					</th>
					<th>
						存入金额
					</th>
					<th>
						支出金额
					</th>
					<th>
						手续费率
					</th>
					<th>
						当前余额
					</th>
					<th>
						被推荐人
					</th>
					<th>
						提成订单号
					</th>
				</tr>
				<#list (member.depositSet)! as deposit>
					<tr >
						<td>
							${action.getText("DepositType." + deposit.depositType)}
						</td>
						<td>
							${deposit.credit}
						</td>
						<td>
							${deposit.debit}
						</td>
						<td>
							${deposit.lossrate}
						</td>
						<td>
							${deposit.balance}
						</td>
						<td>
							${deposit.referrer}
						</td>
						<td>
							${deposit.orderSn}
						</td>
					</tr>
				</#list>
			</table>
			<table id="memberBankTable" class="inputTable tabContent">
				<tr class="title">
					<th>
						银行卡号
					</th>
					<th>
						开户姓名
					</th>
					<th>
						银行名称
					</th>
					<th>
						支行名称
					</th>
					<th>
						银行所在地
					</th>
					<th>
						是否默认
					</th>
					<th>
						操作
					</th>
				</tr>
				<#list (member.memberBankSet)! as memberBank>
					<tr >
						<td>
							${memberBank.banknum}
						</td>
						<td>
							${memberBank.openname}
						</td>
						<td>
							${memberBank.bankname}
						</td>
						<td>
							${memberBank.bankcity}
						</td>
						<td>
							${memberBank.bankdetail}
						</td>
						<td>
							${memberBank.isDefault}
						</td>
						<td>
							<span class="deleteIcon" title="删 除">&nbsp;</span>
						</td>
					</tr>
				</#list>
			</table>
			<table id="memberBusinessTable" class="inputTable tabContent">
				<tr>
					<th>
						商户类型
					</th>
					<td>
						${action.getText("MemberBusiness." + member.memberBusiness.businessType)}
					</td>
					<th>
						真实姓名 
					</th>
					<td>
						${member.memberBusiness.realName}
					</td>
				</tr>
				<tr>
					<th>
						身份证（营业执照）
					</th>
					<td>
						${member.memberBusiness.businessNumber}
					</td>
					<th>
						商户名称 
					</th>
					<td>
						${member.memberBusiness.businessName}
					</td>
				</tr>
				<tr>
					<th>
						商户地区
					</th>
					<td>
						${member.memberBusiness.city}
					</td>
					<th>
						经营范围
					</th>
					<td>
						${member.memberBusiness.scope}
					</td>
				</tr>
				<tr>
					<th>
						商城网址
					</th>
					<td>
						<a href="${member.memberBusiness.url}">${member.memberBusiness.url}</a>
					</td>
					<th>
						ICP证备案号
					</th>
					<td>
						${member.memberBusiness.icp}
					</td>
				</tr>
				<tr>
					<th>
						联系人
					</th>
					<td>
						${member.memberBusiness.linkMan}
					</td>
					<th>
						业务电话
					</th>
					<td>
						${member.memberBusiness.tel}
					</td>
				</tr>
				<tr>
					<th>
						业务手机
					</th>
					<td>
						${member.memberBusiness.phone}
					</td>
					<th>
						服务热线
					</th>
					<td>
						${member.memberBusiness.serverTel}
					</td>
				</tr>
				<tr>
					<th>
						客服工作时间
					</th>
					<td>
						${member.memberBusiness.serverTime}
					</td>
					<th>
						即时通讯
					</th>
					<td>
						${member.memberBusiness.QQ}
					</td>
				</tr>
				<tr>
					<th>
						通讯地址
					</th>
					<td>
						${member.memberBusiness.address}
					</td>
					<th>
						邮编
					</th>
					<td>
						${member.memberBusiness.zipcode}
					</td>
				</tr>
				<tr>
					<th>
						备注
					</th>
					<td colspan="3">
						${member.memberBusiness.mome}
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