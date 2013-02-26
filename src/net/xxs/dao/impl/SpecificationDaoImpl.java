package net.xxs.dao.impl;

import net.xxs.dao.SpecificationDao;
import net.xxs.entity.Specification;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 充值卡规格
 */

@Repository("specificationDaoImpl")
public class SpecificationDaoImpl extends BaseDaoImpl<Specification, String> implements SpecificationDao {

}