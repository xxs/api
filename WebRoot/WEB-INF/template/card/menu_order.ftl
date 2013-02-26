<ul>
	<p>欢迎您!&nbsp;&nbsp;<span class="username">${loginMember.username}</span>&nbsp;&nbsp;[<a class="userLogout" href="member!logout.action"">退出</a>]</p>
	<p>当前账户余额: <span class="red"> ${loginMember.deposit?string(currencyFormat)}</span></p>
	<p>当前可用余额: <span class="red"> ${(loginMember.deposit-loginMember.totalWithdrawMoneyApplying)?string(currencyFormat)}</span></p>
	<p>正在处理金额: <span class="red"> ${loginMember.totalWithdrawMoneyApplying?string(currencyFormat)}</span></p>
	<p>会员提现累计: <span class="red"> ${loginMember.totalWithdrawMoneySuccess?string(currencyFormat)}</span></p>
	<p>账户状态: <#if loginMember.isAccountEnabled && !loginMember.isAccountLocked && !loginMember.isAccountExpired && !loginMember.isCredentialsExpired>
								<span class="green">正常</span>
							<#elseif !loginMember.isAccountEnabled>
								<span class="red"> 未启用 </span>
							<#elseif loginMember.isAccountLocked>
								<span class="red"> 已锁定 </span>
							</#if>
	</p>
	<p>会员等级: <span class="red"> ${loginMember.memberRank.name}</span></p>
</ul>
