package com.chadrc.annotations.processors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chad on 2/6/17.
 */

public class ClassInfo {
    private String name = "";
    private String packageName = "";
    private List<Method> methods = new ArrayList<>();

    public ClassInfo(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
    }

    public ClassInfo addMethod(Method method) {
        methods.add(method);
        return this;
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
