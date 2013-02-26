package net.xxs.interceptor;

import java.util.Map;
import java.util.regex.Pattern;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 拦截器 - 安全
 */

public class SecurityInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -374803404527649448L;
	
	private static Pattern scriptPattern = Pattern.compile("script", Pattern.CASE_INSENSITIVE);

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Map<String, Object> parameters = actionInvocation.getInvocationContext().getParameters();
		for (String key : parameters.keySet()) {
			Object value = parameters.get(key);
			if (value instanceof String[]) {
				String[] values = (String[]) value;
				for (int i = 0; i < values.length; i++) {
					values[i] = scriptPattern.matcher(values[i]).replaceAll("&#x73;cript");
					values[i] = values[i].replaceAll("<", "&lt;");
					values[i] = values[i].replaceAll(">", "&gt;");
				}
				parameters.put(key, values);
			}
		}
		return actionInvocation.invoke();
	}

}