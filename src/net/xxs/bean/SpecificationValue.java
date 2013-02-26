package net.xxs.bean;

import org.apache.commons.lang.StringUtils;

import net.xxs.util.CommonUtil;



/**
 * Bean类 - 充值卡规格值
 */

public class SpecificationValue implements Comparable<SpecificationValue> {
	
	private String id;// ID
	private String name;// 名称
	private String imagePath;// 图片路径
	private Integer orderList;// 排序
	
	public SpecificationValue() {
		this.id = CommonUtil.getUUID();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		if (StringUtils.isEmpty(imagePath)) {
			imagePath = null;
		}
		this.imagePath = imagePath;
	}
	
	public Integer getOrderList() {
		return orderList;
	}
	
	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof SpecificationValue) {
			SpecificationValue specificationValue = (SpecificationValue) obj;
			if (this.getId() == null || specificationValue.getId() == null) {
				return false;
			} else {
				return (this.getId().equals(specificationValue.getId()));
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id == null ? System.identityHashCode(this) : (this.getClass().getName() + this.getId()).hashCode();
	}
	
	public int compareTo(SpecificationValue specificationValue) {
		if (specificationValue.getOrderList() == null) {
			return 1;
		}
		if (this.getOrderList() == null) {
			return -1;
		}
		return this.getOrderList().compareTo(specificationValue.getOrderList());
	}

}