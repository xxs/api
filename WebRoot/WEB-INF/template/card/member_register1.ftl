<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会员注册<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#if (article.metaKeywords)! != ""><meta name="keywords" content="${article.metaKeywords}" /></#if>
<#if (article.metaDescription)! != ""><meta name="description" content="${article.metaDescription}" /></#if>
<link href="${base}/template/card/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/card/css/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/template/card/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/card/js/Validate.js"></script>
<script type="text/javascript" src="${base}/template/card/js/Validate.form.js"></script>

<!--[if lte IE 6]>
	<script type="text/javascript" src="${base}/template/common/js/belatedPNG.js"></script>
	<script type="text/javascript">
		// 解决IE6透明PNG图片BUG
		DD_belatedPNG.fix(".belatedPNG");
	</script>
<![endif]-->

<script type="text/javascript">
	$().ready(function() {
		alert("123");
	})
</script>
</head>
<body>
	<#include "/WEB-INF/template/card/header.ftl">
	<div class="blank"></div>
<div id="formbox">
	<form id="formpersonal" method="post" onsubmit="return false;">
	<div class="form">
		<h3>帐户信息</h3>
		<div class="item">
			<span class="label"><span class="red">*</span>用户名：</span>
			<div class="fl">
				<input type="text" id="username" name="username" class="text" tabindex="1" />
				<label id="username_succeed" class="blank"></label>
				<span class="clear"></span>
				<div id="username_error"></div>
			</div>
		</div><!--item end-->
		
		<div id="o-password">
			<div class="item">
				<span class="label"><span class="red">*</span>设置密码：</span>
				<div class="fl">
					<input type="password" id="pwd" name="pwd" class="text" tabindex="2"/>
					<label id="pwd_succeed" class="blank"></label>
					<input type="checkbox" class="checkbox" id="viewpwd"/>
					<label class="ftx23" for="viewpwd">显示密码字符</label>
					<span class="clear"></span>
					<label class="hide" id="pwdstrength"><span class="fl">安全程度：</span><b></b></label>
					<label id="pwd_error"></label>
				</div>
			</div><!--item end-->
			
			<div class="item">
				<span class="label"><span class="red">*</span>确认密码：</span>
				<div class="fl">
					<input type="password" id="pwd2" name="pwd2" class="text" tabindex="3"/>
					<label id="pwd2_succeed" class="blank"></label>
					<span class="clear"></span>
					<label id="pwd2_error"></label>
				</div>
			</div><!--item end-->
			
		</div><!--o-password end-->
		
		<div class="item">
			<span class="label"><span class="red">*</span>邮箱：</span>
			<div class="fl">
				<input type="text" id="mail" name="mail" class="text" tabindex="4"/>
				<label id="mail_succeed" class="blank"></label>
				<span class="clear"></span>
				<div id="mail_error"></div>
			</div>
		</div><!--item end-->
		
		<div class="item">
			<span class="label">推荐人用户名：</span>
			<div class="fl">
				<input type="text" id="referrer" name="referrer" class="text" value=可不填 tabindex="5" />
				<label id="referrer_succeed" class="blank invisible"></label>
				<span class="clear"></span>
				<label id="referrer_error"></label>
			</div>
		</div><!--item end-->
		
		<div class="item">
			<span class="label"><span class="red">*</span>验证码：</span>
			<div class="fl">
				<input type="text" id="authcode" name="authcode" class="text text-1" autocomplete="off" MaxLength="6" tabindex="6" />
				<label class="img"><img id="JD_Verification1" Ver_ColorOfNoisePoint="#888888" src="http://www.mailuke.com/Inc/Code/adminCode.php?sesstr=regCode" onClick="chanageCode('regCode')" alt="" style="cursor:pointer;width:100px;height:26px;"/>
				</label>
				<label>&nbsp;看不清？点击验证码图片更换</label>
				<label id="authcode_succeed" class="blank invisible"></label>
				<span class="clear"></span>
				<label id="authcode_error"></label>
			</div>
		</div><!--item end-->
	
		<h3>联系人信息</h3>
		<div class="item">
			<span class="label"><span class="red">*</span>联系人姓名：</span>
			<div class="fl">
				<input type="text" id="realname" name="realname" class="text" tabindex="7"/>
				<label id="realname_succeed" class="blank"></label>
				<span class="clear"></span>
				<label id="realname_error"></label>
			</div>
		</div><!--item end-->
		
		<div class="item">
			<span class="label"><span class="red">*</span>所在部门：</span>
			<div class="fl">
				<select rel="select" id="department" name="department" tabindex="8">
					<option value="-1"  >请选择</option>
					<option value="1"  >办公室</option>
					<option value="2"  >市场部</option>
					<option value="3"  >采购部</option>
					<option value="4"  >技术部</option>
					<option value="5"  >人力资源</option>
					<option value="6"  >其他</option>
				</select>
				<label id="department_succeed" class="blank"></label>
				<span class="clear"></span>
				<label id="department_error"></label>
			</div>
		</div><!--item end-->
		
		<div class="item">
			<span class="label"><span class="red">*</span>固定电话：</span>
			<div class="fl">
				<input type="text" id="tel" name="tel" class="text" value="" tabindex="9" />
				<label id="tel_succeed" class="blank"></label>
				<span class="clear"></span>
				<label id="tel_error"></label>
			</div>
		</div><!--item end-->
		
		<div class="item">
			<span class="label"><span class="red">*</span>手机：</span>
			<div class="fl">
				<input type="text" id="mobile" name="mobile" class="text" value="" tabindex="10" />
				<label id="mobile_succeed" class="blank"></label>
				<span class="clear"></span>
				<label id="mobile_error"></label>
			</div>
		</div><!--item end-->
	
		<h3>公司信息</h3>
		<div class="item">
			<span class="label"><span class="red">*</span>公司名称：</span>
			<div class="fl">
				<input type="text" id="companyname" name="companyname" class="text" tabindex="11"/>
				<label id="companyname_succeed" class="blank"></label>
				<span class="clear"></span>
				<label id="companyname_error"></label>
			</div>
		</div><!--item end-->
		
		<div class="item">
			<span class="label"><span class="red">*</span>公司地址：</span>
			<div class="fl">
				<input type="text" id="companyaddr" name="companyaddr" class="text" tabindex="12" />
				<label id="companyaddr_succeed" class="blank"></label>
				<span class="clear"></span>
				<label id="companyaddr_error"></label>
			</div>
		</div><!--item end-->
		
		<div class="item">
			<span class="label"><span class="red">*</span>购买类型/用途：</span>
			<div class="fl">
				<input id="purpose1" tabindex="13" class="checkbox" name="purposetype" runant="server" type="checkbox" value="1" />
				<label class="pad" for="purpose1">IT设备</label>
				<input id="purpose2" tabindex="13" class="checkbox" name="purposetype" runant="server" type="checkbox" value="2" />
				<label class="pad" for="purpose2">数码通讯</label>
				<input id="purpose3" tabindex="13" class="checkbox" name="purposetype" runant="server" type="checkbox" value="3" />
				<label class="pad" for="purpose3">办公用品耗材</label>
				<input id="purpose4" tabindex="13" class="checkbox" name="purposetype" runant="server" type="checkbox" value="4" />
				<label class="pad" for="purpose4">大家电</label>
				<input id="purpose5" tabindex="13" class="checkbox" name="purposetype" runant="server" type="checkbox" value="5" />
				<label class="pad" for="purpose5">项目合作-政府采购</label>
				<input id="purpose6" tabindex="13" class="checkbox" name="purposetype" runant="server" type="checkbox" value="6" />
				<label class="pad" for="purpose6">礼品</label>
				<input id="purpose" type="hidden" value="" />
				<label id="purpose_succeed" class="blank"></label>
				<span class="clear"></span>
				<label id="purpose_error"></label>
			</div>
		</div><!--item end-->
		
		<div class="item">
			<span class="label"><span class="red">*</span>公司网址：</span>
			<div class="fl">
				<input type="text" id="companysite" name="companysite" class="text" tabindex="14"/>
				<label id="companysite_succeed" class="blank"></label>
				<span class="clear"></span>
				<label id="companysite_error"></label>
			</div>
		</div><!--item end-->
	
		<div class="item">
			<span class="label">&nbsp;</span>
			<div class="fl">
				<input type="checkbox" name="protocol" id="protocol" tabindex="7" /><label for="protocol">网站用户注册协议</label>
			</div>
		</div><!--item end-->
		
		<div class="item">
			<span class="label">&nbsp;</span>
			<input type="button" class="yellow_button disabled" id="registsubmit" value="提交注册信息" tabindex="8" disabled="disabled" />
		</div><!--item end-->
		
	</div>
	</form>
</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/card/friend_link.ftl">
	<div class="blank"></div>
	<#include "/WEB-INF/template/card/footer.ftl">
</body>
</html>