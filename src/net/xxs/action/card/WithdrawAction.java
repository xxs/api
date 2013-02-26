package net.xxs.action.card;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import net.xxs.bean.Setting;
import net.xxs.entity.MemberBank;
import net.xxs.entity.Withdraw;
import net.xxs.entity.Withdraw.WithdrawStatus;
import net.xxs.service.MemberBankService;
import net.xxs.service.WithdrawService;
import net.xxs.util.SerialNumberUtil;
import net.xxs.util.SettingUtil;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 会员提现
 */

@ParentPackage("card")
@InterceptorRefs({
	@InterceptorRef(value = "memberVerifyInterceptor"),
	@InterceptorRef(value = "cardStack")
})
public class WithdrawAction extends BaseCardAction {

	private static final long serialVersionUID = 7391785904288731356L;
	private Withdraw withdraw;
	private MemberBank memberBank;
	private List<Withdraw> withdrawList;
	
	@Resource(name = "withdrawServiceImpl")
	private WithdrawService withdrawService;
	@Resource(name = "memberBankServiceImpl")
	private MemberBankService memberBankService;
	
	// 申请提现
	public String apply() {
		return "apply";
	}
	// 预存款列表
	public String list() {
		pager = withdrawService.getWithdeawPager(getLoginMember(), pager);
		withdrawList = withdrawService.getApplyWithdrawList(getLoginMember());
		return LIST;
	}
	
	// 保存
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "memberBank.id", message = "提现账户不能为空!")
			},
			requiredFields = { 
				@RequiredFieldValidator(fieldName = "withdraw.money", message = "提现金额不允许为空!")
			},
			stringLengthFields = {
				@StringLengthFieldValidator(fieldName = "withdraw.memo", minLength = "0", maxLength = "500", message = "备注长度500超出限制!")
			}
		)
	@InputConfig(resultName = "error")
	public String save() {
		Setting setting = SettingUtil.getSetting();
		if(0 != setting.getWithdrawEveryMinMoney()&&null != setting.getWithdrawEveryMinMoney()){
			if(null != withdraw.getMoney()&& Integer.parseInt(withdraw.getMoney().toString()) < setting.getWithdrawEveryMinMoney()){
				addActionError("对不起，目前系统设置的提现下限为"+setting.getWithdrawEveryMinMoney()+"元，请填写大于此数值的金额");
				return ERROR;
			}
		}
		if(0 != setting.getWithdrawEveryMaxMoney()&&null != setting.getWithdrawEveryMaxMoney()){
			if(null != withdraw.getMoney()&& Integer.parseInt(withdraw.getMoney().toString()) > setting.getWithdrawEveryMaxMoney()){
				addActionError("对不起，目前系统设置的提现上限为"+setting.getWithdrawEveryMaxMoney()+"元，请填写小于此数值的金额");
				return ERROR;
			}
		}
		withdrawList = withdrawService.getApplyWithdrawList(getLoginMember());
		if(0 != setting.getWithdrawMaxCount()&&null != setting.getWithdrawMaxCount()){
			if(null != withdrawList&&withdrawList.size() >= setting.getWithdrawMaxCount()){
				addActionError("目前已有"+withdrawList.size()+"条提现申请正在处理中！请等待管理员审核后再增加新提现！");
				return ERROR;
			}
		}
		BigDecimal allMoney = new BigDecimal(0);
		if(null != withdrawList&&withdrawList.size() > 0){
			Withdraw wi = new Withdraw();
			for (int i = 0; i < withdrawList.size(); i++) {
				wi = withdrawList.get(i);
				allMoney = allMoney.add(wi.getMoney());
			}
		}
		if(0 != setting.getWithdrawMaxMoney()&&null != setting.getWithdrawMaxMoney()){
			BigDecimal totalMoney = new BigDecimal(0);
			totalMoney = allMoney.add(withdraw.getMoney());
			System.out.println(setting.getWithdrawMaxMoney());
			System.out.println(Integer.parseInt(totalMoney.toString()));
			if(null != allMoney && Integer.parseInt(totalMoney.toString()) >= setting.getWithdrawMaxMoney()){
				addActionError("目前已有"+allMoney+"元提现申请正在处理中,由于系统设置了申请总额不能超过"+setting.getWithdrawMaxMoney()+"元！您目前可申请的配额为"+(Integer.parseInt((allMoney.add(withdraw.getMoney()).toString()))-setting.getWithdrawMaxMoney())+"元");
				return ERROR;
			}
		}
		
		//判断提现金额是否满足提现设置中的setting范围
		MemberBank pt = memberBankService.get(memberBank.getId());
		withdraw.setWithdrawSn(SerialNumberUtil.buildWithdrawSn());
		withdraw.setMoney(withdraw.getMoney());
		withdraw.setTotalMoney(withdraw.getMoney().multiply(BigDecimal.valueOf(getLoginMember().getMemberRank().getLossrate())));
		withdraw.setMessage("会员自主提现");
		withdraw.setRememo(null);
		withdraw.setWithdrawStatus(WithdrawStatus.apply);
		withdraw.setLossrate(BigDecimal.valueOf(getLoginMember().getMemberRank().getLossrate()));
		withdraw.setMember(getLoginMember());
		withdraw.setMemo(withdraw.getMemo());
		//存储瞬态信息
		withdraw.setBankname(pt.getBankname());
		withdraw.setBanknum(pt.getBanknum());
		withdraw.setOpenname(pt.getOpenname());
		
		withdraw.setMemberBank(pt);
		withdrawService.save(withdraw);
		redirectUrl = "withdraw!list.action";
		return SUCCESS;
	}

	public Withdraw getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(Withdraw withdraw) {
		this.withdraw = withdraw;
	}

	public List<Withdraw> getWithdrawList() {
		return withdrawList;
	}

	public void setWithdrawList(List<Withdraw> withdrawList) {
		this.withdrawList = withdrawList;
	}
	public MemberBank getMemberBank() {
		return memberBank;
	}
	public void setMemberBank(MemberBank memberBank) {
		this.memberBank = memberBank;
	}
	
	
}