package net.xxs.action.admin;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import net.xxs.entity.Member;
import net.xxs.entity.MemberBusiness;
import net.xxs.entity.MemberBusiness.BusinessType;
import net.xxs.entity.MemberBusiness.ResultType;
import net.xxs.service.MemberBusinessService;
import net.xxs.service.MemberService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 商户
 */

@ParentPackage("admin")
public class MemberBusinessAction extends BaseAdminAction {


	private static final long serialVersionUID = 2255362894396886832L;

	private MemberBusiness memberBusiness;
	
	@Resource(name = "memberBusinessServiceImpl")
	private MemberBusinessService memberBusinessService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	// 列表
	public String list() {
		pager = memberBusinessService.findPager(pager);
		return LIST;
	}
	// 列表
	public String applying() {
		pager = memberBusinessService.getMemberBusinessPager(ResultType.apply, pager);
		return LIST;
	}
	// 添加
	public String add() {
		return INPUT;
	}
	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "memberBusiness.realName", message = "真实姓名不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBusiness.businessNumber", message = "身份证号（营业执照号码）不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBusiness.businessName", message = "商户名称不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBusiness.city", message = "商户地区不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBusiness.scope", message = "经营范围不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBusiness.url", message = "商城网址不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBusiness.icp", message = "ICP证备案号不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBusiness.linkMan", message = "联系人不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBusiness.tel", message = "业务电话不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBusiness.phone", message = "业务手机不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBusiness.serverTel", message = "客服热线不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBusiness.serverTime", message = "客服工作时间不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBusiness.QQ", message = "即时通讯不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBusiness.address", message = "通讯地址不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBusiness.zipcode", message = "邮编不允许为空!")
		},
		requiredFields = { 
				@RequiredFieldValidator(fieldName = "memberBusiness.businessType", message = "商户类型不允许为空!"),
				@RequiredFieldValidator(fieldName = "memberBusiness.resultType", message = "审核状态过不允许为空!")
			}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (!memberService.isExistByUsername(memberBusiness.getMember().getUsername())) {
			addActionError("会员名不存在!");
			return ERROR;
		}
		if (memberBusinessService.isExistByBusinessNumber(memberBusiness.getBusinessNumber())) {
			addActionError("身份证号（营业执照）已注册过!");
			return ERROR;
		}
		if (memberBusinessService.isExistByBusinessName(memberBusiness.getBusinessName())) {
			addActionError("商户名称已注册过!");
			return ERROR;
		}
		if (memberBusinessService.isExistByUrl(memberBusiness.getUrl())) {
			addActionError("网址已注册过!");
			return ERROR;
		}
		if (memberBusinessService.isExistByIcp(memberBusiness.getIcp())) {
			addActionError("ICP备案号已注册过!");
			return ERROR;
		}
		Member member = memberService.getMemberByUsername(memberBusiness.getMember().getUsername());
		memberBusiness.setMember(member);
		memberBusinessService.save(memberBusiness);
		redirectUrl = "member_business!list.action";
		return SUCCESS;
	}
	// 编辑
	public String edit() {
		memberBusiness = memberBusinessService.load(id);
		return INPUT;
	}
	// 更新
	@Validations(
		requiredStrings = { 
				@RequiredStringValidator(fieldName = "memberBusiness.realName", message = "真实姓名不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBusiness.businessNumber", message = "身份证号（营业执照号码）不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBusiness.businessName", message = "商户名称不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBusiness.city", message = "商户地区不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBusiness.scope", message = "经营范围不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBusiness.url", message = "商城网址不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBusiness.icp", message = "ICP证备案号不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBusiness.linkMan", message = "联系人不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBusiness.tel", message = "业务电话不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBusiness.phone", message = "业务手机不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBusiness.serverTel", message = "客服热线不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBusiness.serverTime", message = "客服工作时间不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBusiness.QQ", message = "即时通讯不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBusiness.address", message = "通讯地址不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBusiness.zipcode", message = "邮编不允许为空!")
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "memberBusiness.businessType", message = "商户类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "memberBusiness.resultType", message = "审核状态过不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		MemberBusiness persistent = memberBusinessService.get(id);
		BeanUtils.copyProperties(memberBusiness, persistent, new String[] {"id", "createDate", "modifyDate", "member","businessType","resultType"});
		memberBusinessService.update(persistent);
		redirectUrl = "member_business!list.action";
		return SUCCESS;
	}
	public MemberBusiness getMemberBusiness() {
		return memberBusiness;
	}

	public void setMemberBusiness(MemberBusiness memberBusiness) {
		this.memberBusiness = memberBusiness;
	}
	//获取所有的商户类型
	public List<BusinessType> getBusinessTypeList() {
		return Arrays.asList(BusinessType.values());
	}
	//获取所有的商户申请状态
	public List<ResultType> getResultTypeList() {
		return Arrays.asList(ResultType.values());
	}
}