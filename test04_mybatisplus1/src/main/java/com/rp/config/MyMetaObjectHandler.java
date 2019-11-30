package com.rp.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        boolean createTime = metaObject.hasSetter("createTime");
        if(createTime){
            System.out.println("insertFill =  "+ metaObject.toString());
            setInsertFieldValByName("createTime", LocalDateTime.now(),metaObject);

        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {

        Object updateTime = getFieldValByName("updateTime",metaObject);
        if(updateTime == null){
            setUpdateFieldValByName("updateTime",LocalDateTime.now(),metaObject);
            System.out.println("------updateFill-----" + metaObject.toString());

        }


    }
}
