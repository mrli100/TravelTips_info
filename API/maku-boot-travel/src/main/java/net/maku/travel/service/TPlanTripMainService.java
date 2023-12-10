package net.maku.travel.service;

import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.travel.vo.TPlanTripMainVO;
import net.maku.travel.query.TPlanTripMainQuery;
import net.maku.travel.entity.TPlanTripMainEntity;

import java.util.List;

/**
 * 计划路线
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2023-12-10
 */
public interface TPlanTripMainService extends BaseService<TPlanTripMainEntity> {

    PageResult<TPlanTripMainVO> page(TPlanTripMainQuery query);

    void save(TPlanTripMainVO vo);

    void update(TPlanTripMainVO vo);

    void delete(List<Long> idList);
}