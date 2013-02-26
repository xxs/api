package net.xxs.action.admin;

import javax.annotation.Resource;

import net.xxs.service.ArticleService;
import net.xxs.service.CardsService;
import net.xxs.service.MemberBusinessService;
import net.xxs.service.MemberService;
import net.xxs.service.MessageService;
import net.xxs.service.OrderService;
import net.xxs.service.WithdrawService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 后台Action类 - 后台页面
 */

@ParentPackage("admin")
public class PageAction extends BaseAdminAction {

	private static final long serialVersionUID = 3148667965663281403L;

	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "messageServiceImpl")
	private MessageService messageService;
	@Resource(name = "cardsServiceImpl")
	private CardsService cardsService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "withdrawServiceImpl")
	private WithdrawService withdrawService;
	@Resource(name = "memberBusinessServiceImpl")
	private MemberBusinessService memberBusinessService;
	
	// 后台主页面
	public String main() {
		return "main";
	}
	
	// 后台Header
	public String header() {
		return "header";
	}
	
	// 后台菜单
	public String menu() {
		return "menu";
	}
	
	// 后台中间(显示/隐藏菜单)
	public String middle() {
		return "middle";
	}
	
	// 后台首页
	public String index() {
		return "index";
	}
	
	// 获取未处理订单数
	public Long getUnprocessedOrderCount() {
		return orderService.getUnprocessedOrderCount();
	}
	
	// 获取未读消息数
	public Long getUnreadMessageCount() {
		return messageService.getUnreadMessageCount();
	}
	
	// 获取未处理提现申请数
	public Long getUnprocessedWithdrawCount() {
		return withdrawService.getUnprocessedWithdrawCount();
	}
	
	// 获取未处理商户申请数
	public Long getUnprocessedMemberBusinessApplyCount() {
		return memberBusinessService.getUnprocessedMemberBusinessApplyCount();
	}
	
	// 获取商户总数
	public Long getUnprocessedMemberBusinessCount() {
		return memberBusinessService.getUnprocessedMemberBusinessCount();
	}
	
	// 获取已上架充值卡数
	public Long getMarketableCardsCount() {
		return cardsService.getMarketableCardsCount();
	}
	
	// 获取已下架充值卡数
	public Long getUnMarketableCardsCount() {
		return cardsService.getUnMarketableCardsCount();
	}
	
	// 获取会员总数
	public Long getMemberTotalCount() {
		return memberService.getTotalCount();
	}
	
	// 获取文章总数
	public Long getArticleTotalCount() {
		return articleService.getTotalCount();
	}
	
	// 获取JAVA版本
	public String getJavaVersion() {
		return System.getProperty("java.version");
	}
	
	// 获取系统名称
	public String getOsName() {
		return System.getProperty("os.name");
	}
	
	// 获取系统构架
	public String getOsArch() {
		return System.getProperty("os.arch");
	}
	
	// 获取系统版本
	public String getOsVersion() {
		return System.getProperty("os.version");
	}
	
	// 获取Server信息
	public String getServerInfo() {
		return StringUtils.substring(getServletContext().getServerInfo(), 0, 30);
	}
	
	// 获取Servlet版本
	public String getServletVersion() {
		return getServletContext().getMajorVersion() + "." + getServletContext().getMinorVersion();
	}

}