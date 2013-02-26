package net.xxs.action.card;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.xxs.entity.Brand;
import net.xxs.entity.Order.OrderStatus;
import net.xxs.entity.PaymentConfig;
import net.xxs.service.BrandService;
import net.xxs.service.OrderService;
import net.xxs.service.PaymentConfigService;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 前台Action类 - 收益查询
 */

@ParentPackage("card")
@InterceptorRefs({
	@InterceptorRef(value = "memberVerifyInterceptor"),
	@InterceptorRef(value = "token", params = {"excludeMethods", "query"}),
	@InterceptorRef(value = "cardStack")
})
public class QueryAction extends BaseCardAction {
	
	
	private static final long serialVersionUID = -3232267413182060395L;
	
	private Date orderBeginDate;// 订单开始日期
	private Date orderEndDate;// 订单结束日期
	private Date paymentBeginDate;// 支付单开始日期
	private Date paymentEndDate;// 支付单结束日期
	
	
	@Resource(name = "paymentConfigServiceImpl")
	private PaymentConfigService paymentConfigService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	
	
	//查询订单最新状态
	@InputConfig(resultName = "error")	
	public String query() {
		return ajax(Status.success,"刷新成功");
	}
	
	// 获取所有支付方式集合
	public List<PaymentConfig> getAllPaymentConfigList() {
		return paymentConfigService.getAllList();
	}
	// 获取所有品牌
	public List<Brand> getAllBrandList() {
		return brandService.getAllList();
	}
	//获取所有的订单状态
	public List<OrderStatus> getOrderStatusList() {
		return Arrays.asList(OrderStatus.values());
	}

	public Date getOrderBeginDate() {
		return orderBeginDate;
	}

	public void setOrderBeginDate(Date orderBeginDate) {
		this.orderBeginDate = orderBeginDate;
	}

	public Date getOrderEndDate() {
		return orderEndDate;
	}

	public void setOrderEndDate(Date orderEndDate) {
		this.orderEndDate = orderEndDate;
	}

	public Date getPaymentBeginDate() {
		return paymentBeginDate;
	}

	public void setPaymentBeginDate(Date paymentBeginDate) {
		this.paymentBeginDate = paymentBeginDate;
	}

	public Date getPaymentEndDate() {
		return paymentEndDate;
	}

	public void setPaymentEndDate(Date paymentEndDate) {
		this.paymentEndDate = paymentEndDate;
	}
	
}