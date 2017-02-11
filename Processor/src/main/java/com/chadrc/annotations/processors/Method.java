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
    private String requestMethod;
    private String url;
    private List<Parameter> parameters = new ArrayList<>();
    private ClassInfo classInfo;

    public Method(String name, String returnType, String docString, String requestMethod, String url) {
        this.name = name;
        this.returnType = returnType;
        this.docString = docString;
        this.requestMethod = requestMethod;
        this.url = url;
    }

    public Method addParameter(Parameter parameter) {
        parameters.add(parameter);
        return this;
    }

    void setClassInfo(ClassInfo info) {
        this.classInfo = info;
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

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestUrl() {
        return classInfo.getBaseUrl() + url;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public List<Parameter> getQueryParams() {
        List<Parameter> queryParams = new ArrayList<>();
        queryParams.add(parameters.get(0));
        queryParams.add(parameters.get(1));
        return queryParams;
    }

    public List<Parameter> getRouteParams() {
        List<Parameter> routeParams = new ArrayList<>();
        routeParams.add(parameters.get(2));
        return routeParams;
    }

    public boolean getHasBody() {
        return true;
    }

    public String getBodyVar() {
        return parameters.get(parameters.size()-1).getName();
    }
}
