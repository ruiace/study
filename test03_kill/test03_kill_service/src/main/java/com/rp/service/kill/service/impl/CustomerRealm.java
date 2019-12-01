package com.rp.service.kill.service.impl;

import com.google.common.collect.Collections2;
import com.rp.service.kill.entity.User;
import com.rp.service.kill.mapper.UserMapper;
import com.sun.xml.internal.messaging.saaj.soap.SOAPVersionMismatchException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Slf4j
public class CustomerRealm extends AuthorizingRealm {


    /**
     * 授权
     * @param principalCollection
     * @return
     */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }





    @Autowired
    private UserMapper userMapper;
    /**
     * 认证登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String password = String.valueOf(token.getPassword());
        String username = token.getUsername();
        log.info("===========password ={},username = {}=================");

        Map<String, Object> map = new HashMap<>();
        map.put("user_name",username);
        List<User> users = userMapper.selectByMap(map);
        if(users ==null || users.size() ==0){

            throw  new UnknownAccountException("用户名不存在!");
        }
        if(!Objects.equals(1,users.get(0).getIsActive())){
            throw new DisabledAccountException("用户已经被禁用");
        }

        if(!users.get(0).getPassword().equals(password)){
            throw new IncorrectCredentialsException("用户名和密码不匹配");
        }

        SimpleAuthenticationInfo info= new SimpleAuthenticationInfo(users.get(0),password,this.getName());
        setSession("uid",users.get(0).getId());
        return info;
    }



    public void setSession(String key,Object value){
        Session session = SecurityUtils.getSubject().getSession();
        if(session != null){
            session.setAttribute(key,value);
            session.setTimeout(300000L);
        }
    }

    public static void main(String[] args) throws Exception{
        String rp = new Md5Hash("123456", "rp").toString();
        System.out.println(rp);

    }
}
