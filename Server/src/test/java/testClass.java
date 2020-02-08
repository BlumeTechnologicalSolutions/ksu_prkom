import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class testClass {

    public static void main(String[] args ){
        System.out.println("Start mongodb:");
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //MongoClient mongoClient = new MongoClient();


        for(String db: mongoClient.getDatabaseNames() ){
            System.out.println("DB mongodb:"+db);
        }
        DB database = mongoClient.getDB("testDb");

        DBCollection collection = database.getCollection("testCol");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("", "");
        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        BasicDBObject document = new BasicDBObject();
        document.put("name", "Shubham");
        document.put("company", "Baeldung");
        collection.insert(document);

        cursor = collection.find();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();

            System.out.println( dbObject);
        }
    }

}
