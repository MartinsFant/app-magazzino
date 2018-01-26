package marti.magazzino.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import marti.magazzino.models.GeneralProduct;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class GeneralProductRepository {
    private MongoCollection<Document> collection;

    public GeneralProductRepository() {
        try {
            MongoDatabase db = Mongo.getInstance().getConnection();
            this.collection = db.getCollection(Repository.REPO_GENERAL);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public void insert(GeneralProduct p) {
        collection.insertOne(p.toDocument());
    }

    public GeneralProduct find(String productCode) {
        FindIterable<Document> res = collection.find(new Document(GeneralProduct.FIELD_GENERAL_PRODUCT_CODE, productCode));
        return GeneralProduct.parseDocument(res.first());
    }

    public Boolean delete(GeneralProduct p) {
        DeleteResult r = collection.deleteOne(p.toDocument());
        return r.wasAcknowledged();
    }


    public List<GeneralProduct> getGeneralProductList() {
        List<GeneralProduct> generalProducts = new ArrayList<GeneralProduct>();
        List<Document> documents = this.collection.find().into(new ArrayList<Document>());
        for (Document d : documents) {
            generalProducts.add(GeneralProduct.parseDocument(d));
        }

        return generalProducts;
    }
}
