package net.xxs.action.card;

import java.util.Set;

import javax.annotation.Resource;

import net.xxs.entity.Bank;
import net.xxs.entity.Business;
import net.xxs.service.BankService;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 提现账户信息
 */

@ParentPackage("card")
@InterceptorRefs({
	@InterceptorRef(value = "VerifyInterceptor"),
	@InterceptorRef(value = "cardStack")
})
public class BankAction extends BaseCardAction {

	
	private static final long serialVersionUID = -4623009405964163795L;
	
	private Business business;
	private Bank bank;
	
	@Resource(name = "bankServiceImpl")
	private BankService bankService;
	
	// 账户列表
	public String list() {
		business = getLoginBusiness();
		return LIST;
	}
	// 编辑
	public String edit() {
		bank = bankService.get(id);
		return INPUT;
	}
	// 添加
	public String add() {
		Business loginBusiness = getLoginBusiness();
		Set<Bank> bankSet = loginBusiness.getBankSet();
		if (bankSet != null && Bank.MAX_BUSINESSBANK_COUNT != null && bankSet.size() >= Bank.MAX_BUSINESSBANK_COUNT) {
			addActionError("只允许最多添加" + Bank.MAX_BUSINESSBANK_COUNT + "个提现账户!");
			return ERROR;
		}
		return INPUT;
	}
	// 设置默认
	public String check() {
		business = getLoginBusiness();
		bank = bankService.get(id);
		bank.setIsDefault(true);
		bankService.update(bank);
		return LIST;
	}

	// 账户更新
	@Validations(
			requiredStrings = { 
				@RequiredStringValidator(fieldName = "Bank.banknum", message = "账号不允许为空!"),
				@RequiredStringValidator(fieldName = "Bank.bankname", message = "银行名称不允许为空!"),
				@RequiredStringValidator(fieldName = "Bank.openname", message = "开户姓名不允许为空!"),
				@RequiredStringValidator(fieldName = "Bank.bankcity", message = "银行所在地不允许为空!"),
				@RequiredStringValidator(fieldName = "Bank.bankdetail", message = "支行明细不允许为空!")
			}
		)
	@InputConfig(resultName = "error")
	public String update() {
		Bank persistent = bankService.get(id);
		BeanUtils.copyProperties(bank, persistent, new String[] {"id", "createDate", "modifyDate", "business"});
		bankService.update(bank);
		if(null == redirectUrl){
			redirectUrl = "bank!list.action";
		}
		return SUCCESS;
	}
	// Ajax验证银行账号是否存在
	@InputConfig(resultName = "ajaxError")
	public String ajaxBankNumVerify() throws Exception {
		return ajax(bankService.isExistByBankNumber(bank.getBanknum()));
	}
	// 账户添加
	@Validations(
			requiredStrings = { 
				@RequiredStringValidator(fieldName = "Bank.banknum", message = "账号不允许为空!"),
				@RequiredStringValidator(fieldName = "Bank.bankname", message = "银行名称不允许为空!"),
				@RequiredStringValidator(fieldName = "Bank.openname", message = "开户姓名不允许为空!"),
				@RequiredStringValidator(fieldName = "Bank.bankcity", message = "银行所在地不允许为空!"),
				@RequiredStringValidator(fieldName = "Bank.bankdetail", message = "支行明细不允许为空!")
			}
		)
	@InputConfig(resultName = "error")
	public String save() {
		business = getLoginBusiness();
		Set<Bank> bankSet = business.getBankSet();
		if (bankSet != null && Bank.MAX_BUSINESSBANK_COUNT != null && bankSet.size() >= Bank.MAX_BUSINESSBANK_COUNT) {
			addActionError("只允许最多添加" + Bank.MAX_BUSINESSBANK_COUNT + "个提现账户!");
			return ERROR;
		}
		bank.setBusiness(business);
		bankService.save(bank);
		if(null == redirectUrl){
			redirectUrl = "bank!list.action";
		}
		return SUCCESS;
	}
	public Business getBusiness() {
		return business;
	}
	public void setBusiness(Business business) {
		this.business = business;
	}
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}

	

}