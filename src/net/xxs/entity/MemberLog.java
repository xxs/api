package net.xxs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * 实体类 - 日志
 */

@Entity
public class MemberLog extends BaseEntity {


	private static final long serialVersionUID = 4125525234189474300L;
	
	private String operation;// 操作名称
	private String operator;// 操作员
	private String actionClass;// Action名称
	private String actionMethod;// Action方法名称
	private String ip;// IP
	private String info;// 日志信息

	@Column(nullable = false, updatable = false)
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Column(nullable = false, updatable = false)
	public String getOperator() {
		return operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Column(nullable = false, updatable = false)
	public String getActionClass() {
		return actionClass;
	}
	
	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}
	
	@Column(nullable = false, updatable = false)
	public String getActionMethod() {
		return actionMethod;
	}
	
	public void setActionMethod(String actionMethod) {
		this.actionMethod = actionMethod;
	}
	
	@Column(nullable = false, updatable = false)
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(length = 3000, updatable = false)
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (operator == null) {
			operator = "未知";
		}
	}
	
}