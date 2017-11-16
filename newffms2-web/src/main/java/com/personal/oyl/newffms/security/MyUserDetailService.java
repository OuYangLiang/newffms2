package com.personal.oyl.newffms.security;

import java.sql.SQLException;
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


public class MyUserDetailService implements UserDetailsService {
    
    private static final Logger log = LoggerFactory.getLogger(MyUserDetailService.class);
    
    /*@Autowired
    private UserProfileService userProfileService;
    @Autowired
    private OperationUrlService operationUrlService;*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*UserProfile user = null;
        
        try {
            user = userProfileService.selectByLoginId(username.trim());
            
            if (null == user) {
                throw new UsernameNotFoundException(username);
            }
            
            List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
            AUTHORITIES.add(new SimpleGrantedAuthority("/accessDenied"));
            AUTHORITIES.add(new SimpleGrantedAuthority("/profile/initEdit"));
            AUTHORITIES.add(new SimpleGrantedAuthority("/profile/confirmEdit"));
            AUTHORITIES.add(new SimpleGrantedAuthority("/profile/saveEdit"));
            
            List<String> grantUrls = operationUrlService.selectUrlsByUser(user.getUserOid());
            
            for (String url : grantUrls) {
            	AUTHORITIES.add(new SimpleGrantedAuthority(url));
            }
            
            return new User(username, user.getLoginPwd(), AUTHORITIES);
            
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            
            throw new UsernameNotFoundException(username, e);
        }*/
        return new User("oyl", "3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2", new ArrayList<GrantedAuthority>());
    }

}
