package net.xxs.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import net.xxs.bean.PageTemplateConfig;
import net.xxs.common.FreemarkerManager;
import net.xxs.dao.ArticleDao;
import net.xxs.dao.CardsDao;
import net.xxs.entity.Article;
import net.xxs.entity.Cards;
import net.xxs.service.ArticleCategoryService;
import net.xxs.service.HtmlService;
import net.xxs.util.SettingUtil;
import net.xxs.util.TemplateConfigUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Service实现类 - 生成静态
 */

@Service("htmlServiceImpl")
public class HtmlServiceImpl implements HtmlService, ServletContextAware {

	private ServletContext servletContext;
	@Resource(name = "freemarkerManager")
	private FreemarkerManager freemarkerManager;
	@Resource(name = "articleDaoImpl")
	private ArticleDao articleDao;
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	@Resource(name = "cardsDaoImpl")
	private CardsDao cardsDao;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	// 获取公用数据
	public Map<String, Object> getCommonData() {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
		ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
		
		Map<String, Object> commonData = new HashMap<String, Object>();
		commonData.put("bundle", resourceBundleModel);
		commonData.put("base", getContextPath());
		commonData.put("setting", SettingUtil.getSetting());
		commonData.put("currencyFormat", SettingUtil.getCurrencyFormat());
		return commonData;
	}
	
	public void buildHtml(String templatePath, String htmlPath, Map<String, Object> data) {
		try {
			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
			Template template = configuration.getTemplate(templatePath);
			File htmlFile = new File(servletContext.getRealPath(htmlPath));
			File htmlDirectory = htmlFile.getParentFile();
			if (!htmlDirectory.exists()) {
				htmlDirectory.mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
			template.process(data, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buildIndexHtml() {
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.INDEX);
		Map<String, Object> data = getCommonData();
		String htmlPath = pageTemplateConfig.getHtmlPath();
		String templatePath = pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	
	public void buildLoginHtml() {
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.LOGIN);
		Map<String, Object> data = getCommonData();
		String htmlPath = pageTemplateConfig.getHtmlPath();
		String templatePath = pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	public void buildRegisterHtml() {
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.REGISTER);
		Map<String, Object> data = getCommonData();
		String htmlPath = pageTemplateConfig.getHtmlPath();
		String templatePath = pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	
	public void buildRegisterAgreementHtml() {
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.REGISTER_AGREEMENT);
		Map<String, Object> data = getCommonData();
		String htmlPath = pageTemplateConfig.getHtmlPath();
		String templatePath = pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	
	public void buildAdminJs() {
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.ADMIN_JS);
		Map<String, Object> data = getCommonData();
		String htmlPath = pageTemplateConfig.getHtmlPath();
		String templatePath = pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	
	public void buildCardJs() {
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.CARD_JS);
		Map<String, Object> data = getCommonData();
		String htmlPath = pageTemplateConfig.getHtmlPath();
		String templatePath = pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	
	@Transactional(readOnly = true)
	public void buildArticleContentHtml(String id) {
		Article article = articleDao.get(id);
		buildArticleContentHtml(article);
	}
	
	public void buildArticleContentHtml(Article article) {
		if (article == null) {
			return;
		}
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.ARTICLE_CONTENT);
		Map<String, Object> data = getCommonData();
		data.put("article", article);
		data.put("pathList", articleCategoryService.getArticleCategoryPathList(article.getArticleCategory()));
		String htmlPath = article.getHtmlPath();
		String prefix = StringUtils.substringBeforeLast(htmlPath, ".");
		String extension = StringUtils.substringAfterLast(htmlPath, ".");
		List<String> pageContentList = article.getPageContentList();
		for (int i = 0; i < pageContentList.size(); i++) {
			String currentHtmlPath = null;
			if (i == 0) {
				currentHtmlPath = htmlPath;
			} else {
				currentHtmlPath = prefix + "_" + (i + 1) + "." + extension;
			}
			if (article.getIsPublication()) {
				data.put("content", pageContentList.get(i));
				data.put("pageNumber", i + 1);
				data.put("pageCount", pageContentList.size());
				String templatePath = pageTemplateConfig.getTemplatePath();
				buildHtml(templatePath, currentHtmlPath, data);
			} else {
				File htmlFile = new File(servletContext.getRealPath(currentHtmlPath));
				if (htmlFile.exists()) {
					htmlFile.delete();
				}
			}
		}
	}
	
	@Transactional(readOnly = true)
	public void buildArticleContentHtml() {
		long articleTotalCount = articleDao.getTotalCount();
		for (int i = 0; i < articleTotalCount; i += 30) {
			List<Article> articleList = articleDao.getArticleList(null, null, null, i, 30);
			for (Article article : articleList) {
				buildArticleContentHtml(article);
			}
		}
	}
	
	@Transactional(readOnly = true)
	public void buildCardsContentHtml(String id) {
		Cards cards = cardsDao.get(id);
		buildCardsContentHtml(cards);
	}
	
	public void buildCardsContentHtml(Cards cards) {
		if (cards == null) {
			return;
		}
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.CARDS_CONTENT);
		String htmlPath = cards.getHtmlPath();
		if (cards.getIsMarketable()) {
			Map<String, Object> data = getCommonData();
			data.put("cards", cards);
			String templatePath = pageTemplateConfig.getTemplatePath();
			buildHtml(templatePath, htmlPath, data);
		} else {
			File htmlFile = new File(servletContext.getRealPath(cards.getHtmlPath()));
			if (htmlFile.exists()) {
				htmlFile.delete();
			}
		}
	}
	
	@Transactional(readOnly = true)
	public void buildCardsContentHtml() {
		long cardsTotalCount = cardsDao.getTotalCount();
		for (int i = 0; i < cardsTotalCount; i += 30) {
			List<Cards> cardsList = cardsDao.getCardsList(30);
			for (Cards cards : cardsList) {
				buildCardsContentHtml(cards);
			}
		}
	}
	
	public void buildErrorPageHtml() {
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.ERROR_PAGE);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "系统出现异常,请与管理员联系!");
		String htmlPath = pageTemplateConfig.getHtmlPath();
		String templatePath = pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	
	public void buildErrorPageAccessDeniedHtml() {
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.ERROR_PAGE_ACCESS_DENIED);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "您无此访问权限!");
		String htmlPath = pageTemplateConfig.getHtmlPath();
		String templatePath = pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	
	public void buildErrorPage500Html() {
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.ERROR_PAGE_500);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "系统出现异常,请与管理员联系!");
		String htmlPath = pageTemplateConfig.getHtmlPath();
		String templatePath = pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	
	public void buildErrorPage404Html() {
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.ERROR_PAGE_404);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "您访问的页面不存在!准备要出现404了");
		String htmlPath = pageTemplateConfig.getHtmlPath();
		String templatePath = pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	
	public void buildErrorPage403Html() {
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.ERROR_PAGE_403);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "系统出现异常,请与管理员联系!");
		String htmlPath = pageTemplateConfig.getHtmlPath();
		String templatePath = pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	
	public void deleteArticleContentHtml(String htmlPath, Integer pageCount) {
		if (htmlPath != null && pageCount != null) {
			String prefix = StringUtils.substringBeforeLast(htmlPath, ".");
			String extension = StringUtils.substringAfterLast(htmlPath, ".");
			for (int i = 0; i < pageCount; i++) {
				String currentHtmlPath = null;
				if (i == 0) {
					currentHtmlPath = htmlPath;
				} else {
					currentHtmlPath = prefix + "_" + (i + 1) + "." + extension;
				}
				File htmlFile = new File(servletContext.getRealPath(currentHtmlPath));
				if (htmlFile.exists()) {
					htmlFile.delete();
				}
			}
		}
	}
	
	public void deleteCardsContentHtml(String htmlPath) {
		if (htmlPath != null) {
			File htmlFile = new File(servletContext.getRealPath(htmlPath));
			if (htmlFile.exists()) {
				htmlFile.delete();
			}
		}
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