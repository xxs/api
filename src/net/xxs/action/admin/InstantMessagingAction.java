package net.xxs.action.admin;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import net.xxs.bean.Setting;
import net.xxs.bean.Setting.InstantMessagingPosition;
import net.xxs.entity.InstantMessaging;
import net.xxs.service.InstantMessagingService;
import net.xxs.service.JobService;
import net.xxs.util.SettingUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 在线客服
 */

@ParentPackage("admin")
public class InstantMessagingAction extends BaseAdminAction {

	private static final long serialVersionUID = -4099792916193097579L;
	
	private String instantMessagingTitle;
	private InstantMessagingPosition instantMessagingPosition;
	private Boolean isInstantMessagingEnabled;
	private List<InstantMessaging> instantMessagingList;

	@Resource(name = "instantMessagingServiceImpl")
	private InstantMessagingService instantMessagingService;
	@Resource(name = "jobServiceImpl")
	private JobService jobService;

	// 编辑
	public String edit() {
		instantMessagingList = instantMessagingService.getAllList();
		return INPUT;
	}

	// 更新
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "instantMessagingTitle", message = "在线客服标题不允许为空!"),
			@RequiredFieldValidator(fieldName = "instantMessagingPosition", message = "在线客服位置不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		List<InstantMessaging> allInstantMessagingList = instantMessagingService.getAllList();
		if (allInstantMessagingList != null) {
			for (InstantMessaging instantMessaging : allInstantMessagingList) {
				instantMessagingService.delete(instantMessaging);
			}
		}
		if (instantMessagingList != null) {
			for (InstantMessaging instantMessaging : instantMessagingList) {
				if (instantMessaging != null && StringUtils.isNotEmpty(instantMessaging.getTitle()) && StringUtils.isNotEmpty(instantMessaging.getValue())) {
					instantMessagingService.save(instantMessaging);
				}
			}
		}
		
		Setting setting = SettingUtil.getSetting();
		setting.setInstantMessagingTitle(instantMessagingTitle);
		setting.setInstantMessagingPosition(instantMessagingPosition);
		setting.setIsInstantMessagingEnabled(isInstantMessagingEnabled);
		SettingUtil.updateSetting(setting);
		
		jobService.buildCardJs();
		
		redirectUrl = "instant_messaging!edit.action";
		return SUCCESS;
	}
	
	// 获取在线客服位置集合
	public List<InstantMessagingPosition> getInstantMessagingPositionList() {
		return Arrays.asList(InstantMessagingPosition.values());
	}

	public String getInstantMessagingTitle() {
		return instantMessagingTitle;
	}

	public void setInstantMessagingTitle(String instantMessagingTitle) {
		this.instantMessagingTitle = instantMessagingTitle;
	}

	public InstantMessagingPosition getInstantMessagingPosition() {
		return instantMessagingPosition;
	}

	public void setInstantMessagingPosition(InstantMessagingPosition instantMessagingPosition) {
		this.instantMessagingPosition = instantMessagingPosition;
	}

	public Boolean getIsInstantMessagingEnabled() {
		return isInstantMessagingEnabled;
	}

	public void setIsInstantMessagingEnabled(Boolean isInstantMessagingEnabled) {
		this.isInstantMessagingEnabled = isInstantMessagingEnabled;
	}

	public List<InstantMessaging> getInstantMessagingList() {
		return instantMessagingList;
	}

	public void setInstantMessagingList(List<InstantMessaging> instantMessagingList) {
		this.instantMessagingList = instantMessagingList;
	}

}