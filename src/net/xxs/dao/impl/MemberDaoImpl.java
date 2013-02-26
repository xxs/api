package net.xxs.dao.impl;

import java.util.Set;

import net.xxs.dao.MemberDao;
import net.xxs.entity.Member;
import net.xxs.entity.Order;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 会员
 */

@Repository("memberDaoImpl")
public class MemberDaoImpl extends BaseDaoImpl<Member, String> implements MemberDao {

	@SuppressWarnings("unchecked")
	public boolean isExistByUsername(String username) {
		String hql = "from Member as members where lower(members.username) = lower(:username)";
		Member member = (Member) getSession().createQuery(hql).setParameter("username", username).uniqueResult();
		if (member != null) {
			return true;
		} else {
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	public boolean isExistByReferrer(String referrer) {
		String hql = "from Member as members where lower(members.username) = lower(:referrer)";
		Member member = (Member) getSession().createQuery(hql).setParameter("referrer", referrer).uniqueResult();
		if (member != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Member getMemberByUsername(String username) {
		String hql = "from Member as members where lower(members.username) = lower(:username)";
		return (Member) getSession().createQuery(hql).setParameter("username", username).uniqueResult();
	}
	
	// 关联处理
	@Override
	public void delete(Member member) {
		Set<Order> orderSet = member.getOrderSet();
		if (orderSet != null) {
			for (Order order : orderSet) {
				order.setMember(null);
			}
		}
		
		super.delete(member);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Member member = load(id);
		this.delete(member);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Member member = load(id);
			this.delete(member);
		}
	}

}