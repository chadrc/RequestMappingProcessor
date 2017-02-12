package com.chadrc.test;

import com.chadrc.test.models.responses.LoginResponse;
import com.chadrc.test.models.User;
import com.chadrc.test.models.responses.UserResponse;
import org.springframework.web.bind.annotation.*;

/**
 * Created by chad on 2/1/17.
 */

@RestController
@RequestMapping(path="/api")
public class RootController {

    @RequestMapping(path="/root", method = RequestMethod.OPTIONS)
    public String RootRequest() {
        return "App Ready";
    }

    @RequestMapping(path="/login", method = RequestMethod.POST)
    public LoginResponse Login(String userName, String password) {
        return new LoginResponse(new User(userName, password));
    }

    @RequestMapping(path="/user", method = RequestMethod.GET)
    public UserResponse GetUser(@RequestParam String name) {
        return new UserResponse(new User(name, ""));
    }

    @RequestMapping(path="/user/{name}", method = RequestMethod.PATCH)
    public UserResponse EditUser(@PathVariable String name, @RequestBody User user) {
        return new UserResponse(user);
    }
}
