package net.xxs.dao.impl;

import java.util.List;

import net.xxs.dao.FriendLinkDao;
import net.xxs.entity.FriendLink;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 友情链接
 */

@Repository("friendLinkDaoImpl")
public class FriendLinkDaoImpl extends BaseDaoImpl<FriendLink, String> implements FriendLinkDao {
	
	@SuppressWarnings("unchecked")
	public List<FriendLink> getFriendLinkList(String type, Integer maxResults) {
		Query query = null;
		if (StringUtils.equalsIgnoreCase(type, "picture")) {
			String hql = "from FriendLink as friendLink where friendLink.logoPath is not null order by friendLink.orderList asc";
			query = getSession().createQuery(hql);
		} else if (StringUtils.equalsIgnoreCase(type, "text")) {
			String hql = "from FriendLink as friendLink where friendLink.logoPath is null order by friendLink.orderList asc";
			query = getSession().createQuery(hql);
		} else {
			String hql = "from FriendLink as friendLink order by friendLink.orderList asc";
			query = getSession().createQuery(hql);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
}