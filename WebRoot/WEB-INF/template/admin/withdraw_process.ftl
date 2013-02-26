<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>处理体现单- XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $successProcessButton = $("#successProcessButton");
	var $invalidProcessButton = $("#invalidProcessButton");
	// 通过
	$successProcessButton.click( function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "确定通过吗?", ok: "确 定", cancel: "取 消", width: 420, modal: true, okCallback: successProcess});
		function successProcess() {
			$.ajax({
				url: "withdraw!successProcess.action",
				data: {id: "${withdraw.id}"},
				type: "POST",
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$this.attr("disabled", true);
				},
				success: function(data) {
					$.message({type: data.status, content: data.message});
					if (data.status == "success") {
						 window.localtion.href="${base}/admin/withdraw!applying.action";
					} else {
						$this.attr("disabled", true);
					}
				}
			});
		}
	});
	// 作废
	$invalidProcessButton.click( function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "确定作废吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: loseProcess});
		function loseProcess() {
			$.ajax({
				url: "withdraw!loseProcess.action",
				data: {id: "${withdraw.id}"},
				type: "POST",
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$this.attr("disabled", true);
				},
				success: function(data) {
					$.message({type: data.status, content: data.message});
					if (data.status == "success") {
						window.localtion.href="${base}/admin/withdraw!list.action";
					} else {
						$this.attr("disabled", false);
					}
				}
			});
		}
	});	
})
</script>
</head>
<body class="input">
	<div class="bar">
		处理提现单
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
			<table class="inputTable">
				<tr>
					<th>
						申请人: 
					</th>
					<td>
						${(withdraw.member.username)!}
					</td>
				</tr>
				<tr>
					<th>
						提现账户: 
					</th>
					<td>
						提现账号：${(withdraw.memberBank.banknum)!}<br/>
						开户姓名：${(withdraw.memberBank.openname)!}<br/>
						银行名称：${(withdraw.memberBank.bankname)!}<br/>
						银行地址：${(withdraw.memberBank.bankcity)!}<br/>
						支行明细：${(withdraw.memberBank.bankdetail)!}<br/>
					</td>
				</tr>
				<tr>
					<th>
						提现金额: 
					</th>
					<td>
						${withdraw.money?string(currencyFormat)}
					</td>
				</tr>
				<tr>
					<th>
						审核说明: 
					</th>
					<td>
						<textarea rows="4"></textarea>
					</td>
				</tr>
				<#if withdraw.withdrawStatus == "success" || withdraw.withdrawStatus == "lose">
					<tr>
					<th>
						处理结果: 
					</th>
					<td>
						${action.getText("WithdrawStatus." + withdraw.withdrawStatus)}
					</td>
					</tr>								
				</#if>
			</table>
			<div class="buttonArea">
				<#if withdraw.withdrawStatus == "apply">
						<input type="button" id="successProcessButton" name="successProcess" class="formButton" value="通过审核" hidefocus />						
						<input type="button" id="invalidProcessButton" name="invalidProcess" class="formButton" value="作  废" hidefocus />
						</#if>
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
	</div>
</body>
</html>