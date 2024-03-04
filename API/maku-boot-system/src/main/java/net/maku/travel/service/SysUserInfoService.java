package net.maku.travel.service;

import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.travel.vo.SysUserInfoVO;
import net.maku.travel.query.SysUserInfoQuery;
import net.maku.travel.entity.SysUserInfoEntity;

import java.util.List;

/**
 * 用户信息
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-03-04
 */
public interface SysUserInfoService extends BaseService<SysUserInfoEntity> {

    PageResult<SysUserInfoVO> page(SysUserInfoQuery query);

    void save(SysUserInfoVO vo);

    void update(SysUserInfoVO vo);

    void delete(List<Long> idList);
}