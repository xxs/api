package net.xxs.dao.impl;

import java.util.Date;

import net.xxs.bean.Pager;
import net.xxs.dao.DepositDao;
import net.xxs.entity.Business;
import net.xxs.entity.Deposit;
import net.xxs.entity.Deposit.DepositType;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 预存款记录
 */

@Repository("depositDaoImpl")
public class DepositDaoImpl extends BaseDaoImpl<Deposit, String> implements DepositDao {

	public Pager getDepositPager(Business business, Pager pager) {
		return super.findPager(pager, Restrictions.eq("business", business));
	}

	public Pager getDepositPager(Business business, Date startDate, Date endDate,
			DepositType depositType, Pager pager) {
		return null;
	}
	
}