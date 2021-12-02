package com.yuanshen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuanshen.entity.User;
import com.yuanshen.dao.UserMapper;
import com.yuanshen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: chenchenstart
 * @CreateTime: 2021/11/9 - 16:57
 * @Description:
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    public User getByPhone(String phone) {
        return userMapper.getByPhone(phone);
    }

    @Override
    public User getUserByConditions(User user) {
        return userMapper.getByConditions(user);
    }
}
