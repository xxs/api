package net.xxs.service.impl;

import javax.annotation.Resource;

import net.xxs.dao.RoleDao;
import net.xxs.entity.Role;
import net.xxs.service.RoleService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 角色
 */

@Service("roleServiceImpl")
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements RoleService {
	
	@Resource(name = "roleDaoImpl")
	RoleDao roleDao;

	@Resource(name = "roleDaoImpl")
	public void setBaseDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
	}

}