package net.maku.travel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.travel.convert.TPlanTripMainConvert;
import net.maku.travel.entity.TPlanTripMainEntity;
import net.maku.travel.query.TPlanTripMainQuery;
import net.maku.travel.vo.TPlanTripMainVO;
import net.maku.travel.dao.TPlanTripMainDao;
import net.maku.travel.service.TPlanTripMainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 计划路线
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2023-12-10
 */
@Service
@AllArgsConstructor
public class TPlanTripMainServiceImpl extends BaseServiceImpl<TPlanTripMainDao, TPlanTripMainEntity> implements TPlanTripMainService {

    @Override
    public PageResult<TPlanTripMainVO> page(TPlanTripMainQuery query) {
        IPage<TPlanTripMainEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(TPlanTripMainConvert.INSTANCE.convertList(page.getRecords()), page.getTotal(), page.getPages());
    }

    private LambdaQueryWrapper<TPlanTripMainEntity> getWrapper(TPlanTripMainQuery query) {
        LambdaQueryWrapper<TPlanTripMainEntity> wrapper = Wrappers.lambdaQuery();

        return wrapper;
    }

    @Override
    public void save(TPlanTripMainVO vo) {
        TPlanTripMainEntity entity = TPlanTripMainConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(TPlanTripMainVO vo) {
        TPlanTripMainEntity entity = TPlanTripMainConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}