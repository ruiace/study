package com.rp.service;


import com.rp.common.Dto;
import com.rp.vo.ItemDetailVo;

public interface ItemDetailService {
    public Dto<ItemDetailVo> queryItemDetail(Long id)throws  Exception;
}
