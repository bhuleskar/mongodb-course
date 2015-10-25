package com.mongodb.spark;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.freemarker.HelloWorldFreemarkerStyle;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.bson.Document;
import spark.Request;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ronald.bhuleskar on 10/22/15.
 */
public class MondoDBSparkFreemarkerStyle {
    public static void main(String[] args) {

        final Configuration configuration = new Configuration(new Version("2.3.23"));
        configuration.setClassForTemplateLoading(HelloWorldFreemarkerStyle.class, "/");

        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("test");
        final MongoCollection<Document> collection = db.getCollection("findWithFilterTest");

        collection.drop();
        collection.insertOne(new Document("name", "MongoDb!"));

        Spark.get(new Route("/") {
            public Object handle(final Request request, final spark.Response response) {
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = configuration.getTemplate("hello.ftl");
                    Map<String, Object> helloMap = new HashMap();
                    Document document = collection.find().first();

                    helloTemplate.process(document, writer);
                } catch (Exception e) {
                    halt(500);
                    e.printStackTrace();
                }
                return writer;
            }
        });
    }
}
