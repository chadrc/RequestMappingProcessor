package com.chadrc.annotations.processors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chad on 2/6/17.
 */

public class ClassInfo {
    private String name;
    private String packageName;
    private String baseUrl;
    private List<Method> methods = new ArrayList<>();

    public ClassInfo(String name, String packageName, String baseUrl) {
        this.name = name;
        this.packageName = packageName;
        this.baseUrl = baseUrl;
    }

    public ClassInfo addMethod(Method method) {
        methods.add(method);
        method.setClassInfo(this);
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<Method> getMethods() {
        return methods;
    }
}
