package com.zhengpp.padis.entity;

import java.io.Serializable;

/**
 * Created by zpp360 on 2019/7/7.
 */
public class User implements Serializable{

    private static final long serialVersionUID = 6741731290734293215L;

    private String userId;

    private String userName;

    private String userPhone;

    private String userRoele; //1公安 2移动

    private String userUnit;

    private String userNumber;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserRoele() {
        return userRoele;
    }

    public void setUserRoele(String userRoele) {
        this.userRoele = userRoele;
    }

    public String getUserUnit() {
        return userUnit;
    }

    public void setUserUnit(String userUnit) {
        this.userUnit = userUnit;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userRoele='" + userRoele + '\'' +
                ", userUnit='" + userUnit + '\'' +
                ", userNumber='" + userNumber + '\'' +
                '}';
    }
}
