import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.*;

import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;

public class testClass {

    public static void main(String[] args ){
        //{"_id":{"$oid":"5e3fd6ff63c2db07d87b7172"},"abiturients":[{"login":"test"},{"login":"test1"}],"employees":{}}
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("prkom");
        MongoCollection<Document> collection =  database.getCollection("Accounts");

        FindIterable<Document> findIt = collection.find(new Document ());
        for (Document doc : findIt) {
            System.out.println(doc.toJson());
        }
        Iterator iterator = findIt.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }

}
