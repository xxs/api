package net.xxs.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.xxs.entity.Navigation;
import net.xxs.entity.Navigation.NavigationPosition;
import net.xxs.service.NavigationService;
import net.xxs.util.DirectiveUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("navigationListDirective")
public class NavigationListDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "navigation_list";
	private static final String POSITION_PARAMETER_NAME = "position";
	private static final String COUNT_PARAMETER_NAME = "count";
	
	@Resource(name = "navigationServiceImpl")
	private NavigationService navigationService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String navigationPositionString = DirectiveUtil.getStringParameter(POSITION_PARAMETER_NAME, params);
		Integer count = DirectiveUtil.getIntegerParameter(COUNT_PARAMETER_NAME, params);
		
		NavigationPosition navigationPosition = null;
		if (StringUtils.isNotEmpty(navigationPositionString)) {
			navigationPosition = NavigationPosition.valueOf(navigationPositionString);
		}
		
		List<Navigation> navigationList = navigationService.getNavigationList(navigationPosition, count);
		
		if (body != null && navigationList != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(navigationList);
			}
			body.render(env.getOut());
		}
	}

}