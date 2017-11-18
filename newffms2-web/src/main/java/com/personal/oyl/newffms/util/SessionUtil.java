package com.personal.oyl.newffms.util;

import javax.servlet.http.HttpSession;

import com.personal.oyl.newffms.web.user.UserDto;

public class SessionUtil {
    public UserDto getLoginUser(HttpSession session) {
        return (UserDto) session.getAttribute("SESSION_USER_KEY");
    }

    private static SessionUtil instance;

    private SessionUtil() {

    }

    public static SessionUtil getInstance() {
        if (null == instance) {
            synchronized (SessionUtil.class) {
                if (null == instance) {
                    instance = new SessionUtil();
                }
            }
        }

        return instance;
    }
}
