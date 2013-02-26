package net.xxs.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.xxs.entity.ArticleCategory;
import net.xxs.service.ArticleCategoryService;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("articleCategoryTreeDirective")
public class ArticleCategoryTreeDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "article_category_tree";
	
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<ArticleCategory> articleCategoryTree = articleCategoryService.getArticleCategoryTree();
		if (body != null && articleCategoryTree != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(articleCategoryTree);
			}
			body.render(env.getOut());
		}
	}

}