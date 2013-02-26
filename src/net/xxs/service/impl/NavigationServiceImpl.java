package net.xxs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.xxs.dao.NavigationDao;
import net.xxs.entity.Navigation;
import net.xxs.entity.Navigation.NavigationPosition;
import net.xxs.service.NavigationService;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 导航
 */

@Service("navigationServiceImpl")
public class NavigationServiceImpl extends BaseServiceImpl<Navigation, String> implements NavigationService {

	@Resource(name = "navigationDaoImpl")
	private NavigationDao navigationDao;

	@Resource(name = "navigationDaoImpl")
	public void setBaseDao(NavigationDao navigationDao) {
		super.setBaseDao(navigationDao);
	}
	
	@Cacheable(modelId = "navigationCaching")
	@Transactional(readOnly = true)
	public List<Navigation> getNavigationList(NavigationPosition navigationPosition, Integer maxResults) {
		List<Navigation> navigationList = navigationDao.getNavigationList(navigationPosition, maxResults);
		if (navigationList != null) {
			for (Navigation navigation : navigationList) {
				if (!Hibernate.isInitialized(navigation)) {
					Hibernate.initialize(navigation);
				}
			}
		}
		return navigationList;
	}

	@Override
	@CacheFlush(modelId = "navigationFlushing")
	public void delete(Navigation navigation) {
		navigationDao.delete(navigation);
	}

	@Override
	@CacheFlush(modelId = "navigationFlushing")
	public void delete(String id) {
		navigationDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "navigationFlushing")
	public void delete(String[] ids) {
		navigationDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "navigationFlushing")
	public String save(Navigation navigation) {
		return navigationDao.save(navigation);
	}

	@Override
	@CacheFlush(modelId = "navigationFlushing")
	public void update(Navigation navigation) {
		navigationDao.update(navigation);
	}

}