package net.maku.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.system.convert.SysUserInfoConvert;
import net.maku.system.dao.SysUserInfoDao;
import net.maku.system.entity.SysUserInfoEntity;
import net.maku.system.query.SysUserInfoQuery;
import net.maku.system.service.SysUserInfoService;
import net.maku.system.vo.SysUserInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户信息表
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2023-12-05
 */
@Service
@AllArgsConstructor
public class SysUserInfoServiceImpl extends BaseServiceImpl<SysUserInfoDao, SysUserInfoEntity> implements SysUserInfoService {

    @Override
    public PageResult<SysUserInfoVO> page(SysUserInfoQuery query) {
        IPage<SysUserInfoEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(SysUserInfoConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<SysUserInfoEntity> getWrapper(SysUserInfoQuery query){
        LambdaQueryWrapper<SysUserInfoEntity> wrapper = Wrappers.lambdaQuery();

        return wrapper;
    }

    @Override
    public void save(SysUserInfoVO vo) {
        SysUserInfoEntity entity = SysUserInfoConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(SysUserInfoVO vo) {
        SysUserInfoEntity entity = SysUserInfoConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}
