package net.maku.travel.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.travel.entity.SysUserInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 用户信息
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-03-04
*/
@Mapper
public interface SysUserInfoDao extends BaseDao<SysUserInfoEntity> {
	
}