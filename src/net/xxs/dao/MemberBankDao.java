package net.xxs.dao;

import net.xxs.entity.MemberBank;

/**
 * Dao接口 - 会员银行卡
 */

public interface MemberBankDao extends BaseDao<MemberBank, String> {
	/**
	 * 根据银行账号判断此账号是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByBankNumber(String banknum);
}