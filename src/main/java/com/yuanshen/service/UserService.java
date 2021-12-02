package com.yuanshen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuanshen.entity.User;

/**
 * @Author: chenchenstart
 * @CreateTime: 2021/11/9 - 16:57
 * @Description:
 */
public interface UserService extends IService<User> {
    User login(String username);

    User getByPhone(String phone);

    User getUserByConditions(User user);
}
