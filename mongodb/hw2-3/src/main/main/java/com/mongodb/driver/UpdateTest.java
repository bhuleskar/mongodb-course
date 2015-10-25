package com.mongodb.driver;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.Helpers;
import java.util.Random;

/**
 * Created by ronald.bhuleskar on 10/22/15.
 */
public class UpdateTest {
    public static void main(String[] args) {
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
        MongoClient client = new MongoClient(new ServerAddress(), options);

        MongoDatabase db = client.getDatabase("test").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> collection = db.getCollection("findWithFilterTest");

        collection.drop();

        //insert 10 docs
        for (int i = 0; i < 8; i++) {
            collection.insertOne(new Document()
                    .append("_id", i)
                    .append("x", i)
            );
        }

        //UPDATE
        collection.replaceOne(eq("x", 5), new Document("_id", 5).append("x", 10).append("update", true));

        collection.updateOne(
                eq("x", 9),
                new Document("$set", new Document("_id", 20).append("x", 20)),
                new UpdateOptions().upsert(true));

        collection.updateMany(
                lte("_id", 3),
                new Document("$inc", new Document("x", 1)));

        //DELETE
        collection.deleteOne(new Document("_id", 7));
        collection.deleteMany(lt("x", 4));

        for (Document document : collection.find().into(new ArrayList<Document>())) {
            Helpers.printJson(document);
        }
    }
}
