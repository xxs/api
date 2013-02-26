<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>密码找回<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/card/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/card/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/card/js/base.js"></script>
<script type="text/javascript" src="${base}/template/card/js/card.js"></script>
<!--[if lte IE 6]>
	<script type="text/javascript" src="${base}/template/common/js/belatedPNG.js"></script>
	<script type="text/javascript">
		// 解决IE6透明PNG图片BUG
		DD_belatedPNG.fix(".belatedPNG");
	</script>
<![endif]-->
<script type="text/javascript">
	$().ready(function() {
	
		var $passwordRecoverForm = $("#passwordRecoverForm");
		var $memberUsername = $("#memberUsername");
		var $memberEmail = $("#memberEmail");
		
		var $memberSafeQuestionTr = $("#memberSafeQuestionTr");
		var $memberSafeQuestion = $("#memberSafeQuestion");
		var $memberSafeAnswerTr = $("#memberSafeAnswerTr");
		var $memberSafeAnswer = $("#memberSafeAnswer");
		var $submitButton = $("#submitButton");
		var $status = $("#status");
	
		$passwordRecoverForm.submit( function() {
			if ($.trim($memberUsername.val()) == "") {
				$.dialog({type: "warn", content: "请输入用户名!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if ($.trim($memberEmail.val()) == "") {
				$.dialog({type: "warn", content: "请输入E-mail!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if (!/^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test($memberEmail.val())) {
				$.dialog({type: "warn", content: "E-mail格式错误!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if ($memberSafeAnswer.attr("disabled") == false && $.trim($memberSafeAnswer.val()) == "") {
				$.dialog({type: "warn", content: "请输入密码保护答案!", modal: true, autoCloseTime: 3000});
				return false;
			}
			
			$.ajax({
				url: "member!ajaxSendPasswordRecoverMail.action",
				data: $passwordRecoverForm.serialize(),
				type: "POST",
				dataType: "json",
				cache: false,
				beforeSend: function(data) {
					$status.html('<span class="loadingIcon">&nbsp;</span>正在检测密码保护状态,请稍后...');
					$submitButton.attr("disabled", true);
				},
				success: function(data) {
					if (data.status == "warn") {
						$memberSafeQuestion.text(data.safeQuestion);
						$memberSafeQuestionTr.show();
						$memberSafeAnswer.attr("disabled", false);
						$memberSafeAnswerTr.show();
						$status.text(data.message);
					} else if (data.status == "success") {
						$memberUsername.val("");
						$memberSafeAnswer.val("");
						$memberEmail.val("");
						$memberSafeQuestionTr.hide();
						$memberSafeAnswer.attr("disabled", true);
						$memberSafeAnswerTr.hide();
						$status.text(data.message);
						$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
					} else {
						$status.text(data.message);
						$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
					}
					$submitButton.attr("disabled", false);
				}
			});
			return false;
		});
		
		$memberUsername.change( function() {
			$memberSafeAnswer.val("");
			$memberSafeQuestionTr.hide();
			$memberSafeAnswer.attr("disabled", true);
			$memberSafeAnswerTr.hide();
			$status.text("");
		});
	
	})
</script>
<style type="text/css">
<!--
.center {
	width: 960px;
	line-height: 18px;
	margin:20px auto 0 auto;
}
.sendTable {
	width: 960px;
	line-height: 30px;
}

.sendTable .title td {
	height: 30px;
	padding-left: 100px;
	text-align: left;
	font-weight: bold;
	background-color: #ecf2f8;
}

.sendTable th {
	width: 195px;
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
<body>
	<#include "/WEB-INF/template/card/header.ftl">
	<div class="center">
	<div class="singlePage body passwordRecover">
		<div class="sendTable">
			<div class="left"></div>
			<div class="middle">
				<span class="icon">&nbsp;</span>找回密码
			</div>
			<div class="right"></div>
		</div>
		<div class="blank"></div>
		<div class="column">
	<div class="column_left">
    	<div class="column_left_1"><a href="reg.html">免费注册</a><a href="findpassword.html"  class="current" >找回密码</a><a href="login.html">立刻登陆</a>
        </div>
       <ul>
        	<li><a href="card.html">支持卡种</a></li>
            <li><a href="safeguard.html">交易保障</a></li>
            <li style=" text-align:left; padding-left:19px;width:66px;"><a href="success.html">成功案例</a></li>
            <li><a href="contribution.html">名臣公益</a></li>
         
        </ul>
        <div class="column_left_3">
        	<dl>
            	<span>帮助中心</span>
                <dt>
                <img src="images/saaaa/xin.png" width="16" height="16" alt="s" />
                <img src="images/saaaa/a3.jpg" width="16" height="16" alt="s" /> 
                <img src="images/saaaa/a2.jpg" width="16" height="16" alt="s" /> 
                <img src="images/saaaa/cell.png" width="16" height="16" alt="s" /> 
                <img src="images/saaaa/dlzq.gif" width="16" height="16" alt="s" /> 
                <img src="images/saaaa/zizhi.png" width="16" height="16" alt="s" /> 
                </dt>
                <dd><a href="help.html">新手入门</a></dd>
                <dd><a href="question.html">常见问题</a></dd>
                <dd><a href="withdraw.html">提现说明</a></dd>
                <dd><a href="contact.html">联系我们</a></dd>
                <dd><a href="business.html">商务合作</a></dd>
                <dd><a href="company.html">公司资质</a></dd>
               <!-- <p style="background:url(images/saaaa/a7.jpg) no-repeat;"><a href="#">添加</a></p>     
                <p  style="background:url(images/saaaa/a8.jpg) no-repeat;"><a href="#">管理</a></p>-->
        	</dl>
        </div>
    </div>
    <div class="column_right">
   	    <div class="column_right_1"><p><img src="images/hhh.jpg" width="38" height="37" alt="jj" /></p>
   	    忘记账号密码了？填写用户名和注册时留下的邮箱后进查收邮件修改密码！</div>
        <div class="column_right_2">
        	<div class="column_right_2_shang">
            	<span>
                <font>找回密码</font>
                </span>
            </div>     
   	    
        <form action="" method="post" >
        <ol id="need">

<li><label class="email">&nbsp;&nbsp;&nbsp;&nbsp;用户名：</label> <input name='' type='text' id='' maxlength="18" />*<dfn>填写您登录时使用的账号</dfn></li>

<li><label class="email">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮&nbsp;箱：</label> <input name='' type='text' id='' />*<dfn>填写注册时留下的邮箱</dfn></li>

</ol>
 <div class="anniu"><input type="submit" value="确 定" class="button"/><input type="reset" value="重新填写" class="button" style="margin-left:30px;" /></div>

         </form>
         
         </div>
        </div>
    </div>
		<div class="singlePageDetail">
			<form id="passwordRecoverForm">
				<table class="sendTable">
					<tr>
						<th>用户名: </th>
						<td>
							<input type="text" id="memberUsername" name="member.username" class="formText" />
						</td>
					</tr>
					<tr>
						<th>E-mail: </th>
						<td>
							<input type="text" id="memberEmail" name="member.email" class="formText" />
						</td>
					</tr>
					<tr id="memberSafeQuestionTr" class="hidden">
						<th>密码保护问题: </th>
						<td>
							<span id="memberSafeQuestion"></span>
						</td>
					</tr>
					<tr id="memberSafeAnswerTr" class="hidden">
						<th>密码保护答案: </th>
						<td>
							<input type="text" id="memberSafeAnswer" name="member.safeAnswer" class="formText" disabled />
						</td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td>
							<input type="submit" id="submitButton" class="formButton" value="确  定" hidefocus />
						</td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td height="20">
							<span class="gray"><span id="status"></span></span>&nbsp;
						</td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td>
							<span class="warnIcon">&nbsp;</span>如果忘记密码,请填写您的用户名和注册邮箱重新获取密码!
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/card/friend_link.ftl">
	</div>
	<div class="blank"></div>
	</div>
	<#include "/WEB-INF/template/card/footer.ftl">
	
</body>
</html>