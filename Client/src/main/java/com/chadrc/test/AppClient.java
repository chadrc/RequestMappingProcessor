package com.chadrc.test;

import com.chadrc.services.api.ApiProperties;
import com.chadrc.test.models.responses.LoginResponse;

/**
 * Created by chad on 2/11/17.
 */
public class AppClient {

    public static void main(String[] args) throws Exception {
        System.out.println("Root api url: " + ApiProperties.getRootUrl("com.chadrc.test.RootController"));
//        LoginResponse response = RootController.Login("chad", "password").getBody();
    }
}
