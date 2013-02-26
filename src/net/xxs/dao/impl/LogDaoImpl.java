package net.xxs.dao.impl;

import net.xxs.dao.LogDao;
import net.xxs.entity.Log;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 日志
 */

@Repository("logDaoImpl")
public class LogDaoImpl extends BaseDaoImpl<Log, String> implements LogDao {

}