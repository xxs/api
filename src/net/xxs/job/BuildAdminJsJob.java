package net.xxs.job;

import net.xxs.service.HtmlService;
import net.xxs.util.SpringUtil;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * Service接口 - 生成ADMIN.JS任务
 */

public class BuildAdminJsJob implements Job {
	
	public static final String JOB_NAME = "buildAdminJsJob";// 任务名称
	public static final String TRIGGER_NAME = "buildAdminJsTrigger";// Trigger名称
	public static final String GROUP_NAME = "buildAdminJsGroup";// Group名称

	public void execute(JobExecutionContext context) {
		HtmlService htmlService = (HtmlService) SpringUtil.getBean("quartzHtmlServiceImpl");
		htmlService.buildAdminJs();
	}

}