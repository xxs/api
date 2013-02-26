package net.xxs.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import net.xxs.util.SerialNumberUtil;

import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 会员（商户信息）
 */

@Entity
public class MemberBusiness extends BaseEntity {

	private static final long serialVersionUID = -166239890424584670L;
	
	// 商户类型
	public enum BusinessType {
		personal, enterprise
	};
	// 商户类型
	public enum ResultType {
		apply, success, lose
	};
	
	private BusinessType businessType;	//商户类型
	private String realName;			//真实姓名
	private String businessNumber;		//身份证号（营业执照号码）
	private String businessName;		//商户名称
	private String city;				//商户地区
	private String scope;				//经营范围
	private String url;					//商城网址
	private String icp;					//ICP证备案号
	private String linkMan;				//联系人
	private String tel; 				//业务电话
	private String phone; 				//业务手机
	private String serverTel; 			//客服热线
	private String serverTime; 			//客服工作时间
	private String QQ;					//即时通讯 
	private String address;				//通讯地址
	private String zipcode;				//邮编
	private String memo;				//是否通过备注
	
	private String BusinessNum;  			//商户号
	private String BusinessKey;					//秘钥
	
	
	private ResultType resultType;			//审核是否通过
	private Member member; 				// 会员

	@OneToOne(mappedBy = "memberBusiness", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@ForeignKey(name = "fk_member_business_member")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	@Enumerated
	@Column(nullable = false)
	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}
	@Enumerated
	@Column(nullable = false)
	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	@Column(nullable = false)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	@Column(nullable = false, unique = true)
	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}
	@Column(nullable = false, unique = true)
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	@Column(nullable = false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	@Column(nullable = false)
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	@Column(nullable = false, unique = true)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@Column(nullable = false)
	public String getIcp() {
		return icp;
	}

	public void setIcp(String icp) {
		this.icp = icp;
	}
	@Column(nullable = false)
	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	@Column(nullable = false)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	@Column(nullable = false)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(nullable = false)
	public String getServerTel() {
		return serverTel;
	}

	public void setServerTel(String serverTel) {
		this.serverTel = serverTel;
	}
	
	public String getServerTime() {
		return serverTime;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}
	@Column(nullable = false)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(nullable = false)
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Column(nullable = true, updatable = false, unique = true)
	public String getBusinessNum() {
		return BusinessNum;
	}

	public void setBusinessNum(String businessNum) {
		BusinessNum = businessNum;
	}

	@Column(nullable = true, unique = true)
	public String getBusinessKey() {
		return BusinessKey;
	}

	public void setBusinessKey(String businessKey) {
		BusinessKey = businessKey;
	}
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		BusinessNum = SerialNumberUtil.buildBusinessNumber();
		BusinessKey = SerialNumberUtil.buildBusinessKey();
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		
	}

}