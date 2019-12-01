package com.rp.service.kill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    public static Result success(){
        return new Result(200,"成功",null);
    }

    public static Result fail(){
        return new Result(400,"失败",null);
    }

    public static <T>Result success(T t){
        return new Result(200,"成功",t);
    }

    public static <T>Result fail(T t){
        return new Result(400,"失败",t);
    }

    public static <T>Result fail(String msg,T t){
        return new Result(400,msg,t);
    }
}
