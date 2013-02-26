package net.xxs.service;

import java.util.Date;
import java.util.List;

import net.xxs.bean.Pager;
import net.xxs.entity.Brand;
import net.xxs.entity.Cards;

/**
 * Service接口 - 充值卡
 */

public interface CardsService extends BaseService<Cards, String> {
	
	/**
	 * 判断充值卡编号是否存在（不区分大小写）
	 * 
	 * @param cardsSn
	 *            充值卡编号
	 */
	public boolean isExistByCardsSn(String cardsSn);
	
	/**
	 * 判断充值卡编号是否唯一（不区分大小写）
	 * 
	 * @param oldCardsSn
	 *            旧充值卡编号
	 *            
	 * @param newCardsSn
	 *            新充值卡编号
	 * 
	 */
	public boolean isUniqueByCardsSn(String oldCardsSn, String newCardsSn);
	
	/**
	 * 根据最大结果数获取充值卡集合（只包含isMarketable=true的对象）
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 此分类下的充值卡集合
	 */
	public List<Cards> getCardsList(Integer maxResults);
	
	/**
	 * 根据起始日期、结束日期、起始结果数、最大结果数获取充值卡集合（只包含isMarketable=true的对象,包含子分类充值卡）
	 * 
	 * @param beginDate
	 *            起始日期,null表示无限制
	 *            
	 * @param endDate
	 *            结束日期,null表示无限制
	 *            
	 * @param firstResult
	 *            起始结果数,null表示无限制
	 *            
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 此分类下的所有充值卡集合
	 */
	public List<Cards> getCardsList(Date beginDate, Date endDate, Integer firstResult, Integer maxResults);
	
	/**
	 * 根据和Pager对象,获取充值卡分页对象（只包含isMarketable=true的对象,包含子分类充值卡）
	 * 
	 * @param pager
	 *            分页对象
	 * 
	 * @return Pager
	 */
	public Pager getCardsPager(Pager pager);
	
	/**
	 * 根据充值卡品牌、Pager,获取此分类下的充值卡分页对象（只包含isMarketable=true的对象,包含子分类充值卡）
	 * 
	 * @param brand
	 *            充值卡品牌,null表示无限制
	 *            
	 * @param pager
	 *            分页对象
	 * 
	 * @return Pager
	 */
	public Pager getCardsPager(Brand brand, Pager pager);
	
	/**
	 * 根据分页对象搜索文章
	 * 
	 * @param pager
	 *            分页对象
	 * 
	 * @return 分页对象
	 */
	public Pager search(Pager pager);
	
	/**
	 * 获取已上架充值卡数
	 *            
	 * @return 已上架充值卡数
	 */
	public Long getMarketableCardsCount();
	
	/**
	 * 获取已下架充值卡数
	 *            
	 * @return 已下架充值卡数
	 */
	public Long getUnMarketableCardsCount();

}