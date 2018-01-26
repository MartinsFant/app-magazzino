package marti.magazzino;



import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import marti.magazzino.database.Mongo;
import marti.magazzino.database.MongoException;
import marti.magazzino.models.GeneralProduct;
import marti.magazzino.models.Product;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Main menu");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}



//            Item prodo = new Item("martina", "martina frangtta", 1);
//            Document doc = new Document("nome", prodo.getName())
//                    .append("description", prodo.getDescription())
//                    .append("price", prodo.getPrice());
//
//
//
//            collection.insertOne(prodo);
//
//            List<Item> pList = new ArrayList<Item>();
//            int prz = 0;
//            for (int i = 0; i < 50; i++) {
//                Item prodo = new Item("1", "martina", "martina frangtta", 1);
//                collection.insertOne(new Document("nome", "martina" + String.valueOf(i))
//                        .append("description", "martina frangtta")
//                        .append("price", prz += i));
//
//            }
