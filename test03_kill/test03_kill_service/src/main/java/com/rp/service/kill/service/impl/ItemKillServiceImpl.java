package com.rp.service.kill.service.impl;

import com.rp.service.kill.entity.ItemKill;
import com.rp.service.kill.mapper.ItemKillMapper;
import com.rp.service.kill.service.IItemKillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 待秒杀商品表 服务实现类
 * </p>
 *
 * @author rp
 * @since 2019-11-29
 */
@Service
public class ItemKillServiceImpl extends ServiceImpl<ItemKillMapper, ItemKill> implements IItemKillService {

}
