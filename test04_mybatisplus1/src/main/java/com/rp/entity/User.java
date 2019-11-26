package com.rp.entity;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mp_user")
public class User extends Model<User> {

    //主键
    @TableId
    private Long id;

    //姓名
    @TableField(value = "name",condition = SqlCondition.LIKE)
    private String userName;

    //年龄
    //@TableField(condition = "%s&gt;#{%s}")
    private Integer age;

    //邮箱
    private String email;

    //直属上级id
    private Long managerId;

    //创建时间
    private LocalDateTime createTime;

    //描述
    /**
    private transient  String desc;
    public static String desc;

    public static String getDesc() {
        return desc;
    }

    public static void setDesc(String desc) {
        User.desc = desc;
    }

     */

    @TableField(exist = false)
    private String desc;



}
