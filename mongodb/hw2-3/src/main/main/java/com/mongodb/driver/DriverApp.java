package com.mongodb.driver;

import static com.mongodb.util.Helpers.printJson;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;

/**
 * Created by ronald.bhuleskar on 10/21/15.
 */
public class DriverApp {
    public static void main(String[] args){
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
        MongoClient client = new MongoClient(new ServerAddress(), options);

        MongoDatabase db = client.getDatabase("test").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> collection = db.getCollection("source");

        collection.drop();

        Document smith = new Document("name", "Smith")
                .append("age", 30)
                .append("profession", "programmer");
        collection.insertOne(smith);

        Document jones = new Document("name", "Jones")
                .append("age", 40)
                .append("profession", "hacker");
        collection.insertMany(Arrays.asList(jones));

        printJson(smith);
        printJson(jones);
    }
}
