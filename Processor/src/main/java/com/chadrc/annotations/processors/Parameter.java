package com.chadrc.annotations.processors;

/**
 * Created by chad on 2/7/17.
 */

public class Parameter {
    private String type;
    private String name;
    private String varName;

    Parameter(String type, String name) {
        this(type, name, name);
    }

    Parameter(String type, String name, String varName) {
        this.type = type;
        this.name = name;
        this.varName = varName;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getVarName() {
        return this.varName;
    }
}
