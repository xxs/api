package net.xxs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

/**
 * 实体类 - 友情链接
 */

@Entity
public class FriendLink extends BaseEntity {

	private static final long serialVersionUID = 3019642557500517628L;

	private String name;// 名称
	private String logoPath;// LOGO路径
	private String url;// 链接地址
	private Integer orderList;// 排序

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	@Column(nullable = false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
		if (StringUtils.isEmpty(logoPath)) {
			logoPath = null;
		}
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (StringUtils.isEmpty(logoPath)) {
			logoPath = null;
		}
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
	}

}