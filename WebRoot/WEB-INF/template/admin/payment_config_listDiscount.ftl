<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>支付方式列表 - XXS</title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
</head>
<body class="list">
	<div class="bar">
		支付通道列表
	</div>
	<div class="body">
		<form id="listForm" action="payment_config!listDiscount.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='payment_config!addDiscount.action?paymentConfig.id=${paymentConfig.id}'" value="新增" hidefocus />
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#"  hidefocus>支付类型</a>
					</th>
					<th>
						<a href="#"  hidefocus>品牌名称</a>
					</th>
					<th>
						<a href="#"  hidefocus>折扣率</a>
					</th>
					<th>
						<a href="#"  hidefocus>通道编码</a>
					</th>
					<th>
						<a href="#"  hidefocus>支持面额</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list paymentDiscountSet as paymentDiscount>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${paymentDiscount.id}" />
						</td>
						<td>
							${paymentDiscount.paymentConfig.name}
						</td>
						<td>
							${paymentDiscount.brand.name}
						</td>
						<td>
							${paymentDiscount.discount}
						</td>
						<td>
							${paymentDiscount.code}
						</td>
						<td>
							${paymentDiscount.face}
						</td>
						<td>
							<a href="payment_config!editDiscountDetail.action?id=${paymentDiscount.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
		</form>
	</div>
</body>
</html>
