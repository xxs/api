package net.xxs.action.card;

import java.util.Set;

import javax.annotation.Resource;

import net.xxs.entity.Member;
import net.xxs.entity.MemberBank;
import net.xxs.service.MemberBankService;

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
	@InterceptorRef(value = "memberVerifyInterceptor"),
	@InterceptorRef(value = "cardStack")
})
public class BankAction extends BaseCardAction {

	
	private static final long serialVersionUID = -4623009405964163795L;
	
	private Member member;
	private MemberBank memberBank;
	
	@Resource(name = "memberBankServiceImpl")
	private MemberBankService memberBankService;
	
	// 账户列表
	public String list() {
		member = getLoginMember();
		return LIST;
	}
	// 编辑
	public String edit() {
		memberBank = memberBankService.get(id);
		return INPUT;
	}
	// 添加
	public String add() {
		Member loginMember = getLoginMember();
		Set<MemberBank> memberBankSet = loginMember.getMemberBankSet();
		if (memberBankSet != null && MemberBank.MAX_MEMBERBANK_COUNT != null && memberBankSet.size() >= MemberBank.MAX_MEMBERBANK_COUNT) {
			addActionError("只允许最多添加" + MemberBank.MAX_MEMBERBANK_COUNT + "个提现账户!");
			return ERROR;
		}
		return INPUT;
	}
	// 设置默认
	public String check() {
		member = getLoginMember();
		memberBank = memberBankService.get(id);
		memberBank.setIsDefault(true);
		memberBankService.update(memberBank);
		return LIST;
	}

	// 账户更新
	@Validations(
			requiredStrings = { 
				@RequiredStringValidator(fieldName = "memberBank.banknum", message = "账号不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBank.bankname", message = "银行名称不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBank.openname", message = "开户姓名不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBank.bankcity", message = "银行所在地不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBank.bankdetail", message = "支行明细不允许为空!")
			}
		)
	@InputConfig(resultName = "error")
	public String update() {
		MemberBank persistent = memberBankService.get(id);
		BeanUtils.copyProperties(memberBank, persistent, new String[] {"id", "createDate", "modifyDate", "member"});
		memberBankService.update(memberBank);
		if(null == redirectUrl){
			redirectUrl = "bank!list.action";
		}
		return SUCCESS;
	}
	// Ajax验证银行账号是否存在
	@InputConfig(resultName = "ajaxError")
	public String ajaxBankNumVerify() throws Exception {
		return ajax(memberBankService.isExistByBankNumber(memberBank.getBanknum()));
	}
	// 账户添加
	@Validations(
			requiredStrings = { 
				@RequiredStringValidator(fieldName = "memberBank.banknum", message = "账号不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBank.bankname", message = "银行名称不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBank.openname", message = "开户姓名不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBank.bankcity", message = "银行所在地不允许为空!"),
				@RequiredStringValidator(fieldName = "memberBank.bankdetail", message = "支行明细不允许为空!")
			}
		)
	@InputConfig(resultName = "error")
	public String save() {
		member = getLoginMember();
		Set<MemberBank> memberBankSet = member.getMemberBankSet();
		if (memberBankSet != null && MemberBank.MAX_MEMBERBANK_COUNT != null && memberBankSet.size() >= MemberBank.MAX_MEMBERBANK_COUNT) {
			addActionError("只允许最多添加" + MemberBank.MAX_MEMBERBANK_COUNT + "个提现账户!");
			return ERROR;
		}
		memberBank.setMember(member);
		memberBankService.save(memberBank);
		if(null == redirectUrl){
			redirectUrl = "bank!list.action";
		}
		return SUCCESS;
	}
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	public MemberBank getMemberBank() {
		return memberBank;
	}
	public void setMemberBank(MemberBank memberBank) {
		this.memberBank = memberBank;
	}


}