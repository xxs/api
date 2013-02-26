package net.xxs.service;

import net.xxs.bean.Pager;
import net.xxs.entity.MemberBusiness;
import net.xxs.entity.MemberBusiness.ResultType;

/**
 * Service接口 - 商户
 */

public interface MemberBusinessService extends BaseService<MemberBusiness, String> {
	
	public Long getUnprocessedMemberBusinessApplyCount();
	
	public Long getUnprocessedMemberBusinessCount();
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
	public Boolean isExistByBusinessNum(String businessNumber);

	public Pager getMemberBusinessPager(ResultType resultType,Pager pager);
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
}