package com.chadrc.test.models.responses;

import com.chadrc.test.models.User;

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
