package net.xxs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.xxs.bean.Pager;
import net.xxs.dao.ArticleDao;
import net.xxs.entity.Article;
import net.xxs.entity.ArticleCategory;
import net.xxs.service.ArticleService;

import org.apache.lucene.queryParser.QueryParser;
import org.compass.core.Compass;
import org.compass.core.CompassHits;
import org.compass.core.CompassQuery;
import org.compass.core.CompassQueryBuilder;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.compass.core.CompassQuery.SortDirection;
import org.compass.core.CompassQuery.SortPropertyType;
import org.compass.core.CompassQueryBuilder.CompassBooleanQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * Service实现类 - 文章
 */

@Service("articleServiceImpl")
public class ArticleServiceImpl extends BaseServiceImpl<Article, String> implements ArticleService {
	
	private long refreshTime = System.currentTimeMillis();

	@Resource(name = "articleDaoImpl")
	private ArticleDao articleDao;
	@Resource(name = "compassTemplate")
	private CompassTemplate compassTemplate;
	@Resource(name = "cacheManager")
	private GeneralCacheAdministrator cacheManager;

	@Resource(name = "articleDaoImpl")
	public void setBaseDao(ArticleDao articleDao) {
		super.setBaseDao(articleDao);
	}

	@Cacheable(modelId = "articleCaching")
	@Transactional(readOnly = true)
	public List<Article> getArticleList(ArticleCategory articleCategory, String type, boolean isContainChildren, Integer maxResults) {
		return articleDao.getArticleList(articleCategory, type, isContainChildren, maxResults);
	}
	
	@Transactional(readOnly = true)
	public List<Article> getArticleList(ArticleCategory articleCategory, Date beginDate, Date endDate, Integer firstResult, Integer maxResults) {
		return articleDao.getArticleList(articleCategory, beginDate, endDate, firstResult, maxResults);
	}
	
	@Transactional(readOnly = true)
	public Pager getArticlePager(ArticleCategory articleCategory, Pager pager) {
		return articleDao.getArticlePager(articleCategory, pager);
	}
	
	public Pager search(Pager pager) {
		Compass compass = compassTemplate.getCompass();
		CompassSession compassSession = compass.openSession();
		CompassQueryBuilder compassQueryBuilder = compassSession.queryBuilder();
		CompassBooleanQueryBuilder compassBooleanQueryBuilder = compassQueryBuilder.bool();
		
		compassBooleanQueryBuilder.addMust(compassQueryBuilder.term("isPublication", true));
		compassBooleanQueryBuilder.addMust(compassQueryBuilder.queryString(QueryParser.escape(pager.getKeyword())).toQuery());
		
		CompassQuery compassQuery = compassBooleanQueryBuilder.toQuery();
		compassQuery.addSort("isTop", SortPropertyType.STRING, SortDirection.REVERSE);
		compassQuery.addSort("hits", SortPropertyType.STRING, SortDirection.REVERSE);
		CompassHits compassHits = compassQuery.hits();
		
		List<Article> result = new ArrayList<Article>();
		int firstResult = (pager.getPageNumber() - 1) * pager.getPageSize();
		int maxReasults = pager.getPageSize();
		int totalCount = compassHits.length();
		
		int end = Math.min(totalCount, firstResult + maxReasults);
		for (int i = firstResult; i < end; i++) {
			Article article = (Article) compassHits.data(i);
			result.add(article);
		}
		compassSession.close();
		pager.setResult(result);
		pager.setTotalCount(totalCount);
		return pager;
	}
	
	@Transactional
	public Integer viewHits(String id) {
		String cacheKey = Article.ARTICLE_HITS_CACHE_KEY_PREFIX + id;
		Integer hits = null;
		boolean updateSucceeded = false;
		try {
			hits = (Integer) cacheManager.getFromCache(cacheKey) + 1;
			cacheManager.putInCache(cacheKey, hits);
			updateSucceeded = true;
			
			long currentTime = System.currentTimeMillis();
			if (currentTime > refreshTime + Article.ARTICLE_HITS_CACHE_TIME * 1000) {
				Article article = articleDao.load(id);
				article.setHits(hits);
				articleDao.update(article);
				refreshTime = currentTime;
			}
		} catch (NeedsRefreshException needsRefreshException) {
			Article article = articleDao.load(id);
			hits = article.getHits() + 1;
			cacheManager.putInCache(cacheKey, hits);
			updateSucceeded = true;
		} finally {
            if (!updateSucceeded) {
            	cacheManager.cancelUpdate(cacheKey);
            }
        }
		return hits;
	}

	@Override
	@CacheFlush(modelId = "articleFlushing")
	public void delete(Article article) {
		super.delete(article);
	}

	@Override
	@CacheFlush(modelId = "articleFlushing")
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@CacheFlush(modelId = "articleFlushing")
	public void delete(String[] ids) {
		super.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "articleFlushing")
	public String save(Article article) {
		return super.save(article);
	}

	@Override
	@CacheFlush(modelId = "articleFlushing")
	public void update(Article article) {
		super.update(article);
	}
	
}