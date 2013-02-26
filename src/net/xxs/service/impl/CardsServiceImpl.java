package net.xxs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.xxs.bean.Pager;
import net.xxs.bean.Pager.Order;
import net.xxs.dao.CardsDao;
import net.xxs.entity.Brand;
import net.xxs.entity.Cards;
import net.xxs.service.CardsService;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryParser.QueryParser;
import org.compass.core.Compass;
import org.compass.core.CompassHits;
import org.compass.core.CompassQuery;
import org.compass.core.CompassQuery.SortDirection;
import org.compass.core.CompassQuery.SortPropertyType;
import org.compass.core.CompassQueryBuilder;
import org.compass.core.CompassQueryBuilder.CompassBooleanQueryBuilder;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 充值卡
 */

@Service("cardsServiceImpl")
public class CardsServiceImpl extends BaseServiceImpl<Cards, String> implements CardsService {

	@Resource(name = "cardsDaoImpl")
	private CardsDao cardsDao;
	@Resource(name = "compassTemplate")
	private CompassTemplate compassTemplate;

	@Resource(name = "cardsDaoImpl")
	public void setBaseDao(CardsDao cardsDao) {
		super.setBaseDao(cardsDao);
	}
	
	@Transactional(readOnly = true)
	public boolean isExistByCardsSn(String cardsSn) {
		return cardsDao.isExistByCardsSn(cardsSn);
	}
	
	@Transactional(readOnly = true)
	public boolean isUniqueByCardsSn(String oldCardsSn, String newCardsSn) {
		if (StringUtils.equalsIgnoreCase(oldCardsSn, newCardsSn)) {
			return true;
		} else {
			if (cardsDao.isExistByCardsSn(newCardsSn)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	@Cacheable(modelId = "cardsCaching")
	@Transactional(readOnly = true)
	public List<Cards> getCardsList(Integer maxResults) {
		return cardsDao.getCardsList(maxResults);
	}
	
	@Transactional(readOnly = true)
	public List<Cards> getCardsList(Date beginDate, Date endDate, Integer firstResult, Integer maxResults) {
		return cardsDao.getCardsList(beginDate, endDate, firstResult, maxResults);
	}
	
	@Transactional(readOnly = true)
	public Pager getCardsPager(Pager pager) {
		return cardsDao.getCardsPager(pager);
	}
	
	@Transactional(readOnly = true)
	public Pager getCardsPager(Brand brand,Pager pager) {
		return cardsDao.getCardsPager(brand, pager);
	}
	
	@Transactional(readOnly = true)
	public Long getMarketableCardsCount() {
		return cardsDao.getMarketableCardsCount();
	}
	
	@Transactional(readOnly = true)
	public Long getUnMarketableCardsCount() {
		return cardsDao.getUnMarketableCardsCount();
	}
	
	public Pager search(Pager pager) {
		Compass compass = compassTemplate.getCompass();
		CompassSession compassSession = compass.openSession();
		CompassQueryBuilder compassQueryBuilder = compassSession.queryBuilder();
		CompassBooleanQueryBuilder compassBooleanQueryBuilder = compassQueryBuilder.bool();

		compassBooleanQueryBuilder.addMust(compassQueryBuilder.term("isMarketable", true));
		compassBooleanQueryBuilder.addMust(compassQueryBuilder.queryString(QueryParser.escape(pager.getKeyword())).toQuery());
		
		CompassQuery compassQuery = compassBooleanQueryBuilder.toQuery();
		
		String orderBy = pager.getOrderBy();
		Order order = pager.getOrder();
		if (StringUtils.isEmpty(orderBy) || order == null) {
			compassQuery.addSort("isBest", SortPropertyType.STRING, SortDirection.REVERSE);
			compassQuery.addSort("isNew", SortPropertyType.STRING, SortDirection.REVERSE);
			compassQuery.addSort("isHot", SortPropertyType.STRING, SortDirection.REVERSE);
		} else {
			if (StringUtils.equalsIgnoreCase(orderBy, "price")) {
				if (order == Order.asc) {
					compassQuery.addSort("price", SortPropertyType.FLOAT);
				} else {
					compassQuery.addSort("price", SortPropertyType.FLOAT, SortDirection.REVERSE);
				}
			}
		}
		
		CompassHits compassHits = compassQuery.hits();
		List<Cards> result = new ArrayList<Cards>();
		int firstResult = (pager.getPageNumber() - 1) * pager.getPageSize();
		int maxReasults = pager.getPageSize();
		int totalCount = compassHits.length();

		int end = Math.min(totalCount, firstResult + maxReasults);
		for (int i = firstResult; i < end; i++) {
			Cards cards = (Cards) compassHits.data(i);
			result.add(cards);
		}
		compassSession.close();
		pager.setResult(result);
		pager.setTotalCount(totalCount);
		return pager;
	}

	@Override
	@CacheFlush(modelId = "cardsFlushing")
	public void delete(Cards cards) {
		super.delete(cards);
	}

	@Override
	@CacheFlush(modelId = "cardsFlushing")
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@CacheFlush(modelId = "cardsFlushing")
	public void delete(String[] ids) {
		super.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "cardsFlushing")
	public String save(Cards cards) {
		return super.save(cards);
	}

	@Override
	@CacheFlush(modelId = "cardsFlushing")
	public void update(Cards cards) {
		super.update(cards);
	}

}