package net.xxs.dao.impl;

import java.util.List;

import net.xxs.bean.Pager;
import net.xxs.dao.BusinessDao;
import net.xxs.entity.Business;
import net.xxs.entity.Business.ResultType;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


/**
 * Dao实现类 - 商户
 */

@Repository("businessDaoImpl")
public class BusinessDaoImpl extends BaseDaoImpl<Business, String> implements BusinessDao {

	public Long getUnprocessedMemberBusinessApplyCount() {
		String hql = "select count(*) from Business as business where business.resultType = :resultType";
		return (Long) getSession().createQuery(hql).setParameter("resultType", ResultType.apply).uniqueResult();
	}

	public Long getUnprocessedMemberBusinessCount() {
		String hql = "select count(*) from Business as business where business.resultType = :resultType";
		return (Long) getSession().createQuery(hql).setParameter("resultType", ResultType.success).uniqueResult();
	}
	public Pager getMemberBusinessPager(ResultType resultType,Pager pager) {
		Criteria criteria = getSession().createCriteria(Business.class);
		if (resultType != null) {
			criteria.add(Restrictions.eq("resultType", resultType));
		}
		return super.findPager(pager, criteria);
	}

	public boolean isExistByBusinessName(String businessname) {
		String hql = "from Business as business where lower(business.businessName) = lower(:businessname)";
		Business memberBusiness = (Business) getSession().createQuery(hql).setParameter("businessname", businessname).uniqueResult();
		if (memberBusiness != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isExistByBusinessNumber(String businessNumber) {
		String hql = "from Business as business where lower(business.businessNumber) = lower(:businessNumber)";
		Business memberBusiness = (Business) getSession().createQuery(hql).setParameter("businessNumber", businessNumber).uniqueResult();
		if (memberBusiness != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isExistByUrl(String url) {
		String hql = "from Business as business where lower(business.url) = lower(:url)";
		Business memberBusiness = (Business) getSession().createQuery(hql).setParameter("url", url).uniqueResult();
		if (memberBusiness != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isExistByIcp(String icp) {
		String hql = "from Business as business where lower(business.icp) = lower(:icp)";
		Business memberBusiness = (Business) getSession().createQuery(hql).setParameter("icp", icp).uniqueResult();
		if (memberBusiness != null) {
			return true;
		} else {
			return false;
		}
	}

	public String getLastBusinessNumber() {
		String hql = "from Business as business order by business.createDate desc";
		List<Business> businesseList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (businesseList != null && businesseList.size() > 0) {
			return businesseList.get(0).getBusinessNumber();
		} else {
			return null;
		}
	}

	public Boolean isExistByBusinessNum(String businessNumber) {
		String hql = "from Business as business where lower(business.businessNumber) = lower(:businessNumber)";
		Business business = (Business) getSession().createQuery(hql).setParameter("businessNumber", businessNumber).uniqueResult();
		if (business != null) {
			return true;
		} else {
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	public Business getBusinessByUsername(String username) {
		String hql = "from Business as business where lower(business.username) = lower(:username)";
		return (Business) getSession().createQuery(hql).setParameter("username", username).uniqueResult();
	}
}