package net.xxs.dao.impl;

import java.util.List;

import net.xxs.dao.BankDao;
import net.xxs.entity.Bank;

import org.springframework.stereotype.Repository;


/**
 * Dao实现类 - 会员银行卡信息
 */

@Repository("memberBankDaoImpl")
public class BankDaoImpl extends BaseDaoImpl<Bank, String> implements BankDao {
	
	// 保存时若对象isDefault=true,则设置其它对象isDefault值为false
	@Override
	@SuppressWarnings("unchecked")
	public String save(Bank bank) {
		if (bank.getIsDefault()) {
			String hql = "from Bank as bank where bank.isDefault = :isDefault and bank.business = :business";
			List<Bank> memberBankList = getSession().createQuery(hql).setParameter("isDefault", true).setParameter("business", bank.getBusiness()).list();
			if (memberBankList != null) {
				for (Bank r : memberBankList) {
					r.setIsDefault(false);
				}
			}
		}
		return super.save(bank);
	}

	// 更新时若对象isDefault=true,则设置其它对象isDefault值为false
	@Override
	@SuppressWarnings("unchecked")
	public void update(Bank bank) {
		if (bank.getIsDefault()) {
			String hql = "from Bank as bank where bank.isDefault = :isDefault and bank.business = :business and bank != :bank";
			List<Bank> bankList = getSession().createQuery(hql).setParameter("isDefault", true).setParameter("business", bank.getBusiness()).setParameter("bank", bank).list();
			if (bankList != null) {
				for (Bank r : bankList) {
					r.setIsDefault(false);
				}
			}
		}
		super.update(bank);
	}

	public boolean isExistByBankNumber(String banknum) {
		String hql = "from MemberBank as memberBank where lower(memberBank.banknum) = lower(:banknum)";
		Bank memberBank = (Bank) getSession().createQuery(hql).setParameter("banknum", banknum).uniqueResult();
		if (memberBank != null) {
			return true;
		} else {
			return false;
		}
	}

}