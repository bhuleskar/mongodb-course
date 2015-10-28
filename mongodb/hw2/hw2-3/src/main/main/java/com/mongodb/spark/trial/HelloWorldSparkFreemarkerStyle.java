package com.mongodb.spark.trial;

import com.mongodb.freemarker.HelloWorldFreemarkerStyle;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.*;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ronald.bhuleskar on 10/16/15.
 */
public class HelloWorldSparkFreemarkerStyle {

    public static void main(String[] args) {

        final Configuration configuration = new Configuration(new Version("2.3.23"));
        configuration.setClassForTemplateLoading(HelloWorldFreemarkerStyle.class, "/");

//        Spark.get("/hello", (req, res) -> "Hello World!!!");

        Spark.get(new Route("/") {
            public Object handle(final Request request, final spark.Response response) {
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = configuration.getTemplate("hello.ftl");
                    Map<String, Object> helloMap = new HashMap();
                    helloMap.put("name", "freemarker");

                    helloTemplate.process(helloMap, writer);
                }
                catch (Exception e) {
                    halt(500);
                    e.printStackTrace();
                }
                return writer;
            }
        });
    }
}
