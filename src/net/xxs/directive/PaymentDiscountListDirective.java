package net.xxs.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.xxs.entity.Brand;
import net.xxs.entity.PaymentDiscount;
import net.xxs.service.BrandService;
import net.xxs.service.PaymentDiscountService;
import net.xxs.util.DirectiveUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("paymentDiscountListDirective")
public class PaymentDiscountListDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "paymentDiscount_list";
	private static final String BRANDID_PARAMETER_NAME = "brandId";
	
	@Resource(name = "paymentDiscountServiceImpl")
	private PaymentDiscountService paymentDiscountService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	
	
	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String brandIdString = DirectiveUtil.getStringParameter(BRANDID_PARAMETER_NAME, params);
		
		Brand brand = null;
		if (StringUtils.isNotEmpty(brandIdString)) {
			brand = brandService.get(brandIdString);
		}
		
		List<PaymentDiscount> paymentDiscountList = null;
		if(null != brand){
			paymentDiscountList = paymentDiscountService.getPaymentDiscountByBrand(brand);
		}else{
			paymentDiscountList = paymentDiscountService.getAllList();
		}
		if (body != null && paymentDiscountList != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(paymentDiscountList);
			}
			body.render(env.getOut());
		}
	}

}