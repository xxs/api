package net.xxs.service;

import java.util.Date;

import net.xxs.bean.Pager;
import net.xxs.entity.Business;
import net.xxs.entity.Business.ResultType;

/**
 * Service接口 - 商户
 */

public interface BusinessService extends BaseService<Business, String> {
	
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

	public Pager getBusinessPager(ResultType resultType,Pager pager);
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
	 * 判断emial是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByEmail(String emial);
	/**
	 * 判断CIP是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByIcp(String icp);
	/**
	 * 判断Email是否存在（不区分大小写）
	 * 
	 */
	public Business getBusinessByEmail(String email);
	/**
	 * 根据用户名、密码验证会员
	 * 
	 * @param username
	 *            用户名
	 *            
	 * @param password
	 *            密码
	 * 
	 * @return 验证是否通过
	 */
	public boolean verifyBusiness(String username, String password);
	/**
	 * 根据密保问题、密保答案验证是否通过
	 * 
	 * @param safeQuestion  密保问题
	 *            
	 * @param safeAnswer    密保答案
	 * 
	 * @return 验证是否通过
	 */
	public boolean verifySafeQuestion(Business business,String safeQuestion, String safeAnswer);
	
	/**
	 * 生成密码找回Key
	 * 
	 * @return 密码找回Key
	 */
	public String buildPasswordRecoverKey();
	
	/**
	 * 根据密码找回Key获取生成日期
	 * 
	 * @return 生成日期
	 */
	public Date getPasswordRecoverKeyBuildDate(String passwordRecoverKey);
	
	
	
	
}