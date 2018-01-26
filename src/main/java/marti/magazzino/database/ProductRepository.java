package marti.magazzino.database;



import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import marti.magazzino.models.Product;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private MongoCollection<Document> collection;

    public ProductRepository(String repositoryName) {
        try {
            MongoDatabase db = Mongo.getInstance().getConnection();
            this.collection = db.getCollection(repositoryName);


        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public void insert(Product p) {

        collection.insertOne(p.toDocument());
    }

    public Boolean delete(Product p) {
        DeleteResult r = collection.deleteOne(p.toDocument());
        return r.wasAcknowledged();
    }

    public Boolean update(Product p) {
        Document d = collection.findOneAndDelete(new Document(Product.FIELD_PRODUCT_CODE, p.getProductCode()));
        if(null != d && d.get(Product.FIELD_PRODUCT_CODE).equals(p.getProductCode())){
            return true;
        } else {
            return false;
        }
    }

    public List<Product> getProductList() {
        List<Product> products = new ArrayList<Product>();
        List<Document> documents = this.collection.find().into(new ArrayList<Document>());
        for (Document d : documents) {
            products.add(Product.parseDocument(d));
        }


        return products;
    }


}
