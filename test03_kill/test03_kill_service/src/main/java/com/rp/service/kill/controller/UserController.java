package com.rp.service.kill.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author rp
 * @since 2019-11-29
 */
@Controller
@RequestMapping("/kill/user")
@Slf4j
public class UserController {


    @GetMapping(value = {"/to/login", "unauth"})
    public String toLogin() {
        return "login";
    }

    @GetMapping(value = {"/login"})
    public String Login(@RequestParam String userName, @RequestParam String password, ModelMap map) {
        String errorMsg = "";

        try {

            boolean authenticated = SecurityUtils.getSubject().isAuthenticated();

            if (!authenticated) {
                UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
                SecurityUtils.getSubject().login(token);
            }


//
//            throw  new UnknownAccountException("用户名不存在!");
//        }
//        if(!Objects.equals(1,users.get(0).getIsActive())){
//            throw new DisabledAccountException("用户已经被禁用");
//        }
//
//        if(!users.get(0).getPassword().equals(password)){
//            throw new IncorrectCredentialsException("用户名和密码不匹配");
//        }


        } catch (UnknownAccountException e) {
            errorMsg = "用户名不存在";

        } catch (DisabledAccountException e) {
            errorMsg = "用户已经被禁用";

        } catch (IncorrectCredentialsException e) {
            errorMsg = "用户名和密码不匹配";

        } catch (Exception e) {
            errorMsg = "登录异常请联系管理员";
            e.printStackTrace();
        }

        if (StringUtils.isBlank(errorMsg)) {
            return "redirect:/index";
        } else {
            map.addAttribute("errorMsg", errorMsg);
            return "login";
        }
    }


}
