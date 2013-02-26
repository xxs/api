package net.xxs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

/**
 * 实体类 - 在线客服
 */

@Entity
public class InstantMessaging extends BaseEntity {

	private static final long serialVersionUID = -3386810468881374655L;

	// 在线客服类型（QQ、MSN、阿里旺旺、Skype）
	public enum InstantMessagingType {
		qq, msn, wangwang, skype
	};
	
	private InstantMessagingType instantMessagingType;// 在线客服类型
	private String value;// 值
	private String title;// 标题
	private Integer orderList;// 排序
	
	@Enumerated
	@Column(nullable = false)
	public InstantMessagingType getInstantMessagingType() {
		return instantMessagingType;
	}

	public void setInstantMessagingType(InstantMessagingType instantMessagingType) {
		this.instantMessagingType = instantMessagingType;
	}

	@Column(nullable = false)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

}