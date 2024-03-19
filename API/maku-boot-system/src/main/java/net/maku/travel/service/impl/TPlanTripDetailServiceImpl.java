package net.maku.travel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.travel.convert.TPlanTripDetailConvert;
import net.maku.travel.entity.TPlanTripDetailEntity;
import net.maku.travel.query.TPlanTripDetailQuery;
import net.maku.travel.vo.TPlanTripDetailVO;
import net.maku.travel.dao.TPlanTripDetailDao;
import net.maku.travel.service.TPlanTripDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 计划明细
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-01-28
 */
@Service
@AllArgsConstructor
public class TPlanTripDetailServiceImpl extends BaseServiceImpl<TPlanTripDetailDao, TPlanTripDetailEntity> implements TPlanTripDetailService {

    @Override
    public PageResult<TPlanTripDetailVO> page(TPlanTripDetailQuery query) {
        IPage<TPlanTripDetailEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(TPlanTripDetailConvert.INSTANCE.convertList(page.getRecords()), page.getTotal(), page.getPages());
    }

    private LambdaQueryWrapper<TPlanTripDetailEntity> getWrapper(TPlanTripDetailQuery query) {
        LambdaQueryWrapper<TPlanTripDetailEntity> wrapper = Wrappers.lambdaQuery();

        return wrapper;
    }

    @Override
    public void save(TPlanTripDetailVO vo) {
        TPlanTripDetailEntity entity = TPlanTripDetailConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(TPlanTripDetailVO vo) {
        TPlanTripDetailEntity entity = TPlanTripDetailConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}