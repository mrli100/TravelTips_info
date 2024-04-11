package net.maku.travel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.travel.convert.TPainLogConvert;
import net.maku.travel.entity.TPainLogEntity;
import net.maku.travel.query.TPainLogQuery;
import net.maku.travel.vo.TPainLogVO;
import net.maku.travel.dao.TPainLogDao;
import net.maku.travel.service.TPainLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 痛苦日志记录
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-04-11
 */
@Service
@AllArgsConstructor
public class TPainLogServiceImpl extends BaseServiceImpl<TPainLogDao, TPainLogEntity> implements TPainLogService {

    @Override
    public PageResult<TPainLogVO> page(TPainLogQuery query) {
        IPage<TPainLogEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(TPainLogConvert.INSTANCE.convertList(page.getRecords()), page.getTotal(), page.getPages());
    }

    private LambdaQueryWrapper<TPainLogEntity> getWrapper(TPainLogQuery query){
        LambdaQueryWrapper<TPainLogEntity> wrapper = Wrappers.lambdaQuery();

        return wrapper;
    }

    @Override
    public void save(TPainLogVO vo) {
        TPainLogEntity entity = TPainLogConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(TPainLogVO vo) {
        TPainLogEntity entity = TPainLogConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}