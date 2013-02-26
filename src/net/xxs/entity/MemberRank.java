package net.xxs.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类 - 会员等级
 */

@Entity
public class MemberRank extends BaseEntity {

	private static final long serialVersionUID = 3599029355500655209L;

	private String name;// 等級名称
	private Double lossrate;// 手续费率
	private Double benefits;//提成率
	private Integer score;// 所需积分
	private Boolean isDefault;// 是否为默认等级
	
	private Set<Member> memberSet = new HashSet<Member>();// 会员

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public Double getLossrate() {
		return lossrate;
	}

	public void setLossrate(Double lossrate) {
		this.lossrate = lossrate;
	}
	@Column(nullable = false)
	public Double getBenefits() {
		return benefits;
	}

	public void setBenefits(Double benefits) {
		this.benefits = benefits;
	}

	@Column(nullable = false, unique = true)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(nullable = false)
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@OneToMany(mappedBy = "memberRank", fetch = FetchType.LAZY)
	public Set<Member> getMemberSet() {
		return memberSet;
	}

	public void setMemberSet(Set<Member> memberSet) {
		this.memberSet = memberSet;
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (lossrate == null || lossrate < 0D) {
			lossrate = 0D;
		}
		if (benefits == null || benefits < 0D) {
			benefits = 0D;
		}
		if (score == null || score < 0) {
			score = 0;
		}
		if (isDefault == null) {
			isDefault = false;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (lossrate == null || lossrate < 0D) {
			lossrate = 0D;
		}
		if (benefits == null || benefits < 0D) {
			benefits = 0D;
		}
		if (score == null || score < 0) {
			score = 0;
		}
		if (isDefault == null) {
			isDefault = false;
		}
	}

}