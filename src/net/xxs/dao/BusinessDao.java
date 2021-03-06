package net.xxs.dao;

import net.xxs.bean.Pager;
import net.xxs.entity.Business;
import net.xxs.entity.Business.ResultType;

/**
 * Dao接口 - 商户
 */

public interface BusinessDao extends BaseDao<Business, String> {
	
	public Long getUnprocessedBusinessApplyCount();
	
	public Long getUnprocessedBusinessCount();
	
	public Pager getBusinessPager(ResultType resultType,Pager pager);
	
	/**
	 * 获取最后生成的商户编号
	 * 
	 * @return 商户编号
	 */
	public String getLastBusinessNumber();
	/**
	 * 判断商户编号是否存在
	 * 
	 * @return 商户编号
	 */
	public boolean isExistByBusinessNum(String businessNumber);
	/**
	 * 判断商户名称是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByBusinessName(String businessname);
	/**
	 * 判断身份证（营业执照是否存在）（不区分大小写）
	 * 
	 */
	public boolean isExistByBusinessNumber(String businessNumber);
	/**
	 * 判断Url是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByUrl(String url);
	/**
	 * 判断CIP是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByIcp(String icp);
	/**
	 * 判断Email是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByEmail(String email);
	/**
	 * 判断Email是否存在（不区分大小写）
	 * 
	 */
	public Business getBusinessByEmail(String email);
}