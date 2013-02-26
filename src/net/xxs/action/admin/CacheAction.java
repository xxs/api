package net.xxs.action.admin;

import javax.annotation.Resource;

import net.xxs.service.CacheService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import freemarker.template.TemplateException;

/**
 * 后台Action类 - 缓存
 */

@ParentPackage("admin")
public class CacheAction extends BaseAdminAction {

	private static final long serialVersionUID = 3290111140770511789L;

	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	@Resource(name = "cacheManager")
	private GeneralCacheAdministrator cacheManager;
	@Resource(name = "freemarkerManager")
	private FreemarkerManager freemarkerManager;
	
	// 刷新所有缓存
	public String flush() {
		cacheManager.flushAll();
		cacheService.flushAllPageCache(getRequest());
		try {
			freemarkerManager.getConfiguration(getServletContext()).clearTemplateCache();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

}