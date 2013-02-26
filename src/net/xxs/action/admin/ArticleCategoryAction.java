package net.xxs.action.admin;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.xxs.entity.Article;
import net.xxs.entity.ArticleCategory;
import net.xxs.service.ArticleCategoryService;
import net.xxs.service.CacheService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 文章分类
 */

@ParentPackage("admin")
public class ArticleCategoryAction extends BaseAdminAction {

	private static final long serialVersionUID = -7786508966240073537L;

	private String parentId;
	private ArticleCategory articleCategory;

	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	
	// 是否已存在标识 ajax验证
	public String checkSign() {
		String oldSign = getParameter("oldValue");
		String newSign = articleCategory.getSign();
		if (articleCategoryService.isUniqueBySign(oldSign, newSign)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		articleCategory = articleCategoryService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		return LIST;
	}

	// 删除
	public String delete() {
		ArticleCategory articleCategory = articleCategoryService.load(id);
		Set<ArticleCategory> childrenArticleCategoryList = articleCategory.getChildren();
		if (childrenArticleCategoryList != null && childrenArticleCategoryList.size() > 0) {
			addActionError("此文章分类存在下级分类,删除失败!");
			return ERROR;
		}
		Set<Article> articleSet = articleCategory.getArticleSet();
		if (articleSet != null && articleSet.size() > 0) {
			addActionError("此文章分类下存在文章,删除失败!");
			return ERROR;
		}
		articleCategoryService.delete(id);
		logInfo = "删除文章分类: " + articleCategory.getName();
		
		cacheService.flushArticleListPageCache(getRequest());
		
		redirectUrl = "article_category!list.action";
		return SUCCESS;
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "articleCategory.name", message = "分类名称不允许为空!"),
			@RequiredStringValidator(fieldName = "articleCategory.sign", message = "标识不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "articleCategory.orderList", min = "0", message = "排序必须为零或正整数!")
		},
		regexFields = {
			@RegexFieldValidator(fieldName = "articleCategory.sign", expression = "^\\w+$", message = "标识只允许包含英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (articleCategoryService.isExistBySign(articleCategory.getSign())) {
			addActionError("标识已存在!");
			return ERROR;
		}
		if (StringUtils.isNotEmpty(parentId)) {
			ArticleCategory parent = articleCategoryService.load(parentId);
			articleCategory.setParent(parent);
		} else {
			articleCategory.setParent(null);
		}
		articleCategoryService.save(articleCategory);
		logInfo = "添加文章分类: " + articleCategory.getName();
		
		cacheService.flushArticleListPageCache(getRequest());
		
		redirectUrl = "article_category!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "articleCategory.name", message = "分类名称不允许为空!"),
			@RequiredStringValidator(fieldName = "articleCategory.sign", message = "标识不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "articleCategory.orderList", min = "0", message = "排序必须为零或正整数!")
		},
		regexFields = {
			@RegexFieldValidator(fieldName = "articleCategory.sign", expression = "^\\w+$", message = "标识只允许包含英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		ArticleCategory persistent = articleCategoryService.load(id);
		if (!articleCategoryService.isUniqueBySign(persistent.getSign(), articleCategory.getSign())) {
			addActionError("标识已存在!");
			return ERROR;
		}
		BeanUtils.copyProperties(articleCategory, persistent, new String[]{"id", "createDate", "modifyDate", "path", "parent", "children", "articleSet"});
		articleCategoryService.update(persistent);
		logInfo = "编辑文章分类: " + articleCategory.getName();
		
		cacheService.flushArticleListPageCache(getRequest());
		
		redirectUrl = "article_category!list.action";
		return SUCCESS;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	// 获取文章分类树
	public List<ArticleCategory> getArticleCategoryTreeList() {
		return articleCategoryService.getArticleCategoryTreeList();
	}

}