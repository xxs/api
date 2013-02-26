<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>一键网站更新 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	// 根据更新选项显示/隐藏开始日期和结束日期
	$(".buildTypeInput").click( function() {
		if ($(this).val() == "date") {
			$(".dateTr").show();
		} else {
			$(".dateTr").hide();
		}
	})
	
	$("#inputForm").submit(function() {
		$("#buildType").val($(".buildTypeInput:checked").val());
		$("#maxResults").val($("#maxResultsInput").val());
		$("#beginDate").val($("#beginDateInput").val());
		$("#endDate").val($("#endDateInput").val());
	});

	var isInitialized = false;
	var buildCount = 0;
	var buildTime = 0;
	$("#inputForm").ajaxForm({
		dataType: "json",
		beforeSubmit: function(data) {
			var beginDateInputValue = $("#beginDateInput").val();
			var endDateInputValue = $("#endDateInput").val();
			var maxResultsInputValue = $("#maxResultsInput").val();
			if ($.trim(maxResultsInputValue) == "") {
				$.message({type: "warn", content: "请输入每次更新数!"});
				return false;
			}
			if (!/^[1-9]\d*$/.test(maxResultsInputValue)) {
				$.message({type: "warn", content: "每次更新数必须为正整数!"});
				return false;
			}
			
			if (!isInitialized) {
				isInitialized = true;
				$(".buildTypeInput").attr("disabled", true);
				$("#maxResultsInput").attr("disabled", true);
				$("#beginDateInput").attr("disabled", true);
				$("#endDateInput").attr("disabled", true);
				$("#submitButton").attr("disabled", true);
				$("#statusTr").show();
				$("#status").text("正在更新首页,请稍后...");
			}
		},
		success: function(data) {
			buildCount += data.buildCount;
			buildTime += data.buildTime;
			if (data.status == "indexFinish") {
				$("#status").text("正在更新登录页,请稍后...");
				$("#buildContent").val("login");
				$("#inputForm").submit();
			} else if (data.status == "loginFinish") {
				$("#status").text("正在更新注册协议页,请稍后...");
				$("#buildContent").val("registerAgreement");
				$("#inputForm").submit();
			} else if (data.status == "registerAgreementFinish") {
				$("#status").text("正在更新注册页,请稍后...");
				$("#buildContent").val("register");
				$("#inputForm").submit();
			} else if (data.status == "registerFinish") {
				$("#status").text("正在更新ADMIN.JS,请稍后...");
				$("#buildContent").val("adminJs");
				$("#inputForm").submit();
			} else if (data.status == "adminJsFinish") {
				$("#status").text("正在更新CARD.JS,请稍后...");
				$("#buildContent").val("cardJs");
				$("#inputForm").submit();
			} else if (data.status == "cardJsFinish") {
				$("#status").text("正在自定义错误页,请稍后...");
				$("#buildContent").val("errorPage");
				$("#inputForm").submit();
			} else if (data.status == "errorPageFinish") {
				$("#status").text("正在更新文章,请稍后...");
				$("#buildContent").val("article");
				$("#inputForm").submit();
			} else if (data.status == "articleBuilding") {
				var maxResults = Number($("#maxResults").val());
				var firstResult = data.firstResult;
				$("#status").text("正在更新文章[" + (firstResult + 1) + " - " + (firstResult + maxResults) + "],请稍后...");
				$("#buildContent").val("article");
				$("#firstResult").val(firstResult);
				$("#inputForm").submit();
			} else if (data.status == "articleFinish") {
				$("#status").text("正在更新充值卡,请稍后...");
				$("#buildContent").val("cards");
				$("#firstResult").val("0");
				$("#inputForm").submit();
			} else if (data.status == "cardsBuilding") {
				var maxResults = Number($("#maxResults").val());
				var firstResult = data.firstResult;
				$("#status").text("正在更新充值卡[" + (firstResult + 1) + " - " + (firstResult + maxResults) + "],请稍后...");
				$("#buildContent").val("cards");
				$("#firstResult").val(firstResult);
				$("#inputForm").submit();
			} else if (data.status == "cardsFinish") {
				$("#buildContent").val("");
				$("#firstResult").val("0");
				$("#statusTr").hide();
				$(".buildTypeInput").attr("disabled", false);
				$("#maxResultsInput").attr("disabled", false);
				$("#beginDateInput").attr("disabled", false);
				$("#endDateInput").attr("disabled", false);
				$("#submitButton").attr("disabled", false);
				
				var time;
				if (buildTime < 60000) {
					time = (buildTime / 1000).toFixed(2) + "秒";
				} else {
					time = (buildTime / 60000).toFixed(2) + "分";
				}
				$.dialog({type: "success", content: "网站更新成功! [更新总数: " + buildCount + " 耗时: " + time + "]", width: 380, modal: true, autoCloseTime: 3000});
				isInitialized = false;
				buildCount = 0;
				buildTime = 0;
			}
		}
	});

});
</script>
</head>
<body class="input">
	<div class="bar">
		一键网站更新
	</div>
	<div class="body">
		<form id="inputForm" action="build_html!all.action" method="post">
			<input type="hidden" id="buildType" name="buildType" value="" />
			<input type="hidden" id="maxResults" name="maxResults" value="" />
			<input type="hidden" id="firstResult" name="firstResult" value="0" />
			<input type="hidden" id="buildContent" name="buildContent" value="" />
			<input type="hidden" id="beginDate" name="beginDate" value="" />
			<input type="hidden" id="endDate" name="endDate" value="" />
			<table class="inputTable">
				<tr>
					<th>
						更新选项: 
					</th>
					<td>
						<label><input type="radio" name="buildTypeInput" class="buildTypeInput" value="date" checked />指定日期</label>&nbsp;&nbsp;
						<label><input type="radio" name="buildTypeInput" class="buildTypeInput" value="all" />更新所有</label>
					</td>
				</tr>
				<tr class="dateTr">
					<th>
						起始日期: 
					</th>
					<td>
						<input type="text" id="beginDateInput" name="" class="formText" value="${(defaultBeginDate?string("yyyy-MM-dd"))!}" title="留空则从最早的内容开始更新" onclick="WdatePicker()" />
					</td>
				</tr>
				<tr class="dateTr">
					<th>
						结束日期: 
					</th>
					<td>
						<input type="text" id="endDateInput" name="" class="formText" value="${(defaultEndDate?string("yyyy-MM-dd"))!}" title="留空则更新至最后的内容" onclick="WdatePicker()" />
					</td>
				</tr>
				<tr>
					<th>
						每次更新数
					</th>
					<td>
						<input type="text" id="maxResultsInput" name="" class="formText" value="50" />
					</td>
				</tr>
				<tr id="statusTr" class="hidden">
					<th>
						&nbsp;
					</th>
					<td>
						<span class="loadingBar">&nbsp;</span>
						<p id="status"></p>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" id="submitButton" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>