package net.xxs.action.admin;

import java.io.File;

import javax.annotation.Resource;

import net.xxs.entity.Brand;
import net.xxs.service.BrandService;
import net.xxs.service.CacheService;
import net.xxs.util.ImageUtil;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 品牌
 */

@ParentPackage("admin")
public class BrandAction extends BaseAdminAction {

	private static final long serialVersionUID = 1341979251224008699L;
	
	private Brand brand;
	private File logo;

	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	
	// 添加
	public String add() {
		return INPUT;
	}
	
	// 编辑
	public String edit() {
		brand = brandService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = brandService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		brandService.delete(ids);
		
		cacheService.flushCardsListPageCache(getRequest());
		return ajax(Status.success, "删除成功!");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "brand.name", message = "品牌名称不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "brand.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if (logo != null) {
			String logoPath = ImageUtil.copyImageFile(getServletContext(), logo);
			brand.setLogoPath(logoPath);
		} else {
			brand.setLogoPath(null);
		}
		brandService.save(brand);
		redirectUrl = "brand!list.action";
		return SUCCESS;
	}
	
	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "brand.name", message = "品牌名称不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "brand.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Brand persistent = brandService.load(id);
		if (logo != null) {
			String logoPath = ImageUtil.copyImageFile(getServletContext(), logo);
			persistent.setLogoPath(logoPath);
		}
		BeanUtils.copyProperties(brand, persistent, new String[]{"id", "createDate", "modifyDate", "logoPath", "cardsSet", "cardsTypeSet"});
		brandService.update(persistent);
		
		redirectUrl = "brand!list.action";
		cacheService.flushCardsListPageCache(getRequest());
		return SUCCESS;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
		this.logo = logo;
	}

}