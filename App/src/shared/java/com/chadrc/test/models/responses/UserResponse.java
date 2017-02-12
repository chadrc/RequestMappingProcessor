package com.chadrc.test.models.responses;

import com.chadrc.test.models.User;

/**
 * Created by chad on 2/11/17.
 */
public class UserResponse {
    private String userName;

    public UserResponse() {
    }

    public UserResponse(User user) {
        this.userName = user.getUserName();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
