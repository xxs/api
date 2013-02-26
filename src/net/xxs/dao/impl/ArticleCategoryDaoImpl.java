package net.xxs.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.xxs.dao.ArticleCategoryDao;
import net.xxs.entity.ArticleCategory;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 文章分类
 */

@Repository("articleCategoryDaoImpl")
public class ArticleCategoryDaoImpl extends BaseDaoImpl<ArticleCategory, String> implements ArticleCategoryDao {
	
	public boolean isExistBySign(String sign) {
		String hql = "from ArticleCategory as articleCategory where lower(articleCategory.sign) = lower(:sign)";
		ArticleCategory articleCategory = (ArticleCategory) getSession().createQuery(hql).setParameter("sign", sign).uniqueResult();
		if (articleCategory != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArticleCategory getArticleCategoryBySign(String sign) {
		String hql = "from ArticleCategory as articleCategory where lower(articleCategory.sign) = lower(:sign)";
		ArticleCategory articleCategory = (ArticleCategory) getSession().createQuery(hql).setParameter("sign", sign).uniqueResult();
		return articleCategory;
	}
	
	@SuppressWarnings("unchecked")
	public List<ArticleCategory> getArticleCategoryTree() {
		String hql = "from ArticleCategory as articleCategory where articleCategory.parent is null order by articleCategory.orderList asc";
		List<ArticleCategory> articleCategoryTreeList = getSession().createQuery(hql).list();
		initializeArticleCategoryList(articleCategoryTreeList);
		return articleCategoryTreeList;
	}
	
	// 递归实例化充值卡分类对象
	private void initializeArticleCategoryList(List<ArticleCategory> articleCategoryList) {
		for (ArticleCategory articleCategory : articleCategoryList) {
			Set<ArticleCategory> children = articleCategory.getChildren();
			if (children != null && children.size() > 0) {
				if (!Hibernate.isInitialized(children)) {
					Hibernate.initialize(children);
				}
				initializeArticleCategoryList(new ArrayList<ArticleCategory>(children));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ArticleCategory> getRootArticleCategoryList(Integer maxResults) {
		String hql = "from ArticleCategory as articleCategory where articleCategory.parent is null order by articleCategory.orderList asc";
		Query query = getSession().createQuery(hql);
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ArticleCategory> getParentArticleCategoryList(ArticleCategory articleCategory, boolean isContainParent, Integer maxResults) {
		Query query = null;
		if (articleCategory != null) {
			if (isContainParent) {
				if (articleCategory.getParent() == null) {
					return null;
				}
				String parentPath = StringUtils.substringBeforeLast(articleCategory.getPath(), ArticleCategory.PATH_SEPARATOR);
				String[] ids = parentPath.split(ArticleCategory.PATH_SEPARATOR);
				String hql = "from ArticleCategory as articleCategory where articleCategory.id in(:ids) order by articleCategory.grade asc, articleCategory.orderList asc";
				query = getSession().createQuery(hql);
				query.setParameterList("ids", ids);
			} else {
				List<ArticleCategory> parentArticleCategoryList = null;
				ArticleCategory parent = articleCategory.getParent();
				if (maxResults > 0 && parent != null) {
					parentArticleCategoryList = new ArrayList<ArticleCategory>();
					parentArticleCategoryList.add(parent);
				}
				return parentArticleCategoryList;
			}
		} else {
			String hql = "from ArticleCategory as articleCategory order by articleCategory.grade asc, articleCategory.orderList asc";
			query = getSession().createQuery(hql);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ArticleCategory> getChildrenArticleCategoryList(ArticleCategory articleCategory, boolean isContainChildren, Integer maxResults) {
		Query query = null;
		if (articleCategory != null) {
			if (isContainChildren) {
				String hql = "from ArticleCategory as articleCategory where articleCategory.path like :path order by articleCategory.grade asc, articleCategory.orderList asc";
				query = getSession().createQuery(hql);
				query.setParameter("path", articleCategory.getPath() + ArticleCategory.PATH_SEPARATOR + "%");
			} else {
				String hql = "from ArticleCategory as articleCategory where articleCategory.parent = :articleCategory order by articleCategory.grade asc, articleCategory.orderList asc";
				query = getSession().createQuery(hql);
				query.setParameter("articleCategory", articleCategory);
			}
		} else {
			String hql = "from ArticleCategory as articleCategory order by articleCategory.grade asc, articleCategory.orderList asc";
			query = getSession().createQuery(hql);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
	// 自动设置path、grade
	@Override
	public String save(ArticleCategory articleCategory) {
		articleCategory.setPath(articleCategory.getName());
		articleCategory.setGrade(0);
		super.save(articleCategory);
		fillArticleCategory(articleCategory);
		super.update(articleCategory);
		return articleCategory.getId();
	}
	
	// 自动设置path、grade
	@Override
	public void update(ArticleCategory articleCategory) {
		fillArticleCategory(articleCategory);
		super.update(articleCategory);
		List<ArticleCategory> childrenArticleCategoryList = getChildrenArticleCategoryList(articleCategory, true, null);
		if (childrenArticleCategoryList != null) {
			for (int i = 0; i < childrenArticleCategoryList.size(); i ++) {
				ArticleCategory childrenCategory = childrenArticleCategoryList.get(i);
				fillArticleCategory(childrenCategory);
				super.update(childrenCategory);
				if(i % 20 == 0) {
					flush();
					clear();
				}
			}
		}
	}
	
	private void fillArticleCategory(ArticleCategory articleCategory) {
		ArticleCategory parent = articleCategory.getParent();
		if (parent == null) {
			articleCategory.setPath(articleCategory.getId());
		} else {
			articleCategory.setPath(parent.getPath() + ArticleCategory.PATH_SEPARATOR + articleCategory.getId());
		}
		articleCategory.setGrade(articleCategory.getPath().split(ArticleCategory.PATH_SEPARATOR).length - 1);
	}

}