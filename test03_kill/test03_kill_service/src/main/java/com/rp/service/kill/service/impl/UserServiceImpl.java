package com.rp.service.kill.service.impl;

import com.rp.service.kill.entity.User;
import com.rp.service.kill.mapper.UserMapper;
import com.rp.service.kill.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author rp
 * @since 2019-11-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
