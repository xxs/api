package net.xxs.service.impl;

import javax.annotation.Resource;

import net.xxs.bean.Pager;
import net.xxs.dao.LeaveMessageDao;
import net.xxs.entity.LeaveMessage;
import net.xxs.service.LeaveMessageService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 在线留言
 */

@Service("leaveMessageServiceImpl")
public class LeaveMessageServiceImpl extends BaseServiceImpl<LeaveMessage, String> implements LeaveMessageService {
	
	@Resource(name = "leaveMessageDaoImpl")
	private LeaveMessageDao leaveMessageDao;
	
	@Resource(name = "leaveMessageDaoImpl")
	public void setBaseDao(LeaveMessageDao leaveMessageDao) {
		super.setBaseDao(leaveMessageDao);
	}
	
	@Transactional(readOnly = true)
	public Pager getLeaveMessagePager(Pager pager, boolean isContainUnReply) {
		return leaveMessageDao.getLeaveMessagePager(pager, isContainUnReply);
	}

}