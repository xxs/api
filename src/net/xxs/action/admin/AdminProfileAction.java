package net.xxs.action.admin;

import javax.annotation.Resource;

import net.xxs.entity.Admin;
import net.xxs.service.AdminService;
import net.xxs.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 管理员个人资料
 */

@ParentPackage("admin")
public class AdminProfileAction extends BaseAdminAction {

	private static final long serialVersionUID = -7731533592958843271L;

	private Admin admin;
	private String currentPassword;

	@Resource(name = "adminServiceImpl")
	private AdminService adminService;

	// ajax验证当前密码是否正确
	public String checkCurrentPassword() {
		Admin admin = adminService.loadLoginAdmin();
		if (StringUtils.equals(StringUtil.md5(currentPassword), admin.getPassword())) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}

	// 编辑管理员资料
	public String edit() {
		admin = adminService.loadLoginAdmin();
		return INPUT;
	}

	// 更新个人资料
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "admin.email", message = "E-mail不允许为空!")
		}, 
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "admin.password", minLength = "4", maxLength = "20", message = "新密码长度允许在{1}-{2}之间!") 
		}, 
		emails = { 
			@EmailValidator(fieldName = "admin.email", message = "E-mail格式错误!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		Admin persistent = adminService.loadLoginAdmin();
		if (StringUtils.isNotEmpty(currentPassword) && StringUtils.isNotEmpty(admin.getPassword())) {
			if (!StringUtils.equals(StringUtil.md5(currentPassword), persistent.getPassword())) {
				addActionError("当前密码输入错误!");
				return ERROR;
			}
			persistent.setPassword(StringUtil.md5(admin.getPassword()));
		}
		persistent.setEmail(admin.getEmail());
		adminService.update(persistent);
		return SUCCESS;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

}