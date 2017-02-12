package com.chadrc.test.models.responses;

import com.chadrc.test.models.User;

/**
 * Created by chad on 2/11/17.
 */
public class LoginResponse {
    private String userName;

    public LoginResponse() {

    }

    public LoginResponse(User user) {
        this.userName = user.getUserName();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
