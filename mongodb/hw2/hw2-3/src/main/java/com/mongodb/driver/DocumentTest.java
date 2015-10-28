package com.mongodb.driver;

import com.mongodb.util.Helpers;
import org.bson.BsonDocument;
import org.bson.BsonSymbol;
import org.bson.Document;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by ronald.bhuleskar on 10/21/15.
 */
public class DocumentTest {
    public static void main(String[] args){
        Document document = new Document()
                .append("str", "MondoDB, Hello")
                .append("int", 42)
                .append("l", 1l)
                .append("double", 1.1)
                .append("b", false)
                .append("date", new Date())
                .append("null", null)
                .append("embeddedDoc", new Document("x", 0))
                .append("list", Arrays.asList(1, 2, 3));

        Helpers.printJson(document);

        BsonDocument bsonDocument = new BsonDocument("str",
                new BsonSymbol("MongoDB, Hello"));

    }
}
