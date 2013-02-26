<script type="text/javascript">
$().ready( function() {
	
	var $pager = $("#pager");
	
	$pager.pager({
		pagenumber: ${pager.pageNumber},
		pagecount: ${pager.pageCount},
		buttonClickCallback: $.gotoPage
	});

})
</script>
<span id="pager"></span>
<input type="hidden" name="pager.pageNumber" id="pageNumber" value="${pager.pageNumber}" />
<input type="hidden" name="pager.orderBy" id="orderBy" value="${pager.orderBy}" />
<input type="hidden" name="pager.order" id="order" value="${pager.order}" />