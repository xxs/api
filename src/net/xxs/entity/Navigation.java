package net.xxs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

/**
 * 实体类 - 导航
 */

@Entity
public class Navigation extends BaseEntity {

	private static final long serialVersionUID = -7635757647887646795L;

	// 导航位置:顶部、中间、底部
	public enum NavigationPosition {
		top, middle, bottom
	}

	private String name;// 名称
	private NavigationPosition navigationPosition;// 导航位置
	private String url;// 链接地址;
	private Boolean isVisible;// 是否显示
	private Boolean isBlankTarget;// 是否在新窗口中打开
	private Integer orderList;// 排序

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated
	@Column(nullable = false)
	public NavigationPosition getNavigationPosition() {
		return navigationPosition;
	}

	public void setNavigationPosition(NavigationPosition navigationPosition) {
		this.navigationPosition = navigationPosition;
	}

	@Column(nullable = false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(nullable = false)
	public Boolean getIsBlankTarget() {
		return isBlankTarget;
	}

	public void setIsBlankTarget(Boolean isBlankTarget) {
		this.isBlankTarget = isBlankTarget;
	}

	@Column(nullable = false)
	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (isVisible == null) {
			isVisible = false;
		}
		if (isBlankTarget == null) {
			isBlankTarget = false;
		}
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (isVisible == null) {
			isVisible = false;
		}
		if (isBlankTarget == null) {
			isBlankTarget = false;
		}
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
	}

}