package com.yuanshen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuanshen.entity.User;
import com.yuanshen.service.UserService;
import com.yuanshen.util.MD5Util;
import com.yuanshen.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: chenchenstart
 * @CreateTime: 2021/11/9 - 16:58
 * @Description:
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增用户
     */
    @PostMapping("/new")
    public Result save(@RequestBody User user) {
        String password = user.getPassword();
        String md5Password = MD5Util.getMD5(password);
        user.setPassword(md5Password);
        if (userService.login(user.getUsername()) == null && userService.getByPhone(user.getPhone()) == null) {
            userService.save(user);
            return Result.success(user);
        } else {
            return Result.error("用户名或手机号已存在，无法进行新增操作");
        }
    }

    /**
     * 根据用户id删除用户信息
     */
    @PostMapping("/delete")
    public Result delete(@RequestParam(value = "id") int id) {
        if (userService.getById(id) == null) {
            return Result.error("无此id的用户，无法进行删除操作");
        }

        userService.removeById(id);
        return Result.success("删除用户成功");
    }

    /**
     * 根据用户id查询用户信息或者用户列表信息
     */
    @GetMapping("/info")
    public Result info(@RequestParam(value = "id", required = false) Integer id
                       //@PathVariable("id") int id,
    ) {
        if (id == null) {
            List userList = userService.list();
            return Result.success(userList);
        }
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("无此id的用户");
        }
        return Result.success(user);
    }

    /**
     * 查询全部用户信息
     */
    /*@GetMapping("/list")
    public Result list(){
        List userList = userService.list();
        return Result.success(userList);
    }*/

    /**
     * 分页查询用户信息
     */
    /*@GetMapping("/list/{current}/{size}")
    public Result list(@PathVariable("current") int current,
                       @PathVariable("size") int size){
        IPage iPage = new Page<>();
        iPage.setCurrent(current);
        iPage.setSize(size);
        iPage = userService.page(iPage);
        return Result.success(iPage);
    }*/

    /**
     * 条件查询用户信息
     * @param user
     * @return
     */
    /*@PostMapping("/search")
    public Result search(@RequestBody User user){
        User user2 = userService.getUserByConditions(user);

        //返回数据
        return Result.success(user2);
    }*/

    /**
     * 分页条件查询用户信息
     *
     * @param current 当前页
     * @param size 每页大小
     * @param username 用户名
     * @param realName 用户真实姓名
     * @param phone 用户手机号码
     * @return
     */
    @GetMapping("/list")
    public Result list(//@PathVariable("current") int current,
                       //@PathVariable("size") int size,
                       @RequestParam(value = "current", required = false, defaultValue = "1") int current,
                       @RequestParam(value = "size", required = false, defaultValue = "10") int size,
//                       @RequestBody User user,
                       @RequestParam(value = "username", required = false) String username,
                       @RequestParam(value = "realName", required = false) String realName,
                       @RequestParam(value = "phone", required = false) String phone) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        IPage<User> iPage = new Page<>();
        iPage.setCurrent(current);
        iPage.setSize(size);

        /*if(user == null){
            iPage = userService.page(iPage);
            return Result.success(iPage);
        }else{
            wrapper.eq(user.getUsername() != null, "username", user.getUsername())
                    .like(user.getRealName() != null, "real_name", user.getRealName())
                    .eq(user.getPhone() != null, "phone", user.getPhone());
        }*/

        if (username == null && realName == null && phone == null) {
            iPage = userService.page(iPage);
            return Result.success(iPage);
        } else {
            wrapper.eq(username != null, "username", username)
                    .like(realName != null, "real_name", realName)
                    .eq(phone != null, "phone", phone);
        }
        iPage = userService.page(iPage, wrapper);
        if (iPage == null) {
            return Result.error("没有查询到符合条件的用户");
        }
        return Result.success(iPage);
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        if (userService.getById(user.getId()) == null) {
            return Result.error("id为：{" + user.getId() + "}的用户不存在，无法进行更新的操作");
        }

        String password = user.getPassword();
        if (password != null) {
            String md5Password = MD5Util.getMD5(password);
            user.setPassword(md5Password);
        }
        userService.updateById(user);

        return Result.success("更新用户信息成功");
    }

    /**
     * 用户登入功能
     */
    /*@RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody User user, HttpServletResponse response){
        String username = user.getUsername();
        String password = user.getPassword();
        String md5Password = MD5Util.getMD5(password);

        User user2 = userService.login(username);
        if(user2 == null){
            return Result.error("用户名错误，登录失败");
        }

        String password2 = user2.getPassword();
        if(md5Password.equals(password2)){
            String uuid = UUID.randomUUID().toString().replaceAll("-" ,"");

            *//*Map<String, String> payload = new HashMap<>();
            payload.put("username",user2.getUsername());
            String token = JWTUtils.getToken(payload);*//*

            String userToken = uuid;
            String userJson = JSONObject.toJSONString(user2);
            redisTemplate.opsForValue().set(Constants.TOKEN_NAME + userToken, userJson);

            Map<Object, Object> map = new HashMap<>();
            map.put("user", user2);
            map.put("token", userToken);

            *//*Cookie userCookie = new Cookie("userToken", userToken);
            response.addCookie(userCookie);*//*

            return Result.success(map);
        }

        return Result.error("密码错误，登录失败");
    }*/
}
