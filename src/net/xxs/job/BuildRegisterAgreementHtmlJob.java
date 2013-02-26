package net.xxs.job;

import net.xxs.service.HtmlService;
import net.xxs.util.SpringUtil;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * Service接口 - 生成注册协议HTML任务
 */

public class BuildRegisterAgreementHtmlJob implements Job {
	
	public static final String JOB_NAME = "buildRegisterAgreementHtmlJob";// 任务名称
	public static final String TRIGGER_NAME = "buildRegisterAgreementHtmlTrigger";// Trigger名称
	public static final String GROUP_NAME = "buildRegisterAgreementHtmlGroup";// Group名称

	public void execute(JobExecutionContext context) {
		HtmlService htmlService = (HtmlService) SpringUtil.getBean("quartzHtmlServiceImpl");
		htmlService.buildRegisterAgreementHtml();
	}

}