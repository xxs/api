package net.xxs.dao.impl;

import net.xxs.bean.Pager;
import net.xxs.dao.MessageDao;
import net.xxs.entity.Member;
import net.xxs.entity.Message;
import net.xxs.entity.Message.DeleteStatus;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 消息
 */

@Repository("messageDaoImpl")
public class MessageDaoImpl extends BaseDaoImpl<Message, String> implements MessageDao{
	
	public Pager getMemberInboxPager(Member member, Pager pager) {
		return super.findPager(pager, Restrictions.eq("toMember", member), Restrictions.eq("isSaveDraftbox", false), Restrictions.ne("deleteStatus", DeleteStatus.toDelete));
	}
	
	public Pager getMemberOutboxPager(Member member, Pager pager) {
		return super.findPager(pager, Restrictions.eq("fromMember", member), Restrictions.eq("isSaveDraftbox", false), Restrictions.ne("deleteStatus", DeleteStatus.fromDelete));
	}
	
	public Pager getMemberDraftboxPager(Member member, Pager pager) {
		return super.findPager(pager, Restrictions.eq("fromMember", member), Restrictions.eq("isSaveDraftbox", true), Restrictions.ne("deleteStatus", DeleteStatus.fromDelete));
	}
	
	public Pager getAdminInboxPager(Pager pager) {
		return super.findPager(pager, Restrictions.isNull("toMember"), Restrictions.eq("isSaveDraftbox", false), Restrictions.ne("deleteStatus", DeleteStatus.toDelete));
	}
	
	public Pager getAdminOutboxPager(Pager pager) {
		return super.findPager(pager, Restrictions.isNull("fromMember"), Restrictions.eq("isSaveDraftbox", false), Restrictions.ne("deleteStatus", DeleteStatus.fromDelete));
	}
	
	public Long getUnreadMessageCount(Member member) {
		String hql = "select count(*) from Message as message where message.toMember = :toMember and message.isRead = :isRead and message.isSaveDraftbox = :isSaveDraftbox and message.deleteStatus != :deleteStatus";
		return (Long) getSession().createQuery(hql).setParameter("toMember", member).setParameter("isRead", false).setParameter("isSaveDraftbox", false).setParameter("deleteStatus", DeleteStatus.toDelete).uniqueResult();
	}
	
	public Long getUnreadMessageCount() {
		String hql = "select count(*) from Message as message where message.toMember is null and message.isRead = :isRead and message.isSaveDraftbox = :isSaveDraftbox and message.deleteStatus != :deleteStatus";
		return (Long) getSession().createQuery(hql).setParameter("isRead", false).setParameter("isSaveDraftbox", false).setParameter("deleteStatus", DeleteStatus.toDelete).uniqueResult();
	}

}