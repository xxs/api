
xxs = {
	base: "${base}",
	currencySign: "${setting.currencySign}",
	currencyUnit: "${setting.currencyUnit}",
	priceScale: "${setting.priceScale}",
	priceRoundType: "${setting.priceRoundType}"
};

// 货币格式化
function currencyFormat(price) {
	price = setScale(price, xxs.priceScale, xxs.priceRoundType);
	return xxs.currencySign + price + xxs.currencyUnit;
}

$().ready( function() {

	/* ---------- ArticleContent ---------- */
	
	var $articleContent = $("#articleContent");
	if ($articleContent.size() > 0) {
		$changeBigFontSize = $("#changeBigFontSize");
		$changeNormalFontSize = $("#changeNormalFontSize");
		$changeSmallFontSize = $("#changeSmallFontSize");
		
		$changeBigFontSize.click(function () {
			$articleContent.css({"font-size": "16px"});
		});
		
		$changeNormalFontSize.click(function () {
			$articleContent.css({"font-size": "14px"});
		});
		
		$changeSmallFontSize.click(function () {
			$articleContent.css({"font-size": "12px"});
		});
	}
	
	$.showQuestionVerifyWindow = function (question,redirectUrl) {
			
			<@compress single_line = true>
				if ($.trim(question) != "") {
					var questionVerifyWindowHtml = 
					'<form id="questionVerifyWindowForm" autocomplete="off">
						<table>
							<tr>
								<th>密保问题:  </th>
								<td>
									'+question+'
									<input type="hidden" id="questionVerifyWindowMemberQuestion" name="member.safeQuestion" value="'+question+'" />
								</td>
							</tr>
							<tr>
								<th>密保答案:  </th>
								<td>
									<input type="text" id="questionVerifyWindowMemberAnswer" name="member.safeAnswer" class="formText" />
								</td>
							</tr>
						</table>
					</form>';
				}else{
					var questionVerifyWindowHtml = 
					'<form id="questionVerifyWindowForm" autocomplete="off">
						<table>
							<tr>
								<th> </th>
								<td>
									您还没有设置密码问题，<a href="'+xxs.base +'/card/password!safe.action">请点击设置后再进行此操作</a>
								</td>
							</tr>
						</table>
					</form>';
				}
			</@compress>
			
			$.dialog({title: "会员密保验证", content: questionVerifyWindowHtml, ok: "验证", cancel: "取 消", id: "questionVerifyWindow", width: 420, okCallback: questionVerify, modal: true});
			
			var $questionVerifyWindowForm = $("#questionVerifyWindowForm");
			var $questionVerifyWindowMemberQuestion = $("#questionVerifyWindowMemberQuestion");
			var $questionVerifyWindowMemberAnswer = $("#questionVerifyWindowMemberAnswer");
			
			function questionVerify() {
				if ($.trim($questionVerifyWindowMemberQuestion.val()) == "") {
					$questionVerifyWindowMemberQuestion.focus();
					$.message({type: "warn", content: "密保问题不能为空!"});
					return false;
				}
				if ($.trim($questionVerifyWindowMemberAnswer.val()) == "") {
					$questionVerifyWindowMemberAnswer.focus();
					$.message({type: "warn", content: "密保答案不能为空!"});
					return false;
				}
				$.ajax({
					url: xxs.base + "/card/member!ajaxSafeQuestion.action",
					data: $questionVerifyWindowForm.serialize(),
					type: "POST",
					dataType: "json",
					cache: false,
					beforeSend: function() {
						$questionVerifyWindowForm.find("button").attr("disabled", true);
					},
					success: function(data) {
						if (data.status == "success") {
							$.closeDialog("questionVerifyWindow");
							if(redirectUrl != null) {
								location.href = redirectUrl;
							}
						}
						$.message({type: data.status, content: data.message});
					},
					complete: function() {
						$questionVerifyWindowForm.find("button").attr("disabled", false);
					}
				});
				return false;
			}
		}
		$.showAdviceWindow = function (title,content) {
			<@compress single_line = true>
				var adviceWindowHtml = 
					'<table>
							<tr>
								<th>标题：</th>
								<td>
									'+title+'
								</td>
							</tr>
							<tr>
								<th>内容：</th>
								<td>
									'+content+'
								</td>
							</tr>
						</table>';
			</@compress>
			$.dialog({title: "公告明细", content: adviceWindowHtml, ok: "确 定", cancel: "取 消", width: 420, modal: true});

		}
});