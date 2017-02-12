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
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.io.*;
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

    private String rootUrl = "http://localhost:8080";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
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
            Map<String, ClassInfo> classRoots = new HashMap<>();

            for (Element element : classes) {
                AddClassToMap(classRoots, (TypeElement)element);
            }

            for (Element element : methods) {
                String name = AddClassToMap(classRoots, ((TypeElement) element.getEnclosingElement()));
                Method method = new Method((ExecutableElement) element);
                classRoots.get(name).addMethod(method);
            }

            for (String className : classRoots.keySet()) {
                ClassInfo info = classRoots.get(className);

                VelocityContext context = new VelocityContext();
                context.put("info", info);

                Template template = engine.getTemplate("/APIClass.vm");

                StringWriter writer = new StringWriter();
                template.merge(context, writer);

                String classPath = info.getPackageName().replace(".", "/");
                File folder = new File("src/api/java/" + classPath);
                if (!folder.exists()) {
                    boolean created = folder.mkdirs();
                    //System.out.println("Folder made: " + created);
                }
                File file = new File(folder.getAbsolutePath() + "/" + info.getName() + ".java");
                if (file.exists()) {
                    boolean deleted = file.delete();
                    //System.out.println("File removed: " + deleted);
                }

                FileOutputStream outputStream = null;
                try {
                    boolean created = file.createNewFile();
                    if (!created) {
                        throw new IOException("Couldn't create file: " + file.getAbsolutePath());
                    }
                    outputStream = new FileOutputStream(file);
                    outputStream.write(writer.toString().getBytes());
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    private String AddClassToMap(Map<String, ClassInfo> classRoots, TypeElement element) {
        String className = element.getSimpleName().toString();
        if (!classRoots.containsKey(className)) {
                classRoots.put(className, new ClassInfo(element));
        }
        return className;
    }
}
