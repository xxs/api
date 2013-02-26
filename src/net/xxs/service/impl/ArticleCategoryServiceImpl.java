package net.xxs.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.xxs.dao.ArticleCategoryDao;
import net.xxs.entity.Article;
import net.xxs.entity.ArticleCategory;
import net.xxs.service.ArticleCategoryService;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 文章分类
 */

@Service("articleCategoryServiceImpl")
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategory, String> implements ArticleCategoryService {

	@Resource(name = "articleCategoryDaoImpl")
	private ArticleCategoryDao articleCategoryDao;
	
	@Resource(name = "articleCategoryDaoImpl")
	public void setBaseDao(ArticleCategoryDao articleCategoryDao) {
		super.setBaseDao(articleCategoryDao);
	}
	
	@Transactional(readOnly = true)
	public boolean isExistBySign(String sign) {
		return articleCategoryDao.isExistBySign(sign);
	}
	
	@Transactional(readOnly = true)
	public boolean isUniqueBySign(String oldSign, String newSign) {
		if (StringUtils.equalsIgnoreCase(oldSign, newSign)) {
			return true;
		} else {
			if (articleCategoryDao.isExistBySign(newSign)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	@Transactional(readOnly = true)
	public ArticleCategory getArticleCategoryBySign(String sign) {
		return articleCategoryDao.getArticleCategoryBySign(sign);
	}
	
	@Cacheable(modelId = "articleCategoryCaching")
	@Transactional(readOnly = true)
	public List<ArticleCategory> getArticleCategoryTree() {
		return articleCategoryDao.getArticleCategoryTree();
	}
	
	@Cacheable(modelId = "articleCategoryCaching")
	public List<ArticleCategory> getArticleCategoryTreeList() {
		List<ArticleCategory> allArticleCategoryList = this.getAllList();
		return recursivArticleCategoryTreeList(allArticleCategoryList, null, null);
	}
	
	// 递归父类排序分类树
	private List<ArticleCategory> recursivArticleCategoryTreeList(List<ArticleCategory> allArticleCategoryList, ArticleCategory p, List<ArticleCategory> temp) {
		if (temp == null) {
			temp = new ArrayList<ArticleCategory>();
		}
		for (ArticleCategory articleCategory : allArticleCategoryList) {
			ArticleCategory parent = articleCategory.getParent();
			if ((p == null && parent == null) || (articleCategory != null && parent == p)) {
				temp.add(articleCategory);
				if (articleCategory.getChildren() != null && articleCategory.getChildren().size() > 0) {
					recursivArticleCategoryTreeList(allArticleCategoryList, articleCategory, temp);
				}
			}
		}
		return temp;
	}
	
	@Cacheable(modelId = "articleCategoryCaching")
	@Transactional(readOnly = true)
	public List<ArticleCategory> getRootArticleCategoryList(Integer maxResults) {
		List<ArticleCategory> rootArticleCategoryList = articleCategoryDao.getRootArticleCategoryList(maxResults);
		if (rootArticleCategoryList != null) {
			for (ArticleCategory rootArticleCategory : rootArticleCategoryList) {
				if (!Hibernate.isInitialized(rootArticleCategory)) {
					Hibernate.initialize(rootArticleCategory);
				}
			}
		}
		return rootArticleCategoryList;
	}
	
	@Cacheable(modelId = "articleCategoryCaching")
	@Transactional(readOnly = true)
	public List<ArticleCategory> getParentArticleCategoryList(ArticleCategory articleCategory, boolean isContainParent, Integer maxResults) {
		List<ArticleCategory> parentArticleCategoryList = articleCategoryDao.getParentArticleCategoryList(articleCategory, isContainParent, maxResults);
		if (parentArticleCategoryList != null) {
			for (ArticleCategory parentArticleCategory : parentArticleCategoryList) {
				if (!Hibernate.isInitialized(parentArticleCategory)) {
					Hibernate.initialize(parentArticleCategory);
				}
			}
		}
		return parentArticleCategoryList;
	}
	
	public List<ArticleCategory> getParentArticleCategoryList(Article article, boolean isContainParent, Integer maxResults) {
		ArticleCategory articleCategory = article.getArticleCategory();
		List<ArticleCategory> parentArticleCategoryList = new ArrayList<ArticleCategory>();
		List<ArticleCategory> articleCategoryList = this.getParentArticleCategoryList(articleCategory, isContainParent, maxResults);
		if (articleCategoryList != null) {
			parentArticleCategoryList.addAll(articleCategoryList);
		}
		parentArticleCategoryList.add(articleCategory);
		return parentArticleCategoryList;
	}
	
	@Cacheable(modelId = "articleCategoryCaching")
	@Transactional(readOnly = true)
	public List<ArticleCategory> getChildrenArticleCategoryList(ArticleCategory articleCategory, boolean isContainChildren, Integer maxResults) {
		List<ArticleCategory> childrenArticleCategoryList = articleCategoryDao.getChildrenArticleCategoryList(articleCategory, isContainChildren, maxResults);
		if (childrenArticleCategoryList != null) {
			for (ArticleCategory childrenArticleCategory : childrenArticleCategoryList) {
				if (!Hibernate.isInitialized(childrenArticleCategory)) {
					Hibernate.initialize(childrenArticleCategory);
				}
			}
		}
		return childrenArticleCategoryList;
	}
	
	@Cacheable(modelId = "articleCategoryCaching")
	public List<ArticleCategory> getArticleCategoryPathList(ArticleCategory articleCategory) {
		List<ArticleCategory> articleCategoryPathList = new ArrayList<ArticleCategory>();
		List<ArticleCategory> parentArticleCategoryList = this.getParentArticleCategoryList(articleCategory, true, null);
		if (parentArticleCategoryList != null) {
			articleCategoryPathList.addAll(parentArticleCategoryList);
		}
		articleCategoryPathList.add(articleCategory);
		return articleCategoryPathList;
	}
	
	@Override
	@CacheFlush(modelId = "articleCategoryFlushing")
	public void delete(ArticleCategory articleCategory) {
		articleCategoryDao.delete(articleCategory);
	}

	@Override
	@CacheFlush(modelId = "articleCategoryFlushing")
	public void delete(String id) {
		articleCategoryDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "articleCategoryFlushing")
	public void delete(String[] ids) {
		articleCategoryDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "articleCategoryFlushing")
	public String save(ArticleCategory articleCategory) {
		return articleCategoryDao.save(articleCategory);
	}

	@Override
	@CacheFlush(modelId = "articleCategoryFlushing")
	public void update(ArticleCategory articleCategory) {
		articleCategoryDao.update(articleCategory);
	}

}