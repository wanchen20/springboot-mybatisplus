package com.yuanshen.controller;

import com.alibaba.fastjson.JSONObject;
import com.yuanshen.entity.User;
import com.yuanshen.service.UserService;
import com.yuanshen.util.MD5Util;
import com.yuanshen.util.Result;
import com.yuanshen.util.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: chenchenstart
 * @CreateTime: 2021/11/10 - 21:17
 * @Description:
 */

@RestController
@RequestMapping("/api/user")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String md5Password = MD5Util.getMD5(password);

        User user2 = userService.login(username);
        if (user2 == null) {
            return Result.error("用户名错误，登录失败");
        }

        String password2 = user2.getPassword();
        if (md5Password.equals(password2)) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String userToken = uuid;
            String userJson = JSONObject.toJSONString(user2);
            redisTemplate.opsForValue().set(Constants.TOKEN_NAME + userToken, userJson);

            Map<Object, Object> map = new HashMap<>();
            map.put("user", user2);
            map.put("token", userToken);

            return Result.success(map);
        }

        return Result.error("密码错误，登录失败");
    }
}
