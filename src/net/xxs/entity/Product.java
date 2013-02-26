package net.xxs.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import net.xxs.bean.SpecificationValue;
import net.xxs.util.JsonUtil;
import net.xxs.util.SerialNumberUtil;
import net.xxs.util.SettingUtil;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 货品
 */

@Entity
public class Product extends BaseEntity {

	private static final long serialVersionUID = -4663151563624172169L;
	
	private String productSn;// 货品编号
	private String name;// 名称
	private BigDecimal price;// 销售价
	private Boolean isMarketable;// 是否上架
	private Boolean isDefault;// 是否默认
	private String specificationValueStore;// 充值卡规格值存储
	
	private Cards cards;// 充值卡
	
	private Set<Order> orderSet = new HashSet<Order>();// 订单
	
	@Column(nullable = false, unique = true)
	public String getProductSn() {
		return productSn;
	}
	
	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false, precision = 15, scale = 5)
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = SettingUtil.setPriceScale(price);
	}
	
	@Column(nullable = false)
	public Boolean getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	@Column(nullable = false)
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_product_cards")
	public Cards getCards() {
		return cards;
	}
	
	public void setCards(Cards cards) {
		this.cards = cards;
	}
	
	@Column(length = 3000)
	public String getSpecificationValueStore() {
		return specificationValueStore;
	}

	public void setSpecificationValueStore(String specificationValueStore) {
		this.specificationValueStore = specificationValueStore;
	}

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	public Set<Order> getOrderSet() {
		return orderSet;
	}

	public void setOrderSet(Set<Order> orderSet) {
		this.orderSet = orderSet;
	}

	
	// 获取充值卡规格值
	@Transient
	public List<SpecificationValue> getSpecificationValueList() {
		if (StringUtils.isEmpty(specificationValueStore)) {
			return null;
		}
		try {
			return JsonUtil.toObject(specificationValueStore, new TypeReference<List<SpecificationValue>>() {});
		} catch (Exception e) {
			return null;
		}
	}
	
	// 设置充值卡规格值
	@Transient
	public void setSpecificationValueList(List<SpecificationValue> specificationValueList) {
		if (specificationValueList == null || specificationValueList.size() == 0) {
			return;
		}
		Collections.sort(specificationValueList);
		specificationValueStore = JsonUtil.toJson(specificationValueList);
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (price == null || price.compareTo(new BigDecimal(0)) < 0) {
			price = new BigDecimal(0);
		}
		if (isMarketable == null) {
			isMarketable = false;
		}
		if (isDefault == null) {
			isDefault = false;
		}
		if (StringUtils.isEmpty(productSn)) {
			productSn = SerialNumberUtil.buildProductSn();
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (price == null || price.compareTo(new BigDecimal(0)) < 0) {
			price = new BigDecimal(0);
		}
		if (isMarketable == null) {
			isMarketable = false;
		}
		if (isDefault == null) {
			isDefault = false;
		}
		if (StringUtils.isEmpty(productSn)) {
			productSn = SerialNumberUtil.buildProductSn();
		}
	}
}