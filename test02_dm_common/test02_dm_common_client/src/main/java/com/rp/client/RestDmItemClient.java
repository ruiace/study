package com.rp.client;
import com.rp.pojo.DmItem;

import com.rp.fallback.DmItemClientFallBack;
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
@FeignClient(name = "test02-dm-item-provider", configuration = DmConfiguration.class, fallback = DmItemClientFallBack.class)
public interface RestDmItemClient {
@RequestMapping(value = "/getDmItemById",method = RequestMethod.POST)
public DmItem getDmItemById(@RequestParam("id") Long id)throws Exception;

@RequestMapping(value = "/getDmItemListByMap",method = RequestMethod.POST)
public List<DmItem>	getDmItemListByMap(@RequestParam Map<String, Object> param)throws Exception;

@RequestMapping(value = "/getDmItemCountByMap",method = RequestMethod.POST)
public Integer getDmItemCountByMap(@RequestParam Map<String, Object> param)throws Exception;

@RequestMapping(value = "/qdtxAddDmItem",method = RequestMethod.POST)
public Integer qdtxAddDmItem(@RequestBody DmItem dmItem)throws Exception;

@RequestMapping(value = "/qdtxModifyDmItem",method = RequestMethod.POST)
public Integer qdtxModifyDmItem(@RequestBody DmItem dmItem)throws Exception;
}

