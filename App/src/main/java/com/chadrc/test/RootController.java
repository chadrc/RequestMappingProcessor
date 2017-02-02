package com.chadrc.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chad on 2/1/17.
 */

@RestController
public class RootController {

    @RequestMapping("/")
    public String RootRequest() {
        return "App Ready";
    }
}
