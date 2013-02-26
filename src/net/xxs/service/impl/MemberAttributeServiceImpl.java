package net.xxs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.xxs.dao.MemberAttributeDao;
import net.xxs.entity.MemberAttribute;
import net.xxs.service.MemberAttributeService;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 会员属性
 */

@Service("memberAttributeServiceImpl")
public class MemberAttributeServiceImpl extends BaseServiceImpl<MemberAttribute, String> implements MemberAttributeService {

	@Resource(name = "memberAttributeDaoImpl")
	private MemberAttributeDao memberAttributeDao;
	
	@Resource(name = "memberAttributeDaoImpl")
	public void setBaseDao(MemberAttributeDao memberAttributeDao) {
		super.setBaseDao(memberAttributeDao);
	}
	
	@Transactional(readOnly = true)
	public Integer getUnusedPropertyIndex() {
		return memberAttributeDao.getUnusedPropertyIndex();
	}
	
	@Cacheable(modelId = "memberAttributeCaching")
	@Transactional(readOnly = true)
	public List<MemberAttribute> getMemberAttributeList() {
		List<MemberAttribute> memberAttributeList = memberAttributeDao.getMemberAttributeList();
		if (memberAttributeList != null) {
			for (MemberAttribute memberAttribute : memberAttributeList) {
				Hibernate.initialize(memberAttribute);
			}
		}
		return memberAttributeList;
	}

	@Override
	@CacheFlush(modelId = "memberAttributeFlushing")
	public void delete(MemberAttribute memberAttribute) {
		super.delete(memberAttribute);
	}

	@Override
	@CacheFlush(modelId = "memberAttributeFlushing")
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@CacheFlush(modelId = "memberAttributeFlushing")
	public void delete(String[] ids) {
		super.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "memberAttributeFlushing")
	public String save(MemberAttribute memberAttribute) {
		return super.save(memberAttribute);
	}

	@Override
	@CacheFlush(modelId = "memberAttributeFlushing")
	public void update(MemberAttribute memberAttribute) {
		super.update(memberAttribute);
	}

}