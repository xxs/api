package net.xxs.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.xxs.bean.Pager;
import net.xxs.bean.Pager.Order;
import net.xxs.util.DirectiveUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.SimpleHash;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("paginationDirective")
public class PaginationDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "pagination";
	private static final String PAGER_PARAMETER_NAME = "pager";
	private static final String BASE_URL_PARAMETER_NAME = "baseUrl";
	
	private static final String URL_LIST_PARAMETER_NAME = "urlList";
	private static final String PAGE_NUMBER_PARAMETER_NAME = "pageNumber";
	
	private static final String MAX_PAGE_ITEM_COUNT_PARAMETER_NAME = "maxPageItemCount";
	private static final String PARAMETER_MAP_PARAMETER_NAME = "parameterMap";
	
	private static final String PAGE_COUNT_PARAMETER_NAME = "pageCount";
	private static final String FIRST_PAGE_BASE_URL_PARAMETER_NAME = "firstPageUrl";
	private static final String LAST_PAGE_BASE_URL_PARAMETER_NAME = "lastPageUrl";
	private static final String PRE_PAGE_BASE_URL_PARAMETER_NAME = "prePageUrl";
	private static final String NEXT_PAGE_BASE_URL_PARAMETER_NAME = "nextPageUrl";
	private static final String PAGE_ITEM_PARAMETER_NAME = "pageItem";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Pager pager = (Pager) DirectiveUtil.getObjectParameter(PAGER_PARAMETER_NAME, params);
		List<String> urlList = (ArrayList<String>) DirectiveUtil.getObjectParameter(URL_LIST_PARAMETER_NAME, params);
		Integer maxPageItemCount = DirectiveUtil.getIntegerParameter(MAX_PAGE_ITEM_COUNT_PARAMETER_NAME, params);
		Map<String, String> parameterMap = (HashMap<String, String>) DirectiveUtil.getObjectParameter(PARAMETER_MAP_PARAMETER_NAME, params);
		
		Integer pageNumber = null;
		Integer pageCount = null;
		String parameter = null;
		String firstPageUrl = null;
		String lastPageUrl = null;
		String prePageUrl = null;
		String nextPageUrl = null;
		Map<String, String> pageItem = new LinkedHashMap<String, String>();
		Integer startPageItemNumber = null;
		Integer endPageItemNumber = null;
		
		if (pager != null) {
			String baseUrl = DirectiveUtil.getStringParameter(BASE_URL_PARAMETER_NAME, params);
			
			pageNumber = pager.getPageNumber();
			pageCount = pager.getPageCount();
			
			Integer pageSize = pager.getPageSize();
			String searchBy = pager.getSearchBy();
			String keyword = pager.getKeyword();
			String orderBy = pager.getOrderBy();
			Order order = pager.getOrder();
			
			if (maxPageItemCount == null || maxPageItemCount <= 0) {
				maxPageItemCount = 6;
			}
			
			StringBuffer parameterStringBuffer = new StringBuffer();
			if (pageSize != null) {
				parameterStringBuffer.append("&pager.pageSize=" + pageSize);
			}
			if (StringUtils.isNotEmpty(searchBy)) {
				parameterStringBuffer.append("&pager.searchBy=" + searchBy);
			}
			if (StringUtils.isNotEmpty(keyword)) {
				parameterStringBuffer.append("&pager.keyword=" + keyword);
			}
			if (StringUtils.isNotEmpty(orderBy)) {
				parameterStringBuffer.append("&pager.orderBy=" + orderBy);
			}
			if (order != null) {
				parameterStringBuffer.append("&pager.order=" + order);
			}
			if (parameterMap != null) {
				for (String parameterKey : parameterMap.keySet()) {
					String parameterValue = parameterMap.get(parameterKey);
					if (StringUtils.isNotEmpty(parameterKey) && StringUtils.isNotEmpty(parameterValue)) {
						parameterStringBuffer.append("&" + parameterKey + "=" + parameterValue);
					}
				}
			}
			if (StringUtils.contains(baseUrl, "?")) {
				baseUrl += "&";
			} else {
				baseUrl += "?";
			}
			
			parameter = parameterStringBuffer.toString();
			firstPageUrl = baseUrl + "pager.pageNumber=1" + parameter;
			lastPageUrl = baseUrl + "pager.pageNumber=" + pageCount + parameter;
			if (pageNumber > 1) {
				prePageUrl = baseUrl + "pager.pageNumber=" + (pageNumber - 1) + parameter;
			}
			if (pageNumber < pageCount) {
				nextPageUrl = baseUrl + "pager.pageNumber=" + (pageNumber + 1) + parameter;
			}
			
			Integer segment = ((pageNumber - 1) / maxPageItemCount) + 1;
			startPageItemNumber = (segment - 1) * maxPageItemCount + 1;
			endPageItemNumber = segment * maxPageItemCount;
			
			if (startPageItemNumber < 1) {
				startPageItemNumber = 1;
			}
			if (endPageItemNumber > pageCount) {
				endPageItemNumber = pageCount;
			}
			
			for (int i = startPageItemNumber; i <= endPageItemNumber; i ++) {
				pageItem.put(String.valueOf(i), baseUrl + "pager.pageNumber=" + i + parameter);
			}
		} else if(urlList != null) {
			pageNumber = DirectiveUtil.getIntegerParameter(PAGE_NUMBER_PARAMETER_NAME, params);
			pageCount = urlList.size();
			
			if (maxPageItemCount == null || maxPageItemCount <= 0) {
				maxPageItemCount = 6;
			}
			
			StringBuffer parameterStringBuffer = new StringBuffer();
			if (parameterMap != null) {
				for (String parameterKey : parameterMap.keySet()) {
					String parameterValue = parameterMap.get(parameterKey);
					if (StringUtils.isNotEmpty(parameterKey) && StringUtils.isNotEmpty(parameterValue)) {
						parameterStringBuffer.append("&" + parameterKey + "=" + parameterValue);
					}
				}
			}
			
			parameter = parameterStringBuffer.toString();
			firstPageUrl = urlList.get(0);
			lastPageUrl = urlList.get(pageCount - 1);
			if (pageNumber > 1) {
				prePageUrl = urlList.get(pageNumber - 2);
			}
			if (pageNumber < pageCount) {
				nextPageUrl = urlList.get(pageNumber);
			}
			
			Integer segment = ((pageNumber - 1) / maxPageItemCount) + 1;
			startPageItemNumber = (segment - 1) * maxPageItemCount + 1;
			endPageItemNumber = segment * maxPageItemCount;
			
			if (startPageItemNumber < 1) {
				startPageItemNumber = 1;
			}
			if (endPageItemNumber > pageCount) {
				endPageItemNumber = pageCount;
			}
			
			for (int i = startPageItemNumber; i <= endPageItemNumber; i ++) {
				pageItem.put(String.valueOf(i), urlList.get(i - 1));
			}
		} else {
			return;
		}
		
		if (body != null) {
			TemplateModel sourcePageNumber = env.getVariable(PAGE_NUMBER_PARAMETER_NAME);
			TemplateModel sourcePageCount = env.getVariable(PAGE_COUNT_PARAMETER_NAME);
			TemplateModel sourceFirstPageUrl = env.getVariable(FIRST_PAGE_BASE_URL_PARAMETER_NAME);
			TemplateModel sourceLastPageUrl = env.getVariable(LAST_PAGE_BASE_URL_PARAMETER_NAME);
			TemplateModel sourcePrePageUrl = env.getVariable(PRE_PAGE_BASE_URL_PARAMETER_NAME);
			TemplateModel sourceNextPageUrl = env.getVariable(NEXT_PAGE_BASE_URL_PARAMETER_NAME);
			TemplateModel sourcePageItem = env.getVariable(PAGE_ITEM_PARAMETER_NAME);
			
			env.setVariable(PAGE_NUMBER_PARAMETER_NAME, new SimpleNumber(pageNumber));
			env.setVariable(PAGE_COUNT_PARAMETER_NAME, new SimpleNumber(pageCount));
			env.setVariable(FIRST_PAGE_BASE_URL_PARAMETER_NAME, new SimpleScalar(firstPageUrl));
			env.setVariable(LAST_PAGE_BASE_URL_PARAMETER_NAME, new SimpleScalar(lastPageUrl));
			env.setVariable(PRE_PAGE_BASE_URL_PARAMETER_NAME, new SimpleScalar(prePageUrl));
			env.setVariable(NEXT_PAGE_BASE_URL_PARAMETER_NAME, new SimpleScalar(nextPageUrl));
			env.setVariable(PAGE_ITEM_PARAMETER_NAME, new SimpleHash(pageItem));
			
			body.render(env.getOut());
			
			env.setVariable(PAGE_NUMBER_PARAMETER_NAME, sourcePageNumber);
			env.setVariable(PAGE_COUNT_PARAMETER_NAME, sourcePageCount);
			env.setVariable(FIRST_PAGE_BASE_URL_PARAMETER_NAME, sourceFirstPageUrl);
			env.setVariable(LAST_PAGE_BASE_URL_PARAMETER_NAME, sourceLastPageUrl);
			env.setVariable(PRE_PAGE_BASE_URL_PARAMETER_NAME, sourcePrePageUrl);
			env.setVariable(NEXT_PAGE_BASE_URL_PARAMETER_NAME, sourceNextPageUrl);
			env.setVariable(PAGE_ITEM_PARAMETER_NAME, sourcePageItem);
		}
	}

}