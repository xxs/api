package net.xxs.service.impl;

import javax.annotation.Resource;

import net.xxs.bean.SpecificationValue;
import net.xxs.dao.SpecificationDao;
import net.xxs.entity.Specification;
import net.xxs.service.SpecificationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 充值卡规格
 */

@Service("specificationServiceImpl")
public class SpecificationServiceImpl extends BaseServiceImpl<Specification, String> implements SpecificationService {
	
	@Resource(name = "specificationDaoImpl")
	private SpecificationDao specificationDao;

	@Resource(name = "specificationDaoImpl")
	public void setBaseDao(SpecificationDao specificationDao) {
		super.setBaseDao(specificationDao);
	}
	
	@Transactional(readOnly = true)
	public SpecificationValue getSpecificationValue(String specificationId, String specificationValueId) {
		Specification specification = specificationDao.get(specificationId);
		for (SpecificationValue specificationValue : specification.getSpecificationValueList()) {
			if (specificationValue.getId().equals(specificationValueId)) {
				return specificationValue;
			}
		}
		return null;
	}

}