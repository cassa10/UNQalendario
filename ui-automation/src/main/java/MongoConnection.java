import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;

public class MongoConnection {

    MongoClient mongoClient;
    DBCollection coleccion;

    public MongoConnection() {
        try{
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));}
        catch (UnknownHostException e) {System.out.println("error when connecting to mongo");}
    }

    public void deleteAllUsers(){
        mongoClient.getDB("test").getCollection("Usuario").drop();
    }
}
