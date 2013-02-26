package net.xxs.action.api;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.xxs.bean.Setting;
import net.xxs.service.MemberService;
import net.xxs.util.SettingUtil;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 前台Action类 - API基类
 */

public class BaseApiAction extends ActionSupport {

	private static final long serialVersionUID = -8390109819383933918L;
	
	private static final String HEADER_ENCODING = "UTF-8";
	private static final boolean HEADER_NO_CACHE = true;
	
	public static final String REDIRECT = "redirect";
	
	public static final String STATUS_PARAMETER_NAME = "status";// 操作状态参数名称
	public static final String MESSAGE_PARAMETER_NAME = "message";// 操作消息参数名称
	
	protected String redirectUrl;// 跳转URL
	
	
	protected String p0_cmd;				//业务类型
	protected String p1_order;				//商户订单号
	protected String p2_amt;				//订单金额
	protected String p3_pname;				//产品名称
	protected String p4_pnote;				//产品描述
	protected String p5_pcat;				//产品类型
	protected String p6_url;				//异步通知地址
	protected String p7_face;				//面额组
	protected String p8_cardno;				//卡号组
	protected String p9_cardpwd;			//密码组
	protected String p10_cardcode;			//卡类代码
	protected String p10_membercode;		//商户ID（平台发放）
	protected String p11_memberkey;			//商户密匙（平台发放）
	protected String p12_isneedresponse;	//是否需要通知
	protected String p12_datetime;			//时间戳
	protected String p12_format;			//格式
	protected String p12_version;			//版本
	protected String r1_billno;				//支付流水号
	protected String r2_recode;				//返回状态码
	protected String r2_reinfo;				//返回信息
	protected String r3_accountface;		//成交金额
	protected String sign;					//签名
	
	@Resource(name = "memberServiceImpl")
	protected MemberService memberService;
	
	// 获取系统配置信息
	public Setting getSetting() {
		return SettingUtil.getSetting();
	}
	
	// 获取Request
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	// 获取Response
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	// 获取ServletContext
	protected ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	// 获取Attribute
	protected Object getAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}

	// 设置Attribute
	protected void setAttribute(String name, Object value) {
		ServletActionContext.getRequest().setAttribute(name, value);
	}

	// 获取Parameter
	protected String getParameter(String name) {
		return ServletActionContext.getRequest().getParameter(name);
	}

	// 获取Parameter数组
	protected String[] getParameterValues(String name) {
		return ServletActionContext.getRequest().getParameterValues(name);
	}

	// 获取Session
	protected Object getSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session.get(name);
	}
	
	// 设置Session
	protected void setSession(String name, Object value) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put(name, value);
	}

	// 移除Session
	protected void removeSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.remove(name);
	}
	
	// 获取Cookie
	protected String getCookie(String name) {
		Cookie cookies[] = ServletActionContext.getRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	// 设置Cookie
	protected void setCookie(String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(ServletActionContext.getRequest().getContextPath() + "/");
 		ServletActionContext.getResponse().addCookie(cookie);
	}
	
	// 设置Cookie
	protected void setCookie(String name, String value, Integer maxAge) {
		Cookie cookie = new Cookie(name, value);
		if (maxAge != null) {
			cookie.setMaxAge(maxAge);
		}
		cookie.setPath(ServletActionContext.getRequest().getContextPath() + "/");
 		ServletActionContext.getResponse().addCookie(cookie);
	}

	// 移除Cookie
	protected void removeCookie(String name) {
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath(ServletActionContext.getRequest().getContextPath() + "/");
		ServletActionContext.getResponse().addCookie(cookie);
	}

	// 获取真实路径
	protected String getRealPath(String path) {
		return ServletActionContext.getServletContext().getRealPath(path);
	}
	
	// 获取ContextPath
	protected String getContextPath() {
		return ServletActionContext.getRequest().getContextPath();
	}

	// AJAX输出
	protected String ajax(String content, String contentType) {
		try {
			HttpServletResponse response = initResponse(contentType);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	private HttpServletResponse initResponse(String contentType) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType(contentType + ";charset=" + HEADER_ENCODING);
		String pragma = getP(); String value = getV();
		if (HEADER_NO_CACHE) {
			response.addHeader( pragma, value);
			response.setDateHeader("Expires", 1L);
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		}
		return response;
	}
	
	private String getP() {
		return new StringBuffer("yB-derewoP").reverse().toString();
	}
	
	private String getV() {
		return new StringBuffer("+").append("+").append("POHS").reverse().toString();
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getP0_cmd() {
		return p0_cmd;
	}

	public void setP0_cmd(String p0_cmd) {
		this.p0_cmd = p0_cmd;
	}

	public String getP1_order() {
		return p1_order;
	}

	public void setP1_order(String p1_order) {
		this.p1_order = p1_order;
	}

	public String getP2_amt() {
		return p2_amt;
	}

	public void setP2_amt(String p2_amt) {
		this.p2_amt = p2_amt;
	}

	public String getP3_pname() {
		return p3_pname;
	}

	public void setP3_pname(String p3_pname) {
		this.p3_pname = p3_pname;
	}

	public String getP4_pnote() {
		return p4_pnote;
	}

	public void setP4_pnote(String p4_pnote) {
		this.p4_pnote = p4_pnote;
	}

	public String getP5_pcat() {
		return p5_pcat;
	}

	public void setP5_pcat(String p5_pcat) {
		this.p5_pcat = p5_pcat;
	}

	public String getP6_url() {
		return p6_url;
	}

	public void setP6_url(String p6_url) {
		this.p6_url = p6_url;
	}

	public String getP7_face() {
		return p7_face;
	}

	public void setP7_face(String p7_face) {
		this.p7_face = p7_face;
	}

	public String getP8_cardno() {
		return p8_cardno;
	}

	public void setP8_cardno(String p8_cardno) {
		this.p8_cardno = p8_cardno;
	}

	public String getP9_cardpwd() {
		return p9_cardpwd;
	}

	public void setP9_cardpwd(String p9_cardpwd) {
		this.p9_cardpwd = p9_cardpwd;
	}

	public String getP10_cardcode() {
		return p10_cardcode;
	}

	public void setP10_cardcode(String p10_cardcode) {
		this.p10_cardcode = p10_cardcode;
	}

	public String getP10_membercode() {
		return p10_membercode;
	}

	public void setP10_membercode(String p10_membercode) {
		this.p10_membercode = p10_membercode;
	}

	public String getP11_memberkey() {
		return p11_memberkey;
	}

	public void setP11_memberkey(String p11_memberkey) {
		this.p11_memberkey = p11_memberkey;
	}

	public String getP12_isneedresponse() {
		return p12_isneedresponse;
	}

	public void setP12_isneedresponse(String p12_isneedresponse) {
		this.p12_isneedresponse = p12_isneedresponse;
	}

	public String getP12_datetime() {
		return p12_datetime;
	}

	public void setP12_datetime(String p12_datetime) {
		this.p12_datetime = p12_datetime;
	}

	public String getP12_format() {
		return p12_format;
	}

	public void setP12_format(String p12_format) {
		this.p12_format = p12_format;
	}

	public String getP12_version() {
		return p12_version;
	}

	public void setP12_version(String p12_version) {
		this.p12_version = p12_version;
	}

	public String getR1_billno() {
		return r1_billno;
	}

	public void setR1_billno(String r1_billno) {
		this.r1_billno = r1_billno;
	}

	public String getR2_recode() {
		return r2_recode;
	}

	public void setR2_recode(String r2_recode) {
		this.r2_recode = r2_recode;
	}

	public String getR2_reinfo() {
		return r2_reinfo;
	}

	public void setR2_reinfo(String r2_reinfo) {
		this.r2_reinfo = r2_reinfo;
	}

	public String getR3_accountface() {
		return r3_accountface;
	}

	public void setR3_accountface(String r3_accountface) {
		this.r3_accountface = r3_accountface;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
	
}