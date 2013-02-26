package net.xxs.dao.impl;

import net.xxs.dao.MemberLogDao;
import net.xxs.entity.MemberLog;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 会员日志
 */

@Repository("memberLogDaoImpl")
public class MemberLogDaoImpl extends BaseDaoImpl<MemberLog, String> implements MemberLogDao {

}