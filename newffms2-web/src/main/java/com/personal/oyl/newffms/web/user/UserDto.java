package com.personal.oyl.newffms.web.user;

import java.io.Serializable;
import java.math.BigDecimal;

import com.personal.oyl.newffms.user.domain.Gender;
import com.personal.oyl.newffms.user.domain.User;

@SuppressWarnings("serial")
public class UserDto implements Serializable {
    private BigDecimal userOid;
    private String userName;
    private String userAlias;
    private Gender gender;
    private String genderDesc;
    private String phone;
    private String email;
    private String icon;
    private String remarks;
    private String loginId;
    private String loginPwd;
    private BigDecimal userTypeOid;

    public UserDto() {

    }

    public UserDto(User user) {
        this.setUserOid(user.getKey().getUserOid());
        this.setUserName(user.getUserName());
        this.setUserAlias(user.getUserAlias());
        this.setGender(user.getGender());
        this.setGenderDesc(user.getGender().getDesc());
        this.setPhone(user.getPhone());
        this.setEmail(user.getEmail());
        this.setIcon(user.getIcon());
        this.setRemarks(user.getRemarks());
        this.setLoginId(user.getLoginId());
        this.setLoginPwd(user.getLoginPwd());
        this.setUserTypeOid(user.getUserTypeOid());
    }

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getGenderDesc() {
        return genderDesc;
    }

    public void setGenderDesc(String genderDesc) {
        this.genderDesc = genderDesc;
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
}
