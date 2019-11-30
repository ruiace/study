package com.rp.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.rp.entity.HighUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HighUserMapper extends BaseMapper<HighUser> {

    @Select("select * from high_user ${ew.customSqlSegment}")
    List<HighUser> selectMyList(@Param(Constants.WRAPPER) Wrapper wrapper);
}
