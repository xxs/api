<@compress single_line = true>
	<#if isFileAction>
		{"error": 1, "message": 
		"<#if fieldErrors??>
			<#list fieldErrors.keySet() as fieldErrorKey>
				<#list fieldErrors[fieldErrorKey] as fieldErrorValue>
					${fieldErrorValue} 
				</#list>	
			</#list>
		</#if>"}
	<#else>
		{"status": "error", "message": 
		"<#if fieldErrors??>
			<#list fieldErrors.keySet() as fieldErrorKey>
				<#list fieldErrors[fieldErrorKey] as fieldErrorValue>
					${fieldErrorValue} 
				</#list>	
			</#list>
		</#if>"}
	</#if>
</@compress>