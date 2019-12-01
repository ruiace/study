package com.rp.service.kill.mapper;

import com.rp.service.kill.dto.KillSuccessUserInfo;
import com.rp.service.kill.entity.ItemKillSuccess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 秒杀成功订单表 Mapper 接口
 * </p>
 *
 * @author rp
 * @since 2019-11-29
 */
public interface ItemKillSuccessMapper extends BaseMapper<ItemKillSuccess> {

    KillSuccessUserInfo selectByOrderNo(@Param("orderNo") String orderNo);

    List<ItemKillSuccess> selectOrderExpireList();
}
