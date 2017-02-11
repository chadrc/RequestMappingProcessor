package com.chadrc.annotations.processors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chad on 2/7/17.
 */

public class Method {
    private String name;
    private String returnType;
    private String docString;
    private List<Parameter> parameters = new ArrayList<>();

    public Method(String name, String returnType, String docString) {
        this.name = name;
        this.returnType = returnType;
        this.docString = docString;
    }

    public Method addParameter(Parameter parameter) {
        parameters.add(parameter);
        return this;
    }

    public String getDocString() {
        return docString;
    }

    public String getName() {
        return name;
    }

    public String getReturnType() {
        return returnType;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }
}
