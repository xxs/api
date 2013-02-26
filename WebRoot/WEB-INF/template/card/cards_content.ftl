<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<#if (cards.metaKeywords)! != ""><meta name="keywords" content="${cards.metaKeywords}" /></#if>
<#if (cards.metaDescription)! != ""><meta name="description" content="${cards.metaDescription}" /></#if>
<#include "/WEB-INF/template/card/member_head.ftl">

</head>
<body id="cardsContent" class="cardsContent">
	<#include "/WEB-INF/template/card/member_header.ftl">
	<div class="content">
	<div class="contentLeft">
		<#include "/WEB-INF/template/card/menu_cards.ftl">
	</div>
	<div class="contentRight">
		<div class="katong">
			<div class="fangz">${substring(cards.name, 50, "...")}</div>
			<div class="red">注：请一定正确选择卡面值提交,否则造成损失商户自行承担； </div>
			<div class="hei">卡信息提交成功后，可在<a href="#">订单查询</a>页面查询支付结果。处理结果以订单查询页为准。</div>
			<div class="tab">
			<div class="tab_menu">
			  	<ul>
					<li class="selected">单张消耗</li>
					<li style="border-right:1px solid #e1e2e2;">批量消耗</li>
			  	</ul>
				</div>
				<div class="tab_box"> 
	 			  <div class="tab_box_1">
	 			  	<div class="memberCenter">
					<form id="sForm" autocomplete="off">
					<table class="tabTable">
						<tr class="tongdaoS">
							<th>支付通道:</th>
							<td>
								<#list paymentDiscountList as paymentDiscount>
									<input type="radio" name="paymentConfig.id" value="${paymentDiscount.paymentConfig.id}" face="${paymentDiscount.face}" <#if paymentDiscount.paymentConfig.isDefault >checked="checked"</#if> />&nbsp;${paymentDiscount.paymentConfig.name}(<span style="color:red">折扣率：${paymentDiscount.discount}</span>)
								</#list>
							</td>
						</tr>
						<#if cards.isSpecificationEnabled>
							<#assign specificationValueSet = cards.specificationValueSet>
							<#list cards.specificationSet as specification>
									<tr class="text1">
										<th>${specification.name}:</th>
										<td>
											<ul id="buyInfo">
												<#list cards.productSet as product>
													<input type="radio" name="productId" value="${product.id}" price="${product.price}" <#if product.isDefault >checked="checked"</#if> />&nbsp;${product.price}元
												</#list>
											</ul>
										</td>
									</tr>
							</#list>
						</#if>
						<tr>
							<th>确认金额:</th>
							<td>
								<span id="price1" class="red">￥${cards.price} 元</span>
							</td>
						</tr>
						<tr>
							<th>卡号:</th>
							<td>
								<input type="text" name="cardNum"  class="formText"/>
							</td>
						</tr>
						<tr>
							<th>密码:</th>
							<td>
								<input type="text" name="cardPwd"  class="formText"/>
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<input type="button" id="sBtn" class="formButton" value="生成订单" />
							</td>
						</tr>
					</table>
				</form>
				</div>
				</div>
		 			<div class="hide">
				 	<div class="memberCenter">
					<form id="pForm" autocomplete="off">
					<table class="tabTable">
						<tr class="tongdaoP">
							<th>支付通道:</th>
							<td>
								<#list paymentDiscountList as paymentDiscount>
									<input type="radio" name="paymentConfig.id" value="${paymentDiscount.paymentConfig.id}" face="${paymentDiscount.face}" <#if paymentDiscount.paymentConfig.isDefault >checked="checked"</#if> />&nbsp;${paymentDiscount.paymentConfig.name}(<span style="color:red">折扣率：${paymentDiscount.discount}</span>)
								</#list>
							</td>
						</tr>
						<tr>
							<th>充值卡编号: </th>
							<td>
								${cards.cardsSn}
							</td>
						</tr>
						<#if cards.isSpecificationEnabled>
							<#assign specificationValueSet = cards.specificationValueSet>
							<#list cards.specificationSet as specification>
									<tr class="text2">
										<th>${specification.name}:</th>
										<td>
											<ul id="buyInfo">
												<#list cards.productSet as product>
													<input type="radio" name="productId" value="${product.id}" price="${product.price}" <#if product.isDefault >checked="checked"</#if> />&nbsp;${product.price}元
												</#list>
											</ul>
										</td>
									</tr>
							</#list>
						</#if>
						<tr>
							<th>确认金额:</th>
							<td>
								<span id="price2" class="red">￥${cards.price} 元</span>
							</td>
						</tr>
						<tr>
							<th>卡密组合:</th>
							<td>
								<p>输入格式如:xxxxx(账号)xxxxx,xxxxxx(密码)xxxx</p>
								<textarea cols="60" rows="10" name="cardString"  class="formTextarea"></textarea>
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<input type="button" id="pBtn" class="formButton" value="生成订单" />
							</td>
						</tr>
					</table>
				</form>
				</div>
		 			</div>
				</div>
			</div> 

			</div>
			</div>
		</div>
	</div>
	</div>
	<div class="clear"></div>
	<#include "/WEB-INF/template/card/member_footer.ftl">
	<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
	<script type="text/javascript" >
	//<![CDATA[
		$(function(){
		    var $div_li =$("div.tab_menu ul li");
		    $div_li.click(function(){//要点击切换这click
				$(this).addClass("selected")            //当前<li>元素高亮
					   .siblings().removeClass("selected");  //去掉其它同辈<li>元素的高亮
	            var index =  $div_li.index(this);  // 获取当前点击的<li>元素 在 全部li元素中的索引。
				$("div.tab_box > div")   	//选取子节点。不选取子节点的话，会引起错误。如果里面还有div 
						.eq(index).show()   //显示 <li>元素对应的<div>元素
						.siblings().hide(); //隐藏其它几个同辈的<div>元素
			}).hover(function(){
				$(this).addClass("hover");
			},function(){
				$(this).removeClass("hover");
			})
		})
	//]]>
	</script>
	<script type="text/javascript">
		$().ready(function() {
			
	        $(".text1 input[type='radio']").click(function(){     //绑定radio绑定事件                
	             $("#price1").text("￥  "+$(this).attr("price")+" 元");
		    });
			$(".text2 input[type='radio']").click(function(){     //绑定radio绑定事件                
	             $("#price2").text("￥  "+$(this).attr("price")+" 元");
		    });
		    var str = $(".tongdaoS input[type='radio']:checked").attr("face");
		    var strs = str.split(","); //字符分割
		    $(".text1 input[type='radio']").each(function(){
				$(this).attr("disabled","disabled");
			});
			$(".text1 input[type='radio']").each(function(){
		        for (i=0;i<strs.length ;i++ ){
					if(strs[i]!="0"){
					   	if($(this).attr("price")==strs[i]){ 
					    	$(this).removeAttr("disabled");
					    }
					}else{
						$(this).removeAttr("disabled");
					}
				}
			});
			
		    var str = $(".tongdaoP input[type='radio']:checked").attr("face");
		    var strs = str.split(","); //字符分割
		    $(".text2 input[type='radio']").each(function(){
				$(this).attr("disabled","disabled");
			});
			$(".text2 input[type='radio']").each(function(){
		        for (i=0;i<strs.length ;i++ ){
					if(strs[i]!="0"){
					   	if($(this).attr("price")==strs[i]){ 
					    	$(this).removeAttr("disabled");
					    }
					}else{
						$(this).removeAttr("disabled");
					}
				}
			});
			$(".tongdaoS input[type='radio']").click(function(){     //绑定radio绑定事件                
				$(".text1 input[type='radio']").each(function(){
					$(this).attr("disabled","disabled");
				});
	            var str = $(this).attr("face");
	            var strs = str.split(","); //字符分割
				$(".text1 input[type='radio']").each(function(){
		            for (i=0;i<strs.length ;i++ ){
						if(strs[i]!="0"){
						   	if($(this).attr("price")==strs[i]){ 
						    	$(this).removeAttr("disabled");
						    }
						}else{
							$(this).removeAttr("disabled");
						}
					}
				});
		    });
		    $(".tongdaoP input[type='radio']").click(function(){     //绑定radio绑定事件                
				$(".text2 input[type='radio']").each(function(){
					$(this).attr("disabled","disabled");
				});
	            var str = $(this).attr("face");
	            var strs = str.split(","); //字符分割
				$(".text2 input[type='radio']").each(function(){
		            for (i=0;i<strs.length ;i++ ){
						if(strs[i]!="0"){
						   	if($(this).attr("price")==strs[i]){ 
						    	$(this).removeAttr("disabled");
						    }
						}else{
							$(this).removeAttr("disabled");
						}
					}
				});
		    });
			var $sForm = $("#sForm");
			var $pForm = $("#pForm");
			var $sBtn = $("#sBtn");
			var $pBtn = $("#pBtn");
			$sBtn.click( function() {
				$.ajax({
					url: "order!save.action",
					data: $sForm.serialize(),
					type: "POST",
					dataType: "json",
					cache: false,
					beforeSend: function(data) {
						//$.dialog({type: 'warn', content: '<span class="loadingIcon">&nbsp;</span>提交中...', modal: true, autoCloseTime: 1000});
						$sBtn.attr("disabled", true);
					},
					success: function(data) {
						if (data.status == "success") {
							$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
							$("input[type='text'],textarea").val("");
						} else {
							$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
						}
							$sBtn.attr("disabled", false);
					}
				});
				return false;
			});
			$pBtn.click( function() {
				$.ajax({
					url: "order!batch.action",
					data: $pForm.serialize(),
					type: "POST",
					dataType: "json",
					cache: false,
					beforeSend: function(data) {
						//$.dialog({type: 'warn', content: '<span class="loadingIcon">&nbsp;</span>提交中...', modal: true, autoCloseTime: 1000});
						$pBtn.attr("disabled", true);
					},
					success: function(data) {
						if (data.status == "success") {
							$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
							$("input[type='text'],textarea").val("");
						} else {
							$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
						}
						$pBtn.attr("disabled", false);
					}
				});
				return false;
			});
		})
	</script>
</body>
</html>