<ul>
	<@cards_list type="hot" count=100; cardsList>
		<#list cardsList as cards>
			<li>
				<a href="cards!detail.action?id=${cards.id}" title="${cards.name}">${substring(cards.name, 24, "...")}</a>
			</li>
		</#list>
	</@cards_list>
</ul>
