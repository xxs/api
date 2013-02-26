package net.xxs.action.admin;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import net.xxs.entity.Admin;
import net.xxs.entity.Role;
import net.xxs.service.AdminService;
import net.xxs.service.RoleService;
import net.xxs.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 管理员
 */

@ParentPackage("admin")
public class AdminAction extends BaseAdminAction {

	private static final long serialVersionUID = -6296393115930477663L;
	
	private Admin admin;
	private List<Role> roleList;

	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "roleServiceImpl")
	private RoleService roleService;
	
	// 是否已存在 ajax验证
	public String checkUsername() {
		String username = admin.getUsername();
		if (adminService.isExistByUsername(username)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		admin = adminService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = adminService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		if (ids.length >= adminService.getTotalCount()) {
			return ajax(Status.error, "请至少保留一个管理员,删除失败!");
		}
		StringBuffer logInfoStringBuffer = new StringBuffer("删除管理员: ");
		for (String id : ids) {
			Admin admin = adminService.load(id);
			adminService.delete(admin);
			logInfoStringBuffer.append(admin.getUsername() + " ");
		}
		logInfo = logInfoStringBuffer.toString();
		return ajax(Status.success, "删除成功!");
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "admin.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "admin.password", message = "密码不允许为空!"),
			@RequiredStringValidator(fieldName = "admin.email", message = "E-mail不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "admin.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "admin.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!")
		},
		emails = {
			@EmailValidator(fieldName = "admin.email", message = "E-mail格式错误!")
		},
		regexFields = {
			@RegexFieldValidator(fieldName = "admin.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (adminService.isExistByUsername(admin.getUsername())) {
			addActionError("用户名已存在!");
			return ERROR;
		}
		
		if (roleList == null || roleList.size() == 0) {
			addActionError("管理角色不允许为空!");
			return ERROR;
		}
		admin.setUsername(admin.getUsername().toLowerCase());
		admin.setLoginFailureCount(0);
		admin.setIsAccountLocked(false);
		admin.setIsAccountExpired(false);
		admin.setIsCredentialsExpired(false);
		admin.setRoleSet(new HashSet<Role>(roleList));
		String passwordMd5 = StringUtil.md5(admin.getPassword());
		admin.setPassword(passwordMd5);
		adminService.save(admin);
		logInfo = "添加管理员: " + admin.getUsername();
		redirectUrl = "admin!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "admin.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "admin.email", message = "E-mail不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "admin.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "admin.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") },
		emails = {
			@EmailValidator(fieldName = "admin.email", message = "E-mail格式错误!")
		},
		regexFields = {
			@RegexFieldValidator(fieldName = "admin.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		Admin persistent = adminService.load(id);
		if (roleList == null || roleList.size() == 0) {
			addActionError("管理角色不允许为空!");
			return ERROR;
		}
		admin.setRoleSet(new HashSet<Role>(roleList));
		if (StringUtils.isNotEmpty(admin.getPassword())) {
			String passwordMd5 = StringUtil.md5(admin.getPassword());
			persistent.setPassword(passwordMd5);
		}
		BeanUtils.copyProperties(admin, persistent, new String[] {"id", "createDate", "modifyDate", "username", "password", "isAccountLocked", "isAccountExpired", "isCredentialsExpired", "loginFailureCount", "lockedDate", "loginDate", "loginIp", "authorities"});
		adminService.update(persistent);
		logInfo = "编辑管理员: " + admin.getUsername();
		redirectUrl = "admin!list.action";
		return SUCCESS;
	}
	
	// 获取所有管理权限集合
	public List<Role> getAllRoleList() {
		return roleService.getAllList();
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}