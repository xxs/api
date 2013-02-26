package net.xxs.service;

import net.xxs.entity.MemberBank;

/**
 * Service接口 - 会员银行卡
 */

public interface MemberBankService extends BaseService<MemberBank, String> {
	
	/**
	 * 根据银行账号判断此账号是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByBankNumber(String banknum);
}