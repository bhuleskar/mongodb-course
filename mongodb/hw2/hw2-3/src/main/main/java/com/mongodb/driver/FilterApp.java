package com.mongodb.driver;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.Helpers;

/**
 * Created by ronald.bhuleskar on 10/22/15.
 */
public class FilterApp {
    public static void main(String[] args) {
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
        MongoClient client = new MongoClient(new ServerAddress(), options);

        MongoDatabase db = client.getDatabase("test").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> collection = db.getCollection("findWithFilterTest");

        collection.drop();

        //insert 10 docs
        for(int i=0; i<100; i++) {
            collection.insertOne(new Document()
                    .append("x", new Random().nextInt(2))
                    .append("y", new Random().nextInt(100))
                    .append("i", i));
        }

        //FILTER
        Bson filter = and(/*eq("x", 0), */gt("y", 10), lt("y", 90));

        //PROJECT
//        Bson projection = new Document("x", 1).append("y", 1).append("_id", 0);
        Bson projection = fields(include("x", "y", "i"), excludeId());

//        Bson sort = new Document("i", 1).append("y", -1);
        Bson sort = orderBy(ascending("x"), descending("y"));

        List<Document> all = collection.find(filter)
                .projection(projection)
                .sort(sort)
                .skip(40)
                .limit(50)
                .into(new ArrayList<Document>());

        for(Document document : all) {
            Helpers.printJson(document);
        }

    }
}
