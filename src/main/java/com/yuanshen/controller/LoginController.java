package com.yuanshen.controller;

import com.alibaba.fastjson.JSONObject;
import com.yuanshen.entity.User;
import com.yuanshen.service.UserService;
import com.yuanshen.util.MD5Util;
import com.yuanshen.util.Result;
import com.yuanshen.util.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        /*if(username == null){
            return Result.error("请输入用户名，用户名不能为空");
        }

        if(password == null){
            return Result.error("请输入密码，密码不能为空");
        }*/

        User user2 = userService.login(username);
        if (user2 == null) {
            return Result.error("用户名不存在，登录失败");
        }

        String md5Password = MD5Util.getMD5(password);

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
