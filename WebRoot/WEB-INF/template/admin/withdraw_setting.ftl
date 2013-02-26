<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>提现设置 - XXS</title>
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
		提现设置
	</div>
	<div class="body">
		<form id="validateForm" action="withdraw!settingUpdate.action" method="post">
			<table class="inputTable">
				<tr>
					<th>
						单次最小体现额:
					</th>
					<td>
						<input type="text" name="withdrawEveryMinMoney" class="formText" value="${(setting.withdrawEveryMinMoney)!}" title="只允许输入零或正整数,每次提现的最小金额，单位: 元,0表示无限制"/>
						<label class="requireField">*(只允许输入零或正整数,每次提现的最小金额，单位: 元,0表示无限制)</label>
					</td>
				</tr>
				<tr>
					<th>
						单次最大体现额: 
					</th>
					<td>
						<input type="text" name="withdrawEveryMaxMoney" class="formText" value="${(setting.withdrawEveryMaxMoney)!}" title="只允许输入零或正整数,每次提现的最大金额，单位: 元,0表示无限制"/>
						<label class="requireField">*(只允许输入零或正整数,每次提现的最大金额，单位: 元,0表示无限制)</label>
					</td>
				</tr>
				<tr>
					<th>
						单日最大提现次数: 
					</th>
					<td>
						<input type="text" name="withdrawEveryDayCount" class="formText" value="${(setting.withdrawEveryDayCount)!}" title="只允许输入零或正整数,每次提现的最大次数，单位: 次,0表示无限制" />
						<label class="requireField">*(只允许输入零或正整数,每次提现的最大次数，单位: 次,0表示无限制)</label>
					</td>
				</tr>
				<tr>
					<th>
						单日最大提现金额: 
					</th>
					<td>
						<input type="text" name="withdrawEveryDayMoney" class="formText" value="${(setting.withdrawEveryDayMoney)!}" title="只允许输入零或正整数,每天提现的最大总金额，单位: 元,0表示无限制" />
						<label class="requireField">*(只允许输入零或正整数,每天提现的最大总金额，单位: 元,0表示无限制)</label>
					</td>
				</tr>
				<tr>
					<th>
						申请中的最大总记录数: 
					</th>
					<td>
						<input type="text" name="withdrawMaxCount" class="formText" value="${(setting.withdrawMaxCount)!}" title="只允许输入零或正整数,状态为申请中的最大总记录数，单位: 次,0表示无限制" />
						<label class="requireField">*(只允许输入零或正整数,状态为申请中的最大总记录数，单位: 次,0表示无限制)</label>
					</td>
				</tr>
				<tr>
					<th>
						申请中的最大总金额：
					</th>
					<td>
						<input type="text" name="withdrawMaxMoney" class="formText" value="${(setting.withdrawMaxMoney)!}" title="只允许输入零或正整数,状态为申请中的最大总金额，单位: 元,0表示无限制" />
						<label class="requireField">*(只允许输入零或正整数,状态为申请中的最大总金额，单位: 元,0表示无限制)</label>
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