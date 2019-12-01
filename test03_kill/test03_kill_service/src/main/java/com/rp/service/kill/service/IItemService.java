package com.rp.service.kill.service;

import com.rp.service.kill.dto.KillDto;
import com.rp.service.kill.dto.Result;
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

    ItemKill selectDetailById(Integer id);

    Result killItem(KillDto killDto);

    public Result killItem2(KillDto killDto);
    public Result killItem3(KillDto killDto);
    public Result killItem5(KillDto killDto);
}
