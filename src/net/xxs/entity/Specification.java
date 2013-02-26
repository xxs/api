package net.xxs.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import net.xxs.bean.SpecificationValue;
import net.xxs.util.JsonUtil;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.type.TypeReference;
import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 充值卡规格
 */

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "createDate", "modifyDate", "specificationValueStore", "cardsSet", "json"})
public class Specification extends BaseEntity {

	private static final long serialVersionUID = -6346775052811140926L;

	// 充值卡规格类型（文字类型、图片类型）
	public enum SpecificationType {
		text, picture
	};
	
	private String name;// 名称
	private SpecificationType specificationType;// 充值卡规格类型
	private String memo;// 备注
	private String specificationValueStore;// 充值卡规格值存储
	private Integer orderList;// 排序
	
	private Set<Cards> cardsSet = new HashSet<Cards>();// 充值卡
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Enumerated
	@Column(nullable = false)
	public SpecificationType getSpecificationType() {
		return specificationType;
	}

	public void setSpecificationType(SpecificationType specificationType) {
		this.specificationType = specificationType;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Column(nullable = false, length = 3000)
	public String getSpecificationValueStore() {
		return specificationValueStore;
	}

	public void setSpecificationValueStore(String specificationValueStore) {
		this.specificationValueStore = specificationValueStore;
	}

	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}

	@ManyToMany(mappedBy = "specificationSet", fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_specification_cards_set")
	public Set<Cards> getCardsSet() {
		return cardsSet;
	}

	public void setCardsSet(Set<Cards> cardsSet) {
		this.cardsSet = cardsSet;
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
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
	}
	
	// 获取JSON字符串
	@Transient
	public String getJson() {
		return JsonUtil.toJson(this);
	}

}