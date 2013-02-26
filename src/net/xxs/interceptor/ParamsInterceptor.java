package net.xxs.interceptor;

import net.xxs.action.api.BaseApiAction;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class ParamsInterceptor extends BaseApiAction implements Interceptor {

	private static final long serialVersionUID = -7715219805989568533L;

//	private static String msg;
	
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init() {
		// TODO Auto-generated method stub

	}

	public String intercept(ActionInvocation invocation) throws Exception {
//		 ActionContext context = invocation.getInvocationContext();  
//	        HttpServletRequest request = ServletActionContext.getRequest();  
//	        request.setCharacterEncoding("UTF-8");  
//	        System.out.println("request"+request);  
//	        HttpRequest req =  createHttpRequest(request);  
//	        JSONObject json = new JSONObject();  
//	  
//	        if(!checkProperties(req)){  
//	            json.put("msg", msg);  
//	            json.put("state", false);  
//	            return a
//	        }  
//	          
//	        if(!checksign(request,req )){  
//	            json.put("msg", msg);  
//	            json.put("state", false);  
//	            return ajaxJson(json.toJSONString());  
//	        }  
	          
	        return invocation.invoke(); 
	}
    /** 
     * 验证key/sign/secret 
     * @param request 
     * @param req 
     * @return 
     */  
//    private boolean checksign(HttpServletRequest request, HttpRequest req) {  
//        String key = request.getParameter("key");  
//        String key_ad = PropertiesUtil.getPropertyValue("key");  
//        if (key == null || !key_ad.equals(key)) {  
//            msg = "key不正确";  
//            return false;  
//        }  
//        //密钥是MD5(key+随机6位数)  
//        String secret = PropertiesUtil.getPropertyValue("secret");  
//        try {  
//            request.setCharacterEncoding("utf-8");  
//        } catch (UnsupportedEncodingException e) {  
//            // TODO Auto-generated catch block  
//            e.printStackTrace();  
//        }  
//        Map map = request.getParameterMap();  
//        System.out.println("提交的签名值:" + req.getSign());  
//  
//        String sign = SignUtil.generatSign(map, secret);  
//        System.out.println("系统生成的签名值:" + sign);  
//        if (!sign.equalsIgnoreCase(req.getSign())) {  
//            msg = "签名不正确";  
//            return false;  
//        }  
//        return true;  
//    }  
  
    /** 
     * 验证请求参数非空 
     * @param req 
     * @return 
     */  
//    private boolean checkProperties(HttpRequest req) {  
//        if (req.getKey() == null || req.getKey().isEmpty()) {  
//            msg = "key不为空";  
//            return false;  
//        }  
////      if (req.getSign() == null || req.getSign().isEmpty()) {  
////          msg = "签名不为空";  
////          return false;  
////      }  
//        if (req.getIconpath() == null || req.getIconpath().isEmpty()) {  
//            msg = "软件图标不为空";  
//            return false;  
//        }  
//        if  (req.getApppath() == null || req.getApppath().isEmpty()){  
//            msg = "平台上已上传媒体路径不为空";  
//            return false;  
//        }  
//        if  (req.getType() == null || req.getType().isEmpty()){  
//            msg = "媒体类型不为空";  
//            return false;  
//        }  
//        if  (req.getCustomername() == null || req.getCustomername().isEmpty()){  
//            msg = "媒体主名称不为空";  
//            return false;  
//        }  
//        if  (req.getMedianame() == null || req.getMedianame().isEmpty()){  
//            msg = "媒体名称不为空";  
//            return false;  
//        }  
//        if  (req.getDesc() == null || req.getDesc().isEmpty()){  
//            msg = "媒体简介不为空";  
//            return false;  
//        }  
//        if  (req.getIdcode() == null || req.getIdcode().isEmpty()){  
//            msg = "软件标识码不为空";  
//            return false;  
//        }  
//        return true;  
//    }  
//    /** 
//     * 获得请求 
//     * @param request 
//     * @return 
//     */  
//    private HttpRequest createHttpRequest(HttpServletRequest request) {  
//        HttpRequest req = new HttpRequest();  
//        req.setKey(request.getParameter("key"));  
//        req.setApppath(request.getParameter("apppath"));  
//        req.setDesc(request.getParameter("desc"));  
//        req.setEmail(request.getParameter("email"));  
//        req.setIconpath(request.getParameter("iconpath"));  
//        req.setIdcode(request.getParameter("idcode"));  
////      req.setIpaddr();  //ip地址  
//        req.setCustomername(request.getParameter("customername"));  
//        req.setMedianame(request.getParameter("medianame"));  
//        req.setSign(request.getParameter("sign"));  
//        req.setType(request.getParameter("type"));  
//        return req;  
//    }  
}
