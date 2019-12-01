package com.rp.service.kill.mapper;

import com.rp.service.kill.entity.Item;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rp.service.kill.entity.ItemKill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author rp
 * @since 2019-11-29
 */
public interface ItemMapper extends BaseMapper<Item> {

    List<ItemKill> selectAll();

    ItemKill selectDetailById(@Param("id") Integer id);
}
