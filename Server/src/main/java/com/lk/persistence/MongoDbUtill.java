package com.lk.persistence;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDbUtill {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    private static final String DATABASE_NAME = "prkom";
    private static final String CONNECTION_ADDRESS = "localhost";
    private static final Integer PORT = 27017;

    private static MongoClient connection() {
        if(mongoClient!=null) return  mongoClient;
        mongoClient = new MongoClient(CONNECTION_ADDRESS, PORT);
        return mongoClient;
    }

    public static MongoDatabase getDataBase() {
        if(mongoClient==null) mongoClient = connection();
        database = mongoClient.getDatabase(DATABASE_NAME);
        return database;
    }


}
