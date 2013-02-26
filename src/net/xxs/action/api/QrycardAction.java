package net.xxs.action.api;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 前台Action类 - api远程卖卡接口
 */

@ParentPackage("api")
@InterceptorRefs({
	@InterceptorRef(value = "apiStack")
})
public class QrycardAction extends BaseApiAction {

	
	private static final long serialVersionUID = 4779672839882044187L;

	public String execute() throws Exception {
		System.out.println("执行了查询操作");
		return null;
	}

}