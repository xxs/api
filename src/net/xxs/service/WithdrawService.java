package net.xxs.service;

import java.util.List;

import net.xxs.bean.Pager;
import net.xxs.entity.Business;
import net.xxs.entity.Withdraw;
import net.xxs.entity.Withdraw.WithdrawStatus;

/**
 * Service接口 - 提现
 */

public interface WithdrawService extends BaseService<Withdraw, String> {

	/**
	 * 获取最后生成的提现编号
	 * 
	 * @return 提现编号
	 */
	public String getLastWithdrawSn();
	
	/**
	 * 根据Member、Pager获取提现记录分页对象
	 * 
	 * @param member
	 *            Member对象
	 *            
	 * @param pager
	 *            Pager对象
	 *            
	 * @return 提现记录分页对象
	 */
	public Pager getWithdeawPager(Business business, Pager pager);
	
	/**
	 * 获取未处理的提现单
	 *            
	 * @return 未处理
	 */
	public Long getUnprocessedWithdrawCount();
	/**
	 * 获取未处理的提现单
	 *            
	 * @return 未处理
	 */
	public List<Withdraw> getApplyWithdrawList(Business business);

	public Pager getWithdrawPager(WithdrawStatus withdrawStatus,Pager pager);
}