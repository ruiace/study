package com.rp.service.kill.mapper;

import com.rp.service.kill.entity.ItemKill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 待秒杀商品表 Mapper 接口
 * </p>
 *
 * @author rp
 * @since 2019-11-29
 */
public interface ItemKillMapper extends BaseMapper<ItemKill> {

    int updateItemKill(@Param("killId") Integer killId);
}
