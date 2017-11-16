package com.personal.oyl.newffms.util;

import java.math.BigDecimal;

public class UserProfile extends BasePojo {
    private static final long serialVersionUID = 1L;
    private BigDecimal userOid;
    private String userName;
    private String userAlias;
    private String phone;
    private String email;
    private String icon;
    private String remarks;
    private String loginId;
    private String loginPwd;
    private BigDecimal userTypeOid;

    // extended field
    private Boolean changePwd;
    private String loginPwdOrigin;
    private String loginPwdNew;
    private String loginPwdConfirm;

    public BigDecimal getUserOid() {
        return userOid;
    }

    public void setUserOid(BigDecimal userOid) {
        this.userOid = userOid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public BigDecimal getUserTypeOid() {
        return userTypeOid;
    }

    public void setUserTypeOid(BigDecimal userTypeOid) {
        this.userTypeOid = userTypeOid;
    }

    public Boolean getChangePwd() {
        return changePwd;
    }

    public void setChangePwd(Boolean changePwd) {
        this.changePwd = changePwd;
    }

    public String getLoginPwdOrigin() {
        return loginPwdOrigin;
    }

    public void setLoginPwdOrigin(String loginPwdOrigin) {
        this.loginPwdOrigin = loginPwdOrigin;
    }

    public String getLoginPwdNew() {
        return loginPwdNew;
    }

    public void setLoginPwdNew(String loginPwdNew) {
        this.loginPwdNew = loginPwdNew;
    }

    public String getLoginPwdConfirm() {
        return loginPwdConfirm;
    }

    public void setLoginPwdConfirm(String loginPwdConfirm) {
        this.loginPwdConfirm = loginPwdConfirm;
    }

}
