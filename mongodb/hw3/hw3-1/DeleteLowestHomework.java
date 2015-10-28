package com.mongodb.hw3_1;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonDocument;
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

        MongoDatabase db = client.getDatabase("school").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> collection = db.getCollection("students");

        List<Document> toDelete = new ArrayList();

        List<Document> all = collection.find()
                .into(new ArrayList<Document>());

        for (Document doc : all) {

            Document newDoc = doc;
            List<Document> scores = (List<Document>) newDoc.get("scores");

            double min = Double.MAX_VALUE;
            int index = -1;
            int indexToDelete = -1;
            //just for logging
            List<Double> homeworkScores = new ArrayList<Double>(2);
            for (Document score : scores) {
                index++;

                //if not type=homework -- continue
                if (! "homework".equals(score.getString("type"))) {
                    continue;
                }

                double temp = score.getDouble("score");
                if (min > temp) {
                    min = temp;
                    indexToDelete = index;
                }
                homeworkScores.add(temp);
            }
            System.out.println("student : " + doc.getInteger("_id") + " removing hw with score : " + min + "from [" + flatten(homeworkScores) +"]");
            scores.remove(indexToDelete);
            collection.findOneAndReplace(eq("_id", doc.getInteger("_id")), doc);
        }
    }

    private static String flatten(List<Double> scores) {
        StringBuilder sb = new StringBuilder();
        for (double score : scores) {
            sb.append(score).append(", ");
        }
        sb.delete(sb.length()-2, sb.length()-1);
        return sb.toString();
    }

}
