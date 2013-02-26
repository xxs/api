	<div class="friendlink">
    	<ul>
    			<@friend_link_list type="picture" count=9; friendLinkList>
					<#list friendLinkList as friendLink>
						<li>
							<a href="${friendLink.url}" title="${friendLink.name}" width="48" height="61" target="_blank">
								<img src="${base}${friendLink.logoPath}"> <span>${friendLink.name}</span>
							</a>
						</li>
					</#list>
				</@friend_link_list>
        </ul>
    </div>
