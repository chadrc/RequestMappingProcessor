package com.chadrc.annotations.processors;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by chad on 2/1/17.
 */

@SupportedAnnotationTypes(
        "org.springframework.web.bind.annotation.RequestMapping"
)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RequestMappingProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.init();

        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

            Map<Boolean, List<Element>> annotatedMethods = annotatedElements.stream().collect(
                    Collectors.partitioningBy(element -> element.getKind() == ElementKind.METHOD)
            );

            Map<Boolean, List<Element>> annotatedClasses = annotatedElements.stream().collect(
                    Collectors.partitioningBy(element -> element.getKind() == ElementKind.CLASS)
            );

            List<Element> classes = annotatedClasses.get(true);
            List<Element> methods = annotatedMethods.get(true);
            Map<String, String> classRoots = new HashMap<>();

            for (Element element : classes) {
                String className = element.getSimpleName().toString();
                if (!classRoots.containsKey(className)) {
                    classRoots.put(className, className);
                }
            }

            for (Element element : methods) {
                String className = element.getEnclosingElement().getSimpleName().toString();
                if (!classRoots.containsKey(className)) {
                    classRoots.put(className, className);
                }
            }

            for (String className : classRoots.keySet()) {
                String apiClassName = className + "API";
                VelocityContext context = new VelocityContext();
                context.put("className", apiClassName);
                try {
                    Template template = engine.getTemplate("/APIClass.vm");
                    JavaFileObject file = processingEnv.getFiler().createSourceFile(apiClassName);

                    PrintWriter writer = new PrintWriter(file.openWriter());
                    template.merge(context, writer);
                    writer.flush();
                    writer.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }

        return false;
    }
}
