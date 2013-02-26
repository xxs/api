<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单列表<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<#include "/WEB-INF/template/card/member_head.ftl">
<script type="text/javascript">
$().ready( function() {
			var $queryForm = $("#queryForm");
			var $queryBtn = $("#queryBtn");
			$queryBtn.click( function() {
				$.ajax({
					url: "query!query.action",
					data: $queryForm.serialize(),
					type: "POST",
					dataType: "json",
					cache: false,
					beforeSend: function(data) {
						$queryBtn.attr("disabled", true);
					},
					success: function(data) {
						if (data.status == "success") {
							$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
						} else {
							$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
						}
						$queryBtn.attr("disabled", false);
					}
				});
				return false;
			});
		var $refForm = $("#refForm");
		var $refBtn = $("#refBtn");
		var $status = $(".state");
		var $recode = $(".recode");
	
		$refBtn.click( function() {
			$.ajax({
				url: "order!Lquery.action",
				data: $refForm.serialize(),
				type: "POST",
				dataType: "json",
				cache: false,
				beforeSend: function(data) {
					$recode.html('<span class="loadingIcon">&nbsp;</span>刷新中');
					//$status.html('<span class="loadingIcon">&nbsp;</span>刷新中');
					$refBtn.attr("disabled", true);
				},
				success: function(data) {
					if (data.status == "success") {
						var object = eval("("+data.message+")");
						$.each(object,function(index,item){
					      // alert(item.id+""+index);
					       //$("#ss"+item.id).text(item.orderStatus);
					       $("#rr"+item.id).text(item.retCode);
					    })
						//$status.text(data.message);
						//$.dialog({type: data.status, content: '刷新成功', modal: true, autoCloseTime: 3000});
					} else {
						$status.text("刷新失败");
						$recode.text("刷新失败");
						$.dialog({type: data.status, content: '刷新超时', modal: true, autoCloseTime: 3000});
					}
					$refBtn.attr("disabled", false);
				}
			});
			return false;
		});		
});
</script>
</head>
<body class="memberCenter">
	<#include "/WEB-INF/template/card/member_header.ftl">
	<div class="content">
	<div class="contentLeft" style="min-height:320px;">
		<#include "/WEB-INF/template/card/menu_order.ftl">
	</div>
	<div class="contentRight">
		<div class="katong">
			<div class="fangz">订单列表</div>
			<div class="hei">
				<div class="memberCenter">
				<form action="order!search.action" method="post">
				<table class="stateTable">
					<tr>
						<th>订单编号</th>
						<td>
							<input type="text" name="order.orderSn" value="<#if order??>${order.orderSn!""}</#if>"/>
						</td>	
						<th>充值卡卡号</th>
						<td>
							<input type="text" name="order.cardNum" value="<#if order??>${order.cardNum!""}</#if>"/>
						</td>
					</tr>
					<tr>
						<th>充值卡品牌</th>
						<td>
							<select name="order.brandId">
								<option value="">请选择...</option>
								<#list allBrandList as brand>
									<option value="${brand.id}" <#if order??><#if brand.id == order.brandId> selected</#if></#if>>
										${brand.name}
									</option>
								</#list>
							</select>
						</td>	
						<th>单据通道</th>
						<td>
							<select name="order.paymentConfig.id">
								<option value="">请选择...</option>
								<#list allPaymentConfigList as paymentConfig>
									<option value="${paymentConfig.id}" <#if order??><#if paymentConfig == order.paymentConfig> selected</#if></#if>>
										${paymentConfig.name}
									</option>
								</#list>
							</select>
						</td>
					</tr>
					<tr>
						<th>单据状态</th>
						<td>
							<select name="order.orderStatus">
								<option value="">请选择...</option>
								<#list orderStatusList as orderStatus> 
									<option value="${orderStatus}" <#if order??><#if order.orderStatus == orderStatus>selected="selected"</#if></#if> >
										${action.getText("OrderStatus." + orderStatus)}
									</option>
								</#list>
							</select>
						</td>	
						<th>时间范围</th>
						<td >
							<input name="beginDate" onclick="WdatePicker()" value="<#if beginDate??>${beginDate}</#if>" />
							~
							<input name="endDate" onclick="WdatePicker()" value="<#if endDate??>${endDate}</#if>" />
						</td>
					</tr>	
					<tr>
						<td>
						</td>	
						<td colspan="3" align="center">
							<input type="submit" class="formButton" value="查询" />	
							<a href="#">今日订单</a>
							<a href="#">本周订单</a>
							<a href="#">本月订单</a>
							<a href="#">季度订单</a>
						</td>
					</tr>	
				</table>
				</form>
				<form id="queryForm" >
				<table class="stateTable">
					<tr>
						<th>开始时间：</th>
						<td >
							<input name="beginDate" onclick="WdatePicker()" value="<#if beginDate??>${beginDate}</#if>" />
						</td>
						<th>结束时间：</th>
						<td >
							<input name="endDate" onclick="WdatePicker()" value="<#if endDate??>${endDate}</#if>" />
						</td>
					</tr>
					<tr>
						<th>充值卡品牌</th>
						<td>
							<select name="order.brandId">
								<option value="">请选择...</option>
								<#list allBrandList as brand>
									<option value="${brand.id}" <#if order??><#if brand.id == order.brandId> selected</#if></#if>>
										${brand.name}
									</option>
								</#list>
							</select>
						</td>	
						<th>单据通道</th>
						<td>
							<select name="order.paymentConfig.id">
								<option value="">请选择...</option>
								<#list allPaymentConfigList as paymentConfig>
									<option value="${paymentConfig.id}" <#if order??><#if paymentConfig == order.paymentConfig> selected</#if></#if>>
										${paymentConfig.name}
									</option>
								</#list>
							</select>
						</td>
					</tr>
					<tr>
						<th>单据状态</th>
						<td>
							<select name="order.orderStatus">
								<option value="">请选择...</option>
								<#list orderStatusList as orderStatus> 
									<option value="${orderStatus}" <#if order??><#if order.orderStatus == orderStatus>selected="selected"</#if></#if> >
										${action.getText("OrderStatus." + orderStatus)}
									</option>
								</#list>
							</select>
						</td>	
					</tr>	
					<tr>
						<td>
						</td>	
						<td colspan="3" align="center">
							<input type="button" id="queryBtn" class="formButton" value="查询" />	
						</td>
					</tr>	
				</table>
				</form>
				</div>
			</div>
		</div>
	</div>
	<div class="memberCenter">
		<form id="refForm" >
			<table class="listTable tabContent">
				<tr>
							<th>订单编号</th>
							<th>充值卡名称</th>
							<th>下单时间</th>
							<th>单据通道</th>
							<th>充值卡卡号</th>
							<th>订单金额</th>
							<th>订单状态</th>
							<th><input class="formButton red" id="refBtn" type="button" value="刷新订单"/></th>
						</tr>
						<#list pager.result as order>
							<tr>
								<td>
									${order.orderSn}
								</td>
								<td width="250">
										${order.productName}
								</td>
								<td width="200">
									<span title="${order.createDate?string("yyyy-MM-dd HH:mm:ss")}">${order.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
								</td>
								<td>
									${order.paymentConfig.name}
								</td>
								<td>
									${order.cardNum}
								</td>
								<td>
									${order.amount?string(currencyFormat)}
								</td>
								<td>
									<span class="state" id="ss${order.id}">
									<#if order.orderStatus == "paid">
										<span class="green">${action.getText("OrderStatus." + order.orderStatus)}</span>
									<#else>
										<span class="red"> ${action.getText("OrderStatus." + order.orderStatus)}</span>
									</#if>
									</span>
								</td>
								<td  width="250">
									<input type="hidden" name="ids" value="${order.id}"/>
									<span class="recode" id="rr${order.id}" title="${order.retCode}">
										${resultText(order.paymentConfig.paymentProductId,order.retCode)}
									</span>
								</td>
							</tr>
						</#list>	
				</table>
				</form>
				<@pagination pager=pager baseUrl="/card/order!list.action">
         			<#include "/WEB-INF/template/card/pager.ftl">
         		</@pagination>
			</div>
	</div>
</div>
<div class="clear"></div>
	<#include "/WEB-INF/template/card/member_footer.ftl">
</body>
</html>