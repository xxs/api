package net.xxs.service;

import java.util.Date;

import net.xxs.entity.Member;

/**
 * Service接口 - 会员
 */

public interface MemberService extends BaseService<Member, String> {
	
	/**
	 * 根据用户名判断此用户是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByUsername(String username);
	
	/**
	 * 判断此推荐人是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByReferrer(String referrer);
	
	/**
	 * 根据用户名获取会员对象,若会员不存在,则返回null（不区分大小写）
	 * 
	 */
	public Member getMemberByUsername(String username);
	
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
	public boolean verifyMember(String username, String password);
	/**
	 * 根据密保问题、密保答案验证是否通过
	 * 
	 * @param safeQuestion  密保问题
	 *            
	 * @param safeAnswer    密保答案
	 * 
	 * @return 验证是否通过
	 */
	public boolean verifyMemberQuestion(Member loginMember,String safeQuestion, String safeAnswer);
	
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