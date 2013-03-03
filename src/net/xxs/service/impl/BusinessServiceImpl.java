package net.xxs.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import net.xxs.bean.Pager;
import net.xxs.dao.BusinessDao;
import net.xxs.entity.Business;
import net.xxs.entity.Business.ResultType;
import net.xxs.service.BusinessService;
import net.xxs.util.CommonUtil;
import net.xxs.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 商户
 */

@Service("memberBusinessServiceImpl")
public class BusinessServiceImpl extends BaseServiceImpl<Business, String> implements BusinessService {
	
	@Resource(name = "businessDaoImpl")
	BusinessDao businessDao;

	@Resource(name = "businessDaoImpl")
	public void setBaseDao(BusinessDao businessDao) {
		super.setBaseDao(businessDao);
	}
	
	@Transactional(readOnly = true)
	public Business getBusinessByUsername(String username) {
		return businessDao.getBusinessByUsername(username);
	}
	
	@Transactional(readOnly = true)
	public Long getUnprocessedMemberBusinessCount() {
		return businessDao.getUnprocessedMemberBusinessCount();
	}

	public Pager getMemberBusinessPager(ResultType resultType, Pager pager) {
		return businessDao.getMemberBusinessPager(resultType, pager);
	}

	public boolean isExistByBusinessName(String businessname) {
		return businessDao.isExistByBusinessName(businessname);
	}

	public boolean isExistByBusinessNumber(String businessNumber) {
		return businessDao.isExistByBusinessNumber(businessNumber);
	}

	public boolean isExistByUrl(String url) {
		return businessDao.isExistByUrl(url);
	}

	public boolean isExistByIcp(String icp) {
		return businessDao.isExistByIcp(icp);
	}

	public String getLastBusinessNumber() {
		return businessDao.getLastBusinessNumber();
	}

	public Boolean isExistByBusinessNum(String businessNumber) {
		return businessDao.isExistByBusinessNum(businessNumber);
	}

	public boolean verifyBusiness(String username, String password) {
		Business business = get(username);
		if (business != null && business.getPassword().equals(StringUtil.md5(password))) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verifySafeQuestion(Business business, String safeQuestion,
			String safeAnswer) {
		if (safeQuestion.equals(business.getSafeQuestion()) && safeAnswer.equals(business.getSafeAnswer())) {
			return true;
		} else {
			return false;
		}
	}

	public String buildPasswordRecoverKey() {
		return System.currentTimeMillis() + Business.PASSWORD_RECOVER_KEY_SEPARATOR + CommonUtil.getUUID() + StringUtil.md5(String.valueOf(Math.random() * 1000000000));
	}

	public Date getPasswordRecoverKeyBuildDate(String passwordRecoverKey) {
		long time = Long.valueOf(StringUtils.substringBefore(passwordRecoverKey, Business.PASSWORD_RECOVER_KEY_SEPARATOR));
		return new Date(time);
	}

}