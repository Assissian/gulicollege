package com.atcwl.gulicollege.interceptor;

import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.util.JwtUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
public class AuthenticationInterceptor implements WebRequestInterceptor {
    @Override
    public void preHandle(WebRequest request) throws Exception {
        String token = request.getHeader("token");
        boolean check = JwtUtils.checkToken(token);
        if (!check)
            throw new GuliException(30002, "请先登录");
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {

    }
}
