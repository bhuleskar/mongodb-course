package com.mongodb.spark.trial;

import spark.*;
import spark.Response;

/**
 * Created by ronald.bhuleskar on 10/16/15.
 */
public class SparkRoutes {
    public static void main(String[] args){
        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello World";
            }
        });

        Spark.get(new Route("/test") {
            @Override
            public Object handle(Request request, Response response) {
                return "This is a test page.";
            }
        });

        Spark.get(new Route("/echo/:thing") {
            @Override
            public Object handle(Request request, Response response) {
                return request.params(":thing");
            }
        });
    }
}
