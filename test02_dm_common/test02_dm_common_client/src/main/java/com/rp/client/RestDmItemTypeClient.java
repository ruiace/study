package com.rp.client;
import com.rp.pojo.DmItemType;

import com.rp.fallback.DmItemTypeClientFallBack;
import java.util.List;
import java.util.Map;

import com.rp.config.DmConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
/**
* Created by dm
*/
@FeignClient(name = "test02-dm-item-provider", configuration = DmConfiguration.class, fallback = DmItemTypeClientFallBack.class)
public interface RestDmItemTypeClient {
@RequestMapping(value = "/getDmItemTypeById",method = RequestMethod.POST)
public DmItemType getDmItemTypeById(@RequestParam("id") Long id)throws Exception;

@RequestMapping(value = "/getDmItemTypeListByMap",method = RequestMethod.POST)
public List<DmItemType>	getDmItemTypeListByMap(@RequestParam Map<String, Object> param)throws Exception;

@RequestMapping(value = "/getDmItemTypeCountByMap",method = RequestMethod.POST)
public Integer getDmItemTypeCountByMap(@RequestParam Map<String, Object> param)throws Exception;

@RequestMapping(value = "/qdtxAddDmItemType",method = RequestMethod.POST)
public Integer qdtxAddDmItemType(@RequestBody DmItemType dmItemType)throws Exception;

@RequestMapping(value = "/qdtxModifyDmItemType",method = RequestMethod.POST)
public Integer qdtxModifyDmItemType(@RequestBody DmItemType dmItemType)throws Exception;
}

