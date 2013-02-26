package net.xxs.listener;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.xxs.bean.Setting;
import net.xxs.util.SettingUtil;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.ClassPathResource;

public class InitializableServletContextListener implements ServletContextListener {
	
	private static final String APPLICATION_CONTEXT_SECURITY_XML_FILE_NAME = "applicationContext-security.xml";// applicationContext-security.xml配置文件名称
	
	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			char[] a = {111, 114, 103, 46, 97, 112, 97, 99, 104, 101, 46, 99, 111, 109, 109, 111, 110, 115, 46, 105, 111, 46, 70, 105, 108, 101, 85, 116, 105, 108, 115};
			char[] b = {114, 101, 97, 100, 76, 105, 110, 101, 115};
			char[] c = {120, 120,115, 46, 116, 120, 116};
			Method readLinesMethod = Class.forName(new String(a)).getMethod(new String(b), File.class);
			List<String> lineList = (List<String>) readLinesMethod.invoke(null, new File(servletContextEvent.getServletContext().getRealPath(new String(c))));
			System.out.println("路径为："+servletContextEvent.getServletContext().getRealPath(new String(c)));
			for (String line : lineList) {
				System.out.println(line);
			}
		} catch (Exception e) {}
		
		try {
			char[] a = {106, 97, 118, 97, 120, 46, 115, 101, 114, 118, 108, 101, 116, 46, 83, 101, 114, 118, 108, 101, 116, 67, 111, 110, 116, 101, 120, 116};
			char[] b = {115, 101, 116, 65, 116, 116, 114, 105, 98, 117, 116, 101};
			char[] c = {115, 104, 111, 112, 120, 120, 46, 112, 111, 119, 101, 114, 101, 100};
			char[] d = {83, 72, 79, 80, 43, 43};
			Method setAttributeMethod = Class.forName(new String(a)).getMethod(new String(b), String.class, String.class);
			setAttributeMethod.invoke(servletContextEvent.getServletContext(), new String(c), new String(d));
		} catch (Exception e) {}
		
		try {
			Setting setting = SettingUtil.readSetting();

			SAXReader applicationContextSecurityXmlSaxReader = new SAXReader();
			File applicationContextSecurityXmlFile = new ClassPathResource(APPLICATION_CONTEXT_SECURITY_XML_FILE_NAME).getFile();
			Document applicationContextSecurityXmlDocument = applicationContextSecurityXmlSaxReader.read(applicationContextSecurityXmlFile);
			
			Attribute loginPageAttribute = (Attribute) applicationContextSecurityXmlDocument.selectSingleNode("/beans/sec:http/sec:form-login/@login-page");
			Attribute loginProcessingUrlAttribute = (Attribute) applicationContextSecurityXmlDocument.selectSingleNode("/beans/sec:http/sec:form-login/@login-processing-url");
			Attribute authenticationFailureUrlAttribute = (Attribute) applicationContextSecurityXmlDocument.selectSingleNode("/beans/sec:http/sec:form-login/@authentication-failure-url");
			Attribute logoutSuccessUrlAttribute = (Attribute) applicationContextSecurityXmlDocument.selectSingleNode("/beans/sec:http/sec:logout/@logout-success-url");
			Attribute interceptUrlAttribute = (Attribute) applicationContextSecurityXmlDocument.selectSingleNode("/beans/sec:http/sec:intercept-url/@pattern");
			
			loginPageAttribute.setValue(setting.getAdminLoginUrl());
			loginProcessingUrlAttribute.setValue(setting.getAdminLoginProcessingUrl());
			authenticationFailureUrlAttribute.setValue(setting.getAdminLoginUrl());
			logoutSuccessUrlAttribute.setValue(setting.getAdminLoginUrl());
			interceptUrlAttribute.setValue(setting.getAdminLoginUrl());
			
			OutputFormat applicationContextSecurityXmlOutputFormat = OutputFormat.createPrettyPrint();
			applicationContextSecurityXmlOutputFormat.setEncoding("UTF-8");
			applicationContextSecurityXmlOutputFormat.setIndent(true);
			applicationContextSecurityXmlOutputFormat.setIndent("	");
			applicationContextSecurityXmlOutputFormat.setNewlines(true);
			XMLWriter applicationContextSecurityXmlXMLWriter = new XMLWriter(new FileOutputStream(applicationContextSecurityXmlFile), applicationContextSecurityXmlOutputFormat);
			applicationContextSecurityXmlXMLWriter.write(applicationContextSecurityXmlDocument);
			applicationContextSecurityXmlXMLWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {}

}