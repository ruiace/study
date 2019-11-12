package com.rp.test01_dm_gateway_zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RefreshScope
public class PreFilter extends ZuulFilter {

    @Value("${token:false}")
    private boolean token;


    @Override
    public String filterType() { //类型
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() { // 同类型之间的优先级，数越大越低
        return 0;
    }

    @Override
    public boolean shouldFilter() { // 过滤器是否起作用
        return true;
    }


    //object 没有实际意义
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        System.out.println("=================" + token + "===========================");

        String key = request.getParameter("key");
        System.out.println(key + " access filter 1");
        ctx.set("thirdfilter", true);
        if ("1".equals(key)) {
            ctx.setSendZuulResponse(false);
        } else if ("".equals(key)) {
            ctx.set("thirdfilter", false);
        }
        return null;
//        if (token) {
//            String token = request.getHeader("token");
//            if (token == null || token.equals("")) {
//                ctx.setSendZuulResponse(false);
//                ctx.setResponseStatusCode(401);
//                ctx.setResponseBody("{\"msg\":\"401, access without permission , pls login first.\"}");
//                return "access denied";
//            }
//        }
        //return "pass";
    }
}
