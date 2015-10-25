package com.mongodb.spark.trial;

import com.mongodb.freemarker.HelloWorldFreemarkerStyle;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.*;
import spark.Response;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ronald.bhuleskar on 10/17/15.
 */
public class SparkFormHandling {
    public static void main(String[] args){
        final Configuration configuration = new Configuration(new Version("2.3.23"));
        configuration.setClassForTemplateLoading(HelloWorldFreemarkerStyle.class, "/");

//        Spark.get("/hello", (req, res) -> "Hello World!!!");

        Spark.get(new Route("/") {
            public Object handle(final Request request, final spark.Response response) {
                StringWriter writer = new StringWriter();
                try {

                    Map<String, Object> fruitMap = new HashMap();
                    fruitMap.put("fruits",
                            Arrays.asList("apple", "orange", "banana", "peach"));

                    Template fruitPickerTemplate = configuration.getTemplate("fruitPicker.ftl");
                    fruitPickerTemplate.process(fruitMap, writer);
                } catch (Exception e) {
                    halt(500);
                    e.printStackTrace();
                }
                return writer;
            }
        });

        Spark.post(new Route("/favorite_fruit") {
            @Override
            public Object handle(Request request, Response response) {
                String fruit = request.queryParams("fruit");
                if (fruit == null) {
                    return "Why dont you pick a fruit?";
                }

                return "Woww, " + fruit +"!";
            }
        });

    }
}
