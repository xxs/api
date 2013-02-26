package net.xxs.action.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.xxs.entity.Brand;
import net.xxs.entity.Cards;
import net.xxs.entity.PaymentDiscount;
import net.xxs.service.CardsService;
import net.xxs.service.PaymentDiscountService;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 前台Action类 - 充值卡
 */

@ParentPackage("card")
@InterceptorRefs({
	@InterceptorRef(value = "memberVerifyInterceptor"),
	@InterceptorRef(value = "cardStack")
})
public class CardsAction extends BaseCardAction {

	private static final long serialVersionUID = -4969421249817468001L;

	private String sign;
	private Map<String, String> cardsAttributeIdMap;
	private String orderType;
	private Cards cards;
	private Brand brand;
	private List<PaymentDiscount> paymentDiscountList = new ArrayList<PaymentDiscount>();
	
	@Resource(name = "cardsServiceImpl")
	private CardsService cardsService;
	@Resource(name = "paymentDiscountServiceImpl")
	private PaymentDiscountService paymentDiscountService;
	
	@InputConfig(resultName = "error")
	public String list() {
		cards = cardsService.get("4028bc743c3c4340013c3c51dac90001");
		paymentDiscountList = paymentDiscountService.getPaymentDiscountByBrand(cards.getBrand());
		return "content";
	}
	public String detail(){
		cards = cardsService.get(id);
		paymentDiscountList = paymentDiscountService.getPaymentDiscountByBrand(cards.getBrand());
		return "content";
	}
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Map<String, String> getCardsAttributeIdMap() {
		return cardsAttributeIdMap;
	}

	public void setCardsAttributeIdMap(Map<String, String> cardsAttributeIdMap) {
		this.cardsAttributeIdMap = cardsAttributeIdMap;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	public List<PaymentDiscount> getPaymentDiscountList() {
		return paymentDiscountList;
	}
	public void setPaymentDiscountList(List<PaymentDiscount> paymentDiscountList) {
		this.paymentDiscountList = paymentDiscountList;
	}
//	// 获取所有支付方式集合
//	public List<PaymentConfig> getAllPaymentConfigList() {
//		return paymentConfigService.getAllList();
//	}

	public Cards getCards() {
		return cards;
	}

	public void setCards(Cards cards) {
		this.cards = cards;
	}
}