package net.xxs.job;

import java.util.Map;

import net.xxs.service.HtmlService;
import net.xxs.util.SpringUtil;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * Service接口 - 生成充值卡内容HTML任务
 */

public class BuildCardsContentHtmlJob implements Job {
	
	public static final String JOB_NAME = "buildCardsContentHtmlJob";// 任务名称
	public static final String TRIGGER_NAME = "buildCardsContentHtmlTrigger";// Trigger名称
	public static final String GROUP_NAME = "buildCardsContentHtmlGroup";// Group名称

	public void execute(JobExecutionContext context) {
		HtmlService htmlService = (HtmlService) SpringUtil.getBean("quartzHtmlServiceImpl");
		Map<?, ?> jobDataMap = context.getJobDetail().getJobDataMap();
		String id = (String) jobDataMap.get("id");
		if (id != null) {
			htmlService.buildCardsContentHtml(id);
		} else {
			htmlService.buildCardsContentHtml();
		}
	}

}