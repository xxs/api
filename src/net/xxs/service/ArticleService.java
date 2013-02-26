package net.xxs.service;

import java.util.Date;
import java.util.List;

import net.xxs.bean.Pager;
import net.xxs.entity.Article;
import net.xxs.entity.ArticleCategory;

/**
 * Service接口 - 文章
 */

public interface ArticleService extends BaseService<Article, String> {
	
	/**
	 * 根据文章分类、文章类型、是否包含子分类文章、最大结果数获取文章集合（只包含isPublication=true的对象）
	 * 
	 * @param articleCategory
	 *            文章分类,null表示无限制
	 *            
	 * @param type
	 *            文章类型,null表示无限制
	 *            
	 * @param isContainChildren
	 *            是否包含子分类文章
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 此分类下的文章集合
	 */
	public List<Article> getArticleList(ArticleCategory articleCategory, String type, boolean isContainChildren, Integer maxResults);
	
	/**
	 * 根据文章分类、起始日期、结束日期、起始结果数、最大结果数获取文章集合（只包含isPublication=true的对象,包含子分类文章）
	 * 
	 * @param articleCategory
	 *            文章分类,null表示无限制
	 *            
	 * @param beginDate
	 *            起始日期,null表示无限制
	 *            
	 * @param endDate
	 *            结束日期,null表示无限制
	 *            
	 * @param firstResult
	 *            起始结果数,null表示无限制
	 *            
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 此分类下的所有文章集合
	 */
	public List<Article> getArticleList(ArticleCategory articleCategory, Date beginDate, Date endDate, Integer firstResult, Integer maxResults);
	
	/**
	 * 根据ArticleCategory和Pager对象,获取此分类下的文章分页对象（只包含isPublication=true的对象,包含子分类文章）
	 * 
	 * @param articleCategory
	 *            文章分类
	 *            
	 * @param pager
	 *            分页对象
	 * 
	 * @return Pager
	 */
	public Pager getArticlePager(ArticleCategory articleCategory, Pager pager);

	/**
	 * 根据分页对象搜索文章
	 * 
	 * @param pager
	 *            分页对象
	 * 
	 * @return 分页对象
	 */
	public Pager search(Pager pager);
	
	/**
	 * 根据文章ID获取并更新点击数
	 * 
	 * @param id
	 *            文章ID
	 * 
	 * @return 点击数
	 */
	public Integer viewHits(String id);
	
}