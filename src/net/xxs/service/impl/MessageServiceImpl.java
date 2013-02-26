package net.xxs.service.impl;

import javax.annotation.Resource;

import net.xxs.bean.Pager;
import net.xxs.dao.MessageDao;
import net.xxs.entity.Member;
import net.xxs.entity.Message;
import net.xxs.service.MessageService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 消息
 */

@Service("messageServiceImpl")
public class MessageServiceImpl extends BaseServiceImpl<Message, String> implements MessageService {
	
	@Resource(name = "messageDaoImpl")
	private MessageDao messageDao;
	
	@Resource(name = "messageDaoImpl")
	public void setBaseDao(MessageDao messageDao) {
		super.setBaseDao(messageDao);
	}
	
	@Transactional(readOnly = true)
	public Pager getMemberInboxPager(Member member, Pager pager) {
		return messageDao.getMemberInboxPager(member, pager);
	}
	
	@Transactional(readOnly = true)
	public Pager getMemberOutboxPager(Member member, Pager pager) {
		return messageDao.getMemberOutboxPager(member, pager);
	}
	
	@Transactional(readOnly = true)
	public Pager getMemberDraftboxPager(Member member, Pager pager) {
		return messageDao.getMemberDraftboxPager(member, pager);
	}
	
	@Transactional(readOnly = true)
	public Pager getAdminInboxPager(Pager pager) {
		return messageDao.getAdminInboxPager(pager);
	}
	
	@Transactional(readOnly = true)
	public Pager getAdminOutboxPager(Pager pager) {
		return messageDao.getAdminOutboxPager(pager);
	}
	
	@Transactional(readOnly = true)
	public Long getUnreadMessageCount(Member member) {
		return messageDao.getUnreadMessageCount(member);
	}
	
	@Transactional(readOnly = true)
	public Long getUnreadMessageCount() {
		return messageDao.getUnreadMessageCount();
	}

}