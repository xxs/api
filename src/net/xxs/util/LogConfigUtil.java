package net.xxs.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.xxs.bean.LogConfig;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * 工具类 - 日志配置
 */

public class LogConfigUtil {
	
	private static final String CACHE_MANAGER_BEAN_NAME = "cacheManager";// cacheManager Bean名称
	private static final String XXS_XML_FILE_NAME = "xxs.xml";// xxs.xml配置文件名称
	private static final String ALL_LOG_CONFIG_LIST_CACHE_KEY= "XXS_ALL_LOG_CONFIG_LIST";// 缓存Key
	
	/**
	 * 获得所有日志配置集合
	 * 
	 * @return 所有日志配置集合
	 */
	@SuppressWarnings("unchecked")
	public static List<LogConfig> getAllLogConfigList() {
		List<LogConfig> allLogConfigList = null;
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(CACHE_MANAGER_BEAN_NAME);
		try {
			allLogConfigList = (List<LogConfig>) generalCacheAdministrator.getFromCache(ALL_LOG_CONFIG_LIST_CACHE_KEY);
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
				List<Node> nodeList = document.selectNodes("/xxs/logConfig/*");
				Iterator<Node> iterator = nodeList.iterator();
				
				allLogConfigList = new ArrayList<LogConfig>();
				while (iterator.hasNext()) {
					Element element = (Element) iterator.next();
					
					LogConfig logConfig = new LogConfig();
					logConfig.setOperation(element.attributeValue("operation"));
					logConfig.setActionClass(element.attributeValue("actionClass"));
					logConfig.setActionMethod(element.attributeValue("actionMethod"));
					allLogConfigList.add(logConfig);
				}
				generalCacheAdministrator.putInCache(ALL_LOG_CONFIG_LIST_CACHE_KEY, allLogConfigList);
				updateSucceeded = true;
			} finally {
	            if (!updateSucceeded) {
	            	generalCacheAdministrator.cancelUpdate(ALL_LOG_CONFIG_LIST_CACHE_KEY);
	            }
	        }
		}
		return allLogConfigList;
	}
	
	/**
	 * 刷新日志配置缓存
	 * 
	 */
	public static void flush() {
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(CACHE_MANAGER_BEAN_NAME);
		generalCacheAdministrator.flushEntry(ALL_LOG_CONFIG_LIST_CACHE_KEY);
	}

}