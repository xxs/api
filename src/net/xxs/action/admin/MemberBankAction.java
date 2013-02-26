package net.xxs.action.admin;

import java.util.Set;

import javax.annotation.Resource;

import net.xxs.entity.Member;
import net.xxs.entity.MemberBank;
import net.xxs.service.MemberBankService;
import net.xxs.service.MemberService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 银行卡
 */

@ParentPackage("admin")
public class MemberBankAction extends BaseAdminAction {

	private static final long serialVersionUID = -5451875129461788865L;

	private MemberBank memberBank;

	@Resource(name = "memberBankServiceImpl")
	private MemberBankService memberBankService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	// 是否已存在 username ajax验证
	public String checkUsername() {
		String username = memberBank.getMember().getUsername();
		if (!memberService.isExistByUsername(username)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}
	// 是否已存在 memberbanknumber ajax验证
	public String checkBankNumber() {
		String username = memberBank.getMember().getUsername();
		if (!memberService.isExistByUsername(username)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}
	// 列表
	public String list() {
		pager = memberBankService.findPager(pager);
		return LIST;
	}

	// 添加
	public String add() {
		return INPUT;
	}
	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "memberBank.member.username", message = "所属会员名不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBank.banknum", message = "银行账号不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBank.openname", message = "开户姓名不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBank.bankname", message = "所属银行不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBank.bankcity", message = "银行归属地不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBank.bankdetail", message = "所属支行不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (!memberService.isExistByUsername(memberBank.getMember().getUsername())) {
			addActionError("会员名不存在!");
			return ERROR;
		}
		Member member = memberService.getMemberByUsername(memberBank.getMember().getUsername());
		if (memberBankService.isExistByBankNumber(memberBank.getBanknum())) {
			addActionError("银行卡号已绑定过!");
			return ERROR;
		}
		Set<MemberBank> memberBankSet = member.getMemberBankSet();
		if (memberBankSet != null && MemberBank.MAX_MEMBERBANK_COUNT != null && memberBankSet.size() >= MemberBank.MAX_MEMBERBANK_COUNT) {
			addActionError("每个会员只允许最多添加" + MemberBank.MAX_MEMBERBANK_COUNT + "个提现账户!");
			return ERROR;
		}
		memberBank.setMember(member);
		memberBankService.save(memberBank);
		redirectUrl = "member_bank!list.action";
		return SUCCESS;
	}
	// 编辑
	public String edit() {
		memberBank = memberBankService.load(id);
		return INPUT;
	}
	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "memberBank.member.username", message = "所属会员名不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBank.banknum", message = "银行账号不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBank.openname", message = "开户姓名不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBank.bankname", message = "所属银行不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBank.bankcity", message = "银行归属地不允许为空!"),
			@RequiredStringValidator(fieldName = "memberBank.bankdetail", message = "所属支行不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		MemberBank persistent = memberBankService.get(id);
		BeanUtils.copyProperties(memberBank, persistent, new String[] {"id", "createDate", "modifyDate", "member"});
		memberBankService.update(persistent);
		redirectUrl = "member_bank!list.action";
		return SUCCESS;
	}
	public MemberBank getMemberBank() {
		return memberBank;
	}

	public void setMemberBank(MemberBank memberBank) {
		this.memberBank = memberBank;
	}
}