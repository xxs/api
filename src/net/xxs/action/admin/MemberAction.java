package net.xxs.action.admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.xxs.entity.Deposit;
import net.xxs.entity.Deposit.DepositType;
import net.xxs.entity.Member;
import net.xxs.entity.MemberAttribute;
import net.xxs.entity.MemberAttribute.AttributeType;
import net.xxs.entity.MemberAttribute.SystemAttributeType;
import net.xxs.entity.MemberRank;
import net.xxs.service.DepositService;
import net.xxs.service.MemberAttributeService;
import net.xxs.service.MemberRankService;
import net.xxs.service.MemberService;
import net.xxs.util.CheckUtil;
import net.xxs.util.StringUtil;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 会员
 */

@ParentPackage("admin")
public class MemberAction extends BaseAdminAction {

	private static final long serialVersionUID = -5383463207248344967L;

	private Member member;
	private Map<String, String[]> memberAttributeValueMap;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "memberAttributeServiceImpl")
	private MemberAttributeService memberAttributeService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;

	// 是否已存在 ajax验证
	public String checkUsername() {
		String username = member.getUsername();
		if (memberService.isExistByUsername(username)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}

	// 查看
	public String view() {
		member = memberService.load(id);
		return VIEW;
	}

	// 列表
	public String list() {
		pager = memberService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		StringBuffer logInfoStringBuffer = new StringBuffer("删除会员: ");
		for (String id : ids) {
			Member member = memberService.load(id);
			if (member.getDeposit().compareTo(new BigDecimal(0)) > 0) {
				return ajax(Status.error, "会员[" + member.getUsername() + "]预付款余额不为零,删除失败!");
			}
			logInfoStringBuffer.append(member.getUsername());
		}
		memberService.delete(ids);
		logInfo = logInfoStringBuffer.toString();
		return ajax(Status.success, "删除成功!");
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		member = memberService.load(id);
		return INPUT;
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!"),
			@RequiredStringValidator(fieldName = "member.email", message = "E-mail不允许为空!") 
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "member.score", message = "积分不允许为空!"),
			@RequiredFieldValidator(fieldName = "member.deposit", message = "预存款不允许为空!"),
			@RequiredFieldValidator(fieldName = "member.memberRank.id", message = "会员等级不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "member.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") 
		},
		emails = { 
			@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") 
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "member.score", min = "0", message = "积分必须为零或正整数!")
		},
		regexFields = { 
			@RegexFieldValidator(fieldName = "member.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (memberService.isExistByUsername(member.getUsername())) {
			addActionError("用户名已存在!");
			return ERROR;
		}
		if (memberService.isExistByReferrer(member.getReferrer())) {
			addActionError("推荐人不存在!");
			return ERROR;
		}
		if (member.getDeposit().compareTo(new BigDecimal(0)) < 0) {
			addActionError("预存款不允许小于0");
			return ERROR;
		}
		List<MemberAttribute> memberAttributeList = memberAttributeService.getMemberAttributeList();
//		Pattern numberPattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
//		Pattern alphaintPattern = Pattern.compile("[a-zA-Z]+");
		for (MemberAttribute memberAttribute : memberAttributeList) {
			SystemAttributeType systemAttributeType = memberAttribute.getSystemAttributeType();
			AttributeType attributeType = memberAttribute.getAttributeType();
			
			String[] memberAttributeValues = memberAttributeValueMap.get(memberAttribute.getId());
			String memberAttributeValue = memberAttributeValues == null || memberAttributeValues.length == 0 ? null : memberAttributeValues[0];
			
			if (memberAttribute.getIsRequired() && ArrayUtils.isEmpty(memberAttributeValues)) {
				addActionError(memberAttribute.getName() + "不允许为空!");
				return ERROR;
			}
			
			if (systemAttributeType != null) {
				if (StringUtils.isNotEmpty(memberAttributeValue)) {
						member.setMemberAttributeValue(memberAttribute, memberAttributeValue);
				}
			} else if (attributeType != null) {
				if (attributeType == AttributeType.checkbox) {
					if (!ArrayUtils.isEmpty(memberAttributeValues)) {
						member.setMemberAttributeValue(memberAttribute, memberAttributeValues);
					}
				} else {
					if (StringUtils.isNotEmpty(memberAttributeValue)) {
						if (attributeType == AttributeType.number) {
//							Matcher matcher = CheckUtil.numberPattern().matcher(memberAttributeValue);
							if (!CheckUtil.numberCheck(memberAttributeValue)) {
								addActionError(memberAttribute.getName() + "只允许输入数字!");
								return ERROR;
							}
						} else if (attributeType == AttributeType.alphaint) {
//							Matcher matcher = CheckUtil.alphaintPattern().matcher(memberAttributeValue);
							if (!CheckUtil.alphaintCheck(memberAttributeValue)) {
								addActionError(memberAttribute.getName() + "只允许输入字母!");
								return ERROR;
							}
						}
						member.setMemberAttributeValue(memberAttribute, memberAttributeValue);
					}
				}
			} else {
				addActionError("参数错误!");
				return ERROR;
			}
		}
		member.setUsername(member.getUsername().toLowerCase());
		member.setPassword(StringUtil.md5(member.getPassword()));
		member.setSafeQuestion(null);
		member.setSafeAnswer(null);
		member.setIsAccountLocked(false);
		member.setLoginFailureCount(0);
		member.setLockedDate(null);
		member.setRegisterIp(getRequest().getRemoteAddr());
		member.setLoginIp(null);
		member.setLoginDate(new Date());
		member.setPasswordRecoverKey(null);
		memberService.save(member);
		logInfo = "添加会员: " + member.getUsername();
		
		// 预存款记录
		if (member.getDeposit().compareTo(new BigDecimal(0)) > 0) {
			Deposit deposit = new Deposit();
			deposit.setDepositType(DepositType.adminRecharge);
			deposit.setCredit(member.getDeposit());
			deposit.setDebit(new BigDecimal(0));
			deposit.setBalance(member.getDeposit());
			deposit.setMember(member);
			deposit.setOrder(null);
			System.out.println(deposit.getDepositType());
			System.out.println(deposit.getCredit());
			System.out.println(deposit.getDebit());
			System.out.println(deposit.getBalance());
			System.out.println(deposit.getMember().getUsername());
			System.out.println(deposit.getOrder());
			
			depositService.save(deposit);
		}
		redirectUrl = "member!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "member.email", message = "E-mail不允许为空!") 
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "member.score", message = "积分不允许为空!"),
			@RequiredFieldValidator(fieldName = "member.deposit", message = "预存款不允许为空!"),
			@RequiredFieldValidator(fieldName = "member.memberRank.id", message = "会员等级不允许为空!")
		}, 
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "member.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") 
		}, 
		emails = { 
			@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") 
		}, 
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "member.score", min = "0", message = "积分必须为零或正整数!")
		},
		regexFields = { 
			@RegexFieldValidator(fieldName = "member.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		if (member.getDeposit().compareTo(new BigDecimal(0)) < 0) {
			addActionError("预存款不允许小于0");
			return ERROR;
		}
		List<MemberAttribute> memberAttributeList = memberAttributeService.getMemberAttributeList();
		Pattern numberPattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
		Pattern alphaintPattern = Pattern.compile("[a-zA-Z]+");
		for (MemberAttribute memberAttribute : memberAttributeList) {
			SystemAttributeType systemAttributeType = memberAttribute.getSystemAttributeType();
			AttributeType attributeType = memberAttribute.getAttributeType();
			
			String[] memberAttributeValues = memberAttributeValueMap.get(memberAttribute.getId());
			String memberAttributeValue = memberAttributeValues == null || memberAttributeValues.length == 0 ? null : memberAttributeValues[0];
			
			if (memberAttribute.getIsRequired() && ArrayUtils.isEmpty(memberAttributeValues)) {
				addActionError(memberAttribute.getName() + "不允许为空!");
				return ERROR;
			}
			if (systemAttributeType != null) {
				if (StringUtils.isNotEmpty(memberAttributeValue)) {
						member.setMemberAttributeValue(memberAttribute, memberAttributeValue);
				}
			} else if (attributeType != null) {
				if (attributeType == AttributeType.checkbox) {
					if (!ArrayUtils.isEmpty(memberAttributeValues)) {
						member.setMemberAttributeValue(memberAttribute, memberAttributeValues);
					}
				} else {
					if (StringUtils.isNotEmpty(memberAttributeValue)) {
						if (attributeType == AttributeType.number) {
							Matcher matcher = numberPattern.matcher(memberAttributeValue);
							if (!matcher.matches()) {
								addActionError(memberAttribute.getName() + "只允许输入数字!");
								return ERROR;
							}
						} else if (attributeType == AttributeType.alphaint) {
							Matcher matcher = alphaintPattern.matcher(memberAttributeValue);
							if (!matcher.matches()) {
								addActionError(memberAttribute.getName() + "只允许输入字母!");
								return ERROR;
							}
						}
						member.setMemberAttributeValue(memberAttribute, memberAttributeValue);
					}
				}
			} else {
				addActionError("参数错误!");
				return ERROR;
			}
		}
		Member persistent = memberService.load(id);
		BigDecimal previousDeposit = persistent.getDeposit();
		BigDecimal currentDeposit = member.getDeposit();
		if (StringUtils.isEmpty(member.getPassword())) {
			member.setPassword(persistent.getPassword());
		} else {
			member.setPassword(StringUtil.md5(member.getPassword()));
		}
		BeanUtils.copyProperties(member, persistent, new String[] {"id", "createDate", "modifyDate", "username", "safeQuestion", "safeAnswer", "isAccountLocked", "loginFailureCount", "lockedDate", "registerIp", "loginIp", "loginDate", "passwordRecoverKey", "receiverSet", "favoriteCardsSet", "inboxMessageSet", "outboxMessageSet", "orderSet", "depositSet", "cardsNotifySet" });
		if (!memberService.isExistByReferrer(member.getReferrer())) {
			addActionError("推荐人不存在!");
			return ERROR;
		}
		memberService.update(persistent);
		logInfo = "编辑会员: " + persistent.getUsername();
		
		// 预存款记录
		if (currentDeposit.compareTo(previousDeposit) > 0) {
			Deposit deposit = new Deposit();
			deposit.setDepositType(DepositType.adminRecharge);
			deposit.setCredit(currentDeposit.subtract(previousDeposit));
			deposit.setDebit(new BigDecimal(0));
			deposit.setBalance(currentDeposit);
			deposit.setMember(persistent);
			deposit.setOrder(null);
			
			System.out.println(deposit.getDepositType());
			System.out.println(deposit.getCredit());
			System.out.println(deposit.getDebit());
			System.out.println(deposit.getBalance());
			System.out.println(deposit.getMember().getUsername());
			System.out.println(deposit.getOrder());
			
			depositService.save(deposit);
		} else if (member.getDeposit().compareTo(previousDeposit) < 0) {
			Deposit deposit = new Deposit();
			deposit.setDepositType(DepositType.adminChargeback);
			deposit.setCredit(new BigDecimal(0));
			deposit.setDebit(previousDeposit.subtract(currentDeposit));
			deposit.setBalance(currentDeposit);
			deposit.setMember(persistent);
			deposit.setOrder(null);
			
			System.out.println(deposit.getDepositType());
			System.out.println(deposit.getCredit());
			System.out.println(deposit.getDebit());
			System.out.println(deposit.getBalance());
			System.out.println(deposit.getMember().getUsername());
			System.out.println(deposit.getOrder());
			
			depositService.save(deposit);
		}
		redirectUrl = "member!list.action";
		return SUCCESS;
	}
	
	// 获取会员注册项（只包含已启用注册项）
	public List<MemberAttribute> getMemberAttributeList() {
		return memberAttributeService.getMemberAttributeList();
	}
	
	// 获取所有会员等级
	public List<MemberRank> getAllMemberRankList() {
		return memberRankService.getAllList();
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Map<String, String[]> getMemberAttributeValueMap() {
		return memberAttributeValueMap;
	}

	public void setMemberAttributeValueMap(Map<String, String[]> memberAttributeValueMap) {
		this.memberAttributeValueMap = memberAttributeValueMap;
	}

}