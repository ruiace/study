package com.rp.service.kill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rp.service.kill.dto.KillDto;
import com.rp.service.kill.dto.Result;
import com.rp.service.kill.entity.Item;
import com.rp.service.kill.entity.ItemKill;
import com.rp.service.kill.entity.ItemKillSuccess;
import com.rp.service.kill.mapper.ItemKillMapper;
import com.rp.service.kill.mapper.ItemKillSuccessMapper;
import com.rp.service.kill.mapper.ItemMapper;
import com.rp.service.kill.service.IItemService;
import com.rp.service.kill.service.RabbitMqSendService;
import com.rp.service.kill.utils.SnowFlake;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Override
    public ItemKill selectDetailById(Integer id) {
        ItemKill itemKill = itemMapper.selectDetailById(id);
        return itemKill;
    }


    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    @Autowired
    private ItemKillMapper itemKillMapper;

    @Override
    public Result killItem(KillDto killDto) {
        QueryWrapper<ItemKillSuccess> wrapper = new QueryWrapper<>();
        wrapper.eq("kill_id", killDto.getKillId()).eq("user_id", killDto.getUserId());
        Integer integer = itemKillSuccessMapper.selectCount(wrapper);

        if (integer > 0) {
            return Result.fail("已经抢购过来----");
        }
        //ItemKill itemKill = itemKillMapper.selectById(killDto.getKillId());

        ItemKill itemKill = itemMapper.selectDetailById(killDto.getKillId());
        if (itemKill != null && itemKill.getCanKill() == 1) {
            int updateItemKill = itemKillMapper.updateItemKill(killDto.getKillId());

            //扣货成功生产秒杀成功单
            if (updateItemKill > 0) {
                int successInsert = recoredItemKillSuccess(itemKill, killDto.getUserId());
                if (successInsert > 0) {
                    return Result.success();
                }
            }
        }


        return Result.fail();
    }


    @Autowired
    private RabbitMqSendService rabbitMqSendService;

    private int recoredItemKillSuccess(ItemKill itemKill, Integer userId) {

        ItemKillSuccess success = new ItemKillSuccess();
        SnowFlake snowFlake = new SnowFlake(2, 3);
        String orderNo = snowFlake.nextId() + "";
        success.setCode(orderNo);
        success.setCreateTime(new Date());
        success.setItemId(itemKill.getItemId());

        success.setStatus(0);
        success.setKillId(itemKill.getId());
        success.setUserId(userId + "");
        int insert = itemKillSuccessMapper.insert(success);
        if (insert > 0) {
            //rabbitmq + mail


            //rabbitMqSendService.killSuccessSendEmail(orderNo);


            //rabbitMqSendService.killSuccessSendExpireOrder(orderNo);
        }
        return insert;
    }


    //---------------------------分部署所---------------------------------------------


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result killItem2(KillDto killDto) {
        QueryWrapper<ItemKillSuccess> wrapper = new QueryWrapper<>();
        wrapper.eq("kill_id", killDto.getKillId()).eq("user_id", killDto.getUserId());
        Integer integer = itemKillSuccessMapper.selectCount(wrapper);

        if (integer <= 0) {

            //redis 分布式锁
            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();

            //key
            String key = new StringBuilder().append(killDto.getKillId()).append(killDto.getUserId()).append("clock-----").toString();
            String value = String.valueOf(System.currentTimeMillis());

            Boolean aBoolean = stringStringValueOperations.setIfAbsent(key, value);

            //todo  这里如果宕机了将会出现问题

            if (aBoolean) {

                try {
                    ItemKill itemKill = itemMapper.selectDetailById(killDto.getKillId());
                    if (itemKill != null && itemKill.getCanKill() == 1) {
                        int updateItemKill = itemKillMapper.updateItemKill(killDto.getKillId());

                        //扣货成功生产秒杀成功单
                        if (updateItemKill > 0) {
                            int successInsert = recoredItemKillSuccess(itemKill, killDto.getUserId());
                            if (successInsert > 0) {
                                return Result.success();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (value.equals(stringStringValueOperations.get(key))) {
                        stringRedisTemplate.delete(key);
                    }
                }

            }


        }


        return Result.fail();
    }


    @Autowired
    private RedissonClient redissonClient;


    @Override
    public Result killItem3(KillDto killDto) {
        QueryWrapper<ItemKillSuccess> wrapper = new QueryWrapper<>();
        wrapper.eq("kill_id", killDto.getKillId()).eq("user_id", killDto.getUserId());
        Integer integer = itemKillSuccessMapper.selectCount(wrapper);


        //redisson 分布式锁

        //key
        String key = new StringBuilder().append(killDto.getKillId()).append(killDto.getUserId()).append("clock-----").toString();
        RLock lock1 = redissonClient.getLock(key);


        //todo  这里如果宕机了将会出现问题

        try {
            //lock1.lock(10, TimeUnit.SECONDS);
            boolean b = lock1.tryLock(100, 10, TimeUnit.SECONDS);


            if(b){
                if (integer <= 0) {
                    try {
                        ItemKill itemKill = itemMapper.selectDetailById(killDto.getKillId());
                        if (itemKill != null && itemKill.getCanKill() == 1) {
                            int updateItemKill = itemKillMapper.updateItemKill(killDto.getKillId());

                            //扣货成功生产秒杀成功单
                            if (updateItemKill > 0) {
                                int successInsert = recoredItemKillSuccess(itemKill, killDto.getUserId());
                                if (successInsert > 0) {
                                    return Result.success();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else{
                log.debug("已经强过了....");
            }



        }catch (Exception e){
            log.error("=================");
        }finally {

            //lock1.unlock();
            lock1.forceUnlock();
        }

        return Result.fail();
    }


    @Autowired
    private CuratorFramework curatorFramework;


    @Override
    public Result killItem5(KillDto killDto) {
        QueryWrapper<ItemKillSuccess> wrapper = new QueryWrapper<>();
        wrapper.eq("kill_id", killDto.getKillId()).eq("user_id", killDto.getUserId());
        Integer integer = itemKillSuccessMapper.selectCount(wrapper);


        InterProcessMutex mutex = new InterProcessMutex(curatorFramework, "/kill/lock/" + killDto.getKillId() + "/" + killDto.getUserId() + "/lock");


        try {

            if(mutex.acquire(10,TimeUnit.SECONDS)){

                if (integer > 0) {
                    return Result.fail("已经抢购过来----");
                }
                //ItemKill itemKill = itemKillMapper.selectById(killDto.getKillId());

                ItemKill itemKill = itemMapper.selectDetailById(killDto.getKillId());
                if (itemKill != null && itemKill.getCanKill() == 1) {
                    int updateItemKill = itemKillMapper.updateItemKill(killDto.getKillId());

                    //扣货成功生产秒杀成功单
                    if (updateItemKill > 0) {
                        int successInsert = recoredItemKillSuccess(itemKill, killDto.getUserId());
                        if (successInsert > 0) {
                            return Result.success();
                        }
                    }
                }
            }

        }catch (Exception e){
            log.error("----");
        }finally {
            if(mutex != null){
                try {
                    mutex.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }




        return Result.fail();
    }
}
