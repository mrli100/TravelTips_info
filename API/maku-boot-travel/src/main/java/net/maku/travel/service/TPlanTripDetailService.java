package net.maku.travel.service;

import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.travel.vo.TPlanTripDetailVO;
import net.maku.travel.query.TPlanTripDetailQuery;
import net.maku.travel.entity.TPlanTripDetailEntity;

import java.util.List;

/**
 * 计划明细
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-01-28
 */
public interface TPlanTripDetailService extends BaseService<TPlanTripDetailEntity> {

    PageResult<TPlanTripDetailVO> page(TPlanTripDetailQuery query);

    void save(TPlanTripDetailVO vo);

    void update(TPlanTripDetailVO vo);

    void delete(List<Long> idList);
}