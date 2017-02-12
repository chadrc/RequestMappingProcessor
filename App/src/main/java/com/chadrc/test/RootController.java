package com.chadrc.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chad on 2/1/17.
 */

@RestController
@RequestMapping(path="/api")
public class RootController {

    @RequestMapping(path="/root", method= RequestMethod.OPTIONS)
    public String RootRequest() {
        return "App Ready";
    }
}
