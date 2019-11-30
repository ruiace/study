package com.rp.service.kill.service.impl;

import com.rp.service.kill.entity.Item;
import com.rp.service.kill.entity.ItemKill;
import com.rp.service.kill.mapper.ItemMapper;
import com.rp.service.kill.service.IItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author rp
 * @since 2019-11-29
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService {

    @Autowired
    ItemMapper itemMapper;

    @Override
    public List<ItemKill> selectAll() {
        List<ItemKill> list = itemMapper.selectAll();
        return list;
    }
}
