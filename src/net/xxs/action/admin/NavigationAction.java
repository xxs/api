package net.xxs.action.admin;

import java.util.List;

import javax.annotation.Resource;

import net.xxs.entity.ArticleCategory;
import net.xxs.entity.Navigation;
import net.xxs.service.ArticleCategoryService;
import net.xxs.service.NavigationService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 导航
 */

@ParentPackage("admin")
public class NavigationAction extends BaseAdminAction {

	private static final long serialVersionUID = -7786508966240073537L;

	private Navigation navigation;
	private List<ArticleCategory> articleCategoryTreeList;

	@Resource(name = "navigationServiceImpl")
	private NavigationService navigationService;
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		navigation = navigationService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = navigationService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		navigationService.delete(ids);
		redirectUrl = "navigation!list.action";
		return ajax(Status.success, "删除成功!");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "navigation.name", message = "导航名称不允许为空!"),
			@RequiredStringValidator(fieldName = "navigation.url", message = "链接地址不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "navigation.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		navigationService.save(navigation);
		redirectUrl = "navigation!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "navigation.name", message = "导航名称不允许为空!"),
			@RequiredStringValidator(fieldName = "navigation.url", message = "链接地址不允许为空!")
		}, 
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "navigation.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		Navigation persistent = navigationService.load(id);
		BeanUtils.copyProperties(navigation, persistent, new String[]{"id", "createDate", "modifyDate"});
		navigationService.update(persistent);
		redirectUrl = "navigation!list.action";
		return SUCCESS;
	}

	public Navigation getNavigation() {
		return navigation;
	}

	public void setNavigation(Navigation navigation) {
		this.navigation = navigation;
	}

	public List<ArticleCategory> getArticleCategoryTreeList() {
		articleCategoryTreeList = articleCategoryService.getArticleCategoryTreeList();
		return articleCategoryTreeList;
	}

	public void setArticleCategoryTreeList(List<ArticleCategory> articleCategoryTreeList) {
		this.articleCategoryTreeList = articleCategoryTreeList;
	}

}