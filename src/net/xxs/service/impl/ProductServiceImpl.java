package net.xxs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.xxs.bean.SpecificationValue;
import net.xxs.dao.CardsDao;
import net.xxs.dao.ProductDao;
import net.xxs.entity.Cards;
import net.xxs.entity.Product;
import net.xxs.service.ProductService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 货品
 */

@Service("productServiceImpl")
public class ProductServiceImpl extends BaseServiceImpl<Product, String> implements ProductService {
	
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	@Resource(name = "cardsDaoImpl")
	private CardsDao cardsDao;

	@Resource(name = "productDaoImpl")
	public void setBaseDao(ProductDao productDao) {
		super.setBaseDao(productDao);
	}
	
	@Transactional(readOnly = true)
	public boolean isExistByProductSn(String productSn) {
		return productDao.isExistByProductSn(productSn);
	}
	
	@Transactional(readOnly = true)
	public boolean isUniqueByProductSn(String oldProductSn, String newProductSn) {
		if (StringUtils.equalsIgnoreCase(oldProductSn, newProductSn)) {
			return true;
		} else {
			if (productDao.isExistByProductSn(newProductSn)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	// 处理货品名称、货品默认、充值卡销售价、充值卡市场价、充值卡重量、充值卡库存、充值卡被占用库存数
	@Override
	public String save(Product product) {
		Cards cards = product.getCards();
		if (cards.getIsSpecificationEnabled()) {
			StringBuffer productName = new StringBuffer(cards.getName());
			List<SpecificationValue> specificationValueList = product.getSpecificationValueList();
			if (specificationValueList != null) {
				productName.append(" [");
				for (SpecificationValue specificationValue : specificationValueList) {
					productName.append(specificationValue.getName() + " ");
				}
				productName.deleteCharAt(productName.length() - 1);
				productName.append("]");
			}
			product.setName(productName.toString());
		} else {
			product.setName(cards.getName());
			product.setIsDefault(true);
			product.setIsMarketable(cards.getIsMarketable());
		}
		String id = productDao.save(product);
		
		if (cards.getIsSpecificationEnabled()) {
			if (product.getIsDefault()) {
				cards.setPrice(product.getPrice());
			}
		} else {
			cards.setPrice(product.getPrice());
		}
		cardsDao.update(cards);
		
		return id;
	}
	
	// 处理货品名称、货品默认、充值卡销售价、充值卡市场价、充值卡重量、充值卡库存、充值卡被占用库存数
	@Override
	public void update(Product product) {
		Cards cards = product.getCards();
		if (cards.getIsSpecificationEnabled()) {
			StringBuffer productName = new StringBuffer(cards.getName());
			List<SpecificationValue> specificationValueList = product.getSpecificationValueList();
			if (specificationValueList != null) {
				productName.append(" [");
				for (SpecificationValue specificationValue : specificationValueList) {
					productName.append(specificationValue.getName() + " ");
				}
				productName.deleteCharAt(productName.length() - 1);
				productName.append("]");
			}
			product.setName(productName.toString());
		} else {
			product.setName(cards.getName());
			product.setIsDefault(true);
			product.setIsMarketable(cards.getIsMarketable());
		}
		productDao.update(product);
		
		if (cards.getIsSpecificationEnabled()) {
			if (product.getIsDefault()) {
				cards.setPrice(product.getPrice());
			}
		} else {
			cards.setPrice(product.getPrice());
		}
		cardsDao.update(cards);
	}

}