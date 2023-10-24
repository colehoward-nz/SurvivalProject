package me.cole.survivalproject;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Mongo {
    public MongoDatabase database;

    public void createConnection() {

        MongoClient mongoClient = MongoClients.create("mongodb+srv://Admin:admin@survivalproject.flvleyi.mongodb.net/?retryWrites=true&w=majority");
        database = mongoClient.getDatabase("colesmongo");

        System.out.println("[ColeCore] MongoDBTest connected successfully");
    }

}
