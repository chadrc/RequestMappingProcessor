package com.chadrc.annotations.processors;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chad on 2/7/17.
 */
public class TemplateTests {

    @Test
    public void testTemplate() throws Exception{
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.init();

        String apiClassName = "TestAPI";

        ClassInfo info = new ClassInfo(apiClassName, "com.chadrc.test")
                .addMethod(new Method("myMethod", "String", "Documentation")
                        .addParameter(new Parameter("String", "param1"))
                        .addParameter(new Parameter("String", "param2")));

        VelocityContext context = new VelocityContext();
        context.put("info", info);

        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("String", "param1"));
        parameters.add(new Parameter("String", "param2"));

        context.put("parameters", parameters);

        Template template = engine.getTemplate("/APIClass.vm");

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        writer.flush();
        writer.close();

        File folder = new File("output");
        if (!folder.exists()) {
            boolean created = folder.mkdir();
            System.out.println("Folder made: " + created);
        }
        File file = new File("output/outputFile.java");
        if (file.exists()) {
            boolean deleted = file.delete();
            System.out.println("File removed: " + deleted);
        }

        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(writer.toString().getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
