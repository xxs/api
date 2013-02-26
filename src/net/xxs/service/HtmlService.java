package net.xxs.service;

import java.util.Map;

import net.xxs.entity.Article;
import net.xxs.entity.Cards;

/**
 * Service接口 - 生成静态
 */

public interface HtmlService {
	
	/**
	 * 根据Freemarker模板文件路径、Map数据生成HTML
	 * 
	 * @param templatePath
	 *            Freemarker模板文件路径
	 *            
	 * @param htmlPath
	 *            生成HTML路径
	 * 
	 * @param data
	 *            Map数据
	 * 
	 */
	public void buildHtml(String templatePath, String htmlPath, Map<String, Object> data);
	
	/**
	 * 生成首页HTML
	 * 
	 */
	public void buildIndexHtml();
	/**
	 * 生成注册HTML
	 * 
	 */
	public void buildRegisterHtml();
	
	/**
	 * 生成登录HTML
	 * 
	 */
	public void buildLoginHtml();
	
	/**
	 * 生成注册协议HTML
	 * 
	 */
	public void buildRegisterAgreementHtml();
	
	/**
	 * 生成ADMIN.JS
	 * 
	 */
	public void buildAdminJs();
	
	/**
	 * 生成CARD.JS
	 * 
	 */
	public void buildCardJs();
	
	/**
	 * 根据ID生成文章内容HTML
	 * 
	 * @param id
	 *            文章ID
	 */
	public void buildArticleContentHtml(String id);
	
	/**
	 * 根据文章生成文章内容HTML
	 * 
	 * @param article
	 *            文章
	 */
	public void buildArticleContentHtml(Article article);
	
	/**
	 * 生成所有文章内容HTML
	 * 
	 */
	public void buildArticleContentHtml();
	
	/**
	 * 根据ID生成充值卡内容HTML
	 * 
	 * @param id
	 *            充值卡ID
	 */
	public void buildCardsContentHtml(String id);
	
	/**
	 * 根据充值卡生成充值卡内容HTML
	 * 
	 * @param cards
	 *            充值卡
	 */
	public void buildCardsContentHtml(Cards cards);
	
	/**
	 * 生成所有充值卡内容HTML
	 * 
	 */
	public void buildCardsContentHtml();
	
	/**
	 * 生成错误页HTML
	 */
	public void buildErrorPageHtml();
	
	/**
	 * 生成权限错误页HTML
	 */
	public void buildErrorPageAccessDeniedHtml();
	
	/**
	 * 生成错误页500 HTML
	 */
	public void buildErrorPage500Html();
	
	/**
	 * 生成错误页404 HTML
	 */
	public void buildErrorPage404Html();
	
	/**
	 * 生成错误页403 HTML
	 */
	public void buildErrorPage403Html();
	
	/**
	 * 根据HTML路径、分页数删除文章内容HTML
	 * 
	 * @param htmlPath
	 *            HTML路径
	 * 
	 * @param pageCount
	 *            分页数
	 */
	public void deleteArticleContentHtml(String htmlPath, Integer pageCount);
	
	/**
	 * 根据HTML路径删除充值卡内容HTML
	 * 
	 * @param htmlPath
	 *            HTML路径
	 */
	public void deleteCardsContentHtml(String htmlPath);
	
}