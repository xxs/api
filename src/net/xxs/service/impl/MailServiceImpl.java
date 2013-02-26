package net.xxs.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;

import net.xxs.bean.MailTemplateConfig;
import net.xxs.bean.Setting;
import net.xxs.entity.Member;
import net.xxs.service.MailService;
import net.xxs.util.SettingUtil;
import net.xxs.util.TemplateConfigUtil;

import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.context.ServletContextAware;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Service实现类 - 邮件服务
 */

@Service("mailServiceImpl")
public class MailServiceImpl implements MailService, ServletContextAware {

	private ServletContext servletContext;
	@Resource(name = "freemarkerManager")
	private FreemarkerManager freemarkerManager;
	@Resource(name = "javaMailSender")
	private JavaMailSender javaMailSender;
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	// 增加邮件发送任务
	public void addSendMailTask(final MimeMessage mimeMessage) {
		try {
			taskExecutor.execute(
				new Runnable() {
					public void run() {
						javaMailSender.send(mimeMessage);
					}
				}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMail(String subject, String templatePath, Map<String, Object> data, String toMail) {
		try {
			Setting setting = SettingUtil.getSetting();
			JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl)javaMailSender;
			javaMailSenderImpl.setHost(setting.getSmtpHost());
			javaMailSenderImpl.setPort(setting.getSmtpPort());
			javaMailSenderImpl.setUsername(setting.getSmtpUsername());
			javaMailSenderImpl.setPassword(setting.getSmtpPassword());
			MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
			Template template = configuration.getTemplate(templatePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessageHelper.setFrom(MimeUtility.encodeWord(setting.getCardName()) + " <" + setting.getSmtpFromMail() + ">");
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text, true);
			addSendMailTask(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 获取公共数据
	public Map<String, Object> getCommonData() {
		Map<String, Object> commonData = new HashMap<String, Object>();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
		ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
		commonData.put("bundle", resourceBundleModel);
		commonData.put("base", getContextPath());
		commonData.put("setting", SettingUtil.getSetting());
		return commonData;
	}
	
	public void sendSmtpTestMail(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail) {
		Setting setting = SettingUtil.getSetting();
		Map<String, Object> data = getCommonData();
		MailTemplateConfig mailTemplateConfig = TemplateConfigUtil.getMailTemplateConfig(MailTemplateConfig.SMTP_TEST);
		String subject = mailTemplateConfig.getSubject();
		String templatePath = mailTemplateConfig.getTemplatePath();
		try {
			JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl)javaMailSender;
			javaMailSenderImpl.setHost(smtpHost);
			javaMailSenderImpl.setPort(smtpPort);
			javaMailSenderImpl.setUsername(smtpUsername);
			javaMailSenderImpl.setPassword(smtpPassword);
			MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
			Template template = configuration.getTemplate(templatePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessageHelper.setFrom(MimeUtility.encodeWord(setting.getCardName()) + " <" + smtpFromMail + ">");
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text, true);
			javaMailSender.send(mimeMessage);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void sendPasswordRecoverMail(Member member) {
		Map<String, Object> data = getCommonData();
		data.put("member", member);
		MailTemplateConfig mailTemplateConfig = TemplateConfigUtil.getMailTemplateConfig(MailTemplateConfig.PASSWORD_RECOVER);
		String subject = mailTemplateConfig.getSubject();
		String templatePath = mailTemplateConfig.getTemplatePath();
		sendMail(subject, templatePath, data, member.getEmail());
	}
	
	/**
	 * 获取虚拟路径
	 * 
	 * @return 虚拟路径
	 */
	private String getContextPath() {
		if (servletContext.getMajorVersion() < 2 || (servletContext.getMajorVersion() == 2 && servletContext.getMinorVersion() < 5)) {
			return SettingUtil.getSetting().getContextPath();
		} else {
			return servletContext.getContextPath();
		}
	}

}