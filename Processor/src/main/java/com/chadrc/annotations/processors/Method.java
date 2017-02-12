package com.chadrc.annotations.processors;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import java.util.*;

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
    private Parameter bodyParameter = null;
    private List<Parameter> parameters = new ArrayList<>();
    private List<Parameter> queryParams = new ArrayList<>();
    private List<Parameter> routeParams = new ArrayList<>();
    private List<Parameter> fields = new ArrayList<>();
    private ClassInfo classInfo;
    private Collection<String> classRegister = new HashSet<>();

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
                TypeElement typeElement = (TypeElement)((DeclaredType)element.getReturnType()).asElement();
                this.returnType = typeElement.getSimpleName().toString();
                classRegister.add(typeElement.getQualifiedName().toString());
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

        for (VariableElement variableElement : element.getParameters()) {
            String name = variableElement.getSimpleName().toString();
            TypeElement typeElement = (TypeElement)((DeclaredType)variableElement.asType()).asElement();
            String type = typeElement.getSimpleName().toString();
            String varName = name;
            List<Parameter> elementList = fields;
            PathVariable pathVariable = variableElement.getAnnotation(PathVariable.class);
            RequestParam requestParam = variableElement.getAnnotation(RequestParam.class);
            RequestBody requestBody = variableElement.getAnnotation(RequestBody.class);
            if (pathVariable != null) {
                varName = pathVariable.name();
                elementList = routeParams;
            } else if (requestParam != null) {
                varName = requestParam.name();
                elementList = queryParams;
            } else if (requestBody != null) {
                elementList = null;
            }

            if (StringUtils.isBlank(varName)) {
                varName = name;
            }

            Parameter parameter = new Parameter(type, name, varName);
            if (elementList != null) {
                elementList.add(parameter);
            } else {
                bodyParameter = parameter;
            }
            parameters.add(parameter);
            classRegister.add(typeElement.getQualifiedName().toString());
        }
    }

    public Method addParameter(Parameter parameter) {
        parameters.add(parameter);
        return this;
    }

    void setClassInfo(ClassInfo info) {
        this.classInfo = info;
    }

    Collection<String> getClassRegister() {
        return this.classRegister;
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
        return queryParams;
    }

    public List<Parameter> getRouteParams() {
        return routeParams;
    }

    public List<Parameter> getFields() {
        return fields;
    }

    public boolean getHasBody() {
        return bodyParameter != null;
    }

    public String getBodyVar() {
        return bodyParameter.getName();
    }
}
