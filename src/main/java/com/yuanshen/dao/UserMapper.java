package com.yuanshen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuanshen.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: chenchenstart
 * @CreateTime: 2021/11/9 - 16:32
 * @Description:
 */

@Repository
public interface UserMapper extends BaseMapper<User> {
    User getByUsername(@Param("username") String username);

    User getByPhone(@Param("phone") String phone);

    User getByConditions(User user);
}
