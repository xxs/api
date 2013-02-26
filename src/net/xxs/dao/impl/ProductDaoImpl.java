package net.xxs.dao.impl;

import java.util.List;
import java.util.Set;

import net.xxs.dao.ProductDao;
import net.xxs.entity.Order;
import net.xxs.entity.Product;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 货品
 */

@Repository("productDaoImpl")
public class ProductDaoImpl extends BaseDaoImpl<Product, String> implements ProductDao {
	
	public boolean isExistByProductSn(String productSn) {
		String hql = "from Product as product where lower(product.productSn) = lower(:productSn)";
		Product product = (Product) getSession().createQuery(hql).setParameter("productSn", productSn).uniqueResult();
		if (product != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getSiblingsProductList(String productId) {
		Product product = super.load(productId);
		String hql = "from Product as product where product.cards = :cards";
		return getSession().createQuery(hql).setParameter("cards", product.getCards()).list();
	}
	
	// 关联处理
	@Override
	public void delete(Product product) {
		Set<Order> orderSet = product.getOrderSet();
		if (orderSet != null) {
			for (Order order : orderSet) {
				order.setProduct(null);
			}
		}
		
		super.delete(product);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Product product = load(id);
		this.delete(product);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Product product = load(id);
			this.delete(product);
		}
	}

}