package net.xxs.service;

import net.xxs.entity.MemberRank;

/**
 * Service接口 - 会员分类
 */

public interface MemberRankService extends BaseService<MemberRank, String> {
	
	/**
	 * 判断名称是否存在（不区分大小写）
	 * 
	 * @param name
	 *            名称
	 * 
	 */
	public boolean isExistByName(String name);
	
	/**
	 * 根据名称判断此会员等级是否唯一（不区分大小写）
	 * 
	 */
	public boolean isUniqueByName(String oldName, String newName);
	
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