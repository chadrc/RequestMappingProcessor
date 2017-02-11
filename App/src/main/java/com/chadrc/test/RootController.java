package com.chadrc.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chad on 2/1/17.
 */

@RestController
@RequestMapping("/api")
public class RootController {

    @RequestMapping("/root")
    public String RootRequest() {
        return "App Ready";
    }
}
