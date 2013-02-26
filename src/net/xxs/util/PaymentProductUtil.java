package net.xxs.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.xxs.bean.Setting.CurrencyType;
import net.xxs.payment.BasePaymentProduct;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * 工具类 - 支付产品
 */

public class PaymentProductUtil {
	
	private static final String CACHE_MANAGER_BEAN_NAME = "cacheManager";// cacheManager Bean名称
	private static final String XXS_XML_FILE_NAME = "xxs.xml";// xxs.xml配置文件名称
	private static final String PAYMENT_PRODUCT_CACHE_KEY_PREFIX = "XXS_PAYMENT_PRODUCT";// 缓存Key前缀
	
	/**
	 * 获得所有支付产品集合
	 * 
	 * @return 所有支付产品集合
	 */
	@SuppressWarnings("unchecked")
	public static List<BasePaymentProduct> getPaymentProductList() {
		List<BasePaymentProduct> paymentProductList = null;
		String cacheKey = PAYMENT_PRODUCT_CACHE_KEY_PREFIX + "list";
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(CACHE_MANAGER_BEAN_NAME);
		try {
			paymentProductList = (List<BasePaymentProduct>) generalCacheAdministrator.getFromCache(cacheKey);
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
				List<Node> nodeList = document.selectNodes("/xxs/paymentProduct/*");
				Iterator<Node> iterator = nodeList.iterator();
				
				paymentProductList = new ArrayList<BasePaymentProduct>();
				while (iterator.hasNext()) {
					Node node = iterator.next();
					Node nameNode = node.selectSingleNode("name");
					Node descriptionNode = node.selectSingleNode("description");
					Node bargainorIdNameNode = node.selectSingleNode("bargainorIdName");
					Node bargainorKeyNameNode = node.selectSingleNode("bargainorKeyName");
					Node currencyTypesNode = node.selectSingleNode("currencyTypes");
					Node logoPathNode = node.selectSingleNode("logoPath");
					Node classNameNode = node.selectSingleNode("className");
					
					BasePaymentProduct basePaymentProduct = null;
					try {
						basePaymentProduct = (BasePaymentProduct) Class.forName(classNameNode.getText()).newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
					String[] currencyTypeStrings = StringUtils.split(currencyTypesNode.getText(), ",");
					CurrencyType[] currencyTypes = new CurrencyType[currencyTypeStrings.length];
					for (int i = 0; i < currencyTypeStrings.length; i ++) {
						currencyTypes[i] = CurrencyType.valueOf(currencyTypeStrings[i]);
					}
					
					basePaymentProduct.setId(node.getName());
					basePaymentProduct.setName(nameNode.getText());
					basePaymentProduct.setDescription(descriptionNode.getText());
					basePaymentProduct.setBargainorIdName(bargainorIdNameNode.getText());
					basePaymentProduct.setBargainorKeyName(bargainorKeyNameNode.getText());
					basePaymentProduct.setCurrencyTypes(currencyTypes);
					basePaymentProduct.setLogoPath(logoPathNode.getText());
					paymentProductList.add(basePaymentProduct);
				}
				generalCacheAdministrator.putInCache(cacheKey, paymentProductList);
				updateSucceeded = true;
			} finally {
	            if (!updateSucceeded) {
	            	generalCacheAdministrator.cancelUpdate(cacheKey);
	            }
	        }
		}
		return paymentProductList;
	}

	/**
	 * 根据支付产品标识获取支付产品对象
	 * 
	 * @return Payment对象
	 */
	public static BasePaymentProduct getPaymentProduct(String paymentProductId) {
		BasePaymentProduct basePaymentProduct = null;
		String cacheKey = PAYMENT_PRODUCT_CACHE_KEY_PREFIX + paymentProductId;
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(CACHE_MANAGER_BEAN_NAME);
		try {
			basePaymentProduct = (BasePaymentProduct) generalCacheAdministrator.getFromCache(cacheKey);
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
				Node nameNode = document.selectSingleNode("/xxs/paymentProduct/" + paymentProductId + "/name");
				Node descriptionNode = document.selectSingleNode("/xxs/paymentProduct/" + paymentProductId + "/description");
				Node bargainorIdNameNode = document.selectSingleNode("/xxs/paymentProduct/" + paymentProductId + "/bargainorIdName");
				Node bargainorKeyNameNode = document.selectSingleNode("/xxs/paymentProduct/" + paymentProductId + "/bargainorKeyName");
				Node currencyTypesNode = document.selectSingleNode("/xxs/paymentProduct/" + paymentProductId + "/currencyTypes");
				Node logoPathNode = document.selectSingleNode("/xxs/paymentProduct/" + paymentProductId + "/logoPath");
				Node classNameNode = document.selectSingleNode("/xxs/paymentProduct/" + paymentProductId + "/className");
				
				try {
					basePaymentProduct = (BasePaymentProduct) Class.forName(classNameNode.getText()).newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String[] currencyTypeStrings = StringUtils.split(currencyTypesNode.getText(), ",");
				CurrencyType[] currencyTypes = new CurrencyType[currencyTypeStrings.length];
				for (int i = 0; i < currencyTypeStrings.length; i ++) {
					currencyTypes[i] = CurrencyType.valueOf(currencyTypeStrings[i]);
				}
				
				basePaymentProduct.setId(paymentProductId);
				basePaymentProduct.setName(nameNode.getText());
				basePaymentProduct.setDescription(descriptionNode.getText());
				basePaymentProduct.setBargainorIdName(bargainorIdNameNode.getText());
				basePaymentProduct.setBargainorKeyName(bargainorKeyNameNode.getText());
				basePaymentProduct.setCurrencyTypes(currencyTypes);
				basePaymentProduct.setLogoPath(logoPathNode.getText());
				
				generalCacheAdministrator.putInCache(cacheKey, basePaymentProduct);
				updateSucceeded = true;
			} finally {
	            if (!updateSucceeded) {
	            	generalCacheAdministrator.cancelUpdate(cacheKey);
	            }
	        }
		}
		return basePaymentProduct;
	}
	
	/**
	 * 刷新支付产品缓存
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void flush() {
		File xxsXmlFile = null;
		Document document = null;
		try {
			xxsXmlFile = new ClassPathResource(XXS_XML_FILE_NAME).getFile();
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(xxsXmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Node> nodeList = document.selectNodes("/xxs/paymentProduct");
		Iterator<Node> iterator = nodeList.iterator();
		
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(CACHE_MANAGER_BEAN_NAME);
		while (iterator.hasNext()) {
			Node node = iterator.next();
			String paymentProductCacheKey = PAYMENT_PRODUCT_CACHE_KEY_PREFIX + node.getName();
			generalCacheAdministrator.flushEntry(paymentProductCacheKey);
		}
		String paymentProductListCacheKey = PAYMENT_PRODUCT_CACHE_KEY_PREFIX + "list";
		generalCacheAdministrator.flushEntry(paymentProductListCacheKey);
	}

}