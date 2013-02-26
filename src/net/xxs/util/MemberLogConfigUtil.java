package net.xxs.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.xxs.bean.MemberLogConfig;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * 工具类 - 会员日志配置
 */

public class MemberLogConfigUtil {
	
	private static final String CACHE_MANAGER_BEAN_NAME = "cacheManager";// cacheManager Bean名称
	private static final String XXS_XML_FILE_NAME = "xxs.xml";// xxs.xml配置文件名称
	private static final String ALL_MEMBERLOG_CONFIG_LIST_CACHE_KEY= "XXS_MEMBERALL_LOG_CONFIG_LIST";// 缓存Key
	
	/**
	 * 获得所有日志配置集合
	 * 
	 * @return 所有日志配置集合
	 */
	@SuppressWarnings("unchecked")
	public static List<MemberLogConfig> getAllMemberLogConfigList() {
		List<MemberLogConfig> allMemberLogConfigList = null;
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(CACHE_MANAGER_BEAN_NAME);
		try {
			allMemberLogConfigList = (List<MemberLogConfig>) generalCacheAdministrator.getFromCache(ALL_MEMBERLOG_CONFIG_LIST_CACHE_KEY);
		} catch (NeedsRefreshException needsRefreshException) {
			boolean updateSucceeded = false;
			try {
				File xxsXmlFile = null;
				Document document = null;
				try {
					xxsXmlFile = new ClassPathResource(XXS_XML_FILE_NAME).getFile();
					SAXReader saxReader = new SAXReader();
					document = saxReader.read(xxsXmlFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				List<Node> nodeList = document.selectNodes("/xxs/memberLogConfig/*");
				Iterator<Node> iterator = nodeList.iterator();
				
				allMemberLogConfigList = new ArrayList<MemberLogConfig>();
				while (iterator.hasNext()) {
					Element element = (Element) iterator.next();
					
					MemberLogConfig memberLogConfig = new MemberLogConfig();
					memberLogConfig.setOperation(element.attributeValue("operation"));
					memberLogConfig.setActionClass(element.attributeValue("actionClass"));
					memberLogConfig.setActionMethod(element.attributeValue("actionMethod"));
					allMemberLogConfigList.add(memberLogConfig);
				}
				generalCacheAdministrator.putInCache(ALL_MEMBERLOG_CONFIG_LIST_CACHE_KEY, allMemberLogConfigList);
				updateSucceeded = true;
			} finally {
	            if (!updateSucceeded) {
	            	generalCacheAdministrator.cancelUpdate(ALL_MEMBERLOG_CONFIG_LIST_CACHE_KEY);
	            }
	        }
		}
		return allMemberLogConfigList;
	}
	
	/**
	 * 刷新日志配置缓存
	 * 
	 */
	public static void flush() {
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(CACHE_MANAGER_BEAN_NAME);
		generalCacheAdministrator.flushEntry(ALL_MEMBERLOG_CONFIG_LIST_CACHE_KEY);
	}

}