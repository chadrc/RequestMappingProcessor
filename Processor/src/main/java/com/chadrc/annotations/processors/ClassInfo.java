package com.chadrc.annotations.processors;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by chad on 2/6/17.
 */

public class ClassInfo {
    private String name;
    private String packageName;
    private String baseUrl;
    private List<Method> methods = new ArrayList<>();
    private Collection<String> imports = new HashSet<>();

    public ClassInfo(TypeElement element, String rootUrl) {
        String className = element.getSimpleName().toString();
        int lastDot = element.getQualifiedName().toString().lastIndexOf(".");
        String packageName = element.getQualifiedName().toString().substring(0, lastDot);
        String baseUrl = rootUrl;
        RequestMapping mapping = element.getAnnotation(RequestMapping.class);
        if (mapping != null && mapping.path().length > 0) {
            baseUrl += mapping.path()[0];
        }
        this.setFields(className, packageName, baseUrl);
    }

    public ClassInfo(String name, String packageName, String baseUrl) {
        this.setFields(name, packageName, baseUrl);
    }

    public ClassInfo addMethod(Method method) {
        methods.add(method);
        method.setClassInfo(this);
        imports.addAll(method.getClassRegister().stream()
                .filter(item -> !item.startsWith("java."))
                .collect(Collectors.toList())
        );
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

    public Collection<String> getImports() {
        return imports;
    }

    private void setFields(String name, String packageName, String baseUrl) {
        this.name = name;
        this.packageName = packageName;
        this.baseUrl = baseUrl;
    }
}
