package net.xxs.action.api;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 前台Action类 - api远程查询接口
 */

@ParentPackage("api")
@InterceptorRefs({
	@InterceptorRef(value = "apiStack")
})
public class RegcardAction extends BaseApiAction {

	private static final long serialVersionUID = -3361881210496442774L;

	public String execute() throws Exception {
		System.out.println("执行了销卡操作");
		//生成订单
		//生成支付单
		//执行支付
		
		return null;
	}
}