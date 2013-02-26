package net.xxs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 消息
 */

@Entity
public class Message extends BaseEntity {

	private static final long serialVersionUID = -112310144651384975L;

	// 删除状态（未删除、发送者删除、接收者删除）
	public enum DeleteStatus {
		nonDelete, fromDelete, toDelete
	};

	private String title;// 标题
	private String content;// 内容
	private DeleteStatus deleteStatus;// 删除状态
	private Boolean isRead;// 是否已读
	private Boolean isSaveDraftbox;// 是否保存在草稿箱
	
	private Member fromMember;// 消息发出会员,为null时表示管理员
	private Member toMember;// 消息接收会员,为null时表示管理员


	@Column(nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	@Column(nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Enumerated
	@Column(nullable = false)
	public DeleteStatus getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(DeleteStatus deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	@Column(nullable = false)
	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	@Column(nullable = false)
	public Boolean getIsSaveDraftbox() {
		return isSaveDraftbox;
	}

	public void setIsSaveDraftbox(Boolean isSaveDraftbox) {
		this.isSaveDraftbox = isSaveDraftbox;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_message_from_member")
	public Member getFromMember() {
		return fromMember;
	}

	public void setFromMember(Member fromMember) {
		this.fromMember = fromMember;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_message_to_member")
	public Member getToMember() {
		return toMember;
	}

	public void setToMember(Member toMember) {
		this.toMember = toMember;
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (isRead == null) {
			isRead = false;
		}
		if (isSaveDraftbox == null) {
			isSaveDraftbox = false;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (isRead == null) {
			isRead = false;
		}
		if (isSaveDraftbox == null) {
			isSaveDraftbox = false;
		}
	}

}