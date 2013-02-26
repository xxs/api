package net.xxs.dao.impl;

import java.util.List;

import net.xxs.dao.InstantMessagingDao;
import net.xxs.entity.InstantMessaging;
import net.xxs.entity.InstantMessaging.InstantMessagingType;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 在线客服
 */

@Repository("instantMessagingDaoImpl")
public class InstantMessagingDaoImpl extends BaseDaoImpl<InstantMessaging, String> implements InstantMessagingDao {
	
	@SuppressWarnings("unchecked")
	public List<InstantMessaging> getInstantMessagingList(InstantMessagingType instantMessagingType, Integer maxResults) {
		Query query = null;
		if (instantMessagingType != null) {
			String hql = "from InstantMessaging as instantMessaging where instantMessaging.instantMessagingType = :instantMessagingType order by instantMessaging.orderList asc";
			query = getSession().createQuery(hql);
			query.setParameter("instantMessagingType", instantMessagingType);
		} else {
			String hql = "from InstantMessaging as instantMessaging order by instantMessaging.orderList asc";
			query = getSession().createQuery(hql);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}

}