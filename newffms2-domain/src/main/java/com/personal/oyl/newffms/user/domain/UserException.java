package com.personal.oyl.newffms.user.domain;

import com.personal.oyl.newffms.common.NewffmsDomainException;

@SuppressWarnings("serial")
public class UserException {
    public static class UserKeyEmptyException extends NewffmsDomainException {
        public UserKeyEmptyException() {
            super("EFFMS501", "用户id不能为空。");
        }
    }
    
    public static class UserLoginIdEmptyException extends NewffmsDomainException {
        public UserLoginIdEmptyException() {
            super("EFFMS502", "login id不能为空。");
        }
    }
}
