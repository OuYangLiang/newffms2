package com.personal.oyl.newffms.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.personal.oyl.newffms.user.domain.UserException.UserKeyEmptyException;
import com.personal.oyl.newffms.user.domain.UserException.UserLoginIdEmptyException;
import com.personal.oyl.newffms.user.domain.UserRepos;

public class MyUserDetailService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(MyUserDetailService.class);

    @Autowired
    private UserRepos repos;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.personal.oyl.newffms.user.domain.User user = null;

        try {
            user = repos.userOfLoginId(username.trim());
        } catch (UserLoginIdEmptyException e) {
            log.error(e.getErrorCode(), e);
        }

        if (null == user) {
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> autuorities = new ArrayList<GrantedAuthority>();
        autuorities.add(new SimpleGrantedAuthority("/accessDenied"));
        autuorities.add(new SimpleGrantedAuthority("/consumption/monthlyConsumptionTotal"));
        autuorities.add(new SimpleGrantedAuthority("/profile/initEdit"));
        autuorities.add(new SimpleGrantedAuthority("/profile/confirmEdit"));
        autuorities.add(new SimpleGrantedAuthority("/profile/saveEdit"));

        List<String> grantUrls = null;
        try {
            grantUrls = repos.queryUrlsByUser(user.getKey());
        } catch (UserKeyEmptyException e) {
            log.error(e.getErrorCode(), e);
        }

        if (null != grantUrls) {
            for (String url : grantUrls) {
                autuorities.add(new SimpleGrantedAuthority(url));
            }
        }

        return new User(username, user.getLoginPwd(), autuorities);
    }

}
