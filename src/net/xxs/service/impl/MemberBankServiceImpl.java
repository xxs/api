package net.xxs.service.impl;

import javax.annotation.Resource;

import net.xxs.dao.MemberBankDao;
import net.xxs.entity.MemberBank;
import net.xxs.service.MemberBankService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 会员银行卡
 */

@Service("memberBankServiceImpl")
public class MemberBankServiceImpl extends BaseServiceImpl<MemberBank, String> implements MemberBankService {
	
	@Resource(name = "memberBankDaoImpl")
	MemberBankDao memberBankDao;

	@Resource(name = "memberBankDaoImpl")
	public void setBaseDao(MemberBankDao memberBankDao) {
		super.setBaseDao(memberBankDao);
	}

	public boolean isExistByBankNumber(String banknum) {
		return memberBankDao.isExistByBankNumber(banknum);
	}
}