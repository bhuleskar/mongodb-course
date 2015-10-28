package com.mongodb.hw2_3;

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

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.UpdateOptions;
import org.bson.BsonDocument;
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
public class DeleteLowestHomework {

    public static void main(String[] args){
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
        MongoClient client = new MongoClient(new ServerAddress(), options);

        MongoDatabase db = client.getDatabase("students").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> collection = db.getCollection("grades");

        List<Document> toDelete = new ArrayList();

        for (int i = 0; i < 200; i++) {
            List<Document> row = collection.find(and(eq("student_id", i), eq("type", "homework")))
                    .sort(ascending("score"))
                    .limit(1)
                    .into(new ArrayList());

            toDelete.addAll(row);
        }

        //TODO How can we use Collection.deleteMany() here and do all in one shot!!
        for(Document document : toDelete) {
            collection.deleteOne(document);
        }
    }

}
