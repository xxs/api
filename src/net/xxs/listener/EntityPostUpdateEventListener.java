package net.xxs.listener;

import javax.annotation.Resource;

import net.xxs.entity.Product;
import net.xxs.service.JobService;

import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

@Component("entityPostUpdateEventListener")
public class EntityPostUpdateEventListener implements PostUpdateEventListener {
	
	private static final long serialVersionUID = -5981524888794053786L;
	
	private static final String NAVIGATION_ENTITY_NAME = "net.xxs.entity.Navigation";
	private static final String FRIEND_LINK_ENTITY_NAME = "net.xxs.entity.FriendLink";
	private static final String ARTICLE_CATEGORY_ENTITY_NAME = "net.xxs.entity.ArticleCategory";
	private static final String CARDS_CATEGORY_ENTITY_NAME = "net.xxs.entity.CardsCategory";
	private static final String ARTICLE_ENTITY_NAME = "net.xxs.entity.Article";
	private static final String CARDS_ENTITY_NAME = "net.xxs.entity.Cards";
	private static final String PRODUCT_ENTITY_NAME = "net.xxs.entity.Product";
	private static final String COMMENT_ENTITY_NAME = "net.xxs.entity.Comment";
	private static final String INSTANT_MESSAGING_ENTITY_NAME = "net.xxs.entity.InstantMessaging";
	
	@Resource(name = "jobServiceImpl")
	private JobService jobService;

	public void onPostUpdate(PostUpdateEvent event) {
		EntityPersister entityPersister = event.getPersister();
		String entityName = entityPersister.getEntityName();
		String id = String.valueOf(event.getId());
		
		if (entityName.equals(NAVIGATION_ENTITY_NAME)) {
			jobService.buildIndexHtml();
			jobService.buildLoginHtml();
			jobService.buildArticleContentHtml();
			jobService.buildCardsContentHtml();
		} else if (entityName.equals(FRIEND_LINK_ENTITY_NAME)) {
			jobService.buildIndexHtml();
			jobService.buildLoginHtml();
			jobService.buildArticleContentHtml();
			jobService.buildCardsContentHtml();
		} else if (entityName.equals(ARTICLE_CATEGORY_ENTITY_NAME)) {
			jobService.buildIndexHtml();
			jobService.buildArticleContentHtml();
		} else if (entityName.equals(CARDS_CATEGORY_ENTITY_NAME)) {
			jobService.buildIndexHtml();
			jobService.buildCardsContentHtml();
		} else if (entityName.equals(ARTICLE_ENTITY_NAME)) {
			jobService.buildArticleContentHtml(id);
			jobService.buildIndexHtml();
		} else if (entityName.equals(CARDS_ENTITY_NAME)) {
			jobService.buildCardsContentHtml(id);
			jobService.buildIndexHtml();
		} else if (entityName.equals(PRODUCT_ENTITY_NAME)) {
			Product product = (Product) event.getEntity();
			jobService.buildCardsContentHtml(product.getCards().getId());
		} else if (entityName.equals(INSTANT_MESSAGING_ENTITY_NAME)) {
			jobService.buildCardJs();
		}
	}

}