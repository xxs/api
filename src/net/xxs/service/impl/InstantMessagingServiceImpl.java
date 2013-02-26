package net.xxs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.xxs.dao.InstantMessagingDao;
import net.xxs.entity.InstantMessaging;
import net.xxs.entity.InstantMessaging.InstantMessagingType;
import net.xxs.service.InstantMessagingService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 在线客服
 */

@Service("instantMessagingServiceImpl")
public class InstantMessagingServiceImpl extends BaseServiceImpl<InstantMessaging, String> implements InstantMessagingService {

	@Resource(name = "instantMessagingDaoImpl")
	InstantMessagingDao instantMessagingDao;
	
	@Resource(name = "instantMessagingDaoImpl")
	public void setBaseDao(InstantMessagingDao instantMessagingDao) {
		super.setBaseDao(instantMessagingDao);
	}
	
	@Transactional(readOnly = true)
	public List<InstantMessaging> getInstantMessagingList(InstantMessagingType instantMessagingType, Integer maxResults) {
		return instantMessagingDao.getInstantMessagingList(instantMessagingType, maxResults);
	}

}