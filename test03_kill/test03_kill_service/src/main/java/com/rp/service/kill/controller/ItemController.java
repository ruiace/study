package com.rp.service.kill.controller;


import com.rp.service.kill.dto.KillDto;
import com.rp.service.kill.dto.Result;
import com.rp.service.kill.entity.ItemKill;
import com.rp.service.kill.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @GetMapping(value = {"/item/list", "/", "/index", "index.html"})
    public String itemList(ModelMap map) {
        try {
            List<ItemKill> list = itemService.selectAll();
            //int i = 1 /0 ;
            map.put("list", list);
        } catch (Exception e) {
            // e.printStackTrace();
            return "redirect:/error";
        }
        return "list";
    }

    @GetMapping("/error")
    public String error() {
        System.out.println("=============error=============");
        return "error";

    }


    @GetMapping("/item/detail/{id}")
    public String itemDetail(@PathVariable Integer id, ModelMap map) {
        if (id == null || id < 0) {
            return "redirect:error";
        }

        try {
            ItemKill itemKill = itemService.selectDetailById(id);
            map.put("detail", itemKill);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:error";
        }


        return "info";

    }

    @PostMapping("kill/execute")
    @ResponseBody
    public Result excute(@RequestBody @Validated KillDto killDto, BindingResult result, HttpSession session) {

        if (result.hasErrors() || killDto.getKillId() < 0) {
            return Result.fail();
        }

        Object uid = session.getAttribute("uid");

        if(uid ==null){
            return Result.fail();

        }

        Integer id = (Integer)uid;
        killDto.setUserId(id);

        Result re  = itemService.killItem(killDto);
        return re;

    }


    @GetMapping("/kill/execute/success")
    public String success() {
        return "executeSuccess";
    }


    @GetMapping("/kill/execute/fail")
    public String fail() {
        return "executeFail";
    }
}

