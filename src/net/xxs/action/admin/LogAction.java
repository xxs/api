package net.xxs.action.admin;

import javax.annotation.Resource;

import net.xxs.entity.Log;
import net.xxs.service.LogService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 后台Action类 - 日志
 */

@ParentPackage("admin")
public class LogAction extends BaseAdminAction {

	private static final long serialVersionUID = 8784555891643520648L;

	private Log log;

	@Resource(name = "logServiceImpl")
	private LogService logService;
	
	// 删除
	public String delete() {
		logService.delete(ids);
		return ajax(Status.success, "删除成功!");
	}

	// 列表
	public String list() {
		pager = logService.findPager(pager);
		return LIST;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

}