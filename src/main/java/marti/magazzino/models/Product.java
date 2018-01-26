package marti.magazzino.models;

import javafx.beans.property.SimpleStringProperty;
import org.bson.Document;

public class Product {

    public static final String FIELD_PRODUCT_CODE = "productCode";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_SALE_PRICE = "salePrice";

    private SimpleStringProperty productCode;
    private Integer quantity;
    private Double salePrice;

    public Product(String productCode, Integer quantity, Double salePrice) {
        this.productCode = new SimpleStringProperty(productCode);
        this.quantity = quantity;
        this.salePrice = salePrice;
    }

    public String getProductCode() {
        return productCode.get();
    }

    public void setProductCode(String productCode) {
        this.productCode = new SimpleStringProperty(productCode);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }


    public Document toDocument() {
        return new Document(FIELD_PRODUCT_CODE, this.getProductCode())
                .append(FIELD_QUANTITY, this.getQuantity())
                .append(FIELD_SALE_PRICE, this.getSalePrice());
    }

    public static Product parseDocument(Document d) {
        return new Product(d.getString(FIELD_PRODUCT_CODE),
                d.getInteger(FIELD_QUANTITY),
                d.getDouble(FIELD_SALE_PRICE));
    }
}
