package net.xxs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.xxs.bean.Pager;
import net.xxs.dao.WithdrawDao;
import net.xxs.entity.Business;
import net.xxs.entity.Withdraw;
import net.xxs.entity.Withdraw.WithdrawStatus;
import net.xxs.service.WithdrawService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 退货
 */

@Service("withdrawServiceImpl")
public class WithdrawServiceImpl extends BaseServiceImpl<Withdraw, String> implements WithdrawService {
	
	@Resource(name = "withdrawDaoImpl")
	private WithdrawDao withdrawDao;

	@Resource(name = "withdrawDaoImpl")
	public void setBaseDao(WithdrawDao withdrawDao) {
		super.setBaseDao(withdrawDao);
	}
	
	@Transactional(readOnly = true)
	public String getLastWithdrawSn() {
		return withdrawDao.getLastWithdrawSn();
	}

	@Transactional(readOnly = true)
	public Pager getWithdeawPager(Business business, Pager pager) {
		return withdrawDao.getWithdeawPager(business, pager);
	}

	@Transactional(readOnly = true)
	public Long getUnprocessedWithdrawCount() {
		return withdrawDao.getUnprocessedWithdrawCount();
	}
	@Transactional(readOnly = true)
	public List<Withdraw> getApplyWithdrawList(Business business) {
		return withdrawDao.getApplyWithdrawList(business);
	}
	@Transactional(readOnly = true)
	public Pager getWithdrawPager(WithdrawStatus withdrawStatus, Pager pager) {
		return withdrawDao.getWithdrawPager(withdrawStatus, pager);
	}

}