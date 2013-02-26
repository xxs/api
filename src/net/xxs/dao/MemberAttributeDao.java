package net.xxs.dao;

import java.util.List;

import net.xxs.entity.MemberAttribute;

/**
 * Dao接口 - 会员属性
 */

public interface MemberAttributeDao extends BaseDao<MemberAttribute, String> {
	
	/**
	 * 获取未使用的会员注册项值对象属性序号,若无可用序号则返回null.
	 * 
	 * @return 会员注册项值对象属性序号
	 */
	public Integer getUnusedPropertyIndex();

	/**
	 * 获取会员注册项集合（只包含已启用对象）
	 * 
	 * @return 会员注册项集合
	 */
	public List<MemberAttribute> getMemberAttributeList();

}