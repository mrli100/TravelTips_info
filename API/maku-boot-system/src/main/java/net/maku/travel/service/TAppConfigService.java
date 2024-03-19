package net.maku.travel.service;

import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.travel.vo.TAppConfigVO;
import net.maku.travel.query.TAppConfigQuery;
import net.maku.travel.entity.TAppConfigEntity;

import java.util.List;

/**
 * 程序配置
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-03-01
 */
public interface TAppConfigService extends BaseService<TAppConfigEntity> {

    PageResult<TAppConfigVO> page(TAppConfigQuery query);

    void save(TAppConfigVO vo);

    void update(TAppConfigVO vo);

    void delete(List<Long> idList);
}