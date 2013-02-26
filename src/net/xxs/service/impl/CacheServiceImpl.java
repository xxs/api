package net.xxs.service.impl;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import net.xxs.service.CacheService;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;

/**
 * Service实现类 - 缓存
 */

@Service("cacheServiceImpl")
public class CacheServiceImpl implements CacheService, ServletContextAware {
	
	public static final String ARTICLE_LIST_PAGE_CACHE_GROUP = "articleList";// 文章列表页面缓存Group
	public static final String CARDS_LIST_PAGE_CACHE_GROUP = "cardsList";// 文章列表页面缓存Group
	public static final String LEAVE_MESSAGE_LIST_PAGE_CACHE_GROUP = "leaveMessageList";// 在线留言列表页面缓存Group
	
	private ServletContext servletContext;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public void flushArticleListPageCache(HttpServletRequest request) {
		Cache cache = ServletCacheAdministrator.getInstance(servletContext).getCache(request, PageContext.APPLICATION_SCOPE); 
		cache.flushGroup(ARTICLE_LIST_PAGE_CACHE_GROUP);
	}
	
	public void flushCardsListPageCache(HttpServletRequest request) {
		Cache cache = ServletCacheAdministrator.getInstance(servletContext).getCache(request, PageContext.APPLICATION_SCOPE); 
		cache.flushGroup(CARDS_LIST_PAGE_CACHE_GROUP);
	}
	
	public void flushLeaveMessagePageCache(HttpServletRequest request) {
		Cache cache = ServletCacheAdministrator.getInstance(servletContext).getCache(request, PageContext.APPLICATION_SCOPE); 
		cache.flushGroup(LEAVE_MESSAGE_LIST_PAGE_CACHE_GROUP);
	}
	
	public void flushAllPageCache(HttpServletRequest request) {
		Cache cache = ServletCacheAdministrator.getInstance(servletContext).getCache(request, PageContext.APPLICATION_SCOPE); 
		cache.flushAll(new Date());
	}

}