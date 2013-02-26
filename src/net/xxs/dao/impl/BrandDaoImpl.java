package net.xxs.dao.impl;

import java.util.Set;

import net.xxs.dao.BrandDao;
import net.xxs.entity.Brand;
import net.xxs.entity.Cards;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 品牌
 */

@Repository("brandDaoImpl")
public class BrandDaoImpl extends BaseDaoImpl<Brand, String> implements BrandDao {
	
	// 关联处理
	@Override
	public void delete(Brand brand) {
		Set<Cards> cardsSet = brand.getCardsSet();
		if (cardsSet != null) {
			for (Cards cards : cardsSet) {
				cards.setBrand(null);
			}
		}
		
		super.delete(brand);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Brand brand = load(id);
		this.delete(brand);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Brand brand = load(id);
			this.delete(brand);
		}
	}
	
}