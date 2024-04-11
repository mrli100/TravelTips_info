package net.maku.travel.service;

import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.travel.vo.TPainLogVO;
import net.maku.travel.query.TPainLogQuery;
import net.maku.travel.entity.TPainLogEntity;

import java.util.List;

/**
 * 痛苦日志记录
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-04-11
 */
public interface TPainLogService extends BaseService<TPainLogEntity> {

    PageResult<TPainLogVO> page(TPainLogQuery query);

    void save(TPainLogVO vo);

    void update(TPainLogVO vo);

    void delete(List<Long> idList);
}