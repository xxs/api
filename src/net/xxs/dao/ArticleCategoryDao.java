package net.xxs.dao;

import java.util.List;

import net.xxs.entity.ArticleCategory;

/**
 * Dao接口 - 文章分类
 */

public interface ArticleCategoryDao extends BaseDao<ArticleCategory, String> {
	
	/**
	 * 判断标识是否存在（不区分大小写）
	 * 
	 * @param sign
	 *            标识
	 * 
	 */
	public boolean isExistBySign(String sign);
	
	/**
	 * 根据标识获取文章分类
	 * 
	 * @param sign
	 *            标识
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
	 *            文章分类,null表示无限制
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
	 * 根据ArticleCategory对象获取所有子类集合,若无子类则返回null
	 * 
	 * @param articleCategory
	 *            文章分类,null表示无限制
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

}