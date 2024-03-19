package net.maku.travel.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.travel.entity.TPlanTripMainEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 计划路线
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2023-12-10
*/
@Mapper
public interface TPlanTripMainDao extends BaseDao<TPlanTripMainEntity> {
	
}