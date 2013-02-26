package net.xxs.service.impl;

import javax.annotation.Resource;

import net.xxs.bean.Pager;
import net.xxs.dao.MemberBusinessDao;
import net.xxs.entity.MemberBusiness;
import net.xxs.entity.MemberBusiness.ResultType;
import net.xxs.service.MemberBusinessService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 商户
 */

@Service("memberBusinessServiceImpl")
public class MemberBusinessServiceImpl extends BaseServiceImpl<MemberBusiness, String> implements MemberBusinessService {
	
	@Resource(name = "memberBusinessDaoImpl")
	MemberBusinessDao memberBusinessDao;

	@Resource(name = "memberBusinessDaoImpl")
	public void setBaseDao(MemberBusinessDao memberBusinessDao) {
		super.setBaseDao(memberBusinessDao);
	}
	
	@Transactional(readOnly = true)
	public Long getUnprocessedMemberBusinessApplyCount() {
		return memberBusinessDao.getUnprocessedMemberBusinessApplyCount();
	}
	
	@Transactional(readOnly = true)
	public Long getUnprocessedMemberBusinessCount() {
		return memberBusinessDao.getUnprocessedMemberBusinessCount();
	}

	public Pager getMemberBusinessPager(ResultType resultType, Pager pager) {
		return memberBusinessDao.getMemberBusinessPager(resultType, pager);
	}

	public boolean isExistByBusinessName(String businessname) {
		return memberBusinessDao.isExistByBusinessName(businessname);
	}

	public boolean isExistByBusinessNumber(String businessNumber) {
		return memberBusinessDao.isExistByBusinessNumber(businessNumber);
	}

	public boolean isExistByUrl(String url) {
		return memberBusinessDao.isExistByUrl(url);
	}

	public boolean isExistByIcp(String icp) {
		return memberBusinessDao.isExistByIcp(icp);
	}

	public String getLastBusinessNumber() {
		return memberBusinessDao.getLastBusinessNumber();
	}

	public Boolean isExistByBusinessNum(String businessNumber) {
		return memberBusinessDao.isExistByBusinessNum(businessNumber);
	}
}