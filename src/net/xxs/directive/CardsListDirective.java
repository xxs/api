package net.xxs.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.xxs.entity.Cards;
import net.xxs.service.CardsService;
import net.xxs.util.DirectiveUtil;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("cardsListDirective")
public class CardsListDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "cards_list";
	private static final String COUNT_PARAMETER_NAME = "count";
	@Resource(name = "cardsServiceImpl")
	private CardsService cardsService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer count = DirectiveUtil.getIntegerParameter(COUNT_PARAMETER_NAME, params);
		
		
		List<Cards> cardsList = null;
		cardsList = cardsService.getCardsList(count);
		
		if (body != null && cardsList != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(cardsList);
			}
			body.render(env.getOut());
		}
	}

}