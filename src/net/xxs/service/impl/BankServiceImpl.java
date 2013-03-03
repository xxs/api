package net.xxs.service.impl;

import javax.annotation.Resource;

import net.xxs.dao.BankDao;
import net.xxs.entity.Bank;
import net.xxs.service.BankService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 会员银行卡
 */

@Service("memberBankServiceImpl")
public class BankServiceImpl extends BaseServiceImpl<Bank, String> implements BankService {
	
	@Resource(name = "memberBankDaoImpl")
	BankDao memberBankDao;

	@Resource(name = "memberBankDaoImpl")
	public void setBaseDao(BankDao memberBankDao) {
		super.setBaseDao(memberBankDao);
	}

	public boolean isExistByBankNumber(String banknum) {
		return memberBankDao.isExistByBankNumber(banknum);
	}
}