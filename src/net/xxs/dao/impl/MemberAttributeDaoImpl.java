package net.xxs.dao.impl;

import java.util.ArrayList;
import java.util.List;

import net.xxs.dao.MemberAttributeDao;
import net.xxs.entity.Member;
import net.xxs.entity.MemberAttribute;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 会员属性
 */

@Repository("memberAttributeDaoImpl")
public class MemberAttributeDaoImpl extends BaseDaoImpl<MemberAttribute, String> implements MemberAttributeDao {
	
	@SuppressWarnings("unchecked")
	public Integer getUnusedPropertyIndex() {
		String hql = "from MemberAttribute as memberAttribute where memberAttribute.systemAttributeType is null";
		List<MemberAttribute> memberAttributeList = getSession().createQuery(hql).list();
		if (memberAttributeList != null && memberAttributeList.size() >= Member.MEMBER_ATTRIBUTE_VALUE_PROPERTY_COUNT) {
			return null;
		}
		List<Integer> usedPropertyIndexList = new ArrayList<Integer>();
		for (MemberAttribute memberAttribute : memberAttributeList) {
			usedPropertyIndexList.add(memberAttribute.getPropertyIndex());
		}
		for (int i = 0; i < Member.MEMBER_ATTRIBUTE_VALUE_PROPERTY_COUNT; i ++) {
			if (!usedPropertyIndexList.contains(new Integer(i))) {
				return i;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<MemberAttribute> getMemberAttributeList() {
		String hql = "from MemberAttribute as memberAttribute where memberAttribute.isEnabled = :isEnabled order by memberAttribute.orderList asc";
		return getSession().createQuery(hql).setParameter("isEnabled", true).list();
	}
	
	// 删除的同时清除会员对应注册项值
	@Override
	public void delete(MemberAttribute memberAttribute) {
		String propertyName = Member.MEMBER_ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
		String hql = "update Member set " + propertyName + " = :propertyName";
		getSession().createQuery(hql).setParameter("propertyName", null).executeUpdate();
		super.delete(memberAttribute);
	}

	// 删除的同时清除会员对应注册项值
	@Override
	public void delete(String id) {
		MemberAttribute memberAttribute = load(id);
		this.delete(memberAttribute);
	}

	// 删除的同时清除会员对应注册项值
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

}