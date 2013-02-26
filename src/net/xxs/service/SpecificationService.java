package net.xxs.service;

import net.xxs.bean.SpecificationValue;
import net.xxs.entity.Specification;

/**
 * Service接口 - 充值卡规格
 */

public interface SpecificationService extends BaseService<Specification, String> {
	
	/**
	 * 根据充值卡规格ID、充值卡规格值ID获取规格值
	 * 
	 * @param specificationId
	 *            充值卡规格ID
	 *            
	 * @param specificationValueId
	 *            充值卡规格值ID
	 *            
	 * @return 充值卡规格值
	 */
	public SpecificationValue getSpecificationValue(String specificationId, String specificationValueId);

}