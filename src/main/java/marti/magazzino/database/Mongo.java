package marti.magazzino.database;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;

public class Mongo extends MongoClient {
    private static final String HOST = "localhost";
    private static final int DB_PORT = 27017;
    private static final String DB_NAME = "Inventario";

    private static Mongo instance;

    private Mongo(String host, int port) {
        super(host, port);
    }

    public static Mongo getInstance() {
        if(instance == null) {
            instance = new Mongo(HOST, DB_PORT);
        }
        return instance;
    }

    public MongoDatabase getConnection() throws MongoException {
        try {
            return instance.getDatabase(DB_NAME);
        } catch (Exception e) {
                        throw new MongoException("ERROR:" + e.getMessage());

        }
    }
}


