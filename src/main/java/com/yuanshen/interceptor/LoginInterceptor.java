package com.yuanshen.interceptor;

import com.alibaba.fastjson.JSON;
import com.yuanshen.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: chenchenstart
 * @CreateTime: 2021/11/10 - 16:50
 * @Description:
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("执行拦截操作");

        /*Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("userToken")){
                    String userToken = cookie.getValue();
                    String userJson = (String) redisTemplate.opsForValue().get("USER_TOKEN_" + userToken);
                    if(userJson != null){
                        return true;
                    }
                }
            }
        }*/

        String userToken = request.getHeader("Authorization");
        /*Long expire = 0L;
        try{
            expire = redisTemplate.getExpire(userToken); //通过token获取存在redis数据库中的user对象的过期时间
        }catch(Exception e){
            e.printStackTrace();
        }
        if(expire > 0){
            //判断对象过期时间是否小于0，小于0代表过期，没有过期就放行
            //放行之前确保用户在使用过程中登录状态不会过期
            //每次请求的时候重置过期时间
            redisTemplate.expire(userToken, 60, TimeUnit.MINUTES);
            return true;
        }else{
            //数据过期，拦截，重新登录
            return false;
        }*/

        if (userToken != null) {
            String userJson = (String) redisTemplate.opsForValue().get("USER_TOKEN_" + userToken);
            if (userJson != null) {
                return true;
            }
        }
//        throw new RuntimeException("无接口访问权限，需要登录才可以访问"); //不需要返回给前端
        returnJson(response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public void returnJson(HttpServletResponse response) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset = utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(Result.error("无接口访问权限")));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
