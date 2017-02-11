package com.chadrc.annotations.processors;

/**
 * Created by chad on 2/7/17.
 */

public class Parameter {
    private String type;
    private String name;

    Parameter(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }
}
