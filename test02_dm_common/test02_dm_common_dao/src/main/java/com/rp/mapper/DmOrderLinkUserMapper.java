package com.rp.mapper;

import com.rp.pojo.DmOrderLinkUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DmOrderLinkUserMapper {

	public DmOrderLinkUser getDmOrderLinkUserById(@Param(value = "id") Long id)throws Exception;

	public List<DmOrderLinkUser>	getDmOrderLinkUserListByMap(Map<String, Object> param)throws Exception;

	public Integer getDmOrderLinkUserCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertDmOrderLinkUser(DmOrderLinkUser dmOrderLinkUser)throws Exception;

	public Integer updateDmOrderLinkUser(DmOrderLinkUser dmOrderLinkUser)throws Exception;

	public Integer deleteDmOrderLinkUserById(@Param(value = "id") Long id)throws Exception;

	public Integer batchDeleteDmOrderLinkUser(Map<String, List<String>> params);

}