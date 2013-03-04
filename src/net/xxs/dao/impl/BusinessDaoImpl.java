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

	public Long getUnprocessedBusinessApplyCount() {
		String hql = "select count(*) from Business as business where business.resultType = :resultType";
		return (Long) getSession().createQuery(hql).setParameter("resultType", ResultType.apply).uniqueResult();
	}

	public Long getUnprocessedBusinessCount() {
		String hql = "select count(*) from Business as business where business.resultType = :resultType";
		return (Long) getSession().createQuery(hql).setParameter("resultType", ResultType.success).uniqueResult();
	}
	public Pager getBusinessPager(ResultType resultType,Pager pager) {
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

	public boolean isExistByBusinessNum(String businessNumber) {
		String hql = "from Business as business where lower(business.businessNumber) = lower(:businessNumber)";
		Business business = (Business) getSession().createQuery(hql).setParameter("businessNumber", businessNumber).uniqueResult();
		if (business != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isExistByEmail(String email) {
		String hql = "from Business as business where lower(business.email) = lower(:email)";
		Business business = (Business) getSession().createQuery(hql).setParameter("email", email).uniqueResult();
		if (business != null) {
			return true;
		} else {
			return false;
		} 
	}
	public Business getBusinessByEmail(String email) {
		String hql = "from Business as business where lower(business.email) = lower(:email)";
		return (Business) getSession().createQuery(hql).setParameter("email", email).uniqueResult();
	}
}