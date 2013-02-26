package net.xxs.service.impl;

import javax.annotation.Resource;

import net.xxs.dao.MemberRankDao;
import net.xxs.entity.MemberRank;
import net.xxs.service.MemberRankService;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 会员分类
 */

@Service("memberRankServiceImpl")
public class MemberRankServiceImpl extends BaseServiceImpl<MemberRank, String> implements MemberRankService {
	
	@Resource(name = "memberRankDaoImpl")
	MemberRankDao memberRankDao;

	@Resource(name = "memberRankDaoImpl")
	public void setBaseDao(MemberRankDao memberRankDao) {
		super.setBaseDao(memberRankDao);
	}
	
	@Transactional(readOnly = true)
	public boolean isExistByName(String name) {
		return memberRankDao.isExistByName(name);
	}
	
	@Transactional(readOnly = true)
	public boolean isUniqueByName(String oldName, String newName) {
		if (StringUtils.equalsIgnoreCase(oldName, newName)) {
			return true;
		} else {
			if (memberRankDao.isExistByName(newName)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	@Cacheable(modelId = "memberRankCaching")
	@Transactional(readOnly = true)
	public MemberRank getDefaultMemberRank() {
		MemberRank defaultMemberRank = memberRankDao.getDefaultMemberRank();
		if (defaultMemberRank != null && !Hibernate.isInitialized(defaultMemberRank)) {
			Hibernate.initialize(defaultMemberRank);
		}
		return defaultMemberRank;
	}
	
	@Transactional(readOnly = true)
	public MemberRank getMemberRankByScore(Integer score) {
		return memberRankDao.getMemberRankByScore(score);
	}
	
	@Transactional(readOnly = true)
	public MemberRank getUpMemberRankByScore(Integer score) {
		return memberRankDao.getUpMemberRankByScore(score);
	}

	@Override
	@CacheFlush(modelId = "memberRankFlushing")
	public void delete(MemberRank memberRank) {
		memberRankDao.delete(memberRank);
	}

	@Override
	@CacheFlush(modelId = "memberRankFlushing")
	public void delete(String id) {
		memberRankDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "memberRankFlushing")
	public void delete(String[] ids) {
		memberRankDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "memberRankFlushing")
	public String save(MemberRank memberRank) {
		return memberRankDao.save(memberRank);
	}

	@Override
	@CacheFlush(modelId = "memberRankFlushing")
	public void update(MemberRank memberRank) {
		memberRankDao.update(memberRank);
	}

}