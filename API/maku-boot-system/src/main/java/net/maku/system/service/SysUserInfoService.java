package net.maku.system.service;

import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.system.entity.SysUserInfoEntity;
import net.maku.system.query.SysUserInfoQuery;
import net.maku.system.vo.SysUserInfoVO;

import java.util.List;

/**
 * 用户信息表
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2023-12-05
 */
public interface SysUserInfoService extends BaseService<SysUserInfoEntity> {

    PageResult<SysUserInfoVO> page(SysUserInfoQuery query);

    void save(SysUserInfoVO vo);

    void update(SysUserInfoVO vo);

    void delete(List<Long> idList);
}
