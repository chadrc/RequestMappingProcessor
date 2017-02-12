package com.chadrc.test;

/**
 * Created by chad on 2/11/17.
 */
public class LoginResponse {
    private String userName;

    public LoginResponse(User user) {
        this.userName = user.getUserName();
    }

    public String getUserName() {
        return userName;
    }
}
