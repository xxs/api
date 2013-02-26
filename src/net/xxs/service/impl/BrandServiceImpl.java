package net.xxs.service.impl;

import javax.annotation.Resource;

import net.xxs.dao.BrandDao;
import net.xxs.entity.Brand;
import net.xxs.service.BrandService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 品牌
 */

@Service("brandServiceImpl")
public class BrandServiceImpl extends BaseServiceImpl<Brand, String> implements BrandService {

	@Resource(name = "brandDaoImpl")
	public void setBaseDao(BrandDao brandDao) {
		super.setBaseDao(brandDao);
	}

}