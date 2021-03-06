package net.xxs.dao.impl;

import java.util.List;

import net.xxs.bean.Pager;
import net.xxs.dao.WithdrawDao;
import net.xxs.entity.Business;
import net.xxs.entity.Withdraw;
import net.xxs.entity.Withdraw.WithdrawStatus;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 提现
 */

@Repository("withdrawDaoImpl")
public class WithdrawDaoImpl extends BaseDaoImpl<Withdraw, String> implements WithdrawDao {
	
	@SuppressWarnings("unchecked")
	public String getLastWithdrawSn() {
		String hql = "from Withdraw as withdraw order by withdraw.createDate desc";
		List<Withdraw> withdrawList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (withdrawList != null && withdrawList.size() > 0) {
			return withdrawList.get(0).getWithdrawSn();
		} else {
			return null;
		}
	}

	public Pager getWithdeawPager(Business business, Pager pager) {
		return super.findPager(pager, Restrictions.eq("business",business ));
	}
	
	public Long getUnprocessedWithdrawCount() {
		String hql = "select count(*) from Withdraw as withdraw where withdraw.withdrawStatus = :withdrawStatus";
		return (Long) getSession().createQuery(hql).setParameter("withdrawStatus", WithdrawStatus.apply).uniqueResult();
	}
	@SuppressWarnings("unchecked")
	public List<Withdraw> getApplyWithdrawList(Business business) {
		String hql = "from Withdraw as withdraw where withdraw.withdrawStatus = :status and withdraw.business = :business order by withdraw.createDate desc";
		return getSession().createQuery(hql).setParameter("status", WithdrawStatus.apply).setParameter("business", business).list();
	}
	public Pager getWithdrawPager(WithdrawStatus withdrawStatus,Pager pager) {
		Criteria criteria = getSession().createCriteria(Withdraw.class);
		if (withdrawStatus != null) {
			criteria.add(Restrictions.eq("withdrawStatus", withdrawStatus));
		}
		return super.findPager(pager, criteria);
	}
}