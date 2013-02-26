package net.xxs.action.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.xxs.bean.SpecificationValue;
import net.xxs.entity.Cards;
import net.xxs.entity.Specification;
import net.xxs.entity.Specification.SpecificationType;
import net.xxs.service.SpecificationService;
import net.xxs.util.ImageUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 充值卡规格
 */

@ParentPackage("admin")
public class SpecificationAction extends BaseAdminAction {

	private static final long serialVersionUID = -3155241538605530207L;
	
	private Specification specification;
	private List<SpecificationValue> specificationValueList;
	private List<File> specificationValueImageList = new ArrayList<File>();
	
	@Resource(name = "specificationServiceImpl")
	private SpecificationService specificationService;
	
	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		specification = specificationService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = specificationService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		for (String id : ids) {
			Specification specification = specificationService.load(id);
			Set<Cards> cardsSet = specification.getCardsSet();
			if (cardsSet != null && cardsSet.size() > 0) {
				return ajax(Status.error, "规格[" + specification.getName() + "]已被引用,删除失败!");
			}
		}
		specificationService.delete(ids);
		return ajax(Status.success, "删除成功!");
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "specification.name", message = "规格名称不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "specification.specificationType", message = "规格类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "specificationValueList", message = "规格值不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if (specificationValueList.size() == 0) {
			addActionError("请至少添加一个充值卡规格值!");
			return ERROR;
		}
		
		for (int i = 0; i < specificationValueList.size(); i ++) {
			SpecificationValue specificationValue = specificationValueList.get(i);
			if (specificationValue == null) {
				continue;
			}
			if (StringUtils.isEmpty(specificationValue.getName())) {
				addActionError("充值卡规格值名称不允许为空!");
				return ERROR;
			}
			if (specificationValueImageList != null && i < specificationValueImageList.size() && specification.getSpecificationType() == SpecificationType.picture) {
				File specificationValueImage = specificationValueImageList.get(i);
				String specificationValueImagePath = ImageUtil.copyImageFile(getServletContext(), specificationValueImage);
				specificationValue.setImagePath(specificationValueImagePath);
			}
			if (specification.getSpecificationType() == SpecificationType.text) {
				specificationValue.setImagePath(null);
			}
		}
		
		for (Iterator<SpecificationValue> iterator = specificationValueList.iterator(); iterator.hasNext();) {
			SpecificationValue specificationValue = iterator.next();
			if (specificationValue == null) {
				iterator.remove();
			}
		}
		
		specification.setSpecificationValueList(specificationValueList);
		specificationService.save(specification);
		redirectUrl = "specification!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "specification.name", message = "规格名称不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "specification.specificationType", message = "规格类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "specificationValueList", message = "规格值不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		if (specificationValueList.size() == 0) {
			addActionError("请至少添加一个充值卡规格值!");
			return ERROR;
		}
		
		for (int i = 0; i < specificationValueList.size(); i ++) {
			SpecificationValue specificationValue = specificationValueList.get(i);
			if (specificationValue == null) {
				continue;
			}
			if (StringUtils.isEmpty(specificationValue.getName())) {
				addActionError("充值卡规格值名称不允许为空!");
				return ERROR;
			}
			if (specificationValueImageList != null && i < specificationValueImageList.size() && specification.getSpecificationType() == SpecificationType.picture) {
				File specificationValueImage = specificationValueImageList.get(i);
				String specificationValueImagePath = ImageUtil.copyImageFile(getServletContext(), specificationValueImage);
				specificationValue.setImagePath(specificationValueImagePath);
			}
			if (specification.getSpecificationType() == SpecificationType.text) {
				specificationValue.setImagePath(null);
			}
		}
		
		for (Iterator<SpecificationValue> iterator = specificationValueList.iterator(); iterator.hasNext();) {
			SpecificationValue specificationValue = iterator.next();
			if (specificationValue == null) {
				iterator.remove();
			}
		}
		
		Specification persistent = specificationService.load(id);
		specification.setSpecificationValueList(specificationValueList);

		BeanUtils.copyProperties(specification, persistent, new String[] {"id", "createDate", "modifyDate", "cardsSet"});
		specificationService.update(persistent);
		
		redirectUrl = "specification!list.action";
		return SUCCESS;
	}
	
	// 获取充值卡规格类型集合
	public List<SpecificationType> getSpecificationTypeList() {
		return Arrays.asList(SpecificationType.values());
	}

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public List<SpecificationValue> getSpecificationValueList() {
		return specificationValueList;
	}

	public void setSpecificationValueList(List<SpecificationValue> specificationValueList) {
		this.specificationValueList = specificationValueList;
	}

	public List<File> getSpecificationValueImageList() {
		return specificationValueImageList;
	}

	public void setSpecificationValueImageList(List<File> specificationValueImageList) {
		this.specificationValueImageList = specificationValueImageList;
	}

}