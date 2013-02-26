package net.xxs.dao;

import java.util.List;

import net.xxs.entity.InstantMessaging;
import net.xxs.entity.InstantMessaging.InstantMessagingType;

/**
 * Dao接口 - 在线客服
 */

public interface InstantMessagingDao extends BaseDao<InstantMessaging, String> {

	/**
	 * 根据在线客服类型、最大结果数获取在线客服集合
	 *            
	 * @param instantMessagingType
	 *            在线客服类型,null表示无限制
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 在线客服集合
	 */
	public List<InstantMessaging> getInstantMessagingList(InstantMessagingType instantMessagingType, Integer maxResults);

}