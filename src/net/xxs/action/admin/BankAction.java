package net.xxs.action.admin;

import java.util.Set;

import javax.annotation.Resource;

import net.xxs.entity.Bank;
import net.xxs.entity.Business;
import net.xxs.service.BankService;
import net.xxs.service.BusinessService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 银行卡
 */

@ParentPackage("admin")
public class BankAction extends BaseAdminAction {

	private static final long serialVersionUID = -5451875129461788865L;

	private Bank bank;

	@Resource(name = "bankServiceImpl")
	private BankService bankService;
	@Resource(name = "businessServiceImpl")
	private BusinessService businessService;

	// 是否已存在 username ajax验证
	public String checkUsername() {
		String email = bank.getBusiness().getEmail();
		if (!businessService.isExistByEmail(email)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}
	// 是否已存在 banknumber ajax验证
	public String checkBankNumber() {
		String email = bank.getBusiness().getEmail();
		if (!businessService.isExistByEmail(email)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}
	// 列表
	public String list() {
		pager = bankService.findPager(pager);
		return LIST;
	}

	// 添加
	public String add() {
		return INPUT;
	}
	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "bank.business.email", message = "所属会员名不允许为空!"),
			@RequiredStringValidator(fieldName = "bank.banknum", message = "银行账号不允许为空!"),
			@RequiredStringValidator(fieldName = "bank.openname", message = "开户姓名不允许为空!"),
			@RequiredStringValidator(fieldName = "bank.bankname", message = "所属银行不允许为空!"),
			@RequiredStringValidator(fieldName = "bank.bankcity", message = "银行归属地不允许为空!"),
			@RequiredStringValidator(fieldName = "bank.bankdetail", message = "所属支行不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (!businessService.isExistByEmail(bank.getBusiness().getEmail())) {
			addActionError("会员名不存在!");
			return ERROR;
		}
		Business business = businessService.getBusinessByEmail(bank.getBusiness().getEmail());
		if (bankService.isExistByBankNumber(bank.getBanknum())) {
			addActionError("银行卡号已绑定过!");
			return ERROR;
		}
		Set<Bank> bankSet = business.getBankSet();
		if (bankSet != null && Bank.MAX_BUSINESSBANK_COUNT != null && bankSet.size() >= Bank.MAX_BUSINESSBANK_COUNT) {
			addActionError("每个会员只允许最多添加" + Bank.MAX_BUSINESSBANK_COUNT + "个提现账户!");
			return ERROR;
		}
		bank.setBusiness(business);
		bankService.save(bank);
		redirectUrl = "member_bank!list.action";
		return SUCCESS;
	}
	// 编辑
	public String edit() {
		bank = bankService.load(id);
		return INPUT;
	}
	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "bank.business.email", message = "所属会员名不允许为空!"),
			@RequiredStringValidator(fieldName = "bank.banknum", message = "银行账号不允许为空!"),
			@RequiredStringValidator(fieldName = "bank.openname", message = "开户姓名不允许为空!"),
			@RequiredStringValidator(fieldName = "bank.bankname", message = "所属银行不允许为空!"),
			@RequiredStringValidator(fieldName = "bank.bankcity", message = "银行归属地不允许为空!"),
			@RequiredStringValidator(fieldName = "bank.bankdetail", message = "所属支行不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		Bank persistent = bankService.get(id);
		BeanUtils.copyProperties(bank, persistent, new String[] {"id", "createDate", "modifyDate", "business"});
		bankService.update(persistent);
		redirectUrl = "member_bank!list.action";
		return SUCCESS;
	}
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
}