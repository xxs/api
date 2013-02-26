package net.xxs.service;

import net.xxs.entity.Product;

/**
 * Service接口 - 货品
 */

public interface ProductService extends BaseService<Product, String> {
	
	/**
	 * 根据货品编号判断此货品是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByProductSn(String productSn);
	
	/**
	 * 根据货品编号判断此货品是否唯一（不区分大小写）
	 * 
	 */
	public boolean isUniqueByProductSn(String oldProductSn, String newProductSn);
	
}