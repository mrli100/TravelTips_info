package net.maku.travel.service;

import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.travel.vo.TAppSnsCircleVO;
import net.maku.travel.query.TAppSnsCircleQuery;
import net.maku.travel.entity.TAppSnsCircleEntity;

import java.util.List;

/**
 * 圈子表
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-03-12
 */
public interface TAppSnsCircleService extends BaseService<TAppSnsCircleEntity> {

    PageResult<TAppSnsCircleVO> page(TAppSnsCircleQuery query);

    void save(TAppSnsCircleVO vo);

    void update(TAppSnsCircleVO vo);

    void delete(List<Long> idList);
}
