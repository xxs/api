package net.xxs.action.admin;

import java.util.List;

import javax.annotation.Resource;

import net.xxs.bean.PageTemplateConfig;
import net.xxs.util.TemplateConfigUtil;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import freemarker.template.TemplateException;

/**
 * 后台Action类 - 页面模板
 */

@ParentPackage("admin")
public class PageTemplateAction extends BaseAdminAction {

	private static final long serialVersionUID = -8595064703705228101L;
	
	private PageTemplateConfig pageTemplateConfig;
	private String templateFileContent;
	
	@Resource(name = "freemarkerManager")
	private FreemarkerManager freemarkerManager;

	// 列表
	public String list() {
		return LIST;
	}

	// 编辑
	public String edit() {
		pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(pageTemplateConfig.getName());
		templateFileContent = TemplateConfigUtil.readTemplateFileContent(pageTemplateConfig);
		return INPUT;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "templateFileContent", message = "模板内容不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(pageTemplateConfig.getName());
		TemplateConfigUtil.writeTemplateFileContent(pageTemplateConfig, templateFileContent);
		try {
			freemarkerManager.getConfiguration(getServletContext()).clearTemplateCache();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		redirectUrl = "page_template!list.action";
		return SUCCESS;
	}

	// 获取生成静态模板配置集合
	public List<PageTemplateConfig> getAllPageTemplateConfigList() {
		return TemplateConfigUtil.getAllPageTemplateConfigList();
	}

	public PageTemplateConfig getPageTemplateConfig() {
		return pageTemplateConfig;
	}

	public void setPageTemplateConfig(PageTemplateConfig pageTemplateConfig) {
		this.pageTemplateConfig = pageTemplateConfig;
	}

	public String getTemplateFileContent() {
		return templateFileContent;
	}

	public void setTemplateFileContent(String templateFileContent) {
		this.templateFileContent = templateFileContent;
	}

}