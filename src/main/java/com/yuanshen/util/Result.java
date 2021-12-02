package com.yuanshen.util;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: chenchenstart
 * @CreateTime: 2021/11/11 - 15:07
 * @Description:
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code; //状态码：200是正常调用 500是程序出错
    private String msg; //返回消息：成功还是失败
    private Date time; //返回时间
    private T data; //返回的对象，一般为成功后返回的数据

    private Result() {
    }

    private Result(int code, String msg, Date time) {
        this.code = code;
        this.msg = msg;
        this.time = time;
    }

    private Result(int code, String msg, Date time, T data) {
        this.code = code;
        this.msg = msg;
        this.time = time;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 成功的时候调用
     */
    public static <T> Result<T> success() {
        return new Result<T>(200, "成功", new Date());
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(200, "成功", new Date(), data);
    }

    /**
     * 失败的时候调用
     */
    public static <T> Result<T> error() {
        return new Result<T>(500, "失败", new Date());
    }

    public static <T> Result<T> error(T data) {
        return new Result<T>(500, "失败", new Date(), data);
    }
}
