package net.maku.system.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.system.entity.SysUserInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息表
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2023-12-05
 */
@Mapper
public interface SysUserInfoDao extends BaseDao<SysUserInfoEntity> {

}
