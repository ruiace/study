package com.rp.service.kill.service;

import com.rp.service.kill.entity.Item;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rp.service.kill.entity.ItemKill;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author rp
 * @since 2019-11-29
 */
public interface IItemService extends IService<Item> {

    List<ItemKill> selectAll();
}