package com.yuanshen.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Map;

/**
 * @Author: chenchenstart
 * @CreateTime: 2021/11/15 - 10:24
 * @Description:
 */
public class JWTUtils {
    private static String TOKEN = "token!Q@W#E$R";

    /**
     * 生成token  header.payload.signature
     *
     * @param map //传入payload
     * @return 返回token
     */
    public static String getToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7); //默认7天过期

        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        //payload
        map.forEach((k, v) ->
        {
            builder.withClaim(k, v);
        });

        String token = builder.withExpiresAt(instance.getTime()) //指定令牌过期时间
                .sign(Algorithm.HMAC256(TOKEN));//sign
        return token;
    }
}
