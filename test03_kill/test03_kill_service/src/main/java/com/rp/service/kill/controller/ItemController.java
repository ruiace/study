package com.rp.service.kill.controller;


import com.rp.service.kill.entity.Item;
import com.rp.service.kill.entity.ItemKill;
import com.rp.service.kill.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author rp
 * @since 2019-11-29
 */
@Controller
public class ItemController {

    @Autowired
    private IItemService itemService;

    @GetMapping(value = {"/item/list","/","/index","index.html"})
    public String itemList(ModelMap map){
        try {
            List<ItemKill> list = itemService.selectAll();
            int i = 1 /0 ;
            map.put("list",list);
        } catch (Exception e) {
           // e.printStackTrace();
            return "redirect:/error";
        }
        return "list";
    }

    @GetMapping("/error")
    public String error(){
        System.out.println("=============error=============");
        return "error";

    }
}
