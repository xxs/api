package net.xxs.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import net.xxs.bean.Pager;
import net.xxs.dao.DepositDao;
import net.xxs.entity.Deposit;
import net.xxs.entity.Deposit.DepositType;
import net.xxs.entity.Member;
import net.xxs.service.DepositService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 预存款记录
 */

@Service("depositServiceImpl")
public class DepositServiceImpl extends BaseServiceImpl<Deposit, String> implements DepositService {

	@Resource(name = "depositDaoImpl")
	private DepositDao depositDao;
	
	@Resource(name = "depositDaoImpl")
	public void setBaseDao(DepositDao depositDao) {
		super.setBaseDao(depositDao);
	}
	
	@Transactional(readOnly = true)
	public Pager getDepositPager(Member member, Pager pager) {
		return depositDao.getDepositPager(member, pager);
	}

	public Pager getDepositPager(Member member, Date startDate, Date endDate,
			DepositType depositType, Pager pager) {
		return depositDao.getDepositPager(member,startDate,endDate,depositType, pager);
	}

}