package net.xxs.action.card;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.xxs.entity.Member;
import net.xxs.entity.MemberAttribute;
import net.xxs.entity.MemberAttribute.AttributeType;
import net.xxs.entity.MemberAttribute.SystemAttributeType;
import net.xxs.service.MemberAttributeService;
import net.xxs.service.MemberService;
import net.xxs.util.StringUtil;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 个人信息
 */

@ParentPackage("card")
@InterceptorRefs({
	@InterceptorRef(value = "memberVerifyInterceptor"),
	@InterceptorRef(value = "cardStack")
})
public class ProfileAction extends BaseCardAction {

	private static final long serialVersionUID = -7704084885878684413L;
	
	private Member member;
	private Map<String, String[]> memberAttributeValueMap;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberAttributeServiceImpl")
	private MemberAttributeService memberAttributeService;
	
	// 编辑
	public String edit() {
		member = getLoginMember();
		return INPUT;
	}
	
	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "member.email", message = "E-mail不允许为空!") 
		}, 
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") 
		}, 
		emails = { 
			@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") 
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
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
		Member persistent = getLoginMember();
		if (StringUtils.isNotEmpty(member.getPassword())) {
			String passwordMd5 = StringUtil.md5(member.getPassword());
			persistent.setPassword(passwordMd5);
		}
		BeanUtils.copyProperties(member, persistent, new String[] {"id", "createDate", "modifyDate", "username", "password", "safeQuestion", "safeAnswer", "score", "deposit", "isAccountEnabled", "isAccountLocked", "loginFailureCount", "lockedDate", "registerIp", "loginIp", "loginDate", "passwordRecoverKey", "memberRank", "receiverSet", "favoriteProductSet", "cartItemSet", "inboxMessageSet", "outboxMessageSet", "orderSet", "depositSet" });
		if (!memberService.isExistByReferrer(member.getReferrer())) {
			addActionError("推荐人不存在!");
			return ERROR;
		}
		memberService.update(persistent);
		redirectUrl = "profile!edit.action";
		return SUCCESS;
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

	// 获取会员注册项（只包含已启用注册项）
	public List<MemberAttribute> getMemberAttributeList() {
		return memberAttributeService.getMemberAttributeList();
	}

}