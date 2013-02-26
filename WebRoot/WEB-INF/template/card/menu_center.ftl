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
	<div class="heng"></div>	
	<li><a href="profile!edit.action">个人信息</a></li>
	<li><a href="bank!list.action">提现账户</a></li>
	<li><a href="password!safe.action">密保问题</a></li>
	<li><a href="password!edit.action">登录密码</a></li>
	<li><a href="password!withdrawPwd.action">提现密码</a></li>
	<li><a href="promotion.action">推广链接</a></li>
</ul>
