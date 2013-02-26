package net.xxs.dao.impl;

import net.xxs.bean.Pager;
import net.xxs.dao.LeaveMessageDao;
import net.xxs.entity.LeaveMessage;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 在线留言
 */

@Repository("leaveMessageDaoImpl")
public class LeaveMessageDaoImpl extends BaseDaoImpl<LeaveMessage, String> implements LeaveMessageDao{

	public Pager getLeaveMessagePager(Pager pager, boolean isContainUnReply) {
		if (isContainUnReply) {
			return super.findPager(pager, Restrictions.isNull("forLeaveMessage"));
		} else {
			return super.findPager(pager, Restrictions.isNull("forLeaveMessage"), Restrictions.isNotEmpty("replyLeaveMessageSet"));
		}
	}
	
}