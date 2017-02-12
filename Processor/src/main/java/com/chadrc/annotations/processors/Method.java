package com.chadrc.annotations.processors;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chad on 2/7/17.
 */

public class Method {
    private static List<TypeKind> supportedTypes = Arrays.asList(
            TypeKind.BOOLEAN,
            TypeKind.BYTE,
            TypeKind.CHAR,
            TypeKind.SHORT,
            TypeKind.INT,
            TypeKind.LONG,
            TypeKind.FLOAT,
            TypeKind.DOUBLE,
            TypeKind.VOID,
            TypeKind.DECLARED
    );

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

    public Method(ExecutableElement element) {
        this.name = element.getSimpleName().toString();
        this.returnType = "";
        this.docString = "";
        if (supportedTypes.contains(element.getReturnType().getKind())) {
            if (element.getReturnType() instanceof DeclaredType) {
                this.returnType = ((DeclaredType)element.getReturnType()).asElement().getSimpleName().toString();
            } else {
                System.out.println("Could not case return type: " + element.getReturnType().toString());
            }
        }

        RequestMapping mapping = element.getAnnotation(RequestMapping.class);
        this.url = "/";
        this.requestMethod = "get";
        if (mapping != null) {
            if (mapping.path().length > 0) {
                this.url = mapping.path()[0];
            }

            if (mapping.method().length > 0) {
                this.requestMethod = mapping.method()[0].toString();
            }
        }
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
        return queryParams;
    }

    public List<Parameter> getRouteParams() {
        List<Parameter> routeParams = new ArrayList<>();
        return routeParams;
    }

    public boolean getHasBody() {
        return false;
    }

    public String getBodyVar() {
        return "";
    }
}
