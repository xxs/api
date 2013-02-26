package net.xxs.job;

import net.xxs.service.HtmlService;
import net.xxs.util.SpringUtil;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * Service接口 - 生成首页HTML任务
 */

public class BuildIndexHtmlJob implements Job {
	
	public static final String JOB_NAME = "buildIndexHtmlJob";// 任务名称
	public static final String TRIGGER_NAME = "buildIndexHtmlTrigger";// Trigger名称
	public static final String GROUP_NAME = "buildIndexHtmlGroup";// Group名称

	public void execute(JobExecutionContext context) {
		HtmlService htmlService = (HtmlService) SpringUtil.getBean("quartzHtmlServiceImpl");
		htmlService.buildIndexHtml();
	}

}