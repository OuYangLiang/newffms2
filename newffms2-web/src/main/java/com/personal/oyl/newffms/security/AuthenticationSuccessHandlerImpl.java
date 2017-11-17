package com.personal.oyl.newffms.security;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.personal.oyl.newffms.user.domain.Module;
import com.personal.oyl.newffms.user.domain.User;
import com.personal.oyl.newffms.user.domain.UserException.UserKeyEmptyException;
import com.personal.oyl.newffms.user.domain.UserException.UserLoginIdEmptyException;
import com.personal.oyl.newffms.user.domain.UserRepos;
import com.personal.oyl.newffms.util.Constants;


public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);
    
    @Autowired
    private UserRepos repos;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        User user = null;
        try {
            user = repos.userOfLoginId(authentication.getName());
        } catch (UserLoginIdEmptyException e) {
            log.error(e.getErrorCode(), e);
        }
        
        //如果以后涉及子菜单，这里就需要修改一下。
        List<Module> menus = null;
        try {
            menus = repos.queryMenusByUser(user.getKey());
        } catch (UserKeyEmptyException e) {
            log.error(e.getErrorCode(), e);
        }
        
        request.getSession().setAttribute(Constants.SESSION_USER_KEY, user);
        request.getSession().setAttribute(Constants.SESSION_MENU_KEY, menus);
        
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, "/welcome");
    }

}
