package com.personal.oyl.newffms.util;

import javax.servlet.http.HttpSession;


public class SessionUtil {
    public UserProfile getLoginUser(HttpSession session) {
        return (UserProfile) session.getAttribute("SESSION_USER_KEY");
    }
    
    private static SessionUtil instance;
    private SessionUtil() {
        
    }
    
    public static SessionUtil getInstance() {
        if (null == instance) {
            synchronized(SessionUtil.class) {
                if (null == instance) {
                    instance = new SessionUtil();
                }
            }
        }
        
        return instance;
    }
}
