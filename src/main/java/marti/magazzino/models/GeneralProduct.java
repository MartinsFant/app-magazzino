package marti.magazzino.models;

import javafx.beans.property.SimpleStringProperty;
import org.bson.Document;

import java.util.Map;

public class GeneralProduct {

    public static final String FIELD_GENERAL_PRODUCT_CODE = "generalProductCode";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PURCHASE_PRICE = "purchasePrice";

    private SimpleStringProperty generalProductCode, name, description;
    private Double purchasePrice;


    public GeneralProduct(String generalCode, String name, String description, Double purchasePrice) {
        this.generalProductCode = new SimpleStringProperty(generalCode);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.purchasePrice = purchasePrice;
    }


    public String getGeneralProductCode() {
        return generalProductCode.get();
    }

    public void setGeneralProductCode(String productCode) {
        this.generalProductCode = new SimpleStringProperty(productCode);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description = new SimpleStringProperty(description);
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Document toDocument() {
        return new Document("generalProductCode", this.getGeneralProductCode())
                .append("name", this.getName())
                .append("description", this.getDescription())
                .append("purchasePrice", this.getPurchasePrice());
    }

    public static GeneralProduct parseDocument(Document d) {
        return new GeneralProduct(d.getString(FIELD_GENERAL_PRODUCT_CODE),
                d.getString(FIELD_NAME),
                d.getString(FIELD_DESCRIPTION),
                d.getDouble(FIELD_PURCHASE_PRICE));
    }

}
