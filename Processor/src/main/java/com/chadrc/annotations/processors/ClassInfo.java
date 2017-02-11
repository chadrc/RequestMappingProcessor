package com.chadrc.annotations.processors;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
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

    public ClassInfo(TypeElement element) {
        String className = element.getSimpleName().toString();
        int lastDot = element.getQualifiedName().toString().lastIndexOf(".");
        String packageName = element.getQualifiedName().toString().substring(0, lastDot);
        String baseUrl = "";
        RequestMapping mapping = element.getAnnotation(RequestMapping.class);
        if (mapping != null && mapping.path().length > 0) {
            baseUrl = mapping.path()[0];
        }
        this.setFields(className, packageName, baseUrl);
    }

    public ClassInfo(String name, String packageName, String baseUrl) {
        this.setFields(name, packageName, baseUrl);
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

    private void setFields(String name, String packageName, String baseUrl) {
        this.name = name;
        this.packageName = packageName;
        this.baseUrl = baseUrl;
    }
}
