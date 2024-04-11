package net.maku.travel.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.travel.entity.TPainLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 痛苦日志记录
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-04-11
*/
@Mapper
public interface TPainLogDao extends BaseDao<TPainLogEntity> {
	
}