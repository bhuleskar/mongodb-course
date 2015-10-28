package com.mongodb.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ronald.bhuleskar on 10/16/15.
 */
public class HelloWorldFreemarkerStyle {

    public static void main(String[] args){
        Configuration configuration = new Configuration(new Version("2.3.23"));

        configuration.setClassForTemplateLoading(HelloWorldFreemarkerStyle.class, "/");

        try {
            Template helloTemplate = configuration.getTemplate("hello.ftl");
            StringWriter writer = new StringWriter();
            Map<String, Object> helloMap = new HashMap();
            helloMap.put("name", "freemarker");

            helloTemplate.process(helloMap, writer);
            System.out.print(writer);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}