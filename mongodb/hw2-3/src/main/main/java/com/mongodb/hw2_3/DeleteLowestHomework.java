package com.mongodb.hw2_3;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


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
