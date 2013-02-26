package net.xxs.action.admin;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.xxs.entity.Member;
import net.xxs.entity.MemberAttribute;
import net.xxs.entity.MemberAttribute.AttributeType;
import net.xxs.service.MemberAttributeService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 会员注册项
 */

@ParentPackage("admin")
public class MemberAttributeAction extends BaseAdminAction {

	private static final long serialVersionUID = -6763618277991640777L;

	private MemberAttribute memberAttribute;
	private List<String> optionList;

	@Resource(name = "memberAttributeServiceImpl")
	private MemberAttributeService memberAttributeService;

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		memberAttribute = memberAttributeService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = memberAttributeService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		for (String id : ids) {
			MemberAttribute memberAttribute = memberAttributeService.load(id);
			if (memberAttribute.getSystemAttributeType() != null) {
				return ajax(Status.error, "不允许删除系统默认注册项,删除失败!");
			}
		}
		memberAttributeService.delete(ids);
		return ajax(Status.success, "删除成功!");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "memberAttribute.name", message = "注册项名称不允许为空!")
		}, 
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "memberAttribute.attributeType", message = "注册项类型不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "memberAttribute.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (memberAttribute.getAttributeType() == AttributeType.select || memberAttribute.getAttributeType() == AttributeType.checkbox) {
			if(optionList == null || optionList.size() < 1) {
				addActionError("请至少填写一个选项内容!");
				return ERROR;
			}
			Iterator<String> iterator = optionList.iterator(); 
			while (iterator.hasNext()) {
				String option = (String) iterator.next();
				if (StringUtils.isEmpty(option)) {
					iterator.remove();
				}
			}
			memberAttribute.setOptionList(optionList);
		} else {
			memberAttribute.setOptionList(null);
		}
		memberAttribute.setSystemAttributeType(null);
		Integer propertyIndex = memberAttributeService.getUnusedPropertyIndex();
		if (propertyIndex == null) {
			addActionError("最多只允许添加" + Member.MEMBER_ATTRIBUTE_VALUE_PROPERTY_COUNT + "个非系统默认会员注册项!");
			return ERROR;
		}
		memberAttribute.setPropertyIndex(propertyIndex);
		memberAttributeService.save(memberAttribute);
		redirectUrl = "member_attribute!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "memberAttribute.name", message = "注册项名称不允许为空!")
		}, 
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "memberAttribute.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		MemberAttribute persistent = memberAttributeService.load(id);
		if (persistent.getSystemAttributeType() != null) {
			BeanUtils.copyProperties(memberAttribute, persistent, new String[] {"id", "createDate", "modifyDate", "systemAttributeType", "attributeType", "optionStore", "propertyIndex"});
		} else if (persistent.getAttributeType() != null) {
			if (persistent.getAttributeType() == AttributeType.select || persistent.getAttributeType() == AttributeType.checkbox) {
				if(optionList == null || optionList.size() < 1) {
					addActionError("请至少填写一个选项内容!");
					return ERROR;
				}
				Iterator<String> iterator = optionList.iterator();
				while (iterator.hasNext()) {
					String option = (String) iterator.next();
					if (StringUtils.isEmpty(option)) {
						iterator.remove();
					}
				}
				memberAttribute.setOptionList(optionList);
			} else {
				memberAttribute.setOptionList(null);
			}
			memberAttribute.setSystemAttributeType(null);
			BeanUtils.copyProperties(memberAttribute, persistent, new String[] {"id", "createDate", "modifyDate", "attributeType", "propertyIndex"});
		} else {
			addActionError("参数错误!");
			return ERROR;
		}
		memberAttributeService.update(persistent);
		redirectUrl = "member_attribute!list.action";
		return SUCCESS;
	}
	
	// 获取注册项类型集合
	public List<AttributeType> getAttributeTypeList() {
		return Arrays.asList(AttributeType.values());
	}

	public MemberAttribute getMemberAttribute() {
		return memberAttribute;
	}

	public void setMemberAttribute(MemberAttribute memberAttribute) {
		this.memberAttribute = memberAttribute;
	}

	public List<String> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<String> optionList) {
		this.optionList = optionList;
	}

}