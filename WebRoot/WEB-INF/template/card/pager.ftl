<#if (pageCount > 1)>
	<ul class="pager">
		<li class="pageInfo">
			共 ${pageCount} 页
		</li>
		
		<#if (pageNumber > 1)>
			<li class="firstPage">
				<a href="${base}${firstPageUrl}">首页</a>
			</li>
		<#else>
			<li class="firstPage">
				<span>首页</span>
			</li>
		</#if>
		
		<#if (pageNumber > 1)>
			<li class="prePage">
				<a href="${base}${prePageUrl}">上一页</a>
			</li>
		<#else>
			<li class="prePage">
				<span>上一页</span>
			</li>
		</#if>
		
		<#list (pageItem?keys)! as key>
			<#if (key_index == 0 && key?number > 1)>
				<li>...</li>
			</#if>
			<#if pageNumber != key?number>
				<li>
					<a href="${base}${pageItem[key]}">${key}</a>
				</li>
			<#else>
				<li class="currentPage">
					<span>${key}</span>
				</li>
			</#if>
			<#if (!key_has_next && key?number < pageCount)>
				<li>...</li>
			</#if>
		</#list>
	    
		<#if (pageNumber < pageCount)>
			<li class="nextPage">
				<a href="${base}${nextPageUrl}">下一页</a>
			</li>
		<#else>
			<li class="nextPage">
				<span>下一页</span>
			</li>
		</#if>
		
		<#if (pageNumber < pageCount)>
			<li class="lastPage">
				<a href="${base}${lastPageUrl}">末页</a>
			</li>
		<#else>
			<li class="lastPage">
				<span>末页</span>
			</li>
		</#if>
	</ul>
</#if>