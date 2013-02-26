package net.xxs.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 在线留言
 */

@Entity
public class LeaveMessage extends BaseEntity {

	private static final long serialVersionUID = 8304475901054561194L;
	
	public static final int DEFAULT_LEAVE_MESSAGE_LIST_PAGE_SIZE = 12;// 留言列表默认每页显示数
	
	private String username;// 用户名
	private String title;// 标题
	private String content;// 内容
	private String contact;// 联系方式
	private String ip;// IP
	
	private LeaveMessage forLeaveMessage;// 留言
	
	private Set<LeaveMessage> replyLeaveMessageSet = new HashSet<LeaveMessage>();// 回复
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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
	
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(nullable = false)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_leave_message_for_message")
	public LeaveMessage getForLeaveMessage() {
		return forLeaveMessage;
	}

	public void setForLeaveMessage(LeaveMessage forLeaveMessage) {
		this.forLeaveMessage = forLeaveMessage;
	}

	@OneToMany(mappedBy = "forLeaveMessage", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate asc")
	public Set<LeaveMessage> getReplyLeaveMessageSet() {
		return replyLeaveMessageSet;
	}

	public void setReplyLeaveMessageSet(Set<LeaveMessage> replyLeaveMessageSet) {
		this.replyLeaveMessageSet = replyLeaveMessageSet;
	}

}