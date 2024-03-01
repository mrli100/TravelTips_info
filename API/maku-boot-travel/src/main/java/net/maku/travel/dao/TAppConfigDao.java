package net.maku.travel.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.travel.entity.TAppConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 程序配置
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-03-01
*/
@Mapper
public interface TAppConfigDao extends BaseDao<TAppConfigEntity> {
	
}