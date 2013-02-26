package net.xxs.bean;

import net.xxs.util.CommonUtil;

/**
 * Bean类 - 页面模板配置
 */

public class PageTemplateConfig {
	
	public static final String INDEX = "index";// 首页
	public static final String LOGIN = "login";// 登录
	public static final String REGISTER = "register";// 注册
	public static final String REGISTER_AGREEMENT = "registerAgreement";// 注册协议
	public static final String ADMIN_JS = "adminJs";// ADMIN.JS
	public static final String CARD_JS = "cardJs";// CARD.JS
	public static final String ARTICLE_CONTENT = "articleContent";// 文章内容
	public static final String CARDS_CONTENT = "cardsContent";// 充值卡内容
	public static final String ERROR_PAGE = "errorPage";// 错误页
	public static final String ERROR_PAGE_ACCESS_DENIED = "errorPageAccessDenied";// 权限错误页
	public static final String ERROR_PAGE_500 = "errorPage500";// 错误页500
	public static final String ERROR_PAGE_404 = "errorPage404";// 错误页404
	public static final String ERROR_PAGE_403 = "errorPage403";// 错误页403
	
	private String name;// 配置名称
	private String description;// 描述
	private String templatePath;// Freemarker模板文件路径
	private String htmlPath;// 生成HTML静态文件路径

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	// 获取生成HTML静态文件真实路径
	public String getHtmlRealPath() {
		return CommonUtil.getPreparedStatementPath(htmlPath);
	}

}