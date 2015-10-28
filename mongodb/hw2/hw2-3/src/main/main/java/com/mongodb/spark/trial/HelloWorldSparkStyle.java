package com.mongodb.spark.trial;

import spark.*;

/**
 * Created by ronald.bhuleskar on 10/16/15.
 */
public class HelloWorldSparkStyle {
    public static void main(String[] args) {

//        Spark.get("/hello", (req, res) -> "Hello World!!!");

        Spark.get(new Route("/") {
            public Object handle(final Request request, final spark.Response response) {
                return "Hello World from Spark!";
            }
        });
    }
}
