package net.xxs.service;

import java.util.List;

import net.xxs.entity.Article;
import net.xxs.entity.ArticleCategory;

/**
 * Service接口 - 文章分类
 */

public interface ArticleCategoryService extends BaseService<ArticleCategory, String> {
	
	/**
	 * 判断标识是否存在（不区分大小写）
	 * 
	 * @param sign
	 *            标识
	 * 
	 */
	public boolean isExistBySign(String sign);
	
	/**
	 * 判断标识是否唯一（不区分大小写）
	 * 
	 * @param oldSign
	 *            旧标识
	 *            
	 * @param newSign
	 *            新标识
	 * 
	 */
	public boolean isUniqueBySign(String oldSign, String newSign);
	
	/**
	 * 根据标识获取ArticleCategory对象
	 * 
	 * @param sign
	 *            sign
	 * 
	 */
	public ArticleCategory getArticleCategoryBySign(String sign);
	
	/**
	 * 获取文章分类树集合
	 * 
	 * @return 文章分类树集合
	 * 
	 */
	public List<ArticleCategory> getArticleCategoryTree();
	
	/**
	 * 获取文章分类树集合;
	 * 
	 * @return 文章分类树集合
	 * 
	 */
	public List<ArticleCategory> getArticleCategoryTreeList();
	
	/**
	 * 获取顶级文章分类集合
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 顶级文章分类集合
	 * 
	 */
	public List<ArticleCategory> getRootArticleCategoryList(Integer maxResults);
	
	/**
	 * 根据ArticleCategory对象获取所有父类集合,若无父类则返回null
	 * 
	 * @param articleCategory
	 *            articleCategory
	 *            
	 * @param isContainParent
	 *            是否包含所有父分类
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 父类集合
	 * 
	 */
	public List<ArticleCategory> getParentArticleCategoryList(ArticleCategory articleCategory, boolean isContainParent, Integer maxResults);
	
	/**
	 * 根据Article对象获取所有父类集合,若无父类则返回null
	 * 
	 * @param article
	 *            article
	 *            
	 * @param isContainParent
	 *            是否包含所有父分类
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 父类集合
	 * 
	 */
	public List<ArticleCategory> getParentArticleCategoryList(Article article, boolean isContainParent, Integer maxResults);
	
	/**
	 * 根据ArticleCategory对象获取所有子类集合,若无子类则返回null
	 * 
	 * @param articleCategory
	 *            articleCategory
	 *            
	 * @param isContainChildren
	 *            是否包含所有子分类
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<ArticleCategory> getChildrenArticleCategoryList(ArticleCategory articleCategory, boolean isContainChildren, Integer maxResults);
	
	/**
	 * 根据ArticleCategory对象获取路径集合
	 * 
	 * @param articleCategory
	 *            articleCategory
	 * 
	 * @return ArticleCategory路径集合
	 * 
	 */
	public List<ArticleCategory> getArticleCategoryPathList(ArticleCategory articleCategory);

}