package net.xxs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.xxs.dao.FriendLinkDao;
import net.xxs.entity.FriendLink;
import net.xxs.service.FriendLinkService;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 友情链接
 */

@Service("friendLinkServiceImpl")
public class FriendLinkServiceImpl extends BaseServiceImpl<FriendLink, String> implements FriendLinkService {
	
	@Resource(name = "friendLinkDaoImpl")
	FriendLinkDao friendLinkDao;

	@Resource(name = "friendLinkDaoImpl")
	public void setBaseDao(FriendLinkDao friendLinkDao) {
		super.setBaseDao(friendLinkDao);
	}
	
	@Cacheable(modelId = "friendLinkCaching")
	@Transactional(readOnly = true)
	public List<FriendLink> getFriendLinkList(String type, Integer maxResults) {
		List<FriendLink> friendLinkList = friendLinkDao.getFriendLinkList(type, maxResults);
		if (friendLinkList != null) {
			for (FriendLink friendLink : friendLinkList) {
				if (Hibernate.isInitialized(friendLink)) {
					Hibernate.initialize(friendLink);
				}
			}
		}
		return friendLinkList;
	}

	@Override
	@CacheFlush(modelId = "friendLinkFlushing")
	public void delete(FriendLink friendLink) {
		friendLinkDao.delete(friendLink);
	}

	@Override
	@CacheFlush(modelId = "friendLinkFlushing")
	public void delete(String id) {
		friendLinkDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "friendLinkFlushing")
	public void delete(String[] ids) {
		friendLinkDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "friendLinkFlushing")
	public String save(FriendLink friendLink) {
		return friendLinkDao.save(friendLink);
	}

	@Override
	@CacheFlush(modelId = "friendLinkFlushing")
	public void update(FriendLink friendLink) {
		friendLinkDao.update(friendLink);
	}

}