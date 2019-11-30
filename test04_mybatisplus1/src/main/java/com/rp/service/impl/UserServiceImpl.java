package com.rp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rp.dao.UserMapper;
import com.rp.entity.User;
import com.rp.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
