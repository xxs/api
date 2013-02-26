package net.xxs.job;

import net.xxs.service.HtmlService;
import net.xxs.util.SpringUtil;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * Service接口 - 生成CARD.JS任务
 */

public class BuildCardJsJob implements Job {
	
	public static final String JOB_NAME = "buildCardJsJob";// 任务名称
	public static final String TRIGGER_NAME = "buildCardJsTrigger";// Trigger名称
	public static final String GROUP_NAME = "buildCardJsGroup";// Group名称

	public void execute(JobExecutionContext context) {
		HtmlService htmlService = (HtmlService) SpringUtil.getBean("quartzHtmlServiceImpl");
		htmlService.buildCardJs();
	}

}