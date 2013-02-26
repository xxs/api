package net.xxs.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import net.xxs.job.BuildAdminJsJob;
import net.xxs.job.BuildArticleContentHtmlJob;
import net.xxs.job.BuildCardsContentHtmlJob;
import net.xxs.job.BuildErrorHtmlJob;
import net.xxs.job.BuildIndexHtmlJob;
import net.xxs.job.BuildLoginHtmlJob;
import net.xxs.job.BuildRegisterAgreementHtmlJob;
import net.xxs.job.BuildCardJsJob;
import net.xxs.job.DeleteArticleContentHtmlJob;
import net.xxs.job.DeleteCardsContentHtmlJob;
import net.xxs.service.JobService;
import net.xxs.util.SettingUtil;

import org.apache.commons.lang.time.DateUtils;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.stereotype.Service;

/**
 * Service实现类 - 任务
 */

@Service("jobServiceImpl")
public class JobServiceImpl implements JobService {
	
	@Resource(name = "scheduler")
	private Scheduler scheduler;
	
	public void buildIndexHtml() {
		try {
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(BuildIndexHtmlJob.TRIGGER_NAME);
			simpleTrigger.setGroup(BuildIndexHtmlJob.GROUP_NAME);
			simpleTrigger.setJobName(BuildIndexHtmlJob.JOB_NAME);
			simpleTrigger.setJobGroup(BuildIndexHtmlJob.GROUP_NAME);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(BuildIndexHtmlJob.JOB_NAME, BuildIndexHtmlJob.GROUP_NAME);
			if (jobDetail != null) {
				scheduler.rescheduleJob(BuildIndexHtmlJob.TRIGGER_NAME, BuildIndexHtmlJob.GROUP_NAME, simpleTrigger);
			} else {
				jobDetail = new JobDetail(BuildIndexHtmlJob.JOB_NAME, BuildIndexHtmlJob.GROUP_NAME, BuildIndexHtmlJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildLoginHtml() {
		try {
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(BuildLoginHtmlJob.TRIGGER_NAME);
			simpleTrigger.setGroup(BuildLoginHtmlJob.GROUP_NAME);
			simpleTrigger.setJobName(BuildLoginHtmlJob.JOB_NAME);
			simpleTrigger.setJobGroup(BuildLoginHtmlJob.GROUP_NAME);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(BuildLoginHtmlJob.JOB_NAME, BuildLoginHtmlJob.GROUP_NAME);
			if (jobDetail != null) {
				scheduler.rescheduleJob(BuildLoginHtmlJob.TRIGGER_NAME, BuildLoginHtmlJob.GROUP_NAME, simpleTrigger);
			} else {
				jobDetail = new JobDetail(BuildLoginHtmlJob.JOB_NAME, BuildLoginHtmlJob.GROUP_NAME, BuildLoginHtmlJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildRegisterAgreementHtml() {
		try {
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(BuildRegisterAgreementHtmlJob.TRIGGER_NAME);
			simpleTrigger.setGroup(BuildRegisterAgreementHtmlJob.GROUP_NAME);
			simpleTrigger.setJobName(BuildRegisterAgreementHtmlJob.JOB_NAME);
			simpleTrigger.setJobGroup(BuildRegisterAgreementHtmlJob.GROUP_NAME);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(BuildRegisterAgreementHtmlJob.JOB_NAME, BuildRegisterAgreementHtmlJob.GROUP_NAME);
			if (jobDetail != null) {
				scheduler.rescheduleJob(BuildRegisterAgreementHtmlJob.TRIGGER_NAME, BuildRegisterAgreementHtmlJob.GROUP_NAME, simpleTrigger);
			} else {
				jobDetail = new JobDetail(BuildRegisterAgreementHtmlJob.JOB_NAME, BuildRegisterAgreementHtmlJob.GROUP_NAME, BuildRegisterAgreementHtmlJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildAdminJs() {
		try {
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(BuildAdminJsJob.TRIGGER_NAME);
			simpleTrigger.setGroup(BuildAdminJsJob.GROUP_NAME);
			simpleTrigger.setJobName(BuildAdminJsJob.JOB_NAME);
			simpleTrigger.setJobGroup(BuildAdminJsJob.GROUP_NAME);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(BuildAdminJsJob.JOB_NAME, BuildAdminJsJob.GROUP_NAME);
			if (jobDetail != null) {
				scheduler.rescheduleJob(BuildAdminJsJob.TRIGGER_NAME, BuildAdminJsJob.GROUP_NAME, simpleTrigger);
			} else {
				jobDetail = new JobDetail(BuildAdminJsJob.JOB_NAME, BuildAdminJsJob.GROUP_NAME, BuildAdminJsJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildCardJs() {
		try {
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(BuildCardJsJob.TRIGGER_NAME);
			simpleTrigger.setGroup(BuildCardJsJob.GROUP_NAME);
			simpleTrigger.setJobName(BuildCardJsJob.JOB_NAME);
			simpleTrigger.setJobGroup(BuildCardJsJob.GROUP_NAME);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(BuildCardJsJob.JOB_NAME, BuildCardJsJob.GROUP_NAME);
			if (jobDetail != null) {
				scheduler.rescheduleJob(BuildCardJsJob.TRIGGER_NAME, BuildCardJsJob.GROUP_NAME, simpleTrigger);
			} else {
				jobDetail = new JobDetail(BuildCardJsJob.JOB_NAME, BuildCardJsJob.GROUP_NAME, BuildCardJsJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildArticleContentHtml(String id) {
		try {
			String jobName = BuildArticleContentHtmlJob.JOB_NAME + id;
			String triggerName = BuildArticleContentHtmlJob.TRIGGER_NAME + id;
			String groupName = BuildArticleContentHtmlJob.GROUP_NAME;
			
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(triggerName);
			simpleTrigger.setGroup(groupName);
			simpleTrigger.setJobName(jobName);
			simpleTrigger.setJobGroup(groupName);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);
			if (jobDetail != null) {
				scheduler.rescheduleJob(triggerName, groupName, simpleTrigger);
				jobDetail.getJobDataMap().put("id", id);
			} else {
				jobDetail = new JobDetail(jobName, groupName, BuildArticleContentHtmlJob.class);
				jobDetail.getJobDataMap().put("id", id);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildArticleContentHtml() {
		try {
			String jobName = BuildArticleContentHtmlJob.JOB_NAME;
			String triggerName = BuildArticleContentHtmlJob.TRIGGER_NAME;
			String groupName = BuildArticleContentHtmlJob.GROUP_NAME;
			
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(triggerName);
			simpleTrigger.setGroup(groupName);
			simpleTrigger.setJobName(jobName);
			simpleTrigger.setJobGroup(groupName);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime() * 2));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			String[] triggerNames = scheduler.getTriggerNames(groupName);
			String[] jobNames = scheduler.getJobNames(groupName);
			if (triggerNames != null) {
				for (String tn : triggerNames) {
					scheduler.pauseTrigger(tn, groupName);
					scheduler.unscheduleJob(tn, groupName);
				}
			}
			if (jobNames != null) {
				for (String jn : jobNames) {
					scheduler.deleteJob(jn, groupName);
				}
			}
			
			JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);
			if (jobDetail != null) {
				scheduler.rescheduleJob(triggerName, groupName, simpleTrigger);
			} else {
				jobDetail = new JobDetail(jobName, groupName, BuildArticleContentHtmlJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildCardsContentHtml(String id) {
		try {
			String jobName = BuildCardsContentHtmlJob.JOB_NAME + id;
			String triggerName = BuildCardsContentHtmlJob.TRIGGER_NAME + id;
			String groupName = BuildCardsContentHtmlJob.GROUP_NAME;
			
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(triggerName);
			simpleTrigger.setGroup(groupName);
			simpleTrigger.setJobName(jobName);
			simpleTrigger.setJobGroup(groupName);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);
			if (jobDetail != null) {
				scheduler.rescheduleJob(triggerName, groupName, simpleTrigger);
				jobDetail.getJobDataMap().put("id", id);
			} else {
				jobDetail = new JobDetail(jobName, groupName, BuildCardsContentHtmlJob.class);
				jobDetail.getJobDataMap().put("id", id);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildCardsContentHtml() {
		try {
			String jobName = BuildCardsContentHtmlJob.JOB_NAME;
			String triggerName = BuildCardsContentHtmlJob.TRIGGER_NAME;
			String groupName = BuildCardsContentHtmlJob.GROUP_NAME;
			
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(triggerName);
			simpleTrigger.setGroup(groupName);
			simpleTrigger.setJobName(jobName);
			simpleTrigger.setJobGroup(groupName);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime() * 2));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			String[] triggerNames = scheduler.getTriggerNames(groupName);
			String[] jobNames = scheduler.getJobNames(groupName);
			if (triggerNames != null) {
				for (String tn : triggerNames) {
					scheduler.pauseTrigger(tn, groupName);
					scheduler.unscheduleJob(tn, groupName);
				}
			}
			if (jobNames != null) {
				for (String jn : jobNames) {
					scheduler.deleteJob(jn, groupName);
				}
			}
			
			JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);
			if (jobDetail != null) {
				scheduler.rescheduleJob(triggerName, groupName, simpleTrigger);
			} else {
				jobDetail = new JobDetail(jobName, groupName, BuildCardsContentHtmlJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildErrorHtml() {
		try {
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(BuildErrorHtmlJob.TRIGGER_NAME);
			simpleTrigger.setGroup(BuildErrorHtmlJob.GROUP_NAME);
			simpleTrigger.setJobName(BuildErrorHtmlJob.JOB_NAME);
			simpleTrigger.setJobGroup(BuildErrorHtmlJob.GROUP_NAME);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(BuildErrorHtmlJob.JOB_NAME, BuildErrorHtmlJob.GROUP_NAME);
			if (jobDetail != null) {
				scheduler.rescheduleJob(BuildErrorHtmlJob.TRIGGER_NAME, BuildErrorHtmlJob.GROUP_NAME, simpleTrigger);
			} else {
				jobDetail = new JobDetail(BuildErrorHtmlJob.JOB_NAME, BuildErrorHtmlJob.GROUP_NAME, BuildErrorHtmlJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteArticleContentHtml(String htmlPath, Integer pageCount) {
		try {
			String jobName = DeleteArticleContentHtmlJob.JOB_NAME + htmlPath;
			String triggerName = DeleteArticleContentHtmlJob.TRIGGER_NAME + htmlPath;
			String groupName = DeleteArticleContentHtmlJob.GROUP_NAME;
			
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(triggerName);
			simpleTrigger.setGroup(groupName);
			simpleTrigger.setJobName(jobName);
			simpleTrigger.setJobGroup(groupName);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);
			if (jobDetail != null) {
				scheduler.rescheduleJob(triggerName, groupName, simpleTrigger);
				jobDetail.getJobDataMap().put("htmlPath", htmlPath);
				jobDetail.getJobDataMap().put("pageCount", pageCount);
			} else {
				jobDetail = new JobDetail(jobName, groupName, DeleteCardsContentHtmlJob.class);
				jobDetail.getJobDataMap().put("htmlPath", htmlPath);
				jobDetail.getJobDataMap().put("pageCount", pageCount);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCardsContentHtml(String htmlPath) {
		try {
			String jobName = DeleteCardsContentHtmlJob.JOB_NAME + htmlPath;
			String triggerName = DeleteCardsContentHtmlJob.TRIGGER_NAME + htmlPath;
			String groupName = DeleteCardsContentHtmlJob.GROUP_NAME;
			
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(triggerName);
			simpleTrigger.setGroup(groupName);
			simpleTrigger.setJobName(jobName);
			simpleTrigger.setJobGroup(groupName);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);
			if (jobDetail != null) {
				scheduler.rescheduleJob(triggerName, groupName, simpleTrigger);
				jobDetail.getJobDataMap().put("htmlPath", htmlPath);
			} else {
				jobDetail = new JobDetail(jobName, groupName, DeleteCardsContentHtmlJob.class);
				jobDetail.getJobDataMap().put("htmlPath", htmlPath);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}