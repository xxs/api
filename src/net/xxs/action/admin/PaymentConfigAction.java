package net.xxs.action.admin;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.xxs.entity.Brand;
import net.xxs.entity.PaymentConfig;
import net.xxs.entity.PaymentConfig.PaymentConfigType;
import net.xxs.entity.PaymentConfig.PaymentFeeType;
import net.xxs.entity.PaymentDiscount;
import net.xxs.payment.BasePaymentProduct;
import net.xxs.service.BrandService;
import net.xxs.service.PaymentConfigService;
import net.xxs.service.PaymentDiscountService;
import net.xxs.util.PaymentProductUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 支付方式
 */

@ParentPackage("admin")
public class PaymentConfigAction extends BaseAdminAction {

	private static final long serialVersionUID = 3562311377613294892L;
	
	private PaymentConfig paymentConfig;
	private PaymentDiscount paymentDiscount;
	private BasePaymentProduct paymentProduct;
	private Set<PaymentDiscount> paymentDiscountSet;
	private List<Brand> brandList;
	
	@Resource(name = "paymentConfigServiceImpl")
	private PaymentConfigService paymentConfigService;
	@Resource(name = "paymentDiscountServiceImpl")
	private PaymentDiscountService paymentDiscountService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	
	// 列表
	public String list() {
		pager = paymentConfigService.findPager(pager);
		return LIST;
	}
	
	// 删除
	public String delete() {
		long totalCount = paymentConfigService.getTotalCount();
		if (ids.length >= totalCount) {
			return ajax(Status.error, "删除失败,必须保留至少一个支付方式!");
		}
		paymentConfigService.delete(ids);
		return ajax(Status.success, "删除成功!");
	}
	
	// 选择支付方式
	public String select() {
		return "select";
	}
	
	// 添加
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentConfigType", message = "支付配置类型不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String add() {
		if (paymentConfig.getPaymentConfigType() == PaymentConfigType.online) {
			paymentProduct = PaymentProductUtil.getPaymentProduct(paymentConfig.getPaymentProductId());
			if (paymentProduct == null) {
				addActionError("支付产品配置不存在!");
				return ERROR;
			}
		}
		return INPUT;
	}
	// 添加通道
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "paymentConfig.id", message = "支付配置主键不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String addDiscount() {
		paymentConfig = paymentConfigService.load(paymentConfig.getId());
		if (paymentConfig == null) {
			addActionError("此配置不存在!");
			return ERROR;
		}
		brandList = brandService.getAllList();
		return "inputDiscount";
	}	
	
	// 编辑
	public String edit() {
		paymentConfig = paymentConfigService.load(id);
		if (paymentConfig.getPaymentConfigType() == PaymentConfigType.online) {
			paymentProduct = PaymentProductUtil.getPaymentProduct(paymentConfig.getPaymentProductId());
			if (paymentProduct == null) {
				addActionError("支付产品配置不存在!");
				return ERROR;
			}
		}
		return INPUT;
	}
	
	// 通道管理
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "id", message = "支付配置主键不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String editDiscount() {
		paymentConfig = paymentConfigService.load(id);
		paymentDiscountSet = paymentConfig.getPaymentDiscountSet();
		return "listDiscount";
	}	
	
	// 编辑通道明细
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "id", message = "支付通道配置主键不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String editDiscountDetail() {
		paymentDiscount = paymentDiscountService.load(id);
		paymentConfig = paymentDiscount.getPaymentConfig();
		brandList = brandService.getAllList();
		return "inputDiscount";
	}	
	
	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "paymentConfig.name", message = "支付方式名称不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentConfigType", message = "支付方式类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentFeeType", message = "支付手续费设置不允许为空!"),
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentFee", message = "支付手续费不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "paymentConfig.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (paymentConfig.getPaymentFee().compareTo(new BigDecimal(0)) < 0) {
			addActionError("支付手续费金额不允许小于0!");
			return ERROR;
		}
		if (paymentConfig.getPaymentConfigType() == PaymentConfigType.online) {
			paymentProduct = PaymentProductUtil.getPaymentProduct(paymentConfig.getPaymentProductId());
			if (paymentProduct == null) {
				addActionError("支付产品配置不存在!");
				return ERROR;
			}
			if (StringUtils.isEmpty(paymentConfig.getBargainorId())) {
				addActionError(paymentProduct.getBargainorIdName() + "不允许为空!");
				return ERROR;
			}
			if (StringUtils.isEmpty(paymentConfig.getBargainorKey())) {
				addActionError(paymentProduct.getBargainorKeyName() + "不允许为空!");
				return ERROR;
			}
		} else {
			paymentConfig.setPaymentProductId(null);
			paymentConfig.setBargainorId(null);
			paymentConfig.setBargainorKey(null);
		}
		paymentConfigService.save(paymentConfig);
		redirectUrl = "payment_config!list.action";
		return SUCCESS;
	}
	// 保存通道
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "paymentDiscount.paymentConfig.id", message = "支付方式主键不允许为空!"),
			@RequiredStringValidator(fieldName = "paymentDiscount.brand.id", message = "品牌主键不允许为空!"),
			@RequiredStringValidator(fieldName = "paymentDiscount.code", message = "通道编码不允许为空!")
			
		}
	)
	@InputConfig(resultName = "error")
	public String saveDiscount() {
		System.out.println(paymentDiscount.getPaymentConfig().getId());
		System.out.println(paymentDiscount.getBrand().getId());
		System.out.println(paymentDiscount.getMemo());
		System.out.println(paymentDiscount.getDiscount());
		System.out.println(paymentDiscount.getCode());
		paymentDiscount.setOperator("admin");
		paymentDiscountService.save(paymentDiscount);
		System.out.println("id:"+paymentDiscount.getPaymentConfig().getId());
		redirectUrl = "payment_config!editDiscount.action?id="+paymentDiscount.getPaymentConfig().getId();
		return SUCCESS;
	}
	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "paymentConfig.name", message = "支付方式名称不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentConfigType", message = "支付方式类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentFeeType", message = "支付手续费设置不允许为空!"),
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentFee", message = "支付手续费不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "paymentConfig.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		if (paymentConfig.getPaymentFee().compareTo(new BigDecimal(0)) < 0) {
			addActionError("支付手续费金额不允许小于0!");
			return ERROR;
		}
		PaymentConfig persistent = paymentConfigService.load(id);
		if (persistent.getPaymentConfigType() == PaymentConfigType.online) {
			paymentProduct = PaymentProductUtil.getPaymentProduct(paymentConfig.getPaymentProductId());
			if (paymentProduct == null) {
				addActionError("支付产品配置不存在!");
				return ERROR;
			}
			if (StringUtils.isEmpty(paymentConfig.getBargainorId())) {
				addActionError(paymentProduct.getBargainorIdName() + "不允许为空!");
				return ERROR;
			}
			if (StringUtils.isEmpty(paymentConfig.getBargainorKey())) {
				addActionError(paymentProduct.getBargainorKeyName() + "不允许为空!");
				return ERROR;
			}
		} else {
			paymentConfig.setPaymentProductId(null);
			paymentConfig.setBargainorId(null);
			paymentConfig.setBargainorKey(null);
		}
		BeanUtils.copyProperties(paymentConfig, persistent, new String[] {"id", "createDate", "modifyDate", "paymentConfigType", "paymentProductId"});
		paymentConfigService.update(persistent);
		redirectUrl = "payment_config!list.action";
		return SUCCESS;
	}
	// 更新通道
	@Validations(
		requiredStrings = {
				@RequiredStringValidator(fieldName = "paymentDiscount.paymentConfig.id", message = "支付方式主键不允许为空!"),
				@RequiredStringValidator(fieldName = "paymentDiscount.brand.id", message = "品牌主键不允许为空!"),
				@RequiredStringValidator(fieldName = "paymentDiscount.code", message = "通道编码不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String updateDiscount() {
		PaymentDiscount persistent = paymentDiscountService.load(id);
		System.out.println(paymentDiscount.getId());
		System.out.println(paymentDiscount.getPaymentConfig().getId());
		System.out.println(paymentDiscount.getBrand().getId());
		System.out.println(paymentDiscount.getMemo());
		System.out.println(paymentDiscount.getDiscount());
		System.out.println(paymentDiscount.getCode());
		System.out.println(paymentDiscount.getModifyDate());
		System.out.println(paymentDiscount.getFace());
		System.out.println("-------------");
		System.out.println(persistent.getId());
		System.out.println(persistent.getPaymentConfig().getId());
		System.out.println(persistent.getBrand().getId());
		System.out.println(persistent.getMemo());
		System.out.println(persistent.getDiscount());
		System.out.println(persistent.getCode());
		System.out.println(persistent.getModifyDate());
		System.out.println(persistent.getFace());
		BeanUtils.copyProperties(paymentDiscount, persistent, new String[] {"id", "createDate"});
		System.out.println("-------------");
		System.out.println(persistent.getId());
		System.out.println(persistent.getPaymentConfig().getId());
		System.out.println(persistent.getBrand().getId());
		System.out.println(persistent.getMemo());
		System.out.println(persistent.getDiscount());
		System.out.println(persistent.getCode());
		System.out.println(persistent.getModifyDate());
		System.out.println(persistent.getFace());
		paymentDiscountService.update(persistent);
		redirectUrl = "payment_config!editDiscount.action?id="+paymentDiscount.getPaymentConfig().getId();
		return SUCCESS;
	}
	// 获取支付手续费类型集合
	public List<PaymentFeeType> getPaymentFeeTypeList() {
		return Arrays.asList(PaymentFeeType.values());
	}
	
	// 获取所有支付产品集合
	public List<BasePaymentProduct> getPaymentProductList() {
		return PaymentProductUtil.getPaymentProductList();
	}

	public PaymentConfig getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(PaymentConfig paymentConfig) {
		this.paymentConfig = paymentConfig;
	}

	public BasePaymentProduct getPaymentProduct() {
		return paymentProduct;
	}

	public void setPaymentProduct(BasePaymentProduct paymentProduct) {
		this.paymentProduct = paymentProduct;
	}

	public Set<PaymentDiscount> getPaymentDiscountSet() {
		return paymentDiscountSet;
	}

	public void setPaymentDiscountSet(Set<PaymentDiscount> paymentDiscountSet) {
		this.paymentDiscountSet = paymentDiscountSet;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}

	public PaymentDiscount getPaymentDiscount() {
		return paymentDiscount;
	}

	public void setPaymentDiscount(PaymentDiscount paymentDiscount) {
		this.paymentDiscount = paymentDiscount;
	}

}