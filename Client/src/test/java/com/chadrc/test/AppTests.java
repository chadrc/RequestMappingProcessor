package com.chadrc.test;

import com.chadrc.services.api.Api;
import com.chadrc.test.models.User;
import com.chadrc.test.models.responses.LoginResponse;
import com.chadrc.test.models.responses.UserResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by chad on 2/12/17.
 */
public class AppTests {

    @BeforeClass
    public static void setup() {
        Api.Setup();
    }

    @Test
    public void testRoot() throws UnirestException {
        String response = RootController.RootRequest().getBody();
        assertEquals(response, "App Ready");
    }

    @Test
    public void testLogin() throws UnirestException {
        LoginResponse response = RootController.Login("chad", "password").getBody();
        assertEquals(response.getUserName(), "chad");
    }

    @Test
    public void testGetUser() throws UnirestException {
        UserResponse response = RootController.GetUser("chad").getBody();
        assertEquals(response.getUserName(), "chad");
    }

    @Test
    public void testEditUser() throws UnirestException {
        User user = new User("jack", "password");
        UserResponse response = RootController.EditUser("chad", user).getBody();
        assertEquals(response.getUserName(), "jack");
    }
}
