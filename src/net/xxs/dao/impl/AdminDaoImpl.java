package net.xxs.dao.impl;

import net.xxs.dao.AdminDao;
import net.xxs.entity.Admin;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 管理员
 */

@Repository("adminDaoImpl")
public class AdminDaoImpl extends BaseDaoImpl<Admin, String> implements AdminDao {
	
	public boolean isExistByUsername(String username) {
		String hql = "from Admin as admin where lower(admin.username) = lower(:username)";
		Admin admin = (Admin) getSession().createQuery(hql).setParameter("username", username).uniqueResult();
		if (admin != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Admin getAdminByUsername(String username) {
		String hql = "from Admin as admin where lower(admin.username) = lower(:username)";
		return (Admin) getSession().createQuery(hql).setParameter("username", username).uniqueResult();
	}

}