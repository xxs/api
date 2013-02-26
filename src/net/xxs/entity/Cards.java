package net.xxs.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import net.xxs.bean.CardsImage;
import net.xxs.bean.PageTemplateConfig;
import net.xxs.bean.Setting;
import net.xxs.bean.SpecificationValue;
import net.xxs.util.JsonUtil;
import net.xxs.util.ReflectionUtil;
import net.xxs.util.SerialNumberUtil;
import net.xxs.util.SettingUtil;
import net.xxs.util.TemplateConfigUtil;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;
import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 充值卡
 */

@Entity
@Searchable
public class Cards extends BaseEntity {

	private static final long serialVersionUID = 8394952361534286179L;
	
	public static final int DEFAULT_CARDS_LIST_PAGE_SIZE = 12;// 充值卡列表默认每页显示数
	public static final int CARDS_ATTRIBUTE_VALUE_PROPERTY_COUNT = 20;// 充值卡属性值对象属性个数
	public static final String CARDS_ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "cardsAttributeValue";// 充值卡属性值对象属性名称前缀
	
	private String cardsSn;// 充值卡编号
	private String name;// 充值卡名称
	private BigDecimal price;// 销售价
	private Integer score;// 积分
	private Integer orderList;// 排序
	private Boolean isMarketable;// 是否上架
	private Boolean isBest;// 是否为精品充值卡
	private Boolean isNew;// 是否为新品充值卡
	private Boolean isHot;// 是否为热销充值卡
	private Boolean isSpecificationEnabled;// 是否启用充值卡规格
	private String introduction;// 介绍
	private String metaKeywords;// 页面关键词
	private String metaDescription;// 页面描述
	private String htmlPath;// HTML静态文件路径
	private String cardsImageStore;// 充值卡图片存储
	
	private Brand brand;// 品牌
	
	private Set<Specification> specificationSet = new HashSet<Specification>();// 规格
	private Set<Product> productSet = new LinkedHashSet<Product>();// 货品

	@Column(nullable = false, unique = true)
	public String getCardsSn() {
		return cardsSn;
	}

	public void setCardsSn(String cardsSn) {
		this.cardsSn = cardsSn;
	}

	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false, precision = 15, scale = 5)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = SettingUtil.setPriceScale(price);
	}
	
	@Column(nullable = false)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	
	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public Boolean getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public Boolean getIsBest() {
		return isBest;
	}

	public void setIsBest(Boolean isBest) {
		this.isBest = isBest;
	}

	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	@Column(nullable = false)
	public Boolean getIsSpecificationEnabled() {
		return isSpecificationEnabled;
	}

	public void setIsSpecificationEnabled(Boolean isSpecificationEnabled) {
		this.isSpecificationEnabled = isSpecificationEnabled;
	}

	@Lob
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@Column(length = 3000)
	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	@Column(length = 3000)
	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	@SearchableProperty(store = Store.YES, index = Index.NO)
	@Column(nullable = false, updatable = false)
	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	@SearchableProperty(store = Store.YES, index = Index.NO)
	@Column(length = 3000)
	public String getCardsImageStore() {
		return cardsImageStore;
	}

	public void setCardsImageStore(String cardsImageStore) {
		this.cardsImageStore = cardsImageStore;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_cards_brand")
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("orderList asc")
	@ForeignKey(name = "fk_specification_set")
	public Set<Specification> getSpecificationSet() {
		return specificationSet;
	}

	public void setSpecificationSet(Set<Specification> specificationSet) {
		this.specificationSet = specificationSet;
	}

	@OneToMany(mappedBy = "cards", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("price asc")
	public Set<Product> getProductSet() {
		return productSet;
	}

	public void setProductSet(Set<Product> productSet) {
		this.productSet = productSet;
	}

	
	// 获取充值卡图片
	@Transient
	public List<CardsImage> getCardsImageList() {
		if (StringUtils.isEmpty(cardsImageStore)) {
			return null;
		}
		try {
			return JsonUtil.toObject(cardsImageStore, new TypeReference<List<CardsImage>>() {});
		} catch (Exception e) {
			return null;
		}
	}
	
	// 设置充值卡图片
	@Transient
	public void setCardsImageList(List<CardsImage> cardsImageList) {
		if (cardsImageList == null || cardsImageList.size() == 0) {
			cardsImageStore = null;
			return;
		}
		Collections.sort(cardsImageList);
		cardsImageStore = JsonUtil.toJson(cardsImageList);
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (price == null || price.compareTo(new BigDecimal(0)) < 0) {
			price = new BigDecimal(0);
		}
		if (score == null || score < 0) {
			score = 0;
		}
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
		if (isMarketable == null) {
			isMarketable = false;
		}
		if (isBest == null) {
			isBest = false;
		}
		if (isNew == null) {
			isNew = false;
		}
		if (isHot == null) {
			isHot = false;
		}
		if (isSpecificationEnabled == null) {
			isSpecificationEnabled = false;
		}
		if (StringUtils.isEmpty(cardsSn)) {
			cardsSn = SerialNumberUtil.buildCardsSn();
		}
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.CARDS_CONTENT);
		htmlPath = pageTemplateConfig.getHtmlRealPath();
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (price == null || price.compareTo(new BigDecimal(0)) < 0) {
			price = new BigDecimal(0);
		}
		if (score == null || score < 0) {
			score = 0;
		}
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
		if (isMarketable == null) {
			isMarketable = false;
		}
		if (isBest == null) {
			isBest = false;
		}
		if (isNew == null) {
			isNew = false;
		}
		if (isHot == null) {
			isHot = false;
		}
		if (isSpecificationEnabled == null) {
			isSpecificationEnabled = false;
		}
		if (StringUtils.isEmpty(cardsSn)) {
			cardsSn = SerialNumberUtil.buildCardsSn();
		}
	}
	
	// 获取默认货品
	@Transient
	public Product getDefaultProduct() {
		if (isSpecificationEnabled) {
			for (Product product : productSet) {
				if (product.getIsDefault()) {
					return product;
				}
			}
		} else {
			for (Product product : productSet) {
				return product;
			}
		}
		return null;
	}
	
	// 获取默认充值卡图片（大）
	@Transient
	public String getDefaultBigCardsImagePath() {
		List<CardsImage> cardsImageList = getCardsImageList();
		if (cardsImageList != null && cardsImageList.size() > 0) {
			return cardsImageList.get(0).getBigImagePath();
		} else {
			Setting setting = SettingUtil.getSetting();
			return setting.getDefaultBigCardsImagePath();
		}
	}
	
	// 获取默认充值卡图片（小）
	@Transient
	public String getDefaultSmallCardsImagePath() {
		List<CardsImage> cardsImageList = getCardsImageList();
		if (cardsImageList != null && cardsImageList.size() > 0) {
			return cardsImageList.get(0).getSmallImagePath();
		} else {
			Setting setting = SettingUtil.getSetting();
			return setting.getDefaultSmallCardsImagePath();
		}
	}
	
	// 获取默认充值卡图片（缩略图）
	@Transient
	public String getDefaultThumbnailCardsImagePath() {
		List<CardsImage> cardsImageList = getCardsImageList();
		if (cardsImageList != null && cardsImageList.size() > 0) {
			return cardsImageList.get(0).getThumbnailImagePath();
		} else {
			Setting setting = SettingUtil.getSetting();
			return setting.getDefaultThumbnailCardsImagePath();
		}
	}
	
	// 获取充值卡规格值
	@Transient
	public Set<SpecificationValue> getSpecificationValueSet() {
		Set<SpecificationValue> specificationValueSet = new HashSet<SpecificationValue>();
		for (Product product : productSet) {
			if (product.getIsMarketable()) {
				specificationValueSet.addAll(product.getSpecificationValueList());
			}
		}
		return specificationValueSet;
	}
	
	// 设置充值卡属性值为NULL
	@Transient
	public void setCardsAttributeValueToNull() {
		for (int i = 0; i < CARDS_ATTRIBUTE_VALUE_PROPERTY_COUNT; i ++) {
			String propertyName = CARDS_ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
			ReflectionUtil.invokeSetterMethod(this, propertyName, null, String.class);
		}
	}
	
}