package net.xxs.action.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.xxs.bean.CardsImage;
import net.xxs.bean.SpecificationValue;
import net.xxs.entity.Brand;
import net.xxs.entity.Cards;
import net.xxs.entity.Order;
import net.xxs.entity.Order.OrderStatus;
import net.xxs.entity.Product;
import net.xxs.entity.Specification;
import net.xxs.service.BrandService;
import net.xxs.service.CacheService;
import net.xxs.service.CardsImageService;
import net.xxs.service.CardsService;
import net.xxs.service.ProductService;
import net.xxs.service.SpecificationService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 充值卡
 */

@ParentPackage("admin")
public class CardsAction extends BaseAdminAction {

	private static final long serialVersionUID = -4433964283757192334L;

	private String brandId;
	private String cardsTypeId;
	
	private Cards cards;
	private Product defaultProduct;
	private List<CardsImage> cardsImageList;
	private List<File> cardsImageFileList;
	private String[] specificationIds;
	private List<Product> productList;
	private List<String[]> specificationValueIdsList; 
	private Map<String, String> cardsAttributeValueMap;
	private Map<String, String> cardsParameterValueMap;

	@Resource(name = "cardsServiceImpl")
	private CardsService cardsService;
	@Resource(name = "specificationServiceImpl")
	private SpecificationService specificationService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "cardsImageServiceImpl")
	private CardsImageService cardsImageService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	
	// AJAX唯一验证
	public String checkCardsSn() {
		String oldCardsSn = getParameter("oldValue");
		String newCardsSn = cards.getCardsSn();
		if (cardsService.isUniqueByCardsSn(oldCardsSn, newCardsSn)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}
	
	// AJAX唯一验证
	public String checkProductSn() {
		String oldProductSn = getParameter("oldValue");
		String newProductSn = defaultProduct.getProductSn();
		if (productService.isUniqueByProductSn(oldProductSn, newProductSn)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}
	
	// AJAX获取充值卡规格值
	@InputConfig(resultName = "ajaxError")
	public String ajaxSpecification() {
		Specification specification = specificationService.load(id);
		return ajax(specification);
	}
	
	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		cards = cardsService.load(id);
		defaultProduct = cards.getDefaultProduct();
		return INPUT;
	}

	// 列表
	public String list() {
		pager = cardsService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() throws Exception {
		StringBuffer logInfoStringBuffer = new StringBuffer("删除充值卡: ");
		for (String id : ids) {
			Cards cards = cardsService.load(id);
			Set<Product> productSet = cards.getProductSet();
			for (Product product : productSet) {
				Set<Order> orderSet = product.getOrderSet();
				for (Order order : orderSet) {
					if (order.getOrderStatus() != OrderStatus.paid && order.getOrderStatus() != OrderStatus.invalid) {
						return ajax(Status.error, "充值卡[" + cards.getName() + "]订单处理未完成,删除失败!");
					}
				}
			}
			logInfoStringBuffer.append(cards.getName() + " ");
		}
		cardsService.delete(ids);
		logInfo = logInfoStringBuffer.toString();
		
		cacheService.flushCardsListPageCache(getRequest());
		
		return ajax(Status.success, "删除成功!");
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "cards.name", message = "充值卡名称不允许为空!"),
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "cards.isMarketable", message = "是否上架不允许为空!"),
			@RequiredFieldValidator(fieldName = "cards.isSpecificationEnabled", message = "是否开启充值卡规格不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (StringUtils.isNotEmpty(cards.getCardsSn()) && cardsService.isExistByCardsSn(cards.getCardsSn())) {
			addActionError("充值卡编号重复,请重新输入!");
			return ERROR;
		}
		
		if (StringUtils.isNotEmpty(brandId)) {
			Brand brand = brandService.load(brandId);
			cards.setBrand(brand);
		} else {
			addActionError("充值卡品牌不能为空!");
			return ERROR;
		}
		
		if (cardsImageList != null) {
			for (int i = 0; i < cardsImageList.size(); i ++) {
				CardsImage cardsImage = cardsImageList.get(i);
				if (cardsImage == null) {
					continue;
				}
				File cardsImageFile = cardsImageFileList.get(i);
				CardsImage g = cardsImageService.buildCardsImage(cardsImageFile);
				cardsImage.setId(g.getId());
				cardsImage.setPath(g.getPath());
				cardsImage.setSourceImageFormatName(g.getSourceImageFormatName());
			}
			
			for (Iterator<CardsImage> iterator = cardsImageList.iterator(); iterator.hasNext();) {
				CardsImage cardsImage = iterator.next();
				if (cardsImage == null) {
					iterator.remove();
				}
			}
			cards.setCardsImageList(cardsImageList);
		} else {
			cards.setCardsImageList(null);
		}
		
		if (cards.getIsSpecificationEnabled()) {
			for (String specificationId : specificationIds) {
				Specification specification = specificationService.load(specificationId);
				cards.getSpecificationSet().add(specification);
			}
		} else {
			cards.setSpecificationSet(null);
		}
		
		cardsService.save(cards);
		logInfo = "添加充值卡: " + cards.getName();
		
		if (cards.getIsSpecificationEnabled()) {
			for (int i = 0; i < productList.size(); i ++) {
				Product product = productList.get(i);
				if (product == null) {
					continue;
				}
				
				List<SpecificationValue> specificationValueList = new ArrayList<SpecificationValue>();
				String[] specificationValueIds = specificationValueIdsList.get(i);
				for (String specificationId : specificationIds) {
					for (String specificationValueId : specificationValueIds) {
						SpecificationValue specificationValue = specificationService.getSpecificationValue(specificationId, specificationValueId);
						if (specificationValue != null) {
							specificationValueList.add(specificationValue);
							break;
						}
					}
				}
				
				product.setCards(cards);
				product.setSpecificationValueList(specificationValueList);
				productService.save(product);
			}
		} else {
			defaultProduct.setCards(cards);
			defaultProduct.setSpecificationValueList(null);
			productService.save(defaultProduct);
		}
		
		cacheService.flushCardsListPageCache(getRequest());
		
		redirectUrl = "cards!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "cards.name", message = "充值卡名称不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "cards.isMarketable", message = "是否上架不允许为空!"),
			@RequiredFieldValidator(fieldName = "cards.isSpecificationEnabled", message = "是否开启充值卡规格不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Cards persistent = cardsService.load(id);
		
		if (StringUtils.isNotEmpty(cards.getCardsSn()) && !cardsService.isUniqueByCardsSn(persistent.getCardsSn(), cards.getCardsSn())) {
			addActionError("充值卡编号重复,请重新输入!");
			return ERROR;
		}
		
		if (cards.getIsSpecificationEnabled()) {
			if (persistent.getIsSpecificationEnabled()) {
				for (Product product : persistent.getProductSet()) {
					if(!productList.contains(product)) {
						Set<Order> orderSet = product.getOrderSet();
						for (Order order : orderSet) {
							if (order.getOrderStatus() != OrderStatus.paid && order.getOrderStatus() != OrderStatus.invalid) {
								addActionError("货品[" + product.getName() + "]订单处理未完成,删除失败!");
								return ERROR;
							}
						}
					}
				}
			} else {
				for (Product product : persistent.getProductSet()) {
					Set<Order> orderSet = product.getOrderSet();
					for (Order order : orderSet) {
						if (order.getOrderStatus() != OrderStatus.paid && order.getOrderStatus() != OrderStatus.invalid) {
							addActionError("货品[" + product.getName() + "]订单处理未完成,删除失败!");
							return ERROR;
						}
					}
				}
			}
		} else {
			if (persistent.getIsSpecificationEnabled()) {
				for (Product product : persistent.getProductSet()) {
					Set<Order> orderSet = product.getOrderSet();
					for (Order order : orderSet) {
						if (order.getOrderStatus() != OrderStatus.paid && order.getOrderStatus() != OrderStatus.invalid) {
							addActionError("货品[" + product.getName() + "]订单处理未完成,删除失败!");
							return ERROR;
						}
					}
				}
			}
		}
		
		if (StringUtils.isNotEmpty(brandId)) {
			Brand brand = brandService.load(brandId);
			cards.setBrand(brand);
		} else {
			cards.setBrand(null);
		}
		
		if (cardsImageList != null) {
			for (int i = 0; i < cardsImageList.size(); i ++) {
				CardsImage cardsImage = cardsImageList.get(i);
				if (cardsImage == null) {
					continue;
				}
				if (StringUtils.isEmpty(cardsImage.getId())) {
					File cardsImageFile = cardsImageFileList.get(i);
					CardsImage g = cardsImageService.buildCardsImage(cardsImageFile);
					cardsImage.setId(g.getId());
					cardsImage.setPath(g.getPath());
					cardsImage.setSourceImageFormatName(g.getSourceImageFormatName());
				}
			}
			
			for (Iterator<CardsImage> iterator = cardsImageList.iterator(); iterator.hasNext();) {
				CardsImage cardsImage = iterator.next();
				if (cardsImage == null) {
					iterator.remove();
				}
			}
			cards.setCardsImageList(cardsImageList);
		} else {
			cards.setCardsImageList(null);
		}
		
		if (cards.getIsSpecificationEnabled()) {
			for (String specificationId : specificationIds) {
				Specification specification = specificationService.load(specificationId);
				cards.getSpecificationSet().add(specification);
			}
		} else {
			cards.setSpecificationSet(null);
		}
		
		if (cards.getIsSpecificationEnabled()) {
			if (persistent.getIsSpecificationEnabled()) {
				for (Product product : persistent.getProductSet()) {
					if(!productList.contains(product)) {
						productService.delete(product);
					}
				}
			} else {
				for (Product product : persistent.getProductSet()) {
					productService.delete(product);
				}
			}
		} else {
			if (persistent.getIsSpecificationEnabled()) {
				for (Product product : persistent.getProductSet()) {
					productService.delete(product);
				}
			}
		}
		
		BeanUtils.copyProperties(cards, persistent, new String[] {"id", "createDate", "modifyDate", "freezeStore", "productSet"});
		cardsService.update(persistent);
		logInfo = "编辑充值卡: " + cards.getName();
		
		if (cards.getIsSpecificationEnabled()) {
			for (int i = 0; i < productList.size(); i ++) {
				Product product = productList.get(i);
				if (product == null) {
					continue;
				}
				
				List<SpecificationValue> specificationValueList = new ArrayList<SpecificationValue>();
				String[] specificationValueIds = specificationValueIdsList.get(i);
				for (String specificationId : specificationIds) {
					for (String specificationValueId : specificationValueIds) {
						SpecificationValue specificationValue = specificationService.getSpecificationValue(specificationId, specificationValueId);
						if (specificationValue != null) {
							specificationValueList.add(specificationValue);
							break;
						}
					}
				}
				
				product.setCards(persistent);
				product.setSpecificationValueList(specificationValueList);
				
				if (StringUtils.isEmpty(product.getId())) {
					productService.save(product);
				} else {
					Product persistentProduct = productService.load(product.getId());
					BeanUtils.copyProperties(product, persistentProduct, new String[] {"id", "createDate", "modifyDate", "freezeStore"});
					productService.update(persistentProduct);
				}
			}
		} else {
			defaultProduct.setCards(persistent);
			defaultProduct.setSpecificationValueList(null);
			
			if (StringUtils.isEmpty(defaultProduct.getId())) {
				productService.save(defaultProduct);
			} else {
				Product persistentProduct = productService.load(defaultProduct.getId());
				BeanUtils.copyProperties(defaultProduct, persistentProduct, new String[] {"id", "createDate", "modifyDate", "freezeStore"});
				productService.update(persistentProduct);
			}
		}
		
		cacheService.flushCardsListPageCache(getRequest());
		
		redirectUrl = "cards!list.action";
		return SUCCESS;
	}
	
	// 获取所有品牌
	public List<Brand> getAllBrandList() {
		return brandService.getAllList();
	}
	
	// 获取所有充值卡规格
	public List<Specification> getAllSpecificationList() {
		return specificationService.getAllList();
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getCardsTypeId() {
		return cardsTypeId;
	}

	public void setCardsTypeId(String cardsTypeId) {
		this.cardsTypeId = cardsTypeId;
	}

	public Cards getCards() {
		return cards;
	}

	public void setCards(Cards cards) {
		this.cards = cards;
	}

	public Product getDefaultProduct() {
		return defaultProduct;
	}

	public void setDefaultProduct(Product defaultProduct) {
		this.defaultProduct = defaultProduct;
	}

	public List<CardsImage> getCardsImageList() {
		return cardsImageList;
	}

	public void setCardsImageList(List<CardsImage> cardsImageList) {
		this.cardsImageList = cardsImageList;
	}

	public List<File> getCardsImageFileList() {
		return cardsImageFileList;
	}

	public void setCardsImageFileList(List<File> cardsImageFileList) {
		this.cardsImageFileList = cardsImageFileList;
	}

	public String[] getSpecificationIds() {
		return specificationIds;
	}

	public void setSpecificationIds(String[] specificationIds) {
		this.specificationIds = specificationIds;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public List<String[]> getSpecificationValueIdsList() {
		return specificationValueIdsList;
	}

	public void setSpecificationValueIdsList(List<String[]> specificationValueIdsList) {
		this.specificationValueIdsList = specificationValueIdsList;
	}

	public Map<String, String> getCardsAttributeValueMap() {
		return cardsAttributeValueMap;
	}

	public void setCardsAttributeValueMap(Map<String, String> cardsAttributeValueMap) {
		this.cardsAttributeValueMap = cardsAttributeValueMap;
	}

	public Map<String, String> getCardsParameterValueMap() {
		return cardsParameterValueMap;
	}

	public void setCardsParameterValueMap(Map<String, String> cardsParameterValueMap) {
		this.cardsParameterValueMap = cardsParameterValueMap;
	}

}