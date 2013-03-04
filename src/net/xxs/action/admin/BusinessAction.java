package net.xxs.action.admin;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import net.xxs.entity.Business;
import net.xxs.entity.Business.BusinessType;
import net.xxs.entity.Business.ResultType;
import net.xxs.service.BusinessService;

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
public class BusinessAction extends BaseAdminAction {


	private static final long serialVersionUID = 2255362894396886832L;

	private Business business;
	
	@Resource(name = "businessServiceImpl")
	private BusinessService businessService;

	// 列表
	public String list() {
		pager = businessService.findPager(pager);
		return LIST;
	}
	// 列表
	public String applying() {
		pager = businessService.getBusinessPager(ResultType.apply, pager);
		return LIST;
	}
	// 添加
	public String add() {
		return INPUT;
	}
	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "business.realName", message = "真实姓名不允许为空!"),
			@RequiredStringValidator(fieldName = "business.businessNumber", message = "身份证号（营业执照号码）不允许为空!"),
			@RequiredStringValidator(fieldName = "business.businessName", message = "商户名称不允许为空!"),
			@RequiredStringValidator(fieldName = "business.city", message = "商户地区不允许为空!"),
			@RequiredStringValidator(fieldName = "business.scope", message = "经营范围不允许为空!"),
			@RequiredStringValidator(fieldName = "business.url", message = "商城网址不允许为空!"),
			@RequiredStringValidator(fieldName = "business.icp", message = "ICP证备案号不允许为空!"),
			@RequiredStringValidator(fieldName = "business.linkMan", message = "联系人不允许为空!"),
			@RequiredStringValidator(fieldName = "business.tel", message = "业务电话不允许为空!"),
			@RequiredStringValidator(fieldName = "business.phone", message = "业务手机不允许为空!"),
			@RequiredStringValidator(fieldName = "business.serverTel", message = "客服热线不允许为空!"),
			@RequiredStringValidator(fieldName = "business.serverTime", message = "客服工作时间不允许为空!"),
			@RequiredStringValidator(fieldName = "business.QQ", message = "即时通讯不允许为空!"),
			@RequiredStringValidator(fieldName = "business.address", message = "通讯地址不允许为空!"),
			@RequiredStringValidator(fieldName = "business.zipcode", message = "邮编不允许为空!")
		},
		requiredFields = { 
				@RequiredFieldValidator(fieldName = "business.businessType", message = "商户类型不允许为空!"),
				@RequiredFieldValidator(fieldName = "business.resultType", message = "审核状态过不允许为空!")
			}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (!businessService.isExistByEmail(business.getEmail())) {
			addActionError("会员名不存在!");
			return ERROR;
		}
		if (businessService.isExistByBusinessNumber(business.getBusinessNumber())) {
			addActionError("身份证号（营业执照）已注册过!");
			return ERROR;
		}
		if (businessService.isExistByBusinessName(business.getBusinessName())) {
			addActionError("商户名称已注册过!");
			return ERROR;
		}
		if (businessService.isExistByUrl(business.getUrl())) {
			addActionError("网址已注册过!");
			return ERROR;
		}
		if (businessService.isExistByIcp(business.getIcp())) {
			addActionError("ICP备案号已注册过!");
			return ERROR;
		}
		businessService.save(business);
		redirectUrl = "member_business!list.action";
		return SUCCESS;
	}
	// 编辑
	public String edit() {
		business = businessService.load(id);
		return INPUT;
	}
	// 更新
	@Validations(
		requiredStrings = { 
				@RequiredStringValidator(fieldName = "business.realName", message = "真实姓名不允许为空!"),
				@RequiredStringValidator(fieldName = "business.businessNumber", message = "身份证号（营业执照号码）不允许为空!"),
				@RequiredStringValidator(fieldName = "business.businessName", message = "商户名称不允许为空!"),
				@RequiredStringValidator(fieldName = "business.city", message = "商户地区不允许为空!"),
				@RequiredStringValidator(fieldName = "business.scope", message = "经营范围不允许为空!"),
				@RequiredStringValidator(fieldName = "business.url", message = "商城网址不允许为空!"),
				@RequiredStringValidator(fieldName = "business.icp", message = "ICP证备案号不允许为空!"),
				@RequiredStringValidator(fieldName = "business.linkMan", message = "联系人不允许为空!"),
				@RequiredStringValidator(fieldName = "business.tel", message = "业务电话不允许为空!"),
				@RequiredStringValidator(fieldName = "business.phone", message = "业务手机不允许为空!"),
				@RequiredStringValidator(fieldName = "business.serverTel", message = "客服热线不允许为空!"),
				@RequiredStringValidator(fieldName = "business.serverTime", message = "客服工作时间不允许为空!"),
				@RequiredStringValidator(fieldName = "business.QQ", message = "即时通讯不允许为空!"),
				@RequiredStringValidator(fieldName = "business.address", message = "通讯地址不允许为空!"),
				@RequiredStringValidator(fieldName = "business.zipcode", message = "邮编不允许为空!")
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "business.businessType", message = "商户类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "business.resultType", message = "审核状态过不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		Business persistent = businessService.get(id);
		BeanUtils.copyProperties(business, persistent, new String[] {"id", "createDate", "modifyDate", "bank","businessType","resultType"});
		businessService.update(persistent);
		redirectUrl = "member_business!list.action";
		return SUCCESS;
	}
	//获取所有的商户类型
	public List<BusinessType> getBusinessTypeList() {
		return Arrays.asList(BusinessType.values());
	}
	//获取所有的商户申请状态
	public List<ResultType> getResultTypeList() {
		return Arrays.asList(ResultType.values());
	}
	public Business getBusiness() {
		return business;
	}
	public void setBusiness(Business business) {
		this.business = business;
	}
	
	
}