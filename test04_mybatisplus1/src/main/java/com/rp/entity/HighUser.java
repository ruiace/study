package com.rp.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Data
@TableName("high_user")
public class HighUser extends Model<HighUser> {

    //主键
    @TableId
    private Long id;

    //姓名
    //@TableField(value = "name",condition = SqlCondition.LIKE)
    private String name;

    //年龄
    //@TableField(condition = "%s&gt;#{%s}")
    private Integer age;

    //邮箱
    private String email;

    //直属上级id
    private Long managerId;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;



    //修改时间
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    //'版本'
    @Version
    private Integer version;

    //逻辑删除标识(0.未删除,1.已删除)
    @TableLogic
    @TableField(select = false)
    private Integer deleted;

}
