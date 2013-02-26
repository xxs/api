package net.xxs.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import net.xxs.bean.Pager;
import net.xxs.dao.CardsDao;
import net.xxs.entity.Brand;
import net.xxs.entity.Cards;
import net.xxs.entity.Order;
import net.xxs.entity.Product;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 充值卡
 */

@Repository("cardsDaoImpl")
public class CardsDaoImpl extends BaseDaoImpl<Cards, String> implements
		CardsDao {

	public boolean isExistByCardsSn(String cardsSn) {
		String hql = "from Cards as cards where lower(cards.cardsSn) = lower(:cardsSn)";
		Cards cards = (Cards) getSession().createQuery(hql)
				.setParameter("cardsSn", cardsSn).uniqueResult();
		if (cards != null) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cards> getCardsList(Integer maxResults) {
		Query query = null;

		String hql = "from Cards as cards where cards.isMarketable = :isMarketable order by cards.orderList asc";
		query = getSession().createQuery(hql);
		query.setParameter("isMarketable", true);
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Cards> getCardsList(Date beginDate, Date endDate,
			Integer firstResult, Integer maxResults) {
		Query query = null;
		if (beginDate != null && endDate == null) {
			String hql = "from Cards as cards where cards.isMarketable = :isMarketable and cards.createDate > :beginDate order by cards.createDate desc";
			query = getSession().createQuery(hql);
			query.setParameter("isMarketable", true).setParameter("beginDate",
					beginDate);
		} else if (endDate != null && beginDate == null) {
			String hql = "from Cards as cards where cards.isMarketable = :isMarketable and cards.createDate < :endDate order by cards.createDate desc";
			query = getSession().createQuery(hql);
			query.setParameter("isMarketable", true).setParameter("endDate",
					endDate);
		} else if (endDate != null && beginDate != null) {
			String hql = "from Cards as cards where cards.isMarketable = :isMarketable and cards.createDate > :beginDate and cards.createDate < :endDate order by cards.createDate desc";
			query = getSession().createQuery(hql);
			query.setParameter("isMarketable", true)
					.setParameter("beginDate", beginDate)
					.setParameter("endDate", endDate);
		} else {
			String hql = "from Cards as cards where cards.isMarketable = :isMarketable order by cards.createDate desc";
			query = getSession().createQuery(hql);
			query.setParameter("isMarketable", true);
		}
		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}

	public Pager getCardsPager(Pager pager) {
		Criteria criteria = getSession().createCriteria(Cards.class);
		return super.findPager(pager, criteria);
	}

	public Pager getCardsPager(Brand brand,Pager pager) {
		Criteria criteria = getSession().createCriteria(Cards.class);
		if (brand != null) {
			criteria.add(Restrictions.eq("brand", brand));
		}
		return super.findPager(pager, criteria);
	}

	public Long getMarketableCardsCount() {
		String hql = "select count(*) from Cards as cards where cards.isMarketable = :isMarketable";
		return (Long) getSession().createQuery(hql)
				.setParameter("isMarketable", true).uniqueResult();
	}

	public Long getUnMarketableCardsCount() {
		String hql = "select count(*) from Cards as cards where cards.isMarketable = :isMarketable";
		return (Long) getSession().createQuery(hql)
				.setParameter("isMarketable", false).uniqueResult();
	}

	// 关联处理
	@Override
	public void delete(Cards cards) {
		Set<Product> productSet = cards.getProductSet();
		for (Product product : productSet) {
			Set<Order> orderSet = product.getOrderSet();
			if (orderSet != null) {
				for (Order order : orderSet) {
					order.setProduct(null);
				}
			}
		}
		super.delete(cards);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Cards cards = load(id);
		this.delete(cards);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Cards cards = load(id);
			this.delete(cards);
		}
	}

}