package net.xxs.action.admin;

import java.math.BigDecimal;

import javax.annotation.Resource;

import net.xxs.bean.Setting;
import net.xxs.entity.Deposit;
import net.xxs.entity.Deposit.DepositType;
import net.xxs.entity.Member;
import net.xxs.entity.Withdraw;
import net.xxs.entity.Withdraw.WithdrawStatus;
import net.xxs.service.CacheService;
import net.xxs.service.DepositService;
import net.xxs.service.MemberService;
import net.xxs.service.WithdrawService;
import net.xxs.util.SettingUtil;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 会员提现
 */

@ParentPackage("admin")
public class WithdrawAction extends BaseAdminAction {

	private static final long serialVersionUID = 1426838593832738550L;


	@Resource(name = "withdrawServiceImpl")
	private WithdrawService withdrawService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	
	private Withdraw withdraw;
	private Setting setting;
	
	private Integer withdrawEveryDayCount;// 每天允许提现的最大次数,超出次数后不能提现(单位: 次,0表示不限制)
	private Integer withdrawEveryDayMoney;// 每天允许提现的最大金额,超出金额后不能提现(单位: 元,0表示不限制)
	private Integer withdrawEveryMaxMoney;// 每次提现最大金额(单位: 元,0表示不限制)
	private Integer withdrawEveryMinMoney;// 每次提现最小金额(单位: 元,0表示不限制)
	private Integer withdrawMaxMoney;// 提现中的最大总金额(单位: 元,0表示不限制)
	private Integer withdrawMaxCount;// 申请中的最大总记录(单位: 次,0表示不限制)
	
	//  提现单列表
	public String list() {
		pager = withdrawService.findPager(pager);
		return LIST;
	}
	//  提现申请中的列表
	public String applying() {
		pager = withdrawService.getWithdrawPager(WithdrawStatus.apply, pager);
		return LIST;
	}
	// 提现单处理
	public String process() {
		withdraw = withdrawService.load(id);
		pager = withdrawService.getWithdrawPager(WithdrawStatus.apply, pager);
		return "process";
	}
	// 提现通过
	public String successProcess() {
		withdraw = withdrawService.load(id);
		if (withdraw.getWithdrawStatus() == WithdrawStatus.success) {
			return ajax(Status.warn, "此单已通过!");
		} else if (withdraw.getWithdrawStatus() == WithdrawStatus.lose) {
			return ajax(Status.error, "此单已作废!");
		} else {
			//修改会员预存款操作
			Member member = withdraw.getMember();
			BigDecimal newDeposit =  member.getDeposit().subtract(withdraw.getMoney());
			if(Double.parseDouble(newDeposit.toString())<0){
				return ajax(Status.error, "用户预存款余额不足，不能提现!");
			}
			member.setDeposit(newDeposit);
			memberService.update(member);
			
			Deposit deposit = new Deposit();
			deposit.setDepositType(DepositType.memberWithdraw);
			deposit.setCredit(new BigDecimal(0));
			deposit.setDebit(withdraw.getMoney());
			deposit.setLossrate(withdraw.getLossrate());//保存交易时的手续费率
			deposit.setBalance(newDeposit);
			deposit.setMember(member);
			depositService.save(deposit);
			
			withdraw.setWithdrawStatus(WithdrawStatus.success);
			withdrawService.update(withdraw);
			return ajax(Status.success, "审核通过成功！");
		}
	}
	// 提现作废
	public String loseProcess() {
		withdraw = withdrawService.load(id);
		if (withdraw.getWithdrawStatus() == WithdrawStatus.success) {
			return ajax(Status.warn, "此单已通过!");
		} else if (withdraw.getWithdrawStatus() == WithdrawStatus.lose) {
			return ajax(Status.error, "此单已作废!");
		} else {
			withdraw.setWithdrawStatus(WithdrawStatus.lose);
			withdrawService.update(withdraw);
			return ajax(Status.success, "审核作废成功！");
		}
	}
	// 设置
	public String setting() {
		setting = SettingUtil.getSetting();
		return "setting";
	}
	
	// 设置更新
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "withdrawMaxCount", message = "提现中的最大记录数不能为空!为0表示不限制！"),
			@RequiredFieldValidator(fieldName = "withdrawMaxMoney", message = "提现中的最大总金额不能为空!为0表示不限制！"),
			@RequiredFieldValidator(fieldName = "withdrawEveryMinMoney", message = "单次最大提现金额不能为空!为0表示不限制！"),
			@RequiredFieldValidator(fieldName = "withdrawEveryMaxMoney", message = "单次最大提现金额不能为空!为0表示不限制！"),
			@RequiredFieldValidator(fieldName = "withdrawEveryDayMoney", message = "单日最大提现金额不能为空!为0表示不限制！"),
			@RequiredFieldValidator(fieldName = "withdrawEveryDayCount", message = " 单日最大提现次数不能为空!为0表示不限制！")
		}
	)
	@InputConfig(resultName = "error")
	public String settingUpdate() {
		Setting setting = SettingUtil.getSetting();
		setting.setWithdrawMaxCount(withdrawMaxCount);
		setting.setWithdrawMaxMoney(withdrawMaxMoney);
		setting.setWithdrawEveryMinMoney(withdrawEveryMinMoney);
		setting.setWithdrawEveryMaxMoney(withdrawEveryMaxMoney);
		setting.setWithdrawEveryDayMoney(withdrawEveryDayMoney);
		setting.setWithdrawEveryDayCount(withdrawEveryDayCount);
		SettingUtil.updateSetting(setting);
		cacheService.flushLeaveMessagePageCache(getRequest());
		redirectUrl = "withdraw!setting.action";
		return SUCCESS;
	}
	public Withdraw getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(Withdraw withdraw) {
		this.withdraw = withdraw;
	}
	public Integer getWithdrawEveryDayCount() {
		return withdrawEveryDayCount;
	}
	public void setWithdrawEveryDayCount(Integer withdrawEveryDayCount) {
		this.withdrawEveryDayCount = withdrawEveryDayCount;
	}
	public Integer getWithdrawEveryDayMoney() {
		return withdrawEveryDayMoney;
	}
	public void setWithdrawEveryDayMoney(Integer withdrawEveryDayMoney) {
		this.withdrawEveryDayMoney = withdrawEveryDayMoney;
	}
	public Integer getWithdrawMaxMoney() {
		return withdrawMaxMoney;
	}
	public void setWithdrawMaxMoney(Integer withdrawMaxMoney) {
		this.withdrawMaxMoney = withdrawMaxMoney;
	}
	public Setting getSetting() {
		return setting;
	}
	public void setSetting(Setting setting) {
		this.setting = setting;
	}
	public Integer getWithdrawEveryMaxMoney() {
		return withdrawEveryMaxMoney;
	}
	public void setWithdrawEveryMaxMoney(Integer withdrawEveryMaxMoney) {
		this.withdrawEveryMaxMoney = withdrawEveryMaxMoney;
	}
	public Integer getWithdrawEveryMinMoney() {
		return withdrawEveryMinMoney;
	}
	public void setWithdrawEveryMinMoney(Integer withdrawEveryMinMoney) {
		this.withdrawEveryMinMoney = withdrawEveryMinMoney;
	}
	public Integer getWithdrawMaxCount() {
		return withdrawMaxCount;
	}
	public void setWithdrawMaxCount(Integer withdrawMaxCount) {
		this.withdrawMaxCount = withdrawMaxCount;
	}
	
}