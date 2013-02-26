package net.xxs.service;


/**
 * Service接口 - 任务
 */

public interface JobService {
	
	/**
	 * 生成首页HTML
	 * 
	 */
	public void buildIndexHtml();
	
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
	 * 生成文章内容HTML
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
	 * 生成充值卡内容HTML
	 * 
	 */
	public void buildCardsContentHtml();
	
	/**
	 * 生成错误页HTML
	 * 
	 */
	public void buildErrorHtml();
	
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