package net.xxs.action.card;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.xxs.entity.Member;
import net.xxs.entity.Message;
import net.xxs.entity.Message.DeleteStatus;
import net.xxs.service.MemberService;
import net.xxs.service.MessageService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 消息
 */

@ParentPackage("card")
@InterceptorRefs({
	@InterceptorRef(value = "memberVerifyInterceptor"),
	@InterceptorRef(value = "cardStack")
})
public class MessageAction extends BaseCardAction {

	private static final long serialVersionUID = 3248218706961305882L;

	private Message message;
	private String toMemberUsername;

	@Resource(name = "messageServiceImpl")
	private MessageService messageService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	// 检查用户名是否存在
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "toMemberUsername", message = "用户名不允许为空!")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String checkUsername() {
		String toMemberUsername = getParameter("toMemberUsername");
		if (memberService.isExistByUsername(toMemberUsername)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}

	// 发送消息
	public String send() {
		if (StringUtils.isNotEmpty(id)) {
			message = messageService.load(id);
			if (message.getIsSaveDraftbox() == false || message.getFromMember() != getLoginMember()) {
				addActionError("参数错误!");
				return ERROR;
			}
		}
		return "send";
	}
	
	// 回复
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "id", message = "ID不允许为空!")
		}
	)
	public String reply() {
		message = messageService.load(id);
		if (message.getToMember() != getLoginMember()) {
			addActionError("参数错误!");
			return ERROR;
		}
		return "reply";
	}
	
	// 保存消息
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "message.title", message = "标题不允许为空!"),
			@RequiredStringValidator(fieldName = "message.content", message = "消息内容不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "message.title", maxLength = "200", message = "标题长度超出限制!"),
			@StringLengthFieldValidator(fieldName = "message.content", maxLength = "10000", message = "消息内容长度超出限制!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (StringUtils.isNotEmpty(toMemberUsername)) {
			Member toMember = memberService.getMemberByUsername(toMemberUsername);
			if (toMember == null) {
				addActionError("收件人不存在!");
				return ERROR;
			}
			if (toMember == getLoginMember()) {
				addActionError("收件人不允许为自己!");
				return ERROR;
			}
			message.setToMember(toMember);
		} else {
			message.setToMember(null);
		}
		message.setFromMember(getLoginMember());
		message.setDeleteStatus(DeleteStatus.nonDelete);
		message.setIsRead(false);
		
		if (StringUtils.isNotEmpty(id)) {
			Message persistent = messageService.load(id);
			if (persistent.getIsSaveDraftbox() == false || persistent.getFromMember() != getLoginMember()) {
				addActionError("参数错误!");
				return ERROR;
			}
			BeanUtils.copyProperties(message, persistent, new String[] {"id", "createDate", "modifyDate"});
			messageService.update(persistent);
		} else {
			messageService.save(message);
		}
		if (message.getIsSaveDraftbox()) {
			redirectUrl = "message!draftbox.action";
		} else {
			redirectUrl = "message!outbox.action";
		}
		return SUCCESS;
	}
	
	// 收件箱
	public String inbox() {
		pager = messageService.getMemberInboxPager(getLoginMember(), pager);
		return "inbox";
	}

	// 发件箱
	public String outbox() {
		pager = messageService.getMemberOutboxPager(getLoginMember(), pager);
		return "outbox";
	}

	// 草稿箱
	public String draftbox() {
		pager = messageService.getMemberDraftboxPager(getLoginMember(), pager);
		return "draftbox";
	}

	// 删除
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "id", message = "ID不允许为空!")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxDelete() {
		Message message = messageService.load(id);
		if (message.getIsSaveDraftbox()) {
			if (message.getFromMember() != getLoginMember()) {
				return ajax(Status.error, "参数错误!");
			}
			messageService.delete(message);
		} else {
			if (message.getToMember() != null && message.getToMember() == getLoginMember()) {
				if (message.getDeleteStatus() == DeleteStatus.nonDelete) {
					message.setDeleteStatus(DeleteStatus.toDelete);
					messageService.update(message);
				} else if (message.getDeleteStatus() == DeleteStatus.fromDelete) {
					messageService.delete(message);
				}
			} else if (message.getFromMember() != null && message.getFromMember() == getLoginMember()) {
				if (message.getDeleteStatus() == DeleteStatus.nonDelete) {
					message.setDeleteStatus(DeleteStatus.fromDelete);
					messageService.update(message);
				} else if (message.getDeleteStatus() == DeleteStatus.toDelete) {
					messageService.delete(message);
				}
			}
		}
		return ajax(Status.success, "删除成功!");
	}

	// AJAX显示消息内容
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "id", message = "ID不允许为空!")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxShowMessage() {
		Message message = messageService.load(id);
		if (message.getToMember() != getLoginMember()) {
			addActionError("参数错误!");
			return ERROR;
		}
		if (!message.getIsRead()) {
			message.setIsRead(true);
			messageService.update(message);
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS_PARAMETER_NAME, Status.success);
		jsonMap.put("content", message.getContent());
		return ajax(jsonMap);
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getToMemberUsername() {
		return toMemberUsername;
	}

	public void setToMemberUsername(String toMemberUsername) {
		this.toMemberUsername = toMemberUsername;
	}

}