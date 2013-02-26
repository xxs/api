package net.xxs.job;

import java.util.Map;

import net.xxs.service.HtmlService;
import net.xxs.util.SpringUtil;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * Service接口 - 生成文章内容HTML任务
 */

public class BuildArticleContentHtmlJob implements Job {
	
	public static final String JOB_NAME = "buildArticleContentHtmlJob";// 任务名称
	public static final String TRIGGER_NAME = "buildArticleContentHtmlTrigger";// Trigger名称
	public static final String GROUP_NAME = "buildArticleContentHtmlGroup";// Group名称

	// 若充值卡ID不存在,则生成所有文章内容HTML
	public void execute(JobExecutionContext context) {
		HtmlService htmlService = (HtmlService) SpringUtil.getBean("quartzHtmlServiceImpl");
		Map<?, ?> jobDataMap = context.getJobDetail().getJobDataMap();
		String id = (String) jobDataMap.get("id");
		if (id != null) {
			htmlService.buildArticleContentHtml(id);
		} else {
			htmlService.buildArticleContentHtml();
		}
	}

}