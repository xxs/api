package net.xxs.dao;

import net.xxs.entity.MemberRank;

/**
 * Dao接口 - 会员分类
 */

public interface MemberRankDao extends BaseDao<MemberRank, String> {
	
	/**
	 * 根据名称判断会员等级是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByName(String name);
	
	/**
	 * 获取默认会员等级,若无默认会员等级,则获取最先添加的会员等级
	 * 
	 */
	public MemberRank getDefaultMemberRank();
	
	/**
	 * 根据积分获取会员等级,若不存在则返回null
	 * 
	 */
	public MemberRank getMemberRankByScore(Integer score);
	
	/**
	 * 根据积分获取符合此积分条件的最高会员等级,若不存在则返回null
	 * 
	 */
	public MemberRank getUpMemberRankByScore(Integer score);

}